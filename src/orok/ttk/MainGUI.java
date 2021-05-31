package orok.ttk;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.swing.ToolTipManager;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.experimental.chart.swt.ChartComposite;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import orok.ttk.Enemy.Dot;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;

public class MainGUI {

	protected static Shell shell;
	private final static FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtName;
	private Text txtEnemyname;
	
	private Label time_label;
	private Label shots_label;
	private Label v_time_label;
	private Label v_shots_label;
	
	private static NumberFormat format = NumberFormat.getInstance(Locale.getDefault());

	private XYSeries health_series;
	private XYSeries armor_series;
	private XYSeries shield_series;
	private XYSeries scaleSeries;
	private JFreeChart chart;
	private Button btnHeadshots;
	private Button btnGraph;
	private Button btnSimulate;
	private Button btnScale;
	private Button btnRemoveBuild;
	public static Button btnShatteringImpact;
	public static Button btnPrimedchamber;
	public static Button btnCorrosive;
	public static Button btnRadiation;
	public static Button btnGas;
	public static Button btnViral;
	public static Button btnBlast;
	public static Button btnMagnetic;
	public static Button btnAmalgamShatImp;
	private Spinner lvl_spinner;
	public static Combo weaponListCombo;
	public static Combo fireModeCombo;
	public static Combo stance_combo;
	public static Combo move_combo;
	public static Combo secondary_effects_combo;
	
	public Combo enemy_combo;
	
	private boolean graph = false;
	private boolean combo_is_populated = false;
	private boolean ui_set_up = false;
	private static boolean gui_setup = false;

	private double armorReduct = 1;
	private double vigilante;

	private int numSeries = 0;
	
	public static Object wep_obj = null;
	private Spinner bHealthSpinner;
	private Spinner bShieldSpinner;
	private Spinner bLevelSpinner;
	private Spinner ccESpinner;
	private Spinner cdESpinner;
	private Spinner scESpinner;
	private Spinner frESpinner;
	private Spinner pelletESpinner;
	private Spinner reloadESpinner;
	private Spinner magESpinner;
	private Spinner iESpinner;
	private Spinner sESpinner;
	private Spinner pESpinner;
	private Spinner cESpinner;
	private Spinner eESpinner;
	private Spinner hESpinner;
	private Spinner tESpinner;
	private Spinner bESpinner;
	private Spinner corESpinner;
	private Spinner gESpinner;
	private Spinner mESpinner;
	private Spinner rESpinner;
	private Spinner viralESpinner;
	private Spinner voidSpinner;
	private Combo weaponCombo;
	private Text txtBuildName;
	private Label lblAbilitiesseparator;
	private Label lblStatusDur;
	private static Table weapon_table;
	private Spinner armor_strip_spinner;

	private static TableItem tableItemName ;
	private static TableItem tableItem ;
	private static TableItem tableItem1 ;
	private static TableItem tableItem2 ;
	private static TableItem tableItem3 ;
	private static TableItem tableItem4 ;
	private static TableItem tableItem5 ;
	private static TableItem tableItem6 ;
	private static TableItem tableItem7 ;
	private static TableItem tableItem8 ;
	private static TableItem tableItem9 ;
	private static TableItem tableItem10 ;
	private static TableItem tableItem11 ;
	private static TableItem tableItem12 ;
	private static TableItem tableItem13 ;
	private static TableItem tableItem14 ;
	private static TableItem tableItem15 ;
	private static TableItem tableItem16 ;
	private static TableItem tableItem17 ;
	private static TableItem tableItem18 ;
	private static TableItem tableItem19 ;
	private static TableItem tableItem20 ;
	private static TableItem tableItem21 ;
	private static TableItem tableItem22 ;
	private static TableItem tableItem23 ;
	private static TableItem tableItem24 ;
	private static TableItem tableItem25 ;
	private static TableItem tableItem26 ;
	private static TableItem tableItem27 ;
	private static TableItem tableItem28 ;
	private static TableItem tableItem29 ;
	private static TableItem tableItem30 ;
	private static TableItem tableItem31 ;
	private static TableItem tableItem32 ;
	private static TableItem tableItem33 ;
	private static TableItem tableItem34 ;
	private static TableItem tableItem35 ;
	private static TableItem tableItem36 ;
	
	private static TableItem tableItem_1;
	private static TableItem tableItem_2;
	private static TableItem tableItem_3;
	
	private static TableColumn tblclmnStat;
	private static TableColumn tblclmnType;
	
	
	private Random rMulti = new Random();
	private Random rCrit = new Random();
	private Random rVig = new Random();
	private Random rStatus = new Random();
	
	public ArrayList<Double> stance_multipliers = new ArrayList<Double>();
	public ArrayList<Integer> stance_procs = new ArrayList<Integer>();
	static double melee_time=1;
	private Button btnSteelPathModifiers;
	private Text txtSeriesName;
	private Spinner health_scale_spinner;
	private Spinner armor_scale_spinner;
	private Spinner shield_scale_spinner;
	private Label lblHpScale;
	private Label lblArmorScale;
	private Label lblShieldScale;
	private Button btnFullViral;
	private Button btnFullMagnetic;
	private Table enemy_table;
	private TableItem tableItem_4;
	private Spinner resist_spinner;
	private Spinner start_level_spinner;
	private Spinner end_level_spinner;
	public static Text damage_mod_text;
	public static Text crit_chance_mod_text;
	public static Text crit_damage_mod_text;
	public static Text multishot_mod_text;
	public static Text status_chance_mod_text;
	public static Text cold_mod_text;
	public static Text toxin_mod_text;
	public static Text heat_mod_text;
	public static Text electricity_mod_text;
	public static Text fire_rate_mod_text;
	public static Text magazine_mod_text;
	public static Text reload_mod_text;
	public static Text impact_mod_text;
	public static Text puncture_mod_text;
	public static Text slash_mod_text;
	public static Text status_duration_mod_text;
	public static Button btnHunterMunitions;
	private Label lblBane;
	public static Text bane_mod_text;
	public static Text general_multiplier_mod_text;
	private Label lblMiscMultipliers;
	
	private Weapon default_weapon;
	private Enemy default_enemy;
	static Text additive_crit_damage_text;
	private StyledText styledText;
	static Text multiplicative_firerate_mod_text;
	private Label lblMultiplicativeFireRate;
	private Button btnCheckButton0;
	private Button btnCheckButton1;
	private Button btnCheckButton2;
	private Button btnCheckButton3;
	private Button btnCheckButton4;
	private Button btnCheckButton5;
	private Button btnCheckButton6;
	private Button btnCheckButton7;
	private Button btnCheckButton8;
	private Button btnCheckButton9;
	private Button btnCheckButton10;
	private Button btnCheckButton11;
	private Button btnCheckButton12;
	private Button btnCheckButton13;
	private Button btnCheckButton14;
	private Button btnCheckButton15;
	private Button btnCheckButton16;
	static Button debug_checkbutton;
	public static org.eclipse.swt.widgets.List damage_list;
	private Composite ch_composite_2;
	private Composite ch_composite_3;
	
	final int STOP_COND = 20;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainGUI window = new MainGUI();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		shell.setSize(1920, 1080);
		shell.setText("Pocket Simulacrum v0.4");
		shell.setLayout(null);
		shell.setLocation(0, 0);
		shell.setMaximized(true);
		
		//tooltip dont expire
		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
		
		try {
			wep_obj = new JSONParser().parse(new FileReader("weapons.json"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
		
		CTabFolder tabFolder = new CTabFolder(shell, SWT.BORDER);
		tabFolder.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_DARK_SHADOW));
		tabFolder.setBounds(23, 35, 545, 916);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmSimulation = new CTabItem(tabFolder, SWT.NONE);
		tbtmSimulation.setText("Simulation");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmSimulation.setControl(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(8, false));
		
		Composite ch_composite_1 = new Composite(shell, SWT.NONE);
		ch_composite_1.setBounds(920, 35, 974, 438);
		formToolkit.adapt(ch_composite_1);
		formToolkit.paintBordersFor(ch_composite_1);
		
		ch_composite_3 = new Composite(shell, SWT.NONE);
		ch_composite_3.setBounds(920, 479, 974, 472);
		formToolkit.adapt(ch_composite_3);
		formToolkit.paintBordersFor(ch_composite_3);
		
		
		Label lblEnemy = formToolkit.createLabel(composite, "Enemy", SWT.NONE);
		lblEnemy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		enemy_combo = new Combo(composite, SWT.NONE);
		enemy_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_is_populated) {
					String name = enemy_combo.getText();
					//default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,string_to_double(lvl_spinner.getText()), ( 100 - spinner_to_double(armor_strip_spinner) )/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		GridData gd_enemy_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1);
		gd_enemy_combo.widthHint = 236;
		enemy_combo.setLayoutData(gd_enemy_combo);
		formToolkit.adapt(enemy_combo);
		formToolkit.paintBordersFor(enemy_combo);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblWeapon = new Label(composite, SWT.NONE);
		lblWeapon.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblWeapon, true, true);
		lblWeapon.setText("Weapon");
		
		weaponCombo = new Combo(composite, SWT.NONE);
		weaponCombo.add("Custom Build Tab");
		weaponCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				
				move_combo.removeAll();
				stance_procs.clear();
				stance_procs.add(0);
				stance_multipliers.clear();
				stance_multipliers.add(1.0);
				
				String name = weaponCombo.getText();
				if(!name.equals("Custom Build Tab")) {
					btnRemoveBuild.setEnabled(true);
					txtSeriesName.setText(name);
				}else {
					btnRemoveBuild.setEnabled(false);
					txtSeriesName.setText(weaponListCombo.getText());
					//name = weaponListCombo.getText();
				}
				//parse_stance(stance_combo.getText());
				default_weapon = new Weapon(name, true);

				//update_weapon_table(default_weapon);
				String stance_name = stance_combo.getText();
				
				if(stance_name != null && !stance_name.equals("None")) {
					//System.out.println(stance_name);
					setup_move_combo(stance_combo.getText(), default_weapon);
					parse_stance(stance_name);
				}
				
				default_weapon.setupCustomBuild();
				
