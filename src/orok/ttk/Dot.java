package orok.ttk;

public class Dot {
	private int offset;
	private int life; //in milliseconds
	private double damage;
	public static double slow = 1;
	public static int electricity_offset = -1;
	
	public Dot(int offset, int life, double damage) {
		this.offset = offset;
		this.life = life;
		this.damage = damage;
	}
	
	//life is in ms
	public void setLife(int life) {
		this.life =life;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public void setDamage(double damage) {
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
	public double getDamage() {
		return damage;
	}
	public void reset() {
		offset =0;
		life =0;
		damage =0;
	}
}
