# Un template d'Application en Ligne de Commande (CLI) utilisant Java et la JEP 330

Le template **basic-java-quickstart** permet de démarrer une nouvelle application en commande ligne avec Java sans
utiliser aucun système de build (autre que `make`).

Ce template nécessite d'utiliser Java 11, ou une version supérieure, car il utilise la [JEP 330](https://openjdk.org/jeps/330)
qui permet de lancer un fichier source sans le compiler préalablement.

Le package de l'application comprend le fichier source ainsi que ses dépendances.

Pour démarrer une nouvelle application en utilisant ce template, vous pouvez suivre les étapes suivantes.

## Changer le nom de l'application

Pour changer le nom de l'application, initialement nommée _Basic-Quickstart_, il faut modifier la variable APP_NAME dans
le fichier `.envrc`, par exemple comme suit :

```bash
export APP_NAME=MyCmdLine
```

## Lancer l'application localement

```bash
make test
```

```console
./bin/Application.sh
Bonjour 🇫🇷
```

## Construire le package de l'application

```bash
make package
```

```console
mkdir --parents build/MyCmdLine/src build/MyCmdLine/lib build/MyCmdLine/bin
cp --update --recursive src lib bin build/MyCmdLine
cd build/MyCmdLine \
	&& mv src/Application.java src/MyCmdLine.java \
	&& mv bin/Application.sh bin/MyCmdLine.sh
cd build \
	&& zip --quiet --recurse-paths MyCmdLine.zip MyCmdLine
```

### Shell de lancement

Le script de lancement [Application.sh](bin/Application.sh), dont le rôle est de lancer le fichier
[Application.java](src/Application.java), est renommé lors de la construction du package en `MyCmdLine.sh`.

Cela permet d'ajouter plusieurs applications dans le `PATH` et donc d'invoquer directement `MyCmdLine.sh`.

## Installer l'application

```bash
DEST_DIR=/home/user make install
```

```console
unzip -q -d /home/user build/MyCmdLine.zip
```

## Lancer l'application installée

```bash
DEST_DIR=/home/user make test-install
```

```console
PATH=/home/user/MyCmdLine/bin:/usr/lib/jvm/jdk-22/bin:/home/fopy/.local/bin:... MyCmdLine.sh
Bonjour 🇫🇷
```

Il ne nous reste plus qu'à :

- Implémenter notre métier dans [Application.java](src/Application.java)
- Ajouter dans le fichier `Application.java` les classes utilisées par la classe `Main`
- Ajouter les jars de nos dépendances dans le répertoire [lib](lib)

## Autres templates

Le programme d'exemple proposé ici a besoin de Java 14 car il utilise les [Switch Expressions](https://openjdk.org/jeps/361),
mais si on n'en fait pas usage, Java 11 suffit.

Si vous utilisez Java 22, ou une version supérieure, vous pouvez utiliser [basic-java-22-quickstart](https://github.com/java-cli-apps/basic-java-22-quickstart)
qui permet d'utiliser plusieurs fichiers sources grâce à la [JEP 458](https://openjdk.org/jeps/458).
