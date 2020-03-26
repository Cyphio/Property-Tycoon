//package Test;
//import main.GameBoard;
//import main.Player;
//import org.junit.Before;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class GameBoardTest {
//    private GameBoard gb;
//    private Player[] pList = new Player[4];
//
//
//
//    @Test
//    public void MovePlayerTest(){
//        for ( int i=0; i< pList.length; i++) {
//            pList[i] = new Player();
//        }
//        gb = new GameBoard(pList);
//        gb.setPlayerPos(pList[0], 0);
//        gb.movePlayer(pList[0], 5);
//        assertEquals(gb.getPlayerPos(pList[0]), 4);
//        gb.movePlayer(pList[0], -10);
//        assertEquals(gb.getPlayerPos(pList[0]), 34);
//
//
//
//
//    }
//
//}
