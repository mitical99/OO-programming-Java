package igra;

import java.awt.Canvas;

public abstract class Polje extends Canvas {
	private Mreza mreza;

	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
	}

	public Mreza getMreza() {
		return mreza;
	}

	public int[] getPozicija() {
		int[] rez = new int[2];
		Polje[][] p = mreza.getPolja();
		for (int i = 0; i < p.length; i++)
			for (int j = 0; j < p.length; j++)
				if (this.equals(p[i][j])) {
					rez[0] = i;
					rez[1] = j;
				}
		return rez;
	}
	public Polje dohvatiUdaljenoPolje(int i,int j)
	{
		int[] pozicija=this.getPozicija();
		int size=mreza.getPolja().length;
		int vrsta=i+pozicija[0],kolona=j+pozicija[1];
		if(vrsta<0 || vrsta>=size || kolona<0 || kolona>=size) return null;
		else return mreza.getPolja()[vrsta][kolona];
	}
	
	public abstract boolean moze(Figura f);
}
