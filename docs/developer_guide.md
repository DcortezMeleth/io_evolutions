# Podręcznik developera


Rozszerzenia projektu można dokonać w kilku miejscach. Każde z nich ma inny wpływ na przebieg eksperymentu. 
Przy większości zmian należy dodać nowe klasy, a następnie użyć ich w odpowiednim miejscu w klasie `EMASApplet`.
Oczywiście można również stworzyć własny applet lub dokonać zmian w już istniejącym. 

W celu lepszego zrozumienia struktóry projektu polecam zacząć lekturę od [Podręcznika użytkownika](user_guide.md).

## Osobniki

Aby dodać nowy typ osobnika należy stworzyć odpowiednią klasę w pakiecie `genotypes`.
W celu łatwiejszej konfiguracji późniejszych operatorów oraz możliwość korzystania z typów generycznych, zalecane jest
podziedziczenie w tym celu z klasy `AbstractGenotype`, ale nie jest to konieczne. Pola osobnika mogą być dowolnie 
definiowane, powinny jednak dać możliwość wyliczenia pewnej wartości (fitnessu) osobnika, która następnie pozwoli nam 
ocenić który z osobników jest dla naszych celów lepszy, a który gorszy. 
 
Warto w klasie osobnika nadpisać metodę `toString`, która umożliwi nam potem lepsze logowanie postępów.

## Fabryki

Kolejnym elementem który jest bardzo ściśle powiązany z osobnikiem, a który musimy zaimplementować w razie tworzenia
nowego typu osobnika jest Fabryka, która odpowiada za generowanie nowych osobników. W celu implementacji nowej fabryki,
możemy rozszerzyć klasę `AbstractGenotypeFactory<T>`. Udostępnia nam ona metodę `uniform()` zwracającą zmienną losową 
z rozkładu jednostajnego oraz pola `lowerbound` i `upperbound` które odpowiadają za granice tego przedziału. Granice
te ustawiamy w konstuktorze klasy implementującej.

Do implementacji mamy tylko jedną metodę, która ma za zadanie stworzyć nowego kandydata.
Przykładowa implementacja poniżej.
```java
@Override
public PointGenotype generateRandomCandidate(final Random rng) {
    return new PointGenotype(uniform(rng), uniform(rng));
}
```

W przypadku gdy nasza klasa nie dziedziczy po `AbstractGenotype` musimy rozszerzać klasę `AbstractGenotypeFactory<T>`.
Do implementacji mamy wtedy tą samą metodę.

## Operatory

Jak już zostało wcześniej wspomniane w projekcie istnieją 2 typy operatorów. Krzyżowania z pakiecie `crossovers` 
i mutacje w pakiecie `mutations`. 

### Krzyżowania

Bazową klasą dla krzyżowań w  jest klasa `AbstractCrossover<T>`. Odopowiada ona za mechanizm który z populacji wybiera 
osobniki, które posłóżą nam za rodziców do stworzenia nowego pokolenia. Dodatkowo udostępnie nam obiekt `Random` jako
jedno z pól klasy.

Jako iż jest to typ generyczny, implementacje tej klasy powinny rozszerzać klasę bazową z odpowiednim typem, tak by
możliwym było skorzystanie w metodach tej klasy z właściwości konkretnego osobnika. 

Nowa klasa musi implemenotwać jedynie jedną metodę, której przykładową implementację (dla typu `PointGenotype`) 
widać poniżej. Dostaje ona 2 rodziców i ma z nich stworzyć nowego potomka, który będzie częścią nowego pokolania.
```java
@Override
protected PointGenotype cross(final PointGenotype parent1, final PointGenotype parent2) {
    return new PointGenotype((parent1.getX() + parent2.getX())/2, (parent1.getY() + parent2.getY())/2);
}
```

### Mutacje

