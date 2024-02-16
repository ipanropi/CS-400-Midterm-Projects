// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public class Song<T> implements SongInterface, Comparable<T> {

  //declaring all the fields
  private String title; //Title of the song
  private String artist; //Name of the artist of the song
  private int year; //Year of release for the song
  private String genre; //Genre of the song
  private int danceability; //Danceability score of the song

  /**
   * Construcor
   * @param title song title
   * @param artist name of the artist
   * @param year year of release
   * @param genre song genre
   * @param danceability danceability score
   */
  public Song(String title, String artist, int year, String genre, int danceability){

    //setting the fields to the parameters in this constructor
    this.title = title;
    this.artist = artist;
    this.year = year;
    this.genre = genre;
    this.danceability = danceability;
  }

  /**
   * @param title title of the song
   */
  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param artist name of the song's artist
   */
  @Override
  public void setArtist(String artist) {
    this.artist = artist;
  }

  /**
   * @param year release year for the song
   */
  @Override
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * @param genre genre of the song
   */
  @Override
  public void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * @param danceability danceability score of the song
   */
  @Override
  public void setDanceability(int danceability) {
    this.danceability = danceability;
  }

  /**
   * @return title of the song
   */
  @Override
  public String getTitle() {
    return this.title;
  }

  /**
   * @return name of the artist
   */
  @Override
  public String getArtist() {
    return this.artist;
  }

  /**
   * @return release year of the song
   */
  @Override
  public int getYear() {
    return this.year;
  }

  /**
   * @return genre of the song
   */
  @Override
  public String getGenre() {
    return this.genre;
  }

  /**
   * @return danceability score of the song
   */
  @Override
  public int getDanceability() {
    return this.danceability;
  }

  /**
   * @param song song to compare with
   * @return 0 if equal, 1 if greater, -1 if less
   */
  @Override
  public int compareTo(T song) {
    if(song instanceof Song){
      Song mySong = (Song) song;
      return this.danceability - mySong.danceability;
    }
    else{
      throw new IllegalArgumentException("song is not of type song");
    }

  }

  /**
   * @param song song to check if it is equal to
   * @return true if equal, false if not equal
   */
  @Override
  public boolean equals(Object song) {

    if(!(song instanceof Song)){
      throw new IllegalArgumentException("song is not instance of song");
    }

    try{
      Song mySong = (Song) song;
      //if song is null, then we return false
      if (mySong == null){
        return false;
      }

      //returning true if all of these are true, false if any of these are false
      return this.title.equals(mySong.title) &&
          this.artist.equals(mySong.artist) &&
          this.genre.equals(mySong.genre) &&
          this.danceability == mySong.danceability &&
          this.year == mySong.year;
    }
    catch(Exception e){
      return false;
    }
  }

  /**
   *
   * @return string representation of the song
   */
  @Override
  public String toString() {
    return this.title + " by " + this.artist + " (" + this.year + ")" + " [" + this.genre + "] " + this.danceability;
  }
}
