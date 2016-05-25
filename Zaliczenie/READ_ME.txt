Program ten potrafi podstawic zmienne do pliku wzorcowego, i tym sposobem powielic plik wzorcowy.
Aby operacja doszla do skutku nalezy podac adresy ww. plikow w odpowiednich polach GUI ,co wiecej zawartosc oraz typ ww. plikow musi spelniac wymagania spisane ponizej.

Zmienne w pliku wzorcowym musza koniecznie byc poprzedzone znakiem $.
Jeœli uzytkownik chce usunac specjalne znaczenie tegoz znaku wystarczy ze postawi przed nim znak \ albo cale wyrazenie w ktorym nie chcemy interpretacji znakow specjalnych ujmie w apostrofy ''.
Wykorzysujac kombinacje \ oraz '' mozliwe jest wyswietlenie dowolnego tekstu np \$proba1 '$proba2' '\$proba3' \'$proba4' ' \

Program obsluguje jedynie rozszerzenia plikow : .txt .html .css ;zadne inne rozszerzenie pliku wzorcowego nie bedzie przyjmowane przez program. Rozszerzenie pliku/ow wyjsciowego jest takie samo jak pliku wejsciowego.
Plik/i wyjsciowe znaduja sie w folderze wynik ktory z kolei znajduje sie w folderze glownym projektu

Plik egzemplarzy powinien wygladac nastepujaco:
w piewszej linii oddzielone *separatorem nazwy zmiennych
W nastepnych liniach oddzielone separatorem wartosci kolejnych zmiennych
Jesli uzytkownik chce usunac specjalne znaczenie separatora wystarczy ze postawi przed nim dowolna ilosc backslashy'y z których jeden bedzie usuniety.Dzieki temu mechanizmowi mozliwe jest wprowadzenie przez uzytkownika dowolnego tekstu(mozliwe jest nawet podanie separatora poprzedzonego backslashem jako tekst)
Uwaga: linie ktore zawieraja inna liczbe danych niz oczekiwana sa uznawane za niepoprawne a ich indeks w pliku jest wyswietlany w koncowych komunikatach dla uzytkownika
*separator jest przekazywany za pomoca GUI (jest to String ktory oddziela dane w pliku danych/egzemplarzy ,domyslnie srednik)

Zaleznie od tego czy w GUI zostanie zaznaczony checkbox wiele plikow wynik dzialania programu bedzie zachowany w wielu plikach wynik@ ,gdzie @ to kolejna liczba calkowita(liczba wszystkich plikow bedzie rowna liczbie prawidlowych egzemplarzy podanych w pliku egzemplarzy; badz w jednym pliku zas kolejne egzemplarze oddzielone sa linia "---------------"

Zaleznie od tego  czy w GUI zaznaczony zostanie checkbox Zachowaj Biale znaki efekt koncowy bedzie sie roznil nastepujaco:
zachowanie bialych znakow oznacza ze ilosc i rodzaj bialych znakow pomiedzy poszczeglnymi wyrazami bedzie zachowany zas odznaczenie tego checkboxa bedzie skutkowalo zmniejszeniem ilosci bialych znakow pomiedzy poszczegolnymi wyrazami do jednej spacji niezaleznie od tego ile i jakie biale znaki znajduja sie w pliku wzorcowym.

Wprowadzenie w GUI danych ktore beda uniemozliwialy wykonanie programu np brak wypelnienia pola, wpisanie pliku ktory nie istnieje wpisanie pliku z nieodpowiednim rozszerzeniem , wpisanie pliku do ktorego nie ma praw odczytu: bedzie   skutkowalo brakiem wykonania sie programu, odpowiednim komunikatem wypisanym dla uzytkownika oraz podswietleniem obszaru z nieprawidlowymi danymi na czerwono

--------------------------------------------------------
--------------------------------------------------------

Najwa¿niejszymi klasami w projekcie sa klasy:
ZbiorEgzemplarzy
PowielaczSzablonu
UserFrame
W celu sprawdzenia dokumentacji oraz sposobu dzialania ww. klas nalezy zajrzec do kodu oraz dokumentacji Javadoc gdyz jest to dosc obszerna lektura i trudno byloby chocby w skrocie opisac ich dzialanie

klasa InterpreterLiniiDanych 
   dostarcza jedynie odpowiednich operacji na tekscie ktore sa niezbedne do prawidlowego dzialania programu

klasa CzytaczPlikow
   dostarcza jedynie metode ktora pozwala odpowiednio wczytac zadany plik i zwrocic jego wartosc

klasa Sterownik
   jedynie uruchamia caly program poprzez stworzenie okienka GUI

klasa PowielaczTester
   dostarcza jedynie testow ktore sprawdzaja poprawnosc dzialania poszczegolnych elementow programu

Wykonal Bomba Wojciech