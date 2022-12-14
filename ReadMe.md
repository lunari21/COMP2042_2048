# 2048
Game created by Gabriele Cirulli.
Original code from an unknown source.
Refactoring done by Alexander Tan Ka Jin, 20415760. 

This coursework required us to refactor a pre-existing and horridly programmed codebase into a codebase that is more maintainable and manageable by other programmers. 

This project was done entirely in JavaFX 19-x64 using Eclipse
## Compilation
The code was originally written in **Eclipse** without the usage of E(fx)clipse. There are currently no instructions for importing via **IntelliJ** or other Java IDEs.

Eclipse and JavaFX-x64:
```
1. Download JavaFX SDKs if you haven't already from https://openjfx.io/
2. Download a .zip archive of the project
3. Extract it in a suitable folder
4. Open Eclipse and select File->Import->Archive File
5. Select the archive and press finish
6. Right click on the project folder and select your JavaFX library.
7. Build using the build.xml
```
If you do not have a JavaFX library referenced already:
```
1. Select User Library->User Libraries->New...
2. Add a name for the library 
3. Select the new library and add External JARs
4. Locate your JavaFX SDK and goto the lib folder
5. Add all jar files onto the library. 
```
## Newly Added Features
### Fully Complete and working properly:
1. New game modes: 5x5 and 7x7
2. A menu scene when booting up
3. A settings scene featuring credits and the ability to change themes
4. Easy addition for css themes
5. A game saving system and hiscore saving system
### Not Implemented:
1. A standalone runnable jar file
2. A scoreboard system
3. Saving the game in the form of an XML file

These features were not implemented either due to their complexity (like making a standalone executable jar) or due to my incompetence. 

For example, I removed the account class so that I can make the game save an XML file containing everything that was required, only to realized that I don't know how to manipulate XMLs in Java.
## Major changes to the project
All files within the project are entirely new files.

For the sake of restructuring the whole project in accordance to the MVC pattern and to use FXML files, I have decided to rewrite the entirety of the project. Most methods such as those in GameModel are split from the original GameScene and made suitable for controllers to use.

The package main.io is structured based on my lack of experience when it comes to file manipulation. It was ultimately split into several individual .txt files so that it's easier to load them.
## How to contribute
You can read up the documentation for the source code at doc/
A basic template for css files is provided in the form of src/template.css. If you wish to add more styles, you may edit the template (including it's name) and put it into the src folder.