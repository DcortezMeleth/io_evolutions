# io_evolutions


## Budowanie

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