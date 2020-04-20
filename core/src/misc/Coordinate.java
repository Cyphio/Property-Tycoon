package misc;

public class Coordinate {
    private int x;
    private int y;


    public Coordinate(int x, int y){
        this.x = x*64;
        this.y = y*64;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }




}
