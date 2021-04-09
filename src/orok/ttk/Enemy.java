package orok.ttk;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.Math;

public class Enemy {
	
	public String name;
	public double level;
	public String armorType;
	public String healthType;
	public String shieldType;
	public double baseArmor;
	public double modifiedBaseArmor;
	public double baseHealth;
	public double baseLevel;
	public double baseShield;
	
	public boolean eidolon;
	public boolean shattering_impact;
	public boolean amalgam_shattering_impact;
	public boolean demolyst;
	public boolean acolyte;
	
	public double[] shield_multipliers = new double[20];
	public double[] health_multipliers = new double[20];
	public double[] armor_multipliers = new double[20];
	public double[] bypass_multipliers = new double[20];
	public double[] armor_dr_multipliers = new double[20];

	double[] init = new double[20];
	
	public Dot heatDot = new Dot(1000000000,0,init,3);
	public Dot heat_dot_armor_reduction = new Dot(1000000000,0,init,15);
	public Dot heat_dot_armor_regeneration = new Dot(1000000000,0,init,16);
	double heat_armor_reduction = 1;
	
	private double armor;
	private double shield;
	private double health;
	private boolean armorGone;
	
	public double totTrueDotDmg;
	public double totToxDotDmg;
	public double totHeatDotDmg;
	public double totElectricDotDmg;
	public double totGasDotDmg;
	
	private double viralMult=1;
	private double magneticMult=1;
	
	public double health_scale =1;
	public double armor_scale =1;
	public double shield_scale =1;
	
	public static int electricity_offset = 1000000000;
	public static int gas_offset = 1000000000;
	public static int heat_offset = 1000000000;
	
	private Random rProc = new Random();
	
	public double armorReduct;
	
	Deque<Dot> slQ = new LinkedList<>();
	Deque<Dot> toxQ = new LinkedList<>();
	Deque<Dot> elecQ = new LinkedList<>();	
	
	LinkedList<Dot> viralQ = new LinkedList<>();
	LinkedList<Dot> corrosiveQ = new LinkedList<>();
	LinkedList<Dot> magneticQ = new LinkedList<>();
	LinkedList<Dot> gasQ = new LinkedList<>();
	
	private Weapon current_weapon;
	private double status_duration = 1;

	public Enemy(String name, double level, double armorReduct, Weapon weapon) {
		this.name = name;
		this.level = level;
		this.armorReduct=armorReduct;
		this.current_weapon = weapon;
		this.status_duration = weapon.statusDurationPercent;
		
		getEnemy(name);
		reset(armorReduct);
		
		set_bypass_multipliers();
		
		if(name.contains("Acolyte")) {
			acolyte = true;
		}
	}
	
	public class Dot {
		private double offset;
		private double life; //in milliseconds
		private double[] damage;
		//private double status_duration;
		
		int identifier;
		boolean heat_active;
		int count;
		double base_life;
		int tick;
		/*
		IDENTIFIERS
	    0 impact 
	    1 puncture
	    2 Slash
	    3 Heat
	    4 Cold
	    5 Electric
	    6 Toxin
	    7 Blast
	    8 Radiation
	    9 Gas
	    10 Magnetic
	    11 Viral
	    12 Corrosive
	    13 Void
	    14 True
	    15 heat armor reduction
	    16 heat armor regeneration
	    17
	    18
	    19
	    */
		
		public Dot(double d, double e, double[] proc_damage, int id) {
			this.offset = d;
			//this.status_duration = get_status_duration();

			this.life = e ;
			this.damage = proc_damage;
			this.identifier = id;
			this.heat_active = false;
			this.base_life = e;
			this.tick = 0;
		}
		
