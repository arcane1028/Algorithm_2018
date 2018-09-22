import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class Make {

  public static void main(String[] args) {
    int NUMBER = 50;

    try {
      BufferedWriter bufferedWriter = new BufferedWriter(
          new FileWriter(new File("data03.txt"))
      );
      for (int i = 0; i < NUMBER-1; i++) {
        int number = (int)(Math.random()*NUMBER+1);

        bufferedWriter.write(number+",");
      }
      bufferedWriter.write(String.valueOf((int)(Math.random()*NUMBER+1)));
      bufferedWriter.close();

    }catch (Exception e){
      System.err.println(e);
    }


  }

}
