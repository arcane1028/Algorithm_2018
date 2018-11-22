import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        try {
            String openPath = "data11.txt"; // 파일이름
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(new File(openPath)),
                            StandardCharsets.UTF_8
                    )
            );
            String line = "";
            StringTokenizer tokenizer;
            ArrayList<Item> input = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                tokenizer = new StringTokenizer(line, ", ");
                input.add(new Item(
                        Integer.parseInt(tokenizer.nextToken()),
                        Integer.parseInt(tokenizer.nextToken()),
                        Integer.parseInt(tokenizer.nextToken()))
                );
            }
            bufferedReader.close();
            int max_weight = 0;

            Scanner scanner = new Scanner(System.in);
            System.out.print("배낭 사이즈를 입력하세요(0 ~ 50): ");
            max_weight = scanner.nextInt();

            Knapsack knapsack = new Knapsack(input, max_weight);
            // 배열(Table)에 OPT 값을 채우는 함수
            knapsack.make_OPT_table();
            // 배열(Table)을 출력하는 함수
            knapsack.print_OPT_table();
            // 최대 value 출력함수
            System.out.println("Max: " + knapsack.get_MAX());
            // 최대의 item 구성 출력
            knapsack.print_MAX_items();
            // 특정 위치의 item 구성 출력
            // knapsack.print_OPT_items(5,10);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// item을 의미하는 객체
class Item {
    private int item_num;
    private int value;
    private int weight;

    Item(int item_num, int value, int weight) {
        this.item_num = item_num;
        this.value = value;
        this.weight = weight;
    }

    int getItem_num() {
        return item_num;
    }

    int getValue() {
        return value;
    }

    int getWeight() {
        return weight;
    }
}
// knapsack 함수
class Knapsack {
    private ArrayList<Item> items;
    private int[][] table;
    private int max_weight;

    Knapsack(ArrayList<Item> items, int max_weight) {

        this.items = new ArrayList<>(items);
        this.table = new int[items.size() + 1][max_weight + 1];
        this.max_weight = max_weight;
    }
    // 최대 value를 구하는 함수
    int OPT(int i, int w) {
        if (i <= 0) {
            return 0;
        }
        int wi = items.get(i - 1).getWeight();
        if (wi > w) {
            return OPT(i - 1, w);
        }
        int vi = items.get(i - 1).getValue();
        return Math.max(OPT(i - 1, w), vi + OPT(i - 1, w - wi));
    }
    // 배열을 생성하는 함수
    void make_OPT_table() {
        for (int i = 0; i <= items.size(); i++) {
            for (int w = 0; w <= max_weight; w++) {
                table[i][w] = OPT(i, w);
            }
        }
    }
    // 배열을 출력하는 함수
    void print_OPT_table() {
        for (int i = 0; i <= items.size(); i++) {
            for (int w = 0; w <= max_weight; w++) {
                // 보기 좋게하기위해 세자리씩 출력 데이터 변경시 변경
                System.out.printf("%3d ", table[i][w]);
            }
            System.out.println("");
        }
    }
    // 최대 value를 반환하는 함수
    int get_MAX() {
        return table[items.size()][max_weight];
    }
    // 구성 item을 확인하는 함수
    void get_OPT_items(int i, int w, ArrayList<Item> temp_items) {
        if (w <= 0 || i <= 0) {
            return;
        }
        // item 기준, 특정 위치의 value가 이전 value와 같은 경우 현재 item은 포함 되지 않음
        if (table[i - 1][w] == table[i][w]) {
            get_OPT_items(i - 1, w, temp_items);
        } else {
            // 다른 경우 현재 item 포함된 현재의 item만큼 감소시키고 다시 탐색
            temp_items.add(items.get(i - 1));
            int wi = items.get(i - 1).getWeight();
            get_OPT_items(i - 1, w - wi, temp_items);
        }
    }
    // 최대 value의 구성 item을 출력하는 함수
    void print_MAX_items() {
        print_OPT_items(items.size(), max_weight);
    }
    // 특정 value의 구성 item을 출력하는 함수
    void print_OPT_items(int i, int w){
        ArrayList<Item> temp_OPT_items = new ArrayList<>();
        get_OPT_items(i, w, temp_OPT_items);
        System.out.print("Item: ");
        for (int j = temp_OPT_items.size() - 1; j >= 0; j--) {
            System.out.print(temp_OPT_items.get(j).getItem_num() + " ");
        }
        System.out.println("");
    }
}
