package igra;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Mreza mreza;
	private Checkbox trava;
	private Checkbox zid;
	private TextField novcici;
	private Label poeni;
	private Button pocni;
	private int coins = 0;
	private boolean rezimIzmene = true;

	private void dodajMeni() {
		MenuBar bar = new MenuBar();
		Menu meni = new Menu("Rezim");
		MenuItem izmena = new MenuItem("Rezim izmena");
		MenuItem igranje = new MenuItem("Rezim igranje");
		izmena.addActionListener((ae) -> {
			if (rezimIzmene == false && mreza != null)
				mreza.prekini();
			rezimIzmene = true;
			pocni.setEnabled(false);
		});
		igranje.addActionListener((ae) -> {
			pocni.setEnabled(true);
			rezimIzmene = false;
		});
		meni.add(izmena);
		meni.add(igranje);
		bar.add(meni);
		setMenuBar(bar);
	}

	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (mreza != null)
					mreza.prekini();
				dispose();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (mreza != null) {
					Figura i = mreza.getIgrac();
					if (i == null)
						return;
					Polje p = i.getP(), polje = null;
					switch (e.getKeyCode()) {
					case KeyEvent.VK_A:
						;
						polje = p.dohvatiUdaljenoPolje(0, -1);
						break;
					case KeyEvent.VK_D:
						;
						polje = p.dohvatiUdaljenoPolje(0, 1);
						break;
					case KeyEvent.VK_W:
						;
						polje = p.dohvatiUdaljenoPolje(-1, 0);
						break;
					case KeyEvent.VK_S:
						;
						polje = p.dohvatiUdaljenoPolje(1, 0);
						break;
					}
					if (polje != null && i != null)
						i.pomeri(polje);
				}
			}
		});
	}

	private void dodajIstok() {
		Panel istok = new Panel(new GridLayout(1, 2));
		Label podloga = new Label("Podloga: ");
		podloga.setAlignment(Label.CENTER);
		Panel panPodloga = new Panel(new GridLayout(3, 1));
		panPodloga.add(new Label(""));
		panPodloga.add(podloga);
		Panel select = new Panel(new GridLayout(2, 1));
		Panel zeleni = new Panel(), sivi = new Panel();
		sivi.setBackground(Color.LIGHT_GRAY);
		zeleni.setBackground(Color.GREEN);
		CheckboxGroup grupa = new CheckboxGroup();
		trava = new Checkbox("Trava", grupa, true);
		zid = new Checkbox("Zid", grupa, false);
		zeleni.add(trava, BorderLayout.CENTER);
		sivi.add(zid, BorderLayout.CENTER);
		select.add(zeleni);
		select.add(sivi);
		istok.add(panPodloga);
		istok.add(select);
		add(istok, BorderLayout.EAST);
	}

	public Igra() {
		// setBounds(200, 200, 800, 600);
		mreza = new Mreza(17, this);
		add(mreza, BorderLayout.CENTER);
		dodajMeni();
		dodajIstok();
		dodajJug();
		dodajOsluskivace();
		setSize(800, 600);
		// setResizable(false);
		setVisible(true);
	}

	private void dodajJug() {
		Panel jug = new Panel(new FlowLayout());
		Label novcica = new Label("Novcica: ");
		jug.add(novcica);
		novcici = new TextField("0");
		novcici.setEditable(true);
		jug.add(novcici);
		coins = 0;
		poeni = new Label("Poeni: " + coins);
		jug.add(poeni);
		pocni = new Button("Pocni");
		pocni.addActionListener((ae) -> {
			if (!rezimIzmene) {
				if (mreza != null) {
					mreza.prekini();
					mreza.inicijalizuj(Integer.parseInt(novcici.getText()));
					coins = 0;
					poeni.setText("Poeni: " + coins);
					mreza.pokreni();
				}
			}
			this.requestFocusInWindow();
		});
		pocni.setEnabled(false);
		jug.add(pocni);
		add(jug, BorderLayout.SOUTH);
	}

	public boolean proveriTrava() {
		return trava.getState();
	}

	public boolean getRezimIzmene() {
		return rezimIzmene;
	}

	public static void main(String[] args) {
		new Igra();
	}

	public void povecajSkor() {
		coins++;
		poeni.setText("Poeni: " + coins);
	}
}
