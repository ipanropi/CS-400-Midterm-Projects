// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Scanner;

/**
 * This class contains tests for functionalities provided by the frontend of the application,
 * mainly simulating a user's interactions and ensuring the frontend behaves as expected.
 */
public class FrontendDeveloperTests {

  /**
   * Test the functionality of loadfile menu.
   * This method simulates a user trying to load a file named 'SmallSampleData.csv'.
   * It checks if the frontend asks for the file path and attempts to load it.
   */
  @Test
  public void testLoadFile() {
    // Initialize tester with inputs for loading a file
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);
    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // calling loadFile method that prompt the user for the file path
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();
    System.out.println(output);

    // Validate the output for successful file load
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") && output.contains("SmallSampleData.csv"));
  }

  /**
   * Test the functionality for prompting user to list songs based on a specific score.
   * This method simulates a user trying to list songs with a danceability score of 60.
   */
  @Test
  public void testListSongsByScore() {
    // Initialize tester with inputs to list songs by a specific score
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n2\n60\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // calling listSongsByScore method that prompt the user for the score
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();
    System.out.println(output);
    System.out.println();

    // Validate the output for the expected song score
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("Enter the danceability score: ") &&
        output.contains("Thank you for using Song Searcher!"));  }

  /**
   * Test the functionality to display the average danceability score of all songs.
   * It ensures the frontend correctly displays the average score.
   */
  @Test
  public void testShowAvgScore() {
    // Initialize tester with inputs to display average score
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n3\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // Display the average score of all songs
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    // Validate the output for the expected average score
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("The average danceability score is: 70.4"));
  }

  /**
   * Test the functionality of the main menu loop.
   * This method ensures that the frontend's main menu is behaving as expected
   * and respond appropriately without crashing.
   */
  @Test
  public void testMainMenuLoop() {
    // Initialize tester with an invalid main menu option
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n5\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // calling mainMenuLoop method that prompt the user for the main menu option
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    // Validate the output for an invalid input message
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("Invalid input. Please enter a number between 1 and 4 only!"));
  }

  /**
   * Test the functionality of exiting the application.
   * This method ensures that the frontend can handle an exit command and
   * provide a message to the user.
   */
  @Test
  public void testExitApp(){
    // Initialize tester that simulates a user trying to exit the application
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // calling mainMenuLoop method that prompt the user for the main menu option, in this case exit the program
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    // Validate the output for an invalid input message
    Assertions.assertTrue(output.contains("Thank you for using Song Searcher!"));

  }

  /**
   * Test the functionality of the integration of frontend and backend. This method ensures that
   * the frontend will output the expected output when the user chooses to get the average song
   * danceability. This method also check if backend is calculating and returning the expected
   * value of average of danceability.
   */
  @Test
  public void testGetAverageDanceabilityIntegration(){
    // Initialize tester with inputs to display average score
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n3\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    // Display the average score of all songs
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    // Validate the output for the expected average score
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("The average danceability score is: "));

    //Testing backend getAverageDanceability method
    Assertions.assertEquals(70.4, backend.getAverageDanceability());
  }

  /**
   * Test the functionality of the integration of frontend and backend. This method ensures that
   * the frontend will output the expected output when the user chooses to list songs above a certain.
   * This method also check if backend is returning the expected song with score above threshold.
   */
  @Test
  public void testGetSongsAboveThresholdIntegration(){
    // Initialize tester with inputs to display average score
    TextUITester test = new TextUITester("src/songs.csv\n2\n70\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);

    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    backend.loadData("src/songs.csv");


    // calling listSongsByScore method that prompt the user for the score
    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    //Check if frontend work as intended
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("Enter the danceability score: ") &&
        output.contains("Thank you for using Song Searcher!"));

    //checking backend songList have right number of song with score above threshold

    // checking if song danceability is >= 70
    for (SongInterface song : backend.getSongsAboveThreshold(70)) {
      boolean isAboveThreshold = song.getDanceability() >= 70;
      Assertions.assertTrue(isAboveThreshold);
    }


  }


  /**
   * Test the functionality of the integration of frontend and backend. This method ensures that
   * the frontend will output the expected output when the user chooses to load a file.
   * This method also check if backend is returning the expected behavior when asked to load a 
   * nonexistent file.
   */
  @Test
  public void testLoadDataIntegration(){
    // Initialize tester with inputs to display average score
    TextUITester test = new TextUITester("src/SmallSampleData.csv\n4\n");

    //creating scanner object
    Scanner in = new Scanner(System.in);

    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object                                                                                                                                   
    BackendImplementation backend = new BackendImplementation(treeStorage);
    // Instantiate and run the frontend object
    Frontend songSearcher = new Frontend(in, backend);

    songSearcher.mainMenuLoop();

    // Retrieve the output from the tester
    String output = test.checkOutput();

    // Validate the output for successful file load
    Assertions.assertTrue(output.startsWith("Welcome to Song Searcher!") &&
        output.contains("File loaded successfully! The file name is : src/SmallSampleData.csv"));

    //testing backend loadData method
    boolean loadedFile = backend.loadData("src/non-existent.csv");

    //check if backend return expected output.
    Assertions.assertFalse(loadedFile);
  }

  /**
   * This test my partner code (backend). This test check if the backend is loading the file
   * correctly and if the number of songs with danceability above 95 is correct. It also check if
   * each song have danceability above 95.
   */
  @Test
  public void testGetSongsAboveThresholdForPartner(){
    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object
    BackendImplementation backend = new BackendImplementation(treeStorage);


    // check if loading file works
    Assertions.assertTrue(backend.loadData("src/songs.csv"));

    // check if the number of songs with danceability above 95 is correct
    Assertions.assertEquals(3, backend.getSongsAboveThreshold(95).size());

    // checking if song danceability is >= 95
    for (SongInterface song : backend.getSongsAboveThreshold(95)) {
      boolean isAboveThreshold = song.getDanceability() >= 95;
      Assertions.assertTrue(isAboveThreshold);
    }
  }

  /**
   * This test my partner code (backend). This test check if the backend is loading the file
   * correctly both using valid and invalid file. It also check if the backend return the expected
   * output when loading invalid file.
   */
  @Test
  public void testLoadValidAndInvalidFileForPartner(){
    //creating backend treeStorage
    IterableMultiKeyRBT<Integer> treeStorage = new IterableMultiKeyRBT<>();
    //creating backend object
    BackendImplementation backend = new BackendImplementation(treeStorage);


    // check if loading file works
    Assertions.assertTrue(backend.loadData("src/songs.csv"));

    Assertions.assertFalse(backend.loadData("src/invalid.csv"));
  }


}
