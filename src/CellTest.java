import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CellTest {
  @Test
  void testIsNumber() {
    assertTrue(firstPartCell.isNumber("123"));
    assertTrue(firstPartCell.isNumber("-123"));
    assertTrue(firstPartCell.isNumber("123.45"));
    assertTrue(firstPartCell.isNumber("-123.45"));
    assertFalse(firstPartCell.isNumber("123a"));
    assertFalse(firstPartCell.isNumber("abc"));
    assertFalse(firstPartCell.isNumber(null));
    assertFalse(firstPartCell.isNumber(""));
  }

  @Test
  void testIsText() {
    assertTrue(firstPartCell.isText("hello"));
    assertTrue(firstPartCell.isText("123abc"));
    assertTrue(firstPartCell.isText("{}"));
    assertTrue(firstPartCell.isText("{2@}"));
    assertFalse(firstPartCell.isText("123"));
    assertFalse(firstPartCell.isText(""));
    assertFalse(firstPartCell.isText(null));
  }

  @Test
  void testAreParenthesesBalanced() {
    assertTrue(firstPartCell.areParenthesesBalanced("(1+2)"));
    assertTrue(firstPartCell.areParenthesesBalanced("((1+2)*3)"));
    assertFalse(firstPartCell.areParenthesesBalanced("(1+2"));
    assertFalse(firstPartCell.areParenthesesBalanced("1+2)"));
    assertTrue(firstPartCell.areParenthesesBalanced(""));
  }

  @Test
  void testIsOperator() {
    assertTrue(firstPartCell.isOperator('+'));
    assertTrue(firstPartCell.isOperator('-'));
    assertTrue(firstPartCell.isOperator('*'));
    assertTrue(firstPartCell.isOperator('/'));
    assertFalse(firstPartCell.isOperator('a'));
    assertFalse(firstPartCell.isOperator('1'));
  }
  @Test
    void isFormCheck() {
    String s1 = "=1";
    assertEquals(firstPartCell.isForm(s1), true);
    String s2 = "=2+1";
    assertEquals(firstPartCell.isForm(s2), true);
    String s3 = "=1-1";
    assertEquals(firstPartCell.isForm(s3), true);
    String s4 = "=2*3";
    assertEquals(firstPartCell.isForm(s4), true);
    String s5 = "=4/2";
    assertEquals(firstPartCell.isForm(s5), true);
    String s6 = "=(1+2)*3";
    assertEquals(firstPartCell.isForm(s6), true);
    String s7 = "=(1+(2*3))";
    assertEquals(firstPartCell.isForm(s7), true);
    String s8 = "=1+2*3/4-5";
    assertEquals(firstPartCell.isForm(s8), true);
    String s9 = "=((1+2)*3)/4";
    assertEquals(firstPartCell.isForm(s9), true);
    String s10 = "=1+(2*3)";
    assertEquals(firstPartCell.isForm(s10), true);
    String s11 = "=1+";
    assertEquals(firstPartCell.isForm(s11), false);
    String s12 = "=+1";
    assertEquals(firstPartCell.isForm(s12), false);
    String s13 = "=1++2";
    assertEquals(firstPartCell.isForm(s13), false);
    String s14 = "=1+(2*3";
    assertEquals(firstPartCell.isForm(s14), false);
    String s15 = "=(1+2))*3";
    assertEquals(firstPartCell.isForm(s15), false);
  }
  @Test
  void testBasicCalculator() {
    assertEquals(5.0, firstPartCell.basicCalculator("2+3"));
    assertEquals(-1.0, firstPartCell.basicCalculator("2-3"));
    assertEquals(6.0, firstPartCell.basicCalculator("2*3"));
    assertEquals(2.0, firstPartCell.basicCalculator("6/3"));
    assertThrows(NumberFormatException.class, () -> firstPartCell.basicCalculator("2+"));
    assertThrows(ArrayIndexOutOfBoundsException.class, () -> firstPartCell.basicCalculator("2"));
  }

  @Test
  void testCalculateRemaining() {
    assertEquals(5.0, firstPartCell.calculateRemaining("2+3*5/5"));
    assertEquals(15.0, firstPartCell.calculateRemaining("5*3"));
    assertEquals(1.0, firstPartCell.calculateRemaining("3-2"));
    assertEquals(2.0, firstPartCell.calculateRemaining("4/2"));
  }

  @Test
  void testComputeForm() {
    assertTrue(firstPartCell.computeForm("(2+3)*5") == 25.0);
    assertTrue(firstPartCell.computeForm("10+(2*6)") == 22.0);
    assertTrue(firstPartCell.computeForm("100*2+12") == 212.0);
    assertTrue(firstPartCell.computeForm("(10+20)/2") == 15.0);
    assertTrue(firstPartCell.computeForm("10+((2+3)*3)") == 25.0);
  }
}

