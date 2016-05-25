package pl.edu.agh.kis.korespondencja;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * class UserFrame tworzy GUI i interpretuje zlecane z jego poziomu polecenia
 * 
 * @author Bomba Wojciech
 * 
 */
public class UserFrame extends JFrame implements ActionListener {
	private JTextField poleSzablon, poleEgzemplarz, poleSeparator;
	private JTextArea outputUzytkownika;
	private JButton przyciskWykonaj;
	private JCheckBox interpretacjaBialychZnakow, wielePlikow;
	private JLabel labelAdresSzablonu, labelAdresDanych, labelSeparator,
			labelwiadomosc;
	private boolean czyWielePlikow, czyZachowanieBialychZnakow;
	private String adresSzablon, adresEgzemplarze, separator;
	private PowielaczSzablonu korSeryjny;

	/**
	 * konstruktor ustawia poczatkowe pola GUI
	 */
	public UserFrame() {
		setSize(380, 470);
		setTitle("Korespondent Seryjny");
		setLayout(null);

		rozstawOkienka();
	}

	/**
	 * metoda interpretuje polecenie ktore zostalo zlecone do wykonania i
	 * zaleznie od tego jakie dane zostaly podane przez uzytkownika wykonuje
	 * odpowiednie dzialania i zwraca odpowiednie informacje ktore okreslaja co
	 * dalej powinien zrobic uzytkownik lub co ewentualnie poprawic aby uzyskac
	 * oczekikwany efekt
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object zrodlo = e.getSource();
		if (zrodlo == przyciskWykonaj) {
			if ((poleSzablon.getText().length() != 0)
					&& (poleEgzemplarz.getText().length() != 0)
					&& (poleSeparator.getText().length() != 0)
					&& (new File(poleSzablon.getText()).isFile())
					&& (new File(poleEgzemplarz.getText()).isFile())
					&& (czyPoprawneRozszerzenieSzablonu(poleSzablon.getText()))
					&& (czyPoprawneRozszerzenieEgzemplarzy(poleEgzemplarz
							.getText()))
					&& (new File(poleSzablon.getText()).canRead())
					&& (new File(poleEgzemplarz.getText()).canRead())) {

				labelAdresSzablonu.setForeground(Color.BLACK);
				labelAdresDanych.setForeground(Color.BLACK);
				labelSeparator.setForeground(Color.BLACK);

				czyWielePlikow = wielePlikow.isSelected();
				czyZachowanieBialychZnakow = interpretacjaBialychZnakow
						.isSelected();
				adresSzablon = poleSzablon.getText();
				adresEgzemplarze = poleEgzemplarz.getText();
				separator = poleSeparator.getText();

				try {
					stworzSerieKorespondencji(adresSzablon, adresEgzemplarze,
							separator, czyWielePlikow,
							czyZachowanieBialychZnakow);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				stworzRaportPomyslnejPracy();

			} else {
				stworzRaportNiepomyslnejPracy();
			}
		}
	}

	/**
	 * metoda rozmieszcza okienka w GUI oraz nadaje im wartosci poczatkowe
	 */
	private void rozstawOkienka() {
		labelAdresSzablonu = new JLabel("*adres pliku szablonu ==>");
		labelAdresSzablonu.setBounds(20, 20, 160, 22);
		add(labelAdresSzablonu);

		labelAdresDanych = new JLabel("*adres pliku danych    ==>");
		labelAdresDanych.setBounds(20, 50, 160, 22);
		add(labelAdresDanych);

		/*
		 * labelSeparator = new
		 * JLabel("<html>*separator pliku <br>egzemplarzy :"); Font labelFont =
		 * labelSeparator.getFont(); labelSeparator.setFont(new
		 * Font(labelFont.getName(),labelFont.getStyle(), 11));
		 * labelSeparator.setBounds(20, 78, 160, 32); add(labelSeparator);
		 */

		labelSeparator = new JLabel("*separator pliku danych ==>");
		labelSeparator.setBounds(20, 80, 160, 22);
		add(labelSeparator);

		poleSzablon = new JTextField("");
		poleSzablon.setBounds(180, 20, 170, 22);
		add(poleSzablon);

		poleEgzemplarz = new JTextField(".txt");
		poleEgzemplarz.setBounds(180, 50, 170, 22);
		add(poleEgzemplarz);

		poleSeparator = new JTextField(";");
		poleSeparator.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		poleSeparator.setBounds(180, 80, 25, 30);
		add(poleSeparator);

		interpretacjaBialychZnakow = new JCheckBox(
				"<html>   Zachowaj<br/>Biale Znaki", true);
		interpretacjaBialychZnakow.setBounds(180, 110, 100, 40);
		add(interpretacjaBialychZnakow);

		wielePlikow = new JCheckBox("Wiele Plikow", true);
		wielePlikow.setBounds(180, 150, 100, 30);
		add(wielePlikow);

		przyciskWykonaj = new JButton("wykonaj");
		przyciskWykonaj.setBounds(180, 180, 100, 40);
		add(przyciskWykonaj);
		przyciskWykonaj.addActionListener(this);

		outputUzytkownika = new JTextArea();
		outputUzytkownika.setEditable(false);
		outputUzytkownika.setBounds(20, 180, 150, 210);
		add(outputUzytkownika);

		labelwiadomosc = new JLabel("* - pole obowi¹zkowe");
		labelwiadomosc.setBounds(220, 400, 160, 22);
		add(labelwiadomosc);
	}

