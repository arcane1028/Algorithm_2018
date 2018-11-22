import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int INF = Integer.MAX_VALUE;
        int w[][] = {
                {0, 10, 3, INF, INF},
                {INF, 0, 1, 2, INF},
                {INF, 4, 0, 8, 2},
                {INF, INF, INF, 0, 7},
                {INF, INF, INF, 9, 0}
        };
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.path(w);

    }
}

class Dijkstra {
    void path(int w[][]) {
        int d[] = new int[w.length];
        //초기화 2
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.MAX_VALUE;

        }
        d[0] = 0;
        //Q와 S 생성 3
        ArrayList<Node> S = new ArrayList<>();
        MinHeap Q = new MinHeap(new ArrayList<>());

        for (int i = 0; i < d.length; i++) {
            Q.insert(new Node(i, d[i]));
        }
        int i = 0;
        System.out.println("========================");
        while (!Q.isEmpty()) {
            Node u = Q.extract_min(); //추출
            S.add(u); //삽입
            System.out.println("S{" + i + "]  d[" + int2char(S.get(i).node) +"] = "+S.get(i).distance);
            System.out.println("------------------------");
            System.out.println(Q);
            for (int v = 0; v < w.length; v++) {
                if ((d[u.node] + w[u.node][v]) < d[v] && w[u.node][v] != Integer.MAX_VALUE) {
                    d[v] = d[u.node] + w[u.node][v];
                    Q.decrease_key(v, d[v]);
                    System.out.println(Q);
                }
            }
            System.out.println("========================");
            i++;
        }
    }

    char int2char(int a) {
        char c = (char) (a + 65);
        return c;
    }

}