		//life is in ms
		public void setLife(double d) {
			this.life = d ;
		}
		public void setOffset(double d) {
			this.offset = d;
		}
		public void setDamage(double[] damage) {
			this.damage = damage;
		}
		public void decLife(int period) {
			life =life-period;
			
		}
		public double getOffset() {
			return offset;
		}
		public double getLife() {
			return life;
		}
		public double[] getDamage() {
			return damage;
		}
		public void reset() {
			offset = 1000000;
			life = 0;
			damage = new double[20];
			heat_active = false;
			tick = 0;
		}
		public void execute() {
			//slash
			if(identifier == 2) {
				totTrueDotDmg += (health+shield);
				damage_enemy(this.damage, 1, true);

				this.decLife(1000);
				this.setOffset(offset + 1000);
				
				if(health<0)
					health =0;
				if(shield<0)
					shield=0;
				
				totTrueDotDmg -= (health+shield);
				//death scan
				if(this.getLife() <= 0){
					slQ.removeFirst();
				}else {
					slQ.addLast(slQ.removeFirst());
				}
				
				// REMOVE ARMOR
				amalgam_armor_removal();
			}
			//heat
			else if(identifier == 3) {
				//expired
				
				//start armor regeneration
				heat_dot_armor_regeneration.setLife((int) (6000 * status_duration));
				
				//start regeneration
				heat_dot_armor_regeneration.setLife((int) (6000 * status_duration));
				heat_dot_armor_regeneration.setOffset(heatDot.getOffset() + 1500 * status_duration);
				heat_dot_armor_regeneration.tick = 4;
				
				heat_offset = 1000000;
				heatDot.reset();
				
			}
			//electric
			else if(identifier == 5) {

				elecQ.removeFirst();
				
				if(elecQ.isEmpty()) {
					Enemy.electricity_offset = 1000000;
				}
					
			}
			//toxin
			else if(identifier == 6) {
				totToxDotDmg += health; 	//lazy way of adding dot damage without new variable
				
				damage_enemy(this.damage,1,true);
				
				this.decLife(1000);
				
				if(health<0)
					health =0;
				
				totToxDotDmg -= health;		//lazy way of adding dot damage without new variable
				
				this.setOffset(offset+1000);
				//removal scan
				if(this.getLife() <= 0){
					toxQ.removeFirst();
				}else {
					toxQ.addLast(toxQ.removeFirst());
				}
				
				// REMOVE ARMOR
				amalgam_armor_removal();

			}
			//gas
			else if(identifier == 9) {
				gasQ.removeFirst();
				
				if(gasQ.isEmpty()) {
					Enemy.gas_offset = 1000000;
				}
			}
			//magnetic
			else if(identifier == 10) {
				//remove 
				magneticQ.removeFirst();
				
				int size = magneticQ.size();
				
				if(size<=0) {
					magneticMult =1;
				}
				else if(size<=10 && size > 1) {
					magneticMult = 2+0.25*(size-1);
				}
				else if(size == 1) {
					magneticMult =2;
				}		

			}
			//viral
			else if(identifier == 11) {
				//remove 
				viralQ.removeFirst();
		
				int size = viralQ.size();
				
				if(size<=0) {
					viralMult =1;
				}
				else if(size<=10 && size > 1) {
					viralMult = 2+0.25*(size-1);
				}
				else if(size == 1) {
					viralMult =2;
				}		
				
			}
			//corrosive
			else if(identifier == 12) {
				//remove 
				corrosiveQ.removeFirst();

				int size = corrosiveQ.size();
				
				if(size<=0) {
				}
				else if(size<=10 && size > 1) {
					armor= armor*(1-((size-1)*0.06+0.2))/(1-((size)*0.06+0.2));
				}
				else if(size == 1) {
					armor = armor/(1-0.2);
				}		

			}
			//heat armor strip
			else if(identifier == 15) {
				double prev_strip = get_heat_armor_strip(tick);
				tick ++;
				double cur_strip = get_heat_armor_strip(tick);
				heat_armor_reduction = cur_strip;
				
				armor = armor*cur_strip/prev_strip;
				
				this.decLife((int) (500* status_duration));
				if(heat_dot_armor_reduction.getLife() <= 0) {
					
					
					heat_dot_armor_reduction.reset();
				}
				else {
					heat_dot_armor_reduction.setOffset(heat_dot_armor_reduction.getOffset() + 500* status_duration);
					
				}
				
			}
			//heat armor regen
			else if(identifier == 16) {
				double prev_strip = get_heat_armor_strip(tick);
				tick --;
				double cur_strip = get_heat_armor_strip(tick);
				heat_armor_reduction = cur_strip;
				
				armor = armor*cur_strip/prev_strip;
				
				heat_dot_armor_regeneration.decLife((int) (1500* status_duration));
				if(heat_dot_armor_regeneration.getLife() <= 0) {
					heat_dot_armor_regeneration.reset();
				}
				else {
					heat_dot_armor_regeneration.setOffset(heat_dot_armor_regeneration.getOffset() + 1500* status_duration);
					
				}
			}
			
		}
		public double get_heat_armor_strip(int index) {
			double strip = 1;
			if(index == 0) {
				strip = 1;
			}
			else if(index == 1) {
				strip = 0.85;
			}
			else if(index == 2) {
				strip = 0.7;
			}
			else if(index == 3) {
				strip = 0.6;
			}
			else if(index == 4) {
				strip = 0.5;
			}
			return strip;
		}
		
