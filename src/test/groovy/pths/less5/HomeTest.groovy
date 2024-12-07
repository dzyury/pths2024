package pths.less5

import spock.lang.Ignore
import spock.lang.Specification

import static java.lang.Double.NEGATIVE_INFINITY
import static java.lang.Double.NaN
import static java.lang.Double.POSITIVE_INFINITY

class HomeTest extends Specification {
    void "fn1 test"() {
        given:
        Home home = new Home()

        expect:
        home.fn1(list) == expected

        where:
        list           || expected
        []             || []
        [1, 2, 3]      || []
        [1, -2, 3, -4] || [4, 16]
    }

    void "fn2 test"(List<Double> list, Double expected) {
        given:
        Home home = new Home()

        expect:
        home.fn2(list) == expected

        where:
        list                        | expected
        [1d, 1.0e20d, -1.0e20d]     | 0d
        [1d, 2.0d, 3.0d]            | 6d
        [+1e15d] + [+1e-4d] * 10000 | +1e15d + 1
        [-1e15d] + [-1e-4d] * 10000 | -1e15d - 1
    }

    @Ignore
    void "fn3 test"(List<Double> list, Double expected) {
        given:
        Home home = new Home()

        expect:
        home.fn3(list).equals(expected)

        where:
        list                                 | expected
        [0d]                                 | 0d
        [-0d]                                | -0d
        [-0d, -0d]                           | 0d
        [-0d, +0d]                           | -0d
        [1e100d] * 10 + [0d]                 | 0d
        [1e100d] * 10 + [-0d]                | -0d
        [1e-100d] * 10 + [NEGATIVE_INFINITY] | NEGATIVE_INFINITY
        [1e-100d] * 10 + [POSITIVE_INFINITY] | POSITIVE_INFINITY
        [POSITIVE_INFINITY, 17d, +0d]        | NaN
        [POSITIVE_INFINITY, -6d, -0d]        | NaN
        [1e-100d] * 10 + [-1e100d] * 10      | 1d
        [1e-100d] * 11 + [-1e100d] * 11      | -1d
    }
}
