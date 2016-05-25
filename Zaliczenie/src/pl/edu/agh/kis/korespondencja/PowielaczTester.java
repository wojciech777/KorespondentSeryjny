package pl.edu.agh.kis.korespondencja;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.junit.Test;

/**
 * class PowielaczTester testuje dzialanie calego projektu sprawdzajac czy
 * wartosci zwracane przez poszczegolne funkcje sa zgodne z oczekiwanymi
 * 
 * @author Administrator
 * 
 */
public class PowielaczTester {

	private CzytaczPlikow narzedzieDoOdczytuPliku = new CzytaczPlikow();

	@Test
	public void test1() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean zgodny = true;
		if(zbior != null) {
			String[] tablica = zbior.getNextCorrectData();
			String[] wzor = { "wojtek", "bomba", "20" };
			

			for (int i = 0; i < tablica.length; ++i)
				if (!tablica[i].equals(wzor[i])) {
					zgodny = false;
					break;
				}
		}
		else zgodny = false;
		
		assertEquals(zgodny, true);
	}

	@Test
	public void test2() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] tablica = zbior.getNextCorrectData();
		tablica = zbior.getNextCorrectData();
		String[] wzor = { "OLA", "NIEDUZAK", "45" };
		boolean zgodny = true;

		for (int i = 0; i < tablica.length; ++i)
			if (!tablica[i].equals(wzor[i])) {
				zgodny = false;
				break;
			}
		assertEquals(zgodny, true);
	}

	@Test
	public void test3() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] tablica = zbior.getNextIncorrectData();
		String[] wzor = { "wojtek", "bomba" };
		boolean zgodny = true;

		for (int i = 0; i < tablica.length; ++i)
			if (!tablica[i].equals(wzor[i])) {
				zgodny = false;
				break;
			}
		assertEquals(zgodny, true);
	}

	@Test
	public void test4() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] tablica = zbior.getNextIncorrectData();
		tablica = zbior.getNextIncorrectData();
		String[] wzor = { "wojtek" };
		boolean zgodny = true;

		for (int i = 0; i < tablica.length; ++i)
			if (!tablica[i].equals(wzor[i])) {
				zgodny = false;
				break;
			}
		assertEquals(zgodny, true);
	}

	@Test
	public void test5() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String separator = ";";
		assertEquals(separator, zbior.getSeparator());
	}

	@Test
	public void test6() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] tablica = zbior.getOrderedVarNames();

		String[] wzor = { "$imie", "$nazwisko", "$wiek" };
		boolean zgodny = true;

		for (int i = 0; i < tablica.length; ++i)
			if (!tablica[i].equals(wzor[i])) {
				zgodny = false;
				break;
			}
		assertEquals(zgodny, true);
	}

	@Test
	public void test7() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LinkedList<Integer> lista = zbior.zwrocIndeksyLiniiNieprawidlowych();

		LinkedList<Integer> wzor = new LinkedList<Integer>();
		wzor.add(2);
		wzor.add(3);
		boolean zgodny = true;

		for (int i = 0; i < lista.size(); ++i)
			if (!(lista.get(i) == wzor.get(i))) {
				zgodny = false;
				break;
			}
		assertEquals(zgodny, true);
	}

	@Test
	public void test8() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int liczbaLiniiPrawidlowych = zbior
				.zwrocLiczbePrawidlowychEgzemplarzy();
		int wzor = 2;

		assertEquals(liczbaLiniiPrawidlowych, wzor);
	}

	@Test
	public void test9() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int liczbaLiniiPrawidlowych = zbior
				.zwrocLiczbeNieprawidlowychEgzemplarzy();
		int wzor = 2;

		assertEquals(liczbaLiniiPrawidlowych, wzor);
	}

	@Test
	public void test10() {
		ZbiorEgzemplarzy zbior = null;
		try {
			zbior = new ZbiorEgzemplarzy("plikiTestow/Test1_10.txt", ";");
		} catch (IOException e) {
			e.printStackTrace();
		}
		int liczbaLiniiPrawidlowych = zbior.zwrocLiczbeWszysktichEgzemplarzy();
		int wzor = 4;

		assertEquals(liczbaLiniiPrawidlowych, wzor);
	}

	@Test
	public void test11() {
		PowielaczSzablonu powielaczSzablonu = null;
		try {
			powielaczSzablonu = new PowielaczSzablonu(
					"plikiTestow/szablonTest11.txt",
					"plikiTestow/daneTest11.txt", "+");
		} catch (IOException e) {
			e.printStackTrace();
		}

		powielaczSzablonu.rozseparujDokladnie();

		try {
			powielaczSzablonu.podstawZapiszDaneJedenPlik();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> wzor = null;
		try {
			wzor = narzedzieDoOdczytuPliku
					.wczytajPlikLiniami("plikiTestow/wynikTest11.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> wynik = null;

		try {
			wynik = narzedzieDoOdczytuPliku
					.wczytajPlikLiniami("./wynik/wynik.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean zgodny = true;

		for (int i = 0; i < wzor.size(); ++i) {
			if (!(wzor.get(i).equals(wynik.get(i)))) {
				zgodny = false;
				break;
			}
		}

		assertEquals(zgodny, true);
	}

	@Test
	public void test12() {
		PowielaczSzablonu powielaczSzablonu = null;
		try {
			powielaczSzablonu = new PowielaczSzablonu(
					"plikiTestow/szablonTest12.txt",
					"plikiTestow/daneTest12.txt", "+");
		} catch (IOException e) {
			e.printStackTrace();
		}

		powielaczSzablonu.rozseparujWytnijBialeZnaki();

		try {
			powielaczSzablonu.podstawZapiszDaneJedenPlik();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> wzor = null;
		try {
			wzor = narzedzieDoOdczytuPliku
					.wczytajPlikLiniami("plikiTestow/wynikTest12.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		ArrayList<String> wynik = null;

		try {
			wynik = narzedzieDoOdczytuPliku
					.wczytajPlikLiniami("./wynik/wynik.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean zgodny = true;

		for (int i = 0; i < wzor.size(); ++i) {
			if (!(wzor.get(i).equals(wynik.get(i)))) {
				zgodny = false;
				break;
			}
		}

		assertEquals(zgodny, true);
	}
}
