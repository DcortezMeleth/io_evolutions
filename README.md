# io_evolutions


## Budowanie i uruchomienie

W naszym projekcie do budowania artefakt�w wykorzystali�my _gradle_.
Aby zbudowa� i uruchomi� experyment nale�y najpierw zainstalowa� to narz�dzie i dodaj jego katalog domowy do zmiennie
�rodowsikowej _PATH_. Nast�pnie komenda kt�rej u�ywamy do zbudowania wykonywalnego jara to:
```bash
gradle fatJar
```
Po zbudowaniu artefaktu uruchamiamy go za pomoc� komendy:
```bash
java -jar build\libs\evolutions-1.0-SNAPSHOT.jar
```

Naszym oczom powinien si� wtedy ukaza� dzia�aj�cy applet w formie jak poni�ej.
![EMASApplet](docs/images/EMASApplet.png?raw=true)

Przed rozpocz�ciem eksperymentu nale�y ustawi� jego parametry kt�re znajduj� si� w g�rnej cz�ci okna.
S� to po koleji:
* _Population size per island_ - populacja startowa na ka�dej z wysp
* _Islands_ - liczba wysp
* _Elitism_ - liczba elti (najlepszych osobnik�w pozostaj�cych w populacji)
* _Migrations_ - liczba osobnik�w kt�re w ka�dej z epok migruj� pomi�dzy wyspami
* _Selection pressure_ - prawdopodobie�stwo z jakim lepszy osobnik wygrywa w selekcji turniejowej

Gdy to uczynimi mo�emy uruchomi� eksperyment przyciskiem _Start_. 
Od tego momenetu mo�emy na bie��co �ledzi� jego post�py. Widoczne s� one na kilku kartach widocznych poni�ej paska 
parametr�w.

Pierwsza z kart pokazuje nam najlepszego kandydata w danym momencie.
![EMASApplet](docs/images/EMASApplet_fittest.png?raw=true)

Druga pokazuje wykres na kt�rym wida� jak zmienia si� warto�� funkcji dopasowania (fitness) w czasie.
![EMASApplet](docs/images/EMASApplet_example.png?raw=true)

Trzecia daje nam podgl�d na to jaki fitness osi�gn�� najlepszy osobnik na ka�dej z wysp.
![EMASApplet](docs/images/EMASApplet_islands.png?raw=true)

Ostatnia z kart pokazuje nam aktualne zu�ycie pami�ci przez JVM.
![EMASApplet](docs/images/EMASApplet_jvm.png?raw=true)
