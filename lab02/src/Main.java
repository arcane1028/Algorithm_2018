import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static class Sort {
        private void insertionSort(ArrayList<Integer> A, int n) {
            System.out.println("arraylist sort");
            //System.out.println("before " + numbers);
            ArrayList<Integer> numbers = new ArrayList<>(A);
            //numbers.addAll(A);
            for (int j = 1; j < n; j++) {
                int key = numbers.remove(j);
                int i = j - 1;

                while (i > 0 && numbers.get(i) > key) {
                    i = i - 1;
                }
                numbers.add(i + 1, key);

            }
            System.out.println("after  " + numbers);
        }

        private void insertionSort(int[] numbers, int n) {
            System.out.println("array sort");
            /*
            System.out.print("before  ");
            for (int i = 0; i < n; i++) {
                System.out.print(numbers[i] + "  ");
            }
            System.out.println(" ");
            * */

            for (int j = 1; j < n; j++) {
                int key = numbers[j];
                int i = j - 1;

                while (i > 0 && numbers[i] > key) {
                    i = i - 1;
                }
                System.arraycopy(numbers, i + 1, numbers, i + 2, j - i - 1);
                numbers[i + 1] = key;
            }
            System.out.print("after   ");
            for (int i = 0; i < n; i++) {
                System.out.print(numbers[i] + "  ");
            }
            System.out.println(" ");
        }

        private List<Integer> mergeSort(List<Integer> A, int n) {
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
                List<Integer> r1 = new ArrayList<>(merge(A.subList(0, mid1), mid1));
                List<Integer> r2 = new ArrayList<>(merge(A.subList(mid1, n), mid2));

                r1.addAll(r2);
                return r1;
            }
        }

        private List<Integer> merge(List<Integer> A, int n) {
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
                List<Integer> r1 = new ArrayList<>(mergeSort(A.subList(0, mid1), mid1));
                List<Integer> r2 = new ArrayList<>(mergeSort(A.subList(mid1, n), mid2));

                r1.addAll(r2);
                return r1;
            }
        }
    }

    public static void main(String[] args) {
        // TODO 파일 경로
        String openPath = "data02.txt"; // 프로젝트 바로아래 경로
        String savePath = "output.txt"; // 프로젝트 바로아래 경로
        Sort sort = new Sort();
        try {
            // 파일 읽기 쓰기
            File openFile = new File(openPath);
            File saveFile = new File(savePath);

            BufferedReader bufferedReader = new BufferedReader(new FileReader(openFile));
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(saveFile));

            String line = bufferedReader.readLine();

            System.out.println(line);

            StringTokenizer tokenizer = new StringTokenizer(line, ", ");
            System.out.println(tokenizer.countTokens());
            int length = tokenizer.countTokens();

            ArrayList<Integer> numbers = new ArrayList<Integer>();
            int[] intArray = new int[50];
            for (int i = 0; i < length; i++) {
                numbers.add(Integer.parseInt(tokenizer.nextToken()));
                intArray[i] = numbers.get(i);
            }
            System.out.println("before " + numbers);
            sort.insertionSort(numbers, numbers.size());
            sort.insertionSort(intArray, numbers.size());

            System.out.println("asdfd"+numbers);

            System.out.println("merge sort");
            System.out.println("after  " + sort.mergeSort(numbers, numbers.size()));

        } catch (FileNotFoundException e) {
            System.out.println("file");
        } catch (IOException io) {
            System.out.println("io");
        }
    }


}
