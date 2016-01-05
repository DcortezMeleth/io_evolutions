# io_evolutions


## Budowanie

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