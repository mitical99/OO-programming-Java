package basta;

public abstract class Zivotinja {
	protected Rupa rupa;
    protected boolean udarena;
	public Zivotinja(Rupa rupa) {
		udarena=false;
		this.rupa = rupa;
	}

	public abstract void crtaj();

	public void ispoljiEfekatUdarene()
	{
		udarena=true;
	}

	public boolean udarena()
	{
		return udarena;
	}
	
	public abstract void ispoljiEfekatPobegle();
}