				//tableItem8.setText(new String[] {"s/ attack", Double.toString(melee_time)});
				update_weapon_table(default_weapon);
				
				
			}
		});
		
		weaponCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		formToolkit.adapt(weaponCombo);
		formToolkit.paintBordersFor(weaponCombo);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnRemoveBuild = new Button(composite, SWT.NONE);
		btnRemoveBuild.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnRemoveBuild.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
		        // parsing file 
		        Object obj = null;
				try {
					obj = new JSONParser().parse(new FileReader("weapons-custom.json"));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				} 
		          
		        // typecasting obj to JSONObject 
		        JSONObject jo = (JSONObject) obj;
		        
		        //Check if name already exists
		        @SuppressWarnings("unchecked")
				Map<String,Object> data = (Map<String,Object>)jo; 
		        if(data.get(weaponCombo.getText()) != null) {
		        	data.remove(weaponCombo.getText());
		        	weaponCombo.remove(weaponCombo.getSelectionIndex());
		        	btnRemoveBuild.setEnabled(false);
		        	weaponCombo.select(0);
		        }
		        
		        try (FileWriter file = new FileWriter("weapons-custom.json")) { 
		            file.write(jo.toJSONString());
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		        
			}
		});
		btnRemoveBuild.setEnabled(false);
		formToolkit.adapt(btnRemoveBuild, true, true);
		btnRemoveBuild.setText("Remove Build");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		stance_procs.add(0);
		stance_multipliers.add(1.0);
		
		Label lblSep = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		lblSep.setText("sep1");
		formToolkit.adapt(lblSep, true, true);
		new Label(composite, SWT.NONE);
		
		Label lblSettings = new Label(composite, SWT.NONE);
		lblSettings.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblSettings, true, true);
		lblSettings.setText("Settings");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblSep_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSep_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		lblSep_1.setText("sep2");
		formToolkit.adapt(lblSep_1, true, true);
		
		lblHpScale = new Label(composite, SWT.NONE);
		lblHpScale.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		lblHpScale.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		formToolkit.adapt(lblHpScale, true, true);
		lblHpScale.setText("HP scale");
		
		health_scale_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_health_scale_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_health_scale_spinner.widthHint = 31;
		health_scale_spinner.setLayoutData(gd_health_scale_spinner);
		health_scale_spinner.setIncrement(50);
		health_scale_spinner.setDigits(2);
		health_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,string_to_double(lvl_spinner.getText()), (100 - spinner_to_double(armor_strip_spinner))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}

			}
		});
		health_scale_spinner.setMaximum(500);
		health_scale_spinner.setMinimum(1);
		health_scale_spinner.setSelection(100);
		formToolkit.adapt(health_scale_spinner);
		formToolkit.paintBordersFor(health_scale_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblArmorScale = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblArmorScale, true, true);
		lblArmorScale.setText("Armor scale");
		
		armor_scale_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_armor_scale_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_armor_scale_spinner.widthHint = 30;
		armor_scale_spinner.setLayoutData(gd_armor_scale_spinner);
		armor_scale_spinner.setDigits(2);
		armor_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,string_to_double(lvl_spinner.getText()), (100-spinner_to_double(armor_strip_spinner))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		armor_scale_spinner.setIncrement(50);
		armor_scale_spinner.setMaximum(500);
		armor_scale_spinner.setSelection(100);
		formToolkit.adapt(armor_scale_spinner);
		formToolkit.paintBordersFor(armor_scale_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblShieldScale = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblShieldScale, true, true);
		lblShieldScale.setText("Shield scale");
		
		shield_scale_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_shield_scale_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_shield_scale_spinner.widthHint = 31;
		shield_scale_spinner.setLayoutData(gd_shield_scale_spinner);
		shield_scale_spinner.setDigits(2);
		shield_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,string_to_double(lvl_spinner.getText()), (100-spinner_to_double(armor_strip_spinner))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		shield_scale_spinner.setMaximum(500);
		shield_scale_spinner.setSelection(100);
		shield_scale_spinner.setIncrement(50);
		formToolkit.adapt(shield_scale_spinner);
		formToolkit.paintBordersFor(shield_scale_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblEximusResist = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblEximusResist, true, true);
		lblEximusResist.setText("Eximus Resist.");
		
		resist_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_resist_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_resist_spinner.widthHint = 30;
		resist_spinner.setLayoutData(gd_resist_spinner);
		resist_spinner.setMaximum(5);
		resist_spinner.setDigits(1);
		formToolkit.adapt(resist_spinner);
		formToolkit.paintBordersFor(resist_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnSteelPathModifiers = new Button(composite, SWT.CHECK);
		btnSteelPathModifiers.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					if(btnSteelPathModifiers.getSelection()) {
						health_scale_spinner.setSelection(250);
						shield_scale_spinner.setSelection(250);
						armor_scale_spinner.setSelection(250);
					}
					else {
						health_scale_spinner.setSelection(100);
						shield_scale_spinner.setSelection(100);
						armor_scale_spinner.setSelection(100);
					}
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,string_to_double(lvl_spinner.getText()), (100-spinner_to_double(armor_strip_spinner))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		formToolkit.adapt(btnSteelPathModifiers, true, true);
		btnSteelPathModifiers.setText("Steel Path Modifiers");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblAbilitiesseparator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblAbilitiesseparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 8, 1));
		lblAbilitiesseparator.setText("abilitiesSeparator");
		formToolkit.adapt(lblAbilitiesseparator, true, true);
		
		Label lblArmorRemoval = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblArmorRemoval, true, true);
		lblArmorRemoval.setText("Armor Removal %");
		
		armor_strip_spinner = new Spinner(composite, SWT.BORDER);
		armor_strip_spinner.setIncrement(1000);
		GridData gd_armor_strip_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_armor_strip_spinner.widthHint = 28;
		armor_strip_spinner.setLayoutData(gd_armor_strip_spinner);
		armor_strip_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					
					int level = (int)(lvl_spinner.getSelection()/(Math.pow(10, lvl_spinner.getDigits())) );
					double armorStrip = (100-armor_strip_spinner.getSelection()/(Math.pow(10, armor_strip_spinner.getDigits())))/100.0;
					
					default_enemy = new Enemy(name,level, armorStrip , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		armor_strip_spinner.setPageIncrement(100);
		armor_strip_spinner.setMaximum(10000);
		armor_strip_spinner.setDigits(2);
		formToolkit.adapt(armor_strip_spinner);
		formToolkit.paintBordersFor(armor_strip_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnHeadshots = new Button(composite, SWT.CHECK);
		formToolkit.adapt(btnHeadshots, true, true);
		btnHeadshots.setText("Headshots");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnFullViral = new Button(composite, SWT.CHECK);
		formToolkit.adapt(btnFullViral, true, true);
		btnFullViral.setText("Full viral");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		health_series = new XYSeries("Health");
		armor_series = new XYSeries("Armor");
		shield_series = new XYSeries("Shield");
		XYSeriesCollection dataset = new XYSeriesCollection(health_series);
		dataset.addSeries(shield_series);
		dataset.addSeries(armor_series);
		chart = ChartFactory.createXYLineChart("Single Simulation", "Time", "", dataset);
		
		XYSeriesCollection datasetB = new XYSeriesCollection(scaleSeries);
		JFreeChart chartB = ChartFactory.createXYLineChart("Scale Enemy", "Level", "TTK", datasetB);

		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		ChartComposite chcomp_1 = new ChartComposite(ch_composite_1, SWT.NONE, chart, true);
		chcomp_1.setBounds(10, 10, 954, 418);
		formToolkit.adapt(chcomp_1);
		formToolkit.paintBordersFor(chcomp_1);
		
		ChartComposite chcomp_3 = new ChartComposite(ch_composite_3, SWT.NONE, chartB, true);
		
		chcomp_3.setBounds(10, 10, 954, 452);
		formToolkit.adapt(chcomp_3);
		formToolkit.paintBordersFor(chcomp_3);
		
		btnFullMagnetic = new Button(composite, SWT.CHECK);
		formToolkit.adapt(btnFullMagnetic, true, true);
		btnFullMagnetic.setText("Full magnetic");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnSimulate = new Button(composite, SWT.NONE);
		GridData gd_btnSimulate = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnSimulate.widthHint = 100;
		btnSimulate.setLayoutData(gd_btnSimulate);
		btnSimulate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {	
				
				
				
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),lvl_spinner.getSelection(), armorReduct ,default_weapon);

				double results[] = simulate(default_enemy,default_weapon,20);
				
				time_label.setText(String.format("%.2f", results[0]) );
				shots_label.setText(String.format("%.2f", results[1]) );
				v_time_label.setText(String.format("%.2f", results[2]) );
				v_shots_label.setText(String.format("%.2f", results[3]) );
			}
		});
		formToolkit.adapt(btnSimulate, true, true);
		btnSimulate.setText("Simulate");
		
		lvl_spinner = new Spinner(composite, SWT.BORDER);
		lvl_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					int level = (int)(lvl_spinner.getSelection()/(Math.pow(10, lvl_spinner.getDigits())));
					double armorStrip = (100-armor_strip_spinner.getSelection()/(Math.pow(10, armor_strip_spinner.getDigits())))/100.0;
					
					default_enemy = new Enemy(name,level, armorStrip , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		GridData gd_lvl_spinner = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 2);
		gd_lvl_spinner.widthHint = 73;
		lvl_spinner.setLayoutData(gd_lvl_spinner);
		lvl_spinner.setIncrement(10);
		lvl_spinner.setMaximum(9999);
		lvl_spinner.setMinimum(10);
		lvl_spinner.setSelection(165);
		formToolkit.adapt(lvl_spinner);
		formToolkit.paintBordersFor(lvl_spinner);
		
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnGraph = new Button(composite, SWT.NONE);
		GridData gd_btnGraph = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_btnGraph.widthHint = 142;
		btnGraph.setLayoutData(gd_btnGraph);
		btnGraph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				health_series.clear();	
				armor_series.clear();
				shield_series.clear();
				
				XYPlot plot = (XYPlot) chart.getPlot();

				plot.clearDomainMarkers();
				
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),lvl_spinner.getSelection(), armorReduct, default_weapon);
				
				graph = true;
				double results[] = simulate(default_enemy,default_weapon,1);
				graph = false;

				time_label.setText(String.format("%.2f", results[0]) );
				shots_label.setText(String.format("%.2f", results[1]) );
				v_time_label.setText(String.format("%.2f", results[2]) );
				v_shots_label.setText(String.format("%.2f", results[3]) );
			}
		});
		formToolkit.adapt(btnGraph, true, true);
		btnGraph.setText("Graph One Simulation");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnScale = new Button(composite, SWT.NONE);
		GridData gd_btnScale = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_btnScale.widthHint = 180;
		btnScale.setLayoutData(gd_btnScale);
		btnScale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int start_level = start_level_spinner.getSelection();
				int end_level = end_level_spinner.getSelection();
				int increment = 25;
				
				
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),1, armorReduct, default_weapon);
				
				scaleSeries = new XYSeries(numSeries+": "+txtSeriesName.getText());
				datasetB.addSeries(scaleSeries);
				
				for(int i = start_level; i <= end_level+1; i += increment) {	
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy((String)enemy_combo.getText(),i, armorReduct,default_weapon);
					double results[] = simulate(default_enemy,default_weapon,20);
					if(results[0]>= STOP_COND) {
						scaleSeries.add(i,STOP_COND);
						break;	
					}
					scaleSeries.add(i, results[0]);
					
						
				}

				numSeries++;
			}
		});
		formToolkit.adapt(btnScale, true, true);
		btnScale.setText("Graph Scaling Simulation");
		
		start_level_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_start_level_spinner = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_start_level_spinner.widthHint = 75;
		start_level_spinner.setLayoutData(gd_start_level_spinner);
		start_level_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(ui_set_up) {
					ui_set_up = false;
					int start_level = start_level_spinner.getSelection();
					int end_level = end_level_spinner.getSelection();
					
					if(end_level-start_level<=50) {
						end_level_spinner.setSelection(start_level+50);
					}
					ui_set_up = true;
				}
			}
		});
		start_level_spinner.setIncrement(10);
		start_level_spinner.setMaximum(9949);
		start_level_spinner.setMinimum(1);
		formToolkit.adapt(start_level_spinner);
		formToolkit.paintBordersFor(start_level_spinner);
		
		end_level_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_end_level_spinner = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_end_level_spinner.widthHint = 61;
		end_level_spinner.setLayoutData(gd_end_level_spinner);
		end_level_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(ui_set_up) {
					ui_set_up = false;
					int start_level = start_level_spinner.getSelection();
					int end_level = end_level_spinner.getSelection();
					
					if(end_level-start_level<=50) {
						start_level_spinner.setSelection(end_level-50);
					}
					ui_set_up = true;
				}
			}
		});
		end_level_spinner.setMaximum(9999);
		end_level_spinner.setMinimum(51);
		end_level_spinner.setSelection(300);
		end_level_spinner.setIncrement(10);
		formToolkit.adapt(end_level_spinner);
		formToolkit.paintBordersFor(end_level_spinner);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		

		
		Button btnClearGraph = new Button(composite, SWT.NONE);
		btnClearGraph.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnClearGraph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(scaleSeries != null) {
					scaleSeries.clear(); 
				}
				numSeries = 0;
				datasetB.removeAllSeries();
			}
		});
		formToolkit.adapt(btnClearGraph, true, true);
		btnClearGraph.setText("Clear Graph");
		new Label(composite, SWT.NONE);
		
		CTabItem tbtmAddBuild = new CTabItem(tabFolder, SWT.NONE);
		tbtmAddBuild.setText("Custom Build");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmAddBuild.setControl(composite_3);
		formToolkit.paintBordersFor(composite_3);
		composite_3.setLayout(new GridLayout(8, false));
		
		Label lblWeapon_1 = new Label(composite_3, SWT.NONE);
		lblWeapon_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblWeapon_1, true, true);
		lblWeapon_1.setText("Weapon");
		
		weaponListCombo = new Combo(composite_3, SWT.NONE);
		weaponListCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = "Custom Build Tab";
				
				set_up_fire_mode_combo(default_weapon);
				

				txtSeriesName.setText(weaponListCombo.getText());
					
				default_weapon = new Weapon(name, true);
				
				stance_procs.clear();
				stance_procs.add(0);
				stance_multipliers.clear();
				stance_multipliers.add(1.0);
				move_combo.removeAll();
				update_weapon_table(default_weapon);
				
				//default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);

		        txtBuildName.setText(weaponListCombo.getText());  	//new
		        
		        //populate_stance_combo(default_weapon);
		        //weaponCombo
		        weaponCombo.select(0);

			}
		});
		weaponListCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(weaponListCombo);
		formToolkit.paintBordersFor(weaponListCombo);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblFireMode = new Label(composite_3, SWT.NONE);
		lblFireMode.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblFireMode, true, true);
		lblFireMode.setText("Fire Mode");
		
		fireModeCombo = new Combo(composite_3, SWT.NONE);
		fireModeCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = "Custom Build Tab";
		        
				
				default_weapon = new Weapon(name, true);
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		fireModeCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(fireModeCombo);
		formToolkit.paintBordersFor(fireModeCombo);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblStance_1 = new Label(composite_3, SWT.NONE);
		lblStance_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblStance_1, true, true);
		lblStance_1.setText("Stance");
		
		stance_combo = new Combo(composite_3, SWT.NONE);
		stance_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(stance_combo.getText().equals("None")) {
					move_combo.removeAll();
					stance_procs.clear();
					stance_procs.add(0);
					stance_multipliers.clear();
					stance_multipliers.add(1.0);
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}else {
					setup_move_combo(stance_combo.getText(), default_weapon);
					parse_stance(stance_combo.getText());
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		stance_combo.add("None");
		stance_combo.select(0);

		stance_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		formToolkit.adapt(stance_combo);
		formToolkit.paintBordersFor(stance_combo);
		
		move_combo = new Combo(composite_3, SWT.NONE);
		move_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(move_combo.getText());
				if(stance_combo.getText().equals("None")) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
					stance_procs.clear();
					stance_procs.add(0);
					stance_multipliers.clear();
					stance_multipliers.add(1.0);
				}else {
					parse_stance(stance_combo.getText());
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
				
				//tableItem8.setText(new String[] {"s/ attack", Double.toString(melee_time)});
			}
		});
		move_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		formToolkit.adapt(move_combo);
		formToolkit.paintBordersFor(move_combo);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblDamage = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblDamage, true, true);
		lblDamage.setText("Damage");
		
		damage_mod_text = new Text(composite_3, SWT.BORDER);
		damage_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
					
				}
			}
		});
		damage_mod_text.setText("0.0");
		damage_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(damage_mod_text, true, true);
		
		btnCheckButton0 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton0, true, true);
		btnCheckButton0.setText("");
		new Label(composite_3, SWT.NONE);
		
		Label label_1 = new Label(composite_3, SWT.NONE);
		label_1.setText("Elem Combo");
		formToolkit.adapt(label_1, true, true);
		
		lblBane = new Label(composite_3, SWT.NONE);
		lblBane.setText("% Bane");
		formToolkit.adapt(lblBane, true, true);
		
		bane_mod_text = new Text(composite_3, SWT.BORDER);
		bane_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		bane_mod_text.setText("0.0");
		bane_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(bane_mod_text, true, true);
		
		btnCheckButton1 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton1, true, true);
		btnCheckButton1.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnCorrosive = new Button(composite_3, SWT.CHECK);
		btnCorrosive.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnCorrosive.setText("Corrosive");
		formToolkit.adapt(btnCorrosive, true, true);
		
		Label lblCriticalChance_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblCriticalChance_1, true, true);
		lblCriticalChance_1.setText("Critical Chance");
		
		crit_chance_mod_text = new Text(composite_3, SWT.BORDER);
		crit_chance_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		crit_chance_mod_text.setText("0.0");
		crit_chance_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(crit_chance_mod_text, true, true);
		
		btnCheckButton2 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton2, true, true);
		btnCheckButton2.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnViral = new Button(composite_3, SWT.CHECK);
		btnViral.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnViral.setText("Viral");
		formToolkit.adapt(btnViral, true, true);
		
		Label lblCriticalDamage = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblCriticalDamage, true, true);
		lblCriticalDamage.setText("Critical Damage");
		
		crit_damage_mod_text = new Text(composite_3, SWT.BORDER);
		crit_damage_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		crit_damage_mod_text.setText("0.0");
		crit_damage_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(crit_damage_mod_text, true, true);
		
		btnCheckButton3 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton3, true, true);
		btnCheckButton3.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnRadiation = new Button(composite_3, SWT.CHECK);
		btnRadiation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnRadiation.setText("Radiation");
		formToolkit.adapt(btnRadiation, true, true);
		
		Label lblMultishot = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblMultishot, true, true);
		lblMultishot.setText("Multishot");
		
		multishot_mod_text = new Text(composite_3, SWT.BORDER);
		multishot_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		multishot_mod_text.setText("0.0");
		multishot_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(multishot_mod_text, true, true);
		
		btnCheckButton4 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton4, true, true);
		btnCheckButton4.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnMagnetic = new Button(composite_3, SWT.CHECK);
		btnMagnetic.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnMagnetic.setText("Magnetic");
		formToolkit.adapt(btnMagnetic, true, true);
		
		Label lblStatusChance_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblStatusChance_1, true, true);
		lblStatusChance_1.setText("Status Chance");
		
		status_chance_mod_text = new Text(composite_3, SWT.BORDER);
		status_chance_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		status_chance_mod_text.setText("0.0");
		status_chance_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(status_chance_mod_text, true, true);
		
		btnCheckButton5 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton5, true, true);
		btnCheckButton5.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnGas = new Button(composite_3, SWT.CHECK);
		btnGas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnGas.setText("Gas");
		formToolkit.adapt(btnGas, true, true);
		
		Label lblCold_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblCold_1, true, true);
		lblCold_1.setText("Cold");
		
		cold_mod_text = new Text(composite_3, SWT.BORDER);
		cold_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		cold_mod_text.setText("0.0");
		cold_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(cold_mod_text, true, true);
		
		btnCheckButton6 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton6, true, true);
		btnCheckButton6.setText("");
		new Label(composite_3, SWT.NONE);
		
		btnBlast = new Button(composite_3, SWT.CHECK);
		btnBlast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		btnBlast.setText("Blast");
		formToolkit.adapt(btnBlast, true, true);
		
		Label lblToxin_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblToxin_1, true, true);
		lblToxin_1.setText("Toxin");
		
		toxin_mod_text = new Text(composite_3, SWT.BORDER);
		toxin_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		toxin_mod_text.setText("0.0");
		toxin_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(toxin_mod_text, true, true);
		
		btnCheckButton7 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton7, true, true);
		btnCheckButton7.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblHeat_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblHeat_1, true, true);
		lblHeat_1.setText("Heat");
		
		heat_mod_text = new Text(composite_3, SWT.BORDER);
		heat_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		heat_mod_text.setText("0.0");
		heat_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(heat_mod_text, true, true);
		
		btnCheckButton8 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton8, true, true);
		btnCheckButton8.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblElectricity_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblElectricity_1, true, true);
		lblElectricity_1.setText("Electricity");
		
		electricity_mod_text = new Text(composite_3, SWT.BORDER);
		electricity_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		electricity_mod_text.setText("0.0");
		electricity_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(electricity_mod_text, true, true);
		
		btnCheckButton9 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton9, true, true);
		btnCheckButton9.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblFireRate_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblFireRate_1, true, true);
		lblFireRate_1.setText("Fire Rate");
		
		fire_rate_mod_text = new Text(composite_3, SWT.BORDER);
		fire_rate_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		fire_rate_mod_text.setText("0.0");
		fire_rate_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(fire_rate_mod_text, true, true);
		
		btnCheckButton10 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton10, true, true);
		btnCheckButton10.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblMagazine_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblMagazine_1, true, true);
		lblMagazine_1.setText("Magazine");
		
		magazine_mod_text = new Text(composite_3, SWT.BORDER);
		magazine_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		magazine_mod_text.setText("0.0");
		magazine_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(magazine_mod_text, true, true);
		
		btnCheckButton11 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton11, true, true);
		btnCheckButton11.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblReload_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblReload_1, true, true);
		lblReload_1.setText("Reload");
		
		reload_mod_text = new Text(composite_3, SWT.BORDER);
		reload_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		reload_mod_text.setText("0.0");
		reload_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(reload_mod_text, true, true);
		
		btnCheckButton12 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton12, true, true);
		btnCheckButton12.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblImpact_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblImpact_1, true, true);
		lblImpact_1.setText("Impact");
		
		impact_mod_text = new Text(composite_3, SWT.BORDER);
		impact_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		impact_mod_text.setText("0.0");
		impact_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(impact_mod_text, true, true);
		
		btnCheckButton13 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton13, true, true);
		btnCheckButton13.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblPuncture_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblPuncture_1, true, true);
		lblPuncture_1.setText("Puncture");
		
		puncture_mod_text = new Text(composite_3, SWT.BORDER);
		puncture_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
					
				}

			}
		});
		puncture_mod_text.setText("0.0");
		puncture_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(puncture_mod_text, true, true);
		
		btnCheckButton14 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton14, true, true);
		btnCheckButton14.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblSlash_1 = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblSlash_1, true, true);
		lblSlash_1.setText("Slash");
		
		slash_mod_text = new Text(composite_3, SWT.BORDER);
		slash_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		slash_mod_text.setText("0.0");
		slash_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(slash_mod_text, true, true);
		
		btnCheckButton15 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton15, true, true);
		btnCheckButton15.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		CTabItem tbtmAddWeapon = new CTabItem(tabFolder, SWT.NONE);
		tbtmAddWeapon.setText("Add Weapon");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmAddWeapon.setControl(composite_1);
		formToolkit.paintBordersFor(composite_1);
		composite_1.setLayout(new GridLayout(3, false));
		
		Label lblName = new Label(composite_1, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblName, true, true);
		lblName.setText("Name");
		
		txtName = new Text(composite_1, SWT.BORDER);
		txtName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		formToolkit.adapt(txtName, true, true);
		
		Label lblCriticalChance = new Label(composite_1, SWT.NONE);
		lblCriticalChance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblCriticalChance, true, true);
		lblCriticalChance.setText("Critical Chance %");
		
		ccESpinner = new Spinner(composite_1, SWT.BORDER);
		ccESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		ccESpinner.setIncrement(10);
		ccESpinner.setDigits(1);
		ccESpinner.setMaximum(2000);
		formToolkit.adapt(ccESpinner);
		formToolkit.paintBordersFor(ccESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblCriticalMultiplier = new Label(composite_1, SWT.NONE);
		lblCriticalMultiplier.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblCriticalMultiplier, true, true);
		lblCriticalMultiplier.setText("Critical Multiplier");
		
		cdESpinner = new Spinner(composite_1, SWT.BORDER);
		cdESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cdESpinner.setIncrement(10);
		cdESpinner.setDigits(1);
		cdESpinner.setMaximum(1000);
		cdESpinner.setMinimum(1);
		formToolkit.adapt(cdESpinner);
		formToolkit.paintBordersFor(cdESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblStatusChance = new Label(composite_1, SWT.NONE);
		lblStatusChance.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblStatusChance, true, true);
		lblStatusChance.setText("Status Chance %");
		
		scESpinner = new Spinner(composite_1, SWT.BORDER);
		scESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		scESpinner.setMaximum(1000);
		scESpinner.setDigits(1);
		formToolkit.adapt(scESpinner);
		formToolkit.paintBordersFor(scESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblFireRate = new Label(composite_1, SWT.NONE);
		lblFireRate.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblFireRate, true, true);
		lblFireRate.setText("Fire Rate");
		
		frESpinner = new Spinner(composite_1, SWT.BORDER);
		frESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		frESpinner.setIncrement(1000);
		frESpinner.setMaximum(10000);
		frESpinner.setMinimum(100);
		frESpinner.setDigits(3);
		formToolkit.adapt(frESpinner);
		formToolkit.paintBordersFor(frESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblPellets = new Label(composite_1, SWT.NONE);
		lblPellets.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblPellets, true, true);
		lblPellets.setText("Pellets");
		
		pelletESpinner = new Spinner(composite_1, SWT.BORDER);
		pelletESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		pelletESpinner.setMaximum(50);
		pelletESpinner.setMinimum(1);
		formToolkit.adapt(pelletESpinner);
		formToolkit.paintBordersFor(pelletESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblReload = new Label(composite_1, SWT.NONE);
		lblReload.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblReload, true, true);
		lblReload.setText("Reload");
		
		reloadESpinner = new Spinner(composite_1, SWT.BORDER);
		reloadESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		reloadESpinner.setMaximum(10000);
		reloadESpinner.setMinimum(100);
		reloadESpinner.setIncrement(1000);
		reloadESpinner.setDigits(3);
		formToolkit.adapt(reloadESpinner);
		formToolkit.paintBordersFor(reloadESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblMagazine = new Label(composite_1, SWT.NONE);
		lblMagazine.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblMagazine, true, true);
		lblMagazine.setText("Magazine");
		
		magESpinner = new Spinner(composite_1, SWT.BORDER);
		magESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		magESpinner.setMaximum(10000);
		magESpinner.setMinimum(1);
		formToolkit.adapt(magESpinner);
		formToolkit.paintBordersFor(magESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblImpact = new Label(composite_1, SWT.NONE);
		lblImpact.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblImpact, true, true);
		lblImpact.setText("Impact");
		
		iESpinner = new Spinner(composite_1, SWT.BORDER);
		iESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		iESpinner.setIncrement(10);
		iESpinner.setMaximum(100000);
		iESpinner.setDigits(1);
		formToolkit.adapt(iESpinner);
		formToolkit.paintBordersFor(iESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblSlash = new Label(composite_1, SWT.NONE);
		lblSlash.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblSlash, true, true);
		lblSlash.setText("Slash");
		
		sESpinner = new Spinner(composite_1, SWT.BORDER);
		sESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		sESpinner.setIncrement(10);
		sESpinner.setMaximum(100000);
		sESpinner.setDigits(1);
		formToolkit.adapt(sESpinner);
		formToolkit.paintBordersFor(sESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblPuncture = new Label(composite_1, SWT.NONE);
		lblPuncture.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblPuncture, true, true);
		lblPuncture.setText("Puncture");
		
		pESpinner = new Spinner(composite_1, SWT.BORDER);
		pESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		pESpinner.setIncrement(10);
		pESpinner.setMaximum(100000);
		pESpinner.setDigits(1);
		formToolkit.adapt(pESpinner);
		formToolkit.paintBordersFor(pESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblCold = new Label(composite_1, SWT.NONE);
		lblCold.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblCold, true, true);
		lblCold.setText("Cold");
		
		cESpinner = new Spinner(composite_1, SWT.BORDER);
		cESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		cESpinner.setIncrement(10);
		cESpinner.setMaximum(100000);
		cESpinner.setDigits(1);
		formToolkit.adapt(cESpinner);
		formToolkit.paintBordersFor(cESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblElectricity = new Label(composite_1, SWT.NONE);
		lblElectricity.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblElectricity, true, true);
		lblElectricity.setText("Electricity");
		
		eESpinner = new Spinner(composite_1, SWT.BORDER);
		eESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		eESpinner.setIncrement(10);
		eESpinner.setMaximum(100000);
		eESpinner.setDigits(1);
		formToolkit.adapt(eESpinner);
		formToolkit.paintBordersFor(eESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblHeat = new Label(composite_1, SWT.NONE);
		lblHeat.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblHeat, true, true);
		lblHeat.setText("Heat");
		
		hESpinner = new Spinner(composite_1, SWT.BORDER);
		hESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		hESpinner.setMaximum(100000);
		hESpinner.setIncrement(10);
		hESpinner.setDigits(1);
		formToolkit.adapt(hESpinner);
		formToolkit.paintBordersFor(hESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblToxin = new Label(composite_1, SWT.NONE);
		lblToxin.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblToxin, true, true);
		lblToxin.setText("Toxin");
		
		tESpinner = new Spinner(composite_1, SWT.BORDER);
		tESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		tESpinner.setMaximum(100000);
		tESpinner.setIncrement(10);
		tESpinner.setDigits(1);
		formToolkit.adapt(tESpinner);
		formToolkit.paintBordersFor(tESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblBlast = new Label(composite_1, SWT.NONE);
		lblBlast.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBlast, true, true);
		lblBlast.setText("Blast");
		
		bESpinner = new Spinner(composite_1, SWT.BORDER);
		bESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		bESpinner.setMaximum(100000);
		bESpinner.setIncrement(10);
		bESpinner.setDigits(1);
		formToolkit.adapt(bESpinner);
		formToolkit.paintBordersFor(bESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblCorrosive = new Label(composite_1, SWT.NONE);
		lblCorrosive.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblCorrosive, true, true);
		lblCorrosive.setText("Corrosive");
		
		corESpinner = new Spinner(composite_1, SWT.BORDER);
		corESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		corESpinner.setMaximum(100000);
		corESpinner.setIncrement(10);
		corESpinner.setDigits(1);
		formToolkit.adapt(corESpinner);
		formToolkit.paintBordersFor(corESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblGas = new Label(composite_1, SWT.NONE);
		lblGas.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblGas, true, true);
		lblGas.setText("Gas");
		
		gESpinner = new Spinner(composite_1, SWT.BORDER);
		gESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		gESpinner.setMaximum(100000);
		gESpinner.setIncrement(10);
		gESpinner.setDigits(1);
		formToolkit.adapt(gESpinner);
		formToolkit.paintBordersFor(gESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblMagnetic = new Label(composite_1, SWT.NONE);
		lblMagnetic.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblMagnetic, true, true);
		lblMagnetic.setText("Magnetic");
		
		mESpinner = new Spinner(composite_1, SWT.BORDER);
		mESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		mESpinner.setMaximum(100000);
		mESpinner.setIncrement(10);
		mESpinner.setDigits(1);
		formToolkit.adapt(mESpinner);
		formToolkit.paintBordersFor(mESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblRadiation = new Label(composite_1, SWT.NONE);
		lblRadiation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblRadiation, true, true);
		lblRadiation.setText("Radiation");
		
		rESpinner = new Spinner(composite_1, SWT.BORDER);
		rESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		rESpinner.setMaximum(100000);
		rESpinner.setIncrement(10);
		rESpinner.setDigits(1);
		formToolkit.adapt(rESpinner);
		formToolkit.paintBordersFor(rESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblViral = new Label(composite_1, SWT.NONE);
		lblViral.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblViral, true, true);
		lblViral.setText("Viral");
		
		viralESpinner = new Spinner(composite_1, SWT.BORDER);
		viralESpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		viralESpinner.setMaximum(100000);
		viralESpinner.setIncrement(10);
		viralESpinner.setDigits(1);
		formToolkit.adapt(viralESpinner);
		formToolkit.paintBordersFor(viralESpinner);
		new Label(composite_1, SWT.NONE);
		
		Label lblvoid = new Label(composite_1, SWT.NONE);
		lblvoid.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblvoid, true, true);
		lblvoid.setText("Void");
		
		voidSpinner = new Spinner(composite_1, SWT.BORDER);
		voidSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		voidSpinner.setMaximum(100000);
		voidSpinner.setIncrement(10);
		voidSpinner.setDigits(1);
		formToolkit.adapt(voidSpinner);
		formToolkit.paintBordersFor(voidSpinner);
		new Label(composite_1, SWT.NONE);
		
		Button btnAdd_weapon = new Button(composite_1, SWT.NONE);
		btnAdd_weapon.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			
			public void widgetSelected(SelectionEvent e) {
				
		        // parsing file 
		        Object obje = null;
				try {
					obje = new JSONParser().parse(new FileReader("weapons.json"));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				} 
		          
		        // typecasting obj to JSONObject 
		        JSONObject jo = (JSONObject) obje;
		        
		        //Check if name already exists
		        Map<String,Object> weapons = (Map<String,Object>)jo;
		        String name = txtName.getText();
		        if(weapons.get(name) != null) {
					name += "0"; // append character if name exists
		        }
		        
				//double pellet = string_to_double(pelletESpinner.getText());
		        double pellet = (double)pelletESpinner.getSelection()/Math.pow(10, pelletESpinner.getDigits());
				JSONArray dmg = new JSONArray();
				dmg.add((double)iESpinner.getSelection()/Math.pow(10, iESpinner.getDigits()));
				dmg.add((double)pESpinner.getSelection()/Math.pow(10, pESpinner.getDigits()));
				dmg.add((double)sESpinner.getSelection()/Math.pow(10, sESpinner.getDigits()));
				
				dmg.add((double)hESpinner.getSelection()/Math.pow(10, hESpinner.getDigits()));
				dmg.add((double)cESpinner.getSelection()/Math.pow(10, cESpinner.getDigits()));
				dmg.add((double)eESpinner.getSelection()/Math.pow(10, eESpinner.getDigits()));
				dmg.add((double)tESpinner.getSelection()/Math.pow(10, tESpinner.getDigits()));
				
				dmg.add((double)bESpinner.getSelection()/Math.pow(10, bESpinner.getDigits()));
				dmg.add((double)rESpinner.getSelection()/Math.pow(10, rESpinner.getDigits()));
				dmg.add((double)gESpinner.getSelection()/Math.pow(10, gESpinner.getDigits()));
				dmg.add((double)mESpinner.getSelection()/Math.pow(10, mESpinner.getDigits()));
				dmg.add((double)viralESpinner.getSelection()/Math.pow(10, viralESpinner.getDigits()));
				dmg.add((double)corESpinner.getSelection()/Math.pow(10, corESpinner.getDigits()));
				dmg.add((double)voidSpinner.getSelection()/Math.pow(10, voidSpinner.getDigits()));
				
				
				double status = (double)scESpinner.getSelection()/Math.pow(10, 2+scESpinner.getDigits());
				double critChance = (double)ccESpinner.getSelection()/Math.pow(10, 2+ccESpinner.getDigits()) ;
				double critMultiplier = (double)cdESpinner.getSelection()/Math.pow(10, cdESpinner.getDigits());
				double fireRate = (double)frESpinner.getSelection()/Math.pow(10, frESpinner.getDigits());
				double reload = (double)reloadESpinner.getSelection()/Math.pow(10, reloadESpinner.getDigits());
				double magazine = (double)magESpinner.getSelection();
				
				JSONObject weapon = new JSONObject();
				
				weapon.put("damagePerShot", dmg);
				
				weapon.put("criticalChance",critChance);
				weapon.put("criticalMultiplier",critMultiplier);
				weapon.put("multishot",pellet);
				weapon.put("procChance",status);
				weapon.put("reloadTime",reload);
				weapon.put("magazineSize",magazine);
				weapon.put("fireRate",fireRate);
				
				weapons.put(name, weapon);
				JSONObject otherfm = new JSONObject();
				JSONObject sec_ef = new JSONObject();
				
				weapons.put("OtherFireModes", null);
				weapons.put("SecondaryEffects", null);
				
		        try (FileWriter file = new FileWriter("weapons.json")) { 
		            file.write(jo.toJSONString());
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }

				weaponListCombo.add(txtName.getText());
				try {
					wep_obj = new JSONParser().parse(new FileReader("weapons.json"));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		btnAdd_weapon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(btnAdd_weapon, true, true);
		btnAdd_weapon.setText("Add");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Button btnOpenFile_weapon = new Button(composite_1, SWT.NONE);
		btnOpenFile_weapon.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnOpenFile_weapon.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File weapon_file = new File("weapons.json");
				try {
					Desktop.getDesktop().open(weapon_file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		formToolkit.adapt(btnOpenFile_weapon, true, true);
		btnOpenFile_weapon.setText("Open file");
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		CTabItem tbtmAddEnemy = new CTabItem(tabFolder, SWT.NONE);
		tbtmAddEnemy.setText("Add Enemy");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmAddEnemy.setControl(composite_2);
		formToolkit.paintBordersFor(composite_2);
		composite_2.setLayout(new GridLayout(2, false));
		
		Label lblEnemyName = new Label(composite_2, SWT.NONE);
		lblEnemyName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblEnemyName, true, true);
		lblEnemyName.setText("Name");
		
		txtEnemyname = new Text(composite_2, SWT.BORDER);
		txtEnemyname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(txtEnemyname, true, true);
		
		Label lblArmorType = new Label(composite_2, SWT.NONE);
		lblArmorType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblArmorType, true, true);
		lblArmorType.setText("Armor Type");
		
		Combo armor_combo = new Combo(composite_2, SWT.NONE);
		armor_combo.setItems(new String[] {"Alloy", "Ferrite"});
		armor_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(armor_combo);
		formToolkit.paintBordersFor(armor_combo);
		armor_combo.select(1);
		
		Label lblHealthType = new Label(composite_2, SWT.NONE);
		lblHealthType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblHealthType, true, true);
		lblHealthType.setText("Health Type");
		
		Combo health_combo = new Combo(composite_2, SWT.NONE);
		health_combo.setItems(new String[] {"ClonedFlesh", "Robotic", "Machinery", "Flesh", "Robotic", "Infested", "InfestedFlesh", "Fossilized", "InfestedSinew"});
		health_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(health_combo);
		formToolkit.paintBordersFor(health_combo);
		health_combo.select(0);
		
		Label lblShieldType = new Label(composite_2, SWT.NONE);
		lblShieldType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblShieldType, true, true);
		lblShieldType.setText("Shield Type");
		
		Combo shield_combo = new Combo(composite_2, SWT.NONE);
		shield_combo.setItems(new String[] {"Shield", "ProtoShield", "Eidolon Shield"});
		shield_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(shield_combo);
		formToolkit.paintBordersFor(shield_combo);
		shield_combo.select(0);
		
		Label lblBaseArmor = new Label(composite_2, SWT.NONE);
		lblBaseArmor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseArmor, true, true);
		lblBaseArmor.setText("Base Armor");
		
		Spinner bArmorSpinner = new Spinner(composite_2, SWT.BORDER);
		bArmorSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		bArmorSpinner.setMaximum(100000);
		formToolkit.adapt(bArmorSpinner);
		formToolkit.paintBordersFor(bArmorSpinner);
		
		Label lblBaseHealth = new Label(composite_2, SWT.NONE);
		lblBaseHealth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseHealth, true, true);
		lblBaseHealth.setText("Base Health");
		
		bHealthSpinner = new Spinner(composite_2, SWT.BORDER);
		bHealthSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		bHealthSpinner.setMaximum(100000);
		bHealthSpinner.setMinimum(1);
		formToolkit.adapt(bHealthSpinner);
		formToolkit.paintBordersFor(bHealthSpinner);
		
		Label lblBaseShield = new Label(composite_2, SWT.NONE);
		lblBaseShield.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseShield, true, true);
		lblBaseShield.setText("Base Shield");
		
		bShieldSpinner = new Spinner(composite_2, SWT.BORDER);
		bShieldSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		bShieldSpinner.setMaximum(100000);
		formToolkit.adapt(bShieldSpinner);
		formToolkit.paintBordersFor(bShieldSpinner);
		
		Label lblBaseLevel = new Label(composite_2, SWT.NONE);
		lblBaseLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseLevel, true, true);
		lblBaseLevel.setText("Base Level");
		
		bLevelSpinner = new Spinner(composite_2, SWT.BORDER);
		bLevelSpinner.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		bLevelSpinner.setMaximum(500);
		bLevelSpinner.setMinimum(1);
		formToolkit.adapt(bLevelSpinner);
		formToolkit.paintBordersFor(bLevelSpinner);
		
		Button btnAdd_enemy = new Button(composite_2, SWT.NONE);
		btnAdd_enemy.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {        

		        // parsing file 
		        Object eobj = null;
				try {
					eobj = new JSONParser().parse(new FileReader("enemy.json"));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				} 
		          
		        // typecasting obj to JSONObject 
		        JSONObject jo = (JSONObject) eobj; 
		        
		        //Check if name already exists .get("Enemy")
		        Map<String,Object> data = (Map<String,Object>)jo; 
		        String name = txtEnemyname.getText();
		        
		        if(data.get(name) != null) {
					name += "0"; // append character if name exists
		        }
	        
		        JSONObject stats = new JSONObject();
		        stats.put("ArmorType", armor_combo.getText());
		        stats.put("HealthType", health_combo.getText());
		        stats.put("ShieldType", shield_combo.getText());
		        stats.put("BaseArmor", bArmorSpinner.getSelection());
		        stats.put("BaseHealth", bHealthSpinner.getSelection());
		        stats.put("BaseShield", bShieldSpinner.getSelection());
		        stats.put("BaseLevel", bLevelSpinner.getSelection());
		        data.put(name, stats);
		        
		        try (FileWriter file = new FileWriter("enemy.json")) { 
		            file.write(jo.toJSONString());
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		        
				enemy_combo.add(name);
			}
		});
		btnAdd_enemy.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(btnAdd_enemy, true, true);
		btnAdd_enemy.setText("Add");
		
		Button btnOpenFile_enemy = new Button(composite_2, SWT.NONE);
		btnOpenFile_enemy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File enemy_file = new File("enemy.json");
				try {
					Desktop.getDesktop().open(enemy_file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		formToolkit.adapt(btnOpenFile_enemy, true, true);
		btnOpenFile_enemy.setText("Open File");
		//String[] elements = new String[] {"Damage","Multishot","CritChance","CritMultiplier","Toxin","Electricity","Cold","Heat","Slash","Puncture","Impact","Reload","Magazine","FireRate"};
		
		//weaponCombo.add("Custom Build Tab");
		
		populate_weapon_combo(weaponListCombo, default_weapon);
		populate_enemy_combo(enemy_combo);
		//populateCombo(enemy_combo, weaponListCombo,weaponCombo);
		
		weaponCombo.select(0);
		new Label(composite, SWT.NONE);
		
		txtSeriesName = new Text(composite, SWT.BORDER);
		txtSeriesName.setText("Scaling Simulation Series Name");
		GridData gd_txtSeriesName = new GridData(SWT.FILL, SWT.CENTER, true, false, 6, 1);
		gd_txtSeriesName.widthHint = 81;
		txtSeriesName.setLayoutData(gd_txtSeriesName);
		formToolkit.adapt(txtSeriesName, true, true);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label title_time = new Label(composite, SWT.NONE);
		title_time.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(title_time, true, true);
		title_time.setText("Time");
		
		Label title_shots = new Label(composite, SWT.NONE);
		title_shots.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(title_shots, true, true);
		title_shots.setText("Shots");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblMean = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblMean, true, true);
		lblMean.setText("Mean");
		
		time_label = new Label(composite, SWT.NONE);
		time_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(time_label, true, true);
		time_label.setText("0");
		
		shots_label = new Label(composite, SWT.NONE);
		shots_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(shots_label, true, true);
		shots_label.setText("0");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label lblVariance = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblVariance, true, true);
		lblVariance.setText("Stnd Dev");
		
		v_time_label = new Label(composite, SWT.NONE);
		v_time_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(v_time_label, true, true);
		v_time_label.setText("0");
		
		v_shots_label = new Label(composite, SWT.NONE);
		v_shots_label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(v_shots_label, true, true);
		v_shots_label.setText("0");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblStatusDur = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblStatusDur, true, true);
		lblStatusDur.setText("Status Dur");
		
		status_duration_mod_text = new Text(composite_3, SWT.BORDER);
		status_duration_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		status_duration_mod_text.setText("0.0");
		status_duration_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(status_duration_mod_text, true, true);
		
		btnCheckButton16 = new Button(composite_3, SWT.CHECK);
		formToolkit.adapt(btnCheckButton16, true, true);
		btnCheckButton16.setText("");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		lblMultiplicativeFireRate = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblMultiplicativeFireRate, true, true);
		lblMultiplicativeFireRate.setText("Multiplicative Fire Rate");
		
		multiplicative_firerate_mod_text = new Text(composite_3, SWT.BORDER);
		multiplicative_firerate_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		multiplicative_firerate_mod_text.setText("0.0");
		GridData gd_multiplicative_firerate_mod_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1);
		gd_multiplicative_firerate_mod_text.widthHint = 33;
		multiplicative_firerate_mod_text.setLayoutData(gd_multiplicative_firerate_mod_text);
		formToolkit.adapt(multiplicative_firerate_mod_text, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		btnHunterMunitions = new Button(composite_3, SWT.CHECK);
		btnHunterMunitions.setText("Hunter Munitions");
		formToolkit.adapt(btnHunterMunitions, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		btnShatteringImpact = new Button(composite_3, SWT.CHECK);
		btnShatteringImpact.setText("Shatt. Imp.");
		formToolkit.adapt(btnShatteringImpact, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		btnAmalgamShatImp = new Button(composite_3, SWT.CHECK);
		btnAmalgamShatImp.setText("Amalgam Shat. Imp.");
		formToolkit.adapt(btnAmalgamShatImp, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		btnPrimedchamber = new Button(composite_3, SWT.CHECK);
		btnPrimedchamber.setText("Primed Chamber");
		formToolkit.adapt(btnPrimedchamber, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		lblMiscMultipliers = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblMiscMultipliers, true, true);
		lblMiscMultipliers.setText("Misc. Multipliers");
		
		general_multiplier_mod_text = new Text(composite_3, SWT.BORDER);
		general_multiplier_mod_text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					
					default_weapon.setupCustomBuild();
					update_weapon_table(default_weapon);
				}
			}
		});
		general_multiplier_mod_text.setText("1.0");
		general_multiplier_mod_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(general_multiplier_mod_text, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Label lblAdditiveCd = new Label(composite_3, SWT.NONE);
		formToolkit.adapt(lblAdditiveCd, true, true);
		lblAdditiveCd.setText("Additive CD");
		
		additive_crit_damage_text = new Text(composite_3, SWT.BORDER);
		additive_crit_damage_text.setText("0.0");
		additive_crit_damage_text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(additive_crit_damage_text, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Button btnSaveBuild = new Button(composite_3, SWT.NONE);
		GridData gd_btnSaveBuild = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveBuild.widthHint = 77;
		btnSaveBuild.setLayoutData(gd_btnSaveBuild);
		btnSaveBuild.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				weaponCombo.select(0);
				
		        Object obj = null;
				try {
					obj = new JSONParser().parse(new FileReader("weapons-custom.json"));
				} catch (IOException | ParseException e1) {
					e1.printStackTrace();
				} 
		          
		        // typecasting obj to JSONObject 
		        JSONObject jo = (JSONObject) obj;
		        
		        //Check if name already exists
		        Map<String,Object> data = (Map<String,Object>)jo; 
		        String name = txtBuildName.getText();
		        if(data.get(name) != null) {
					name += "0"; // append character if name exists
		        }
		        
		        JSONObject mods = new JSONObject();
		        mods.put("Damage", damage_mod_text.getText());
		        mods.put("Bane", bane_mod_text.getText());
		        mods.put("CriticalChance", crit_chance_mod_text.getText());
		        mods.put("CriticalDamage", crit_damage_mod_text.getText());
		        mods.put("Multishot", multishot_mod_text.getText());
		        mods.put("StatusChance", status_chance_mod_text.getText());
		        mods.put("Cold", cold_mod_text.getText());
		        mods.put("Toxin", toxin_mod_text.getText());
		        mods.put("Heat", heat_mod_text.getText());
		        mods.put("Electricity", electricity_mod_text.getText());
		        mods.put("FireRate", fire_rate_mod_text.getText());
		        mods.put("BerserkFireRate", multiplicative_firerate_mod_text.getText());
		        mods.put("Magazine", magazine_mod_text.getText());
		        mods.put("Reload", reload_mod_text.getText());
		        mods.put("Impact", impact_mod_text.getText());
		        mods.put("Puncture", puncture_mod_text.getText());
		        mods.put("Slash", slash_mod_text.getText());
		        mods.put("StatusDuration", status_duration_mod_text.getText());
		        
		        mods.put("GeneralMultiplier", general_multiplier_mod_text.getText());
		        mods.put("AdditiveCriticalDamage", additive_crit_damage_text.getText());
		        
		        mods.put("HunterMunitions", btnHunterMunitions.getSelection());
		        mods.put("ShatteringImpact", btnShatteringImpact.getSelection());
		        mods.put("AmalgamShatteringImpact", btnAmalgamShatImp.getSelection());
		        mods.put("PrimedChamber", btnPrimedchamber.getSelection());
		        
		        JSONObject elemental_combo = new JSONObject();
		        elemental_combo.put("Corrosive", btnCorrosive.getSelection());
		        elemental_combo.put("Magnetic", btnMagnetic.getSelection());
		        elemental_combo.put("Radiation", btnRadiation.getSelection());
		        elemental_combo.put("Blast", btnBlast.getSelection());
		        elemental_combo.put("Viral", btnViral.getSelection());
		        elemental_combo.put("Gas", btnGas.getSelection());
		        
		        JSONObject weapon_config = new JSONObject();
		        weapon_config.put("WeaponName", weaponListCombo.getText());
		        weapon_config.put("FireMode", fireModeCombo.getText());
		        weapon_config.put("Stance", stance_combo.getText());
		        weapon_config.put("MoveSet", move_combo.getText());
		        //weapon_config.put("MoveSet", move_combo.getText());
		        
		        JSONObject container = new JSONObject();
		        container.put("Mods", mods);
		        container.put("ElementalCombo", elemental_combo);
		        container.put("WeaponConfig", weapon_config);
		        
		        data.put(name, container);
				
		        try (FileWriter file = new FileWriter("weapons-custom.json")) { 
		            file.write(jo.toJSONString());
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }

				weaponCombo.add(name);
				
			}
		});
		formToolkit.adapt(btnSaveBuild, true, true);
		btnSaveBuild.setText("Save Build");
		
		txtBuildName = new Text(composite_3, SWT.BORDER);
		txtBuildName.setText("Build Name");
		txtBuildName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 4, 1));
		formToolkit.adapt(txtBuildName, true, true);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Button btnClearConfig = new Button(composite_3, SWT.NONE);
		btnClearConfig.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnClearConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear_mod_setup();
				weaponCombo.select(0);
				default_weapon.setupCustomBuild();
				update_weapon_table(default_weapon);
			}
		});
		formToolkit.adapt(btnClearConfig, true, true);
		btnClearConfig.setText("Clear Config");
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Button btnGetBestRiven = new Button(composite_3, SWT.NONE);
		btnGetBestRiven.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnGetBestRiven.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),lvl_spinner.getSelection(), armorReduct ,default_weapon);
				find_best_riven_stat( default_weapon,  default_enemy, 3);
			}
		});
		formToolkit.adapt(btnGetBestRiven, true, true);
		btnGetBestRiven.setText("Get best riven");
		
		styledText = new StyledText(composite_3, SWT.BORDER);
		GridData gd_styledText = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_styledText.heightHint = 50;
		styledText.setLayoutData(gd_styledText);
		formToolkit.adapt(styledText);
		formToolkit.paintBordersFor(styledText);
		new Label(composite_3, SWT.NONE);
		new Label(composite_3, SWT.NONE);
		
		Button btnClearRiven = new Button(composite_3, SWT.NONE);
		btnClearRiven.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				clear_riven_selection();
			}
		});
		btnClearRiven.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(btnClearRiven, true, true);
		btnClearRiven.setText("Clear Riven");
		
		weapon_table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		weapon_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		weapon_table.setBounds(589, 35, 305, 788);
		formToolkit.adapt(weapon_table);
		formToolkit.paintBordersFor(weapon_table);
		weapon_table.setHeaderVisible(true);
		weapon_table.setLinesVisible(true);
		
		tblclmnStat = new TableColumn(weapon_table, SWT.NONE);
		tblclmnStat.setWidth(156);
		tblclmnStat.setText("");
		
		TableColumn tblclmnNewColumn = new TableColumn(weapon_table, SWT.NONE);
		tblclmnNewColumn.setWidth(141);
		tblclmnNewColumn.setText("");
		
		secondary_effects_combo = new Combo(shell, SWT.NONE);
		secondary_effects_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				update_weapon_table(default_weapon);
			}
		});
		secondary_effects_combo.setBounds(746, 10, 148, 23);
		formToolkit.adapt(secondary_effects_combo);
		formToolkit.paintBordersFor(secondary_effects_combo);
		secondary_effects_combo.setText("Secondary Effects");

		default_weapon = new Weapon(weaponCombo.getText(), true);
		default_enemy = new Enemy(enemy_combo.getText(),165, 1, default_weapon);
		populate_build_combo(weaponCombo);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		damage_list = new org.eclipse.swt.widgets.List(composite, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_damage_list = new GridData(SWT.LEFT, SWT.CENTER, false, false, 7, 5);
		gd_damage_list.heightHint = 213;
		gd_damage_list.widthHint = 348;
		damage_list.setLayoutData(gd_damage_list);
		formToolkit.adapt(damage_list, true, true);
		damage_list.setVisible(false);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		debug_checkbutton = new Button(composite, SWT.CHECK);
		debug_checkbutton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(debug_checkbutton.getSelection())
					damage_list.setVisible(true);
				else {
					damage_list.setVisible(false);
				}
			}
		});
		formToolkit.adapt(debug_checkbutton, true, true);
		debug_checkbutton.setText("Damage Debug");
		

		
		tableItemName = new TableItem(weapon_table, SWT.NONE);
		tableItem = new TableItem(weapon_table, SWT.NONE);
		tableItem1 = new TableItem(weapon_table, SWT.NONE);
		tableItem2 = new TableItem(weapon_table, SWT.NONE);
		tableItem3 = new TableItem(weapon_table, SWT.NONE);
		tableItem4 = new TableItem(weapon_table, SWT.NONE);
		tableItem5 = new TableItem(weapon_table, SWT.NONE);
		tableItem6 = new TableItem(weapon_table, SWT.NONE);
		tableItem7 = new TableItem(weapon_table, SWT.NONE);
		tableItem8 = new TableItem(weapon_table, SWT.NONE);
		tableItem9 = new TableItem(weapon_table, SWT.NONE);
		tableItem10 = new TableItem(weapon_table, SWT.NONE);
		tableItem11 = new TableItem(weapon_table, SWT.NONE);
		tableItem12 = new TableItem(weapon_table, SWT.NONE);
		tableItem13 = new TableItem(weapon_table, SWT.NONE);
		tableItem14 = new TableItem(weapon_table, SWT.NONE);
		tableItem15 = new TableItem(weapon_table, SWT.NONE);
		tableItem16 = new TableItem(weapon_table, SWT.NONE);
		tableItem17 = new TableItem(weapon_table, SWT.NONE);
		tableItem18 = new TableItem(weapon_table, SWT.NONE);
		tableItem19 = new TableItem(weapon_table, SWT.NONE);
		tableItem20 = new TableItem(weapon_table, SWT.NONE);
		tableItem21 = new TableItem(weapon_table, SWT.NONE);
		tableItem22 = new TableItem(weapon_table, SWT.NONE);
		tableItem23 = new TableItem(weapon_table, SWT.NONE);
		tableItem24 = new TableItem(weapon_table, SWT.NONE);
		
		//weapon_table.getItem(24).setForeground(red);
		tableItem24.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tableItem25 = new TableItem(weapon_table, SWT.NONE);
		tableItem26 = new TableItem(weapon_table, SWT.NONE);
		tableItem27 = new TableItem(weapon_table, SWT.NONE);
		tableItem28 = new TableItem(weapon_table, SWT.NONE);
		tableItem29 = new TableItem(weapon_table, SWT.NONE);
		tableItem30 = new TableItem(weapon_table, SWT.NONE);
		tableItem31 = new TableItem(weapon_table, SWT.NONE);
		tableItem32 = new TableItem(weapon_table, SWT.NONE);
		tableItem33 = new TableItem(weapon_table, SWT.NONE);
		tableItem34 = new TableItem(weapon_table, SWT.NONE);
		tableItem35 = new TableItem(weapon_table, SWT.NONE);
		tableItem36 = new TableItem(weapon_table, SWT.NONE);

		
		enemy_table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		enemy_table.setBounds(589, 829, 305, 122);
		formToolkit.adapt(enemy_table);
		formToolkit.paintBordersFor(enemy_table);
		enemy_table.setHeaderVisible(true);
		enemy_table.setLinesVisible(true);
		
		TableColumn tblclmnEnemyStat = new TableColumn(enemy_table, SWT.NONE);
		tblclmnEnemyStat.setWidth(72);
		tblclmnEnemyStat.setText("Enemy Stat");
		
		TableColumn tblclmnBaseValue = new TableColumn(enemy_table, SWT.NONE);
		tblclmnBaseValue.setWidth(75);
		tblclmnBaseValue.setText("Base Value");
		
		TableColumn tblclmnValue = new TableColumn(enemy_table, SWT.NONE);
		tblclmnValue.setWidth(81);
		tblclmnValue.setText("Scaled Value");
		
		tblclmnType = new TableColumn(enemy_table, SWT.NONE);
		tblclmnType.setWidth(70);
		tblclmnType.setText("Type");
		
		
		tableItem_1 = new TableItem(enemy_table, SWT.NONE);
		tableItem_1.setText(new String[] {"Health", String.format("%,.0f", default_enemy.baseHealth), String.format("%,.0f", default_enemy.getHealthRemaining()), default_enemy.healthType});
		
		tableItem_2 = new TableItem(enemy_table, SWT.NONE);
		tableItem_2.setText(new String[] {"Armor", String.format("%,.0f", default_enemy.baseArmor), String.format("%,.0f", default_enemy.getArmorRemaining()), default_enemy.armorType});
		
		tableItem_3 = new TableItem(enemy_table, SWT.NONE);
		tableItem_3.setText(new String[] {"Shield", String.format("%,.0f", default_enemy.baseShield), String.format("%,.0f", default_enemy.getShieldRemaining()), default_enemy.shieldType});
		
		tableItem_4 = new TableItem(enemy_table, SWT.NONE);
		tableItem_4.setText(new String[] {"EHP","",String.format("%,.0f", default_enemy.ehp()),""});

		ui_set_up = true;
		gui_setup = true;
		combo_is_populated = true;
		update_weapon_table(default_weapon);
	}
	
	@SuppressWarnings("unchecked")
	private void populate_weapon_combo(Combo weaponListCombo, Weapon dw) {
		weaponListCombo.removeAll();
		
		ArrayList<String> init_wL = new ArrayList<String>();

		try {
			init_wL = Weapon.parseWeaponList();
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}

		for(int i =0; i<init_wL.size();i++) {
			weaponListCombo.add(init_wL.get(i));
		}
			
		weaponListCombo.select(0);
		
		fireModeCombo.removeAll();
		fireModeCombo.add("Primary");
		
        // typecasting obj to JSONObject 
		Object nobj = null;
		try {
			nobj = new JSONParser().parse(new FileReader("weapons.json"));
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
        JSONObject jo = (JSONObject) nobj; 
          
        // getting address 
        Map<String,Object> weapons = (Map<String,Object>)jo;
        Map<String,Object> selectedWep =(Map<String,Object>)weapons.get(weaponListCombo.getText());
        Map<String,Object> otherFireModes =(Map<String,Object>)selectedWep.get("OtherFireModes");
        
        if(selectedWep != null) {
	        ArrayList<String> attackModes = new ArrayList<>();
	        try {
		        Iterator<Map.Entry<String,Object>> itr1 = otherFireModes.entrySet().iterator(); 
		        while (itr1.hasNext()) { 
	
		            Map.Entry<String,Object> pair = itr1.next(); 
	            	attackModes.add((String)pair.getKey());
		        }
        	}
        	catch(Exception e) {
        	  //  Block of code to handle errors
        	}

	        
	        for(int i =0;i<attackModes.size();i++) {
	        	fireModeCombo.add(attackModes.get(i));
	        }
	        int fm_index = 0;
	        try {
	        	fm_index = MainGUI.fireModeCombo.indexOf( dw.fire_mode );
	        }
	        catch(Exception e) {
	        	fm_index = 0;
	        }
	        fireModeCombo.select(fm_index);
			//fireModeCombo.select(0);
        }
	}
	private void populate_build_combo(Combo weaponCombo) {
		ArrayList<String> init_build = new ArrayList<String>(); 
		
		try {
			init_build = Weapon.parseBuildList();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		for(int i =0; i< init_build.size(); i++) {
			weaponCombo.add(init_build.get(i));
		}
		weaponCombo.select(0);

	}
	private void populate_enemy_combo(Combo enemy_combo) {
		
		ArrayList<String> init_e = Enemy.getEnemyNames();
		
		for(int i =0; i<init_e.size();i++) {
			enemy_combo.add(init_e.get(i));
		}
		
		enemy_combo.select(0);
	}
	static void populate_stance_combo(Weapon weapon) {
		stance_combo.add("None");
		stance_combo.removeAll();

		ArrayList<String> init_stance = new ArrayList<String>(); 

		try {
			init_stance = weapon.parseStanceList();
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		
		
		stance_combo.add("None");
		for(int i =0; i<init_stance.size();i++) {
			stance_combo.add(init_stance.get(i));
		}
		stance_combo.select(0);
	}

	@SuppressWarnings("unchecked")
	static void setup_move_combo(String stance_name, Weapon dw) {
		ArrayList<String> moveList = new ArrayList<String>();
		move_combo.removeAll();
		Object s_obj = null;
		try {
			s_obj = new JSONParser().parse(new FileReader("stances.json"));
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
		}
        JSONObject jo = (JSONObject) s_obj; 
        // getting address 
        Map<String,Object> stances = (Map<String,Object>)jo.get(stance_name);
        Map<String,Object> moveset = (Map<String,Object>)stances.get("ComboName");
		for (Map.Entry<String, Object> entry : moveset.entrySet()) {
        	moveList.add((String)entry.getKey());
        }
		for(int i =0; i<moveList.size();i++) {
			move_combo.add(moveList.get(i));
		}
		int move_index = move_combo.indexOf( dw.move_set );
		move_combo.select(move_index);
	}
	
	private void parse_stance(String stance_name) {
		Object s_obj = null;
		try {
			s_obj = new JSONParser().parse(new FileReader("stances.json"));
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
		}
        JSONObject jo = (JSONObject) s_obj; 	
        JSONObject selected_stance = (JSONObject)jo.get(stance_name);	
        JSONObject combos = (JSONObject)selected_stance.get("ComboName");
        JSONObject selected_combo = (JSONObject)combos.get(move_combo.getText());
        
        int len = 1;
        
        stance_multipliers = new ArrayList<Double>();     
        JSONArray jsonArray = (JSONArray)selected_combo.get("Multipliers"); 
        if (jsonArray != null) { 
           len = jsonArray.size();
           for (int i=0;i<len;i++){ 
        	   stance_multipliers.add(string_to_double(jsonArray.get(i).toString()));
           } 
        } 
        stance_procs = new ArrayList<Integer>();     
        JSONArray prcs = (JSONArray)selected_combo.get("Procs"); 
        if (prcs != null) { 
           len = prcs.size();
           for (int i=0;i<len;i++){ 
        	   stance_procs.add(Integer.parseInt(prcs.get(i).toString()));
           } 
        } 
        
        melee_time = string_to_double(selected_combo.get("Time").toString())/len;
        
	}
	
	private double[] simulate(Enemy enemy, Weapon weapon, int numSim) {
		weapon.setupCustomBuild();
		enemy = new Enemy((String)enemy_combo.getText(),enemy.level, armorReduct,default_weapon);
		
		damage_list.removeAll();
		
		double meanTime = 0;
		double meanShots = 0;
		double varTime = 0;
		double varShots = 0;
		
		double sum_t = 0;
		double sum_t2 = 0;
		double sum_s = 0;
		double sum_s2 = 0;
		
		double tox_proc_dmg =0;
		double sl_proc_dmg =0;
		double heat_proc_dmg =0;		
		double electric_proc_dmg =0;
		double gas_proc_dmg =0;
		
		double oneSim[] = new double[2];
			
		parseSettings(weapon, enemy); //this is only called once
		//TODO
		//May need to set viral, headshots etc for each sendary effect? or get those stats from the parent weapon
		//weapon.set_secondary_effects();
		
		//System.out.printf("Simulating Weapon: "+weapon.name+"\n");
		
		int count=0;
		double std_shots =0;
		double std_time = 0;
		int newCount=0;
		int thresh = 20;
		while(count<numSim) {
			oneSim = simulate_once(enemy,weapon,count);
			if(oneSim[0]>= STOP_COND) {
				double q[] = {20,20,0,0};
				return q;
			}
				
			sum_t +=oneSim[0];
			sum_t2 += Math.pow(oneSim[0], 2);
			
			sum_s += oneSim[1];
			sum_s2 += Math.pow(oneSim[1], 2);
			
			tox_proc_dmg += enemy.totToxDotDmg;
			sl_proc_dmg += enemy.totTrueDotDmg;
			heat_proc_dmg += enemy.totHeatDotDmg;
			electric_proc_dmg += enemy.totElectricDotDmg;
			gas_proc_dmg += enemy.totGasDotDmg;

			count++;
			//progressBar.setSelection( count % (progressBar.getMaximum() + 1) );
		}

			
		meanTime = sum_t/count;
		meanShots = sum_s/count;
		varTime = sum_t2/count - Math.pow(meanTime, 2);
		varShots = sum_s2/count - Math.pow(meanShots, 2);
		
		std_shots = Math.pow(varShots, 0.5);
		std_time = Math.pow(varTime, 0.5);
		newCount =(int)Math.pow(std_time/0.05, 2);
		
		
		if(newCount > numSim && numSim != 1) {
			if( newCount > 300) {
				newCount = 300;
			}
			count = 0;
			while(count<(newCount-numSim)) {
				oneSim = simulate_once(enemy,weapon,count);
				if(oneSim[0]>= STOP_COND)
					break;
				sum_t +=oneSim[0];
				sum_t2 += Math.pow(oneSim[0], 2);
				
				sum_s += oneSim[1];
				sum_s2 += Math.pow(oneSim[1], 2);
				
				tox_proc_dmg += enemy.totToxDotDmg;
				sl_proc_dmg += enemy.totTrueDotDmg;
				heat_proc_dmg += enemy.totHeatDotDmg;
				electric_proc_dmg += enemy.totElectricDotDmg;
				gas_proc_dmg += enemy.totGasDotDmg;

				count++;
				//progressBar.setSelection( count % (progressBar.getMaximum() + 1) );
			}
			
			meanTime = sum_t/newCount;
			meanShots = sum_s/newCount;
			varTime = sum_t2/newCount - Math.pow(meanTime, 2);
			varShots = sum_s2/newCount - Math.pow(meanShots, 2);
			thresh = newCount;
			//System.out.printf("Number of iterations: "+newCount+"\n");

		}
		else {
			thresh = numSim;
			//System.out.printf("Number of iterations: "+numSim+"\n");
			
		}
		//progressBar.setSelection( 0 );
		tox_proc_dmg = tox_proc_dmg/thresh;
		sl_proc_dmg = sl_proc_dmg/thresh;
		heat_proc_dmg = heat_proc_dmg/thresh;
		electric_proc_dmg = electric_proc_dmg/thresh;
		gas_proc_dmg = gas_proc_dmg/thresh;

		enemy.reset(1);
		tableItem25.setText(new String[] {"Ammo Efficiency",String.format("%,.1f", meanShots*weapon.ammoCost*100/(double)weapon.base_ammo)+"% of max ammo"});
		tableItem24.setText(new String[] {"DPS",String.format("%,.0f", (enemy.getHealthRemaining() + enemy.getShieldRemaining())/meanTime)});
		
		tableItem27.setText(new String[] {"Slash Proc", String.format("%,.1f",sl_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem28.setText(new String[] {"Toxin Proc", String.format("%,.1f",tox_proc_dmg*100/enemy.getHealthRemaining())+"%"});
		tableItem29.setText(new String[] {"Heat Proc", String.format("%,.1f",heat_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem30.setText(new String[] {"Electric Proc", String.format("%,.1f",electric_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem31.setText(new String[] {"Gas Proc", String.format("%,.1f",gas_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		
		std_shots = Math.pow(varShots, 0.5);
		std_time = Math.pow(varTime, 0.5);
		double results[] = {meanTime,meanShots,std_time,std_shots};
		return results;
	}
	private double[] simulate_once(Enemy enemy, Weapon weapon, int iteration) {
		double result[] = new double[2];
		int millis = 0;
		int shots= 0;
		double critMultiplier;
		Weapon cur_effect = null;
		
		enemy.reset(armorReduct);
		weapon.fo.reset();
		weapon.reset_secondary_effects();
		
		int prevHP = (int)enemy.getHealthRemaining();
		int curHP = prevHP;
		int cur_shield = (int)enemy.getShieldRemaining();
		int prev_shield = (int)enemy.getShieldRemaining();
		
		int prev_armor = (int)(enemy.getArmorRemaining());
		int cur_armor = prev_armor;
		
		
		int num_combo_attacks = stance_multipliers.size();
		int combo_count=0;
		boolean force_slash = false;
		
		double crit_roll;
		
		if(graph) {
			//series.add(millis/1000.0, ehp);
			health_series.add(millis/1000.0, curHP);
			shield_series.add(millis/1000.0, cur_shield);
			armor_series.add(millis/1000.0, enemy.getArmorRemaining());
			//System.out.println(millis*1000);
		}
		cur_effect = weapon;
		double next_event = cur_effect.fo.get_event_time();
		
		while(enemy.getHealthRemaining() > 0){ 
			
			int[] event_index = new int[] {1000000,1000000,1000000,1000000,1000000};
			
			event_index[0] = (int)(next_event*1000);
		
			Dot next_dot = enemy.slQ.peekFirst();
			
			if(enemy.toxQ.peekFirst() != null && enemy.toxQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.toxQ.peekFirst();
			}
			if(enemy.elecQ.peekFirst() != null && enemy.elecQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.elecQ.peekFirst();
			}
			if(enemy.viralQ.peekFirst() != null && enemy.viralQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.viralQ.peekFirst();
			}
			if(enemy.corrosiveQ.peekFirst() != null && enemy.corrosiveQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.corrosiveQ.peekFirst();
			}
			if(enemy.magneticQ.peekFirst() != null && enemy.magneticQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.magneticQ.peekFirst();
			}
			if(enemy.gasQ.peekFirst() != null && enemy.gasQ.peekFirst().getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.gasQ.peekFirst();
			}
			if(enemy.heatDot.getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.heatDot;
			}
			if(enemy.heat_dot_armor_reduction.getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.heat_dot_armor_reduction;
			}
			if(enemy.heat_dot_armor_regeneration.getOffset() < get_safe_offset(next_dot)) {
				next_dot = enemy.heat_dot_armor_regeneration;
			}
			
			if(next_dot != null) {
				event_index[1] = (int)next_dot.getOffset();
			}
			else {
				event_index[1] = 1000000;
			}
			
			event_index[2] = Enemy.electricity_offset;
			event_index[3] = Enemy.gas_offset;
			event_index[4] = Enemy.heat_offset;
			
			
			int mindex = get_min_index(event_index);
			//System.out.println(mindex);
			
			//Weapon damage event
			if(mindex == 0) {
				
				//System.out.println(next_event);
				
				millis = (int)(next_event*1000);
				
				if(combo_count < num_combo_attacks) {
					weapon.melee_multiplier = stance_multipliers.get(combo_count);
				}else {
					combo_count =0;
					weapon.melee_multiplier = stance_multipliers.get(combo_count);
				}
				if(stance_procs.get(combo_count) == 1) {
					force_slash =true;
				}else {
					force_slash = false;
				}
				combo_count++;
						
				int numPellets;
				
				if(rMulti.nextDouble() <= cur_effect.getMultiShotChance()){
					numPellets = (int)(cur_effect.pellet)+1;								
				}else{
					numPellets = (int)(cur_effect.pellet);								
				}
				if(cur_effect.shot_type.equals("HELD")) {
					if(rMulti.nextDouble() <= cur_effect.beam_multishot_chance){
						cur_effect.beam_multishot_multiplier = (int)(cur_effect.beam_multishot) + 1;								
					}else{
						cur_effect.beam_multishot_multiplier = (int)(cur_effect.beam_multishot);								
					}
				}
				
				for(int i = 0; i < numPellets; i++){
					
					crit_roll = rCrit.nextDouble();
					critMultiplier = weapon.crit_inst.roll_crit(crit_roll, enemy, weapon.headshot);
					enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()), critMultiplier, false);

					//Crit
					if(weapon.high_crit_tier>1) {
						enemy.applyProc(cur_effect, true, critMultiplier, millis, rStatus.nextDouble(), force_slash);
					}
					//Crit
					else if(crit_roll - weapon.high_crit_tier_chance <= 0){
						enemy.applyProc(cur_effect, true, critMultiplier, millis, rStatus.nextDouble(), force_slash);
					}
					//No crit
					else {
						enemy.applyProc(cur_effect, false, critMultiplier, millis, rStatus.nextDouble(), force_slash);
					}
					
					
					if(enemy.shattering_impact) {
						if(enemy.modifiedBaseArmor<=0) {
							enemy.modifiedBaseArmor=0;
							enemy.setArmor(0);
						}else {
							enemy.modifiedBaseArmor-=6;
							enemy.setArmor(enemy.armorScale(enemy.modifiedBaseArmor, enemy.level, enemy.baseLevel)*enemy.armorReduct*enemy.getCorrosiveReduction()*enemy.getHeatArmorReduction());
						}
						
					}
					if(enemy.amalgam_shattering_impact) {
						if(enemy.modifiedBaseArmor <= 0) {
							enemy.modifiedBaseArmor = 0;
							enemy.setArmor(0);
						}else {
							enemy.modifiedBaseArmor-=6;
							enemy.setArmor(enemy.armorScale(enemy.modifiedBaseArmor, enemy.level, enemy.baseLevel)*enemy.armorReduct*enemy.getCorrosiveReduction()*enemy.getHeatArmorReduction());
						}
					}
				}	
				
				shots++;
				weapon.primed_chamber_multiplier=1;

				//Set the time of the next event for this effect
				cur_effect.fo.increment();
				//find next scheduled effect to be completed
				cur_effect = weapon.get_next_effect();
				next_event = cur_effect.fo.get_event_time();
				
			}
			//Proc event
			else if(mindex == 1) {
				millis = (int)next_dot.getOffset();
				next_dot.execute();
			}
			//Electric DOT event
			else if(mindex == 2) {
				millis = (int)Enemy.electricity_offset;
				enemy.elecQ.peekFirst().electric_trigger();
			}
			//Gas DOT event
			else if(mindex == 3) {
				millis = (int)Enemy.gas_offset;
				enemy.gasQ.peekFirst().gas_trigger();
			}
			//heat DOT event
			else if(mindex == 4) {
				millis = (int)Enemy.heat_offset;
				enemy.heatDot.heat_trigger();
			}
				
			curHP = (int)(enemy.getHealthRemaining());
			cur_armor = (int)(enemy.getArmorRemaining());
			cur_shield = (int)enemy.getShieldRemaining();
			
			//System.out.printf("Time: %d, Prev: %d, Cur: %d\n",millis, prevHP, curHP);
			//System.out.printf("Millis: %d, Dmg: %d\n", millis, prevHP-curHP);
			
			if(graph) {
				if(prev_shield != cur_shield) {
					shield_series.add(millis/1000.0, prev_shield);
					shield_series.add(millis/1000.0, cur_shield);
					prev_shield = cur_shield;
				}
				if(prevHP != curHP) {
					health_series.add(millis/1000.0, prevHP);
					health_series.add(millis/1000.0, curHP);
					prevHP = curHP;
				}
				
				if(prev_armor != cur_armor) {
					armor_series.add(millis/1000.0, prev_armor);
					armor_series.add(millis/1000.0, cur_armor);
					prev_armor = cur_armor;
				}
			}
			
			//taking longer than 20s
			if (millis > 20000 || shots > 1000){
				break;
			}
		}
		
		result[0] = millis/1000.0;
		result[1] = (double)shots;
		
		return result;
	}
	public int get_min_index(int[] a1) {
		int sz = a1.length;
		int min = a1[0];
		int mindex = 0;
		for(int i =0;i<sz;i++) {
			if(a1[i]< min) {
				min = a1[i];
				mindex = i;
			}
		}
		return mindex;
	}
	public double get_safe_offset(Dot d) {
		double o = 0;
		if(d == null) {
			o =100000000;
		}
		else {
			o = d.getOffset();
		}
		return o;
		
	}

	private void parseSettings(Weapon weapon, Enemy enemy) {
		if(btnFullViral.getSelection())
			enemy.setViralMult(4.25);
		if(btnFullMagnetic.getSelection())
			enemy.setMagneticMult(4.25);
		
		enemy.health_scale = spinner_to_double(health_scale_spinner);
		enemy.armor_scale = spinner_to_double(armor_scale_spinner);
		enemy.shield_scale = spinner_to_double(shield_scale_spinner);
		
		
		/*
		enemy.setHealth(enemy.getHealthRemaining()*enemy.health_scale);
		enemy.setArmor(enemy.getArmorRemaining()*enemy.armor_scale);
		enemy.scaled_armor = enemy.getArmorRemaining();
		enemy.setShield(enemy.getShieldRemaining()*enemy.shield_scale);
		*/
		enemy.reset(enemy.armorReduct);
		enemy.scaled_armor = enemy.getArmorRemaining();
		
		enemy.resist(spinner_to_double(resist_spinner));
		
		boolean hunterMunitions = btnHunterMunitions.getSelection();
		enemy.shattering_impact = btnShatteringImpact.getSelection();
		enemy.amalgam_shattering_impact = btnAmalgamShatImp.getSelection();

		weapon.headshot = btnHeadshots.getSelection();
		if(weapon.headshot) {
			weapon.headshot_multiplier = 2;
		}
		
		weapon.setHunter(hunterMunitions);
		
		//TODO remove cd manipulation
		if(enemy.acolyte) {
			//weapon.critMultiplier = (weapon.critMultiplier-1)*0.5+1;
			weapon.viralChance = 0;
		}
			
		
		armorReduct = (100-armor_strip_spinner.getSelection()/(Math.pow(10, armor_strip_spinner.getDigits())))/100.0;
			 
	}
	
	
	private void update_enemy_table(Enemy enemy) {
		enemy_table.clearAll();
		
		//tableItem_1 = new TableItem(enemy_table, SWT.NONE);
		tableItem_1.setText(new String[] {"Health", String.format("%,.0f", enemy.baseHealth), String.format("%,.0f", enemy.getHealthRemaining()), enemy.healthType});
		
		//tableItem_2 = new TableItem(enemy_table, SWT.NONE);
		tableItem_2.setText(new String[] {"Armor", String.format("%,.0f", enemy.baseArmor), String.format("%,.0f", enemy.getArmorRemaining()), enemy.armorType});
		
		//tableItem_3 = new TableItem(enemy_table, SWT.NONE);
		tableItem_3.setText(new String[] {"Shield", String.format("%,.0f", enemy.baseShield), String.format("%,.0f", enemy.getShieldRemaining()), enemy.shieldType});
		
		tableItem_4.setText(new String[] {"EHP","",String.format("%,.0f", enemy.ehp()),""});
		
		tblclmnType.setToolTipText(get_weakness_string(enemy));
		
	}
	
	public String get_weakness_string(Enemy enemy) {
		
		String s = "";
		s += "		Health		Armor		Shield\n";
		for(int i =0; i< 13; i++) {
			String elem = get_type(i);
			s += elem;
			if(elem.length() > 6) 
				s += ":	";
			else
				s += ":		";
			s += enemy.health_multipliers[i];
			s += "		";
			s += enemy.armor_multipliers[i];
			s += "		";
			s += enemy.shield_multipliers[i];
			s += "\n";
		}
		
		return s;
	}
	
	public static String get_type(int s) {
	    if (s == 0)
	        return "impact";
	    else if (s == 1)
	        return "puncture";
	    else if (s == 2)
	        return "slash";
	    else if (s == 3)
	        return "heat";
	    else if (s == 4)
	        return "cold";
	    else if (s == 5)
	        return "electricity";
	    else if (s == 6)
	        return "toxin";
	    else if (s == 7)
	        return "blast";
	    else if (s == 8)
	        return "radiation";
	    else if (s == 9)
	        return "gas";
	    else if (s == 10)
	        return "magnetic";
	    else if (s == 11)
	        return "viral";
	    else if (s == 12)
	        return "corrosive";
	    else if (s == 13)
	        return "void";
	    else if (s == 14)
	        return "true";
	    System.out.printf("Invalid damage type name," );
	    return "true";
	}
	
	public void update_weapon_table(Weapon wep) {
		Display display = Display.getDefault();
		Font damage_font = new Font( display, "Helvetica", 11, SWT.BOLD );
		
		Weapon w = wep;
		for(int i = 0; i < wep.secondary_effects.size(); i++) {
			if(secondary_effects_combo.getText().equals(wep.secondary_effects.get(i).effect_name)) {
				w = wep.secondary_effects.get(i);
			}
		}
		
		String [][] table_string = get_table_string(w);
		
		weapon_table.clearAll();
		w.melee_multiplier = stance_multipliers.get(0);
		
		tblclmnStat.setText(weaponListCombo.getText());
		
		tableItem.setText( table_string[0] );
		tableItem1.setText( table_string[1] );
		tableItem2.setText( table_string[2] );
		
		tableItem3.setText( table_string[3] );
		tableItem4.setText( table_string[4] );
		tableItem5.setText( table_string[5] );
		tableItem6.setText( table_string[6] );
		
		tableItem7.setText( table_string[7] );
		tableItem8.setText( table_string[8] );
		tableItem9.setText( table_string[9] );
		tableItem10.setText( table_string[10] );
		tableItem11.setText( table_string[11] );
		tableItem12.setText( table_string[12] );
		
		tableItem13.setText( table_string[13] );
		tableItem14.setText( table_string[14] );
		tableItem15.setText( table_string[15] );
		tableItem16.setText( table_string[16] );
		
		tableItem17.setText( table_string[17] );
		
		tableItem18.setText( table_string[18] );
		tableItem19.setText( table_string[19] );
		tableItem20.setText( table_string[20] );
		tableItem21.setText( table_string[21] );
		tableItem22.setText( table_string[22] );
		
		
		/////////
		tableItem23.setText(new String[] {"", ""});
		
		tableItem25.setText(new String[] {"Ammo Efficiency", "Run a simulation"});
		tableItem24.setText(new String[] {"DPS", "Run a simulation"});
		

		//tableItem24.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		//tableItem24.setBackground(red);
		//tableItem24.setForeground(red);
		
		tableItem26.setText(new String[] {"", ""});
		
		tableItem27.setText(new String[] {"Slash Procs", "Run a simulation"});
		tableItem28.setText(new String[] {"Toxin Procs", "Run a simulation"});
		tableItem29.setText(new String[] {"Heat Procs", "Run a simulation"});
		tableItem30.setText(new String[] {"Electric Procs", "Run a simulation"});
		tableItem31.setText(new String[] {"Gas Procs", "Run a simulation"});

		tableItem32.setText(new String[] {"", ""});
		
		String l_shield ="";
		String l_health ="";
		String h_shield ="";
		String h_health ="";
		double shield_damage = (default_enemy.display_damage(default_enemy.array_scale(w.damage_array, w.getMultiplier()/w.beam_ramp_multiplier),w)[1]);
		double health_damage = (default_enemy.display_damage(default_enemy.array_scale(w.damage_array, w.getMultiplier()/w.beam_ramp_multiplier),w)[0]);
		

		l_shield = String.format("%,.2f", shield_damage * wep.crit_inst.roll_crit(1, default_enemy, wep.headshot));
		l_health = String.format("%,.2f", health_damage * wep.crit_inst.roll_crit(1, default_enemy, wep.headshot));
		h_shield = String.format("%,.2f", shield_damage * wep.crit_inst.roll_crit(0, default_enemy, wep.headshot));
		h_health = String.format("%,.2f", health_damage * wep.crit_inst.roll_crit(0, default_enemy, wep.headshot));
		
		int high_tier = wep.crit_inst.get_tier(0);
		int low_tier= high_tier - 1;
		
		tableItem33.setText(new String[] {"Low Crit Health Damage", l_health });
		tableItem33.setFont(1, damage_font);
		tableItem33.setForeground(1, get_crit_color(low_tier, display) );
		tableItem33.setBackground(1, get_crit_background_color(low_tier, display));
		
		tableItem34.setText(new String[] {"High Crit Health Damage", h_health });
		tableItem34.setFont(1, damage_font);
		tableItem34.setForeground(1, get_crit_color(high_tier, display) );
		tableItem34.setBackground(1, get_crit_background_color(high_tier, display));
		
		tableItem35.setText(new String[] {"Low Crit Shield Damage", l_shield });
		tableItem35.setFont(1, damage_font);
		tableItem35.setForeground(1, get_crit_color(low_tier, display) );
		tableItem35.setBackground(1, get_crit_background_color(low_tier, display));
		
		tableItem36.setText(new String[] {"High Crit Shield Damage", h_shield });
		tableItem36.setFont(1, damage_font);
		tableItem36.setForeground(1, get_crit_color(high_tier, display) );
		tableItem36.setBackground(1, get_crit_background_color(high_tier, display));
				
	}
	Color get_crit_color(int tier, Display display) {
		
		Color red = display.getSystemColor(SWT.COLOR_RED);
		Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		Color orange = new Color( display, new RGB( 255, 102, 0 ) );
		
		Color result = white;
		
		if(tier == 0) {
			result = white;
		}
		else if(tier == 1) {
			result = yellow;
		}
		else if(tier == 2) {
			result = orange;
		}
		else {
			result = red;
		}
		
		return result;
		
	}
	
	Color get_crit_background_color(int tier, Display display) {
		Color black = display.getSystemColor(SWT.COLOR_BLACK);
		Color white = display.getSystemColor(SWT.COLOR_WHITE);
		
		Color result = white;
		
		if(tier == 0) {
			result = black;
		}
		else if(tier == 1) {
			result = black;
		}
		else if(tier == 2) {
			result = white;
		}
		else {
			result = white;
		}
		
		return result;
	}
	
	String [][] get_table_string(Weapon w) {
		String [][] table_string = new String[24][];
		int index = 0;
		
		if( w.damage_array[index("impact")] != 0) {
			table_string[index] = new String[] { "Impact", format_double( w.beam_multishot * w.damage_array[index("impact")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("puncture")] != 0) {
			table_string[index] = new String[] { "puncture", format_double( w.beam_multishot * w.damage_array[index("puncture")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("slash")] != 0) {
			table_string[index] = new String[] { "slash", format_double( w.beam_multishot * w.damage_array[index("slash")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("heat")] != 0) {
			table_string[index] = new String[] { "heat", format_double( w.beam_multishot * w.damage_array[index("heat")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("cold")] != 0) {
			table_string[index] = new String[] { "cold", format_double( w.beam_multishot * w.damage_array[index("cold")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("electricity")] != 0) {
			table_string[index] = new String[] { "electric", format_double( w.beam_multishot * w.damage_array[index("electricity")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("toxin")] != 0) {
			table_string[index] = new String[] { "toxin", format_double( w.beam_multishot * w.damage_array[index("toxin")] , 3 ) };
			index++;
		}
		
		if( w.damage_array[index("blast")] != 0) {
			table_string[index] = new String[] { "blast", format_double( w.beam_multishot * w.damage_array[index("blast")] , 1 ) };
			index++;
		}
		if( w.damage_array[index("radiation")] != 0) {
			table_string[index] = new String[] { "radiation", format_double( w.beam_multishot * w.damage_array[index("radiation")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("gas")] != 0) {
			table_string[index] = new String[] { "gas", format_double( w.beam_multishot * w.damage_array[index("gas")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("magnetic")] != 0) {
			table_string[index] = new String[] { "magnetic", format_double( w.beam_multishot * w.damage_array[index("magnetic")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("viral")] != 0) {
			table_string[index] = new String[] { "viral", format_double( w.beam_multishot * w.damage_array[index("viral")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("corrosive")] != 0) {
			table_string[index] = new String[] { "corrosive", format_double( w.beam_multishot * w.damage_array[index("corrosive")] , 3 ) };
			index++;
		}
		if( w.damage_array[index("void")] != 0) {
			table_string[index] = new String[] { "void", format_double( w.beam_multishot * w.damage_array[index("void")] , 3 ) };
			index++;
		}
		table_string[index] = new String[] {"", ""};
		index++;
		
		table_string[index] = new String[] {"Critical Chance", format_double(w.critChance,3)};
		index++;
		table_string[index] = new String[] {"Critical Damage", format_double(w.critMultiplier, 3)};
		index++;
		table_string[index] = new String[] {"Pellets", format_double(w.pellet,3)};
		index++;
		table_string[index] = new String[] {"Status", format_double(w.status,3)};
		index++;
		
		table_string[index] = new String[] {"", ""};
		index++;
		
		
		table_string[index] = new String[] {"Reload", format_double(w.reload, 2)};
		index++;
		table_string[index] = new String[] {"Fire Rate", format_double(w.fireRate, 2)};
		index++;
		table_string[index] = new String[] {"Magazine", String.format("%,d",(w.magazine))};
		index++;
		table_string[index] = new String[] {"Ammo", String.format("%,d",(w.ammo))};
		index++;
		table_string[index] = new String[] {"Ammo Cost", format_double(w.ammoCost,2)};
		index++;
		
		for(int i = index; i< 24;i++) {
			table_string[i] = new String[] {"",""};
		}
		
		return table_string;
	}
	
	static int integerDigits(BigDecimal n) {
		return (n.signum() == 0) ? 1 : ( n.precision() - n.scale());
	}
	
	void update_table_item(TableItem t, String s, double val) {
		if(val > 0) {
			t.setText(new String[] {s, format_double(val, 3)});
		}else {
			t.setText(new String[] {"", ""});
		}
			
		
	}
	static String format_double(double d, int sig_fig) {
		
		BigDecimal bd = new BigDecimal(d);
		sig_fig = integerDigits(bd) + 2;
		bd = bd.round(new MathContext(sig_fig));
		double rounded = bd.doubleValue();
		return Double.toString(rounded);
	}
	
	static double parse_double_textbox(String r) {

		String[] s = r.split("\\$");
		double tot = 0;
		for(int i =0; i<s.length;i++) {
		    String[] arrSplit = s[i].split("\\s");
		    
		    for (int j=0; j < arrSplit.length; j++)
		    {
		      if( isNumeric(arrSplit[j]) ) {
		    	  tot += string_to_double(arrSplit[j]);
		      }
		    }
		}

	    return tot;
		
	}
	static double parse_double_multiply_textbox(String s) {
	    String[] arrSplit = s.split("\\s");
	    double tot = 1.0;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	      if( isNumeric(arrSplit[i]) ) {
	    	  tot *= Double.parseDouble(arrSplit[i]);
	      }
	    }
	    return tot;
	}
	public static boolean isNumeric(String str) { 
		  try {  
		    Double.parseDouble(str);  
		    return true;
		  } catch(NumberFormatException e){  
		    return false;  
		  }  
		}
	public static double percent_to_double(double percent, int base) {
		double res = percent/100.0 + base;
		if(res < 0) {
			res =0;
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public static void set_up_fire_mode_combo(Weapon dw) {
		fireModeCombo.removeAll();
		fireModeCombo.add("Primary");
		
        // typecasting obj to JSONObject 
        JSONObject jo = (JSONObject) wep_obj; 
  
        // getting address 
        Map<String,Object> weapons = (Map<String,Object>)jo;
        Map<String,Object> selectedWep =(Map<String,Object>)weapons.get(weaponListCombo.getText());
        
        
        if(selectedWep != null && selectedWep.get("OtherFireModes") != null) {
        	Map<String,Object> firemodes =(Map<String,Object>)selectedWep.get("OtherFireModes");
	        ArrayList<String> attackModes = new ArrayList<>();
	        Iterator<Map.Entry<String,Object>> itr1 = firemodes.entrySet().iterator(); 
	        while (itr1.hasNext()) { 

	            Map.Entry<String,Object> pair = itr1.next(); 
	            

	            attackModes.add((String)pair.getKey());
	        }
	        
	        for(int i =0;i<attackModes.size();i++) {
	        	fireModeCombo.add(attackModes.get(i));
	        }
	        int fm_index = 0;
	        try {
	        	fm_index = fireModeCombo.indexOf( dw.fire_mode );
	        }
	        catch(Exception e) {
	        	fm_index = 0;
	        }
	        fireModeCombo.select(fm_index);
        }
	}
	
	
	private static void clear_mod_setup() {
		damage_mod_text.setText( "0.0" );
		bane_mod_text.setText( "0.0" );
        crit_chance_mod_text.setText( "0.0" );
        crit_damage_mod_text.setText( "0.0" );
        multishot_mod_text.setText( "0.0" );
        status_chance_mod_text.setText( "0.0" );
        cold_mod_text.setText( "0.0" );
        toxin_mod_text.setText( "0.0" );
        heat_mod_text.setText( "0.0" );
        electricity_mod_text.setText( "0.0" );
        fire_rate_mod_text.setText( "0.0" );
        magazine_mod_text.setText( "0.0" );
        reload_mod_text.setText( "0.0" );
        impact_mod_text.setText( "0.0" );
        puncture_mod_text.setText( "0.0" );
        slash_mod_text.setText( "0.0" );
        status_duration_mod_text.setText( "0.0" );
        general_multiplier_mod_text.setText("1.0");
        additive_crit_damage_text.setText("0.0");
        
        btnCorrosive.setSelection( false );
        btnMagnetic.setSelection( false );
        btnRadiation.setSelection( false );
        btnBlast.setSelection( false );
        btnViral.setSelection( false );
        btnGas.setSelection( false );
        
        btnAmalgamShatImp.setSelection( false );
        btnPrimedchamber.setSelection( false );
        btnHunterMunitions.setSelection( false );
        btnShatteringImpact.setSelection( false );
          
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
	private void find_best_riven_stat(Weapon wep, Enemy enem, int iter) {
		
		String[] neg_stats = simulate_riven_stats( wep,  enem, wep.negative_riven_stats, 1, wep.riven_curse_possibility);
		String[] stats = simulate_riven_stats( wep,  enem, wep.riven_stats, 3,  wep.riven_buff_possibility);
				
		
		System.out.println(stats[0]);
		System.out.println(stats[1]);
		System.out.println(stats[2]);
		System.out.printf("\n");
		//styledText.setText("Best stats (higher = longer TTK)\n" + stats[0]+"\n"+stats[1]+"\n"+stats[2]);
		
		
		System.out.println(neg_stats[0]);
		
		styledText.setText("Best stats (higher = longer TTK)\n" + stats[0]+"\n"+stats[1]+"\n"+stats[2]+"\nNegative: "+neg_stats[0]);

	}
	
	private String[] simulate_riven_stats(Weapon wep, Enemy enem, double[] riven_stats, int iter, boolean[] possibilities) {
		int stat_a = -1;
		int stat_b = -1;
		int stat_c = -1;
		int cur_index =0;

		String[] stats = {"Stat A: ", "Stat B: ", "Stat C: "};
		int[] ignore_arr = new int[18];
		boolean[] check = get_riven_enable_check() ;
		
		for(int i = 0; i<iter; i++) {
			double[] times = new double[18];
			int best_index = 0;
			double min_time = 100;
			double[] res = new double[4];
			
			//for each text box, try max riven value and keep lowest time
			String prev_text = "";
			
			//No buff/negative
			res = simulate(enem, wep, 100);
			if(res[0] <= min_time) {
				min_time = res[0];
				best_index = 17;
			}
			times[17] = res[0];
	
			
			cur_index = 0;
			if(possibilities[cur_index]) {
				prev_text = damage_mod_text.getText();
				damage_mod_text.setText( damage_mod_text.getText() + " $" +Double.toString(riven_stats[0]) );
				res = simulate(enem, wep, 100);
				if(res[0] <= min_time) {
	
					if(stat_a != 0 && stat_b != 0 && ignore_arr[cur_index] != 1 && check[cur_index] != true) {
						min_time = res[0];
						best_index = 0;
					}
				}
				times[0] = res[0];
				damage_mod_text.setText(prev_text);
			}
			
			cur_index = 1;
			if(possibilities[cur_index]) {
				prev_text = bane_mod_text.getText();
		        bane_mod_text.setText( bane_mod_text.getText() + " $" +Double.toString(riven_stats[1]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 1 && stat_b != 1 && ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 1;
		        	}
				}
		        times[1] = res[0];
		        bane_mod_text.setText(prev_text);
			}
			
	        cur_index = 2;
	        if(possibilities[cur_index]) {
				prev_text = crit_chance_mod_text.getText();
		        crit_chance_mod_text.setText( crit_chance_mod_text.getText() + " $" +Double.toString(riven_stats[2]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 2 && stat_b != 2 && ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 2;
		        	}
				}
		        times[2] = res[0];
		        crit_chance_mod_text.setText(prev_text);
	        }
			
	        cur_index = 3;
	        if(possibilities[cur_index]) {
				prev_text = crit_damage_mod_text.getText();
		        crit_damage_mod_text.setText( crit_damage_mod_text.getText() + " $" +Double.toString(riven_stats[3]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 3 && stat_b != 3&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 3;
		        	}
				}
		        times[3] = res[0];
		        crit_damage_mod_text.setText(prev_text);
	        }
			
	        cur_index = 4;
	        if(possibilities[cur_index]) {
				prev_text = multishot_mod_text.getText();
		        multishot_mod_text.setText( multishot_mod_text.getText() + " $" +Double.toString(riven_stats[4]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 4 && stat_b != 4&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 4;
		        	}
				}
		        times[4] = res[0];
		        multishot_mod_text.setText(prev_text);
	        }
			
	        cur_index = 5;
	        if(possibilities[cur_index]) {
				prev_text = status_chance_mod_text.getText();
		        status_chance_mod_text.setText( status_chance_mod_text.getText() + " $" +Double.toString(riven_stats[5]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 5 && stat_b != 5&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 5;
		        	}
				}
		        times[5] = res[0];
		        status_chance_mod_text.setText(prev_text);
	        }
			
	        cur_index = 6;
	        if(possibilities[cur_index]) {
				prev_text = cold_mod_text.getText();
		        cold_mod_text.setText( cold_mod_text.getText() + " $" +Double.toString(riven_stats[6]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 6 && stat_b != 6&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 6;
		        	}
				}
		        times[6] = res[0];
		        cold_mod_text.setText(prev_text);
	        }
			
	        cur_index = 7;
	        if(possibilities[cur_index]) {
				prev_text = toxin_mod_text.getText();
		        toxin_mod_text.setText( toxin_mod_text.getText() + " $" +Double.toString(riven_stats[7]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 7 && stat_b != 7&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 7;
		        	}
				}
		        times[7] = res[0];
		        toxin_mod_text.setText(prev_text);
	        }
			
	        cur_index = 8;
	        if(possibilities[cur_index]) {
				prev_text = heat_mod_text.getText();
		        heat_mod_text.setText( heat_mod_text.getText() + " $" +Double.toString(riven_stats[8]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 8 && stat_b != 8&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 8;
		        	}
				}
		        times[8] = res[0];
		        heat_mod_text.setText(prev_text);
	        }
			
	        cur_index = 9;
	        if(possibilities[cur_index]) {
				prev_text = electricity_mod_text.getText();
		        electricity_mod_text.setText( electricity_mod_text.getText() + " $" +Double.toString(riven_stats[9]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 9 && stat_b != 9&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 9;
		        	}
				}
		        times[9] = res[0];
		        electricity_mod_text.setText(prev_text);
	        }
			
	        cur_index = 10;
	        if(possibilities[cur_index]) {
				prev_text = fire_rate_mod_text.getText();
		        fire_rate_mod_text.setText( fire_rate_mod_text.getText() + " $" +Double.toString(riven_stats[10]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 10 && stat_b != 10&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 10;
		        	}
				}
		        times[10] = res[0];
		        fire_rate_mod_text.setText(prev_text);
	        }
			
	        cur_index = 11;
	        if(possibilities[cur_index]) {
				prev_text = magazine_mod_text.getText();
		        magazine_mod_text.setText( magazine_mod_text.getText() + " $" +Double.toString(riven_stats[11]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 11 && stat_b != 11&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 11;
		        	}
				}
		        times[11] = res[0];
		        magazine_mod_text.setText(prev_text);
	        }
			
	        cur_index = 12;
	        if(possibilities[cur_index]) {
				prev_text = reload_mod_text.getText();
		        reload_mod_text.setText( reload_mod_text.getText() + " $" +Double.toString(riven_stats[12]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 12 && stat_b != 12&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 12;
		        	}
				}
		        times[12] = res[0];
		        reload_mod_text.setText(prev_text);
	        }
			
	        cur_index = 13;
	        if(possibilities[cur_index]) {
				prev_text = impact_mod_text.getText();
		        impact_mod_text.setText( impact_mod_text.getText() + " $" +Double.toString(riven_stats[13]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 13 && stat_b != 13&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 13;
		        	}
				}
		        times[13] = res[0];
		        impact_mod_text.setText(prev_text);
	        }
			
	        cur_index = 14;
	        if(possibilities[cur_index]) {
				prev_text = puncture_mod_text.getText();
		        puncture_mod_text.setText( puncture_mod_text.getText() + " $" +Double.toString(riven_stats[14]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 14 && stat_b != 14&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 14;
		        	}
				}	
		        times[14] = res[0];
		        puncture_mod_text.setText(prev_text);
	        }
			
	        cur_index = 15;
	        if(possibilities[cur_index]) {
				prev_text = slash_mod_text.getText();
		        slash_mod_text.setText( slash_mod_text.getText() + " $" +Double.toString(riven_stats[15]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 15 && stat_b != 15&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 15;
		        	}
				}
		        times[15] = res[0];
		        slash_mod_text.setText(prev_text);
	        }
			
	        cur_index = 16;
	        if(possibilities[cur_index]) {
				prev_text = status_duration_mod_text.getText();
		        status_duration_mod_text.setText( status_duration_mod_text.getText() + " $" +Double.toString(riven_stats[16]) );
		        res = simulate(enem, wep, 100);
		        if(res[0] <= min_time) {
		        	if(stat_a != 16 && stat_b != 16&& ignore_arr[cur_index] != 1&& check[cur_index] != true) {
						min_time = res[0];
						best_index = 16;
		        	}
				}
		        times[16] = res[0];
		        status_duration_mod_text.setText(prev_text);
	        }
	        
	        set_textbox_riven(wep, best_index, riven_stats);
	        if(i == 0) {
	        	stat_a = best_index;
	        }
	        else if (i == 1) {
	        	stat_b = best_index;
	        }
	        else if (i == 2) {
	        	stat_c = best_index;
	        }
	        
	        for(int j = 0; j< times.length; j++) {
	        	if( possibilities[j] && ignore_arr[j] != 1 && (times[j]-min_time)/min_time < 0.05 ) {
	        		stats[i] += (get_riv_stat_name(j) + ": " + Double.toString((int)(100 * (times[j]-min_time)/min_time)) +  "%, ");
	        		
	        	}
	        }
	        ignore_arr[best_index] = 1;
		}
		
		return stats;
	}
	private boolean[] get_riven_enable_check() {
		boolean[] check = new boolean[17];
		
		check[0] = btnCheckButton0.getSelection();
		check[1] = btnCheckButton1.getSelection();
		check[2] = btnCheckButton2.getSelection();
		check[3] = btnCheckButton3.getSelection();
		check[4] = btnCheckButton4.getSelection();
		check[5] = btnCheckButton5.getSelection();
		check[6] = btnCheckButton6.getSelection();
		check[7] = btnCheckButton7.getSelection();
		check[8] = btnCheckButton8.getSelection();
		check[9] = btnCheckButton9.getSelection();
		check[10] = btnCheckButton10.getSelection();
		check[11] = btnCheckButton11.getSelection();
		check[12] = btnCheckButton12.getSelection();
		check[13] = btnCheckButton13.getSelection();
		check[14] = btnCheckButton14.getSelection();
		check[15] = btnCheckButton15.getSelection();
		check[16] = btnCheckButton16.getSelection();
		
		return check;

	}
	private String get_riv_stat_name(int index) {
		String name = "Damage";
		if(index == 17) {
			name = "Harmless Negative";
		}
		else if(index == 0) {
			name = "Damage";
		}
		else if(index == 1) {
			name = "Bane";
		}
		else if(index == 2) {
			name = "Critical Chance";
		}
		else if(index == 3) {
			name = "Critical Damage";
		}
		else if(index == 4) {
			name = "Multishot";
		}
		else if(index == 5) {
			name = "Status Chance";
		}
		else if(index == 6) {
			name = "Cold";
		}
		else if(index == 7) {
			name = "Toxin";
		}
		else if(index == 8) {
			name = "Heat";
		}
		else if(index == 9) {
			name = "Electricity";
		}
		else if(index == 10) {
			name = "Fire Rate";
		}
		else if(index == 11) {
			name = "Magazine";
		}
		else if(index == 12) {
			name = "Reload";
		}
		else if(index == 13) {
			name = "Impact";
		}
		else if(index == 14) {
			name = "Puncture";
		}
		else if(index == 15) {
			name = "Slash";
		}
		else if(index == 16) {
			name = "Status Duration";
		}
		return name;
	}
	private void set_textbox_riven(Weapon wep, int index, double[] riven_stats) {

		if(index == 0) {
			damage_mod_text.setText( damage_mod_text.getText() + " $" +Integer.toString((int)riven_stats[0]) );
		}
		else if(index == 1) {
			bane_mod_text.setText( bane_mod_text.getText() +" $" + Integer.toString((int)riven_stats[1]) );
		}
		else if(index == 2) {
			crit_chance_mod_text.setText( crit_chance_mod_text.getText() +" $" + Integer.toString((int)riven_stats[2]) );
		}
		else if(index == 3) {
			crit_damage_mod_text.setText( crit_damage_mod_text.getText() +" $" + Integer.toString((int)riven_stats[3]) );
		}
		else if(index == 4) {
			multishot_mod_text.setText( multishot_mod_text.getText() +" $" + Integer.toString((int)riven_stats[4]) );
		}
		else if(index == 5) {
			status_chance_mod_text.setText( status_chance_mod_text.getText() +" $" + Integer.toString((int)riven_stats[5]) );
		}
		else if(index == 6) {
			cold_mod_text.setText( cold_mod_text.getText() +" $" + Integer.toString((int)riven_stats[6]) );
		}
		else if(index == 7) {
			toxin_mod_text.setText( toxin_mod_text.getText() +" $" + Integer.toString((int)riven_stats[7]) );
		}
		else if(index == 8) {
			heat_mod_text.setText( heat_mod_text.getText() +" $" + Integer.toString((int)riven_stats[8]) );
		}
		else if(index == 9) {
			electricity_mod_text.setText( electricity_mod_text.getText() +" $" + Integer.toString((int)riven_stats[9]) );
		}
		else if(index == 10) {
			fire_rate_mod_text.setText( fire_rate_mod_text.getText() +" $" + Integer.toString((int)riven_stats[10]) );
		}
		else if(index == 11) {
			magazine_mod_text.setText( magazine_mod_text.getText() +" $" + Integer.toString((int)riven_stats[11]) );
		}
		else if(index == 12) {
			reload_mod_text.setText( reload_mod_text.getText() +" $" + Integer.toString((int)riven_stats[12]) );
		}
		else if(index == 13) {
			impact_mod_text.setText( impact_mod_text.getText() +" $" + Integer.toString((int)riven_stats[13]) );
		}
		else if(index == 14) {
			puncture_mod_text.setText( puncture_mod_text.getText() +" $" + Integer.toString((int)riven_stats[14]) );
		}
		else if(index == 15) {
			slash_mod_text.setText( slash_mod_text.getText() +" $" + Integer.toString((int)riven_stats[15]) );
		}
		else if(index == 16) {
			status_duration_mod_text.setText( status_duration_mod_text.getText() +" $" + Integer.toString((int)riven_stats[16]) );
		}
		

	}
	public void clear_riven_selection() {

		damage_mod_text.setText( delete_identified_word(damage_mod_text.getText()) );

		bane_mod_text.setText( delete_identified_word(bane_mod_text.getText()) );

		crit_chance_mod_text.setText( delete_identified_word(crit_chance_mod_text.getText()) );

		crit_damage_mod_text.setText( delete_identified_word(crit_damage_mod_text.getText()) );

		multishot_mod_text.setText( delete_identified_word(multishot_mod_text.getText()) );

		status_chance_mod_text.setText( delete_identified_word(status_chance_mod_text.getText()) );

		cold_mod_text.setText( delete_identified_word(cold_mod_text.getText()) );
	
		toxin_mod_text.setText( delete_identified_word(toxin_mod_text.getText()) );
	
		heat_mod_text.setText( delete_identified_word(heat_mod_text.getText()));
	
		electricity_mod_text.setText( delete_identified_word(electricity_mod_text.getText()) );
	
		fire_rate_mod_text.setText( delete_identified_word(fire_rate_mod_text.getText()) );
	
		magazine_mod_text.setText( delete_identified_word( magazine_mod_text.getText()) );
	
		reload_mod_text.setText( delete_identified_word(reload_mod_text.getText()));
	
		impact_mod_text.setText( delete_identified_word(impact_mod_text.getText()) );
	
		puncture_mod_text.setText( delete_identified_word(puncture_mod_text.getText()) );
	
		slash_mod_text.setText( delete_identified_word( slash_mod_text.getText()) );
	
		status_duration_mod_text.setText( delete_identified_word(status_duration_mod_text.getText()) );
		
	}
	public String delete_identified_word(String s) {
		String res = "";
		String[] spl = s.split("\\s");
		
		for(int i =0;i<spl.length;i++) {
			if(!spl[i].contains("$") && !spl[i].equals("")) {
				res += (" " +spl[i]);
			}
		}
		return res;
		
	}
	public static void add_non_duplicate_string_to_list(String s, org.eclipse.swt.widgets.List l) {
		for(int i =0; i<l.getItemCount();i++) {
			if(l.getItem(i).equals(s)) {
				return;
			}
		}
		damage_list.add(s);
	}
	
	public static double string_to_double(String s) {
		Number number = null;
		try {
			number = format.parse(s);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			if(isNumeric(s))
				return Double.parseDouble(s);
			else
				e.printStackTrace();
			
		}
		return number.doubleValue();
	}
	public double spinner_to_double(Spinner s) {
		return (double)(s.getSelection()/Math.pow(10, s.getDigits()));
	}
}
