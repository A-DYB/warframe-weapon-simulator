package orok.ttk;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import org.eclipse.ui.forms.widgets.FormToolkit;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import orok.ttk.Enemy.Dot;
import org.eclipse.wb.swt.SWTResourceManager;

public class MainGUI {

	protected static Shell shell;
	private final static FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text txtName;
	private Text txtEnemyname;
	
	private Label time_label;
	private Label shots_label;
	private Label v_time_label;
	private Label v_shots_label;

	private XYSeries series;
	private XYSeries armor_series;
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
	
	private boolean graph = false;
	private boolean combo_is_populated = false;
	private boolean ui_set_up = false;
	private static boolean gui_setup = false;

	private double armorReduct = 1;
	private double vigilante;

	private int numSeries = 0;
	
	public static Object wep_obj = null;
	private Button btnEidolon;
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
	private Spinner void1ESpinner;
	private Spinner void2ESpinner;
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
	
	private static TableItem tableItem_1;
	private static TableItem tableItem_2;
	private static TableItem tableItem_3;
	
	private static TableColumn tblclmnStat;
	
	
	private Random rMulti = new Random();
	private Random rCrit = new Random();
	private Random rVig = new Random();
	private Random rStatus = new Random();
	
	public ArrayList<Double> stance_multipliers = new ArrayList<Double>();
	public ArrayList<Integer> stance_procs = new ArrayList<Integer>();
	static double melee_time=1;
	private Button btnDemolyst;
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
		shell.setSize(823, 950);
		shell.setText("Pocket Simulacrum v0.3.2");
		shell.setLayout(null);
		
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
		tabFolder.setBounds(0, 0, 469, 901);
		formToolkit.adapt(tabFolder);
		formToolkit.paintBordersFor(tabFolder);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		CTabItem tbtmSimulation = new CTabItem(tabFolder, SWT.NONE);
		tbtmSimulation.setText("Simulation");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		tbtmSimulation.setControl(composite);
		formToolkit.paintBordersFor(composite);
		composite.setLayout(new GridLayout(7, false));
		
