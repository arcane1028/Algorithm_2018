import java.util.ArrayList;

public class MinHeapTest {
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap(new ArrayList<>());

        minHeap.insert(new Node(5, 5));
        minHeap.insert(new Node(4, 4));
        minHeap.insert(new Node(3, 3));
        minHeap.insert(new Node(2, 2));
        minHeap.insert(new Node(1, 1));

        System.out.println(minHeap.extract_min());

    }
}

class MinHeap {
    ArrayList<Node> nodes;

    public MinHeap(ArrayList<Node> nodes) {
        this.nodes = nodes;
        nodes.add(new Node(-1, -1));
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

    public void decrease_key(int node, int k) {
        int index = findIndex(node);
        if (index < 0) {
            System.out.println("increase key find error");
            return;
        }
        Node temp = nodes.get(index);
        nodes.set(index, new Node(temp.node, k));
        lineHeapify(index); // heapify 실행
    }


    //특정 노드 위치 리턴
    private int findIndex(int node) {
        for (int i = 1; i < nodes.size(); i++) {
            Node temp = nodes.get(i);
            if (temp.node == node) {
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

    @Override
    public String toString() {
        String s ="";
        for (int i = 1; i < nodes.size(); i++) {
            s=s+"Q{" + (i-1) + "]  d[" + int2char(nodes.get(i).node) +"] = "+nodes.get(i).distance+"\n";
        }
        return s;
    }
    char int2char(int a) {
        char c = (char) (a + 65);
        return c;
    }
}

class Node {
    int node;
    int distance;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}