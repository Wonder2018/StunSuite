package top.imwonder.stunsuite;

import org.junit.Test;

public class LogicTest {

    @Test
    public void multipleOfEight() {
        int a = 33;
        int b = (a + (8 - 1)) & ~(8-1);
        System.out.println(String.format("\n\ncalc a: %d\nresult b: %d\n\n", a,b));
    }
}
