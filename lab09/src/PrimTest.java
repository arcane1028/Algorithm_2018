import java.util.ArrayList;

public class PrimTest {
    public static void main(String[] args) {
        int I = Integer.MAX_VALUE;
        int w[][] = {
                {0, 4, I, I, I, I, I, 8, I},
                {4, 0, 8, I, I, I, I, 11, I},
                {I, 8, 0, 7, I, 4, I, I, 2},
                {I, I, 7, 0, 9, 14, I, I, I},
                {I, I, I, 9, 0, 10, I, I, I},
                {I, I, 4, 14, 10, 0, 2, I, I},
                {I, I, I, I, I, 2, 0, 1, 6},
                {8, 11, I, I, I, I, 1, 0, 7},
                {I, I, 2, I, I, I, 6, 7, 0}
        };
        Prim p = new Prim();
        p.getMst(w);


    }

}

class Prim {

    void getMst(int w[][]) {
        int vSize = w.length;
        int[] key = new int[vSize];
        for (int i = 0; i < vSize; i++) {
            key[i] = Integer.MAX_VALUE;
        }
        key[0] = 0;
        MinHeap Q = new MinHeap(new ArrayList<>());
        for (int i = 0; i < vSize; i++) {
            Q.insert(new Node(int2char(i), key[i]));
        }

        char[] pi = new char[vSize];

        while (!Q.isEmpty()) {
            Node u = Q.extract_min();
            for (int i = 0; i < vSize; i++) {
                // 무한대 값이 아니면 인접한 점
                // Q에 있는지 검사
                // 가중치 점사
                if ((w[char2int(u.v)][i] != Integer.MAX_VALUE) &&
                        Q.hasChar(int2char(i)) &&
                        (w[char2int(u.v)][i] < key[i])){
                    key[i] = w[char2int(u.v)][i];
                    pi[i] = u.v;
                    Q.decrease_key(int2char(i), key[i]);
                }
            }
            System.out.println("w("+pi[char2int(u.v)]+", "+u.v+") = "+key[char2int(u.v)]);
        }
        int sum = 0;
        for (int i = 0; i <vSize ; i++) {
            sum += key[i];
        }
        System.out.println("\nMST = "+sum);

    }

    int char2int(char c) {
        int i = (int) (c - 97);
        return i;
    }

    char int2char(int a) {
        char c = (char) (a + 97);
        return c;
    }

}

class MinHeap {
    ArrayList<Node> nodes;

    public MinHeap(ArrayList<Node> nodes) {
        this.nodes = nodes;
        nodes.add(new Node('\0', -1));
    }

    boolean hasChar(char c) {
        for (Node n : nodes) {
            if (n.v == c) {
                return true;
            }
        }
        return false;
    }

    public void buildMinHeap() {
        int length = nodes.size();
        for (int i = length / 2; i > 0; i--) {
            heapify(i);
        }
    }

    private void heapify(int i) {
        int l = leftChild(i);
        int r = rightChild(i);
        int heap_size = nodes.size() - 1; // 0번 제거
        int smallest;

        if (l <= heap_size && nodes.get(l).distance < nodes.get(i).distance) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= heap_size && nodes.get(r).distance < nodes.get(smallest).distance) {
            smallest = r;
        }
        if (smallest != i) {
            Node smallestNode = nodes.get(i);
            nodes.set(i, nodes.get(smallest));
            nodes.set(smallest, smallestNode);
            heapify(smallest);
        }
    }

    public void insert(Node x) {
        nodes.add(x);
        lineHeapify(lastNode());
    }

    private void lineHeapify(int i) {
        while (i > 0) {
            heapify(i);
            if (nodes.get(parent(i)).distance > nodes.get(i).distance) {
                i = parent(i);
            } else {
                break;
            }
        }
    }

    public boolean isEmpty() {
        if (nodes.size() == 1) {
            return true;
        }
        return false;
    }

    public Node min() {
        return nodes.get(1);
    }

    public Node extract_min() {

        if (nodes.size() == 1) {
            return null;
        } else if (nodes.size() == 2) {
            return nodes.remove(1);
        }
        Node min = nodes.get(1);
        nodes.set(1, nodes.remove(lastNode()));
        heapify(1);
        return min;
    }

    public void decrease_key(char v, int k) {
        int index = findIndex(v);
        if (index < 0) {
            System.out.println("increase key find error");
            return;
        }
        Node temp = nodes.get(index);
        nodes.set(index, new Node(temp.v, k));
        lineHeapify(index); // heapify 실행
    }


    //특정 노드 위치 리턴
    private int findIndex(char v) {
        for (int i = 1; i < nodes.size(); i++) {
            Node temp = nodes.get(i);
            if (temp.v == v) {
                return i;
            }
        }
        return -1;
    }

    // 마지막 원소 위치
    private int lastNode() {
        return nodes.size() - 1;
    }

    // 왼쪽 자식 위치 함수
    private int leftChild(int i) {
        return 2 * i;
    }

    // 오른쪽 자식 위치 함수
    private int rightChild(int i) {
        return 2 * i + 1;
    }

    // 부모 위치 함수
    private int parent(int i) {
        return i / 2;
    }

    char int2char(int a) {
        char c = (char) (a + 65);
        return c;
    }
}

class Node {
    char v;
    int distance;

    public Node(char v, int distance) {
        this.v = v;
        this.distance = distance;
    }
}