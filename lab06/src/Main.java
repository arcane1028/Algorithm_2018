import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            String openPath = "data05.txt"; // 파일이름
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(new File(openPath)),
                            StandardCharsets.UTF_8
                    )
            );
            String line = "";
            ArrayList<Dot> dots = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ", ");
                double x = Double.parseDouble(tokenizer.nextToken());
                double y = Double.parseDouble(tokenizer.nextToken());

                dots.add(new Dot(x, y)); //한 좌표
            }
            Cpp cpp = new Cpp(); //Closest Pair of Points 객체
            System.out.println("Brute\n"+cpp.getDotBrute(dots)+"\n");

            Collections.sort(dots); // X축 기준 정렬

            System.out.println(dots.toString()+"\n");
            Pair pair = cpp.getDot(dots); // Closest pair 구함
            System.out.println("Divide and Conquer\n"+pair); //출력
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//점을 나타내는 객체
class Dot implements Comparable<Dot> {
    double x;
    double y;

    public Dot(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Dot(Dot dot) {
        this.x = dot.x;
        this.y = dot.y;
    }

    @Override
    public String toString() {
        return "{" + "x=" + x + ", y=" + y + "}\n";
    }

    // 정렬을 위해 구현
    @Override
    public int compareTo(Dot o) {
        if (this.x < o.x) {
            return -1;
        } else if (this.x > o.x) {
            return 1;
        }
        return 0;
    }
}
// 점 두개로 이루어진 한 쌍, 두점과 두점사이의 길이로 구성
class Pair {
    Dot dot1;
    Dot dot2;
    double distance;

    public Pair(Dot dot1, Dot dot2, double distance) {
        this.dot1 = dot1;
        this.dot2 = dot2;
        this.distance = distance;
    }

    public Pair(Pair pair) {
        this.dot1 = pair.dot1;
        this.dot2 = pair.dot2;
        this.distance = pair.distance;
    }

    @Override
    public String toString() {
        return "Pair{" + "dot1=" + dot1 + ", dot2=" + dot2 +
                ", distance=" + distance + '}';
    }
}
// Closest Pain 구하는 객체
class Cpp {
    // 모든점을 순회해 구하는 방법, 정답 확인을 위해 구현
    public Pair getDotBrute(ArrayList<Dot> dots) {
        double minDis = Double.MAX_VALUE;
        Pair minPair = null;
        for (int i = 0; i < dots.size(); i++) {
            for (int j = i + 1; j < dots.size(); j++) {
                Dot iDot = dots.get(i);
                Dot jDot = dots.get(j);
                double distance = getDistance(iDot, jDot);
                if (minDis > distance) {
                    minDis = distance;
                    minPair = new Pair(iDot, jDot, minDis);
                }
            }
        }

        return minPair;
    }
    // Divide and Conquer를 사용한 구현
    public Pair getDot(ArrayList<Dot> dots) {
        // 3개 이하일때 모든 점사이의 길이를 구해 최소인 점을 구한다.
        if (dots.size() <= 3) {
            double minDis = Double.MAX_VALUE;
            Dot minDot1 = null;
            Dot minDot2 = null;
            for (int i = 0; i < dots.size(); i++) {
                for (int j = i + 1; j < dots.size(); j++) {
                    double dis = getDistance(dots.get(i), dots.get(j));
                    if (dis < minDis) {
                        minDis = dis;
                        minDot1 = dots.get(i);
                        minDot2 = dots.get(j);
                    }
                }
            }
            return new Pair(minDot1, minDot2, minDis);
        } else {
            //3개보다 많으면 좌측과 우측에서 최소인 쌍을 구한다.
            Pair left_min = getDot(new ArrayList<>(dots.subList(0, dots.size() / 2)));
            Pair right_min = getDot(new ArrayList<>(dots.subList(dots.size() / 2, dots.size())));

            // 좌측과 우측 중에서 최소인 길이를 d로 한다.
            double d;
            Pair minPair;
            if (left_min.distance > right_min.distance) {
                d = right_min.distance;
                minPair = new Pair(right_min);
            } else {
                d = left_min.distance;
                minPair = new Pair(left_min);
            }
            // x축 기준 점의 개수를 절반으로 나누는 x를 구한다.
            // 여기에서는 좌측의 맨 우측점과 좌측의 맨 왼쪽점으로 의 중간으로 구현했다.
            double meanX = (dots.get(dots.size() / 2).x + dots.get(dots.size() / 2 + 1).x) / 2;

            // Window 내부의 점을 찾아내 리스트를 생성했다.
            ArrayList<Dot> windowList = new ArrayList<>();
            for (int i = 0; i < dots.size(); i++) {
                if (dots.get(i).x > meanX - d && dots.get(i).x < meanX + d) {
                    windowList.add(dots.get(i));
                }
            }
            // Window 내부의 최소인 점을 구했다
            Pair inside_window = getDotInWindow(windowList, d);

            // 기존의 좌,우측의 최소와 window 내부의 최소를 비교해 작은 쌍을 반환한다.
            if (d > inside_window.distance) {
                return inside_window;
            } else {
                return minPair;
            }
        }
    }
    // Window 내부의 최소인 쌍을 구하는 함수이다.
    private Pair getDotInWindow(ArrayList<Dot> windowList, double d) {
        // Y축 기준으로 정렬한다
        Collections.sort(windowList, new Comparator<Dot>() {
            @Override
            public int compare(Dot o1, Dot o2) {
                if (o1.y < o2.y) {
                    return -1;
                } else if (o1.y > o2.y) {
                    return 1;
                }
                return 0;
            }
        });
        Pair minPair = null;
        double minDis = Double.MAX_VALUE;
        // y축 기준 d이하 만큼 떨어진 점들에 대해서 계산하였다.
        for (int i = 0; i < windowList.size(); i++) {
            for (int j = i + 1; j < windowList.size(); j++) {
                Dot iDot = windowList.get(i);
                Dot jDot = windowList.get(j);
                if (iDot.y + d > jDot.y) {
                    double distance = getDistance(iDot, jDot);
                    if (minDis > distance) {
                        minDis = distance;
                        minPair = new Pair(iDot, jDot, minDis);
                    }
                }
            }
        }
        return minPair;
    }
    // 길이를 구하는 함수이다.
    private double getDistance(Dot dot1, Dot dot2) {
        return Math.sqrt(Math.pow(dot1.x - dot2.x, 2) + Math.pow(dot1.y - dot2.y, 2));
    }
}