package pths.less2;

/**
 * Этот класс складывает целые числа.
 * Если в результате сложения двух чисел получается переполнение,
 * бросается исключение ArithmeticException.
 */
public class ThrowingAdder {
    public int add(int a, int b) {
        int sum = a + b;
        if (a > 0 && b > 0 && sum < 0) throw new ArithmeticException("int overflow");
        if (a < 0 && b < 0 && sum >= 0) throw new ArithmeticException("int overflow");
        return sum;
    }

    public long add(long a, long b) {
        long sum = a + b;
        if (a > 0 && b > 0 && sum < 0) throw new ArithmeticException("long overflow");
        if (a < 0 && b < 0 && sum >= 0) throw new ArithmeticException("long overflow");
        return sum;
    }
}
