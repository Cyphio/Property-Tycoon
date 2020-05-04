import main.Dice;
import org.junit.*;
import static org.junit.Assert.*;

public class DiceTest {

    private Dice dice;

    @Before
    public void setUp() {
        dice = new Dice();
        dice.reset();
    }

    @Test
    public void rollDiceTest1() {
        dice.rollDice();
        assert(dice.getValue() >= 1 && dice.getValue() <= 12);
    }

    @Test
    public void rollDiceTest2() {
        dice.rollDice();
        assertFalse(dice.getValue() < 1 || dice.getValue() > 12);
    }


}
