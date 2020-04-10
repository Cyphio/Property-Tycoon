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


    
    public GameController(TiledMapTileLayer layer){

        cellToTile = new HashMap<>();

        board = new GameBoard();
        buildCellReference(layer);



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


        int count = 0;


//go 1
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
        }

        tileNUm++;
// vertical row 10
        for (int y = 4; y < 31; y++) {


            if (count == 3) {
                count = 0;
                tileNUm++;


            }
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
            count++;

        }

        tileNUm++;

        //jail 1 (12)

        for (int y = 31; y < 35; y++) {
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
        }


        //row horizontal
        for (int x = 4; x<31; x++){

            if (count == 3) {
                count = 0;
                tileNUm++;
            }

            for (int y = 31; y < 35; y++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }

            count++;


        }


        tileNUm++;

//free parking
        for (int y = 31; y < 35; y++) {
            for (int x = 31; x < 35; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
        }

        //vertical row
        for (int y = 30; y > 3; y--) {

            if (count == 3) {
                count = 0;
                tileNUm++;

            }
            for (int x = 31; x < 35; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
            count++;
        }
        tileNUm++;
        //go to jail
        for (int y = 0; y < 4; y++) {
            for (int x = 31; x < 35; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }
        }


        //horizontal final row
        for (int x = 30; x>3; x--){


            if (count == 3) {
                count = 0;
                tileNUm++;
            }

            for (int y = 0; y < 4; y++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
            }

            count++;


        }




        System.out.println("built");



        }


    @Override
    public void endGame() {

    }
}





