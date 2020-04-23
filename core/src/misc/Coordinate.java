package misc;

public class Coordinate {
    private int x;
    private int y;


    public Coordinate(int CellX, int CellY){
        this.x = CellX*64;
        this.y = CellY*64;
    }

    public void setCoordinate(int x, int y){


        this.x = x;
        this.y= y;



    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }




}
