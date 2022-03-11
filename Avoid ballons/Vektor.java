package igra;

public class Vektor implements Cloneable {
	private double x;
	private double y;

	public Vektor(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public Vektor clone() {
		return new Vektor(x, y);
	}

	public void pomnozi(double vrednost) {
		x *= vrednost;
		y *= vrednost;
	}

	public void saberi(Vektor v) {
		x += v.x;
		y += v.y;
	}
}
