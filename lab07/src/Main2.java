import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        long x;
        long y;
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n Input X: ");
        x = scanner.nextLong();
        System.out.print("\n Input Y: ");
        y = scanner.nextLong();

        Karatsuba karatsuba = new Karatsuba();
        long answer = karatsuba.mul(x, y);
        System.out.println("ANSWER: " + answer);
    }
}

class Karatsuba {
    long mul(long x, long y) {
        int threshold = 3;
        int size_x = getSize(x);
        int size_y = getSize(y);
        int size = size_x > size_y ? size_x : size_y;

        if (size <= threshold) {
            return x * y;
        }
        int decimal = 1;
        for (int i = 0; i < (size + 1) / 2; i++) {
            decimal = decimal * 10;
        }

        long x1 = x / decimal;
        long x0 = x % decimal;
        long y1 = y / decimal;
        long y0 = y % decimal;

        long z2 = mul(x1, y1);
        long z0 = mul(x0, y0);
        long z1 = mul((x1 + x0), (y1 + y0)) - z2 - z0;

        return z2 * decimal * decimal + z1 * decimal + z0;
    }

    private int getSize(long num) {
        int size = 1;
        long left = num;

        if (num == 0) {
            return 0;
        }
        while ((left = left / 10) != 0) {
            size++;
        }
        return size;
    }
}
