package main;

import java.util.Random;

class Dice implements DiceInterface {
    private int counter;
    private int d1;
    private int d2;
    private boolean wasDouble;


    /**
     * The dice class simulates a pair of two six sided dice to roll whenever a player moves
     */

    public Dice(){


        counter=0;

    }


    /**
     * reset sets all three variables to their initial state
     */
    @Override
    public void reset(){
        counter = 0;
        d1 = 0;
        d2 = 0;
        wasDouble = false;
    }

    /**
     * rollDice returns true if its a double (both rolls come out with the same number) and false otherwise.
     * With each roll counter is incremented by one
     */
    @Override
    public void rollDice(){
        Random r = new Random();

        d1 = r.nextInt(5) + 1;
        d2 = r.nextInt(5) + 1;
        counter++;

        if(d1 == d2){
            wasDouble =true;
        }
        else{
            wasDouble=false;
        }
    }

    /**
     * jailCheck will return true if both rolls are the same number and the counter is equal to three and false otherwise.
     * @return returns true or false depending if player rolled three doubles or not
     */
    @Override
    public boolean jailCheck(){
        if((d1 == d2) && (counter == 3)){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * getValue will return the summed up value of the two values rolled by the dices
     * @return returns the total value of the two dices
     */
    @Override
    public int getValue(){


        return d1+d2;
    }


    public boolean wasItADouble(){

        return wasDouble;

    }

}









