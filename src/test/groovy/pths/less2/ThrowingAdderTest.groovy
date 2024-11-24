package pths.less2

import pths.less2.ThrowingAdder
import spock.lang.Specification

class ThrowingAdderTest extends Specification {
    void "add returns just a sum of ints"(int a, int b, int c) {
        given:
        ThrowingAdder adder = new ThrowingAdder()

        expect:
        adder.add(a, b) == c

        where:
        a                 | b                 || c
        3                 | 7                 || 10
        5                 | -4                || 1
        9                 | -18               || -9
    }

    void "add throws exceptions due to overflow"(int a, int b) {
        given:
        ThrowingAdder adder = new ThrowingAdder()

        when:
        adder.add(a, b)

        then:
        ArithmeticException e = thrown()
        e.message == "int overflow"

        where:
        a                 | b
        Integer.MAX_VALUE | 1
        Integer.MAX_VALUE | Integer.MAX_VALUE
        Integer.MIN_VALUE | -1
        Integer.MIN_VALUE | Integer.MIN_VALUE
    }

    void "add returns just a sum of longs"(long a, long b, long c) {
        given:
        ThrowingAdder adder = new ThrowingAdder()

        expect:
        adder.add(a, b) == c

        where:
        a              | b              || c
        3              | 7              || 10
        5              | -4             || 1
        9              | -18            || -9
    }

    void "add throws exceptions due to overflow"(long a, long b) {
        given:
        ThrowingAdder adder = new ThrowingAdder()

        when:
        adder.add(a, b)

        then:
        ArithmeticException e = thrown()
        e.message == "long overflow"

        where:
        a              | b
        Long.MAX_VALUE | 1
        Long.MAX_VALUE | Long.MAX_VALUE
        Long.MIN_VALUE | -1
        Long.MIN_VALUE | Long.MIN_VALUE
    }
}
