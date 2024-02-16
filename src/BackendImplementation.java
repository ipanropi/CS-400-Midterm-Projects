// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class BackendImplementation implements BackendInterface{
  IterableMultiKeySortedCollectionInterface songs;

  public BackendImplementation(IterableMultiKeySortedCollectionInterface iter){
    this.songs = iter;
  }

  /**
   * loads data from a file containing the data
   * @param filename the path to the file containing the data
   * @return True if the data is successfully loaded, False otherwise
   */
  @Override
  public boolean loadData(String filename){
    //using buffered reader to load the csv
    try(BufferedReader br = new BufferedReader(new FileReader(filename))){
      String line;
      br.readLine(); //skipping the header
      while((line = br.readLine()) != null){

        //getting all columns as an array of strings of length 14
        String[] values = parseCSVLine(line);


        //defining temp values from csv
        String title = values[0];
        String artist = values[1];
        String genre = values[2];
        int year = Integer.parseInt(values[3]);
        int dance = Integer.parseInt(values[6]);

        //creating a new song and adding this into the song ArrayList
        Song song = new Song(title, artist, year, genre, dance);

        songs.insertSingleKey(song);
      }
    }
    catch (Exception e){
      return false; //error occurred while loading data
    }
    return true; //successfully loaded data
  }

  /**
   * This method parses a line from the csv file and returns an array of strings
   * @param line - the line to be parsed
   * @return an array of strings containing the values of the columns
   */
  private String[] parseCSVLine(String line) {

    //creating an array of empty strings of length 14
    String[] values = new String[15];

    //this variable contains the current string we are building
    String currentValue = "";

    //when this is true -> we are inside quotes and add commas to current value
    //when this is false -> we are outside quotes and commas act as separators
    boolean insideQuotes = false;

    //index to keep track of the current column we are at right now
    int index = 0;

    //looping through every char in the current line
    for (char c : line.toCharArray()) {

      //if we hit a quote, we flip the insideQuotes bool
      if (c == '"') {
        insideQuotes = !insideQuotes;
      }
      //if we hit a comma and we are not inside quotes, we store the currentValue into the array
      //and restart the process for the next column
      else if (c == ',' && !insideQuotes) {
        values[index] = currentValue.toString();
        currentValue = ""; // Clear the StringBuilder
        index += 1;
      } else
      //if we are inside quotes with comma or on any other character, we just add it to the current valeu
      {
        currentValue += c;
      }
    }

    //last value doesn't end with a comma so we have to ensure it is added with this
    values[index] = currentValue;
    return values;
  }

  /**
   * This method lists the average danceability of all songs
   * @return the average danceability
   */
  @Override
  public double getAverageDanceability(){
    //keeping track of the sum of danceability scores and the number of songs (for average calculation)
    int totalDanceability = 0;
    int arraySize = this.songs.numKeys();

    //using an advanced for-loop
    for (Object o: this.songs){

      //o needs to be Song
      if(!(o instanceof Song) ){
        throw new IllegalArgumentException("Not a song");
      }

      //castin git
      Song song = (Song) o;

      totalDanceability += song.getDanceability();
    }

    double average = ((double) totalDanceability) / ((double) arraySize);

    return average;
  }

  /**
   * Get a list of all songs with danceability score above or equal to a minimum specific threshold
   * @param threshold the minimum danceability score
   * @return
   */
  @Override
  public ArrayList<SongInterface> getSongsAboveThreshold(int threshold) {
    ArrayList<SongInterface> result = new ArrayList<>();

    for (Object o : this.songs) {
      //o needs to be Song
      if (!(o instanceof Song)) {
        throw new IllegalArgumentException("Not a song");
      }
      //casting
      Song song = (Song) o;
      if (song.getDanceability() >= threshold) {
        result.add(song);
      }
    }
    return result;
  }
}
