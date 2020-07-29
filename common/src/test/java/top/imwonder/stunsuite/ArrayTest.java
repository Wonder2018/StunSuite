package top.imwonder.stunsuite;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

public class ArrayTest {
    @Test
    public void arrayCopyTest() {
        Integer a[] = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8 };
        Integer b[] = new Integer[15];
        Arrays.fill(b, 0);
        System.arraycopy(a, 0, b, b.length - a.length, a.length);
        System.out.println(
            String.format("test array a: %s\nlength of b: %d\nresult : %s",
                new ArrayList<>(Arrays.asList(a)).toString(), 
                b.length, 
                new ArrayList<>(Arrays.asList(b)).toString()
            )
        );
    }
}