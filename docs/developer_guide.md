# Podrêcznik developera


Rozszerzenia projektu mo¿na dokonaæ w kilku miejscach. Ka¿de z nich ma inny wp³yw na przebieg eksperymentu. 
Przy wiêkszoœci zmian nale¿y dodaæ nowe klasy, a nastêpnie u¿yæ ich w odpowiednim miejscu w klasie `EMASApplet`.
Oczywiœcie mo¿na równie¿ stworzyæ w³asny applet lub dokonaæ zmian w ju¿ istniej¹cym. 

W celu lepszego zrozumienia struktóry projektu polecam zacz¹æ lekturê od [Podrêcznika u¿ytkownika](user_guide.md).

## Osobniki

Aby dodaæ nowy typ osobnika nale¿y stworzyæ odpowiedni¹ klasê w pakiecie `genotypes`.
W celu ³atwiejszej konfiguracji póŸniejszych operatorów oraz mo¿liwoœæ korzystania z typów generycznych, zalecane jest
podziedziczenie w tym celu z klasy `AbstractGenotype`, ale nie jest to konieczne. Pola osobnika mog¹ byæ dowolnie 
definiowane, powinny jednak daæ mo¿liwoœæ wyliczenia pewnej wartoœci (fitnessu) osobnika, która nastêpnie pozwoli nam 
oceniæ który z osobników jest dla naszych celów lepszy, a który gorszy. 
 
Warto w klasie osobnika nadpisaæ metodê `toString`, która umo¿liwi nam potem lepsze logowanie postêpów.

## Fabryki

Kolejnym elementem który jest bardzo œciœle powi¹zany z osobnikiem, a który musimy zaimplementowaæ w razie tworzenia
nowego typu osobnika jest Fabryka, która odpowiada za generowanie nowych osobników. W celu implementacji nowej fabryki,
mo¿emy rozszerzyæ klasê `AbstractGenotypeFactory<T>`. Udostêpnia nam ona metodê `uniform()` zwracaj¹c¹ zmienn¹ losow¹ 
z rozk³adu jednostajnego oraz pola `lowerbound` i `upperbound` które odpowiadaj¹ za granice tego przedzia³u. Granice
te ustawiamy w konstuktorze klasy implementuj¹cej.

Do implementacji mamy tylko jedn¹ metodê, która ma za zadanie stworzyæ nowego kandydata.
Przyk³adowa implementacja poni¿ej.
```java
@Override
public PointGenotype generateRandomCandidate(final Random rng) {
    return new PointGenotype(uniform(rng), uniform(rng));
}
```

W przypadku gdy nasza klasa nie dziedziczy po `AbstractGenotype` musimy rozszerzaæ klasê `AbstractGenotypeFactory<T>`.
Do implementacji mamy wtedy t¹ sam¹ metodê.

## Operatory

Jak ju¿ zosta³o wczeœniej wspomniane w projekcie istniej¹ 2 typy operatorów. Krzy¿owania z pakiecie `crossovers` 
i mutacje w pakiecie `mutations`. 

### Krzy¿owania

Bazow¹ klas¹ dla krzy¿owañ w  jest klasa `AbstractCrossover<T>`. Odopowiada ona za mechanizm który z populacji wybiera 
osobniki, które pos³ó¿¹ nam za rodziców do stworzenia nowego pokolenia. Dodatkowo udostêpnie nam obiekt `Random` jako
jedno z pól klasy.

Jako i¿ jest to typ generyczny, implementacje tej klasy powinny rozszerzaæ klasê bazow¹ z odpowiednim typem, tak by
mo¿liwym by³o skorzystanie w metodach tej klasy z w³aœciwoœci konkretnego osobnika. 

Nowa klasa musi implemenotwaæ jedynie jedn¹ metodê, której przyk³adow¹ implementacjê (dla typu `PointGenotype`) 
widaæ poni¿ej. Dostaje ona 2 rodziców i ma z nich stworzyæ nowego potomka, który bêdzie czêœci¹ nowego pokolania.
```java
@Override
protected PointGenotype cross(final PointGenotype parent1, final PointGenotype parent2) {
    return new PointGenotype((parent1.getX() + parent2.getX())/2, (parent1.getY() + parent2.getY())/2);
}
```

### Mutacje

