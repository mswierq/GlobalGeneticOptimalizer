Marcin Œwierczek
Marcin Sodkiewicz

Automatyka i Robotyka, Politechnika Wroc³awska, 2013
=================================================================

====================== Jak uruchomiæ program ====================

Nale¿y wybraæ odpowiedni skrypt uruchamiaj¹cy, który znajduje siê
w œcie¿ce:

./GlobalGeneticOptimalizer/+runnable/

Uruchamiamy odpowiedni skrypt:

	- dla systemów Windows -> run-Windows.bat
	- dla systemów Linux   -> run-Linux.sh

=================== Uwagi do rysowania wykresów =================

Czerwone punkty oznaczj¹ najlepsze dopasowanie z ka¿dej iteracji,
zielony punkt oznacza najlepsze dopasowanie ze wszystkich
iteracji.

Je¿eli chcemy narysowaæ przekrój dla funkcji o wymiarze n > 2. To
zawsze pod uwagê brane s¹ dwa pierwsze przedzia³y, których d³ugoœæ
jest wiêksza ni¿ 0. Pozosta³e parametry, których d³ugoœæ zakresów
jest wiêksza od zera s¹ traktowane jako sta³e równe 0.

Mo¿na te¿ zadecydowaæ, które parametry maj¹ byæ traktowane jako
sta³e i wybraæ wartoœæ tej sta³ej. Wystarczy, ¿e w obu okienkach
tekstowych przeznaczonych do wpisania granic zakresu wpiszemy
t¹ sam¹ wartoœæ.

Przyk³ady:

#1#

x1 [ 1 ] [ 2 ]
x2 [ 3 ] [ 4 ]
x3 [ 1 ] [ 5 ]
x4 [ 3 ] [ 3 ]

Dla podanych wy¿ej parametrów x1 jest umiejcowiony na osi X
wykresu, a x2 na osi Y. Parametr x3 jest traktowany jako sta³a
równa zero. X4 jest sta³¹ o wartoœci 3, wybran¹ przez u¿ytkownika.

#2#

x1 [ 1 ] [ 2 ]
x2 [ 3 ] [ 3 ]
x3 [ 1 ] [ 5 ]
x4 [ 4 ] [ 7 ]

Dla podanych wy¿ej parametrów x1 jest umiejcowiony na osi X
wykresu, a x3 na osi Y. Parametr x4 jest traktowany jako sta³a
równa zero. X2 jest sta³¹ o wartoœci 3, wybran¹ przez u¿ytkownika.