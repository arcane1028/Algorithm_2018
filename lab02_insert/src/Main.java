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
      String openPath = "data03.txt";
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

// ArrayList 를 사용한 정렬함수
class MySort {

  void run(ArrayList<Integer> numbers, int length) {
    String path1 = "[알고리즘]01_02_201302480_insertion.txt";
    String path2 = "[알고리즘]01_02_201302480_binary_insertion.txt";
    try {
      // 파일 읽기 쓰기
      BufferedWriter bufferedWriter1 = new BufferedWriter(
          new FileWriter(new File(path1))
      );
      BufferedWriter bufferedWriter2 = new BufferedWriter(
          new FileWriter(new File(path2))
      );

      // Sort
      System.out.println("array size " + numbers.size());
      long start = System.nanoTime();
      ArrayList<Integer> insertionSortArrayList = insertionSort(numbers, numbers.size());
      long end = System.nanoTime();
      System.out.println("1 실행 시간 : " + (end - start));
      writeFile(bufferedWriter1, insertionSortArrayList);

      start = System.nanoTime();
      ArrayList<Integer> binaryInsertionSortArrayList = binaryInsertionSort(numbers,
          numbers.size());
      end = System.nanoTime();
      System.out.println("2 실행 시간 : " + (end - start));
      writeFile(bufferedWriter2, binaryInsertionSortArrayList);

      // 같은지 확인
      boolean isSame = true;
      for (int i = 0; i < length; i++) {
        ArrayList<Integer> temp = new ArrayList<>(
            Arrays.asList(
                insertionSortArrayList.get(i),
                binaryInsertionSortArrayList.get(i)
            )
        );
        for (Integer integer : temp) {
          if (!integer.equals(insertionSortArrayList.get(i))) {
            isSame = false;
            break;
          }
        }
      }
      System.out.println("same " + isSame);

      bufferedWriter1.close();
      bufferedWriter2.close();

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
  // 삽입정렬
  private ArrayList<Integer> insertionSort(ArrayList<Integer> A, int n) {
    ArrayList<Integer> numbers = new ArrayList<>(A);

    for (int j = 1; j < n; j++) {
      int key = numbers.get(j);
      int i = j - 1;

      while (i > -1 && numbers.get(i) > key) {
        numbers.set(i + 1, numbers.get(i));
        i = i - 1;
      }
      numbers.set(i + 1, key);

    }
    return numbers;
  }

  // 이진탐색을 활용한 삽입정렬
  private ArrayList<Integer> binaryInsertionSort(ArrayList<Integer> A, int n) {
    ArrayList<Integer> numbers = new ArrayList<>(A);

    for (int j = 1; j < n; j++) {
      int key = numbers.remove(j);
      int index = binarySearch(numbers, 0, j - 1, key);

      numbers.add(index, key);
    }
    return numbers;
  }
  //이진탐색 재귀로 구현
  private int binarySearch(ArrayList<Integer> arrayList, int start, int end, int key) {
    int mid = (end - start) / 2 + start;
    if (end < start) {
      return mid;
    } else {
      if (arrayList.get(mid) > key) {
        return binarySearch(arrayList, start, mid - 1, key);
      } else if (arrayList.get(mid) < key) {
        return binarySearch(arrayList, mid + 1, end, key);
      } else {
        return mid;
      }
    }
  }
}
// 배열을 사용한 정렬
class MySort2 {

  void run(int[] numbers, int length) {
    String path1 = "[알고리즘]01_02_201302480_insertion.txt";
    String path2 = "[알고리즘]01_02_201302480_binary_insertion.txt";

    try {
      // 파일 읽기 쓰기
      BufferedWriter bufferedWriter1 = new BufferedWriter(
          new FileWriter(new File(path1))
      );
      BufferedWriter bufferedWriter2 = new BufferedWriter(
          new FileWriter(new File(path2))
      );

      // Sort
      System.out.println("array size " + numbers.length);
      long start = System.nanoTime();
      int[] insertionSortArrayList = insertionSort(numbers, numbers.length);
      long end = System.nanoTime();
      System.out.println("1 실행 시간 : " + (end - start));
      writeFile(bufferedWriter1, insertionSortArrayList);

      start = System.nanoTime();
      int[] binaryInsertionSortArrayList = binaryInsertionSort(numbers,
          numbers.length);
      end = System.nanoTime();
      System.out.println("2 실행 시간 : " + (end - start));
      writeFile(bufferedWriter2, binaryInsertionSortArrayList);

      boolean isSame = true;
      for (int i = 0; i < length; i++) {
        int[] temp = {
            insertionSortArrayList[i],
            binaryInsertionSortArrayList[i]
        };
        for (Integer integer : temp) {
          if (!integer.equals(insertionSortArrayList[i])) {
            isSame = false;
            break;
          }
        }
      }
      System.out.println("same " + isSame);

      bufferedWriter1.close();
      bufferedWriter2.close();

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
  //배열을 사용한 삽입정렬
  private int[] insertionSort(int[] numbers, int n) {
    int[] temp = new int[n];
    System.arraycopy(numbers, 0, temp, 0, n);

    for (int j = 1; j < n; j++) {
      int key = temp[j];
      int i = j - 1;

      while (i > -1 && temp[i] > key) {
        temp[i + 1] = temp[i];
        i = i - 1;
      }
      temp[i + 1] = key;
    }

    return temp;
  }
  //배열을 사용한 이진탐색 삽입정렬
  private int[] binaryInsertionSort(int[] numbers, int n) {
    int[] temp = new int[n];
    System.arraycopy(numbers, 0, temp, 0, n);

    for (int j = 1; j < n; j++) {
      int key = temp[j];
      int index = binarySearch(temp, 0, j - 1, key);

      System.arraycopy(temp, index, temp, index + 1, j - index);
      temp[index] = key;

    }
    return temp;
  }
  // 이진탐색
  private int binarySearch(int[] numbers, int start, int end, int key) {

    int mid = (end - start) / 2 + start;
    if (end < start) {
      return mid;
    } else {
      if (numbers[mid] > key) {
        return binarySearch(numbers, start, mid - 1, key);
      } else if (numbers[mid] < key) {
        return binarySearch(numbers, mid + 1, end, key);
      } else {
        return mid;
      }
    }
  }


}