		public void electric_trigger() {
			totElectricDotDmg += (health + shield);
			
			double total_elec_dot = 0;
			double [] dmg = new double[20];
			for (Dot item: elecQ) {
				total_elec_dot += item.getDamage()[5];
	        }
			//System.out.println(elecQ.size());
			
			Enemy.electricity_offset += 1000;
			
			//Note, electric procced on head will trigger headshot multiplier again
			dmg[5] = total_elec_dot * current_weapon.headshot_multiplier;

			
			damage_enemy(dmg, 1, true);
			
			// REMOVE ARMOR
			amalgam_armor_removal();
			
			totElectricDotDmg -= (health + shield);
		}
		public void gas_trigger() {
			totGasDotDmg += (health + shield);
			
			double total_gas_dot = 0;
			double [] dmg = new double[20];
			for (Dot item: gasQ) {
				total_gas_dot += item.getDamage()[9];
	        }
			
			Enemy.gas_offset += 1000;
			
			//Note, gas procced on head will trigger headshot multiplier again
			dmg[9] = total_gas_dot * current_weapon.headshot_multiplier;
			
			damage_enemy(dmg, 1, true);
			
			// REMOVE ARMOR
			amalgam_armor_removal();
			
			totGasDotDmg -= (health + shield);
		}
		public void heat_trigger() {
			totHeatDotDmg += (health + shield);
			
			Enemy.heat_offset += 1000;
			
			damage_enemy(heatDot.getDamage(), 1, true);
			//System.out.println(heatDot.getDamage()[3]);
			
			// REMOVE ARMOR
			amalgam_armor_removal();
			
			totHeatDotDmg -= (health + shield);
		}
	}
	
	
	public void damage_enemy(double[] damage, double crit_multiplier, boolean proc) {
		// Apply current_weapon.getMultiplier(), before passing array
		double total_shield_damage = 0;
		double total_health_damage = 0;
		
		
		if(shield >0 ) {
			
			total_shield_damage = array_multiply_sum(damage, shield_multipliers) * magneticMult;
			if(armor > 0) {
				total_health_damage = damage[6] * armor_multipliers[6] * health_multipliers[6] * get_armor_dr()[6] * viralMult;
			}
			else {
				total_health_damage = damage[6] * health_multipliers[6] * viralMult;
			}

		}
		else if(armor > 0) {
			total_health_damage = array_multiply_sum(damage, array_multiply(armor_multipliers,array_multiply(health_multipliers,get_armor_dr()))) * viralMult;
			//System.out.println(total_health_damage);
		}
		else {
			total_health_damage = array_multiply_sum(damage, health_multipliers) * viralMult;
		}
		
		
		//double shield_dr = get_dr(proc, total_shield_damage * current_weapon.fire_rate_non_melee * current_weapon.multishot_mods * current_weapon.multishot_scalar);
		//double health_dr = get_dr(proc, (total_health_damage + shield_multipliers[6]*damage[6]) * current_weapon.fire_rate_non_melee * current_weapon.multishot_scalar * current_weapon.multishot_mods);
		double shield_dr = get_dr(proc, total_shield_damage , current_weapon.fire_rate_non_melee * current_weapon.multishot_mods * current_weapon.multishot_scalar);
		double health_dr = get_dr(proc, (total_health_damage + shield_multipliers[6]*damage[6]) , current_weapon.fire_rate_non_melee * current_weapon.multishot_scalar * current_weapon.multishot_mods);
				
		System.out.printf("Damage: %f, ",total_shield_damage);
		
		total_health_damage = total_health_damage * crit_multiplier * health_dr;
		total_shield_damage = total_shield_damage * crit_multiplier * shield_dr;
		
		//System.out.printf("R-Damage: %f, DR: %f, Crit Mult: %f, Proc: %b\n",total_health_damage, health_dr, crit_multiplier, proc );
		System.out.printf("Health Damage: %f, Shield Damage: %f,Crit Mult: %f, Proc: %b\n",total_health_damage,total_shield_damage,crit_multiplier, proc);
		
		
		health -= total_health_damage  ;
		shield -= total_shield_damage ;
		
		if(shield < 0) {
			double frac = Math.abs(shield/(total_shield_damage * shield_dr));
			if(armor > 0) {
				total_health_damage = array_multiply_sum(damage, array_multiply(armor_multipliers,array_multiply(health_multipliers,get_armor_dr()))) * viralMult;
			}
			else {
				total_health_damage = array_multiply_sum(damage, health_multipliers) * viralMult;
			}
			//double overflow_dr = get_dr(proc, frac * total_health_damage * 0.05 * current_weapon.fire_rate_non_melee * current_weapon.multishot_mods * current_weapon.multishot_scalar / crit_multiplier);
			double overflow_dr = get_dr(proc, frac * total_health_damage * 0.05 / crit_multiplier , current_weapon.fire_rate_non_melee * current_weapon.multishot_mods * current_weapon.multishot_scalar );
			
			System.out.printf("	frac: %f, health damage: %f, overflow dr: %f\n",frac,total_health_damage,overflow_dr);
			
			
			health -= frac * total_health_damage * 0.05 * overflow_dr; 
			shield = 0;
		}
		if(health <0) {
			health = 0;
		}
	}
	public double[] display_damage(double []damage, Weapon wep) {
		// Apply current_weapon.getMultiplier(), before passing array
		//double []damage = current_weapon.damage_array;
		
		double total_shield_damage = 0;
		double total_health_damage = 0;
		
		
		if(shield >0 ) {
			
			total_shield_damage = array_multiply_sum(damage, shield_multipliers) * magneticMult;
			if(armor > 0) {
				total_health_damage = damage[6] * armor_multipliers[6] * health_multipliers[6] * get_armor_dr()[6] * viralMult;
			}
			else {
				total_health_damage = damage[6] * health_multipliers[6] * viralMult;
			}

		}
		else if(armor > 0) {
			total_health_damage = array_multiply_sum(damage, array_multiply(armor_multipliers,array_multiply(health_multipliers,get_armor_dr()))) * viralMult;
			//System.out.println(total_health_damage);
		}
		else {
			total_health_damage = array_multiply_sum(damage, health_multipliers) * viralMult;
		}
		
		
		double shield_dr = get_dr(false, total_shield_damage , wep.fire_rate_non_melee * wep.multishot_mods * wep.multishot_scalar);
		double health_dr = get_dr(false, (total_health_damage + shield_multipliers[6]*damage[6]) , wep.fire_rate_non_melee * wep.multishot_scalar * wep.multishot_mods);
		//System.out.printf("Damage: %f, ",total_health_damage*crit_multiplier);
		
		total_health_damage = total_health_damage * health_dr;
		total_shield_damage = total_shield_damage * shield_dr;
		
		//System.out.printf("Health Damage: %f, Shield Damage: %f\n",total_health_damage,total_shield_damage);
		
		double [] res = { total_health_damage, total_shield_damage };
		
		return res;
		
	}


	
	public double demolyst_dr(boolean proc, double dps) {
		double dr=1;
		if(!demolyst) {
			return 1;
		}
		if(proc) {
			if(dps<1562.5) {
				dr=0.64;
			}else if(dps>=1562.5 && dps <3906.25) {
				dr=(dps*0.512+200)/dps;
			}else if(dps>=3906.25 && dps <7812.5) {
				dr=(dps*0.448+450)/dps;
			}else if(dps>=7812.5 && dps <15625) {
				dr=(dps*0.256+1950)/dps;
			}else if(dps>=15625 && dps <31250) {
				dr=(dps*0.128+3950)/dps;
			}else if(dps>=31250) {
				dr=(dps*0.064+5950)/dps;
			}
		}else {
			if(dps<1250) {
				dr=0.8;
			}else if(dps>=1250 && dps <3125) {
				dr=(dps*0.64+200)/dps;
			}else if(dps>=3125 && dps <6250) {
				dr=(dps*0.56+450)/dps;
			}else if(dps>=6250 && dps <12500) {
				dr=(dps*0.32+1950)/dps;
			}else if(dps>=12500 && dps <25000) {
				dr=(dps*0.16+3950)/dps;
			}else if(dps>=25000) {
				dr=(dps*0.08+5950)/dps;
			}

		}
		
		return dr;
		
	}
	
