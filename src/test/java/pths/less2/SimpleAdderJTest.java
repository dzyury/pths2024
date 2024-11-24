package pths.less2;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class SimpleAdderJTest {
    @Test
    public void test() {
        int sum = 1 + 1;
        Assert.assertEquals(2, sum);
    }

    @Test
    @Parameters({
            "0, 0, 0",
            "1, 2, 3"
    })
    public void intTest(int a, int b, int c) {
        int sum = a + b;

        Assert.assertEquals(c, sum);
    }

    @Test
    @Parameters({
            "0, 0, 0",
            "1, 2, 3"
    })
    public void intTest(long a, long b, long c) {
        long sum = a + b;

        Assert.assertEquals(c, sum);
    }
}
