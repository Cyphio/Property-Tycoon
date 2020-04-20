package main;


import Tiles.Tile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import misc.Coordinate;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.propertytycoonmakers.make.PropertyTycoon.players;

public class GameController implements GameControllerInterface {

    private int height;
    private int width;
    private boolean running;
    private static GameBoard board;
    private Player currentPlayer;
    private HashMap<TiledMapTileLayer.Cell, Tile> cellToTile;

    public GameController(TiledMapTileLayer layer) {
        cellToTile = new HashMap<>();
        board = new GameBoard();
        buildCellReference(layer);
        Tile tile = board.getTile(0);
        for (Player p : players) {
            Coordinate coord =  tile.getAvailableCoordinates();
            p.setCurrentCoordinates(coord);
        }
    }

    public Tile retTile(TiledMapTileLayer.Cell cell) {
        return cellToTile.get(cell);
    }

    /**
     * getCurrentPlayer provides functionality to return the current player outside of this class
     *
     * @return returns the player who's turn it currently is
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Tile movePlayer(Player player) {
        Tile oldTile = board.getTile(board.getPlayerPos(player));
        oldTile.removePlayer(player);

        Dice dice = new Dice();
        //need to implement rolling dice multiple time + jail here
        dice.rollDice();
        board.movePlayer(player, dice.getValue());
        dice.reset();

        return board.getTile(board.getPlayerPos(player));

    }

    // the most fucked code ever written (will rewrite this when we are in optimizing stage xoxoxoxoxo sorry boys was just a bit mad)
    public void buildCellReference(TiledMapTileLayer l) {
        ArrayList<Coordinate> cellCoordinates;
        Coordinate coord;
        int tileNUm = 0;
        int count = 0;
        //go tile
        cellCoordinates = new ArrayList<>();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);

            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;
        // vertical row 10
        cellCoordinates = new ArrayList<>();
        for (int y = 4; y < 31; y++) {
            if (count == 3) {
                board.getTile(tileNUm).setCoordinates(cellCoordinates);
                cellCoordinates = new ArrayList<>();
                count = 0;
                tileNUm++;
            }
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
            count++;

        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;
        //jail 1 (16)
        cellCoordinates = new ArrayList<>();
        for (int y = 31; y < 35; y++) {
            for (int x = 0; x < 4; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);

            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        cellCoordinates = new ArrayList<>();
        //row horizontal
        for (int x = 4; x < 31; x++) {
            if (count == 3) {
                if (cellCoordinates.size() > 0) {
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates = new ArrayList<>();
                count = 0;
                tileNUm++;
            }
            for (int y = 34; y > 30; y--) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;
//free parking
        cellCoordinates = new ArrayList<>();
        for (int y = 31; y < 35; y++) {
            for (int x = 31; x < 35; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        //vertical row
        cellCoordinates = new ArrayList<>();
        for (int y = 30; y > 3; y--) {
            if (count == 3) {
                if (cellCoordinates.size() > 0) {
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates = new ArrayList<>();
                count = 0;
                tileNUm++;
            }
            for (int x = 34; x > 30; x--) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        tileNUm++;
        //go to jail
        cellCoordinates = new ArrayList<>();
        for (int y = 0; y < 4; y++) {
            for (int x = 31; x < 35; x++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);
        //horizontal final row
        cellCoordinates = new ArrayList<>();
        for (int x = 30; x > 3; x--) {
            if (count == 3) {
                if (cellCoordinates.size() > 0) {
                    board.getTile(tileNUm).setCoordinates(cellCoordinates);
                }
                cellCoordinates = new ArrayList<>();
                count = 0;
                tileNUm++;
            }
            for (int y = 0; y < 4; y++) {
                cellToTile.put(l.getCell(x, y), board.getTile(tileNUm));
                coord = new Coordinate(x, y);
                cellCoordinates.add(coord);
            }
            count++;
        }
        board.getTile(tileNUm).setCoordinates(cellCoordinates);

        System.out.println("built");

    }


    @Override
    public void endGame() {

    }
}





