// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class tests the SongInterface and the BackendInterface.
 */
public class BackendDeveloperTests {

  /**
   * This method tests the Song constructor using the getter methods.
   */
  @Test
  public void testSongInterface() {
    // Creating a song object
    Song s1 = new Song( "Hey, Soul Sister","Train", 2010, "neo mellow", 67);

    // Testing the getters of the song
    assertEquals("Train", s1.getArtist(), "Artist name is not correct");
    assertEquals("Hey, Soul Sister", s1.getTitle(), "Song title is not correct");
    assertEquals(2010, s1.getYear(), "Song year is not correct");
    assertEquals("neo mellow", s1.getGenre(), "Song genre is not correct");
    assertEquals(67, s1.getDanceability(), "Song danceability score is not correct");

    // Creating a second song object
    Song s2 = new Song( "Love The Way You Lie","Eminem", 2010, "detroit hip hop", 75);

    // Testing the getters of the song
    assertEquals("Eminem", s2.getArtist(), "Artist name is not correct");
    assertEquals("Love The Way You Lie", s2.getTitle(), "Song title is not correct");
    assertEquals(2010, s2.getYear(), "Song year is not correct");
    assertEquals("detroit hip hop", s2.getGenre(), "Song genre is not correct");
    assertEquals(75, s2.getDanceability(), "Song danceability score is not correct");

    // Creating a third song object
    Song s3 = new Song( "TiK ToK","Kesha", 2010, "dance pop", 76);

    // Testing the getters of the song
    assertEquals("Kesha", s3.getArtist(), "Artist name is not correct");
    assertEquals("TiK ToK", s3.getTitle(), "Song title is not correct");
    assertEquals(2010, s3.getYear(), "Song year is not correct");
    assertEquals("dance pop", s3.getGenre(), "Song genre is not correct");
    assertEquals(76, s3.getDanceability(), "Song danceability score is not correct");

    // Testing the compareTo method
    assertTrue(s3.compareTo(s2) == 1, "CompareTo method is broken");
    assertTrue(s1.compareTo(s2) == -8, "CompareTo method is broken");

    // Testing equals method
    Song s3Duplicate = new Song("TiK ToK","Kesha", 2010, "dance pop", 76);
    assertEquals(true, s3.equals(s3Duplicate));

    Song s2Wrong = new Song ("Bob", "Bob", 2023, "dance pop", 75);
    assertEquals(false, s2.equals(s2Wrong));
  }

  /**
   * This method checks if a valid file path results in the data being read from a small sample csv
   * file.
   */
  @Test
  public void testValidFilePath() {
    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();

    //instantiating a new backend
    BackendImplementation backend = new BackendImplementation(placeholder);

    //a valid data path
    String validPath = "src/songs.csv";

    //checking if the loadData returns true, meaning it successfully loaded the file path
    assertTrue(backend.loadData(validPath), "Unable to load valid file path!");
  }

  /**
   * This method checks if an invalid file path results in an exception being thrown using a random
   * path name.
   */
  @Test
  public void testInvalidFilePath() {
    // Testing if an invalid file path results in an exception being thrown
    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();

    //instantiating a new backend
    BackendImplementation backend = new BackendImplementation(placeholder);

    //an invalid path
    String invalidPath = "rubbish.csv";

    //checking if it loads
    assertFalse(backend.loadData(invalidPath), "Invalid path not returning correct output");
  }

  /**
   * This method checks if the getAverageDanceability method correctly calculates the average
   * danceability score of a small data set in a red black tree.
   */
  @Test
  public void testAvgDanceability() {
    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();
    //instantiating a new backend
    BackendImplementation backend = new BackendImplementation(placeholder);

    //loading the data from a small subset with a known average danceability
    String validPath = "src/SmallSampleData.csv";
    backend.loadData(validPath);

    //getting the average danceability
    double average = backend.getAverageDanceability();

    double expectedAverage = 70.4;

    //seeing if the average is equal to the expected
    //using the delta value to accommodate for the double error
    assertEquals(average, expectedAverage, 0.001);
  }

  /**
   * This method checks if the list returned by the getSongsAboveThreshold method contains the same
   * song objects as expected.
   */
  @Test
  public void testSongsAboveThreshold() {
    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();

    //instantiating a new backend
    BackendImplementation backend = new BackendImplementation(placeholder);

    //loading the data
    backend.loadData("songs.csv");

    int threshold = 70;

    ArrayList<SongInterface> songs = backend.getSongsAboveThreshold(threshold);

    //goes through every song in songs, and makes sure it is above or equal to the threshold
    for (int i = 0; i < songs.size(); i++) {
      assertTrue(songs.get(i).getDanceability() >= threshold);
    }
  }

  //Test 6: Check if getSongsAboveThreshold() returns an empty list when no
  //songs meet the threshold
  @Test
  public void testGetSongsAboveInvalidThreshold() {

    IterableMultiKeyRBT<Integer> placeholder = new IterableMultiKeyRBT<>();

    //instantiating a new backend
    BackendImplementation backend = new BackendImplementation(placeholder);

    //loading the data from a subset with no songs equal to or above 95
    backend.loadData("SmallSampleData.csv");

    int invalidThreshold = 95;

    ArrayList<SongInterface> songs = backend.getSongsAboveThreshold(invalidThreshold);

    //checking if it is empty
    assertTrue(songs.isEmpty());
  }
}
