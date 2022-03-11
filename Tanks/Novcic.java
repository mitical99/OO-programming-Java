package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje p) {
		super(p);
	}

	@Override
	public void crtaj() {
    Graphics g=p.getGraphics();
    g.setColor(Color.YELLOW);
    g.fillOval(p.getWidth()/4, p.getHeight()/4, p.getWidth()/2, p.getHeight()/2);
	}

	@Override
	public void pomeri(Polje polje) {
	}

}