	public double get_dr(boolean proc, double base, double dps_multiplier) {
		double dr = 1;
		base = base * dps_multiplier;
		if(acolyte) {
			if(proc) {
				if(shield > 0) {
					if(base<2000*0.6) {
						dr=2.5;
					}else if(base>=1200 && base <3000) {
						dr=(base*2+600)/base;
					}else if(base>=3000 && base <9000) {
						dr=(base*1.333333333+2600)/base;
					}else if(base>=9000) {
						dr=(base*0+14600)/base;
					}
				}
				else if(armor > 0) {
					if(base<8000*0.5) {
						dr=0.375/0.5;
					}else if(base>=8000*0.5 && base <20000*0.5) {
						dr=(base*0.3/0.5+600)/base;
					}else if(base>=20000*0.5 && base <60000*0.5) {
						dr=(base*0.2/0.5+2600)/base;
					}else if(base>=60000*0.5) {
						dr=(base*0+14600)/base;
					}
				}
				else {
					if(base<8000*0.5) {
						dr=0.375/0.5;
					}else if(base>=8000*0.5 && base <20000*0.5) {
						dr=(base*0.3/0.5+600)/base;
					}else if(base>=20000*0.5 && base <60000*0.5) {
						dr=(base*0.2/0.5+2600)/base;
					}else if(base>=60000*0.5) {
						dr=(base*0+14600)/base;
					}
				}
			}else {
				if(shield > 0) {
					if(base<2000) {
						dr=1.5;
					}else if(base>=2000 && base <5000) {
						dr=(base*1.2+600)/base;
					}else if(base>=5000 && base <15000) {
						dr=(base*0.8+2600)/base;
					}else if(base>=15000) {
						dr=(base*0+14600)/base;
					}
				}
				else if(armor > 0) {
					if(base<8000) {
						dr=0.375;
					}else if(base>=8000 && base <20000) {
						dr=(base*0.3+600)/base;
					}else if(base>=20000 && base <60000) {
						dr=(base*0.2+2600)/base;
					}else if(base>=60000) {
						dr=(base*0+14600)/base;
					}
				}
				else {
					if(base<8000) {
						dr=0.375;
					}else if(base>=8000 && base <20000) {
						dr=(base*0.3+600)/base;
					}else if(base>=20000 && base <60000) {
						dr=(base*0.2+2600)/base;
					}else if(base>=60000) {
						dr=(base*0+14600)/base;
					}
					/*
					if(base<13000) {
						dr = 3037.5/13000.0;
					}else if(base>=13000 && base <32000) {
						dr=(base*0.18 75+600)/base;
					}else if(base>=32000 && base <96000) {
						dr=(base*0.125+2600)/base;
					}else if(base>=96000) {
						dr=(base*0+14600)/base;
					}
					*/
				}
			}

		}
		else if(demolyst) {
			if(proc) {
				if(base<1562.5) {
					dr=0.64;
				}else if(base>=1562.5 && base <3906.25) {
					dr=(base*0.512+200)/base;
				}else if(base>=3906.25 && base <7812.5) {
					dr=(base*0.448+450)/base;
				}else if(base>=7812.5 && base <15625) {
					dr=(base*0.256+1950)/base;
				}else if(base>=15625 && base <31250) {
					dr=(base*0.128+3950)/base;
				}else if(base>=31250) {
					dr=(base*0.064+5950)/base;
				}
			}else {
				if(base<1250) {
					dr=0.8;
				}else if(base>=1250 && base <3125) {
					dr=(base*0.64+200)/base;
				}else if(base>=3125 && base <6250) {
					dr=(base*0.56+450)/base;
				}else if(base>=6250 && base <12500) {
					dr=(base*0.32+1950)/base;
				}else if(base>=12500 && base <25000) {
					dr=(base*0.16+3950)/base;
				}else if(base>=25000) {
					dr=(base*0.08+5950)/base;
				}

			}
		}
		
		return dr;
	}
	
