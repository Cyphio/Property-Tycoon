package misc;

/**
 * Simulates the cards and their functions
 */
public class Card implements CardInterface {

    private String action;
    private Integer value;


    /**
     * used to change the action that a card performs
     * @param action the action to be performed
     */
    @Override
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return returns the action of the card object
     */
    @Override
    public String getAction(){
        return action;
    }

    /**
     * Certain card actions, such as paying, require an int value, this sets the value for that int
     * @param value an int value
     */
    @Override
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return returns the int value assigned to the card
     */
    @Override
    public int getValue(){
        return value;
    }

    /**
     * carries out the action of the card
     */
    @Override
    public void runAction() {
    }

    public String getCardMessage(){


        if(value != null && !action.equals("Go back to")){

            return action+" "+value+".";

        }

        return action;

    }


}
