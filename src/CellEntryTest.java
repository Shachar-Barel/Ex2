import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellEntryTest {

    @Test
    public void testValidToString() {
        CellEntry cell = new CellEntry(0, 0);
        assertEquals("A1", cell.toString());
        cell = new CellEntry(1, 2);
        assertEquals("B3", cell.toString());
        cell = new CellEntry(25, 99);
        assertEquals("Z100", cell.toString());
    }

    @Test
    public void testInvalidToString() {
        CellEntry cell = new CellEntry(-1, 0);
        assertEquals(Ex2Utils.ERR_FORM, cell.toString());
        cell = new CellEntry(0, -1);
        assertEquals(Ex2Utils.ERR_FORM, cell.toString());
        cell = new CellEntry(26, 0);
        assertEquals(Ex2Utils.ERR_FORM, cell.toString());
        cell = new CellEntry(0, 100);
        assertEquals(Ex2Utils.ERR_FORM, cell.toString());
    }

    @Test
    public void testIsValid() {
        CellEntry cell = new CellEntry(0, 0);
        assertTrue(cell.isValid());
        cell = new CellEntry(25, 99);
        assertTrue(cell.isValid());
        cell = new CellEntry(-1, 0);
        assertFalse(cell.isValid());
        cell = new CellEntry(0, -1);
        assertFalse(cell.isValid());
        cell = new CellEntry(26, 0);
        assertFalse(cell.isValid());
    }

    @Test
    public void testGetX() {
        CellEntry cell = new CellEntry(5, 10);
        assertEquals(5, cell.getX());
    }

    @Test
    public void testGetY() {
        CellEntry cell = new CellEntry(5, 10);
        assertEquals(10, cell.getY());
    }
}
