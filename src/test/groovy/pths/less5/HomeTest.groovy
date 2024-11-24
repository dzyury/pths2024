package pths.less5

import spock.lang.Specification

class HomeTest extends Specification {
    void "fn1 возвести в квадрат отрицательные числа"() {
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

    void "fn2 вычислить сумму"(List<Double> list, Double expected) {
        given:
        Home home = new Home()

        expect:
        home.fn2(list.collect {it.toDouble()}) == expected

        where:
        [list, expected] << [
                [[1d, 1e20, -1e20], 0.0],
                [[1d, 2d, 3d], 6.0],
                [[1e15] + [1e-4]*10000, 1e15+1],
                [[-1e15] + [-1e-4]*10000, -1e15-1]
        ]
    }
}
