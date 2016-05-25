package pl.edu.agh.kis.korespondencja;

import java.io.IOException;
import java.util.ArrayList;

/**
 * interfejs implementowany przez CzytaczPlikow ktory dostarcza metod ktore
 * pozwalaja wczytac plik liniami i zwrocic jego zawartosc w postaci kontenera
 * Stringow
 * 
 * @author Bomba Wojciech
 * 
 */
public interface InterCzytaczPlikow {
	/**
	 * wczytuje plik linia po linii
	 * 
	 * @param adres
	 *            adres pliku do wczytania
	 * @return tablica linii pliku w zachowanej kolejnosci
	 * @throws IOException
	 *             mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	ArrayList<String> wczytajPlikLiniami(String adres) throws IOException;
}