import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) {
    try {
      // TODO 읽는 파일 위치
      String openPath = "data02.txt";
      MySort sort = new MySort();
      MySort2 sort2 = new MySort2();
      BufferedReader bufferedReader = new BufferedReader(
          new FileReader(new File(openPath))
      );
      String line = bufferedReader.readLine();
      System.out.println(line);

      StringTokenizer tokenizer = new StringTokenizer(line, ", ");
      System.out.println("token " + tokenizer.countTokens());

      int length = tokenizer.countTokens();
      // 배열에 저장
      ArrayList<Integer> numbers = new ArrayList<Integer>();
      int[] array = new int[length];

      for (int i = 0; i < length; i++) {
        numbers.add(Integer.parseInt(tokenizer.nextToken()));
        array[i] = numbers.get(i);
      }

      sort.run(numbers, length); // ArrayList 사용
      //sort2.run(array, length); // array 사용

      bufferedReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("file");
    } catch (IOException io) {
      System.out.println("io");
    }

  }
}


class MySort {

  void run(ArrayList<Integer> numbers, int length) {
    String path3 = "[알고리즘]01_02_201302480_merge.txt";
    String path4 = "[알고리즘]01_02_201302480_3way_merge.txt";
    try {
      // 파일 읽기 쓰기
      BufferedWriter bufferedWriter3 = new BufferedWriter(
          new FileWriter(new File(path3))
      );
      BufferedWriter bufferedWriter4 = new BufferedWriter(
          new FileWriter(new File(path4))
      );

      // Sort
      long start = System.nanoTime();
      ArrayList<Integer> mergeSortArrayList = mergeSort(numbers, numbers.size());
      long end = System.nanoTime();
      System.out.println("3 실행 시간 : " + (end - start));
      writeFile(bufferedWriter3, mergeSortArrayList);

      start = System.nanoTime();
      ArrayList<Integer> mergeSort3wayArrayList = mergeSort3way(numbers, numbers.size());
      end = System.nanoTime();
      System.out.println("4 실행 시간 : " + (end - start));
      writeFile(bufferedWriter4, mergeSort3wayArrayList);

      boolean isSame = true;
      for (int i = 0; i < length; i++) {
        ArrayList<Integer> temp = new ArrayList<>(
            Arrays.asList(
                mergeSortArrayList.get(i),
                mergeSort3wayArrayList.get(i))
        );
        for (Integer integer : temp) {
          if (!integer.equals(mergeSortArrayList.get(i))) {
            isSame = false;
            break;
          }
        }
      }
      System.out.println("same " + isSame);

      bufferedWriter3.close();
      bufferedWriter4.close();

    } catch (FileNotFoundException e) {
      System.out.println("file");
    } catch (IOException io) {
      System.out.println("io");
    }
  }
  // 결과 파일에 쓰는 함수
  private void writeFile(BufferedWriter bufferedWriter, ArrayList<Integer> arrayList)
      throws IOException {
    int i;
    for (i = 0; i < arrayList.size() - 1; i++) {
      bufferedWriter.write(arrayList.get(i) + ",");
    }
    bufferedWriter.write(arrayList.get(i)+"");
  }
  // 합병 정렬
  private ArrayList<Integer> mergeSort(ArrayList<Integer> A, int n) {
    if (n == 1) {
      return A;
    } else {
      int mid1, mid2;
      if (n % 2 == 0) {
        mid1 = mid2 = (int) (n / 2);
      } else {
        mid1 = (int) (n / 2);
        mid2 = (int) (n / 2) + 1;
      }
      ArrayList<Integer> r1 = new ArrayList<>(
          mergeSort(new ArrayList<>(A.subList(0, mid1)), mid1));
      ArrayList<Integer> r2 = new ArrayList<>(
          mergeSort(new ArrayList<>(A.subList(mid1, n)), mid2));

      return merge(r1, r2);
    }
  }
  // 병합하는 함수
  private ArrayList<Integer> merge(ArrayList<Integer> A, ArrayList<Integer> B) {

    ArrayList<Integer> returnArray = new ArrayList<>();
    while (A.size() != 0 && B.size() != 0) {
      if (A.get(0) > B.get(0)) {
        returnArray.add(B.remove(0));
      } else {
        returnArray.add(A.remove(0));
      }
    }
    if (A.size() == 0) {
      returnArray.addAll(B);
    } else {
      returnArray.addAll(A);
    }
    return returnArray;
  }
  // 세부분으로 나누는 합병 정렬 함수
  private ArrayList<Integer> mergeSort3way(ArrayList<Integer> A, int n) {
    if (n == 1) {
      return A;
    } else {
      int mid1 = 0, mid2 = 0, mid3 = 0;
      if (n % 3 == 0) {
        mid1 = n / 3;
        mid2 = n / 3;
        mid3 = n / 3;
      } else if (n % 3 == 1) {
        mid1 = n / 3;
        mid2 = n / 3;
        mid3 = n / 3 + 1;
      } else if (n % 3 == 2) {
        mid1 = n / 3 + 1;
        mid2 = n / 3 + 1;
        mid3 = n / 3;
      }
      ArrayList<Integer> r1 = new ArrayList<>(
          mergeSort(new ArrayList<>(A.subList(0, mid1)), mid1));
      ArrayList<Integer> r2 = new ArrayList<>(
          mergeSort(new ArrayList<>(A.subList(mid1, mid1 + mid2)), mid2));
      ArrayList<Integer> r3 = new ArrayList<>(
          mergeSort(new ArrayList<>(A.subList(mid1 + mid2, n)), mid3));

      return merge(merge(r1, r2), r3);
    }
  }
}
// 배열 사용
class MySort2 {

