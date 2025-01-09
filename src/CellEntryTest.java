/*
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CellEntryTest {

    @Test
    void testToString() {
        CellEntry validCell = new CellEntry("C12");
        assertEquals("C12", validCell.toString());

        CellEntry invalidCell = new CellEntry("Z101");
        assertEquals(Ex2Utils.ERR_FORM, invalidCell.toString());
    }

    @Test
    void testIsValid() {
        assertTrue(new CellEntry("A1").isValid());
        assertTrue(new CellEntry("Z99").isValid());
        assertTrue(new CellEntry("M50").isValid());

        assertFalse(new CellEntry("1A").isValid());
        assertFalse(new CellEntry("A100").isValid());
        assertFalse(new CellEntry("AA1").isValid());
    }

    @Test
    void testGetX() {
        assertEquals(3, new CellEntry("C12").getX());
        assertEquals(1, new CellEntry("A1").getX());

        assertEquals(Ex2Utils.ERR, new CellEntry("Z101").getX());
        assertEquals(Ex2Utils.ERR, new CellEntry("1A").getX());
    }

    @Test
    void testGetY() {
        assertEquals(12, new CellEntry("C12").getY());
        assertEquals(1, new CellEntry("A1").getY());

        assertEquals(Ex2Utils.ERR, new CellEntry("Z101").getY());
        assertEquals(Ex2Utils.ERR, new CellEntry("A-1").getY());
    }
}
*/