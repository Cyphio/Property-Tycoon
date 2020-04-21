package misc;

public class Coordinate {
    private int x;
    private int y;


    public Coordinate(int CellX, int CellY){
        this.x = CellX*64;
        this.y = CellY*64;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }




}
