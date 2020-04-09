package main;


import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.HashMap;
import java.util.List;

public class GameController implements GameControllerInterface {


    private int height;
    private int width;
    private boolean running;
    private static GameBoard board;
    private Player currentPlayer;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;

    private Player p;
    
    
    public GameController(TiledMapTileLayer layer){

        cellToTile = new HashMap<>();



 //for testing purposes
        p = new Player();

        Player[] ps = new Player[1];

        ps[0] = p;
        board = new GameBoard(ps);
        buildCellReference(layer);
//=============================================


        }


        public Tile retTile(TiledMapTileLayer.Cell cell){


        return cellToTile.get(cell);

        }
        
        
        
        

    
    
    

    /**
     * getCurrentPlayer provides functionality to return the current player outside of this class
     * @return returns the player who's turn it currently is
     */
    public Player getCurrentPlayer(){

        return this.currentPlayer;
    }



    public void buildCellReference(TiledMapTileLayer l ) {



        int tileNUm = 0;

        int cordY = 0;




        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
        }

        tileNUm++;

        for (int y = 3; y < 21; y++) {
            for (int x = 0; x < 3; x++) {
                cellToTile.put(l.getCell(x, cordY + y), board.getTile(tileNUm));
            }
            if (y % 2 == 0) {

                tileNUm++;
            }
        }

        for (int y = 21; y < 24; y++) {
            for (int x = 0; x < 3; x++) {
                cellToTile.put(l.getCell(x, cordY + y), board.getTile(tileNUm));
            }
        }
        tileNUm++;


        System.out.println("built");



        }


    @Override
    public void endGame() {

    }
}





