# Podr�cznik developera


Rozszerzenia projektu mo�na dokona� w kilku miejscach. Ka�de z nich ma inny wp�yw na przebieg eksperymentu. 
Przy wi�kszo�ci zmian nale�y doda� nowe klasy, a nast�pnie u�y� ich w odpowiednim miejscu w klasie `EMASApplet`.
Oczywi�cie mo�na r�wnie� stworzy� w�asny applet lub dokona� zmian w ju� istniej�cym. 

W celu lepszego zrozumienia strukt�ry projektu polecam zacz�� lektur� od [Podr�cznika u�ytkownika](user_guide.md).

## Osobniki

Aby doda� nowy typ osobnika nale�y stworzy� odpowiedni� klas� w pakiecie `genotypes`.
W celu �atwiejszej konfiguracji p�niejszych operator�w oraz mo�liwo�� korzystania z typ�w generycznych, zalecane jest
podziedziczenie w tym celu z klasy `AbstractGenotype`, ale nie jest to konieczne. Pola osobnika mog� by� dowolnie 
definiowane, powinny jednak da� mo�liwo�� wyliczenia pewnej warto�ci (fitnessu) osobnika, kt�ra nast�pnie pozwoli nam 
oceni� kt�ry z osobnik�w jest dla naszych cel�w lepszy, a kt�ry gorszy. 
 
Warto w klasie osobnika nadpisa� metod� `toString`, kt�ra umo�liwi nam potem lepsze logowanie post�p�w.

## Fabryki

Kolejnym elementem kt�ry jest bardzo �ci�le powi�zany z osobnikiem, a kt�ry musimy zaimplementowa� w razie tworzenia
nowego typu osobnika jest Fabryka, kt�ra odpowiada za generowanie nowych osobnik�w. W celu implementacji nowej fabryki,
mo�emy rozszerzy� klas� `AbstractGenotypeFactory<T>`. Udost�pnia nam ona metod� `uniform()` zwracaj�c� zmienn� losow� 
z rozk�adu jednostajnego oraz pola `lowerbound` i `upperbound` kt�re odpowiadaj� za granice tego przedzia�u. Granice
te ustawiamy w konstuktorze klasy implementuj�cej.

Do implementacji mamy tylko jedn� metod�, kt�ra ma za zadanie stworzy� nowego kandydata.
Przyk�adowa implementacja poni�ej.
```java
@Override
public PointGenotype generateRandomCandidate(final Random rng) {
    return new PointGenotype(uniform(rng), uniform(rng));
}
```

W przypadku gdy nasza klasa nie dziedziczy po `AbstractGenotype` musimy rozszerza� klas� `AbstractGenotypeFactory<T>`.
Do implementacji mamy wtedy t� sam� metod�.

## Operatory

Jak ju� zosta�o wcze�niej wspomniane w projekcie istniej� 2 typy operator�w. Krzy�owania z pakiecie `crossovers` 
i mutacje w pakiecie `mutations`. 

### Krzy�owania

Bazow� klas� dla krzy�owa� w  jest klasa `AbstractCrossover<T>`. Odopowiada ona za mechanizm kt�ry z populacji wybiera 
osobniki, kt�re pos�� nam za rodzic�w do stworzenia nowego pokolenia. Dodatkowo udost�pnie nam obiekt `Random` jako
jedno z p�l klasy.

Jako i� jest to typ generyczny, implementacje tej klasy powinny rozszerza� klas� bazow� z odpowiednim typem, tak by
mo�liwym by�o skorzystanie w metodach tej klasy z w�a�ciwo�ci konkretnego osobnika. 

Nowa klasa musi implemenotwa� jedynie jedn� metod�, kt�rej przyk�adow� implementacj� (dla typu `PointGenotype`) 
wida� poni�ej. Dostaje ona 2 rodzic�w i ma z nich stworzy� nowego potomka, kt�ry b�dzie cz�ci� nowego pokolania.
```java
@Override
protected PointGenotype cross(final PointGenotype parent1, final PointGenotype parent2) {
    return new PointGenotype((parent1.getX() + parent2.getX())/2, (parent1.getY() + parent2.getY())/2);
}
```

### Mutacje

Bazow� klas� dla krzy�owa� w  jest klasa `AbstractMutation<T>`. Odopowiada ona za mechanizm kt�ry z na wybranych osobnikach
wywo�a funkcj� mutacji, tworz�c� osobniki wchodz�ce w sk�ad nowego pokolenia. Dodatkowo udost�pnie nam obiekt `Random` 
jako jedno z p�l klasy oraz metod� `uniform()` zwracaj�c� liczb� wylosowan� z rozk�adu jednostajnego. Dodatkowo posiada
2 pola prywatne, kt�re ustawiane b�d� w konstuktorze klas potomnych. S� to `probability` czyli prawdopodobie�two tego 
�e osobnik powinien mutowa�, oraz `radius` czyli promie� w jakim losowana jest zmienna w metodzie `uniform()`.

