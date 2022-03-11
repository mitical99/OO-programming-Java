package igra;

public abstract class Figura {
	protected Polje p;

	public Figura(Polje p) {
		super();
		this.p = p;
	}

	public Polje getP() {
		return p;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Figura)) return false;
		Figura f=(Figura) obj;
		if(f.getP() == this.p) return true;
		else return false;
	}
public abstract void crtaj();
public abstract void pomeri(Polje polje);
}
