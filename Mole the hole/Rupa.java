package basta;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class Rupa extends Canvas implements Runnable {
	private Basta basta;
	private Zivotinja zivotinja;
	private boolean pokrenuta;
	private int protekliKoraci;
	private int brojKoraka;
	private Thread nit;

	public Rupa(Basta b) {
		basta = b;
		zivotinja = null;
		nit = null;
		pokrenuta = false;
		brojKoraka = 0;
		protekliKoraci = 0;
	}

	public synchronized void setBrojKoraka(int br) {
		brojKoraka = br;
	}

	public synchronized int getBrojKoraka() {
		return brojKoraka;
	}

	public Zivotinja getZivotinja() {
		return zivotinja;
	}

	public void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}

	public synchronized int getTrenKoraka() {
		return protekliKoraci;
	}

	@Override
	public void paint(Graphics g) {
		setBackground(new Color(165, 42, 42));
		// g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
	}

	public void zgazi() {
		if (zivotinja != null)
			zivotinja.ispoljiEfekatUdarene();
	}

	public void kreni() {
		if (nit == null)
			stvori();
		pokrenuta = true;
		nit.start();

	}

	public synchronized void prekini() {
		if (nit != null)
			nit.interrupt();
		repaint();
		if (zivotinja != null)
			if (!zivotinja.udarena())
				zivotinja.ispoljiEfekatPobegle();
		zivotinja = null;
		pokrenuta = false;
		obavesti();
	}

	public void stvori() {
		nit = new Thread(this);
	}

	public boolean pokrenutaNit() {
		return pokrenuta;
	}

	@Override
	public void run() {
		try {
			for (protekliKoraci = 0; protekliKoraci < brojKoraka; protekliKoraci++)
				if (zivotinja != null) {
					zivotinja.crtaj();
					Thread.sleep(100);
				}
			Thread.sleep(2000);
			if (zivotinja != null)
				zivotinja.ispoljiEfekatPobegle();
			zivotinja = null;
			repaint();
			obavesti();
		} catch (InterruptedException e) {
			zivotinja = null;
			repaint();
			obavesti();
		}
	}

	public Basta getBasta() {
		return basta;
	}

	public void obavesti() {
		basta.getList().add(this);
	}
}
