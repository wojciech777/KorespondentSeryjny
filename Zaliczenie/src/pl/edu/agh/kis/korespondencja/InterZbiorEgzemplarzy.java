package pl.edu.agh.kis.korespondencja;

import java.util.LinkedList;
/**
 * dostarcza metod ktore pozwalaja w odpowiedni sposob wczytac dane egzemplarza
 * @author Administrator
 *
 */
public interface InterZbiorEgzemplarzy {
	/**
	 * zwraca kolejny zestaw prawidlowych danych
	 * 
	 * @return String[] jesli istnieja jeszcze jakies prawidlowe dane do
	 *         odczytania null w przeciwnym wypadku
	 */
	String[] getNextCorrectData();

	/**
	 * zwraca kolejny zestaw nieprawidlowych danych
	 * 
	 * @return String[] jesli istnieja jeszcze jakies nieprawidlowe dane do
	 *         odczytania null w przeciwnym wypadku
	 */
	String[] getNextIncorrectData();

	/**
	 * zwraca nazwy zmiennych w kolejnosci wystepowania w pliku poprzedzone
	 * znakiem $
	 * 
	 * @return String[] -tablica zawierajaca nazwy zmiennych 
	 */
	String[] getOrderedVarNames();

	/**
	 * zwraca separator oddzielajacy dane w pliku egzemplarzy
	 * @return String - separator oddzielajacy dane w pliku egzemplarzy
	 */
	String getSeparator();

	/**
	 * zwraca indeksy nieprawidlowych linii w kolejnosci rosnacej
	 * @return indeksy nieprawidlowych linii w kolejnosci rosnacej
	 */
	LinkedList<Integer> zwrocIndeksyLiniiNieprawidlowych();

	/**
	 * zwraca liczbe wszystkich (prawidlowych i nieprawidlowych) egzemplarzy
	 * @return liczba wszystkich egzemplarzy
	 */
	int zwrocLiczbeWszysktichEgzemplarzy();

	/**
	 * zwraca liczbe wszystkich prawidlowych egzemplarzy
	 * @return liczba wszystkich prawidlowych egzemplarzy
	 */
	int zwrocLiczbePrawidlowychEgzemplarzy();

	/**
	 * zwraca liczbe wszystkich nieprawidlowych egzemplarzy
	 * @return liczba wszystkich nieprawidlowych egzemplarzy
	 */
	int zwrocLiczbeNieprawidlowychEgzemplarzy();
	
	/**
	 * zwraca adres pliku Egzemplarzy skad odczytywane byly dane 
	 * @return adres pliku Egzemplarzy w postaci String
	 */
	String zwrocAdresEgzemplarzy();
}
