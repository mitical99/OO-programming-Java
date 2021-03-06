package igra;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	private Scena scena;

	public Igra() {
		super("Baloni");
		setBounds(100, 100, 600, 600);
		scena = new Scena(this);
		add(scena, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (scena != null)
					scena.prekini();
				dispose();
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (scena.getIgrac() == null)
					return;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					scena.getIgrac().pomeri(-5);
					break;
				case KeyEvent.VK_RIGHT:
					scena.getIgrac().pomeri(5);
					break;
				}
			}
		});
		setResizable(false);
		setVisible(true);
		scena.pokreni();
	}

	public static void main(String[] args) {
		new Igra();
	}
}
