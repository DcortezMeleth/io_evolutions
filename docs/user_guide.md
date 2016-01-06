# Podr�cznik u�ytownika


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

### Uruchomienie konsolowe

Istnieje r�wnie� mo�liwo�� uruchomienia eksperymentu w konsoli, bez appletu, co mo�e by� przydatne gdy b�dziemy chcieli
uruchomi� go w �rodowisku bez interfejsu graficznego, jak np. zdalny serwer albo klaster obliczeniowy.

Aby tego dokona� nale�y zmieni� property `mainClassName` w pliku `build.gradle`.
Nast�pnie uruchamiamy eksperyment w ten sam spos�b co przy wersji appletowej.

Utworzona jest ju� przyk��dowa klasa `Experiment` kt�ra mo�e pos�u�y� nam w tym celu.
Mo�na w niej oczywi�cie zmieni� wszystkie parametry tak samo jak w wersji appletowej.
Nale�y jednak pami�ta�, �e s� one tutaj definiowane na poziomie kodu, tam gdzie operatory i reszta atrybut�w. 


## Zmiany w eksperymencie

Aby dokona� zmian z przeprowadzanym eksperymencie (tj. zmieni� funkcj� ewaluj�c� lub operatory) nale�y dokona� zmian w
pliku `EMASApplet.java`.

Miejscem w kt�rym ustawiane s� wszystkie atrybuty eksperymentu jest funkcja `performTask()` w klasie `EvolutionTask`.

Modyfikacje mo�emy robi� na kilku poziomach.

### Osobnik
Pierwsz� rzecz� jak� nale�y ustali� jest to na jakich osobnikach b�dziemy operowa�. Zaimplementowane s� 2 ich typy.
* PointGenotyp - osobnik reprezentuj�cy punkt w 2-wymairowej przestrzeni 
* FloatGenotype - osobnik reprezentuj�cy punkt w n-wymiarowej przestrzeni

### Operatory
Operatory dzielimy na 2 rodzaje, krzy�owania (crossover) i mutacje (mutation).

**Krzy�owania** 
Operuj� one na 2 osobnikach, rodzicach, tworz�c z ich cz�ci potomka, czyli nowego osobnika.

Zaimplementowane s� nast�puj�ce krzy�owania dla PointGenotype:
* _AverageCrossover_ - wsp�rz�dne potomka s� �redni� wsp�rz�dnych rodzic�w

Oraz poni�sze dla FloatGenotype: 
* _AverageFloatCrossover_ - wsp�rz�dne potomka s� �redni� wsp�rz�dnych rodzic�w
* _UniformCrossover_ - kolejne wsp�rz�dne potomka s� losowo wybierane od jednego lub drugiego z rodzic�w
* _SinglePointCrossover_ - pierwsze p wsp�rz�dnych brane jest od pierwszego z rodzic�w, a reszta od drugiego, gdzie p 
losujemy z przedzia�u 0 <= p <= n

**Mutacje**
Operuj� na 1 osobniku losowo przekszta�caj�c cz�� jego wsp�rz�dnych.

Zaimplementowane s� nast�puj�ce mutacje dla PointGenotype:
* _UniformPointMutation_ - do wsp�rz�dnych potomka dodawane lub odejmowane s� niewielkie warto�ci losowane z rozk�adu 
jednostajnego

Oraz poni�sze dla FloatGenotype: 
* _NormalMutation_ -  do wsp�rz�dnych potomka dodawane lub odejmowane s� niewielkie warto�ci losowane z rozk�adu 
normalnego
* _UniformFloatMutation_ - do losowo wybranej wsp�rz�dnej potomka dodawana lub odejmowana jest niewielka warto�c 
losowana z rosk�adu jednostajnego

### Funkcje ewaluj�ce
Odpowiadaj� one za rozwi�zywany przez nas problem. 

Zaimplementowane s� nast�puj�ce funkcja dla PointGenotype:
* _DeJongEvaluation_ - rozwi�zuje pierwsz� funkcj� De Jong'a
* _RastriginEvaluation_ - rozwi�zuje funckj� Rastrigina

Oraz poni�sze dla FloatGenotype: 
* _RastriginEvaluation_ - rozwi�zuje funkcj� Rastrigina
* _SchwefelEvaluation_ - rozwi�zuje funkcj� Schwefela
