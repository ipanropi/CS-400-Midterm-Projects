// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


/**
 * This public interface represents the frontend of the Song Searcher app, music app that interacts
 * with the user to perform various commands based on the dance-ability of the song.
 *
 * @author nikhil gudladana, Anushka Dwivedi, Muhammad Irfan Bin Mohd Ropi
 */

public interface FrontendInterface {

  //Hypothetical constructor is
  //public frontend(Scanner sc, BackendClass ref);


  /**
   * Main menu loop to allow users to interact with the program.
   */
  void mainMenuLoop();

  /**
   * Displays the command menu for loading a data file and handles user input for this command.
   *
   */
  void loadFile();

  /**
   * This method should prompt the user for a danceability score and display the matching songs.
   */
  void listSongsByScore();

  /**
   * This method should calculate and display the average danceability score in the loaded dataset.
   */
  void showAvgScore();

  /**
   * This method should terminate the application.
   */
  void exitApp();



}
