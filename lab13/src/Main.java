import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = null;
        ArrayList<Matrix> A = new ArrayList<>();
        A.add(new Matrix(-1, -1));// 더미값
        // 입력 루프
        /*
        while ((line = scanner.nextLine()) != null) {
            if (line.equals("0")) {
                break;
            }
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            int row = Integer.parseInt(tokenizer.nextToken());
            int column = Integer.parseInt(tokenizer.nextToken());

            A.add(new Matrix(row, column));
            System.out.println(row + " " + column);
        }
        */
        A.add(new Matrix(30, 35));
        A.add(new Matrix(35, 15));
        A.add(new Matrix(15, 5));
        A.add(new Matrix(5, 10));
        A.add(new Matrix(10, 20));
        A.add(new Matrix(20, 25));

        /*
        A.add(new Matrix(5, 2));
        A.add(new Matrix(2, 3));
        A.add(new Matrix(3, 4));
        A.add(new Matrix(4, 6));
        A.add(new Matrix(6, 7));
        A.add(new Matrix(7, 8));
*/
        MatrixChain matrixChain = new MatrixChain(A);

        matrixChain.make_table();
        matrixChain.print_table();

        matrixChain.print_optimal(1,6);

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

            }
        }
    }

    void make_table() {
        int p[] = p = new int[A.size()];
        p[0] = A.get(1).getRow();
        for (int i = 1; i < p.length; i++) {
            p[i] = A.get(i).getColumn();
        }
        matrix_chain_order(p);
    }

    void matrix_chain_order(int p[]) {
        int n = p.length - 1;
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    void print_table() {
        for (int i = 1; i < m.length; i++) {
            for (int j = 1; j < m[0].length; j++) {
                System.out.printf("%6d", m[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
        for (int i = 1; i < s.length; i++) {
            for (int j = 1; j < s[0].length; j++) {
                System.out.printf("%6d", s[i][j]);
            }
            System.out.println("");
        }
    }
    void print_optimal(int i, int j){
        System.out.println("\nOptimal Solution: "+m[i][j]);
        System.out.println("\nOptimal Parens: ");
        print_optimal_parens(i, j);
    }
    void print_optimal_parens(int i, int j) {
        if (i == j) {
            System.out.print("A" + i+" ");
        } else {
            System.out.print("( ");
            print_optimal_parens(i, s[i][j]);
            print_optimal_parens(s[i][j]+1, j);
            System.out.print(") ");
        }
    }
}