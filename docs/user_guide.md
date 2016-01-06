# Podręcznik użytownika


## Budowanie i uruchomienie

W naszym projekcie do budowania artefaktów wykorzystaliśmy _gradle_.
Aby zbudować i uruchomić experyment należy najpierw zainstalować to narzędzie i dodaj jego katalog domowy do zmiennie
środowsikowej _PATH_. Następnie komenda której używamy do zbudowania wykonywalnego jara to:
```bash
gradle fatJar
```
Po zbudowaniu artefaktu uruchamiamy go za pomocą komendy:
```bash
java -jar build\libs\evolutions-1.0-SNAPSHOT.jar
```

Naszym oczom powinien się wtedy ukazać działający applet w formie jak poniżej.
![EMASApplet](docs/images/EMASApplet.png?raw=true)

Przed rozpoczęciem eksperymentu należy ustawić jego parametry które znajdują się w górnej części okna.
Są to po koleji:
* _Population size per island_ - populacja startowa na każdej z wysp
* _Islands_ - liczba wysp
* _Elitism_ - liczba elti (najlepszych osobników pozostających w populacji)
* _Migrations_ - liczba osobników które w każdej z epok migrują pomiędzy wyspami
* _Selection pressure_ - prawdopodobieństwo z jakim lepszy osobnik wygrywa w selekcji turniejowej

Gdy to uczynimi możemy uruchomić eksperyment przyciskiem _Start_. 
Od tego momenetu możemy na bieżąco śledzić jego postępy. Widoczne są one na kilku kartach widocznych poniżej paska 
parametrów.

Pierwsza z kart pokazuje nam najlepszego kandydata w danym momencie.
![EMASApplet](docs/images/EMASApplet_fittest.png?raw=true)

Druga pokazuje wykres na którym widać jak zmienia się wartość funkcji dopasowania (fitness) w czasie.
![EMASApplet](docs/images/EMASApplet_example.png?raw=true)

Trzecia daje nam podgląd na to jaki fitness osiągnął najlepszy osobnik na każdej z wysp.
![EMASApplet](docs/images/EMASApplet_islands.png?raw=true)

Ostatnia z kart pokazuje nam aktualne zużycie pamięci przez JVM.
![EMASApplet](docs/images/EMASApplet_jvm.png?raw=true)

### Uruchomienie konsolowe

Istnieje również możliwość uruchomienia eksperymentu w konsoli, bez appletu, co może być przydatne gdy będziemy chcieli
uruchomić go w środowisku bez interfejsu graficznego, jak np. zdalny serwer albo klaster obliczeniowy.

Aby tego dokonać należy zmienić property `mainClassName` w pliku `build.gradle`.
Następnie uruchamiamy eksperyment w ten sam sposób co przy wersji appletowej.

Utworzona jest już przykłądowa klasa `Experiment` która może posłużyć nam w tym celu.
Można w niej oczywiście zmienić wszystkie parametry tak samo jak w wersji appletowej.
Należy jednak pamiętać, że są one tutaj definiowane na poziomie kodu, tam gdzie operatory i reszta atrybutów. 


## Zmiany w eksperymencie

Aby dokonać zmian z przeprowadzanym eksperymencie (tj. zmienić funkcję ewalującą lub operatory) należy dokonać zmian w
pliku `EMASApplet.java`.

Miejscem w którym ustawiane są wszystkie atrybuty eksperymentu jest funkcja `performTask()` w klasie `EvolutionTask`.

Modyfikacje możemy robić na kilku poziomach.

### Osobnik
Pierwszą rzeczą jaką należy ustalić jest to na jakich osobnikach będziemy operować. Zaimplementowane są 2 ich typy.
* PointGenotyp - osobnik reprezentujący punkt w 2-wymairowej przestrzeni 
* FloatGenotype - osobnik reprezentujący punkt w n-wymiarowej przestrzeni

### Operatory
Operatory dzielimy na 2 rodzaje, krzyżowania (crossover) i mutacje (mutation).

**Krzyżowania** 
Operują one na 2 osobnikach, rodzicach, tworząc z ich części potomka, czyli nowego osobnika.

Zaimplementowane są następujące krzyżowania dla PointGenotype:
* _AverageCrossover_ - współrzędne potomka są średnią współrzędnych rodziców

Oraz poniższe dla FloatGenotype: 
* _AverageFloatCrossover_ - współrzędne potomka są średnią współrzędnych rodziców
* _UniformCrossover_ - kolejne współrzędne potomka są losowo wybierane od jednego lub drugiego z rodziców
* _SinglePointCrossover_ - pierwsze p współrzędnych brane jest od pierwszego z rodziców, a reszta od drugiego, gdzie p 
losujemy z przedziału 0 <= p <= n

**Mutacje**
Operują na 1 osobniku losowo przekształcając część jego współrzędnych.

Zaimplementowane są następujące mutacje dla PointGenotype:
* _UniformPointMutation_ - do współrzędnych potomka dodawane lub odejmowane są niewielkie wartości losowane z rozkładu 
jednostajnego

Oraz poniższe dla FloatGenotype: 
* _NormalMutation_ -  do współrzędnych potomka dodawane lub odejmowane są niewielkie wartości losowane z rozkładu 
normalnego
* _UniformFloatMutation_ - do losowo wybranej współrzędnej potomka dodawana lub odejmowana jest niewielka wartośc 
losowana z roskładu jednostajnego

### Funkcje ewalujące
Odpowiadają one za rozwiązywany przez nas problem. 

Zaimplementowane są następujące funkcja dla PointGenotype:
* _DeJongEvaluation_ - rozwiązuje pierwszą funkcję De Jong'a
* _RastriginEvaluation_ - rozwiązuje funckję Rastrigina

Oraz poniższe dla FloatGenotype: 
* _RastriginEvaluation_ - rozwiązuje funkcję Rastrigina
* _SchwefelEvaluation_ - rozwiązuje funkcję Schwefela
