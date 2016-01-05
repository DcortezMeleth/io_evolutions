# io_evolutions


## Budowanie i uruchomienie

W naszym projekcie do budowania artefaktów wykorzystaliœmy _gradle_.
Aby zbudowaæ i uruchomiæ experyment nale¿y najpierw zainstalowaæ to narzêdzie i dodaj jego katalog domowy do zmiennie
œrodowsikowej _PATH_. Nastêpnie komenda której u¿ywamy do zbudowania wykonywalnego jara to:
```bash
gradle fatJar
```
Po zbudowaniu artefaktu uruchamiamy go za pomoc¹ komendy:
```bash
java -jar build\libs\evolutions-1.0-SNAPSHOT.jar
```

Naszym oczom powinien siê wtedy ukazaæ dzia³aj¹cy applet w formie jak poni¿ej.
![EMASApplet](docs/images/EMASApplet.png?raw=true)

Przed rozpoczêciem eksperymentu nale¿y ustawiæ jego parametry które znajduj¹ siê w górnej czêœci okna.
S¹ to po koleji:
* _Population size per island_ - populacja startowa na ka¿dej z wysp
* _Islands_ - liczba wysp
* _Elitism_ - liczba elti (najlepszych osobników pozostaj¹cych w populacji)
* _Migrations_ - liczba osobników które w ka¿dej z epok migruj¹ pomiêdzy wyspami
* _Selection pressure_ - prawdopodobieñstwo z jakim lepszy osobnik wygrywa w selekcji turniejowej

Gdy to uczynimi mo¿emy uruchomiæ eksperyment przyciskiem _Start_. 
Od tego momenetu mo¿emy na bie¿¹co œledziæ jego postêpy. Widoczne s¹ one na kilku kartach widocznych poni¿ej paska 
parametrów.

Pierwsza z kart pokazuje nam najlepszego kandydata w danym momencie.
![EMASApplet](docs/images/EMASApplet_fittest.png?raw=true)

Druga pokazuje wykres na którym widaæ jak zmienia siê wartoœæ funkcji dopasowania (fitness) w czasie.
![EMASApplet](docs/images/EMASApplet_example.png?raw=true)

Trzecia daje nam podgl¹d na to jaki fitness osi¹gn¹³ najlepszy osobnik na ka¿dej z wysp.
![EMASApplet](docs/images/EMASApplet_islands.png?raw=true)

Ostatnia z kart pokazuje nam aktualne zu¿ycie pamiêci przez JVM.
![EMASApplet](docs/images/EMASApplet_jvm.png?raw=true)
