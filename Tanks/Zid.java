package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {

	public Zid(Mreza mreza) {
		super(mreza);
		setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public boolean moze(Figura f) {
		return false;
	}
}
