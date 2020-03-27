package main;


public class GameController implements GameControllerInterface {


    private int height;
    private int width;
    private boolean running;
    private static GameBoard board;
    private Player currentPlayer;

    /**
     * getCurrentPlayer provides functionality to return the current player outside of this class
     * @return returns the player who's turn it currently is
     */
    public Player getCurrentPlayer(){

        return this.currentPlayer;
    }


    public static void main(String[] args) {


        // board = new GameBoard();


    }
//
//    public void runGame() {
//        while (running) {
//
//            for(Player player: players){
//                currentPlayer = player;
//
//                boolean playingTurn = true;
//
//                EventListener e = new EventListener(){
//
//
//
//
//                };
//
//
//
//                while(playingTurn){
//
//                }
//
//
//
//
//
//            }
//
//
//
//
//        }
//    }
//
//
//    }


    @Override
    public void endGame() {

    }


}
