
import Tiles.Property;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import main.GameBoard;
import main.GameController;
import org.junit.Before;
import main.Player;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;


public class GameBoardTest {
    private Sprite sprite1;
    private Sprite sprite2;
    private Sprite sprite3;
    private Sprite sprite4;
    private Sprite sprite5;
    private Sprite sprite6;
    private ArrayList<Sprite> spriteList;


    private GameBoard testBoard;
    private GameController testController;

    private Player[] testPlayers;


    @Before
    public void setUp() {


        this.sprite1 = new Sprite();
        this.sprite2 = new Sprite();
        this.sprite3 = new Sprite();
        this.sprite4 = new Sprite();
        this.sprite5 = new Sprite();
        this.sprite6 = new Sprite();
        this.spriteList = new ArrayList<>(Arrays.asList(sprite1, sprite2, sprite3, sprite4, sprite5, sprite6));
        this.testPlayers = new Player[6];


        int i = 0;

        for (Sprite x : this.spriteList) {
            testPlayers[i] = new Player("player " + i, x);
            i++;
        }

        testBoard = new GameBoard(testPlayers);


    }
}




