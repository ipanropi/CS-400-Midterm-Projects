// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public interface SortedCollectionInterface<T extends Comparable<T>> {

  public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

  public boolean contains(Comparable<T> data);

  public int size();

  public boolean isEmpty();

  public void clear();

}
