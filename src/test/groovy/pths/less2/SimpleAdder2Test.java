package pths.less2;

public class SimpleAdder2Test {
    public static void main(String[] args) {
        test(0, 1, 0);
        test(1, 2, 3);
    }

    static void test(int a, int b, int res) {
        SimpleAdder adder = new SimpleAdder();

        int sum = adder.add(a, b);
        System.out.println("a: " + a + " , b: " + b + ", sum: " + sum);
        if (sum != res) System.out.println("---- ERROR ----");

    }
}
