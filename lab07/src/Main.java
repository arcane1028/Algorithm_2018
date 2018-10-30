import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            String openPath = "data07.txt"; // 파일이름
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(new File(openPath)),
                            StandardCharsets.UTF_8
                    )
            );
            StringTokenizer tokenizer = new StringTokenizer(bufferedReader.readLine(), ",");
            int[] array = new int[tokenizer.countTokens()];
            int i = 0;

            while (tokenizer.hasMoreTokens()) {
                array[i] = Integer.parseInt(tokenizer.nextToken());
                i++;
            }

            CountInversion c = new CountInversion();
            c.print(array);
            int inversion = c.divide(array);
            System.out.println("\nInversion: " + inversion);
            c.print(array);

            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CountInversion {
    int divide(int array[]) {
        int size = array.length;//

        if (size <= 1) {
            return 0; // 배열길이가 1보다 작거나 같으면 Inversion이 없으므로 0을 반환
        }
        // 배열을 반으로 나눔
        int mid = (size + 1) / 2;
        int[] temp1 = new int[mid];
        int[] temp2 = new int[size - mid];
        for (int i = 0; i < temp1.length; i++) {
            temp1[i] = array[i];
        }
        for (int i = 0; i < temp2.length; i++) {
            temp2[i] = array[i + mid];
        }
        // 좌우 배열의 Inversion 구함
        int count_inversion1 = divide(temp1);
        int count_inversion2 = divide(temp2);

        // 합병 정렬과 Inversion 개수
        int index1 = 0, index2 = 0;
        int main_index = 0;
        int cur_inversion = 0;
        while ((index1 != temp1.length) && (index2 != temp2.length)) {
            if (temp1[index1] < temp2[index2]) {
                array[main_index] = temp1[index1];
                main_index++;
                index1++;
            } else {
                // Inversion 출력
                for (int i = index1; i < temp1.length; i++) {
                    System.out.printf("(%d-%d)\n", temp1[i], temp2[index2]); //
                }
                array[main_index] = temp2[index2];
                main_index++;
                index2++;
                cur_inversion = cur_inversion + temp1.length - index1; //인버젼 개수 증가
            }
        }
        if (index1 != temp1.length) {
            while (index1 != temp1.length) {
                array[main_index] = temp1[index1];
                main_index++;
                index1++;
            }
        } else {
            while (index2 != temp2.length) {
                array[main_index] = temp2[index2];
                main_index++;
                index2++;
            }
        }
        // 현재와 나눈 배열의 Inversion 합
        return cur_inversion + count_inversion1 + count_inversion2;
    }
    // 출력하는 함수
    void print(int array[]) {
        for (int i = 0; i < array.length; i++) {
            System.out.printf("%d ", array[i]);
        }
        System.out.println("");
    }
}
