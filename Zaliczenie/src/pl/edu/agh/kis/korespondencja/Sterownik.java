package pl.edu.agh.kis.korespondencja;

import javax.swing.JFrame;

/**
 * class Sterownik aktywuje dzialanie programu wykorzystujac klasy wystepujace w
 * projekcie
 * 
 * @author Bomba Wojciech
 * 
 */
public class Sterownik {
	/**
	 * glowna metoda ktora tworzy okienko GUI i jednoczesnie aktywuje dzialanie
	 * programu
	 * 
	 * @param args
	 *            argumenty wywolania programu
	 */
	public static void main(String[] args) {
		UserFrame apka = new UserFrame();
		apka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		apka.setVisible(true);
	}
}