  void run(int[] numbers, int length) {
    String path3 = "[알고리즘]01_02_201302480_merge.txt";
    String path4 = "[알고리즘]01_02_201302480_3way_merge.txt";
    try {
      // 파일 읽기 쓰기
      BufferedWriter bufferedWriter3 = new BufferedWriter(
          new FileWriter(new File(path3))
      );
      BufferedWriter bufferedWriter4 = new BufferedWriter(
          new FileWriter(new File(path4))
      );

      // Sort
      long start = System.nanoTime();
      int[] mergeSortArrayList = mergeSort(numbers, numbers.length);
      long end = System.nanoTime();
      System.out.println("3 실행 시간 : " + (end - start));
      writeFile(bufferedWriter3, mergeSortArrayList);

      start = System.nanoTime();
      int[] mergeSort3wayArrayList = mergeSort3way(numbers, numbers.length);
      end = System.nanoTime();
      System.out.println("4 실행 시간 : " + (end - start));
      writeFile(bufferedWriter4, mergeSort3wayArrayList);

      boolean isSame = true;
      for (int i = 0; i < length; i++) {
        int[] temp = {
            mergeSortArrayList[i],
            mergeSort3wayArrayList[i]
        };
        for (Integer integer : temp) {
          if (!integer.equals(mergeSortArrayList[i])) {
            isSame = false;
            break;
          }
        }
      }
      System.out.println("same " + isSame);

      bufferedWriter3.close();
      bufferedWriter4.close();

    } catch (FileNotFoundException e) {
      System.out.println("file");
    } catch (IOException io) {
      System.out.println("io");
    }
  }

  private void writeFile(BufferedWriter bufferedWriter, int[] array)
      throws IOException {
    int i;
    for (i = 0; i < array.length - 1; i++) {
      bufferedWriter.write(array[i] + ",");
    }
    bufferedWriter.write(array[i]+"");
  }
  // 배열사용 합병정렬
  private int[] mergeSort(int[] numbers, int n) {
    if (n == 1) {
      return numbers;
    } else {
      int mid1, mid2;
      if (n % 2 == 0) {
        mid1 = mid2 = (int) (n / 2);
      } else {
        mid1 = (int) (n / 2);
        mid2 = (int) (n / 2) + 1;
      }
      int[] r1 = new int[mid1];
      int[] r2 = new int[mid2];

      System.arraycopy(numbers, 0, r1, 0, mid1);
      System.arraycopy(numbers, mid1, r2, 0, mid2);

      r1 = mergeSort(r1, mid1);
      r2 = mergeSort(r2, mid2);

      return merge(r1, r2);
    }
  }
  // 배열사용 병합하는 함수
  private int[] merge(int[] numbers1, int[] numbers2) {
    int length1 = numbers1.length, length2 = numbers2.length;
    int index1 = 0, index2 = 0, index3 = 0;
    int[] returnArray = new int[length1 + length2];

    while (index1 != length1 && index2 != length2) {
      if (numbers1[index1] > numbers2[index2]) {
        returnArray[index3] = numbers2[index2];
        index2 += 1;
      } else {
        returnArray[index3] = numbers1[index1];
        index1 += 1;
      }
      index3 += 1;
    }

    if (index1 == length1) {
      System.arraycopy(numbers2, index2, returnArray, index3, length2 - index2);
    } else {
      System.arraycopy(numbers1, index1, returnArray, index3, length1 - index1);
    }
    return returnArray;
  }
  // 배열을 사용한 세부분으로 합병 정렬하는 함수
  private int[] mergeSort3way(int[] numbers, int n) {

    if (n == 1) {
      return numbers;
    } else {
      int mid1 = 0, mid2 = 0, mid3 = 0;
      if (n % 3 == 0) {
        mid1 = n / 3;
        mid2 = n / 3;
        mid3 = n / 3;
      } else if (n % 3 == 1) {
        mid1 = n / 3;
        mid2 = n / 3;
        mid3 = n / 3 + 1;
      } else if (n % 3 == 2) {
        mid1 = n / 3 + 1;
        mid2 = n / 3 + 1;
        mid3 = n / 3;
      }
      int[] r1 = new int[mid1];
      int[] r2 = new int[mid2];
      int[] r3 = new int[mid3];

      System.arraycopy(numbers, 0, r1, 0, mid1);
      System.arraycopy(numbers, mid1, r2, 0, mid2);
      System.arraycopy(numbers, mid1 + mid2, r3, 0, mid3);

      r1 = mergeSort(r1, mid1);
      r2 = mergeSort(r2, mid2);
      r3 = mergeSort(r3, mid3);

      return merge(merge(r1, r2), r3);
    }
  }
}