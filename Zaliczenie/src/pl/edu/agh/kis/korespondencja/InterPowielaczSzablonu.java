package pl.edu.agh.kis.korespondencja;

import java.io.IOException;
/**
 * dostarcza metod ktore pozwalaja w wybrany sposob powielic plik wzorcowy
 * @author Administrator
 *
 */
public interface InterPowielaczSzablonu {
	/**
	 * wczytuje plik wzorcowy z zachowaniem wszystkich bialych znakow tzn liczba
	 * i rodzaj bialych znakow pomiedzy wyrazami w plikach wynikowych sa takie
	 * same jak w pliku wzorcowym (tj. szablonie)
	 */
	void rozseparujDokladnie();

	/**
	 * wczytuje plik wzorcowy bez zachowania bialych znakow tzn liczba i rodzaj
	 * bialych znakow pomiedzy wyrazami w plikach wynikowych sa redukowane do jednej 
	 * spacji niezaleznie od tego jakie biale znaki wystepuja w pliku wzorcowym
	 */
	void rozseparujWytnijBialeZnaki();

	/**
	 * podstawia odpowiednie zmienne w celu powielenia pliku wzorcowego
	 * oraz zapisania wyniku swojej pracy w jednym pliku wynik.txt
	 * ktory znajduje sie w folderze glownym projektu 
	 * @throws IOException mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	void podstawZapiszDaneJedenPlik() throws IOException;

	/**
	 * podstawia odpowiednie zmienne w celu powielenia pliku wzorcowego
	 * oraz zapisania wyniku swojej pracy w wielu plikach wynik@.txt
	 * gdzie @ to kolejna liczba naturalna dodatnia 
	 * ktore znajduja sie w folderze glownym projektu 
	 * @throws IOException mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	void podstawZapiszDaneWielePlikow() throws IOException;
	
	/**
	 * metoda zwraca adres pliku szablonu z ktory jest wzorcem do powstania
	 * innych plikow
	 * 
	 * @return adres pliku szablonu w postaci String
	 */
	String zwrocAdresSzablonu(); 
}
