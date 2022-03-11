package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {

	private Thread nit;
	private enum Smer {GORE,LEVO,DOLE,DESNO};
	private static Smer[] smerovi= {Smer.GORE,Smer.LEVO,Smer.DOLE,Smer.DESNO};
	public Tenk(Polje p) {
		super(p);
		nit=null;
	}

	@Override
	public void crtaj() {
		Graphics g=getP().getGraphics();
		g.setColor(Color.BLACK);
		Polje p=getP();
		g.drawLine(0, 0,p.getWidth() , p.getHeight());
        g.drawLine(p.getWidth(), 0, 0, p.getHeight());
	}

	@Override
	public void pomeri(Polje polje) {
		if(!polje.moze(this)) return;
		Polje preth=this.p;
		this.p=polje;
		preth.repaint();
	}
   public void pokreni()
   {
	   if(nit!=null) nit.interrupt();
	   nit=new Thread(this);
	   nit.start();
   }
   public void prekini()
   {
	   if(nit!=null) nit.interrupt();
   }
   public void run()
   {
	   try
	   {
		   while(!Thread.interrupted())
		   {
			   Thread.sleep(500);
			   int rnd=(int)(Math.random()*smerovi.length);
			   Smer smer=smerovi[rnd];
			   Polje polje=null;
			   switch(smer)
			   {
			   case GORE:polje=p.dohvatiUdaljenoPolje(0, -1);break;
			   case LEVO:polje=p.dohvatiUdaljenoPolje(-1, 0);break;
			   case DOLE:polje=p.dohvatiUdaljenoPolje(0, 1);break;
			   case DESNO:polje=p.dohvatiUdaljenoPolje(1, 0);break;
			   }
			   if(polje!=null) this.pomeri(polje);
		   }
	   } catch(InterruptedException e) { }
   }
}
