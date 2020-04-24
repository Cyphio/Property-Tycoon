package misc;

import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import main.GameBoard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class builds the mapping between cells and Tile objetcs allowing for board interactivity
 */
public class CellToTileBuilder {

    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;

    /**
     * Constructor for CellToTileBuilder. Takes a tilemap and the gameboard and assigns Tile objects to each cell on the
     * gameboard that requires a Tile
     * @param l the tilemap to use for the board
     * @param board the gameboard to tile
     */
    public CellToTileBuilder(TiledMapTileLayer l, GameBoard board){

        cellToTile = new HashMap<>();

        ArrayList<Coordinate> cellCoordinates;
        Coordinate coord;
        int tileNUm=0;
        int count=0;

        //go tile
        cellCoordinates=new ArrayList<>();
        for(int y=0;y< 4;y++){
            for(int x=0;x< 4;x++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);

            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;

        // vertical row 10
        cellCoordinates=new ArrayList<>();
        for(int y=4;y< 31;y++){
            if(count==3){
                board.getTile(tileNUm).setCoordinates(cellCoordinates);
                cellCoordinates=new ArrayList<>();
                count=0;
                tileNUm++;
            }
            for(int x=0;x< 4;x++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
            count++;

        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;

        //jail 1 (16)
        cellCoordinates=new ArrayList<>();
        for(int y=31;y< 35;y++){
            for(int x=0;x< 4;x++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);

            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        cellCoordinates=new ArrayList<>();

        //row horizontal
        for(int x=4;x< 31;x++){
            if(count==3){
                if(cellCoordinates.size()>0){
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates=new ArrayList<>();
                count=0;
                tileNUm++;
            }
            for(int y=34;y>30;y--){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;

        //free parking
        cellCoordinates=new ArrayList<>();
        for(int y=31;y< 35;y++){
            for(int x=31;x< 35;x++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);

        //vertical row
        cellCoordinates=new ArrayList<>();
        for(int y=30;y>3;y--){
            if(count==3){
                if(cellCoordinates.size()>0){
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates=new ArrayList<>();
                count=0;
                tileNUm++;
            }
            for(int x=34;x>30;x--){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;

        //go to jail
        cellCoordinates=new ArrayList<>();
        for(int y=0;y< 4;y++){
            for(int x=31;x< 35;x++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);

        //horizontal final row
        cellCoordinates=new ArrayList<>();
        for(int x=30;x>3;x--){
            if(count==3){
                if(cellCoordinates.size()>0){
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates=new ArrayList<>();
                count=0;
                tileNUm++;
            }
            for(int y=0;y< 4;y++){
                cellToTile.put(l.getCell(x,y),board.getTile(tileNUm));
                coord=new Coordinate(x,y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);

        System.out.println("built");
    }

    /**
     * @return returns the hashmap of what cells are assigned to what tiles
     */

    public HashMap getReferenceList(){
        return cellToTile;
    }
}