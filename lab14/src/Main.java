import java.util.*;

public class Main {
    public static void main(String[] args) {
        int c[][] = {
                {0, 12, 0, 10, 0, 0},
                {0, 0, 8, 5, 0, 0},
                {0, 0, 0, 0, 0, 15},
                {0, 0, 5, 0, 15, 0},
                {0, 0, 8, 0, 0, 10},
                {0, 0, 0, 0, 0, 0}
        };
        int c2[][] = {
                {0, 12, 0, 3, 0, 0},
                {0, 0, 10, 0, 0, 0},
                {0, 0, 0, 0, 3, 15},
                {0, 11, 0, 0, 5, 0},
                {0, 0, 0, 0, 0, 17},
                {0, 0, 0, 0, 0, 0}
        };
        int c3[][] = {
                {0, 20, 10, 0},
                {0, 0, 30, 10},
                {0, 0, 0, 30},
                {0, 0, 0, 0}
        };
        FordFulkerson ff = new FordFulkerson(c.length, c);
        int total = ff.networkFlow(0, 5);
        //System.out.println("Total Flow: " + total);
        ff.print_table();
    }
}

class FordFulkerson {
    private int flow[][];
    private int capacity[][];
    private int residual[][];
    private int MAX_V;
    private ArrayList<String> pathList;
    private int totalAmount;


    FordFulkerson(int max, int c[][]) {
        this.flow = new int[max][max];
        this.capacity = new int[max][max];
        this.residual = new int[max][max];
        this.MAX_V = max;
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                capacity[i][j] = c[i][j];
                residual[i][j] = c[i][j];
            }
        }
        List<Integer> list = new ArrayList<>();
        list.add(1);
        this.pathList = new ArrayList<>();
    }

    int networkFlow(int source, int sink) {
        int totalFlow = 0;
        while (true) {
            int parent[] = new int[MAX_V];
            for (int i = 0; i < parent.length; i++) {
                parent[i] = -1;
            }
            Queue<Integer> q = new LinkedList<>();
            parent[source] = source;
            q.offer(source);
            while ((!q.isEmpty()) && (parent[sink] == -1)) {
                int here = (int) q.peek();
                q.poll();
                for (int there = 0; there < MAX_V; there++) {
                    if (capacity[here][there] - flow[here][there] > 0 && (parent[there] == -1)) {
                        q.offer(there);
                        parent[there] = here;
                    }
                }
            }
            if (parent[sink] == -1) break;
            int amount = Integer.MAX_VALUE;
            for (int p = sink; p != source; p = parent[p]) {
                amount = Math.min(capacity[parent[p]][p] - flow[parent[p]][p], amount);
            }
            for (int p = sink; p != source; p = parent[p]) {
                flow[parent[p]][p] += amount;

                residual[parent[p]][p] -= amount;
                residual[p][parent[p]] += amount;

            }
            String path = "";
            for (int p = sink; p != source; p = parent[p]) {
                path = "-" + p + path;
            }
            path = "경로: " + source + path;
            path = path + " Amount: " + amount + "";
            pathList.add(path);
            totalFlow += amount;
        }
        totalAmount = totalFlow;
        return totalFlow;
    }

    void print_table() {
        System.out.println("\nCapacity");
        for (int i = 0; i < capacity.length; i++) {
            for (int j = 0; j < capacity[0].length; j++) {
                System.out.printf("%4d", capacity[i][j]);
            }
            System.out.println("");
        }

        System.out.println("\nFlow");
        for (int i = 0; i < flow.length; i++) {
            for (int j = 0; j < flow[0].length; j++) {
                System.out.printf("%4d", flow[i][j]);
            }
            System.out.println("");
        }
        System.out.println("\nResidual");
        for (int i = 0; i < residual.length; i++) {
            for (int j = 0; j < residual[0].length; j++) {
                System.out.printf("%4d", residual[i][j]);
            }
            System.out.println("");
        }

        System.out.println("");

        for (String path :
                pathList) {
            System.out.println(path);
        }
        System.out.println("최대 용량: " + totalAmount);
    }
}