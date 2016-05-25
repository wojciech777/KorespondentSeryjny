package pl.edu.agh.kis.korespondencja;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class PowielaczSzablonu dostarcza metod ktore pozwalaja odpowiednio powielic
 * plik wzorcowy tj. szablon implementuje interfejs InterPowielaczSzablonu
 * 
 * @author Administrator
 * 
 */
public class PowielaczSzablonu implements InterPowielaczSzablonu {
	public ZbiorEgzemplarzy daneEgzemplarza;
	private ArrayList<String> calaZawartosc;
	private ArrayList<String[]> calaZawartoscRozsep;
	private String[] zmienne;
	private String adresSzablonu;
	private String rozszerzenieSzablonu;
	
	private CzytaczPlikow narzedzieDoOdczytuPliku = new CzytaczPlikow();

	/**
	 * konstruktor inicjalizuje wartosci pol za pomoca danych zawartych w
	 * plikach ktorych adresy zostaly przkazane jako argumenty konstruktora
	 * 
	 * @param adresSzablonu
	 *            - adres Pliku zawierajacego dane wzorcowe (tzw. szablon)
	 * @param adresEgzemplarzy
	 *            - adres pliku zaweirajacego kolejne egzemplarze danych
	 * @param separator
	 *            separator jakim zmienne w egzemplarzu sa oddzielone od siebie
	 * @throws IOException
	 *             mozliwe wyrzucenie wyjatku w trakcie dzialania programu
	 */
	public PowielaczSzablonu(String adresSzablonu, String adresEgzemplarzy,
			String separator) throws IOException {
		this.adresSzablonu = adresSzablonu;
		rozszerzenieSzablonu = sprawdzRozszerzenie(adresSzablonu);

		przygotujFolderWynikowy();

		daneEgzemplarza = new ZbiorEgzemplarzy(adresEgzemplarzy, separator);
		calaZawartosc = narzedzieDoOdczytuPliku.wczytajPlikLiniami(adresSzablonu);
		calaZawartoscRozsep = new ArrayList<String[]>();

		zmienne = daneEgzemplarza.getOrderedVarNames();
	}

	/**
	 * wczytuje plik wzorcowy z zachowaniem wszystkich bialych znakow tzn liczba
	 * i rodzaj bialych znakow pomiedzy wyrazami w plikach wynikowych sa takie
	 * same jak w pliku wzorcowym (tj. szablonie)
	 */
	public void rozseparujDokladnie() {
		boolean wSpacjach, wWyrazie;
		String[] liniaRozseparowana = null;
		ArrayList<Integer> lista = null;
		ArrayList<String> lista2 = null;

		for (String linia : calaZawartosc) {
			wSpacjach = false;
			wWyrazie = false;

			lista = new ArrayList<Integer>();

			for (int i = 0; i < linia.length(); ++i) {
				if (Character.isWhitespace(linia.charAt(i))) {
					if (!wSpacjach) {
						wSpacjach = true;
						wWyrazie = false;
						lista.add(i);
					}
				} else {
					if (!wWyrazie) {
						wSpacjach = false;
						wWyrazie = true;
						lista.add(i);
					}
				}
			}

			lista.add(linia.length());

			lista2 = new ArrayList<String>();

			for (int i = 0; i < lista.size() - 1; ++i)
				lista2.add(linia.substring(lista.get(i), lista.get(i + 1)));

			liniaRozseparowana = new String[lista2.size()];
			for (int i = 0; i < liniaRozseparowana.length; ++i) {
				liniaRozseparowana[i] = lista2.get(i);
			}

			calaZawartoscRozsep.add(liniaRozseparowana);
		}
	}

	/**
	 * wczytuje plik wzorcowy bez zachowania bialych znakow tzn liczba i rodzaj
	 * bialych znakow pomiedzy wyrazami w plikach wynikowych sa redukowane do
	 * jednej spacji niezaleznie od tego jakie biale znaki wystepuja w pliku
	 * wzorcowym
	 */
	public void rozseparujWytnijBialeZnaki() {
		String[] bufor;
		String[] tablicaWynik;
		int licznik;
		int m;
		for (String s : calaZawartosc) {
			bufor = s.split("[\\s]+");
			licznik = 0;

			for (int i = 0; i < bufor.length; ++i)
				if (!bufor[i].equals("")) {
					++licznik;
				}

			tablicaWynik = new String[2 * licznik];

			m = 0;

			for (String str : bufor)
				if (!str.equals("")) {
					tablicaWynik[m] = str;
					tablicaWynik[m + 1] = " ";
					++m;
					++m;
				}

			calaZawartoscRozsep.add(tablicaWynik);
		}
	}

