package orok.ttk;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONArray;
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
	String sub_class;
	String stance = "None";
	String move_set = "Neutral";

	/*
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
	*/
	
	//base stats
	public double base_crit_chance;
	public double base_crit_multiplier;
	public double base_fireRate = 1;
	public double base_pellet;
	public double base_status;
	public double base_reload;
	public int base_magazine;
	public int base_ammo;

	/*
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
	*/
	
	public double[] damage_array = new double[20];
	public double[] base_damage_array = new double[20];
	
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
	double beam_ramp_base = 1;
	double beam_ramp_multiplier = 1;
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
			
			//System.out.println(beam_ramp_multiplier);
			//beam ramp
			beam_ramp_multiplier = Math.min( beam_ramp_base + ((1-beam_ramp_base)/0.6)*index/fireRate , 1 );
			
			//reload
			if(index > size - 1) {
				current_time = fire_offset[index-1];
				index = 0;
				for(int i = 0; i<size; i++) {
					fire_offset[i] = current_time + reload + (1/fireRate)*i;

				}
				
				//set beam ramp multiplier
				//change beam ramp base from when reload ends
				if(reload > 0.8) {
					//decay over 2 s
					double decay_time = (reload - 0.8);
					beam_ramp_base = beam_ramp_multiplier - decay_time * (beam_ramp_multiplier - beam_ramp_base)/2;
				}
				beam_ramp_multiplier = beam_ramp_base;
				
			}
			
			
		}
		public double get_event_time() {
			return fire_offset[index];
		}
		public void reset() {
			index = 0;
			current_time = 0;
			size = (int)(magazine/ammoCost);
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
	        
			//setupCustomBuild();

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
	        
			//setupCustomBuild();
			//this.name = MainGUI.weaponListCombo.getText();
			
		}
		//MainGUI.populate_stance_combo(this);
		
		
		fo = new Firing_offsets();
		setupCustomBuild();
		
		//setup_base_array();
	}
	@SuppressWarnings("unchecked")
	public ArrayList<String> parseStanceList() throws FileNotFoundException, IOException, ParseException{
		ArrayList<String> stanceList = new ArrayList<String>();
          
        // typecasting obj to JSONObject 
		Object s_obj = new JSONParser().parse(new FileReader("stances.json"));
        JSONObject jo = (JSONObject) s_obj; 
          
        // getting address 
        Map<String,Object> stances = (Map<String,Object>)jo;
        
        for (Map.Entry<String, Object> entry : stances.entrySet()) {

            Map<String,Object> cur_stance = (Map<String,Object>)stances.get(entry.getKey());
            String cl = (String)cur_stance.get("Class");
            if(cl.equals(this.sub_class) ) {
            	stanceList.add((String)entry.getKey());
            }
        }

        Collections.sort(stanceList);
		
		return stanceList;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> parseWeaponList() throws FileNotFoundException, IOException, ParseException{
		ArrayList<String> weaponList = new ArrayList<String>();
		Object obj = new JSONParser().parse(new FileReader("weapons.json"));
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> weapons = (Map<String,Object>)jo;
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
        Map<String,Object> data = (Map<String,Object>)jo; 
        Iterator<Map.Entry<String,Object>> itr1 = data.entrySet().iterator(); 
        
        while (itr1.hasNext()) { 
            Map.Entry<String,Object> pair = itr1.next(); 
            buildList.add(pair.getKey());
        } 
        Collections.sort(buildList);
		
		return buildList;
	}

	@SuppressWarnings("unchecked")
	private void getStats(String name) throws FileNotFoundException, IOException, ParseException {
        // parsing file 
        Object obj = new JSONParser().parse(new FileReader("weapons.json")); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> weapons = (Map<String,Object>)jo;
        Map<String,Object> selectedWep;
        if(weapons.get(name) != null) {
            selectedWep =(Map<String,Object>)weapons.get(name);
        }else {
        	selectedWep =(Map<String,Object>)weapons.get(MainGUI.weaponListCombo.getItem(0));
        	MainGUI.weaponListCombo.select(0);
        }
        weapon_class = (String)selectedWep.get("productCategory");
        sub_class = (String)selectedWep.get("type");
        
        //first get stats of the primary fire
        base_pellet = ((Number)selectedWep.getOrDefault("multishot", 1.0)).doubleValue();
		if(base_pellet > 1 ) {
			multishot_scalar = base_pellet/2;
		}
		JSONArray jsonArray = (JSONArray)selectedWep.get("damagePerShot"); 

		damage_array = fillData(jsonArray);
		base_damage_array = damage_array;
		
		totBaseDMG =  array_sum(damage_array);
		
		base_crit_chance = ((Number)selectedWep.get("criticalChance")).doubleValue();
		base_crit_multiplier = ((Number)selectedWep.get("criticalMultiplier")).doubleValue();
		
		if(selectedWep.get("chargeTime") != null) {
			base_fireRate = 1/((Number)selectedWep.get("chargeTime")).doubleValue();
		}else
			base_fireRate = ((Number)selectedWep.get("fireRate")).doubleValue();
		
		ammoCost = ((Number)selectedWep.getOrDefault("ammoCost", 1 )).doubleValue();
		base_status = ((Number)selectedWep.getOrDefault("procChance", 0)).doubleValue();
		base_reload = ((Number)selectedWep.getOrDefault("reloadTime", 0 )).doubleValue();
		base_ammo = ((Number)selectedWep.getOrDefault("maxAmmo", 540 )).intValue();
		base_magazine = ((Number)selectedWep.getOrDefault("magazineSize", 100)).intValue();
		shot_type = (String)selectedWep.getOrDefault("trigger", "AUTO");
        
        //swap in stats of selected firemode
        if(!MainGUI.fireModeCombo.getText().contentEquals("Primary") && !MainGUI.fireModeCombo.getText().contentEquals("")) {
        	selectedWep = (Map<String, Object>) selectedWep.get("OtherFireModes");
        	selectedWep = (Map<String, Object>) selectedWep.get(MainGUI.fireModeCombo.getText());
        	
        	base_pellet = ((Number)selectedWep.getOrDefault("multishot", base_pellet)).doubleValue();
    		if(base_pellet > 1 ) {
    			multishot_scalar = base_pellet/2;
    		}
    		JSONArray jsonArray2 = (JSONArray)selectedWep.get("damagePerShot"); 

    		// Extract numbers from JSON array.
    		damage_array = fillData(jsonArray2);

    		totBaseDMG =  array_sum(damage_array);
    		
    		base_crit_chance = ((Number)selectedWep.getOrDefault("criticalChance", base_crit_chance)).doubleValue();
    		base_crit_multiplier = ((Number)selectedWep.getOrDefault("criticalMultiplier", base_crit_multiplier)).doubleValue();
    		
    		if(selectedWep.get("chargeTime") != null) {
    			base_fireRate = 1/((Number)selectedWep.get("chargeTime")).doubleValue();
    		}else
    			base_fireRate = ((Number)selectedWep.getOrDefault("fireRate", base_fireRate)).doubleValue();
    		
    		ammoCost = ((Number)selectedWep.getOrDefault("ammoCost", ammoCost)).doubleValue();
    		base_status = ((Number)selectedWep.getOrDefault("procChance", base_status)).doubleValue();
    		base_reload = ((Number)selectedWep.getOrDefault("reloadTime", base_reload)).doubleValue();
    		base_ammo = ((Number)selectedWep.getOrDefault("maxAmmo", base_ammo )).intValue();
    		base_magazine = ((Number)selectedWep.getOrDefault("magazineSize", base_magazine)).intValue();
    		shot_type = (String)selectedWep.getOrDefault("trigger", shot_type);
        }
        
        if(shot_type.equals("HELD")) {
        	Map<String,Object> bm = (Map<String, Object>) selectedWep.get("damageRamp");
        	beam_ramp_base = ((Number)bm.getOrDefault("min", 1)).doubleValue();
        	beam_ramp_multiplier = beam_ramp_base;
        }
        

		

	}
	@SuppressWarnings("unchecked")
	private void getBuildStats(String name) throws FileNotFoundException, IOException, ParseException {
		
        // parsing file 
        Object obj = new JSONParser().parse(new FileReader("weapons-custom.json")); 
          
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) obj; 
          
        // getting address 
        Map<String,Object> selectedWep =(Map<String,Object>)jo.get(name);
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
        move_set = (String)weap_conf.getOrDefault("MoveSet", "Neutral");
 
        MainGUI.set_up_fire_mode_combo();
        
        this.name = wep_name;
        
	}
	
	public void setupCustomBuild() {
		
		String s = MainGUI.damage_mod_text.getText();
		double damage_mods = MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		s = MainGUI.bane_mod_text.getText();
		double bane_dmg = MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		s = MainGUI.general_multiplier_mod_text.getText();
		double general_mult = MainGUI.parse_double_multiply_textbox(s);
		
		double combined_multipliers = bane_dmg * general_mult;
		//TODO
		//setup base damages
		damage_array = array_scale(base_damage_array, damage_mods * combined_multipliers);
        
        totBaseDMG = array_sum(damage_array);
        
        quanta = totBaseDMG / 16;
        
        //set up modded physical
        s = MainGUI.slash_mod_text.getText();
        damage_array[index("slash")] = damage_array[index("slash")] * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
        slashDOT = 0.35 * totBaseDMG * bane_dmg ;
		
        s = MainGUI.puncture_mod_text.getText();
        damage_array[index("puncture")] = damage_array[index("puncture")] * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
        
        s = MainGUI.impact_mod_text.getText(); 
        damage_array[index("impact")] = damage_array[index("impact")] * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
        
        //set up modded elemental
        s = MainGUI.cold_mod_text.getText();
        damage_array[index("cold")] += totBaseDMG*MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 0);
        
        s = MainGUI.electricity_mod_text.getText();
        damage_array[index("electricity")] += totBaseDMG * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 0);
        electricityDOT = totBaseDMG * ( MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1)/2.0 ) * bane_dmg;
        
        s = MainGUI.heat_mod_text.getText();
        damage_array[index("heat")] += totBaseDMG * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 0);
        heatDOT = totBaseDMG * ( MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1)/2.0 ) * bane_dmg;
        
        s = MainGUI.toxin_mod_text.getText();
        damage_array[index("toxin")] += totBaseDMG*MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 0);
        toxinDOT = totBaseDMG * ( MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1)/2.0 ) * bane_dmg;
                        
        gasDOT = 0.5 * totBaseDMG * bane_dmg;
        
        if(MainGUI.btnCorrosive.getSelection()) {
        	damage_array[index("corrosive")] += damage_array[index("electricity")] + damage_array[index("toxin")];
        	damage_array[index("electricity")] = 0;
        	damage_array[index("toxin")] = 0;
        }
        if(MainGUI.btnGas.getSelection()) {
        	damage_array[index("gas")] += damage_array[index("heat")] + damage_array[index("toxin")];
        	damage_array[index("heat")] =0;
        	damage_array[index("toxin")] = 0;
        }
        if(MainGUI.btnRadiation.getSelection()) {
        	damage_array[index("radiation")] += damage_array[index("electricity")] + damage_array[index("heat")];
        	damage_array[index("electricity")] = 0;
        	damage_array[index("heat")] = 0;
        }
        if(MainGUI.btnViral.getSelection()) {
        	damage_array[index("viral")] += damage_array[index("cold")] + damage_array[index("toxin")];
        	damage_array[index("toxin")] = 0;
        	damage_array[index("cold")] = 0;
        	
        }
        if(MainGUI.btnBlast.getSelection()) {
        	damage_array[index("blast")] += damage_array[index("heat")] + damage_array[index("cold")];
        	damage_array[index("heat")] = 0;
        	damage_array[index("cold")] = 0;
        }
        if(MainGUI.btnMagnetic.getSelection()) {
        	damage_array[index("magnetic")] += damage_array[index("electricity")] + damage_array[index("cold")];
        	damage_array[index("cold")] = 0;
        	damage_array[index("electricity")] =0;
        }
		
        totProportionalDMG = array_sum(damage_array);
        
        //aux stats
		s = MainGUI.crit_chance_mod_text.getText();
		critChance = base_crit_chance * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		s = MainGUI.crit_damage_mod_text.getText();
		critMultiplier = base_crit_multiplier *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		if(!MainGUI.stance_combo.getText().equals("None") && MainGUI.melee_time!=0) {
			s = MainGUI.fire_rate_mod_text.getText();
			fireRate = base_fireRate * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1)/MainGUI.melee_time;
			fire_rate_non_melee = base_fireRate *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
			//System.out.println(fireRate);
			
		}
		else {
			s = MainGUI.fire_rate_mod_text.getText();
			//Find more elegant way to handle melee fire rate
			fireRate = base_fireRate * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
			fire_rate_non_melee = base_fireRate *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);		
		}
		
		s = MainGUI.status_chance_mod_text.getText();
		status = base_status *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		s = MainGUI.multishot_mod_text.getText();
		if(shot_type.equals("HELD")) {
			beam_multishot = base_pellet * MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
			
			pellet = 1;
			status = beam_multishot * status;
		}
		else {
			pellet = base_pellet *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		}
		
		multishot_mods = MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		
		s = MainGUI.reload_mod_text.getText();
		reload = base_reload /MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);
		
		s = MainGUI.magazine_mod_text.getText();
		magazine = (int) (base_magazine *MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1));
		
		s = MainGUI.status_duration_mod_text.getText();
		statusDurationPercent =MainGUI.percent_to_double( MainGUI.parse_double_textbox(s), 1);	
		
		s = MainGUI.additive_crit_damage_text.getText();
		additive_crit_damage = MainGUI.parse_double_textbox(s);	
		
		//setup status
		//setStatus();
		slashChance = damage_array[index("slash")]/ totProportionalDMG;
		corrosiveChance = damage_array[index("corrosive")]/ totProportionalDMG;
		viralChance = damage_array[index("viral")]/ totProportionalDMG;
		toxinChance = damage_array[index("toxin")]/ totProportionalDMG;
		heatChance = damage_array[index("heat")]/ totProportionalDMG;
		gasChance = damage_array[index("gas")]/ totProportionalDMG;
		magneticChance = damage_array[index("magnetic")]/ totProportionalDMG;
		coldChance = damage_array[index("cold")]/ totProportionalDMG;
		electricityChance = damage_array[index("electricity")]/ totProportionalDMG;
		
		quantize();
		
		reload_ms = (int) (reload * 1000 + 1);
		critTier = (int)critChance + 1;
		highCC = critChance % 1;
		if (highCC == 0 && critChance != 0) // corrects modulo if cc is whole num, but realcritchance will stay 0 if cc is 0
			highCC = 1;
		
		msPerShot = (int) Math.round(1000 / fireRate);
		multiShotChance = pellet % 1;
		beam_multishot_chance = beam_multishot % 1;
		
	}

	public void quantize() {
		int l = damage_array.length;
		for(int i=0;i<l;i++) {
			damage_array[i] = Math.round(damage_array[i]/quanta)*quanta;
		}
		
	}
	
	public double getTotalDamage() {
		return array_sum(damage_array);
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
		multiplier = melee_multiplier * primed_chamber_multiplier * beam_multishot_multiplier * headshot_multiplier * beam_ramp_multiplier;
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

	public double array_sum(double[] a1) {
		int l1 = a1.length;
		double result = 0;
		for(int i = 0; i < l1; i++) {
			result += a1[i];
		}

		return result;
		
	}
	public double[] array_scale(double[] a1, double d) {
		int l1 = a1.length;
		double[] res = new double[20];
		
		for(int i =0;i<l1;i++) {
			res[i] = a1[i] * d;
		}

		return res;
		
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
	public static int index(String s) {
	    if (s.equals("impact"))
	        return 0;
	    else if (s.equals("puncture"))
	        return 1;
	    else if (s.equals("slash"))
	        return 2;
	    else if (s.equals("heat"))
	        return 3;
	    else if (s.equals("cold"))
	        return 4;
	    else if (s.equals("electricity"))
	        return 5;
	    else if (s.equals("toxin"))
	        return 6;
	    else if (s.equals("blast"))
	        return 7;
	    else if (s.equals("radiation"))
	        return 8;
	    else if (s.equals("gas"))
	        return 9;
	    else if (s.equals("magnetic"))
	        return 10;
	    else if (s.equals("viral"))
	        return 11;
	    else if (s.equals("corrosive"))
	        return 12;
	    else if (s.equals("void"))
	        return 13;
	    else if (s.equals("true"))
	        return 14;
	    System.out.printf("Invalid damage type name," );
	    return 0;
	}

}
