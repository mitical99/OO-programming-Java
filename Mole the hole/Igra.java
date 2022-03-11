package basta;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {

	private Basta basta;
	private static Igra instance = null;
	private Button kreni;
	private Label kolPovrca;
	private Checkbox tezina[];
	private static final String[] tezine = { "Lako", "Srednje", "Tesko" };

	private Igra() {
		super("Igra");
		setBounds(100, 100, 800, 600);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (basta != null)
					basta.prekini();
				dispose();
			}
		});
		basta = new Basta(4, 4, this, 1000, 10);
		add(basta, BorderLayout.CENTER);
		dodajIstok();
		setResizable(false);
		setVisible(true);
	}

	private void dodajIstok() {
		Panel istok = new Panel(new GridLayout(2, 1));
		Panel top = new Panel(new GridLayout(5, 1));
		Label lTezina = new Label("Tezina:");
		lTezina.setFont(new Font("Serif", Font.BOLD, 14));
		lTezina.setAlignment(Label.CENTER);
		top.add(lTezina);
		CheckboxGroup grupa = new CheckboxGroup();
		tezina = new Checkbox[3];
		for (int i = 0; i < 3; i++) {
			tezina[i] = new Checkbox(tezine[i], grupa, i == 0);
			top.add(tezina[i]);
		}
		kreni = new Button("Kreni");
		kreni.addActionListener((ae) -> {
			if (ae.getActionCommand().equals("Kreni")) {
				int i = 0;
				for (; i < 3; i++)
					if (tezina[i].getState() == true)
						break;
				int interval = 1000;
				int brojKoraka = 10;
				switch (i) {
				case 0:
					interval = 1000;
					brojKoraka = 10;
					break;
				case 1:
					interval = 750;
					brojKoraka = 8;
					break;
				case 2:
					interval = 500;
					brojKoraka = 6;
					break;
				}
				basta.setInterval(interval);
				basta.setBrojKoraka(brojKoraka);
				basta.setPovrce(100);
				basta.kreni();
				kolPovrca.setText("Povrce: " + basta.getPovrce());
				kreni.setLabel("Stani");
			}
			if (ae.getActionCommand().equals("Stani")) {
				basta.prekini();
				kreni.setLabel("Kreni");
			}
		});
		top.add(kreni);
		istok.add(top);
		Panel bottom = new Panel(new GridLayout(5, 1));
		kolPovrca = new Label("Povrce: 0");
		kolPovrca.setFont(new Font("Serif", Font.BOLD, 18));
		kolPovrca.setAlignment(Label.CENTER);
		bottom.add(kolPovrca);
		istok.add(bottom);
		add(istok, BorderLayout.EAST);
	}

	public void azurirajPovrce(int kolicina) {
		kolPovrca.setText("Povrce: " + kolicina);
		if (kolicina == 0) {
			kreni.setLabel("Kreni");
		}
	}

	public static Igra getInstance() {
		if (instance == null) {
			instance = new Igra();
		}
		return instance;
	}

	public static void main(String[] args) {
		Igra.getInstance();
	}
}
