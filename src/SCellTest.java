import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class SCellTest {
    private SCell cell;

    @BeforeEach
    public void setUp() {
        cell = new SCell("");
    }
    @Test
    public void testcomputeOrder() {
        String s= "=a1+b3+3";
        assertEquals(3, SCell.computeOrder(s));
        String s2= "=(a1+3.5)-3+3+g4";
        assertEquals(3, SCell.computeOrder(s2));

    }
    @Test
    public void testSetData() {
        cell.setData("=(1+2)*3+a1");
        assertEquals("=(1+2)*3+a1", cell.getData());
        assertEquals(Ex2Utils.FORM, cell.getType());
        cell.setData("123");
        assertEquals("123", cell.getData());
        assertEquals(Ex2Utils.NUMBER, cell.getType());
        cell.setData("-456.78");
        assertEquals("-456.78", cell.getData());
        assertEquals(Ex2Utils.NUMBER, cell.getType());
        cell.setData("Hello");
        assertEquals("Hello", cell.getData());
        assertEquals(Ex2Utils.TEXT, cell.getType());
        cell.setData("Hello123!");
        assertEquals("Hello123!", cell.getData());
        assertEquals(Ex2Utils.TEXT, cell.getType());
        cell.setData("=A1+B2*C3-D4");
        assertEquals("=A1+B2*C3-D4", cell.getData());
        assertEquals(Ex2Utils.FORM, cell.getType());
    }

    @Test
    public void testGetData() {
        cell.setData("99");
        assertEquals("99", cell.getData());
        cell.setData("Some text");
        assertEquals("Some text", cell.getData());
        cell.setData("=A1+B1");
        assertEquals("=A1+B1", cell.getData());
        cell.setData("");
        assertEquals("", cell.getData());
    }

    // Tests for getType
    @Test
    public void testGetType() {
        cell.setData("3.14");
        assertEquals(Ex2Utils.NUMBER, cell.getType());
        cell.setData("Text123");
        assertEquals(Ex2Utils.TEXT, cell.getType());
        cell.setData("=1+a3");
        assertEquals(Ex2Utils.FORM, cell.getType());
        cell.setData("=a3");
        assertEquals(Ex2Utils.FORM, cell.getType());
        cell.setData("=123+");
        assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell.getType());
    }

    @Test
    public void testGetOrder() {
        SCell numberCell = new SCell("123");
        assertEquals(0, numberCell.getOrder());
        SCell textCell = new SCell("Hello");
        assertEquals(0, textCell.getOrder());
        SCell simpleFormulaCell = new SCell("=A1+B2");
        assertEquals(3, simpleFormulaCell.getOrder());
        SCell FormulaCell = new SCell("=A1*(B2+C3)");
        assertEquals(4, FormulaCell.getOrder());
        SCell invalidFormulaCell = new SCell("=++");
        assertEquals(-1, invalidFormulaCell.getOrder());
        SCell emptyCell = new SCell("");
        assertEquals(-1, emptyCell.getOrder());
    }
    @Test
    void testIsForm() {
        assertTrue(SCell.isForm("=A1")); // Simple cell reference
        assertTrue(SCell.isForm("=-A1 + 5")); // Unary operator
        assertTrue(SCell.isForm("=5 + 10 / 2")); // Numbers and operators
        assertTrue(SCell.isForm("=-A1 + -4 * A2 + A2 / 2")); // Complex formula
        assertTrue(SCell.isForm("=(A1 + A2) * 3")); // Formula with parentheses
        assertFalse(SCell.isForm("=5 +")); // Ends with an operator
        assertFalse(SCell.isForm("=A1 * (2 +")); // Unbalanced parentheses
        assertFalse(SCell.isForm("=+")); // Only unary operator
        assertFalse(SCell.isForm("A1 + 5")); // Missing '='
    }
    @Test
    public void testToString() {
        cell.setData("42");
        assertEquals("42", cell.toString());
        cell.setData("Hello World");
        assertEquals("Hello World", cell.toString());
        cell.setData("=A1+B2");
        assertEquals("=A1+B2", cell.toString());
        cell.setData("=A1+");
        assertEquals("=A1+", cell.toString());
    }
}