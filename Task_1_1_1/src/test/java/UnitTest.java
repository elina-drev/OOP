import org.example.Heapsort;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class UnitTest {
    Heapsort func = new Heapsort();
    Random rnd = new Random();

    @Test
    void TestEMPTY() {
        int[] out = {};
        int[] arr = func.sort(out, out.length);
        assertArrayEquals(new int[] {}, arr);
    }

    @Test
    void TestOneElem() {
        int len = 1;
        int[] out = new int[len];
        for (int i = 0; i < len; i++){
            out[i] = rnd.nextInt();
        }
        int[] arr = out.clone();
        int[] our = func.sort(arr, arr.length);
        Arrays.sort(arr);

        assertArrayEquals(arr, our);
    }

    @Test
    void TestNotSoBigCountElems() {
        int len = 10000;
        int[] out = new int[len];
        for (int i = 0; i < len; i++){
            out[i] = rnd.nextInt();
        }
        int[] arr = out.clone();
        int[] our = func.sort(arr, arr.length);
        Arrays.sort(arr);

        assertArrayEquals(arr, our);
    }

    @Test
    void TestBiggerCountElems() {
        int len = 100000;
        int[] out = new int[len];
        for (int i = 0; i < len; i++){
            out[i] = rnd.nextInt();
        }
        int[] arr = out.clone();
        int[] our = func.sort(arr, arr.length);
        Arrays.sort(arr);

        assertArrayEquals(arr, our);
    }

    @Test
    void TestBigCountElems() {
        int len = 10000000;
        int[] out = new int[len];
        for (int i = 0; i < len; i++){
            out[i] = rnd.nextInt();
        }
        int[] arr = out.clone();
        int[] our = func.sort(arr, arr.length);
        Arrays.sort(arr);

        assertArrayEquals(arr, our);
    }

    @Test
    void TestSame() {
        int[] out = {0,0,0,0,0,0,0,0};
        int[] arr = func.sort(out, out.length);
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, arr);
    }
}
