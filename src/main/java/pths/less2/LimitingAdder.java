package pths.less2;

/**
 * Этот класс складывает целые числа.
 * Если в результате сложения двух положительных чисел получается переполнение,
 * возвращается максимально-возможное положительное число.
 * Если в результате сложения двух отрицательных чисел получается переполнение,
 * возвращается минимально-возможное отрицательное число (максимальное по модулю).
 */
public class LimitingAdder {
    public int add(int a, int b) {
        int sum = a + b;
        if (a > 0 && b > 0 && sum < 0) return Integer.MAX_VALUE;
        if (a < 0 && b < 0 && sum >= 0) return Integer.MIN_VALUE;
        return sum;
    }

    public long add(long a, long b) {
        long sum = a + b;
        if (a > 0 && b > 0 && sum < 0) return Long.MAX_VALUE;
        if (a < 0 && b < 0 && sum >= 0) return Long.MIN_VALUE;
        return sum;
    }
}
