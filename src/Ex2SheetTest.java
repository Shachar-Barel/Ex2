import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Ex2SheetTest {

    private Ex2Sheet sheet;

    @BeforeEach
    public void setUp() {
        sheet = new Ex2Sheet(5, 5); // Create a 5x5 spreadsheet for testing
    }

    @Test
    public void testSetAndGet() {
        sheet.set(0, 0, "Hello");
        assertEquals("Hello", sheet.get(0, 0).getData());

        sheet.set(1, 1, "123");
        assertEquals("123", sheet.get(1, 1).getData());
    }

    @Test
    public void testValue() {
        sheet.set(0, 0, "42");
        assertEquals("42", sheet.value(0, 0));

        sheet.set(1, 1, "=A1");
        assertEquals("42.0", sheet.value(1, 1));
    }

    @Test
    public void testIsIn() {
        assertTrue(sheet.isIn(0, 0));
        assertTrue(sheet.isIn(4, 4));
        assertFalse(sheet.isIn(-1, 0));
        assertFalse(sheet.isIn(5, 5));
    }
    @Test
    public void testEvalSimpleFormula() {
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "3");
        sheet.set(2, 0, "=A1+B1");

        assertEquals("8.0", sheet.eval(2, 0));
    }

    @Test
    public void testEvalWithCycle() {
        sheet.set(0, 0, "=B1");
        sheet.set(1, 0, "=A1");

        assertEquals(Ex2Utils.ERR_CYCLE, sheet.eval(0, 0));
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.eval(1, 0));
    }

    @Test
    public void testDepth() {
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "=A1");
        sheet.set(2, 0, "=B1");

        int[][] depth = sheet.depth();
        assertEquals(0, depth[0][0]);
        assertEquals(1, depth[1][0]);
        assertEquals(2, depth[2][0]);
    }

    @Test
    public void testEmptyCell() {
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.value(3, 3));
        assertNull(sheet.get(3, 3).getData());
    }

    @Test
    public void testLoadAndSave() throws Exception {
        sheet.set(0, 0, "Hello");
        sheet.set(1, 1, "123");

        sheet.save("testSheet.txt");

        Ex2Sheet loadedSheet = new Ex2Sheet(5, 5);
        loadedSheet.load("testSheet.txt");

        assertEquals("Hello", loadedSheet.get(0, 0).getData());
        assertEquals("123", loadedSheet.get(1, 1).getData());
    }
}