	/**
	 * podstawia odpowiednie zmienne w celu powielenia pliku wzorcowego oraz
	 * zapisania wyniku swojej pracy w jednym pliku wynik.txt ktory znajduje sie
	 * w folderze glownym projektu
	 * 
	 * @throws IOException
	 *             mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	public void podstawZapiszDaneJedenPlik() throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter("./wynik/wynik."
				+ rozszerzenieSzablonu));
		String[] wektorDanych;
		while ((wektorDanych = daneEgzemplarza.getNextCorrectData()) != null) {
			for (String[] s : calaZawartoscRozsep) {
				for (String wyraz : s) {
					if (wyraz.equals(interpretujWyraz(wyraz)))
						for (int i = 0; i < zmienne.length; ++i) {
							if (wyraz.equals(zmienne[i])) {
								wyraz = wektorDanych[i];
								break;
							}
						}
					else
						wyraz = interpretujWyraz(wyraz);

					bw.write(wyraz);
				}
				bw.newLine();
			}
			bw.write("---------------------------------------------------");
			bw.newLine();
		}
		bw.close();
	}

	/**
	 * podstawia odpowiednie zmienne w celu powielenia pliku wzorcowego oraz
	 * zapisania wyniku swojej pracy w wielu plikach wynik@.txt gdzie @ to
	 * kolejna liczba naturalna dodatnia ktore znajduja sie w folderze glownym
	 * projektu
	 * 
	 * @throws IOException
	 *             mozliwe wyrzucenie wyjatku w trakcie trwania programu
	 */
	public void podstawZapiszDaneWielePlikow() throws IOException {
		BufferedWriter bw = null; // tutaj zaSztywno
		String[] wektorDanych;
		String nazwaPlikuWynikowego = null;
		int licznik = 0;
		while ((wektorDanych = daneEgzemplarza.getNextCorrectData()) != null) {
			++licznik;
			nazwaPlikuWynikowego = "./wynik/wynik" + Integer.toString(licznik)
					+ "." + rozszerzenieSzablonu;
			bw = new BufferedWriter(new FileWriter(nazwaPlikuWynikowego));
			for (String[] s : calaZawartoscRozsep) {
				for (String wyraz : s) {
					if (wyraz.equals(interpretujWyraz(wyraz)))
						for (int i = 0; i < zmienne.length; ++i) {
							if (wyraz.equals(zmienne[i])) {
								wyraz = wektorDanych[i];
								break;
							}
						}
					else
						wyraz = interpretujWyraz(wyraz);

					bw.write(wyraz);
				}
				bw.newLine();
			}
			bw.close();
		}
	}

	/**
	 * metoda zwraca adres pliku szablonu z ktory jest wzorcem do powstania
	 * innych plikow
	 * 
	 * @return adres pliku szablonu w postaci String
	 */
	public String zwrocAdresSzablonu() {
		return adresSzablonu;
	}

	/**
	 * przygotowuje nowy czysty folder w ktorym umieszczone beda wyniki
	 * dzialania programu
	 */
	private void przygotujFolderWynikowy() {
		File folder = new File("./wynik");
		if (!(folder.mkdir())) {
			File[] tablica = folder.listFiles();
			if(tablica != null)
			for (File f : tablica) {
				try {
					f.delete();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		}
	}

	/**
	 * metoda interpretuje kazdy wyraz znaleziony w pliku szablonu interpretuje
	 * tzn. sprawdza czy jest to normalny wyraz czy zmienna oraz czy wystepuja w
	 * nim znaki szczegolne \ oraz '
	 * 
	 * @param wyraz
	 * @return
	 */
	private static String interpretujWyraz(final String wyraz) {
		StringBuilder wynik = new StringBuilder(wyraz);
		if (wyraz.length() > 1) {
			if (wyraz.charAt(0) == '\\') {
				wynik.deleteCharAt(0);
			} else if ((wyraz.charAt(0) == '\'')
					&& (wyraz.charAt(wyraz.length() - 1) == '\'')) {
				wynik.deleteCharAt(0);
				wynik.deleteCharAt(wynik.length() - 1);
			}
		}
		return wynik.toString();
	}

	/**
	 * prawdza rozszerzenie pliku zadanego jako argument
	 * 
	 * @param adresPliku
	 *            adres pliku ktory bedzie rozpatrywany pod wzgledem
	 *            rozszerzenia
	 * @return rozszerzenie zadanego pliku
	 */
	private String sprawdzRozszerzenie(String adresPliku) {
		String[] tablica = adresPliku.split("\\.");

		return tablica[tablica.length - 1];
	}
}