	@SuppressWarnings("unchecked")
	public void getEnemy(String enemy) {

        // parsing file 136
        Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("enemy.json"));
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		} 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> e = (Map<String,Object>)jo.get(enemy); 
		armorType = (String)e.getOrDefault("ArmorType", "Ferrite");
		healthType = (String)e.getOrDefault("HealthType", "ClonedFlesh");
		shieldType = (String)e.getOrDefault("ShieldType", "Shield");
		baseArmor = ((Number)e.getOrDefault("BaseArmor", 0.0)).doubleValue();
		modifiedBaseArmor = baseArmor;
		baseHealth = ((Number)e.getOrDefault("BaseHealth", 1.0)).doubleValue();
		baseLevel = ((Number)e.getOrDefault("BaseLevel", 1.0)).doubleValue();
		baseShield = ((Number)e.getOrDefault("BaseShield", 0.0)).doubleValue();
         
        Object obj2 = null;
		try {
			obj2 = new JSONParser().parse(new FileReader("modifiers.json"));
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		} 
          
        // typecasting obj to JSONObject 
        JSONObject jo2 = (JSONObject) obj2; 
		JSONArray js = (JSONArray) jo2.get(shieldType);
		JSONArray jh = (JSONArray) jo2.get(healthType);
		JSONArray ja = (JSONArray) jo2.get(armorType);
		
        shield_multipliers = fillData(js);
    	health_multipliers = fillData(jh);
    	armor_multipliers = fillData(ja);
		
	}
	private double[] fillData(JSONArray jsonArray){

		double[] fData = new double[jsonArray.size()];

	    for (int i = 0; i < jsonArray.size(); i++) {
	    	double d = 0.0;
	    	if (jsonArray.get(i) instanceof Number) {
	    	    d = ((Number) jsonArray.get(i)).doubleValue();
	    	}
	    	fData[i] = d;
	    }
	    return fData;
	}
	
	@SuppressWarnings("unchecked")
	public static ArrayList<String> getEnemyNames() {
		ArrayList<String> enemyList = new ArrayList<String>();
        
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("enemy.json"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

        Map<String,Object> enem = (Map<String,Object>)obj;
         
        Iterator<Map.Entry<String,Object>> itr1 = enem.entrySet().iterator(); 
        while (itr1.hasNext()) { 

            Map.Entry<String,Object> pair = itr1.next(); 
            
            enemyList.add((String)pair.getKey());
        } 
        Collections.sort(enemyList);
		
		return enemyList;
		
	}

	public void applyProc(Weapon weapon,boolean crit, double critM, int millis, double random, boolean force_slash) {
		//NOTE : VIRAL SHOULD ONLY BE APPLIED WHEN APPLYING DAMAGE
		
		if(acolyte && crit) {
			critM = (critM-1)*2+1;
		}
		
		double [] proc_damage;
		if(force_slash) {
			proc_damage = new double[20];
			proc_damage[14] = weapon.slashDOT * weapon.getMultiplier() * critM;
			Dot p = new Dot(millis+1000 ,6000 * status_duration ,proc_damage, 2);
			slQ.addLast(p);
		}
		
		if(weapon.toxicLash) {
			proc_damage = new double[20];
			proc_damage[6] = weapon.toxicLashPercent * weapon.getTotalDamage() * weapon.getMultiplier() * critM / 2;
			
			Dot p = new Dot(millis+1000,6000* status_duration, proc_damage, 6); // affected by bane 3x, affected by elemental mods too
			toxQ.addLast(p);
		}
		
		if(weapon.getHunter() && Math.random() < 0.3) {
			proc_damage = new double[20];
			proc_damage[14] = weapon.slashDOT * weapon.getMultiplier() * critM;
			Dot p = new Dot(millis+1000 ,6000* status_duration ,proc_damage, 2);
			slQ.addLast(p);
		}
		
		//handle status above 100%
		for(int i = 0; i< Math.floor(weapon.status);i++) {
			procAssign(weapon,critM,millis);
		}
		//handle last chance for status below 100%
		if( (random-=(weapon.status%1)) < 0 ) {
			procAssign(weapon,critM,millis);

		}
		
	}
	
	public void procAssign(Weapon weapon, double critM, int millis) {
		//NOTE : VIRAL SHOULD ONLY BE APPLIED WHEN APPLYING DAMAGE
		double random = rProc.nextDouble();
		double []proc_damage = new double[20];
		if( (random -= weapon.slashChance) < 0) {
			proc_damage[14] = weapon.slashDOT * weapon.getMultiplier() * critM;
			Dot p = new Dot(millis+1000 ,6000* status_duration ,proc_damage, 2);
			slQ.addLast(p);
		}
		else if((random -= weapon.toxinChance) < 0){	
			proc_damage[6] = weapon.toxinDOT*weapon.getMultiplier()*critM;
			Dot p = new Dot(millis+1000 ,6000* status_duration ,proc_damage, 6);
			toxQ.addLast(p);
		}
		else if((random -= weapon.gasChance) < 0) {																
			proc_damage[9] = weapon.gasDOT*weapon.getMultiplier()*critM;
			if(gasQ.isEmpty()) {
				gas_offset = millis;
			}
			
			Dot p = new Dot(millis + 6000* status_duration,6000* status_duration,proc_damage ,9);
			gasQ.addLast(p);

			if(gasQ.size()>=10) {
				gasQ.removeFirst();
			}

		}
		else if((random -= weapon.corrosiveChance) < 0){	 
			Dot p = new Dot(millis+6000* status_duration,6000* status_duration, proc_damage, 12);
			
			int size = corrosiveQ.size();
			
			if(size<=0) {
				armor = armor*(1-0.2-0.06);
				corrosiveQ.addLast(p);
			}
			else if(size<10) {
				//p.setDamage(0.2+size*0.06);
				armor = armor*( (1-((size+1)*0.06+0.2)) /(1-(size*0.06+0.2)) );
				
				corrosiveQ.addLast(p);
			}
			else if(size >= 10) {
				//p.setDamage(0.8);
				
				corrosiveQ.removeFirst();
				corrosiveQ.addLast(p);
			}
				

			if(armor <= 1 && !armorGone) {
				armorGone = true;
				armor = 0;
			}
			
		}	
		
		else if((random -= weapon.viralChance) < 0){		
			Dot p = new Dot(millis + 6000* status_duration,6000* status_duration, proc_damage, 11);
			int size = viralQ.size();
			
			if(size<=0) {
				//p.setDamage(2);
				viralMult = 2;
				viralQ.addLast(p);
			}
			else if(size<10) {
				//p.setDamage(2+size*0.25);
				viralMult = 2+size*0.25;
				viralQ.addLast(p);
			}
			else if(size >= 10) {
				//p.setDamage(4.25);
				
				viralQ.removeFirst();
				viralQ.addLast(p);
			}									
		}	
		
		else if((random -= weapon.heatChance) < 0 ){	//heat Proc
			proc_damage[3] = weapon.heatDOT * weapon.getMultiplier() * critM;
			
			
			if(heatDot.heat_active) {
				if(acolyte) {
					if(heatDot.count < 4) {
						heatDot.count ++;
						heatDot.setDamage( array_add( proc_damage, heatDot.getDamage() ) );
						heatDot.setLife(6000* status_duration);
						heatDot.setOffset(millis + heatDot.getLife());
						
					}else {
						heatDot.setLife(6000* status_duration);
					}
				}else {
					heatDot.setDamage( array_add( proc_damage, heatDot.getDamage() ) );
					heatDot.setLife(6000* status_duration);
					heatDot.setOffset(millis + heatDot.getLife());
				}
				
				//heat_offset = millis + 1000;
			}
			else {
				heat_dot_armor_regeneration.reset();
				heatDot.setDamage( proc_damage );
				heatDot.setLife(6000* status_duration);
				heatDot.setOffset(millis + heatDot.getLife());
				heatDot.heat_active = true;
				heat_offset = millis + 1000;
				
				//start armor reduction
				heat_dot_armor_reduction.heat_active = true;
				heat_dot_armor_reduction.setOffset(millis + 500 * status_duration);
				heat_dot_armor_reduction.setLife(2000 * status_duration);

			}
			
		}
		else if((random -= weapon.magneticChance) < 0){	//mag Proc
			Dot p = new Dot(millis+6000* status_duration,6000* status_duration, proc_damage, 10);
			int size = magneticQ.size();
			
			if(size<=0) {
				//p.setDamage(2);
				magneticMult =2;
				magneticQ.addLast(p);
			}
			else if(size<10) {
				//p.setDamage(2+size*0.25);
				magneticMult = 2+size*0.25;
				magneticQ.addLast(p);
			}
			else if(size >= 10) {
				//p.setDamage(4.25);
				
				magneticQ.removeFirst();
				magneticQ.addLast(p);
			}
		}
		else if((random -= weapon.electricityChance) < 0 ) {
			Dot p;
			proc_damage[5] = weapon.electricityDOT*weapon.getMultiplier()*critM;
			if(elecQ.isEmpty()) {
				Enemy.electricity_offset = millis;
				p = new Dot(millis+6000* status_duration, 6000* status_duration, proc_damage, 5);
			}
			else {
				p = new Dot(millis+6000* status_duration, 6000* status_duration, proc_damage, 5);
			}

			
			elecQ.addLast(p);
					
		}
		
		//limit # of procs active if acolyte
		proc_limiter();
		
	}
	private void proc_limiter() {
		if(acolyte) {
			int sz = slQ.size();
			int rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					slQ.removeFirst();
				}
			}
			
			sz = toxQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					toxQ.removeFirst();
				}
			}
			
			sz = gasQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					gasQ.removeFirst();
				}
			}
			
			sz = magneticQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					magneticQ.removeFirst();
				}
			}
			
			sz = corrosiveQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					corrosiveQ.removeFirst();
				}
			}
			
			sz = viralQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					viralQ.removeFirst();
				}
			}
			
			sz = elecQ.size();
			rem = sz -4 ;
			if(rem > 0) {
				for(int i =0;i<rem;i++) {
					elecQ.removeFirst();
				}
			}
		}
	}
	
	public void reset(double armorReduct) {
		modifiedBaseArmor =baseArmor;
		slQ.clear();
		toxQ.clear();
		gasQ.clear();
		magneticQ.clear();
		corrosiveQ.clear();
		viralQ.clear();
		elecQ.clear();

		
		health  = heathScale(baseHealth,level,baseLevel);
		armor  = armorScale(baseArmor,level,baseLevel) * armorReduct;
		shield = shieldScale(baseShield,level,baseLevel);
		
		electricity_offset = 1000000;
		gas_offset = 1000000;
		heat_offset = 1000000;
		//shattering_impact=false;
		
		if(armor >1) {
			armorGone = false;
		}

		totToxDotDmg =0;
		totTrueDotDmg =0;
		totHeatDotDmg=0;
		totElectricDotDmg = 0;
		totGasDotDmg = 0;
		
		heatDot.reset();
		heat_dot_armor_reduction.reset();
		heat_dot_armor_regeneration.reset();
		
		
		
	}
	
	public double getCorrosiveReduction() {
		int size = corrosiveQ.size();
		double reduc=1;
		
		if(size<=0) {
			reduc=1;
		}
		else if(size<=10 && size > 1) {
			reduc= 1-((size)*0.06+0.2);
		}
		else if(size == 1) {
			reduc = 0.8;
		}
		return reduc;
	}
	public double getHeatArmorReduction() {
		return heat_armor_reduction;
	}
	
	public void resist(double resistance) {
		health_multipliers = array_add_const(health_multipliers, -resistance);
		health_multipliers = check_positive(health_multipliers);
		
		shield_multipliers = array_add_const(shield_multipliers, -resistance);
		shield_multipliers = check_positive(shield_multipliers);
		
	}

	public double armorScale(double a, double level, double b_level){
		if(level<b_level) {
			level = b_level;
		}
		
		double diff = level- b_level;
		double c = Math.pow(diff,1.75);
		double func1 = (1+c *0.005);
		double func2 = (1+0.4*Math.pow(diff, 0.75));
		double func3 = Math.min(1, (Math.max(level, b_level+60)-(60+b_level))/20);
		double tot_mult = 1+(func1-1)*(1-func3)+(func2-1)*func3;
		
		return a*tot_mult*armor_scale;
	}
	
	private double shieldScale(double b_s, double level, double b_level){
		if(level<b_level) {
			level = b_level;
		}
		
		double diff = level- b_level;
		double c = Math.pow(diff,2);
		double func1 = (1+c *0.0075);
		double func2 = (1+1.6*Math.pow(diff, 0.75));
		double func3 = Math.min(1, (Math.max(level, b_level+70)-(70+b_level))/15);
		double tot_mult = 1+(func1-1)*(1-func3)+(func2-1)*func3;
		
		return b_s*tot_mult*shield_scale;
	}
	
	private double heathScale(double health, double level, double b_level){
		if(level<b_level) {
			level = b_level;
		}
		double diff = level- b_level;
		double c = Math.pow(diff,2);
		double func1 = (1+c *0.015);
		double func2 = (1+10.75*Math.pow(diff, 0.5));
		double func3 = Math.min(1, (Math.max(level, b_level+70)-(70+b_level))/15);
		double tot_mult = 1+(func1-1)*(1-func3)+(func2-1)*func3;
		
		return health*tot_mult*health_scale;
	}
	public void setViralMult(double m) {
		this.viralMult = m;
	}
	public void setMagneticMult(double m) {
		this.magneticMult = m;
	}
	public double ehp(){
		return health/damageReduction(armor) + shield;
	}
	
	private double damageReduction(double a){
		return 1/(1+a/300);
	}	
	
	public double getHealthRemaining() {
		return health;
	}
	public void setHealth(double h) {
		this.health=h;
	}
	public double getShieldRemaining() {
		return shield;
	}
	public void setShield(double s) {
		this.shield=s;
	}
	public double getArmorRemaining() {
		return armor;
	}
	public void setArmor(double a) {
		this.armor=a;
	}
	public boolean getArmorGone() {
		return armorGone;
	}
	public double[] get_armor_dr() {
		int l1 = armor_multipliers.length;
		double[] result = new double[20];
		for(int i = 0; i < l1; i++) {
			if(i == 14) {
				result[i] = 1.0;
			}else {
				result[i] = 1.0/(1.0 + (armor * bypass_multipliers[i])/300.0);
			}

			
		}
		return result;
	}
	public void amalgam_armor_removal() {
		if(amalgam_shattering_impact) {
			if(modifiedBaseArmor<=0) {
				modifiedBaseArmor=0;
				setArmor(0);
			}else {
				modifiedBaseArmor-=6;
				setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction()*getHeatArmorReduction());
			}
				
		}
	}
	public void set_bypass_multipliers() {
		int l1 = bypass_multipliers.length;
		
		for(int i =0;i<l1;i++) {
			bypass_multipliers[i] = 2 - armor_multipliers[i];
		}

	}
	public double array_multiply_sum(double[] a1, double[] a2) {
		int l1 = a1.length;
		int l2 = a2.length;
		double result = 0;
		
		if(l1 == l2) {
			for(int i =0;i<l1;i++) {
				result += a1[i]*a2[i];
			}
		}
		else {
			System.out.printf("Array size not equal, %d %d\n",l1,l2);
		}
		return result;
		
	}
	public double[] array_multiply(double[] a1, double[] a2) {
		int l1 = a1.length;
		int l2 = a2.length;
		double[] res = new double[20];
		
		if(l1 == l2) {
			for(int i =0;i<l1;i++) {
				res[i] = a1[i] * a2[i];
			}
		}
		else {
			System.out.printf("Array size not equal, %d %d\n",l1,l2);
		}
		return res;
		
	}
	public double[] array_scale(double[] a1, double d) {
		int l1 = a1.length;
		double[] res = new double[20];
		
		for(int i =0;i<l1;i++) {
			res[i] = a1[i] * d;
		}

		return res;
		
	}
	public double array_sum(double[] a1) {
		int l1 = a1.length;
		double result = 0;
		for(int i = 0; i < l1; i++) {
			result += a1[i];
		}

		return result;
		
	}
	public double[] array_add(double[] a1, double[] a2) {
		int l1 = a1.length;
		int l2 = a2.length;
		double[] res = new double[20];
		
		if(l1 == l2) {
			for(int i =0;i<l1;i++) {
				res[i] = a1[i] + a2[i];
			}
		}
		else {
			System.out.printf("Array size not equal, %d %d\n",l1,l2);
		}
		return res;
		
	}
	public double[] array_add_const(double[] a1, double a2) {
		int l1 = a1.length;

		for(int i =0;i<l1;i++) {
			a1[i] += a2;
		}

		return a1;
	}
	public double[] check_positive(double[] a1) {
		int l1 = a1.length;

		for(int i =0;i<l1;i++) {
			if(a1[i]<0) {
				a1[i] = 0;	
			}
		}

		return a1;
	}
	public double get_status_duration() {

		return status_duration;
	}

	
}
