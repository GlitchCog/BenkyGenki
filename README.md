# BenkyGenki

BenkyGenki is a database of all the vocabulary (and soon grammar) included in the Genki Japanese textbook series. There is a comprehensive user interface for filtering, sorting, and selecting vocabulary to be used in a flash card popup. I'm pretty confident that errors and missing vocabulary are quite minimal at this point.

Please enjoy!

## Quick Start

Download [BenkyGenki.zip](../../raw/master/zip/BenkyGenki.zip). Unzip its contents. There should be two files:

```
benkygenki.jar
benkygenki.db
```

Double-click benkygenki.jar

## Hey, That Didn't Work!

The .jar file is a Java Executable Archive file, so it is a program that can be run if you have the Java Runtime Environment installed. You may already have it, but if you need to, you can download it here [JRE](https://java.com/en/download/). After that's installed, you can run it with this command:

```
java -jar benkygenki.jar
```

Or possibly by double clicking on the file itself, if your operating system lets you run rando programs you got off the internet, which is something I recommend. If it looks too small on your high-res screen, try this command instead:

```
java -jar -Dsun.java2d.uiScale=2.5 benkygenki.jar
```

The other .db file is the SQLite database containing all the vocabulary. If the program can't find that file in the same place as the JAR, it won't be able to run. If you must move it, you can specify its location like this:

```
java -jar benkygenki.jar /home/user/randofolder/whydidimovethedatabasehere.db
```

## Usage

* There is a slider along the top of the program window for specifying a lesson range. Drag the ends back and forth to limit the list to vocabulary of certain lessons.
* There are check boxes below that for specifying which parts of speech should be included in the list. Switch them off to limit the list to vocabulary of certain parts of speech.

* Clicking on a line representing a vocabulary word in the filtered list will add it to the bottom selection list. Once a word is added, changing the filtering will remove it from the selection list.
* The selection list represents the vocabulary you want to focus on. The button labeled Clear to the right of the list will empty the selection list entirely so you can start over.
* The remaining options to the right of the selection list are configuration for the flash card feature. Use them to select the color and what content you want on each side of the flash card. The space above the Clear button will preview what the flash card will look like.
* Click on the flash card preview to see the reverse side. You can use a mouse scroll wheel or trackpad scroll function to resize the flash card preview window content.
* Click the button labeled Flash to start a session of flash cards.

* The flash cards can go forward or in reverse using the arrow buttons on the side of the flash card popup.
* The program will remember the order of the flash cards already shown, and you can change direction to go back through them the opposite way.
* The slider above the flash card will change the probability of that card being shown again. The idea is that if you know a vocabulary word well, you can set it to a lower probability of being selected again. Similarly, if you have trouble with a vocabulary word, you can set it to a higher probability of being selected.
* Close the flash card popup to return to the filterable and selection lists.

## Missing Features

* Ability to save configuration
* Ability to save decks
* Grammar
