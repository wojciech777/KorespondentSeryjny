package pl.edu.agh.kis.korespondencja;

/**
 * interface InterInterpreterLiniiDanych implementowany przez klase InterpreterLiniiDanych
 * dostarcza metode ktora pozwala odpowiednio odczytac zmienne z linii tekstu znajac separator
 * 
 * @author Bomba Wojciech
 * 
 */
public interface InterInterpreterLiniiDanych {
	/**
	 * zachowujac wszystkie konwencje interpretowania znakow specjalnych metoda
	 * ta zwraca rozparsowane zmienne
	 * 
	 * @param linia
	 *            linia tekstu z pliku danych ktora bedzie rozseparowywana na
	 *            zmienne
	 * @return poprawnie rozseparowane zmienne typu String zebrane w tablicy
	 */
	String[] zwrocZmienne(String linia);
}