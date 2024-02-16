import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Tests {

  @Test
  public void test1() {
    System.out.println("Green");
    ;
  }

  @Test
  public void test2() {
    Assertions.assertTrue(false);
    System.out.println("Green");
    ;
  }

  @Test
  public void test3() {
    System.out.println("Green");
  }

  public void test4(){

    Assertions.assertTrue(true);
    System.out.println("Green");
  }

  @Test
  public static void test5(){
    System.out.println("Blue");
  }}
