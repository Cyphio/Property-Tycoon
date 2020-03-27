package misc;

public class Card implements CardInterface {

    private String action;
    private int value;


    @Override
    public void setAction(String action) {
        this.action = action;

    }
    @Override
    public String getAction(){

        return action;
    }
    @Override
    public void setValue(int value) {
        this.value = value;

    }
    @Override
    public int getValue(){
        return value;

    }

    @Override
    public void runAction() {

    }


}
