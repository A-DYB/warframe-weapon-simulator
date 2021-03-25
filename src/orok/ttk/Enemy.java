package orok.ttk;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import org.jfree.chart.plot.ValueMarker;

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

	// health multipliers
	public double impactH;
	public double slashH;
	public double punctureH;
	public double coldH;
	public double electricityH;
	public double heatH;
	public double toxinH;
	public double blastH;
	public double corrosiveH;
	public double gasH;
	public double magneticH;
	public double radiationH;
	public double viralH;
	public double voidH;
	public double trueH=1;

	// armor multipliers
	public double impactA;
	public double slashA;
	public double punctureA;
	public double coldA;
	public double electricityA;
	public double heatA;
	public double toxinA;
	public double blastA;
	public double corrosiveA;
	public double gasA;
	public double magneticA;
	public double radiationA;
	public double viralA;
	public double voidA;
	
	//armor bypass multipliers
	public double impactResist;
	public double slashResist;
	public double punctureResist;
	public double coldResist;
	public double electricityResist;
	public double heatResist;
	public double toxinResist;
	public double blastResist;
	public double corrosiveResist;
	public double gasResist;
	public double magneticResist;
	public double radiationResist;
	public double viralResist;
	public double voidResist;

	// shield multipliers
	public double impactS;
	public double slashS;
	public double punctureS;
	public double coldS;
	public double electricityS;
	public double heatS;
	public double toxinS;
	public double blastS;
	public double corrosiveS;
	public double gasS;
	public double magneticS;
	public double radiationS;
	public double viralS;
	public double voidS;
	public double trueS=1;

	private Dot heatDot = new Dot(0,0,0);
	private Dot coldDot = new Dot(0,0,0);
	private Dot armorHeatDot = new Dot(0,0,0);
	
	private double armor;
	private double shield;
	private double health;
	private boolean armorGone;
	public double totTrueDotDmg;
	public double totToxDotDmg;
	public double totHeatDotDmg;
	
	private double viralMult=1;
	private double magneticMult=1;
	
	public double health_scale =1;
	public double armor_scale =1;
	public double shield_scale =1;
	
	private boolean heatActive;
	private boolean heatDegrade;
	private int heatIndex;
	
	private boolean gasActive;
	private int gasOffset;
	
	private Random rProc = new Random();
	
	public double armorReduct;
	
	
	Deque<Dot> slQ = new LinkedList<>();
	Deque<Dot> toxQ = new LinkedList<>();
	Deque<Dot> heatQ = new LinkedList<>();
	Deque<Dot> elecQ = new LinkedList<>();	
	
	LinkedList<Dot> viralQ = new LinkedList<>();
	LinkedList<Dot> corrosiveQ = new LinkedList<>();
	LinkedList<Dot> magneticQ = new LinkedList<>();
	LinkedList<Dot> gasQ = new LinkedList<>();
	
	private Weapon current_weapon;

	public Enemy(String name, double level, double armorReduct, Weapon weapon) {
		this.name = name;
		this.level = level;
		this.armorReduct=armorReduct;
		this.current_weapon = weapon;
		
		getEnemy(name);
		reset(armorReduct);
	}
	
	public void applyDamage(Weapon weapon, double critMul) {
		
		double shield_damage = get_shield_damage(critMul,false);
		double health_damage = get_health_damage(critMul, true);
		
		double shield_dr = demolyst_dr(false, shield_damage * weapon.fire_rate_non_melee * weapon.multishot_mods *weapon.multishot_scalar / critMul);
		double health_dr = demolyst_dr(false, (health_damage + get_shield_damage(critMul,true)) * weapon.fire_rate_non_melee * weapon.multishot_scalar * weapon.multishot_mods / critMul);
		//System.out.println(shield_dr);
		
		health -= health_damage * health_dr ;
		shield -= shield_damage * shield_dr;
		
		//System.out.printf("Health Damage: (%f, %f, %f)",health_damage, health_damage * health_dr, health_dr );
		//System.out.printf("	Shield Damage: (%f, %f)	Crit Multiplier: %f\n",shield_damage, shield_damage * shield_dr, critMul);
		
		if(shield < 0) {
			double frac = Math.abs(shield/(shield_damage * shield_dr));
			health_damage = get_health_damage(critMul, false);
			double overflow_dr = demolyst_dr(false, frac * health_damage * 0.05 * weapon.fire_rate_non_melee * weapon.multishot_mods *weapon.multishot_scalar / critMul);
			System.out.printf("	frac: %f, health damage: %f, overflow dr: %f\n",frac,health_damage,overflow_dr);
			
			health -= frac * health_damage * 0.05 * overflow_dr; 
			shield = 0;
		}
			
	}

	private double get_shield_damage(double crit_multiplier, boolean include_toxin) {
		double tot = 0;
		if(shield >0 ) {	
			tot = 
					(current_weapon.impact * impactS+
					current_weapon.slash * slashS+
					current_weapon.puncture * punctureS+
					current_weapon.cold * coldS+
					current_weapon.electricity * electricityS+
					current_weapon.heat * heatS+
					current_weapon.toxin * toxinS+
					current_weapon.blast * blastS+
					current_weapon.corrosive * corrosiveS+
					current_weapon.gas * gasS+
					current_weapon.magnetic * magneticS+
					current_weapon.radiation * radiationS+
					current_weapon.viral * viralS)
					* magneticMult * current_weapon.getMultiplier();
			if(include_toxin) {
				//TODO
				//Demolyst
				//Note, the multiplier depends on what mods you have equipped. 60/60 will incur the 1.5 health multiplier, 90 the 1.0 toxin shield multiplier
				//unkonwn/inconsistent what happens when both equipped
				tot += current_weapon.toxin * 1.0;
			}
		}

		return tot * crit_multiplier;
	}
	private double get_health_damage(double crit_multiplier, boolean include_toxin) {
		double tot;
		if(shield > 0) {
			if(armor > 0) {
				tot = current_weapon.toxin * toxinA * toxinH * damageReduction(armor * toxinResist) * viralMult * current_weapon.getMultiplier();
			}
			else {
				tot = current_weapon.toxin * toxinH * viralMult * current_weapon.getMultiplier();
			}
		}
		else if(armor > 0) {
			tot = 	(
					current_weapon.impact * impactA * impactH * damageReduction(armor * impactResist) +
					current_weapon.slash * slashA * slashH * damageReduction(armor * slashResist) +
					current_weapon.puncture * punctureA * punctureH * damageReduction(armor * punctureResist) +
					current_weapon.cold * coldA * coldH * damageReduction(armor * coldResist) +
					current_weapon.electricity * electricityA * electricityH * damageReduction(armor * electricityResist) +
					current_weapon.heat * heatA * heatH * damageReduction(armor * heatResist) +
					current_weapon.toxin * toxinA * toxinH * damageReduction(armor * toxinResist) +
					current_weapon.blast * blastA * blastH * damageReduction(armor * blastResist) +
					current_weapon.corrosive * corrosiveA * corrosiveH * damageReduction(armor * corrosiveResist) +
					current_weapon.gas * gasA * gasH * damageReduction(armor * gasResist) +
					current_weapon.magnetic * magneticA * magneticH * damageReduction(armor * magneticResist) +
					current_weapon.radiation * radiationA * radiationH * damageReduction(armor * radiationResist) +
					current_weapon.viral * viralA * viralH * damageReduction(armor * viralResist)
					)* viralMult * current_weapon.getMultiplier();
			if(!include_toxin) {
				tot -= current_weapon.toxin * toxinA * toxinH * damageReduction(armor * toxinResist) * viralMult * current_weapon.getMultiplier();
			}

		}
		else {
			
			tot = 	(
					current_weapon.impact * impactH +
					current_weapon.slash * slashH +
					current_weapon.puncture * punctureH +
					current_weapon.cold *  coldH +
					current_weapon.electricity * electricityH +
					current_weapon.heat * heatH +
					current_weapon.toxin * toxinH +
					current_weapon.blast * blastH +
					current_weapon.corrosive * corrosiveH +
					current_weapon.gas * gasH +
					current_weapon.magnetic * magneticH +
					current_weapon.radiation * radiationH +
					current_weapon.viral * viralH
					)* viralMult * current_weapon.getMultiplier();
			if(!include_toxin) {
				tot -= current_weapon.toxin * toxinH * viralMult * current_weapon.getMultiplier();
			}
			
		}

		return tot * crit_multiplier;
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
	
	@SuppressWarnings("unchecked")
	public void getEnemy(String enemy) {

        // parsing file 
        Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("enemies.json"));
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		} 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> ene = (Map<String,Object>)jo.get("Enemy"); 
        Map<String,Object> data = (Map<String,Object>)ene.get(enemy); 
        
        
        Map<String,Object> resist = (Map<String,Object>)jo.get("Resistance"); 
        Map<String,Object> armorNameMap = (Map<String,Object>)resist.get("ArmorName"); //null
        Map<String,Object> healthNameMap = (Map<String,Object>)resist.get("HealthName"); 
        Map<String,Object> shieldNameMap = (Map<String,Object>)resist.get("ShieldName"); 
        
		armorType = (String)data.getOrDefault("ArmorType", "Ferrite");
		healthType = (String)data.getOrDefault("HealthType", "ClonedFlesh");
		shieldType = (String)data.getOrDefault("ShieldType", "Shield");
		baseArmor = ((Number)data.getOrDefault("BaseArmor", 0.0)).doubleValue();
		modifiedBaseArmor = baseArmor;
		baseHealth = ((Number)data.getOrDefault("BaseHealth", 1.0)).doubleValue();
		baseLevel = ((Number)data.getOrDefault("BaseLevel", 1.0)).doubleValue();
		baseShield = ((Number)data.getOrDefault("BaseShield", 0.0)).doubleValue();
		eidolon = (boolean)data.getOrDefault("Eidolon", false);
		
        Map<String,Object> armorResist = (Map<String,Object>)armorNameMap.get(armorType); //null
        Map<String,Object> healthResist = (Map<String,Object>)healthNameMap.get(healthType); 
        Map<String,Object> shieldResist = (Map<String,Object>)shieldNameMap.get(shieldType); 
        
		impactA = ((Number)armorResist.getOrDefault("Impact", 1.0)).doubleValue();
		slashA = ((Number)armorResist.getOrDefault("Slash", 1.0)).doubleValue();
		punctureA = ((Number)armorResist.getOrDefault("Puncture", 1.0)).doubleValue();
		coldA = ((Number)armorResist.getOrDefault("Cold", 1.0)).doubleValue();
		electricityA = ((Number)armorResist.getOrDefault("Electricity", 1.0)).doubleValue();
		heatA = ((Number)armorResist.getOrDefault("Heat", 1.0)).doubleValue();
		toxinA = ((Number)armorResist.getOrDefault("Toxin", 1.0)).doubleValue();
		blastA = ((Number)armorResist.getOrDefault("Blast", 1.0)).doubleValue();
		corrosiveA = ((Number)armorResist.getOrDefault("Corrosive", 1.0)).doubleValue();
		gasA = ((Number)armorResist.getOrDefault("Gas", 1.0)).doubleValue();
		magneticA = ((Number)armorResist.getOrDefault("Magnetic", 1.0)).doubleValue();
		radiationA = ((Number)armorResist.getOrDefault("Radiation", 1.0)).doubleValue();
		viralA = ((Number)armorResist.getOrDefault("magnetic", 1.0)).doubleValue();
		voidA = ((Number)armorResist.getOrDefault("Void", 1.0)).doubleValue();
		
		impactH = ((Number)healthResist.getOrDefault("Impact", 1.0)).doubleValue();
		slashH = ((Number)healthResist.getOrDefault("Slash", 1.0)).doubleValue();
		punctureH = ((Number)healthResist.getOrDefault("Puncture", 1.0)).doubleValue();
		coldH = ((Number)healthResist.getOrDefault("Cold", 1.0)).doubleValue();
		electricityH = ((Number)healthResist.getOrDefault("Electricity", 1.0)).doubleValue();
		heatH = ((Number)healthResist.getOrDefault("Heat", 1.0)).doubleValue();
		toxinH = ((Number)healthResist.getOrDefault("Toxin", 1.0)).doubleValue();
		blastH = ((Number)healthResist.getOrDefault("Blast", 1.0)).doubleValue();
		corrosiveH = ((Number)healthResist.getOrDefault("Corrosive", 1.0)).doubleValue();
		gasH = ((Number)healthResist.getOrDefault("Gas", 1.0)).doubleValue();
		magneticH = ((Number)healthResist.getOrDefault("Magnetic", 1.0)).doubleValue();
		radiationH = ((Number)healthResist.getOrDefault("Radiation", 1.0)).doubleValue();
		viralH = ((Number)healthResist.getOrDefault("Viral", 1.0)).doubleValue();
		voidH = ((Number)healthResist.getOrDefault("Void", 1.0)).doubleValue();

		impactS = ((Number)shieldResist.getOrDefault("Impact", 1.0)).doubleValue();
		slashS = ((Number)shieldResist.getOrDefault("Slash", 1.0)).doubleValue();
		punctureS = ((Number)shieldResist.getOrDefault("Puncture", 1.0)).doubleValue();
		coldS = ((Number)shieldResist.getOrDefault("Cold", 1.0)).doubleValue();
		electricityS = ((Number)shieldResist.getOrDefault("Electricity", 1.0)).doubleValue();
		heatS = ((Number)shieldResist.getOrDefault("Heat", 1.0)).doubleValue();
		toxinS = ((Number)shieldResist.getOrDefault("Toxin", 1.0)).doubleValue();
		blastS = ((Number)shieldResist.getOrDefault("Blast", 1.0)).doubleValue();
		corrosiveS = ((Number)shieldResist.getOrDefault("Corrosive", 1.0)).doubleValue();
		gasS = ((Number)shieldResist.getOrDefault("Gas", 1.0)).doubleValue();
		magneticS = ((Number)shieldResist.getOrDefault("Magnetic", 1.0)).doubleValue();
		radiationS = ((Number)shieldResist.getOrDefault("Radiation", 1.0)).doubleValue();
		viralS = ((Number)shieldResist.getOrDefault("Viral", 1.0)).doubleValue();
		voidS = ((Number)shieldResist.getOrDefault("Void", 1.0)).doubleValue();
		
		impactResist = (2 - impactA);
		slashResist = (2 - slashA);
		punctureResist = (2 - punctureA);
		coldResist = (2 - coldA);
		electricityResist = (2 - electricityA);
		heatResist = (2 - heatA);
		toxinResist = (2 - toxinA);
		blastResist = (2 - blastA);
		corrosiveResist = (2 - corrosiveA);
		gasResist = (2 - gasA);
		magneticResist = (2 - magneticA);
		radiationResist = (2 - radiationA);
		viralResist = (2 - viralA);
		voidResist = (2 - voidA);
		
	}
	
	public static ArrayList<String> getEnemyNames() {
		ArrayList<String> enemyList = new ArrayList<String>();
        
		Object obj = null;
		try {
			obj = new JSONParser().parse(new FileReader("enemies.json"));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> enem = (Map<String,Object>)jo.get("Enemy"); 
        Iterator<Map.Entry<String,Object>> itr1 = enem.entrySet().iterator(); 
        while (itr1.hasNext()) { 

            Map.Entry<String,Object> pair = itr1.next(); 
            
            enemyList.add((String)pair.getKey());
        } 
        Collections.sort(enemyList);
		
		return enemyList;
		
	}

	public void applyProc(Weapon weapon, double critM, int millis, double random, boolean force_slash) {
		//NOTE : VIRAL SHOULD ONLY BE APPLIED WHEN APPLYING DAMAGE
		if(force_slash) {
			Dot p = new Dot(millis+1000,6000,weapon.slashDOT*weapon.getMultiplier()*critM);
			slQ.addLast(p);
		}
		
		if(weapon.toxicLash) {
			Dot p = new Dot(millis+1000,6000,weapon.toxicLashPercent*weapon.getTotalDamage()*weapon.getMultiplier()*critM/2); // affected by bane 3x, affected by elemental mods too
			toxQ.addLast(p);
		}
		
		if(weapon.getHunter() && Math.random() < 0.3) {
			Dot p = new Dot(millis+1000,6000,weapon.slashDOT*weapon.getMultiplier()*critM);
			slQ.addLast(p);
		}
		
		//handle status above 100%
		for(int i = 0; i< Math.floor(weapon.status);i++) {
			procAssign(weapon,critM,millis);
			//System.out.printf("elec procs: %d, Shot: %d, millis: %d, shot: %f \n", elecQ.size(),i, millis, millis/44.0);
		}
		//handle last chance for status below 100%
		if( (random-=(weapon.status%1)) < 0 ) {
			procAssign(weapon,critM,millis);
			//System.out.printf("elec procs: %d, Shot: 2, millis: %d, \n", elecQ.size(), millis);
		}
		
	}
	
	public void procAssign(Weapon weapon, double critM, int millis) {
		//NOTE : VIRAL SHOULD ONLY BE APPLIED WHEN APPLYING DAMAGE
		double random = rProc.nextDouble();
		double dmg = 0;
		/*TODO*/
		if( (random -= weapon.slashChance) < 0) {
			Dot p = new Dot(millis+1000,6000,weapon.slashDOT*weapon.getMultiplier()*critM);
			slQ.addLast(p);
		}
		else if((random -= weapon.toxinChance) < 0){																				
			Dot p = new Dot(millis+1000,6000,weapon.toxinDOT*weapon.getMultiplier()*critM);
			toxQ.addLast(p);
		}
		else if((random -= weapon.gasChance) < 0) {																
			Dot p;
			if(gasActive) {
				p = new Dot(gasOffset,6000,weapon.gasDOT*weapon.getMultiplier()*critM );
			}else {
				gasActive = true;
				p = new Dot(millis+1000,6000,weapon.gasDOT*weapon.getMultiplier()*critM );
				gasOffset = millis+1000;
			}


			if(gasQ.size()>=10) {
				gasQ = removeOldestDot(gasQ);
			}
			gasQ.addLast(p);
		}
		else if((random -= weapon.corrosiveChance) < 0){	 
			Dot p = new Dot(millis+(int)(6000*weapon.statusDurationPercent),(int)(6000*weapon.statusDurationPercent),0);
			
			int size = corrosiveQ.size();
			
			if(size<=0) {
				
				armor = armor*(1-0.2-0.06);
				corrosiveQ.addLast(p);
			}
			else if(size<10) {
				p.setDamage(0.2+size*0.06);
				armor = armor*( (1-((size+1)*0.06+0.2)) /(1-(size*0.06+0.2)) );
				
				corrosiveQ.addLast(p);
			}
			else if(size >= 10) {
				p.setDamage(0.8);
				
				corrosiveQ=removeOldestDot(corrosiveQ);
				corrosiveQ.addLast(p);
			}
				

			if(armor <= 1 && !armorGone) {
				armorGone = true;
				armor = 0;
			}
			
		}	
		
		else if((random -= weapon.viralChance) < 0){		
			Dot p = new Dot(millis + (int)(6000*weapon.statusDurationPercent),(int)(6000*weapon.statusDurationPercent),0);
			int size = viralQ.size();
			
			if(size<=0) {
				p.setDamage(2);
				viralMult = 2;
				viralQ.addLast(p);
			}
			else if(size<10) {
				p.setDamage(2+size*0.25);
				viralMult = 2+size*0.25;
				viralQ.addLast(p);
			}
			else if(size >= 10) {
				p.setDamage(4.25);
				
				viralQ=removeOldestDot(viralQ);
				viralQ.addLast(p);
			}									
		}	
		
		else if((random -= weapon.heatChance) < 0 ){	//heat Proc
			if(!heatActive) {
				heatDot.setLife((int) (6000* weapon.statusDurationPercent));
				heatDot.setOffset(millis+500);
				heatDot.setDamage(weapon.heatDOT*weapon.getMultiplier()*critM);
				heatActive =true;
				
				//armorHeatDot.setLife(7000* weapon.statusDurationPercent);
				//armorHeatDot.setOffset(millis);
				heatDegrade = false;
				armorHeatDot.reset();
				
			}else {
				heatDot.setLife((int) (6000* weapon.statusDurationPercent));
				heatDot.setDamage(heatDot.getDamage()+weapon.heatDOT*weapon.getMultiplier()*critM);
			}
			
			
			/*
			Dot p = new Dot(millis,9,weapon.heatDOT*weapon.getMultiplier()*critM*vs);
			heatQ.addFirst(p);
			*/
		}
		else if((random -= weapon.magneticChance) < 0){	//mag Proc
			Dot p = new Dot(millis+(int)(6000*weapon.statusDurationPercent),(int)(6000*weapon.statusDurationPercent),0);
			int size = magneticQ.size();
			
			if(size<=0) {
				p.setDamage(2);
				magneticMult =2;
				magneticQ.addLast(p);
			}
			else if(size<10) {
				p.setDamage(2+size*0.25);
				magneticMult = 2+size*0.25;
				magneticQ.addLast(p);
			}
			else if(size >= 10) {
				p.setDamage(4.25);
				
				magneticQ=removeOldestDot(magneticQ);
				magneticQ.addLast(p);
			}
		}
		else if((random -= weapon.coldChance) < 0) {
			coldDot.setLife((int) (6000* weapon.statusDurationPercent));		//should be 8 seconds, but the cold will slow itself from 6 to 8 seconds
			coldDot.setOffset(millis+1000);
			if(coldDot.getDamage() == 0) {
				Dot.slow = 0.75;
				coldDot.setDamage(1);
			}
		}
		else if((random -= weapon.electricityChance) < 0 ) {
			Dot p;
			if(elecQ.isEmpty()) {
				Dot.electricity_offset = millis;
				p = new Dot(millis,6000,weapon.electricityDOT*weapon.getMultiplier()*critM);
			}
			else {
				p = new Dot(millis,7000,weapon.electricityDOT*weapon.getMultiplier()*critM);
			}
			//life has to stay 6000 because it is used in a way that does not literraly mean seconds, just damage instances

			//have to add to the front for electric b/c it damages instantly
			elecQ.addFirst(p);
					
		}
		
	}
	public LinkedList<Dot> removeOldestDot(LinkedList<Dot> d) {
		//bad... do something less... bad
		int oldest_index=0;
		double cur_oldest=d.peekFirst().getLife();
		
		for(int i=0;i<d.size();i++) {
			if(d.get(i).getLife() < cur_oldest) {
				cur_oldest =d.get(i).getLife();
				oldest_index = i;
			}
		}
		d.remove(oldest_index);
		return d;
	}
	
	public void scanProcs(Weapon weapon, int millis) {
		//System.out.printf("Checked procs at: %d\n", millis);
		Dot sd = slQ.peekFirst();
		Dot td = toxQ.peekFirst();
		Dot gd = gasQ.peekFirst();
		Dot ed = elecQ.peekFirst();
		Dot vd = viralQ.peekFirst();
		Dot cd = corrosiveQ.peekFirst();
		Dot md = magneticQ.peekFirst();
		
		double totGasDmg=0;
		
		double dr =1;
		//Dot hd = heatQ.peekFirst();

		//decay
		while(vd != null && vd.getOffset() == millis){

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
			//remove 
			viralQ.removeFirst();
			vd = viralQ.peekFirst();
		}
		while(md != null && md.getOffset() == millis){

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
			//remove 
			magneticQ.removeFirst();
			md = magneticQ.peekFirst();
		}
		while(cd != null && cd.getOffset() == millis){

			int size = corrosiveQ.size();
			
			if(size<=0) {
			}
			else if(size<=10 && size > 1) {
				armor= armor*(1-((size-1)*0.06+0.2))/(1-((size)*0.06+0.2));
			}
			else if(size == 1) {
				armor = armor/(1-0.2);
			}		
			//remove 
			corrosiveQ.removeFirst();
			cd = corrosiveQ.peekFirst();
		}
		
		if(coldDot.getLife() > 0 && coldDot.getOffset() == millis){
			coldDot.decLife(1000);
			coldDot.setOffset(millis+1000);

			//remove 
			if(coldDot.getLife() <= 0){
				Dot.slow = 1;												
				coldDot.setDamage(0);
			}
		}
		
		if(heatDot.getLife() > 0 && heatDot.getOffset() == millis){

			if(heatIndex ==0) {
				armor *=0.85;
				heatIndex++; //hi 1, 0.5s
			}
			else if(heatIndex == 1) {
				armor *=(0.7/0.85);
				heatIndex ++;//hi 2, 1s ,tick
			}
			else if(heatIndex == 2) {
				armor*=(0.6/0.7);
				heatIndex ++;//hi 3, 1.5s 
			}
			else if(heatIndex ==3) {
				armor*=(0.5/0.6);
				heatIndex ++;//hi 4, 2s, tick
			}
			
			if(heatIndex%2 ==0) {
				totHeatDotDmg += (health+shield);
				
				//////////////////
				
				if(shield>0) {
					dr=demolyst_dr(true, heatDot.getDamage()*heatS*magneticMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					shield = shield - heatDot.getDamage()*heatS*magneticMult*dr;
					if(shield < 0) {
						if(armor>0) {
							double armor_dr = damageReduction(armor*heatResist);
							health += shield*0.05*viralMult*heatH*heatA*armor_dr/(magneticMult*heatS);
						}else {
							health += shield*0.05*viralMult*heatH/(magneticMult*heatS);
						}
						shield =0;
					}
				}
				else if(shield<=0 && armor>0) {
					double armor_dr = damageReduction(armor*heatResist);
					dr=demolyst_dr(true, heatDot.getDamage()*heatA*heatH*viralMult*armor_dr*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					health -= heatDot.getDamage()*dr*armor_dr*heatH*heatA*viralMult;
				}
				else {
					dr=demolyst_dr(true, heatDot.getDamage()*heatH*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					health -= heatDot.getDamage()*dr*heatH*viralMult;
				}
				
				////////////////////////
				/*
				if(armor>0){
					dr = demolyst_dr(true, damageReduction(armor*heatResist)*heatDot.getDamage()*heatA*heatH*viralMult*weapon.fire_rate_non_melee*weapon.pellet);
					health = health - damageReduction(armor*heatResist)*heatDot.getDamage()*heatA*heatH*viralMult*dr;								
				}else{
					dr = demolyst_dr(true, heatDot.getDamage()*heatH*viralMult*weapon.fire_rate_non_melee*weapon.pellet);
					health = health - heatDot.getDamage()*heatH*viralMult*dr;												
				}
				*/
				if(health<0)
					health =0;
				if(shield<0)
					shield=0;
				
				totHeatDotDmg -=(health+shield);
			}
			
			if(heatIndex ==4) {
				heatDot.setOffset(millis+1000);
				heatDot.decLife(1000);
			}else{
				heatDot.setOffset(millis+500);
				heatDot.decLife(500);
			}
			
			// AMALGAM SHATTERING IMPACT ARMOR REMOVAL
			if(amalgam_shattering_impact) {
				if(shattering_impact) {
					if(modifiedBaseArmor<=0) {
						modifiedBaseArmor=0;
						setArmor(0);
					}else {
						modifiedBaseArmor-=6;
						setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction());
					}
					
				}
			}
		}
		else if(heatDot.getLife()<=0 && heatActive) {
			//heatDot.decLife(500);
			//armor *=2;
			heatDot.setDamage(0);
			//heatIndex = 0;
			heatActive =false;
			heatDegrade =true;
			armorHeatDot.setLife((int) (6000* weapon.statusDurationPercent));
			armorHeatDot.setOffset((int) (millis+1500* weapon.statusDurationPercent));
			
		}
		if(heatDegrade =true && armorHeatDot.getOffset()==millis && armorHeatDot.getLife() > 0) {
			
			if(heatIndex == 1) {
				armor /=0.85;
				heatIndex--;
				heatDegrade =false;
				armorHeatDot.reset();
			}
			else if(heatIndex == 2) {
				armor *=(0.85/0.7);
			}
			else if(heatIndex ==3) {
				armor*=(0.7/0.6);
			}
			else if(heatIndex ==4) {
				armor*=(0.6/0.5);
			}
			armorHeatDot.setOffset((int) (millis+1500* weapon.statusDurationPercent));
			armorHeatDot.decLife((int) (1500* weapon.statusDurationPercent));
			heatIndex--;
		}
		
		while(td != null && td.getOffset() == millis) {
			totToxDotDmg += health; 	//lazy way of adding dot damage without new variable
			if(armor>0){
				dr=demolyst_dr(true, (damageReduction(armor*toxinResist)*(td.getDamage()*toxinA*toxinH))*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				health = health - (damageReduction(armor*toxinResist)*(td.getDamage()*toxinA*toxinH))*viralMult*dr;						
			}else{
				dr=demolyst_dr(true, (td.getDamage()*toxinH)*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				health = health - (td.getDamage()*toxinH)*viralMult*dr;										
			}
			td.decLife(1000);
			
			if(health<0)
				health =0;
			
			totToxDotDmg -= health;		//lazy way of adding dot damage without new variable
			
			td.setOffset(millis+1000);
			//removal scan
			if(td.getLife() <= 0){
				toxQ.remove();
			}else {
				toxQ.removeFirst();
				toxQ.addLast(td);
			}
			td = toxQ.peekFirst();
			
			// REMOVE ARMOR
			
			if(amalgam_shattering_impact) {
				if(modifiedBaseArmor<=0) {
					modifiedBaseArmor=0;
					setArmor(0);
				}else {
					modifiedBaseArmor-=6;
					setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction());
				}
					
			}
			
		}
		
		while(sd != null && sd.getOffset() == millis) {
			totTrueDotDmg += (health+shield);
			if(shield <=0) {
				dr=demolyst_dr(true, sd.getDamage()*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				health = health - sd.getDamage()*trueH*viralMult*dr;
			}
				
			else {
				dr=demolyst_dr(true, sd.getDamage()*magneticMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				System.out.println(sd.getDamage()*trueS*magneticMult*dr);
				shield = shield - sd.getDamage()*trueS*magneticMult*dr;
				if(shield < 0) {
					health += shield*trueH*viralMult*0.05/magneticMult;
					shield =0;
				}
			}

			sd.decLife(1000);
			sd.setOffset(millis+1000);
			
			if(health<0)
				health =0;
			if(shield<0)
				shield=0;
			
			totTrueDotDmg -= (health+shield);
			//death scan
			if(sd.getLife() <= 0){
				slQ.removeFirst();
			}else {
				slQ.addLast(slQ.removeFirst());
			}
			sd = slQ.peek();
			
			// REMOVE ARMOR
			
			if(amalgam_shattering_impact) {
				if(modifiedBaseArmor<=0) {
					modifiedBaseArmor=0;
					setArmor(0);
				}else {
					modifiedBaseArmor-=6;
					setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction());
				}
			}
			
		}
		

		while(ed != null && ed.getOffset() == millis) {
			//System.out.printf("#millis: %d, elec offset: %d\n", millis,Dot.electricity_offset);

			// Must be <= 
			if(Dot.electricity_offset <= millis) {
				double total_elec_dot = 0;
				double print_stat = health;
				for (Dot item: elecQ) {
					total_elec_dot += item.getDamage();
		        }
				if(shield>0) {
					dr=demolyst_dr(true, total_elec_dot*electricityS*magneticMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					shield = shield - total_elec_dot*electricityS*magneticMult*dr;
					if(shield < 0) {
						if(armor>0) {
							double armor_dr = damageReduction(armor*electricityResist);
							health += shield*0.05*viralMult*electricityH*electricityA*armor_dr/(magneticMult*electricityS);
						}else {
							health += shield*0.05*viralMult*electricityH/(magneticMult*electricityS);
						}
					}
				}
				else if(shield<=0 && armor>0) {
					double armor_dr = damageReduction(armor*electricityResist);
					dr=demolyst_dr(true, total_elec_dot*electricityA*electricityH*viralMult*armor_dr*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					health -= total_elec_dot*electricityA*electricityH*dr*armor_dr*viralMult;
				}
				else {
					dr=demolyst_dr(true, total_elec_dot*electricityH*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
					health -= total_elec_dot*electricityH*dr*viralMult;
				}
				
				Dot.electricity_offset = millis+1000;
				print_stat -= health;
				System.out.printf("# of elec procs: %d, Damage: %f, Time: %d\n", elecQ.size(),print_stat, millis);
			}

			ed.decLife(1000);
			ed.setOffset(millis+1000);
			
			//death scan
			if(ed.getLife() <= 0){
				elecQ.removeFirst();
				
			}else {
				elecQ.addLast(elecQ.removeFirst());
			}
			ed = elecQ.peek();
			
			if(elecQ.isEmpty()) {
				//TODO
				//Is this ok?
				Dot.electricity_offset = -1;
			}
			
			// REMOVE ARMOR
			if(amalgam_shattering_impact) {
				if(modifiedBaseArmor<=0) {
					modifiedBaseArmor=0;
					setArmor(0);
				}else {
					modifiedBaseArmor-=6;
					setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction());
				}
			}
			
		}

		
		if(gd != null && gd.getOffset() == millis && gasQ.size()>0) {
			
			for(int i =0;i<gasQ.size();i++) {
				gd=gasQ.peekFirst();
				gd.setOffset(millis+1000);		
				totGasDmg+=gd.getDamage();
				gd.decLife(1000);
				
				if(gd.getLife()<=0) {
					gasQ.removeFirst();
					if(gasQ.size()<=0) {
						gasActive=false;
					}
				}else {
					gasQ.removeFirst();
					gasQ.addLast(gd);
				}
				
				
			}
			
			
			if(shield>0) {
				dr=demolyst_dr(true, totGasDmg*gasS*magneticMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				shield = shield - totGasDmg*gasS*magneticMult*dr;
				if(shield < 0) {
					if(armor>0) {
						double armor_dr = damageReduction(armor*gasResist);
						health += shield*0.05*viralMult*gasH*gasA*armor_dr/(magneticMult*gasS);
					}else {
						health += shield*0.05*viralMult*gasH/(magneticMult*gasS);
					}
					shield = 0;
				}
			}
			else if(shield<=0 && armor>0) {
				double armor_dr = damageReduction(armor*gasResist);
				dr=demolyst_dr(true, totGasDmg*gasA*gasH*viralMult*armor_dr*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				health -= totGasDmg*dr*armor_dr*gasH*gasA*viralMult;
			}
			else {
				dr=demolyst_dr(true, totGasDmg*gasH*viralMult*weapon.fire_rate_non_melee* weapon.multishot_mods *weapon.multishot_scalar);
				health -= totGasDmg*dr*gasH*viralMult;
			}
			
			// REMOVE ARMOR
			
			if(amalgam_shattering_impact) {
				if(modifiedBaseArmor<=0) {
					modifiedBaseArmor=0;
					setArmor(0);
				}else {
					modifiedBaseArmor-=6;
					setArmor(armorScale(modifiedBaseArmor, level, baseLevel)*armorReduct*getCorrosiveReduction());
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
		
		//heatQ.clear();
		heatDot.setDamage(0);
		heatActive =false;
		heatIndex =0;
		heatDegrade =false;
		
		health  = heathScale(baseHealth,level,baseLevel);
		armor  = armorScale(baseArmor,level,baseLevel) * armorReduct;
		shield = shieldScale(baseShield,level,baseLevel);
		
		//shattering_impact=false;
		
		if(armor >1) {
			armorGone = false;
		}

		totToxDotDmg =0;
		totTrueDotDmg =0;
		totHeatDotDmg=0;
		
		heatDot.reset();
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
	
	public void resist(double resistance) {
		
		impactH = subtract_resistance(impactH ,resistance);
		slashH = subtract_resistance(slashH ,resistance);
		punctureH = subtract_resistance(punctureH ,resistance);
		coldH = subtract_resistance(coldH ,resistance);
		electricityH = subtract_resistance(electricityH ,resistance);
		heatH = subtract_resistance(heatH ,resistance);
		toxinH = subtract_resistance(toxinH ,resistance);
		blastH = subtract_resistance(blastH ,resistance);
		corrosiveH = subtract_resistance(corrosiveH ,resistance);
		gasH = subtract_resistance(gasH ,resistance);
		magneticH = subtract_resistance(magneticH ,resistance);
		radiationH = subtract_resistance(radiationH ,resistance);
		viralH = subtract_resistance(viralH ,resistance);
		voidH = subtract_resistance(voidH ,resistance);
		trueH = subtract_resistance(trueH ,resistance);

		impactS = subtract_resistance(impactS ,resistance);
		slashS = subtract_resistance(slashS ,resistance);
		punctureS = subtract_resistance(punctureS ,resistance);
		coldS = subtract_resistance(coldS ,resistance);
		electricityS = subtract_resistance(electricityS ,resistance);
		heatS = subtract_resistance(heatS ,resistance);
		toxinS = subtract_resistance(toxinS ,resistance);
		blastS = subtract_resistance(blastS ,resistance);
		corrosiveS = subtract_resistance(corrosiveS ,resistance);
		gasS = subtract_resistance(gasS ,resistance);
		magneticS = subtract_resistance(magneticS ,resistance);
		radiationS = subtract_resistance(radiationS ,resistance);
		viralS = subtract_resistance(viralS ,resistance);
		voidS = subtract_resistance(voidS ,resistance);
		trueS = subtract_resistance(trueS ,resistance);
	}
	
	private double subtract_resistance(double modifier, double resist_val) {
		modifier-=resist_val;
		if(modifier < 0)
			modifier=0;
		
		return modifier;
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
	public void addHeatH(double m) {
		this.heatH = Math.min(2, m+this.heatH);
	}
	
}
