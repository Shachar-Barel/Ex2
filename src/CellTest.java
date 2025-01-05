import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
class CellTest {
  @Test
    void isNumber(){
    String s= "13";
      assertEquals(Cell.isNumber(s), true);

  }
}