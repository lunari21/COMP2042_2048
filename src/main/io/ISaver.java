package main.io;

/**
 * Saver interface for categorizing saver classes.
 * @author Alexander Tan Ka Jin
 */
public interface ISaver {
	//Empty. Originally, there were plans to put a shared save method but that proved
	//to be difficult as all savers are static util classes and they require different save files.
	//
	//In general, main.io was a later addition to the project. I didn't had much time to understand
	//how to use XML in java which was why the save files were the structured the way they are.
}
