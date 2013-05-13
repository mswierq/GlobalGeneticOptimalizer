Marcin �wierczek
Marcin Sodkiewicz

Automatyka i Robotyka, Politechnika Wroc�awska, 2013
=================================================================

====================== Jak uruchomi� program ====================

Nale�y wybra� odpowiedni skrypt uruchamiaj�cy, kt�ry znajduje si�
w �cie�ce:

./GlobalGeneticOptimalizer/+runnable/

Uruchamiamy odpowiedni skrypt:

	- dla system�w Windows -> run-Windows.bat
	- dla system�w Linux   -> run-Linux.sh

=================== Uwagi do rysowania wykres�w =================

Czerwone punkty oznaczj� najlepsze dopasowanie z ka�dej iteracji,
zielony punkt oznacza najlepsze dopasowanie ze wszystkich
iteracji.

Je�eli chcemy narysowa� przekr�j dla funkcji o wymiarze n > 2. To
zawsze pod uwag� brane s� dwa pierwsze przedzia�y, kt�rych d�ugo��
jest wi�ksza ni� 0. Pozosta�e parametry, kt�rych d�ugo�� zakres�w
jest wi�ksza od zera s� traktowane jako sta�e r�wne 0.

Mo�na te� zadecydowa�, kt�re parametry maj� by� traktowane jako
sta�e i wybra� warto�� tej sta�ej. Wystarczy, �e w obu okienkach
tekstowych przeznaczonych do wpisania granic zakresu wpiszemy
t� sam� warto��.

Przyk�ady:

#1#

x1 [ 1 ] [ 2 ]
x2 [ 3 ] [ 4 ]
x3 [ 1 ] [ 5 ]
x4 [ 3 ] [ 3 ]

Dla podanych wy�ej parametr�w x1 jest umiejcowiony na osi X
wykresu, a x2 na osi Y. Parametr x3 jest traktowany jako sta�a
r�wna zero. X4 jest sta�� o warto�ci 3, wybran� przez u�ytkownika.

#2#

x1 [ 1 ] [ 2 ]
x2 [ 3 ] [ 3 ]
x3 [ 1 ] [ 5 ]
x4 [ 4 ] [ 7 ]

Dla podanych wy�ej parametr�w x1 jest umiejcowiony na osi X
wykresu, a x3 na osi Y. Parametr x4 jest traktowany jako sta�a
r�wna zero. X2 jest sta�� o warto�ci 3, wybran� przez u�ytkownika.