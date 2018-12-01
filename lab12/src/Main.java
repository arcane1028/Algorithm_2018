public class Main {
    public static void main(String[] args) {
        String x = "actgagttaa"; // 10자리
        String y = "acagaagta"; // 9자리

        //x = "gaacg";
        //y = "gact";

        System.out.println("X : " + x);
        System.out.println("Y : " + y);
        System.out.println("");

        Sequence sequence = new Sequence(x, y); //객체 생성
        sequence.fill_table(); // 테이블 생성
        sequence.print_a(); // 테이블 출력

        sequence.find_optimal_string(); // 최적 문자열들 출력
    }
}

class Sequence {
    private int[][] a;
    private int lx;
    private int ly;
    private String x;
    private String y;
    int q = -2;

    public Sequence(String x, String y) {
        // 테이블 모양 때문에 반대로 입력
        this.x = y;
        this.y = x;
        this.lx = y.length() + 1;
        this.ly = x.length() + 1;
        this.a = new int[lx][ly];
    }

    // 테이블 만드는 함수
    void fill_table() {
        // 초기화
        for (int i = 0; i < lx; i++) {
            a[i][0] = i * q;
        }
        for (int i = 0; i < ly; i++) {
            a[0][i] = i * q;
        }
        // 조건에 따라 채움
        for (int i = 1; i < lx; i++) {
            for (int j = 1; j < ly; j++) {
                a[i][j] = max3(a[i][j - 1] + q, a[i - 1][j - 1] + p(i, j), a[i - 1][j] + q);
            }
        }
    }

    // 같으면 1 틀리면 -1
    int p(int i, int j) {
        if (x.charAt(i - 1) == y.charAt(j - 1)) {
            return 1;
        } else {
            return -1;
        }
    }

    // 3개중 최대값을 반환하는 함수
    int max3(int n1, int n2, int n3) {
        int n4 = Math.max(n1, n2);
        return Math.max(n3, n4);
    }

    // 테이블 출력하는 함수
    void print_a() {
        for (int i = 0; i < lx; i++) {
            for (int j = 0; j < ly; j++) {
                System.out.printf("%4d", a[i][j]);
            }
            System.out.println("");
        }
    }

    // 최적 문자열 조합 출력하는 함수
    void find_optimal_string() {
        String x_opt = "";
        String y_opt = "";
        // 테이블의 맨 끝에서부터 탐색 시작
        find_optimal(lx - 1, ly - 1, x_opt, y_opt);
    }

    // 최적 문자열 찾는 함수, 테이블 맨 끝에서 시작 처음에 도달하면 종료
    void find_optimal(int xi, int yi, String x_opt, String y_opt) {
        // 범위를 감지하는 변수
        boolean x_is_zero = false;
        boolean y_is_zero = false;
        if (xi <= 0) {
            x_is_zero = true;
        }
        if (yi <= 0) {
            y_is_zero = true;
        }
        // 처음에 도달하면 문자열 출력후 종료
        if (x_is_zero && y_is_zero) {
            System.out.println("");
            System.out.println(y_opt);
            System.out.println(x_opt);
            return;
        }
        // 현재의 값이 온 방향을 거꾸로 올라간다.
        // 하나가 아니면 나머지도 따라 올라가서 출력한다.
        String x_tmp = x_opt;
        String y_tmp = y_opt;
        // 대각선 방향 따라 올라간다.
        // 이경우 문자열이 같거나 다른 경우이므로 문자열에 추가
        if (!(x_is_zero || y_is_zero) && (a[xi][yi] == a[xi - 1][yi - 1] + p(xi, yi))) {
            x_tmp = x.charAt(xi - 1) + x_tmp;
            y_tmp = y.charAt(yi - 1) + y_tmp;
            find_optimal(xi - 1, yi - 1, x_tmp, y_tmp);
        }
        // 다른 경로에도 최적문자열이 있을수 있으므로 이전 문자열로 복구하고 탐색
        x_tmp = x_opt;
        y_tmp = y_opt;
        // x 문자열이 짧은 경우 빈공간이 입력되고 y에는 문자 추가
        if ((!y_is_zero) && (a[xi][yi] == a[xi][yi - 1] + q)) {
            x_tmp = '_' + x_tmp;
            y_tmp = y.charAt(yi - 1) + y_tmp;
            find_optimal(xi, yi - 1, x_tmp, y_tmp);
        }
        x_tmp = x_opt;
        y_tmp = y_opt;
        // y문자열이 짧은 경우 빈공간이 입력되고 x에는 문자 추가
        if ((!x_is_zero) && (a[xi][yi] == a[xi - 1][yi] + q)) {
            x_tmp = x.charAt(xi - 1) + x_tmp;
            y_tmp = '_' + y_tmp;
            find_optimal(xi - 1, yi, x_tmp, y_tmp);
        }
    }
}