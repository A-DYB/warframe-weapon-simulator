package orok.ttk;

public class Dot {
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
    13 
    14 
    15 
    16
    17
    18
    19
    */
	private int offset;
	private int life; //in milliseconds
	private double[] damage;
	public static double slow = 1;
	public static int electricity_offset = -1;
	int identifier;
	
	
	public Dot(int offset, int life, double[] damage, int id) {
		this.offset = offset;
		this.life = life;
		this.damage = damage;
		this.identifier = id;
	}
	
	//life is in ms
	public void setLife(int life) {
		this.life =life;
	}
	public void setOffset(int offset) {
		this.offset = offset;
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
		offset =0;
		life =0;
		damage = new double[15];
	}
}
