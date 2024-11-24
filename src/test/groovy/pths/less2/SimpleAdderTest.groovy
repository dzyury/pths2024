package pths.less2

import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class SimpleAdderTest extends Specification {
    @Ignore
    void test() {
        expect:
        1 + 1 == 3
    }

    void "add returns just a sum of ints"(int a, int b, int c) {
        given:
        SimpleAdder adder = new SimpleAdder()

        expect:
        adder.add(a, b) == c

        where:
        a | b || c
        0 | 0 || 0
        1 | 2 || 3
    }

    void "add returns just a sum of longs"(long a, long b, long c) {
        given:
        SimpleAdder adder = new SimpleAdder()

        expect:
        adder.add(a, b) == c

        where:
        a | b || c
        0 | 0 || 0
        1 | 2 || 3
    }
}
