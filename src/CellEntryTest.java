import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellEntryTest {

    @Test
    public void testValidCellEntries() {
        CellEntry cellA1 = new CellEntry(0, 1); // A1
        assertTrue(cellA1.isValid());
        assertEquals(0, cellA1.getX());
        assertEquals(1, cellA1.getY()); // 1-based row index
        assertEquals("A1", cellA1.toString());

        CellEntry cellB2 = new CellEntry(1, 2); // B2
        assertTrue(cellB2.isValid());
        assertEquals(1, cellB2.getX());
        assertEquals(2, cellB2.getY()); // 1-based row index
        assertEquals("B2", cellB2.toString());

        CellEntry cellC3 = new CellEntry(2, 3); // C3
        assertTrue(cellC3.isValid());
        assertEquals(2, cellC3.getX());
        assertEquals(3, cellC3.getY()); // 1-based row index
        assertEquals("C3", cellC3.toString());

        CellEntry cellZ100 = new CellEntry(25, 100); // Z100
        assertTrue(cellZ100.isValid());
        assertEquals(25, cellZ100.getX());
        assertEquals(100, cellZ100.getY()); // 1-based row index
        assertEquals("Z100", cellZ100.toString());
    }

    @Test
    public void testInvalidCellEntries() {
        CellEntry invalidNegative = new CellEntry(-1, 1); // Invalid column
        assertFalse(invalidNegative.isValid());
        assertEquals(Ex2Utils.ERR, invalidNegative.getX());
        assertEquals(Ex2Utils.ERR, invalidNegative.getY());
        assertEquals(Ex2Utils.ERR_FORM, invalidNegative.toString());

        CellEntry invalidTooLarge = new CellEntry(26, 1); // Invalid column
        assertFalse(invalidTooLarge.isValid());
        assertEquals(Ex2Utils.ERR, invalidTooLarge.getX());
        assertEquals(Ex2Utils.ERR, invalidTooLarge.getY());
        assertEquals(Ex2Utils.ERR_FORM, invalidTooLarge.toString());

        CellEntry invalidRow = new CellEntry(0, 0); // Invalid row (0-based)
        assertFalse(invalidRow.isValid());
        assertEquals(Ex2Utils.ERR, invalidRow.getX());
        assertEquals(Ex2Utils.ERR, invalidRow.getY());
        assertEquals(Ex2Utils.ERR_FORM, invalidRow.toString());
    }

    @Test
    public void testSpreadsheetNotation() {
        CellEntry cellD4 = new CellEntry(3, 4); // D4
        assertTrue(cellD4.isValid());
        assertEquals("D4", cellD4.toString());

        CellEntry cellY50 = new CellEntry(24, 50); // Y50
        assertTrue(cellY50.isValid());
        assertEquals("Y50", cellY50.toString());
    }
}
