package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje p) {
		super(p);
	}

	@Override
	public void crtaj() {
     Graphics g=p.getGraphics();
     g.setColor(Color.RED);
     g.drawLine(p.getWidth()/2, 0, p.getWidth()/2, p.getHeight());
     g.drawLine(0, p.getHeight()/2, p.getWidth(), p.getHeight()/2);
	}

	@Override
	public void pomeri(Polje polje) {
		if(!polje.moze(this)) return;
        Polje preth=this.p;
        this.p=polje;
        preth.repaint();
	}

}
