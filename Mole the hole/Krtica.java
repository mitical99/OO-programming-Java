package basta;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {

	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void crtaj() {
		Graphics g=rupa.getGraphics();
		g.setColor(Color.DARK_GRAY);
		double procenat=(double)rupa.getTrenKoraka()/rupa.getBrojKoraka();
		int x=rupa.getWidth()/2;
		int y=rupa.getHeight()/2;
		int sirina=(int)(rupa.getWidth()*procenat);
		int visina=(int)(rupa.getHeight()*procenat);
		g.fillOval(x-sirina/2, y-visina/2, sirina, visina);
	}

	@Override
	public void ispoljiEfekatUdarene() {
		super.ispoljiEfekatUdarene();
		rupa.prekini();
	}

	@Override
	public void ispoljiEfekatPobegle() {
		rupa.getBasta().smanji();
	}

}
