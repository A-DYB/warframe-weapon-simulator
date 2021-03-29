package orok.ttk;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weapon {
	
	public String name;
	public double critChance;
	public double critMultiplier;
	public double fireRate;
	public double pellet;
	public double status;
	public double reload;
	public int magazine;
	public double ammoCost;
	public int ammo;
	double additive_crit_damage;
	String weapon_class;
	String stance = "None";
	String move_set = "Still";

	public double impact;
	public double slash;
	public double puncture;
	public double cold;
	public double electricity;
	public double heat;
	public double toxin;
	public double toxicLashDamage;
	public double blast;
	public double corrosive;
	public double gas;
	public double magnetic;
	public double radiation;
	public double viral;
	public double void1;
	public double void2;
	
	//base stats
	public double base_crit_chance;
	public double base_crit_multiplier;
	public double base_fireRate = 1;
	public double base_pellet;
	public double base_status;
	public double base_reload;
	public int base_magazine;
	public int base_ammo;

	public double base_impact;
	public double base_slash;
	public double base_puncture;
	public double base_cold;
	public double base_electricity;
	public double base_heat;
	public double base_toxin;
	public double base_blast;
	public double base_corrosive;
	public double base_gas;
	public double base_magnetic;
	public double base_radiation;
	public double base_viral;
	public double base_void1;
	public double base_void2;
	
	public double[] damage_array = new double[15];
	
	//chances
	public double slashChance;
	public double corrosiveChance;
	public double viralChance;
	public double toxinChance;
	public double heatChance;
	public double gasChance;
	public double magneticChance;
	public double coldChance;
	public double electricityChance;

	//dots
	public double slashDOT;
	public double toxinDOT;
	public double heatDOT;
	public double gasDOT;
	public double electricityDOT;
	
	public double totBaseDMG;
	public double totProportionalDMG;
	
	private boolean hunter;
	private boolean primed_chamber;
	private double bane = 1;
	private double multiplier=1;
	public double melee_multiplier=1;
	public double primed_chamber_multiplier=1;
	public boolean toxicLash;
	public double toxicLashPercent;
	public double statusDurationPercent;
	
	int msPerShot;
	
	int reload_ms;
	int critTier;
	double highCC;
	double multiShotChance;
	double multishot_mods = 1;
	double multishot_scalar = 1;
	double beam_multishot = 1;
	double beam_multishot_chance;
	double beam_multishot_multiplier = 1;
	double headshot_multiplier = 1;
	boolean headshot = false;
	public double quanta = 1;
	double fire_rate_non_melee = 1;
	String shot_type;
	
	public Firing_offsets fo;
	
	public class Firing_offsets{
		int index;
		double fire_offset[];
		int size;
		double current_time;
		public Firing_offsets() {
			size = (int)(magazine/ammoCost);
			fire_offset = new double[size];
			
			for(int i = 0; i<size; i++) {
				fire_offset[i] = (1/fireRate)*i;

			}
		}
		public void increment() {
			index ++;
			if(index > magazine - 1) {
				current_time = fire_offset[index-1];
				index = 0;
				for(int i = 0; i<size; i++) {
					fire_offset[i] = current_time + reload + (1/fireRate)*i;

				}
				
			}
		}
		public double get_event_time() {
			return fire_offset[index];
		}
		public void reset() {
			index = 0;
			current_time = 0;
			size = magazine;
			fire_offset = new double[size];
			

			for(int i = 0; i<size; i++) {
				fire_offset[i] = (1/fireRate)*i;

			}
		}
		
	}

	public Weapon(String name) {
				
		//this.name = MainGUI.weaponListCombo.getText();
		
		if(name.contentEquals("Custom Build Tab")) {
			this.name = MainGUI.weaponListCombo.getText();			
				
			try {
				getStats(this.name);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			MainGUI.populate_stance_combo(this);
			int stance_index = MainGUI.stance_combo.indexOf( stance );
			int move_index = MainGUI.move_combo.indexOf( move_set );
	        MainGUI.stance_combo.select(stance_index);
	        MainGUI.move_combo.select(move_index);
	        
			MainGUI.setupCustomBuild(this.name, this);

		}else {
			//go look for name in build 
			this.name = name;
			try {
				getBuildStats(name);
				//name was updated in getBuildStats
				getStats(this.name);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
			MainGUI.populate_stance_combo(this);
			int stance_index = MainGUI.stance_combo.indexOf( stance );
			int move_index = MainGUI.move_combo.indexOf( move_set );
	        MainGUI.stance_combo.select(stance_index);
	        MainGUI.move_combo.select(move_index);
	        
			MainGUI.setupCustomBuild(this.name, this);
			//this.name = MainGUI.weaponListCombo.getText();
			
		}
		//MainGUI.populate_stance_combo(this);
		
		
		fo = new Firing_offsets();
		
		setup_base_array();
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> parseStanceList() throws FileNotFoundException, IOException, ParseException{
		ArrayList<String> stanceList = new ArrayList<String>();
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) MainGUI.obj; 
          
        // getting address 
        Map<String,Object> data = (Map<String,Object>)jo.get("data"); 
        Map<String,Object> stances = (Map<String,Object>)data.get("Stances");
        
        for (Map.Entry<String, Object> entry : stances.entrySet()) {

            Map<String,Object> cur_stance = (Map<String,Object>)stances.get(entry.getKey());
            String cl = (String)cur_stance.get("Class");
            if(cl.equals(this.weapon_class) ) {
            	stanceList.add((String)entry.getKey());
            }
        }
        /*
        Iterator<Map.Entry<String,Object>> itr1 = stances.entrySet().iterator(); 
        while (itr1.hasNext()) { 

            Map.Entry<String,Object> pair = itr1.next(); 
            Map<String,Object> elem = (Map<String,Object>)pair;
            
            if(elem.get("Class").equals(this.weapon_class) ) {
            	stanceList.add((String)pair.getKey());
            }
        } 
        */
        Collections.sort(stanceList);
		
		return stanceList;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> parseWeaponList() throws FileNotFoundException, IOException, ParseException{
		ArrayList<String> weaponList = new ArrayList<String>();
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) MainGUI.obj; 
          
        // getting address 
        Map<String,Object> data = (Map<String,Object>)jo.get("data"); 
        Map<String,Object> weapons = (Map<String,Object>)data.get("Weapons");
        Map<String,Object> selectedWep;
        Iterator<Map.Entry<String,Object>> itr1 = weapons.entrySet().iterator(); 
        while (itr1.hasNext()) { 

            Map.Entry<String,Object> pair = itr1.next(); 
            
            selectedWep = ((Map<String,Object>)weapons.get((String)pair.getKey()));
            
            //if(!((String)selectedWep.getOrDefault("Type","Custom")).equals("Melee"))	
            weaponList.add((String)pair.getKey());
        } 
        Collections.sort(weaponList);
		
		return weaponList;
	}
	@SuppressWarnings("unchecked")
	public static ArrayList<String> parseBuildList() throws FileNotFoundException, IOException, ParseException{
		ArrayList<String> buildList = new ArrayList<String>();
		
        Object obj =null;
		try {
			obj = new JSONParser().parse(new FileReader("weapons-custom.json"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> data = (Map<String,Object>)jo.get("data"); 
        Iterator<Map.Entry<String,Object>> itr1 = data.entrySet().iterator(); 
        
        while (itr1.hasNext()) { 
            Map.Entry<String,Object> pair = itr1.next(); 
            buildList.add(pair.getKey());
        } 
        Collections.sort(buildList);
		
		return buildList;
	}
	public void clearHeat() {
		heat = 0;
	}
	public void clearToxin() {
		toxin = 0;
	}
	public void clearCold() {
		cold = 0;
	}
	public void clearElectricity() {
		electricity = 0;
	}

	@SuppressWarnings("unchecked")
	private void getStats(String name) throws FileNotFoundException, IOException, ParseException {
		Map<String,Object> normalAttack;
        // parsing file 
        Object obj = new JSONParser().parse(new FileReader("weapons-wiki.json")); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> data = (Map<String,Object>)jo.get("data"); 
        Map<String,Object> weapons = (Map<String,Object>)data.get("Weapons");
        Map<String,Object> selectedWep;
        if(weapons.get(name) != null) {
            selectedWep =(Map<String,Object>)weapons.get(name);
        }else {
        	selectedWep =(Map<String,Object>)weapons.get(MainGUI.weaponListCombo.getItem(0));
        	MainGUI.weaponListCombo.select(0);
        }
        weapon_class = (String)selectedWep.get("Class");

        normalAttack =((Map<String,Object>)selectedWep.get("NormalAttack"));
        if(normalAttack == null) {
        	normalAttack =((Map<String,Object>)selectedWep.get("ChargeAttack"));
        }
        
		//Map<String,Object> attackMode = ((Map<String,Object>)selectedWep.getOrDefault(MainGUI.fireModeCombo.getText(),(Map<String,Object>)selectedWep.get("NormalAttack")));
        Map<String,Object> attackMode = (Map<String, Object>) (selectedWep.getOrDefault(MainGUI.fireModeCombo.getText(),normalAttack));
        
        Map<String,Object> dmg =((Map<String,Object>)attackMode.get("Damage"));  
        
		base_pellet = ((Number)attackMode.getOrDefault("PelletCount", 1.0)).doubleValue();
		if(base_pellet > 1 ) {
			multishot_scalar = base_pellet/2;
		}
        
		base_slash = ((Number)dmg.getOrDefault("Slash", 0.0)).doubleValue()/base_pellet;
		base_puncture =((Number)dmg.getOrDefault("Puncture", 0.0)).doubleValue()/base_pellet;
		base_impact =((Number)dmg.getOrDefault("Impact", 0.0)).doubleValue()/base_pellet;
		base_cold = ((Number)dmg.getOrDefault("Cold", 0.0)).doubleValue()/base_pellet;
		base_electricity = ((Number)dmg.getOrDefault("Electricity", 0.0)).doubleValue()/base_pellet;
		base_heat = ((Number)dmg.getOrDefault("Heat", 0.0)).doubleValue()/base_pellet;
		base_toxin = ((Number)dmg.getOrDefault("Toxin", 0.0)).doubleValue()/base_pellet;
		base_blast = ((Number)dmg.getOrDefault("Blast", 0.0)).doubleValue()/base_pellet;
		base_corrosive = ((Number)dmg.getOrDefault("Corrosive", 0.0)).doubleValue()/base_pellet;
		base_gas = ((Number)dmg.getOrDefault("Gas", 0.0)).doubleValue()/base_pellet;
		base_magnetic = ((Number)dmg.getOrDefault("Magnetic", 0.0)).doubleValue()/base_pellet;
		base_radiation = ((Number)dmg.getOrDefault("Radiation", 0.0)).doubleValue()/base_pellet;
		base_viral = ((Number)dmg.getOrDefault("Viral", 0.0)).doubleValue()/base_pellet;
		base_void1 = ((Number)dmg.getOrDefault("Void1", 0.0)).doubleValue()/base_pellet;
		base_void2 = ((Number)dmg.getOrDefault("Void2", 0.0)).doubleValue()/base_pellet;

		totBaseDMG =  impact + puncture + slash + cold+ electricity+ heat + toxin + blast + corrosive + gas + magnetic + radiation+ viral;
		
		base_crit_chance = ((Number)attackMode.getOrDefault("CritChance", ((Number)normalAttack.getOrDefault("CritChance", 0.0)).doubleValue() )).doubleValue();
		base_crit_multiplier = ((Number)attackMode.getOrDefault("CritMultiplier", ((Number)normalAttack.getOrDefault("CritMultiplier", 0.0)).doubleValue() )).doubleValue();
		if(attackMode.get("ChargeTime") != null) {
			base_fireRate = 1/((Number)attackMode.getOrDefault("ChargeTime", 1.0)).doubleValue();
		}else
			base_fireRate = ((Number)attackMode.getOrDefault("FireRate", ((Number)normalAttack.getOrDefault("FireRate", 1.0)).doubleValue() )).doubleValue();
		ammoCost = ((Number)attackMode.getOrDefault("AmmoCost", ((Number)normalAttack.getOrDefault("AmmoCost", 1.0)).doubleValue() )).doubleValue();

		
		double burstCount = ((Number)attackMode.getOrDefault("BurstCount", 1.0)).doubleValue();
		
		base_status = ((Number)attackMode.getOrDefault("StatusChance", ((Number)normalAttack.getOrDefault("StatusChance", 0.0)).doubleValue())).doubleValue();
		//status = 1-Math.pow(1 - status, 1/pellet);
		base_reload = ((Number)selectedWep.getOrDefault("Reload", ((Number)normalAttack.getOrDefault("Reload", 0.0)).doubleValue()) ).doubleValue();
		base_ammo = ((Number)selectedWep.getOrDefault("MaxAmmo", ((Number)normalAttack.getOrDefault("MaxAmmo", 100000.0)).intValue()) ).intValue();
		base_magazine = (int)((Number)selectedWep.getOrDefault("Magazine", ((Number)normalAttack.getOrDefault("Magazine", 100.0)).doubleValue())).doubleValue();
		shot_type = (String)normalAttack.getOrDefault("ShotType", "Hit-Scan");

	}
	@SuppressWarnings("unchecked")
	private void getBuildStats(String name) throws FileNotFoundException, IOException, ParseException {
		
        // parsing file 
        Object obj = new JSONParser().parse(new FileReader("weapons-custom.json")); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> data = (Map<String,Object>)jo.get("data"); 
        Map<String,Object> selectedWep =(Map<String,Object>)data.get(name);
        Map<String,Object> mods = (Map<String,Object>)selectedWep.get("Mods");
        Map<String,Object> elem_comb = (Map<String,Object>)selectedWep.get("ElementalCombo");
        Map<String,Object> weap_conf = (Map<String,Object>)selectedWep.get("WeaponConfig");
        
        MainGUI.damage_mod_text.setText( (String)mods.getOrDefault("Damage", "0.0") );
        MainGUI.bane_mod_text.setText( (String)mods.getOrDefault("Bane", "0.0") );
        MainGUI.crit_chance_mod_text.setText( (String)mods.getOrDefault("CriticalChance", "0.0") );
        MainGUI.crit_damage_mod_text.setText( (String)mods.getOrDefault("CriticalDamage", "0.0") );
        MainGUI.multishot_mod_text.setText( (String)mods.getOrDefault("Multishot", "0.0") );
        MainGUI.status_chance_mod_text.setText( (String)mods.getOrDefault("StatusChance", "0.0") );
        MainGUI.cold_mod_text.setText( (String)mods.getOrDefault("Cold", "0.0") );
        MainGUI.toxin_mod_text.setText( (String)mods.getOrDefault("Toxin", "0.0") );
        MainGUI.heat_mod_text.setText( (String)mods.getOrDefault("Heat", "0.0") );
        MainGUI.electricity_mod_text.setText( (String)mods.getOrDefault("Electricity", "0.0") );
        MainGUI.fire_rate_mod_text.setText( (String)mods.getOrDefault("FireRate", "0.0") );
        MainGUI.magazine_mod_text.setText( (String)mods.getOrDefault("Magazine", "0.0") );
        MainGUI.reload_mod_text.setText( (String)mods.getOrDefault("Reload", "0.0") );
        MainGUI.impact_mod_text.setText( (String)mods.getOrDefault("Impact", "0.0") );
        MainGUI.puncture_mod_text.setText( (String)mods.getOrDefault("Puncture", "0.0") );
        MainGUI.slash_mod_text.setText( (String)mods.getOrDefault("Slash", "0.0") );
        MainGUI.status_duration_mod_text.setText( (String)mods.getOrDefault("StatusDuration", "0.0") );
        MainGUI.general_multiplier_mod_text.setText( (String)mods.getOrDefault("GeneralMultiplier", "1.0") );
        MainGUI.btnHunterMunitions.setSelection( (Boolean)mods.getOrDefault("HunterMunitions", false) );
        MainGUI.btnShatteringImpact.setSelection( (Boolean)mods.getOrDefault("ShatteringImpact", false) );
        MainGUI.btnAmalgamShatImp.setSelection( (Boolean)mods.getOrDefault("AmalgamShatteringImpact", false) );
        MainGUI.btnPrimedchamber.setSelection( (Boolean)mods.getOrDefault("PrimedChamber", false) );
        
        MainGUI.btnCorrosive.setSelection( (Boolean)elem_comb.getOrDefault("Corrosive", false) );
        MainGUI.btnMagnetic.setSelection( (Boolean)elem_comb.getOrDefault("Magnetic", false) );
        MainGUI.btnRadiation.setSelection( (Boolean)elem_comb.getOrDefault("Radiation", false) );
        MainGUI.btnBlast.setSelection( (Boolean)elem_comb.getOrDefault("Blast", false) );
        MainGUI.btnViral.setSelection( (Boolean)elem_comb.getOrDefault("Viral", false) );
        MainGUI.btnGas.setSelection( (Boolean)elem_comb.getOrDefault("Gas", false) );
        
        String wep_name = (String)weap_conf.getOrDefault("WeaponName", "Custom Build Tab");
        int index = MainGUI.weaponListCombo.indexOf( wep_name );
        MainGUI.weaponListCombo.select(index);
        
        stance = (String)weap_conf.getOrDefault("Stance", "None");
        move_set = (String)weap_conf.getOrDefault("MoveSet", "Still");
        //index = MainGUI.fireModeCombo.indexOf( (String)weap_conf.getOrDefault("FireMode", "NormalAttack") );
        //MainGUI.fireModeCombo.select(index);
        
        
        
        MainGUI.set_up_fire_mode_combo();
        
        Weapon tableWeapon = new Weapon("Custom Build Tab");
        MainGUI.update_weapon_table(tableWeapon);
        
        this.name = wep_name;
        
	}

	public void quantize() {
		slash = Math.round(slash/quanta)*quanta;
		impact = Math.round(impact/quanta)*quanta;
		puncture = Math.round(puncture/quanta)*quanta;
		cold = Math.round(cold/quanta)*quanta;
		electricity = Math.round(electricity/quanta)*quanta;
		toxin = Math.round(toxin/quanta)*quanta;
		heat = Math.round(heat/quanta)*quanta;
		blast = Math.round(blast/quanta)*quanta;
		corrosive = Math.round(corrosive/quanta)*quanta;
		radiation = Math.round(radiation/quanta)*quanta;
		viral = Math.round(viral/quanta)*quanta;
		gas = Math.round(gas/quanta)*quanta;
		magnetic = Math.round(magnetic/quanta)*quanta;
		
	}
	public double getTotalDamage() {
		return impact+puncture+slash+cold+heat+toxin+electricity+corrosive+radiation+magnetic+gas+blast+viral+void1+void2;
	}
	public void setSimulationStats() {

	}

	public void setHunter(boolean hunter) {//
		this.hunter = hunter;
	}
	public boolean getHunter() {
		return hunter;
	}
	public void set_primed_chamber(boolean pc) {//
		this.primed_chamber = pc;
	}
	public boolean get_primed_chamber() {
		return primed_chamber;
	}
	
	public void setBane(double bane) {//
		this.bane = bane;
	}
	public double getBane() {
		return bane;
	}
	
	public void setMultiplier(double multiplier) {//
		this.multiplier = multiplier;
	}
	public double getMultiplier() {
		multiplier = melee_multiplier * primed_chamber_multiplier * beam_multishot_multiplier * headshot_multiplier;
		return multiplier;
	}
	
	public int getMsPerShot() {
		return msPerShot;
	}

	public void setMsPerShot(int msPerShot) {
		this.msPerShot = msPerShot;
	}

	public int getReload_ms() {
		return reload_ms;
	}

	public void setReload_ms(int reload_ms) {
		this.reload_ms = reload_ms;
	}

	public int getCritTier() {
		return critTier;
	}

	public void setCritTier(int critTier) {
		this.critTier = critTier;
	}

	public double getHighCC() {
		return highCC;
	}

	public void setHighCC(double highCC) {
		this.highCC = highCC;
	}

	public double getMultiShotChance() {
		return multiShotChance;
	}

	public void setMultiShotChance(double multiShotChance) {
		this.multiShotChance = multiShotChance;
	}
	public void setup_base_array() {
	    /*
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
	    13 
	    14 
	    15 
	    16
	    17
	    18
	    19
	    */
		
		damage_array[0] = impact;
		damage_array[1] = puncture;
		damage_array[2] = slash;
		damage_array[3] = heat;
		damage_array[4] = cold;
		damage_array[5] = electricity;
		damage_array[6] = toxin;
		damage_array[7] = blast;
		damage_array[8] =  radiation;
		damage_array[9] = gas;
		damage_array[10] = magnetic;
		damage_array[11] =  viral;
		damage_array[12] = corrosive;
		damage_array[13] =  void1;
		damage_array[14] =  0;
		
	}

}
