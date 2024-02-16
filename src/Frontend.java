// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

/**
 * This class is the frontend of the program. It is responsible for taking in user input and
 * displaying the output to the user.
 */
public class Frontend implements FrontendInterface {

  //declaring instance variables
  Scanner sc;
  BackendImplementation ref;
  boolean fileNotLoaded = true;

  /**
   * This method act as the constructor for the frontend class. It takes in a scanner object and
   * a reference to backend class and initialize the instance variables.
   * @param sc - scanner object
   * @param ref - reference to backend class
   */
  public Frontend(Scanner sc, BackendImplementation ref) {
    //initializing the instance variables
    this.sc = sc;
    this.ref = ref;
  }

  /**
   * This method is responsible for the main menu loop. It will display the main menu and take in
   * user input. It will keep running until user choose to exit the program.
   */
  public void mainMenuLoop() {
    int choice;

    //prompting the user to enter a valid file path for the first time before doing anything else.
    do {

      System.out.println("Welcome to Song Searcher!");
      loadFile();
    }while (fileNotLoaded);

    //main menu loop
    do {

      System.out.println("\n\nPlease choose from the following options:");
      System.out.println("1. Load another file");
      System.out.println("2. List songs by score");
      System.out.println("3. Show average score");
      System.out.println("4. Exit");

      System.out.println("\nEnter your choice: ");
      choice = sc.nextInt();
      sc.nextLine();

      if (choice < 1 || choice > 4) {
        System.out.println("Invalid input. Please enter a number between 1 and 4 only!");
        continue;
      }

      System.out.println("\n\nYou chose: " + choice + "\n");
      switch (choice) {
        case 1 -> loadFile();
        case 2 -> listSongsByScore();
        case 3 -> showAvgScore();
        case 4 -> exitApp();
      }

    } while (choice != 4);
  }

  /**
   * This method is responsible for loading the file. It will take in user input for the file path
   * and load the file by calling backend's loadData method. It will keep running until user input
   * a valid file path.
   */
  public void loadFile() {
    String path;

    boolean isNotValid = true;
    //clearing the songs arraylist if the user choose to load another file
    if (!fileNotLoaded){
      ref.songs.clear();
    }

    //prompting the user to enter a valid file path
    do {
      System.out.println("\n\nEnter the file path: ");
      path = sc.nextLine();

      isNotValid = !ref.loadData(path);

      if (isNotValid){
        System.out.println("\n\nYou entered non existent or unvalid file path! Please try again!");
      }
    } while (isNotValid);
    fileNotLoaded = false;
    System.out.println("\n\nFile loaded successfully! The file name is : " + path);
  }

  /**
   * This method is responsible for listing the songs by score. It will take in user input for the
   * danceability score and display the songs with danceability score above the threshold. The
   * method will keep running until user input a valid danceability score. Then it will display all
   * the songs with danceability score above the threshold.
   */
  public void listSongsByScore() {
    int danceScore;
    //prompting the user to enter a valid danceability score
    do {
      System.out.println("\n\nEnter the danceability score: ");
      danceScore = sc.nextInt();
      sc.nextLine();

      if (danceScore < 0 || danceScore > 100) {
        System.out.println("\n\nInvalid input. Please enter a number between 0 and 100 only!");
      }

    } while (danceScore < 0 || danceScore > 100);

    //getting the songs with danceability score above the threshold from backend class
    ArrayList<SongInterface> songsAboveThreshold = new ArrayList<>();
    songsAboveThreshold = ref.getSongsAboveThreshold(danceScore);

    //displaying the songs with danceability score above the threshold
    System.out.println("\n\nThe songs with danceability score above " + danceScore + " are: ");
    for (int i = 0; i < songsAboveThreshold.size(); i++) {
      System.out.println(songsAboveThreshold.get(i).toString());
    }
  }

  /**
   * This method is responsible for showing the average danceability score. It will call backend's
   * getAverageDanceability method and display the average danceability score.
   */
  public void showAvgScore() {
    //getting the average danceability score from backend class
    double avg = ref.getAverageDanceability();
    System.out.println("\n\nThe average danceability score is: " + avg);

  }

  /**
   * This method is responsible for exiting the program. It will display a thank you message to the
   * user.
   */
  public void exitApp() {
    System.out.println("\n\nThank you for using Song Searcher!");
  }


  /**
   * This is the main method of the program. It will create a scanner object and a backend object.
   * Then it will create a frontend object and run the main menu loop.
   * @param args - command line arguments
   */
  public static void main(String[] args) {

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend placeholder
    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();
    //creating backend object
    BackendImplementation backend = new BackendImplementation(placeholder);

    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    songSearcher.mainMenuLoop();

  }

}
