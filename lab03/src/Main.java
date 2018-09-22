import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

  public static void main(String[] args) {
    try {
      String openPath = "data03.txt";
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(
              new FileInputStream(new File(openPath)),
              "EUC-KR"
          )
      );
      BufferedReader bufferedReader2 = new BufferedReader(
          new FileReader(new File("data03_2.txt"))
      );
      String line;
      ArrayList<Node> heap = new ArrayList<>();
      heap.add(new Node(-1, "NULL")); // 계산 편의 값

      for (int i = 0; (line = bufferedReader.readLine()) != null; i++) {
        StringTokenizer tokenizer = new StringTokenizer(line, ", ");
        int key = Integer.parseInt(tokenizer.nextToken());

        StringBuilder stringBuilder = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
          stringBuilder.append(tokenizer.nextToken());
          stringBuilder.append(" ");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        String subject = stringBuilder.toString();

        heap.add(new Node(key, subject));

        //System.out.println(line);
      }

      MaxHeap maxHeap = new MaxHeap();
      maxHeap.buildMaxHeap(heap);
      Scanner scanner = new Scanner(System.in);
      scanner.useDelimiter("\n");

      System.out.println("현재 우선 순위 큐에 저장되어 있는 작업대기 목록은 다음과 같습니다.***");
      System.out.println("[index] key, value");
      System.out.println("-------------------");
      maxHeap.print(heap);
      maxHeap.printTree(heap);
      System.out.println("-------------------");
      System.out.println("1.작업추가\t2.최대값\t3.최대 우선순위 작업 처리");
      System.out.println("4.원소 키 값 증가\t 5.작업 제거\t 6.종료");
      System.out.println("-------------------");
      System.out.print("명령 번호 입력 : ");

      String commandNum;
      boolean isExit = false;
      while ((commandNum = scanner.next()) != null) {
        switch (Integer.parseInt(commandNum)) {
          case 1:
            System.out.print("Key : ");
            int key = scanner.nextInt();
            System.out.print("Value : ");
            String value = scanner.next();
            maxHeap.insert(heap, new Node(key, value));
            break;
          case 2:
            System.out.println("최대값 : " + maxHeap.max(heap));
            break;
          case 3:
            System.out.println("작업 처리 :" + maxHeap.extract_max(heap));
            break;
          case 4:
            System.out.print("Key : ");
            int key2 = scanner.nextInt();
            System.out.print("Value : ");
            String value2 = scanner.next();
            System.out.print("Increase Key : ");
            int increaseKey = scanner.nextInt();
            maxHeap.increase_key(heap, new Node(key2, value2), increaseKey);
            break;
          case 5:
            System.out.print("Key : ");
            int key3 = scanner.nextInt();
            System.out.print("Value : ");
            String value3 = scanner.next();
            maxHeap.h_delete(heap, new Node(key3, value3));
            break;
          case 6:
            isExit = true;
            System.out.println("종료");
            break;
          default:
            System.out.println("잘못된 숫자 입력");
            break;
        }
        if (isExit) {
          break;
        }
        System.out.println("-------------------");
        maxHeap.print(heap);
        System.out.println("-------------------");
        System.out.println("1.작업추가\t2.최대값\t3.최대 우선순위 작업 처리");
        System.out.println("4.원소 키 값 증가\t 5.작업 제거\t 6.종료");
        System.out.println("-------------------");
        System.out.print("명령 번호 입력 : ");
      }
      bufferedReader.close();
      bufferedReader2.close();

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

class Node {

  private int key;
  private String subject;

  Node(int key, String subject) {
    this.key = key;
    this.subject = subject;
  }

  public int getKey() {
    return key;
  }

  public String getSubject() {
    return subject;
  }

  @Override
  public String toString() {
    return key + ", " + subject;
  }
}

class MaxHeap {

  public void buildMaxHeap(ArrayList<Node> nodes) {
    int length = nodes.size();
    for (int i = length / 2; i > 0; i--) {
      heapify(nodes, i);
    }
  }

  private void heapify(ArrayList<Node> nodes, int i) {
    int l = leftChild(i);
    int r = rightChild(i);
    int heap_size = nodes.size() - 1; // 0번 제거
    int largest;

    if (l <= heap_size && nodes.get(l).getKey() > nodes.get(i).getKey()) {
      largest = l;
    } else {
      largest = i;
    }
    if (r <= heap_size && nodes.get(r).getKey() > nodes.get(largest).getKey()) {
      largest = r;
    }
    if (largest != i) {
      Node largestNode = nodes.get(i);
      nodes.set(i, nodes.get(largest));
      nodes.set(largest, largestNode);
      heapify(nodes, largest);
    }
  }

  public void insert(ArrayList<Node> nodes, Node x) {
    nodes.add(x);

    //buildMaxHeap(nodes);
    lineHeapify(nodes, heapSize(nodes));
  }

  private void lineHeapify(ArrayList<Node> nodes, int i) {
    while (i > 0) {
      heapify(nodes, i);
      if (nodes.get(parent(i)).getKey() < nodes.get(i).getKey()) {
        i = parent(i);
      } else {
        break;
      }
    }
  }

  public Node max(ArrayList<Node> nodes) {
    return nodes.get(1);
  }

  public Node extract_max(ArrayList<Node> nodes) {
    Node max = nodes.get(1);
    nodes.set(1, nodes.remove(heapSize(nodes)));

    heapify(nodes, 1);

    return max;
  }

  public void increase_key(ArrayList<Node> nodes, Node x, int k) {
    int index = findIndex(nodes, x);
    if (index < 0) {
      System.out.println("increase key find error");
      return;
    }
    Node temp = nodes.get(index);

    if (k < temp.getKey()) {
      System.out.println("increase key k error");
      return;
    }
    nodes.set(index, new Node(k, temp.getSubject()));

    lineHeapify(nodes, index);
  }

  public Node h_delete(ArrayList<Node> nodes, Node x) {
    int index = findIndex(nodes, x);

    if (index < 0) {
      System.out.println("h_delete find error");
      return new Node(-1, "error");
    }
    Node deleted = nodes.get(index);
    nodes.set(index, nodes.remove(heapSize(nodes)));

    //buildMaxHeap(nodes);
    lineHeapify(nodes, index);
    return deleted;
  }

  private int findIndex(ArrayList<Node> nodes, Node x) {
    for (int i = 1; i < nodes.size(); i++) {
      Node temp = nodes.get(i);
      if (temp.getKey() == x.getKey() && temp.getSubject().equals(x.getSubject())) {
        return i;
      }
    }
    return -1;
  }

  private int heapSize(ArrayList<Node> nodes) {
    return nodes.size() - 1;
  }

  private int leftChild(int i) {
    return 2 * i;
  }

  private int rightChild(int i) {
    return 2 * i + 1;
  }

  private int parent(int i) {
    return i / 2;
  }

  public void print(ArrayList<Node> nodes) {
    for (int i = 1; i < nodes.size(); i++) {
      Node temp = nodes.get(i);
      //System.out.println("[" + i + "] " + temp.getKey() + ", " + temp.getSubject());
      System.out.printf("[%2d] %3d, %10s\n", i, temp.getKey(), temp.getSubject());
    }
  }

  public void printTree(ArrayList<Node> nodes) {
    int height = (int) (Math.log(nodes.size() - 1) / Math.log(2));

    for (int j = 0; j < height; j++) {
      //System.out.println(Math.pow(2, j) + " " + (Math.pow(2, j + 1) - 1));

      for (int k = 0; k < (int) (Math.pow(2, height - j) - 1) / 2; k++) {
        System.out.print("                           ");
      }
      for (int i = (int) (Math.pow(2, j)); i < Math.pow(2, j + 1); i++) {
        Node temp = nodes.get(i);
        System.out.printf("[%2d] %3d, %10s", i, temp.getKey(), temp.getSubject());
        for (int k = 0; k < (int) (Math.pow(2, height - j+1) - 1) / 2; k++) {
          System.out.print("                           ");
        }

      }
      System.out.println("");
      for (int i = 0; i <(int)(Math.pow(2, height)-1) ; i++) {
        System.out.print("---------------------------");
      }
      System.out.println();
    }
  }
}