Bazową klasą dla krzyżowań w  jest klasa `AbstractMutation<T>`. Odopowiada ona za mechanizm który z na wybranych osobnikach
wywoła funkcję mutacji, tworzącą osobniki wchodzące w skład nowego pokolenia. Dodatkowo udostępnie nam obiekt `Random` 
jako jedno z pól klasy oraz metodę `uniform()` zwracającą liczbę wylosowaną z rozkładu jednostajnego. Dodatkowo posiada
2 pola prywatne, które ustawiane będą w konstuktorze klas potomnych. Są to `probability` czyli prawdopodobieńśtwo tego 
że osobnik powinien mutować, oraz `radius` czyli promień w jakim losowana jest zmienna w metodzie `uniform()`.

Jako iż, podobnie jak `AbstractCrossover<T>`, jest to typ generyczny, implementacje tej klasy powinny rozszerzać klasę 
bazową z odpowiednim typem, tak by możliwym było skorzystanie w metodach tej klasy z właściwości konkretnego osobnika. 

Nowa klasa musi jednynie implementować metodę, która odpowiada za mutację na pojedynczym osobniku.
Dostaje ona jako parametr starego osobnika, zwrócić powinna natomiast nowego osobnika będącego pewną mutacją starego.
Przykładowa implementacja poniżej.
```java
@Override
protected PointGenotype mutate(final PointGenotype genotype) {
    PointGenotype result = new PointGenotype(genotype);
    result.setX(result.getX() + uniform());
    result.setY(result.getY() + uniform());
    return result;
}
```


**Uwaga!** Należy pamiętać że powinniśmy tu zwracać nowy obiekt, a nie zmieniać stary, gdyż tego wymaga od nas 
framework _Watchmaker_.

### Inne operatory

Wykorzystujemy je w momencie gdy chcemy dodać nowy tym operatora lub nasz osobnik nie rozszerza klasy `AbstractGenotype`.
Należy wtedy jednak rozszerzać interfejs `EvolutionaryOperator<T>` w którym musimy zaimplementować metodę `apply()`.

## Funkcje ewalujące

Implementacje rozwiązywanych problemów znajdują się w pakiecie `evalutaion`. Każda z znadujących się tam funkcji musi
rozszerzać generyczny interfejs `FitnessEvaluator<T>` podając typ osobnika na którym zamierzamy działać w miejscu `T`.

W klasie funckji musimy zaimplementować 2 metody.
 
Pierwsza, `getFitness`, która wyznacza fitness naszego osobnika. Dostaje ona na wejściu osobnika którwego wartość ma 
wyznaczyć oraz całą populację, jeśli wartość jest w jakiś sposób powiązana z tym jak wyglądają inne osobniki. Powinna 
zwracać wartość liczbową określającą wartość naszego kandydata. 

Druga, `isNatural`, która zwraca `true` jeśli chcemy maksymalizować nasz problem, a `false` w przypadku gdy szukamy 
minimum.

## Eksperyment

Sam eksperyment konsolowy jest prosty w implementacji. O tym jak go stworzyć można przeczytać w manualu _Watchmakera_,
lub wzorować się na klasie `Experiment`. 

Modyfikacji appletu można dokonać w klasie `EMASApplet`. Nowe slidery oraz przyciski możemy łątwo dodać wzorując się 
na już istniejących. Jeśli natomiast chcemy stworzyć nowy applet, polecam rozszerzyć w tym celu klase _Watchmakera_, 
`AbstractExampleApplet`.

## Obserwatory

Dają nam one możliwość wglądu w populację przy każdej epoce oraz zalogowania pewnych parametrów. W celu stworzenia 
nowego obserwatora musimy rozszerzyć klasę `IslandEvolutionObserver<T>` gdy korzystamy z silnika wyspowego
lub `EvolutionObserver<T>` gdy wykorzystujemy silnik pojedynczy. 

Do rozszerzenia mamy wtedy odpowiednio 2 lub 1 metodę. 1 z nich odpowiada za update populacji jednego silnika.
W obserwatorze wyspowym dodatkową metodą jest wspólna dla wszystkich wysp (silników).
Obserwatory te możemy tworzyć zarówno jako pełnoprawne klasy jak i jako anonimowe. Przykład tych drugich, oraz tego w
którym miejscu należy je ustawić w eksperymencie znajdują się w klasie `Experiment`.

Poniżej przykład.
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