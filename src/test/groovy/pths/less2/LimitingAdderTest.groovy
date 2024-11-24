package pths.less2

import pths.less2.LimitingAdder
import spock.lang.Specification

class LimitingAdderTest extends Specification {
    void "add returns just a sum of ints"(int a, int b, int c) {
        given:
        LimitingAdder adder = new LimitingAdder()

        expect:
        adder.add(a, b) == c

        where:
        a                     | b                 || c
        3                     | 7                 || 10
        5                     | -4                || 1
        9                     | -18               || -9
        Integer.MAX_VALUE     | 1                 || Integer.MAX_VALUE
        Integer.MAX_VALUE     | Integer.MAX_VALUE || Integer.MAX_VALUE
        Integer.MIN_VALUE     | -1                || Integer.MIN_VALUE
        Integer.MIN_VALUE     | Integer.MIN_VALUE || Integer.MIN_VALUE
    }

    void "add returns just a sum of longs"(long a, long b, long c) {
        given:
        LimitingAdder adder = new LimitingAdder()

        expect:
        adder.add(a, b) == c

        where:
        a              | b              || c
        3              | 7              || 10
        5              | -4             || 1
        9              | -18            || -9
        Long.MAX_VALUE | 1              || Long.MAX_VALUE
        Long.MAX_VALUE | Long.MAX_VALUE || Long.MAX_VALUE
        Long.MIN_VALUE | -1             || Long.MIN_VALUE
        Long.MIN_VALUE | Long.MIN_VALUE || Long.MIN_VALUE
    }
}
