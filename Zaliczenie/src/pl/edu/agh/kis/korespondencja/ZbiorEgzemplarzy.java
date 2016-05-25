package pl.edu.agh.kis.korespondencja;

import java.io.IOException;
import java.util.LinkedList;

/**
 * class ZbiorEgzemplarzy wczytuje odpowiednio dane zawarte w pliku egzemplarza
 * implementuje InterZbiorEgzemplarzy
 * 
 * @author Bomba Wojciech
 * 
 */
public class ZbiorEgzemplarzy implements InterZbiorEgzemplarzy {
	private LinkedList<String> zawartoscPliku;
	private LinkedList<String[]> wszystkieEgzemplarze;
	private LinkedList<String[]> poprawneEgzemplarze;
	private LinkedList<String[]> niepoprawneEgzemplarze;
	private String[] orderedVarNames;
	private int liczbaZmiennych;
	private int liczbaOdczytanychPoprawnych = 0;
	private int liczbaOdczytanychNiepoprawnych = 0;
	private int liczbaPoprawnych;
	private int liczbaNiepoprawnych;
	private LinkedList<Integer> indeksyNieprawidlowychLinii;
	private String separator;
	private String adresEgzemplarzy;
	
	private InterpreterLiniiDanych interpreterLiniiDanych;
	
	private CzytaczPlikow narzedzieDoOdczytuPliku = new CzytaczPlikow();

	/**
	 * konstruktor inicjalizuje wartosci pol odcztujac odpowiednie wartosci
	 * oddzielone separatorem z podanego pliku
	 * 
	 * @param adres
	 *            adres pliku z egzemplarzami
	 * @param separator
	 *            separator ktotym dane sa oddzielone w pliku
	 * @throws IOException
	 *             mozliwe wyrzucenie tego wyjatku w trakcie dzialania
	 *             konstruktora
	 */
	public ZbiorEgzemplarzy(String adres, String separator) throws IOException {
		adresEgzemplarzy = adres;
		interpreterLiniiDanych = new InterpreterLiniiDanych(separator);
		this.separator = separator;
		poprawneEgzemplarze = new LinkedList<String[]>();
		niepoprawneEgzemplarze = new LinkedList<String[]>();
		indeksyNieprawidlowychLinii = new LinkedList<Integer>();

		zawartoscPliku = new LinkedList<String>(narzedzieDoOdczytuPliku.wczytajPlikLiniami(adres));

		//orderedVarNames = zawartoscPliku.get(0).split(this.separator);
		orderedVarNames = interpreterLiniiDanych.zwrocZmienne(zawartoscPliku.get(0));
		liczbaZmiennych = orderedVarNames.length;
		zawartoscPliku.removeFirst();

		dodajDolary(orderedVarNames);

		wszystkieEgzemplarze = wytnijZmienne(zawartoscPliku);

		for (int i = 0; i < wszystkieEgzemplarze.size(); ++i) {
			if (wszystkieEgzemplarze.get(i).length != liczbaZmiennych) {
				niepoprawneEgzemplarze.add(wszystkieEgzemplarze.get(i));
				indeksyNieprawidlowychLinii.add(i + 2);
			} else {
				poprawneEgzemplarze.add(wszystkieEgzemplarze.get(i));
			}
		}

		liczbaPoprawnych = poprawneEgzemplarze.size();
		liczbaNiepoprawnych = niepoprawneEgzemplarze.size();
	}

	/**
	 * zwraca kolejny zestaw prawidlowych danych
	 * 
	 * @return String[] jesli istnieja jeszcze jakies prawidlowe dane do
	 *         odczytania null w przeciwnym wypadku
	 */
	public String[] getNextCorrectData() {
		if (liczbaOdczytanychPoprawnych < liczbaPoprawnych) {
			++liczbaOdczytanychPoprawnych;
			return poprawneEgzemplarze.get(liczbaOdczytanychPoprawnych - 1);
		} else
			return null;
	}

	/**
	 * zwraca kolejny zestaw nieprawidlowych danych
	 * 
	 * @return String[] jesli istnieja jeszcze jakies nieprawidlowe dane do
	 *         odczytania null w przeciwnym wypadku
	 */
	public String[] getNextIncorrectData() {
		if (liczbaOdczytanychNiepoprawnych < liczbaNiepoprawnych) {
			++liczbaOdczytanychNiepoprawnych;
			return niepoprawneEgzemplarze
					.get(liczbaOdczytanychNiepoprawnych - 1);
		} else
			return null;
	}

	/**
	 * zwraca nazwy zmiennych w kolejnosci wystepowania w pliku poprzedzone
	 * znakiem $
	 * 
	 * @return String[] -tablica zawierajaca nazwy zmiennych
	 */
	public String[] getOrderedVarNames() {
		return orderedVarNames;
	}

	/**
	 * zwraca separator oddzielajacy dane w pliku egzemplarzy
	 * 
	 * @return String - separator oddzielajacy dane w pliku egzemplarzy
	 */
	public String getSeparator() {
		return separator;
	}

	/**
	 * zwraca indeksy nieprawidlowych linii w kolejnosci rosnacej
	 * 
	 * @return indeksy nieprawidlowych linii w kolejnosci rosnacej
	 */
	public LinkedList<Integer> zwrocIndeksyLiniiNieprawidlowych() {
		return indeksyNieprawidlowychLinii;
	}

	/**
	 * zwraca liczbe wszystkich (prawidlowych i nieprawidlowych) egzemplarzy
	 * 
	 * @return liczba wszystkich egzemplarzy
	 */
	public int zwrocLiczbeWszysktichEgzemplarzy() {
		return liczbaPoprawnych + liczbaNiepoprawnych;
	}

	/**
	 * zwraca liczbe wszystkich prawidlowych egzemplarzy
	 * 
	 * @return liczba wszystkich prawidlowych egzemplarzy
	 */
	public int zwrocLiczbePrawidlowychEgzemplarzy() {
		return liczbaPoprawnych;
	}

	/**
	 * zwraca liczbe wszystkich nieprawidlowych egzemplarzy
	 * 
	 * @return liczba wszystkich nieprawidlowych egzemplarzy
	 */
	public int zwrocLiczbeNieprawidlowychEgzemplarzy() {
		return liczbaNiepoprawnych;
	}

	/**
	 * zwraca adres pliku Egzemplarzy skad odczytywane byly dane
	 * 
	 * @return adres pliku Egzemplarzy w postaci String
	 */
	public String zwrocAdresEgzemplarzy() {
		return adresEgzemplarzy;
	}

	/**
	 * interpretuje poszczegolne linie dzielac je za pomoca separatora zwraca
	 * liste tablic String'ow ktore zawieraja juz rozseparowane zmienne
	 * 
	 * @param dane
	 *            lista Stringow do rozseparowania
	 * @return lista tablic String'ow ktore zawieraja juz rozseparowane zmienne
	 */
	private LinkedList<String[]> wytnijZmienne(LinkedList<String> dane) {
		LinkedList<String[]> zwrot = new LinkedList<String[]>();
		for (String s : dane) 
			zwrot.add(interpreterLiniiDanych.zwrocZmienne(s));
		
		return zwrot;
	}

	/**
	 * dodaje do zmiennych znak $ na poczatku
	 * 
	 * @param nazwyZmiennych
	 *            tablica Stringow do ktorych beda dodane dolary
	 */
	private void dodajDolary(String[] nazwyZmiennych) {
		for (int i = 0; i < nazwyZmiennych.length; ++i)
			nazwyZmiennych[i] = "$" + nazwyZmiennych[i];
	}

}