Bazow¹ klas¹ dla krzy¿owañ w  jest klasa `AbstractMutation<T>`. Odopowiada ona za mechanizm który z na wybranych osobnikach
wywo³a funkcjê mutacji, tworz¹c¹ osobniki wchodz¹ce w sk³ad nowego pokolenia. Dodatkowo udostêpnie nam obiekt `Random` 
jako jedno z pól klasy oraz metodê `uniform()` zwracaj¹c¹ liczbê wylosowan¹ z rozk³adu jednostajnego. Dodatkowo posiada
2 pola prywatne, które ustawiane bêd¹ w konstuktorze klas potomnych. S¹ to `probability` czyli prawdopodobieñœtwo tego 
¿e osobnik powinien mutowaæ, oraz `radius` czyli promieñ w jakim losowana jest zmienna w metodzie `uniform()`.

Jako i¿, podobnie jak `AbstractCrossover<T>`, jest to typ generyczny, implementacje tej klasy powinny rozszerzaæ klasê 
bazow¹ z odpowiednim typem, tak by mo¿liwym by³o skorzystanie w metodach tej klasy z w³aœciwoœci konkretnego osobnika. 

Nowa klasa musi jednynie implementowaæ metodê, która odpowiada za mutacjê na pojedynczym osobniku.
Dostaje ona jako parametr starego osobnika, zwróciæ powinna natomiast nowego osobnika bêd¹cego pewn¹ mutacj¹ starego.
Przyk³adowa implementacja poni¿ej.
```java
@Override
protected PointGenotype mutate(final PointGenotype genotype) {
    PointGenotype result = new PointGenotype(genotype);
    result.setX(result.getX() + uniform());
    result.setY(result.getY() + uniform());
    return result;
}
```


**Uwaga!** Nale¿y pamiêtaæ ¿e powinniœmy tu zwracaæ nowy obiekt, a nie zmieniaæ stary, gdy¿ tego wymaga od nas 
framework _Watchmaker_.

### Inne operatory

Wykorzystujemy je w momencie gdy chcemy dodaæ nowy tym operatora lub nasz osobnik nie rozszerza klasy `AbstractGenotype`.
Nale¿y wtedy jednak rozszerzaæ interfejs `EvolutionaryOperator<T>` w którym musimy zaimplementowaæ metodê `apply()`.

## Funkcje ewaluj¹ce

Implementacje rozwi¹zywanych problemów znajduj¹ siê w pakiecie `evalutaion`. Ka¿da z znaduj¹cych siê tam funkcji musi
rozszerzaæ generyczny interfejs `FitnessEvaluator<T>` podaj¹c typ osobnika na którym zamierzamy dzia³aæ w miejscu `T`.

W klasie funckji musimy zaimplementowaæ 2 metody.
 
Pierwsza, `getFitness`, która wyznacza fitness naszego osobnika. Dostaje ona na wejœciu osobnika którwego wartoœæ ma 
wyznaczyæ oraz ca³¹ populacjê, jeœli wartoœæ jest w jakiœ sposób powi¹zana z tym jak wygl¹daj¹ inne osobniki. Powinna 
zwracaæ wartoœæ liczbow¹ okreœlaj¹c¹ wartoœæ naszego kandydata. 

Druga, `isNatural`, która zwraca `true` jeœli chcemy maksymalizowaæ nasz problem, a `false` w przypadku gdy szukamy 
minimum.

## Eksperyment

Sam eksperyment konsolowy jest prosty w implementacji. O tym jak go stworzyæ mo¿na przeczytaæ w manualu _Watchmakera_,
lub wzorowaæ siê na klasie `Experiment`. 

Modyfikacji appletu mo¿na dokonaæ w klasie `EMASApplet`. Nowe slidery oraz przyciski mo¿emy ³¹two dodaæ wzoruj¹c siê 
na ju¿ istniej¹cych. Jeœli natomiast chcemy stworzyæ nowy applet, polecam rozszerzyæ w tym celu klase _Watchmakera_, 
`AbstractExampleApplet`.

## Obserwatory

Daj¹ nam one mo¿liwoœæ wgl¹du w populacjê przy ka¿dej epoce oraz zalogowania pewnych parametrów. W celu stworzenia 
nowego obserwatora musimy rozszerzyæ klasê `IslandEvolutionObserver<T>` gdy korzystamy z silnika wyspowego
lub `EvolutionObserver<T>` gdy wykorzystujemy silnik pojedynczy. 

Do rozszerzenia mamy wtedy odpowiednio 2 lub 1 metodê. 1 z nich odpowiada za update populacji jednego silnika.
W obserwatorze wyspowym dodatkow¹ metod¹ jest wspólna dla wszystkich wysp (silników).
Obserwatory te mo¿emy tworzyæ zarówno jako pe³noprawne klasy jak i jako anonimowe. Przyk³ad tych drugich, oraz tego w
którym miejscu nale¿y je ustawiæ w eksperymencie znajduj¹ siê w klasie `Experiment`.

Poni¿ej przyk³ad.
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