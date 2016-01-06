# Podrêcznik u¿ytownika


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

### Uruchomienie konsolowe

Istnieje równie¿ mo¿liwoœæ uruchomienia eksperymentu w konsoli, bez appletu, co mo¿e byæ przydatne gdy bêdziemy chcieli
uruchomiæ go w œrodowisku bez interfejsu graficznego, jak np. zdalny serwer albo klaster obliczeniowy.

Aby tego dokonaæ nale¿y zmieniæ property `mainClassName` w pliku `build.gradle`.
Nastêpnie uruchamiamy eksperyment w ten sam sposób co przy wersji appletowej.

Utworzona jest ju¿ przyk³¹dowa klasa `Experiment` która mo¿e pos³u¿yæ nam w tym celu.
Mo¿na w niej oczywiœcie zmieniæ wszystkie parametry tak samo jak w wersji appletowej.
Nale¿y jednak pamiêtaæ, ¿e s¹ one tutaj definiowane na poziomie kodu, tam gdzie operatory i reszta atrybutów. 


## Zmiany w eksperymencie

Aby dokonaæ zmian z przeprowadzanym eksperymencie (tj. zmieniæ funkcjê ewaluj¹c¹ lub operatory) nale¿y dokonaæ zmian w
pliku `EMASApplet.java`.

Miejscem w którym ustawiane s¹ wszystkie atrybuty eksperymentu jest funkcja `performTask()` w klasie `EvolutionTask`.

Modyfikacje mo¿emy robiæ na kilku poziomach.

### Osobnik
Pierwsz¹ rzecz¹ jak¹ nale¿y ustaliæ jest to na jakich osobnikach bêdziemy operowaæ. Zaimplementowane s¹ 2 ich typy.
* PointGenotyp - osobnik reprezentuj¹cy punkt w 2-wymairowej przestrzeni 
* FloatGenotype - osobnik reprezentuj¹cy punkt w n-wymiarowej przestrzeni

### Operatory
Operatory dzielimy na 2 rodzaje, krzy¿owania (crossover) i mutacje (mutation).

**Krzy¿owania** 
Operuj¹ one na 2 osobnikach, rodzicach, tworz¹c z ich czêœci potomka, czyli nowego osobnika.

Zaimplementowane s¹ nastêpuj¹ce krzy¿owania dla PointGenotype:
* _AverageCrossover_ - wspó³rzêdne potomka s¹ œredni¹ wspó³rzêdnych rodziców

Oraz poni¿sze dla FloatGenotype: 
* _AverageFloatCrossover_ - wspó³rzêdne potomka s¹ œredni¹ wspó³rzêdnych rodziców
* _UniformCrossover_ - kolejne wspó³rzêdne potomka s¹ losowo wybierane od jednego lub drugiego z rodziców
* _SinglePointCrossover_ - pierwsze p wspó³rzêdnych brane jest od pierwszego z rodziców, a reszta od drugiego, gdzie p 
losujemy z przedzia³u 0 <= p <= n

**Mutacje**
Operuj¹ na 1 osobniku losowo przekszta³caj¹c czêœæ jego wspó³rzêdnych.

Zaimplementowane s¹ nastêpuj¹ce mutacje dla PointGenotype:
* _UniformPointMutation_ - do wspó³rzêdnych potomka dodawane lub odejmowane s¹ niewielkie wartoœci losowane z rozk³adu 
jednostajnego

Oraz poni¿sze dla FloatGenotype: 
* _NormalMutation_ -  do wspó³rzêdnych potomka dodawane lub odejmowane s¹ niewielkie wartoœci losowane z rozk³adu 
normalnego
* _UniformFloatMutation_ - do losowo wybranej wspó³rzêdnej potomka dodawana lub odejmowana jest niewielka wartoœc 
losowana z rosk³adu jednostajnego

### Funkcje ewaluj¹ce
Odpowiadaj¹ one za rozwi¹zywany przez nas problem. 

Zaimplementowane s¹ nastêpuj¹ce funkcja dla PointGenotype:
* _DeJongEvaluation_ - rozwi¹zuje pierwsz¹ funkcjê De Jong'a
* _RastriginEvaluation_ - rozwi¹zuje funckjê Rastrigina

Oraz poni¿sze dla FloatGenotype: 
* _RastriginEvaluation_ - rozwi¹zuje funkcjê Rastrigina
* _SchwefelEvaluation_ - rozwi¹zuje funkcjê Schwefela
