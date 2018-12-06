import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = null;
        ArrayList<Matrix> A = new ArrayList<>();

        // 입력 루프
        int i = 1;
        System.out.printf("%d 번째 행렬을 입력하세요(숫자 숫자, 0 입력시 종료): ", i);
        while ((line = scanner.nextLine()) != null) {
            if (line.equals("0")) {
                break;
            }
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            int row = Integer.parseInt(tokenizer.nextToken());
            int column = Integer.parseInt(tokenizer.nextToken());

            A.add(new Matrix(row, column));
            i++;
            System.out.printf("%d 번째 행렬을 입력하세요(숫자 숫자, 0 입력시 종료): ", i);
        }
        System.out.println("");

                /*
        A.add(new Matrix(30, 35));
        A.add(new Matrix(35, 15));
        A.add(new Matrix(15, 5));
        A.add(new Matrix(5, 10));
        A.add(new Matrix(10, 20));
        A.add(new Matrix(20, 25)); //size 6 index 5

        A.add(new Matrix(5, 2));
        A.add(new Matrix(2, 3));
        A.add(new Matrix(3, 4));
        A.add(new Matrix(4, 6));
        A.add(new Matrix(6, 7));
        A.add(new Matrix(7, 8));
        */
        MatrixChain matrixChain = new MatrixChain(A);

        matrixChain.make_table(); //테이블 생성
        matrixChain.print_table(); // 출력

        matrixChain.print_optimal(0, A.size()-1); //최적값 출력

    }
}

class Matrix {
    private int row;
    private int column;

    Matrix(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}

class MatrixChain {
    int m[][];
    int s[][];
    ArrayList<Matrix> A;

    public MatrixChain(ArrayList<Matrix> a) {
        A = new ArrayList<>(a);
        m = new int[A.size()][A.size()];
        s = new int[A.size()][A.size()];
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                m[i][j] = -1;
                s[i][j] = -1;
            }
        }
    }

    void make_table() {
        // p 배열 생성
        int p[] = p = new int[A.size() + 1];
        p[0] = A.get(0).getRow();
        for (int i = 1; i < p.length; i++) {
            p[i] = A.get(i - 1).getColumn();
        }
        matrix_chain_order(p);
    }
    // 테이블 생성
    private void matrix_chain_order(int p[]) {
        int n = p.length - 1;
        // 하나만 있는 경우 0으로 초기화
        for (int i = 0; i < n; i++) {
            m[i][i] = 0;
        }
        // 곱셉을 하는 개수 l
        for (int l = 2; l <= n; l++) {
            for (int i = 0; i < n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                // 행렬곱 내에서 곱셉을 수행할 위치
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1 + 1] * p[k + 1] * p[j + 1];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    void print_table() {
        for (int i = 0; i < A.size(); i++) {
            System.out.printf("A(%d)   = %4d X %4d\n",(i+1),A.get(i).getRow(),A.get(i).getColumn());
        }
        System.out.println("");

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.printf("%6d", m[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[0].length; j++) {
                System.out.printf("%6d", s[i][j]);
            }
            System.out.println("");
        }
    }

    void print_optimal(int i, int j) {
        System.out.println("\nOptimal Solution: " + m[i][j]);
        System.out.print("\nOptimal Parens: ");
        print_optimal_parens(i, j);
    }

    private void print_optimal_parens(int i, int j) {
        if (i == j) {
            System.out.print("A" + (i + 1) + " ");
        } else {
            System.out.print("( ");
            print_optimal_parens(i, s[i][j]);
            print_optimal_parens(s[i][j] + 1, j);
            System.out.print(") ");
        }
    }
}