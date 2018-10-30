import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class MySort04 {

    public static void main(String[] args) {
        try {
            String openPath = "data04.txt"; // 파일이름
            String savePath1 = "data04_Sort_Sel.txt";
            String savePath2 = "data04_Sort_Bub.txt";
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(new File(openPath)),
                    StandardCharsets.UTF_8
                )
            );
            BufferedWriter bufferedWriter1 = new BufferedWriter(
                new FileWriter(new File(savePath1))
            );
            BufferedWriter bufferedWriter2 = new BufferedWriter(
                new FileWriter(new File(savePath2))
            );
            StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), ", ");
            int[] array = new int[tokenizer.countTokens()];

            for (int i = 0; tokenizer.hasMoreTokens(); i++) {
                array[i] = Integer.parseInt(tokenizer.nextToken());
            }

            Sort sort = new Sort();

            int[] temp = new int[array.length];
            // 선택정렬
            System.arraycopy(array, 0, temp, 0, array.length);

            sort.selSort(temp);

            sort.myWriter(bufferedWriter1, temp);
            // 버블 정렬
            System.arraycopy(array, 0, temp, 0, array.length);

            sort.bubSort(temp);

            sort.myWriter(bufferedWriter2, temp);

            bufferedReader.close();
            bufferedWriter1.close();
            bufferedWriter2.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Sort {

    void selSort(int[] A) {

        int n = A.length - 1;

        for (int i = 0; i <= n - 1; i++) {
            int smallest = i;
            for (int j = i + 1; j <= n; j++) {
                if (A[j] < A[smallest]) {
                    smallest = j;
                }
            }
            exchange(A, i, smallest);
        }

    }

    void bubSort(int[] array) {
        int length = array.length - 1;

        for (int i = 0; i <= length - 1; i++) {

            for (int j = length; j >= i + 1; j--) {
                if (array[j] < array[j - 1]) {
                    exchange(array, j, j - 1);
                }
            }

        }
    }

    /*
    *void bubSort(int[] array) {
        int n = array.length-1;

        for (int i = n - 1; i >= 0; i--) {

            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    exchange(array, j, j + 1);
                }
            }

        }
    }
    * */

    private void exchange(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }

    void myWriter(BufferedWriter bufferedWriter, int[] A) throws IOException {

        for (int i = 0; i < A.length; i++) {
            //System.out.print(temp[i] + " ");
            if (i == A.length - 1) {
                bufferedWriter.write(A[i] + "");
            } else {
                bufferedWriter.write(A[i] + ",");
            }
        }

    }
}