		Label lblEnemy = formToolkit.createLabel(composite, "Enemy", SWT.NONE);
		lblEnemy.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		
		Combo enemy_combo = new Combo(composite, SWT.NONE);
		enemy_combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_is_populated) {
					String name = enemy_combo.getText();
					//default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,Double.parseDouble(lvl_spinner.getText()), ( 100 - Double.parseDouble(armor_strip_spinner.getText()) )/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		GridData gd_enemy_combo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 3, 1);
		gd_enemy_combo.widthHint = 236;
		enemy_combo.setLayoutData(gd_enemy_combo);
		formToolkit.adapt(enemy_combo);
		formToolkit.paintBordersFor(enemy_combo);
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
				String name = weaponCombo.getText();
				if(!name.equals("Custom Build Tab")) {
					btnRemoveBuild.setEnabled(true);
				}else {
					btnRemoveBuild.setEnabled(false);
					//name = weaponListCombo.getText();
				}
				//parse_stance(stance_combo.getText());
				default_weapon = new Weapon(name, true);

				//update_weapon_table(default_weapon);
				String stance_name = stance_combo.getText();
				
				if(stance_name != null && !stance_name.equals("None")) {
					//System.out.println(stance_name);
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
		
		btnRemoveBuild = new Button(composite, SWT.NONE);
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
		lblSep.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 7, 1));
		lblSep.setText("sep1");
		formToolkit.adapt(lblSep, true, true);
		
		Label lblSettings = new Label(composite, SWT.NONE);
		lblSettings.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 7, 1));
		formToolkit.adapt(lblSettings, true, true);
		lblSettings.setText("Settings");
		
		Label lblSep_1 = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSep_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 7, 1));
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
		health_scale_spinner.setIncrement(5);
		health_scale_spinner.setDigits(1);
		health_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,Double.parseDouble(lvl_spinner.getText()), (100 - Double.parseDouble(armor_strip_spinner.getText()))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}

			}
		});
		health_scale_spinner.setMaximum(50);
		health_scale_spinner.setMinimum(10);
		formToolkit.adapt(health_scale_spinner);
		formToolkit.paintBordersFor(health_scale_spinner);
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
		armor_scale_spinner.setDigits(1);
		armor_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,Double.parseDouble(lvl_spinner.getText()), (100-Double.parseDouble(armor_strip_spinner.getText()))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		armor_scale_spinner.setIncrement(5);
		armor_scale_spinner.setMaximum(50);
		armor_scale_spinner.setMinimum(10);
		formToolkit.adapt(armor_scale_spinner);
		formToolkit.paintBordersFor(armor_scale_spinner);
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
		shield_scale_spinner.setDigits(1);
		shield_scale_spinner.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,Double.parseDouble(lvl_spinner.getText()), (100-Double.parseDouble(armor_strip_spinner.getText()))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		shield_scale_spinner.setMaximum(50);
		shield_scale_spinner.setMinimum(10);
		shield_scale_spinner.setIncrement(5);
		formToolkit.adapt(shield_scale_spinner);
		formToolkit.paintBordersFor(shield_scale_spinner);
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
		
		btnDemolyst = new Button(composite, SWT.CHECK);
		btnDemolyst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(combo_is_populated && gui_setup) {
					String name = enemy_combo.getText();
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy(name,Double.parseDouble(lvl_spinner.getText()), (100-Double.parseDouble(armor_strip_spinner.getText()))/100.0 , default_weapon);
					parseSettings(default_weapon, default_enemy);
					
					update_enemy_table(default_enemy);
					update_weapon_table(default_weapon);
				}
			}
		});
		formToolkit.adapt(btnDemolyst, true, true);
		btnDemolyst.setText("Demolyst");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		lblAbilitiesseparator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblAbilitiesseparator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 7, 1));
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
		
		btnHeadshots = new Button(composite, SWT.CHECK);
		formToolkit.adapt(btnHeadshots, true, true);
		btnHeadshots.setText("Headshots");
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
		
		series = new XYSeries("Health + Shields");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		chart = ChartFactory.createXYLineChart("Single Simulation", "Time", "Health", dataset);
		ChartFrame frame = new ChartFrame("chart",chart);
		frame.setAlwaysOnTop(false);
		frame.setBounds(0, 0, 135, 240);
		
		//armor graph
		armor_series = new XYSeries("Armor");
		XYSeriesCollection armor_dataset = new XYSeriesCollection(armor_series);
		JFreeChart armor_chart = ChartFactory.createXYLineChart("Armor Tracker", "Time", "Armor", armor_dataset);
		ChartFrame armor_frame = new ChartFrame("chart", armor_chart);
		armor_frame.setAlwaysOnTop(false);
		armor_frame.setBounds(0, 480, 135, 240);
		
		
		XYSeriesCollection datasetB = new XYSeriesCollection(scaleSeries);
		JFreeChart chartB = ChartFactory.createXYLineChart("Time to Kill", "Level", "TTK", datasetB);
		ChartFrame frameB = new ChartFrame("chart",chartB);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
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
		
		btnSimulate = new Button(composite, SWT.NONE);
		GridData gd_btnSimulate = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
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
		GridData gd_lvl_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
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
		
		btnGraph = new Button(composite, SWT.NONE);
		GridData gd_btnGraph = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnGraph.widthHint = 101;
		btnGraph.setLayoutData(gd_btnGraph);
		btnGraph.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				series.clear();	
				armor_series.clear();
				
				XYPlot plot = (XYPlot) chart.getPlot();
				XYPlot armor_plot = (XYPlot) armor_chart.getPlot();
				
				plot.clearDomainMarkers();
				armor_plot.clearDomainMarkers();
				
				
				
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),lvl_spinner.getSelection(), armorReduct, default_weapon);
				
				graph = true;
				double results[] = simulate(default_enemy,default_weapon,1);
				graph = false;
				
				frame.setVisible(true);
				frame.setSize(1000, 450);
				
				if(default_enemy.baseArmor > 0) {
					armor_frame.setVisible(true);
					
					
					armor_frame.setSize(1000, 450);
				}

				time_label.setText(String.format("%.2f", results[0]) );
				shots_label.setText(String.format("%.2f", results[1]) );
				v_time_label.setText(String.format("%.2f", results[2]) );
				v_shots_label.setText(String.format("%.2f", results[3]) );
			}
		});
		formToolkit.adapt(btnGraph, true, true);
		btnGraph.setText("Graph");
		
		txtSeriesName = new Text(composite, SWT.BORDER);
		GridData gd_txtSeriesName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSeriesName.widthHint = 82;
		txtSeriesName.setLayoutData(gd_txtSeriesName);
		formToolkit.adapt(txtSeriesName, true, true);
		

		
		Button btnClearGraph = new Button(composite, SWT.NONE);
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
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		btnScale = new Button(composite, SWT.NONE);
		GridData gd_btnScale = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnScale.widthHint = 103;
		btnScale.setLayoutData(gd_btnScale);
		btnScale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int start_level = start_level_spinner.getSelection();
				int end_level = end_level_spinner.getSelection();
				
				
				
				default_weapon.setupCustomBuild();
				default_enemy = new Enemy((String)enemy_combo.getText(),1, armorReduct, default_weapon);
				
				scaleSeries = new XYSeries(numSeries+": "+txtSeriesName.getText());
				datasetB.addSeries(scaleSeries);
				
				for(int i = start_level; i <= end_level; i += 25) {	
					
					default_weapon.setupCustomBuild();
					default_enemy = new Enemy((String)enemy_combo.getText(),i, armorReduct,default_weapon);
					double results[] = simulate(default_enemy,default_weapon,20);
					scaleSeries.add(i, results[0]);
					
				}
				
				frameB.setVisible(true);
				frameB.setSize(1000, 450);
				numSeries++;
			}
		});
		formToolkit.adapt(btnScale, true, true);
		btnScale.setText("Scale");
		
		start_level_spinner = new Spinner(composite, SWT.BORDER);
		GridData gd_start_level_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
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
		GridData gd_end_level_spinner = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
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
		new Label(composite, SWT.NONE);
		
		CTabItem tbtmAddBuild = new CTabItem(tabFolder, SWT.NONE);
		tbtmAddBuild.setText("Custom Build");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmAddBuild.setControl(composite_3);
		formToolkit.paintBordersFor(composite_3);
		composite_3.setLayout(new GridLayout(7, false));
		
		Label lblWeapon_1 = new Label(composite_3, SWT.NONE);
		lblWeapon_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblWeapon_1, true, true);
		lblWeapon_1.setText("Weapon");
		
		weaponListCombo = new Combo(composite_3, SWT.NONE);
		weaponListCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = "Custom Build Tab";
				
				set_up_fire_mode_combo();
				
					
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
					setup_move_combo(stance_combo.getText());
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
		
		void1ESpinner = new Spinner(composite_1, SWT.BORDER);
		void1ESpinner.setMaximum(100000);
		void1ESpinner.setIncrement(10);
		void1ESpinner.setDigits(1);
		formToolkit.adapt(void1ESpinner);
		formToolkit.paintBordersFor(void1ESpinner);
		
		void2ESpinner = new Spinner(composite_1, SWT.BORDER);
		void2ESpinner.setMaximum(100000);
		void2ESpinner.setIncrement(10);
		void2ESpinner.setDigits(1);
		formToolkit.adapt(void2ESpinner);
		formToolkit.paintBordersFor(void2ESpinner);
		
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
		        
				double pellet = Double.parseDouble(pelletESpinner.getText());
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
		
		Button btnOpenFile_weapon = new Button(composite_1, SWT.NONE);
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
		txtEnemyname.setText("enemyName");
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
		shield_combo.setItems(new String[] {"Shield", "ProtoShield"});
		shield_combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		formToolkit.adapt(shield_combo);
		formToolkit.paintBordersFor(shield_combo);
		shield_combo.select(0);
		
		Label lblBaseArmor = new Label(composite_2, SWT.NONE);
		lblBaseArmor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseArmor, true, true);
		lblBaseArmor.setText("Base Armor");
		
		Spinner bArmorSpinner = new Spinner(composite_2, SWT.BORDER);
		bArmorSpinner.setMaximum(100000);
		formToolkit.adapt(bArmorSpinner);
		formToolkit.paintBordersFor(bArmorSpinner);
		
		Label lblBaseHealth = new Label(composite_2, SWT.NONE);
		lblBaseHealth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseHealth, true, true);
		lblBaseHealth.setText("Base Health");
		
		bHealthSpinner = new Spinner(composite_2, SWT.BORDER);
		bHealthSpinner.setMaximum(100000);
		bHealthSpinner.setMinimum(1);
		formToolkit.adapt(bHealthSpinner);
		formToolkit.paintBordersFor(bHealthSpinner);
		
		Label lblBaseShield = new Label(composite_2, SWT.NONE);
		lblBaseShield.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseShield, true, true);
		lblBaseShield.setText("Base Shield");
		
		bShieldSpinner = new Spinner(composite_2, SWT.BORDER);
		bShieldSpinner.setMaximum(100000);
		formToolkit.adapt(bShieldSpinner);
		formToolkit.paintBordersFor(bShieldSpinner);
		
		Label lblBaseLevel = new Label(composite_2, SWT.NONE);
		lblBaseLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		formToolkit.adapt(lblBaseLevel, true, true);
		lblBaseLevel.setText("Base Level");
		
		bLevelSpinner = new Spinner(composite_2, SWT.BORDER);
		bLevelSpinner.setMaximum(500);
		bLevelSpinner.setMinimum(1);
		formToolkit.adapt(bLevelSpinner);
		formToolkit.paintBordersFor(bLevelSpinner);
		new Label(composite_2, SWT.NONE);
		
		btnEidolon = new Button(composite_2, SWT.CHECK);
		formToolkit.adapt(btnEidolon, true, true);
		btnEidolon.setText("Eidolon");
		
		Button btnAdd_enemy = new Button(composite_2, SWT.NONE);
		btnAdd_enemy.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				//int dr_arr[] = {0,0,1,0};
				//int [][]a = {dr_arr};
				JSONArray list = new JSONArray();
		        list.add(0);
		        list.add(0);
		        list.add(1);
		        list.add(0);
		        JSONArray listlist = new JSONArray();
		        listlist.add(list);
		        

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
		        stats.put("Eidolon", btnEidolon.getSelection());
		        stats.put("Eidolon", btnEidolon.getSelection());
		        stats.put("DamageReduction", listlist);
		        stats.put("ProcReduction", listlist);
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
		
		populate_weapon_combo(weaponListCombo);
		populate_enemy_combo(enemy_combo);
		//populateCombo(enemy_combo, weaponListCombo,weaponCombo);
		
		weaponCombo.select(0);
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
		
		Label lblVariance = new Label(composite, SWT.NONE);
		formToolkit.adapt(lblVariance, true, true);
		lblVariance.setText("Variance");
		
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
		
		btnShatteringImpact = new Button(composite_3, SWT.CHECK);
		btnShatteringImpact.setText("Shatt. Imp.");
		formToolkit.adapt(btnShatteringImpact, true, true);
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
		
		btnPrimedchamber = new Button(composite_3, SWT.CHECK);
		btnPrimedchamber.setText("Primed Chamber");
		formToolkit.adapt(btnPrimedchamber, true, true);
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
		        mods.put("Magazine", magazine_mod_text.getText());
		        mods.put("Reload", reload_mod_text.getText());
		        mods.put("Impact", impact_mod_text.getText());
		        mods.put("Puncture", puncture_mod_text.getText());
		        mods.put("Slash", slash_mod_text.getText());
		        mods.put("StatusDuration", status_duration_mod_text.getText());
		        mods.put("GeneralMultiplier", general_multiplier_mod_text.getText());
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
		
		weapon_table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		weapon_table.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		weapon_table.setBounds(486, 35, 305, 738);
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
		secondary_effects_combo.setBounds(643, 10, 148, 23);
		formToolkit.adapt(secondary_effects_combo);
		formToolkit.paintBordersFor(secondary_effects_combo);
		secondary_effects_combo.setText("Secondary Effects");

		default_weapon = new Weapon(weaponCombo.getText(), true);
		default_enemy = new Enemy(enemy_combo.getText(),165, 1, default_weapon);
		populate_build_combo(weaponCombo);
		
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
		tableItem25 = new TableItem(weapon_table, SWT.NONE);
		tableItem26 = new TableItem(weapon_table, SWT.NONE);
		tableItem27 = new TableItem(weapon_table, SWT.NONE);
		tableItem28 = new TableItem(weapon_table, SWT.NONE);
		tableItem29 = new TableItem(weapon_table, SWT.NONE);
		tableItem30 = new TableItem(weapon_table, SWT.NONE);
		tableItem31 = new TableItem(weapon_table, SWT.NONE);
		tableItem32 = new TableItem(weapon_table, SWT.NONE);
		tableItem33 = new TableItem(weapon_table, SWT.NONE);

		
		enemy_table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		enemy_table.setBounds(486, 779, 305, 122);
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
		
		TableColumn tblclmnType = new TableColumn(enemy_table, SWT.NONE);
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
	private void populate_weapon_combo(Combo weaponListCombo) {
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
			fireModeCombo.select(0);
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
	private void setup_move_combo(String stance_name) {
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
		move_combo.select(0);
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
        	   stance_multipliers.add(Double.parseDouble(jsonArray.get(i).toString()));
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
        
        melee_time = Double.parseDouble(selected_combo.get("Time").toString())/len;
        
	}
	
	private double[] simulate(Enemy enemy, Weapon weapon, int numSim) {
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
		//May need to set virla, headshots etc for each sendary effect? or get those stats from the parent weapon
		//weapon.set_secondary_effects();
		
		//System.out.printf("Simulating Weapon: "+weapon.name+"\n");
		
		int count=0;
		double std_shots =0;
		double std_time = 0;
		int newCount=0;
		int thresh = 20;
		while(count<numSim) {
			oneSim = simulate_once(enemy,weapon);
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
				oneSim = simulate_once(enemy,weapon);
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
		tox_proc_dmg = tox_proc_dmg/thresh;
		sl_proc_dmg = sl_proc_dmg/thresh;
		heat_proc_dmg = heat_proc_dmg/thresh;
		electric_proc_dmg = electric_proc_dmg/thresh;
		gas_proc_dmg = gas_proc_dmg/thresh;

		enemy.reset(0);
		tableItem25.setText(new String[] {"Ammo Efficiency",String.format("%,.1f", meanShots*weapon.ammoCost*100/(double)weapon.base_ammo)+"% of max ammo"});
		tableItem24.setText(new String[] {"DPS",String.format("%,.0f", enemy.getHealthRemaining()/meanTime)});
		
		tableItem27.setText(new String[] {"Slash Proc", String.format("%,.1f",sl_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem28.setText(new String[] {"Toxin Proc", String.format("%,.1f",tox_proc_dmg*100/enemy.getHealthRemaining())+"%"});
		tableItem29.setText(new String[] {"Heat Proc", String.format("%,.1f",heat_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem30.setText(new String[] {"Electric Proc", String.format("%,.1f",electric_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		tableItem31.setText(new String[] {"Gas Proc", String.format("%,.1f",gas_proc_dmg*100/(enemy.getHealthRemaining()+enemy.getShieldRemaining()))+"%"});
		
		
		double results[] = {meanTime,meanShots,varTime,varShots};
		return results;
	}
	private double[] simulate_once(Enemy enemy, Weapon weapon) {
		double result[] = new double[2];
		int vigilanteCritLevel = 0;
		int millis = 0;
		int shots= 0;
		double critMultiplier;
		Weapon cur_effect = null;
		
		enemy.reset(armorReduct);
		weapon.fo.reset();
		weapon.reset_secondary_effects();
		
		int prevHP = (int)(enemy.getHealthRemaining()+enemy.getShieldRemaining());
		int curHP = prevHP;
		
		int prev_armor = (int)(enemy.getArmorRemaining());
		int cur_armor = prev_armor;
		
		
		int num_combo_attacks = stance_multipliers.size();
		int combo_count=0;
		boolean force_slash = false;
		
		//weapon.damage_array = enemy.array_scale(weapon.damage_array,weapon.getMultiplier());
		
		if(graph) {
			//series.add(millis/1000.0, ehp);
			series.add(millis/1000.0, curHP);
			armor_series.add(millis/1000.0, enemy.getArmorRemaining());
			//System.out.println(millis*1000);
		}
		cur_effect = weapon;
		double next_event = cur_effect.fo.get_event_time();
		
		while(enemy.getHealthRemaining() > 0){ 
			
			int[] event_index = new int[] {1000000,1000000,1000000,1000000, 1000000};
			
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
				///////////
						
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
					
					
					if(rVig.nextDouble() <= vigilante)
						vigilanteCritLevel = 1;
					
					if(rCrit.nextDouble() <= cur_effect.getHighCC()){ //highcrit?

						if(weapon.headshot) {
							critMultiplier = weapon.additive_crit_damage + ((2*cur_effect.critMultiplier-1)*(cur_effect.getCritTier()+vigilanteCritLevel)+1);								
							enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()) , critMultiplier, false);	//have to pass crit mul for toxin shield bypass
							enemy.applyProc(cur_effect,true, critMultiplier, millis, rStatus.nextDouble(),force_slash);		
																													
						//no headshot
						}else {
							critMultiplier = weapon.additive_crit_damage + (cur_effect.critMultiplier-1)*(cur_effect.getCritTier()+vigilanteCritLevel)+1;																					
							enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()),critMultiplier, false);																					
							enemy.applyProc(cur_effect,true, critMultiplier, millis, rStatus.nextDouble(),force_slash);	
																											
						}
						
					}else{
						
						//no crit
						if(cur_effect.getCritTier() == 0){
							
							critMultiplier = 1 ;
							//headshot
							if(weapon.headshot) {
								critMultiplier = 1 + weapon.additive_crit_damage;
								enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()), critMultiplier, false);
								enemy.applyProc(cur_effect,false, critMultiplier, millis, rStatus.nextDouble(),force_slash);
																					
							//no headshot
							}else {
								enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()), critMultiplier,false);										
								enemy.applyProc(cur_effect,false, critMultiplier, millis, rStatus.nextDouble(),force_slash);														
							}

						//lower tier crit	
						}else{
							//ex if crit chance is 130%, this is the 70% chance to do a yellow crit
							//headshot
							if(weapon.headshot) {
								critMultiplier = weapon.additive_crit_damage + ((2*cur_effect.critMultiplier-1)*(cur_effect.getCritTier()+vigilanteCritLevel-1)+1);
								enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()), critMultiplier,false);
								enemy.applyProc(cur_effect,true, critMultiplier, millis, rStatus.nextDouble(),force_slash);		
																			
							//not headshot
							}else {
								critMultiplier = weapon.additive_crit_damage + ((cur_effect.critMultiplier-1)*(cur_effect.getCritTier()+vigilanteCritLevel-1)+1);
								enemy.damage_enemy(enemy.array_scale(cur_effect.damage_array, weapon.getMultiplier()), critMultiplier, false);
								enemy.applyProc(cur_effect,true, critMultiplier, millis, rStatus.nextDouble(),force_slash);

							}
						} 
					}
					//clean up
					vigilanteCritLevel = 0;
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
				
				/*
				if(!cur_effect.is_secondary_effect) {
					shots++;
					weapon.primed_chamber_multiplier=1;
				}
				*/
				shots++;
				weapon.primed_chamber_multiplier=1;
				//System.out.println(numPellets);
				
				
				cur_effect.fo.increment();
				
				cur_effect = weapon.get_next_effect();
				next_event = cur_effect.fo.get_event_time();
				
			}
			else if(mindex == 1) {
				millis = (int)next_dot.getOffset();
				
				next_dot.execute();
			}
			else if(mindex == 2) {
				millis = (int)Enemy.electricity_offset;
				
				enemy.elecQ.peekFirst().electric_trigger();
			}
			else if(mindex == 3) {
				millis = (int)Enemy.gas_offset;
				
				enemy.gasQ.peekFirst().gas_trigger();
			}
			else if(mindex == 4) {
				millis = (int)Enemy.heat_offset;
				
				enemy.heatDot.heat_trigger();
			}
			
			
			
			curHP = (int)(enemy.getHealthRemaining()+enemy.getShieldRemaining());
			cur_armor = (int)(enemy.getArmorRemaining());
			
			//System.out.printf("Time: %d, Prev: %d, Cur: %d\n",millis, prevHP, curHP);
			//System.out.printf("Millis: %d, Dmg: %d\n", millis, prevHP-curHP);
			
			if(graph) {
				if(prevHP != curHP) {
					//series.add(millis/1000.0, ehp);
					series.add(millis/1000.0, prevHP);
					series.add(millis/1000.0, curHP);
					//System.out.println(millis*1000);
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
		
		enemy.health_scale= Double.parseDouble(health_scale_spinner.getText());
		enemy.armor_scale= Double.parseDouble(armor_scale_spinner.getText());
		enemy.shield_scale= Double.parseDouble(shield_scale_spinner.getText());
		
		enemy.setHealth(enemy.getHealthRemaining()*enemy.health_scale);
		enemy.setArmor(enemy.getArmorRemaining()*enemy.armor_scale);
		enemy.scaled_armor = enemy.getArmorRemaining();
		enemy.setShield(enemy.getShieldRemaining()*enemy.shield_scale);
		
		enemy.resist(Double.parseDouble(resist_spinner.getText()));
		
		boolean hunterMunitions = btnHunterMunitions.getSelection();
		enemy.shattering_impact = btnShatteringImpact.getSelection();
		enemy.amalgam_shattering_impact = btnAmalgamShatImp.getSelection();
		enemy.demolyst = btnDemolyst.getSelection();

		weapon.headshot = btnHeadshots.getSelection();
		if(weapon.headshot) {
			weapon.headshot_multiplier = 2;
		}
		
		weapon.setHunter(hunterMunitions);
		
		if(enemy.acolyte) {
			weapon.critMultiplier = (weapon.critMultiplier-1)*0.5+1;
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
	}
	
	public void update_weapon_table(Weapon wep) {
		
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
		
		tableItem26.setText(new String[] {"", ""});
		
		tableItem27.setText(new String[] {"Slash Procs", "Run a simulation"});
		tableItem28.setText(new String[] {"Toxin Procs", "Run a simulation"});
		tableItem29.setText(new String[] {"Heat Procs", "Run a simulation"});
		tableItem30.setText(new String[] {"Electric Procs", "Run a simulation"});
		tableItem31.setText(new String[] {"Gas Procs", "Run a simulation"});

		tableItem32.setText(new String[] {"", ""});
		
		String s ="";
		if(default_enemy.baseShield > 0) {
			s = String.format("%,.2f",(default_enemy.display_damage(default_enemy.array_scale(w.damage_array, w.getMultiplier()),w)[1]));
		}else {
			s = String.format("%,.2f",(default_enemy.display_damage(default_enemy.array_scale(w.damage_array, w.getMultiplier()),w)[0]));
		}
		tableItem33.setText(new String[] {"Damage", s });
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
			table_string[index] = new String[] { "blast", format_double( w.beam_multishot * w.damage_array[index("blast")] , 3 ) };
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
	
	
	void update_table_item(TableItem t, String s, double val) {
		if(val > 0) {
			t.setText(new String[] {s, format_double(val, 3)});
		}else {
			t.setText(new String[] {"", ""});
		}
			
		
	}
	String format_double(double d, int sig_fig) {
		BigDecimal bd = new BigDecimal(d);
		bd = bd.round(new MathContext(sig_fig));
		double rounded = bd.doubleValue();
		return Double.toString(rounded);
	}
	
	
	static double parse_double_textbox(String s) {
	    String[] arrSplit = s.split("\\s");
	    double tot = 0;
	    for (int i=0; i < arrSplit.length; i++)
	    {
	      if( isNumeric(arrSplit[i]) ) {
	    	  tot += Double.parseDouble(arrSplit[i]);
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
		return percent/100.0 + base;
	}
	
	@SuppressWarnings("unchecked")
	public static void set_up_fire_mode_combo() {
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
			fireModeCombo.select(0);
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
}
