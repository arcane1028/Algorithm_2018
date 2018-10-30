import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int INF = Integer.MAX_VALUE;
        int array[][] = {
                {0, 10, 3, INF, INF},
                {INF, 0, 1, 2, INF},
                {INF, 4, 0, 8, 2},
                {INF, INF, INF, 0, 7},
                {INF, INF, INF, 9, 0}
        };
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.path(array);

    }
}

class Dijkstra {
    void path(int array[][]) {
        int d[] = new int[array.length];
        //초기화
        for (int i = 0; i < d.length; i++) {
            if (i == 0) {
                d[i] = 0;
            } else {
                d[i] = Integer.MAX_VALUE;
            }
        }
        //Q와 S 생성
        ArrayList<Node> S = new ArrayList<>();
        MinHeap Q = new MinHeap();
        ArrayList<Node> nodes = new ArrayList<>();
        nodes.add(null);
        for (int i = 0; i < d.length; i++) {
            nodes.add(new Node(i, d[i]));
        }
        Q.buildMinHeap(nodes);

        while (nodes.size() != 1) {
            Node node = Q.extract_min(nodes);
            S.add(node);

            for (int i = 0; i < array.length; i++) {
                if ((d[node.node] + array[node.node][i]) < d[i]) {
                    d[i] = d[node.node] + array[node.node][i];
                    Q.decrease_key(nodes, new Node(node.node, Integer.MAX_VALUE), d[i]);
                }
            }
            //System.out.println(S);
        }

    }
}

class MinHeap {
    public void buildMinHeap(ArrayList<Node> nodes) {
        int length = nodes.size();
        for (int i = length / 2; i > 0; i--) {
            heapify(nodes, i);
        }
    }

    private void heapify(ArrayList<Node> nodes, int i) {
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
            heapify(nodes, smallest);
        }
    }

    public void insert(ArrayList<Node> nodes, Node x) {
        nodes.add(x);
        lineHeapify(nodes, lastNode(nodes));
    }

    private void lineHeapify(ArrayList<Node> nodes, int i) {
        while (i > 0) {
            heapify(nodes, i);
            if (nodes.get(parent(i)).distance < nodes.get(i).distance) {
                i = parent(i);
            } else {
                break;
            }
        }
    }

    public Node min(ArrayList<Node> nodes) {
        return nodes.get(1);
    }

    public Node extract_min(ArrayList<Node> nodes) {
        Node min = nodes.get(1);
        nodes.set(1, nodes.remove(lastNode(nodes)));
        heapify(nodes, 1);
        return min;
    }

    public void decrease_key(ArrayList<Node> nodes, Node x, int k) {
        int index = findIndex(nodes, x);
        if (index < 0) {
            System.out.println("increase key find error");
            return;
        }
        Node temp = nodes.get(index);

        nodes.set(index, new Node(temp.node, k));

        lineHeapify(nodes, index); // heapify 실행
    }

    // 특정 노드 삭제 함수
    public Node h_delete(ArrayList<Node> nodes, Node x) {
        int index = findIndex(nodes, x);

        if (index < 0) {
            System.out.println("h_delete find error");
            return null;
        }
        Node deleted = nodes.get(index);
        nodes.set(index, nodes.remove(lastNode(nodes))); // 마지막 노드 삭제된 위치로 이동

        //buildMaxHeap(nodes);
        lineHeapify(nodes, index); // heapify 실행
        return deleted;
    }

    //특정 노드 위치 리턴
    private int findIndex(ArrayList<Node> nodes, Node x) {
        for (int i = 1; i < nodes.size(); i++) {
            Node temp = nodes.get(i);
            if (temp.distance == x.distance && temp.node == (x.node)) {
                return i;
            }
        }
        return -1;
    }

    // 마지막 원소 위치
    private int lastNode(ArrayList<Node> nodes) {
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
}

class Node {
    int node;
    int distance;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }
}