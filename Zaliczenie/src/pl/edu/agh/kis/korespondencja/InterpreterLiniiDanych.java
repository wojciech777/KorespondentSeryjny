package pl.edu.agh.kis.korespondencja;

import java.util.LinkedList;

/**
 * class InterpreterLiniiDanych pozwala odpowiednio odczytac dane z jednej linii
 * tekstu znajac separator. Odpowiednio oznacza z prawidlowa interpretacja znakow
 * specjalnych czyli separatorow
 * 
 * @author Bomba Wojciech
 * 
 */
public class InterpreterLiniiDanych implements InterInterpreterLiniiDanych {
	private String separator;
	private int dlugoscSeparatora;
	private String bezpiecznySeparator;
	private char znakUnikalny = (char) 0;
	private LinkedList<Integer> listaSeparatorow = new LinkedList<Integer>();
	private LinkedList<Integer> listaIndeksowFalszywychSeparatorowNaLiscieSeparatorow = new LinkedList<Integer>();
	private LinkedList<Integer> listaSleszyDoUsuniecia = new LinkedList<Integer>();

	/**
	 * konstruktor tworzy klase i inicjuje zmienne ktore moga byc zainicjowane
	 * na tym etapie
	 * 
	 * @param separator
	 */
	public InterpreterLiniiDanych(String separator) {
		this.separator = separator;
		dlugoscSeparatora = separator.length();
		bezpiecznySeparator = "\\" + separator;
	}

	/**
	 * zachowujac wszystkie konwencje interpretowania znakow specjalnych metoda
	 * ta zwraca rozparsowane zmienne
	 * 
	 * @param linia
	 *            linia tekstu z pliku danych ktora bedzie rozseparowywana na
	 *            zmienne
	 * @return poprawnie rozseparowane zmienne typu String zebrane w tablicy
	 */
	public String[] zwrocZmienne(String linia) {
		wyczyscListy();
		listaSeparatorow = zwrocIndeksyWszystkichSeparatorow(linia);
		ZbadajPrawdziwoscSeparatorowOznaczUsuwaneBekslesze(linia);

		for (Integer i : listaIndeksowFalszywychSeparatorowNaLiscieSeparatorow) {
			listaSeparatorow.remove(i);
		}

		String nowySeparator = stworzNiepowtarzalnySeparatorOdpowiedniejDlugosci();

		StringBuilder liniaModyfikowana = new StringBuilder(linia);
		for (Integer i : listaIndeksowFalszywychSeparatorowNaLiscieSeparatorow) {
			liniaModyfikowana.replace((int) i, i + separator.length(),
					nowySeparator);
		}

		for (int i = listaSleszyDoUsuniecia.size() - 1; i >= 0; --i) {
			liniaModyfikowana.deleteCharAt(listaSleszyDoUsuniecia.get(i));
		}

		// ponizsza linijka dodana poniewaz na Stringuilderze nie ma split
		String przykladModified = new String(liniaModyfikowana);

		String[] tablica = przykladModified.split(bezpiecznySeparator);

		for (int i = 0; i < tablica.length; ++i) {
			tablica[i] = tablica[i].replaceAll(nowySeparator, separator);
		}

		return tablica;
	}

	/**
	 * aby zawartosc poprzednich operacji nie zakloci³a pracy nowych operacji
	 * konieczne jest wyczyszczenie list
	 */
	private void wyczyscListy() {
		listaSeparatorow.clear();
		listaIndeksowFalszywychSeparatorowNaLiscieSeparatorow.clear();
		listaSleszyDoUsuniecia.clear();
	}

	/**
	 * metoda ta zwraca indeksy wszystkich separatorow czyli nawet tych ktore
	 * tak na prawde separatorami nie sa poniewaz uzytkownik zdjal z nich ich
	 * specjalne znaczenie
	 * 
	 * @param linia
	 *            rozwazana linia tekstu
	 * @return indeksy pierwszych znakow separatorow w rozpatrywanej linii
	 */
	private LinkedList<Integer> zwrocIndeksyWszystkichSeparatorow(String linia) {
		LinkedList<Integer> lista = new LinkedList<Integer>();

		int aktualnyKoncowyIndeks = linia.length();

		while (true) {
			if (aktualnyKoncowyIndeks > 0) {
				aktualnyKoncowyIndeks = linia.lastIndexOf(separator,
						aktualnyKoncowyIndeks - 1);

				if ((aktualnyKoncowyIndeks != -1)
						&& ((lista.isEmpty()) || (aktualnyKoncowyIndeks != lista
								.peek())))
					lista.push(aktualnyKoncowyIndeks);
				else
					break;
			} else
				break;
		}

		return lista;
	}

	/**
	 * jesli dany znaczenie jakiegos separatora zostalo usuniete przez bekslesze
	 * nalezy jeden z tych beksleszy usunac
	 * 
	 * @param indexSeparatora
	 *            index w Stringu separatora przed ktorym beda szukane bekslesze
	 * @param linia
	 *            rozwazana linia tekstu
	 * @return indeks slesza do usuniecia
	 */
	private int wyswietlIndexPierwszegoBeksleszaPrzedSeparatorem(
			int indexSeparatora, String linia) {
		if ((indexSeparatora == 0)
				|| (linia.charAt(indexSeparatora - 1) != '\\'))
			return -1;
		else {
			int indexZwracany = indexSeparatora - 1;
			while (linia.charAt(indexZwracany - 1) == '\\')
				--indexZwracany;
			return indexZwracany;
		}

	}

	/**
	 * aby odroznic prawdziwe separatory od tych ktore sa zwyklym tekstem
	 * podstawiam za te drugie znaki null
	 * 
	 * @return nowo-stworzony separator
	 */
	private String stworzNiepowtarzalnySeparatorOdpowiedniejDlugosci() {
		StringBuilder buforek = new StringBuilder();
		for (int i = 0; i < dlugoscSeparatora; ++i) {
			buforek.append(znakUnikalny);
		}
		return buforek.toString();
	}

	/**
	 * metoda bada przed ktorymi separatorami wystepowaly bekslesze i na tej
	 * podstawie
	 * 
	 * @param linia
	 */
	private void ZbadajPrawdziwoscSeparatorowOznaczUsuwaneBekslesze(String linia) {
		int index;
		for (int i = 0; i < listaSeparatorow.size(); ++i) {
			if ((index = wyswietlIndexPierwszegoBeksleszaPrzedSeparatorem(
					listaSeparatorow.get(i), linia)) != -1) {
				listaSleszyDoUsuniecia.push(index);
				listaIndeksowFalszywychSeparatorowNaLiscieSeparatorow
						.push(listaSeparatorow.get(i));
			}
		}
	}
}