Jako i�, podobnie jak `AbstractCrossover<T>`, jest to typ generyczny, implementacje tej klasy powinny rozszerza� klas� 
bazow� z odpowiednim typem, tak by mo�liwym by�o skorzystanie w metodach tej klasy z w�a�ciwo�ci konkretnego osobnika. 

Nowa klasa musi jednynie implementowa� metod�, kt�ra odpowiada za mutacj� na pojedynczym osobniku.
Dostaje ona jako parametr starego osobnika, zwr�ci� powinna natomiast nowego osobnika b�d�cego pewn� mutacj� starego.
Przyk�adowa implementacja poni�ej.
```java
@Override
protected PointGenotype mutate(final PointGenotype genotype) {
    PointGenotype result = new PointGenotype(genotype);
    result.setX(result.getX() + uniform());
    result.setY(result.getY() + uniform());
    return result;
}
```


**Uwaga!** Nale�y pami�ta� �e powinni�my tu zwraca� nowy obiekt, a nie zmienia� stary, gdy� tego wymaga od nas 
framework _Watchmaker_.

### Inne operatory

Wykorzystujemy je w momencie gdy chcemy doda� nowy tym operatora lub nasz osobnik nie rozszerza klasy `AbstractGenotype`.
Nale�y wtedy jednak rozszerza� interfejs `EvolutionaryOperator<T>` w kt�rym musimy zaimplementowa� metod� `apply()`.

## Funkcje ewaluj�ce

Implementacje rozwi�zywanych problem�w znajduj� si� w pakiecie `evalutaion`. Ka�da z znaduj�cych si� tam funkcji musi
rozszerza� generyczny interfejs `FitnessEvaluator<T>` podaj�c typ osobnika na kt�rym zamierzamy dzia�a� w miejscu `T`.

W klasie funckji musimy zaimplementowa� 2 metody.
 
Pierwsza, `getFitness`, kt�ra wyznacza fitness naszego osobnika. Dostaje ona na wej�ciu osobnika kt�rwego warto�� ma 
wyznaczy� oraz ca�� populacj�, je�li warto�� jest w jaki� spos�b powi�zana z tym jak wygl�daj� inne osobniki. Powinna 
zwraca� warto�� liczbow� okre�laj�c� warto�� naszego kandydata. 

Druga, `isNatural`, kt�ra zwraca `true` je�li chcemy maksymalizowa� nasz problem, a `false` w przypadku gdy szukamy 
minimum.

## Eksperyment

Sam eksperyment konsolowy jest prosty w implementacji. O tym jak go stworzy� mo�na przeczyta� w manualu _Watchmakera_,
lub wzorowa� si� na klasie `Experiment`. 

Modyfikacji appletu mo�na dokona� w klasie `EMASApplet`. Nowe slidery oraz przyciski mo�emy ��two doda� wzoruj�c si� 
na ju� istniej�cych. Je�li natomiast chcemy stworzy� nowy applet, polecam rozszerzy� w tym celu klase _Watchmakera_, 
`AbstractExampleApplet`.

## Obserwatory

Daj� nam one mo�liwo�� wgl�du w populacj� przy ka�dej epoce oraz zalogowania pewnych parametr�w. W celu stworzenia 
nowego obserwatora musimy rozszerzy� klas� `IslandEvolutionObserver<T>` gdy korzystamy z silnika wyspowego
lub `EvolutionObserver<T>` gdy wykorzystujemy silnik pojedynczy. 

Do rozszerzenia mamy wtedy odpowiednio 2 lub 1 metod�. 1 z nich odpowiada za update populacji jednego silnika.
W obserwatorze wyspowym dodatkow� metod� jest wsp�lna dla wszystkich wysp (silnik�w).
Obserwatory te mo�emy tworzy� zar�wno jako pe�noprawne klasy jak i jako anonimowe. Przyk�ad tych drugich, oraz tego w
kt�rym miejscu nale�y je ustawi� w eksperymencie znajduj� si� w klasie `Experiment`.

Poni�ej przyk�ad.
```java
islandEvolution.addEvolutionObserver(new IslandEvolutionObserver<FloatGenotype>() {
    @Override
    public void islandPopulationUpdate(int islandIndex, PopulationData<? extends FloatGenotype> data) {
        System.out.println("Island:" + islandIndex + "Generation: " + data.getGenerationNumber()
                + ", best candidate: " + data.getBestCandidate()
                + ", with fitness: " + data.getBestCandidateFitness());
    }
    
    @Override
    public void populationUpdate(PopulationData<? extends FloatGenotype> data) {
        System.out.println("Generation: " + data.getGenerationNumber()
                + ", best candidate: " + data.getBestCandidate()
                + ", with fitness: " + data.getBestCandidateFitness());
    }
});
```