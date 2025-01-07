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
        cell.setData(null);
        assertNull(cell.getData());
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
        cell.setData("=123+");
        assertEquals(Ex2Utils.ERR_FORM_FORMAT, cell.getType());
    }

    @Test
    public void testGetOrder() {
        assertEquals(0, cell.getOrder());
        cell.setOrder(2);
        assertEquals(2, cell.getOrder());
        cell.setOrder(5);
        assertEquals(5, cell.getOrder());
        cell.setOrder(1);
        assertEquals(1, cell.getOrder());
        cell.setOrder(3);
        assertEquals(3, cell.getOrder());
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