	/**
	 * metoda wywolywana jesli polecenie zlecone do wykonania bylo prawidlowe
	 * jednoczesnie interpretujac odpowiednio rodzaj polecenia czyli jakie
	 * checkboxy zostaly zaznaczone
	 * 
	 * @param adresSzablon
	 *            adres pliku szablonu
	 * @param adresEgzemplarze
	 *            adres pliku egzemplarzy
	 * @param separator
	 *            separator pliku egzemplarzy
	 * @param czyWielePlikow
	 *            boolean okreslajacy czy stan checkboxa wielePlikow
	 * @param czyZachowanieBialychZnakow
	 *            boolean okreslajacy czy stan checkboxa
	 *            interpretacjaBialychZnakow
	 * @throws IOException
	 */
	private void stworzSerieKorespondencji(String adresSzablon,
			String adresEgzemplarze, String separator, boolean czyWielePlikow,
			boolean czyZachowanieBialychZnakow) throws IOException {

		korSeryjny = new PowielaczSzablonu(adresSzablon, adresEgzemplarze,
				separator);

		if (czyZachowanieBialychZnakow)
			korSeryjny.rozseparujDokladnie();
		else
			korSeryjny.rozseparujWytnijBialeZnaki();

		if (czyWielePlikow)
			korSeryjny.podstawZapiszDaneWielePlikow();
		else
			korSeryjny.podstawZapiszDaneJedenPlik();
	}

	/**
	 * metoda wypisuje w odpowidnim okienku GUI informacje koncowe po
	 * przeprowadzeniu pomyslnego powielania pliku wejsciowego
	 */
	private void stworzRaportPomyslnejPracy() {
		outputUzytkownika.setText("polecenie zostalo\nwykonane pomyslnie");

		LinkedList<Integer> indeksyNieprawidlowychLinii;

		int liczbaWszystkich, liczbaPrawidlowych, liczbaNieprawidlowych;

		liczbaWszystkich = korSeryjny.daneEgzemplarza
				.zwrocLiczbeWszysktichEgzemplarzy();
		liczbaPrawidlowych = korSeryjny.daneEgzemplarza
				.zwrocLiczbePrawidlowychEgzemplarzy();
		liczbaNieprawidlowych = korSeryjny.daneEgzemplarza
				.zwrocLiczbeNieprawidlowychEgzemplarzy();

		outputUzytkownika.append("\nwczytano " + liczbaWszystkich
				+ " egzemplarzy");
		outputUzytkownika.append("\nprawidlowych bylo " + liczbaPrawidlowych);
		outputUzytkownika.append("\nnieprawidlowych bylo "
				+ liczbaNieprawidlowych);
		if (liczbaNieprawidlowych != 0) {
			indeksyNieprawidlowychLinii = korSeryjny.daneEgzemplarza
					.zwrocIndeksyLiniiNieprawidlowych();
			outputUzytkownika.append("\nnieprawdidlowe dane byly\nw pliku\n"
					+ adresEgzemplarze + "\nw liniach:");
			for (Integer i : indeksyNieprawidlowychLinii) {
				outputUzytkownika.append(i + " ");
			}
		}
	}

