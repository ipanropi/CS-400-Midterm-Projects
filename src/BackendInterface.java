// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;


/**
 * Interface for the backend to expose stuff to the frontend
 * @author Adish Jain, Learoy Daryl Joseph, Ahmad Adi Imran
 */
public interface BackendInterface {

  //  /**
  //   * Constructor for creating a BackendInterface object.
  //   */
  //  BackendInterface();
  //  public BackendInterface(IterableMultiKeySortedCollectionInterface iterable) {
  //
  //  }

  /**
   * Read data from a file containing the data
   * @param filePath the path to the file containing the data
   * @return True if the data is successfully loaded, False otherwise
   */
  public boolean loadData(String filePath);

  /**
   * This method lists the average danceability of all songs
   * @return the average danceability
   */
  public double getAverageDanceability();

  /**
   * Get a list of all songs with danceability score above or equal to a minimum specific threshold
   * @param threshold the minimum danceability score
   * @return a list of songs above or equal to the minimum danceability score
   */
  public ArrayList<SongInterface> getSongsAboveThreshold(int threshold);
}
