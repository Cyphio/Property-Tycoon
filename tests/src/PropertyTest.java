import Tiles.Property;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import main.Player;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PropertyTest {

    private Property p;

    @Before
    public void setUp(){
        p = new Property();
        p.addPlayer(new Player("Jeff", new Sprite()));
    }

    @Test
    public void getColourAsStringTest(){
        p.setColour("BROWN");
        assertEquals("BROWN", p.getColourAsString());
    }

    @Test
    public void getColorTest1() {
        p.setColour("BROWN");
        assert(p.getColor() == Color.BROWN);
    }

    @Test
    public void getColorTest2() {
        p.setColour("BROWN");
        assert(p.getColor() instanceof  Color);
    }

    @Test
    public void getHousePriceTest() {
        p.setHousePrice(50);
        assertEquals(50, p.getHousePrice());
    }

    @Test
    public void getHotelPriceTest() {
        p.setHotelPrice(50);
        assertEquals(50, p.getHotelPrice());
    }

    @Test
    public void developTest() {
        p.develop();
        assert(p.getHousesOwned() == 1);
    }
}