	/**
	 * metoda podswietla a czerwono odpowiednie pola oraz wypisuje odpowiednie
	 * komunikaty ktore powinny naprowadzic uzytkownika na odpowiedz dlaczego
	 * wykonanie polecenia nie moglo dojsc do skutku
	 */
	private void stworzRaportNiepomyslnejPracy() {
		outputUzytkownika.setText("polecenie nie zostalo\nwykonane pomyslnie"
				+ "\nze wzgledu na bledy\ndanych wejsciowych");

		if (poleSeparator.getText().length() == 0) {
			outputUzytkownika.append("\nnie podano\nseparatora");
			labelSeparator.setForeground(Color.RED);
		} else
			labelSeparator.setForeground(Color.BLACK);

		if (poleSzablon.getText().length() == 0) {
			outputUzytkownika.append("\nnie podano\npliku szablonu");
			labelAdresSzablonu.setForeground(Color.RED);
		} else if (!(new File(poleSzablon.getText()).isFile())) {
			outputUzytkownika.append("\nplik szablonu\nnie istnieje");
			labelAdresSzablonu.setForeground(Color.RED);
		} else if (!czyPoprawneRozszerzenieSzablonu(poleSzablon.getText())) {
			outputUzytkownika
					.append("\nbledne rozszerzenie pliku \nszablonu tylko .txt .html .css");
			labelAdresSzablonu.setForeground(Color.RED);
		} else if (!(new File(poleSzablon.getText()).canRead())) {
			outputUzytkownika.append("\nplik szablonu\nnie ma praw odczytu");
			labelAdresSzablonu.setForeground(Color.RED);
		} else
			labelAdresSzablonu.setForeground(Color.BLACK);

		if (poleEgzemplarz.getText().length() == 0) {
			outputUzytkownika.append("\nnie podano\npliku danych");
			labelAdresDanych.setForeground(Color.RED);
		} else if (!(new File(poleEgzemplarz.getText()).isFile())) {
			outputUzytkownika.append("\nplik danych\nnie istnieje");
			labelAdresDanych.setForeground(Color.RED);
		} else if (!czyPoprawneRozszerzenieEgzemplarzy(poleEgzemplarz.getText())) {
			outputUzytkownika
					.append("\nbledne rozszerzenie pliku \negzemplarzy tylko .txt");
			labelAdresDanych.setForeground(Color.RED);
		} else if (!(new File(poleEgzemplarz.getText()).canRead())) {
			outputUzytkownika.append("\nplik danych\nnie ma praw odczytu");
			labelAdresDanych.setForeground(Color.RED);
		} else
			labelAdresDanych.setForeground(Color.BLACK);
	}

	/**
	 * sprawdza rozszerzenie pliku szablonu ktory moze byc jedynie .txt lub
	 * .html
	 * 
	 * @param adresPliku
	 * @return
	 */
	private boolean czyPoprawneRozszerzenieSzablonu(String adresPliku) {
		String[] tablica = adresPliku.split("\\.");

		if ((tablica[tablica.length - 1].equals("txt"))
				|| (tablica[tablica.length - 1].equals("html"))
				|| (tablica[tablica.length - 1].equals("css")))
			return true;
		return false;
	}

	/**
	 * sprawdza czy rozszerzenie pliku egzemplarzy jest zgodne z oczekiwanym
	 * czyli .txt
	 * 
	 * @param adresPliku
	 * @return
	 */
	private boolean czyPoprawneRozszerzenieEgzemplarzy(String adresPliku) {
		String[] tablica = adresPliku.split("\\.");

		if (tablica[tablica.length - 1].equals("txt"))
			return true;
		return false;
	}

	//pod spodem alternatywne rozwiazanie bez uzycia klasy Sterownik
/*
	public static void main(String[] args) {

		UserFrame apka = new UserFrame();
		apka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		apka.setVisible(true);
	}
*/
}
