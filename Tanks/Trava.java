package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {

	public Trava(Mreza mreza) {
		super(mreza);
		setBackground(Color.GREEN);
	}

	@Override
	public boolean moze(Figura f) {
		return true;
	}
}
