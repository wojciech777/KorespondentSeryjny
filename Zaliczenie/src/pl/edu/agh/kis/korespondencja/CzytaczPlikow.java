package pl.edu.agh.kis.korespondencja;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * klasa ktora pozwala wczytac plik liniami i zwrocic jego zawartosc w postaci kontenera Stringow
 * @author Bomba Wojciech
 *
 */
public class CzytaczPlikow implements InterCzytaczPlikow {
	/**
	 * wczytuje plik linia po linii
	 * 
	 * @param adres
	 *            adres pliku do wczytania
	 * @return tablica linii pliku w zachowanej kolejnosci
	 * @throws IOException
	 *             mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	public ArrayList<String> wczytajPlikLiniami(String adres)
			throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(adres));
		String line;
		ArrayList<String> lista = new ArrayList<String>();

		while ((line = bf.readLine()) != null)
			lista.add(line);

		bf.close();

		return lista;
	}
}
