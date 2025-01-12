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
    void testDepthMalformedFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "="); // A1 with an invalid formula

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][1]); // A1
    }
    @Test
    public void testValue() {
        sheet.set(0, 1, "42");
        assertEquals("42.0", sheet.value(0, 1));

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
        sheet.set(0, 1, "5");
        sheet.set(1, 1, "3");
        sheet.set(2, 0, "=A1+B1");
        sheet.set(3, 0, "=C0");


        assertEquals("8.0", sheet.eval(2, 0));
        assertEquals("8.0", sheet.eval(3, 0));

    }
    @Test
    public void testEvalWithCycle() {
        sheet.set(0, 1, "=B1");
        sheet.set(1, 1, "=A1");

        assertEquals(Ex2Utils.ERR_CYCLE, sheet.eval(0, 1));
        assertEquals(Ex2Utils.ERR_CYCLE, sheet.eval(1, 1));
    }
    @Test
    void testComplexFormula() {
        sheet.set(0, 1, "5");
        sheet.set(1, 1, "3");
        sheet.set(2, 0, "=-A1+3 -4*b1+b1/2");
        sheet.set(3, 0, "=a1-2");


        assertEquals("-12.5", sheet.eval(2, 0));
        assertEquals("3.0", sheet.eval(3, 0));

    }
    @Test
    public void testDepth() {
        sheet.set(0, 1, "5");
        sheet.set(1, 1, "=A1");
        sheet.set(4, 4, "=B1+B3");

        int[][] depth = sheet.depth();
        assertEquals(0, depth[0][1]);
        assertEquals(1, depth[1][1]);
        assertEquals(2, depth[4][4]);
    }

    @Test
    void testDepthWithEmptyCell() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, ""); // Empty cell

        int[][] depthMatrix = sheet.depth();

        assertEquals(0, depthMatrix[0][0]); // Empty cells should have depth 0
    }

    @Test
    void testDepthWithMalformedFormula() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "="); // Malformed formula

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][0]); // Invalid formula should return error
    }

    @Test
    void testDepthWithInvalidReference() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=Z100"); // Invalid reference

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][0]); // Invalid references should return error
    }


    @Test
    void testDepthSimpleValues() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "5");
        sheet.set(1, 2, "10");
        sheet.set(2, 2, "15");

        int[][] depthMatrix = sheet.depth();

        assertEquals(0, depthMatrix[0][1]);
        assertEquals(0, depthMatrix[1][2]);
        assertEquals(0, depthMatrix[2][2]);
    }
    @Test
    void testDepthWithSelfReference() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=A1"); // Self-reference

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][1]); // A1
    }

    @Test
    void testDepthWithDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=B1+10"); // A1 depends on B1
        sheet.set(1, 1, "5"); // B1

        int[][] depthMatrix = sheet.depth();

        assertEquals(1, depthMatrix[0][1]); // A1
        assertEquals(0, depthMatrix[1][1]); // B1
    }

    @Test
    void testDepthWithCycle() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=B1"); // A1 depends on B1
        sheet.set(1, 1, "=A1"); // B1 depends on A1

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][1]); // A1
        assertEquals(-1, depthMatrix[1][1]); // B1
    }

    @Test
    void testDepthWithComplexCycle() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=B1+1"); // A1
        sheet.set(0, 2, "=C1+2"); // A2
        sheet.set(1, 1, "=A1+3"); // B1

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][1]); // A1
        assertEquals(1, depthMatrix[0][2]); // A2
        assertEquals(-1, depthMatrix[1][1]); // B1
    }

    @Test
    void testDepthInvalidReference() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=Z1"); // A1 references an invalid cell

        int[][] depthMatrix = sheet.depth();

        assertEquals(-1, depthMatrix[0][1]); // A1
    }

    @Test
    void testDepthFormulaWithMultipleDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 1, "=B1+C1"); // A1 depends on B1 and C1
        sheet.set(1, 1, "5"); // B1
        sheet.set(2, 1, "10"); // C1

        int[][] depthMatrix = sheet.depth();

        assertEquals(1, depthMatrix[0][1]); // A1
        assertEquals(0, depthMatrix[1][1]); // B1
        assertEquals(0, depthMatrix[2][1]); // C1
    }
}



