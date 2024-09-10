import org.example.Heapsort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class UnitTest {
    @Test
    void TestTwoElem() {
        int[] out = {493,1};
        func.sort(out, out.length);
        assertArrayEquals(new int[] {1, 493}, out);
    }

    Heapsort func = new Heapsort();
    @Test
    void TestRandomARRAY() {
        int[] out = {10, 5, 3, -1, -4};
        func.sort(out, out.length);
        assertArrayEquals(new int[] {-4, -1, 3, 5, 10}, out);
    }

    @Test
    void TestClass() {
        int[] out = {5, 4, 3, 2, 1};
        func.sort(out, out.length);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, out);
    }

    @Test
    void TestEMPTY() {
        int[] out = {};
        func.sort(out, out.length);
        assertArrayEquals(new int[] {}, out);
    }

    @Test
    void TestSame() {
        int[] out = {0,0,0,0,0,0,0,0};
        func.sort(out, out.length);
        assertArrayEquals(new int[] {0,0,0,0,0,0,0,0}, out);
    }
}
