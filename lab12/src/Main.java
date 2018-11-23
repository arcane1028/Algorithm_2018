public class Main {
    public static void main(String[] args) {
        String x = "acagaagta"; // 9자리
        String y = "actgagttaa"; // 10자리

        // x = "gact";
        // y = "gaacg";

        Sequence sequence = new Sequence(x, y);
        sequence.fill_table();
        sequence.print_a();

        sequence.find_optimal_string();
    }
}

class Sequence {
    private int[][] a;
    private int lx; // 10
    private int ly; // 11
    private String x;
    private String y;
    int q = -2;

    public Sequence(String x, String y) {
        this.x = x;
        this.y = y;
        this.lx = x.length() + 1;
        this.ly = y.length() + 1;
        this.a = new int[lx][ly];
    }

    void fill_table() {
        for (int i = 0; i < lx; i++) {
            a[i][0] = i * q;
        }
        for (int i = 0; i < ly; i++) {
            a[0][i] = i * q;
        }
        for (int i = 1; i < lx; i++) {
            for (int j = 1; j < ly; j++) {
                a[i][j] = max3(a[i][j - 1] + q, a[i - 1][j - 1] + p(i, j), a[i - 1][j] + q);
            }
        }

    }

    int p(int i, int j) {
        if (x.charAt(i - 1) == y.charAt(j - 1)) {
            return 1;
        } else {
            return -1;
        }
    }

    int max3(int n1, int n2, int n3) {
        int n4 = Math.max(n1, n2);
        return Math.max(n3, n4);
    }

    void print_a() {
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                System.out.printf("%3d", a[i][j]);
            }
            System.out.println("");
        }
    }

    void find_optimal_string() {
        String x_opt = "";
        String y_opt = "";
        find_optimal(lx - 1, ly - 1, x_opt, y_opt);
    }

    void find_optimal(int xi, int yi, String x_opt, String y_opt) {
        if (xi <= 0 && yi <= 0) {
            System.out.println("");
            System.out.println(y_opt);
            System.out.println(x_opt);
            return;
        }
        String x_tmp = x_opt;
        String y_tmp = y_opt;
        if (a[xi][yi] == a[xi - 1][yi - 1] + p(xi, yi)) {
            x_tmp = x.charAt(xi - 1) + x_tmp;
            y_tmp = y.charAt(yi - 1) + y_tmp;
            find_optimal(xi - 1, yi - 1, x_tmp, y_tmp);
        }
        x_tmp = x_opt;
        y_tmp = y_opt;
        if (a[xi][yi] == a[xi][yi - 1] + q) {
            x_tmp = '_' + x_tmp;
            y_tmp = y.charAt(yi - 1) + y_tmp;
            find_optimal(xi, yi - 1, x_tmp, y_tmp);
        }
        x_tmp = x_opt;
        y_tmp = y_opt;
        if (a[xi][yi] == a[xi - 1][yi] + q) {
            x_tmp = x.charAt(xi - 1) + x_tmp;
            y_tmp = '_' + y_tmp;
            find_optimal(xi - 1, yi, x_tmp, y_tmp);
        }
    }
}