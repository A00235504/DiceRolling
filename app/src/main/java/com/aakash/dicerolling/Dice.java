package com.aakash.dicerolling;

public class Dice {

    //initializing the variables
    String diceType;
    int numberOfSides;
    int currentSideUp;


    public Dice(int numberOfSides){
        //dice cannot have sides less than 3 or more than 100
        if(numberOfSides < 3 || numberOfSides > 100)
            return;
        diceType = "d"+numberOfSides;
        this.numberOfSides = numberOfSides;
        currentSideUp =  (int)(Math.random()*numberOfSides + 1);
    }



    public int rollmethod(){
        currentSideUp = (int)(Math.random()*this.numberOfSides + 1);
        return currentSideUp;
    }

    //defining the accessors for the dice
    public String getDiceType() {
        return diceType;
    }

    public int getNumberofSides() {
        return numberOfSides;
    }

    public int getSidesUp() {
        return currentSideUp;
    }

    //defining the mutators for the dice
    public void setDiceType(String diceType) {
        this.diceType = diceType;
    }

    public void setNumSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
        this.diceType = "d"+numberOfSides;
    }

    public void setSidesUp(int sideUp) {
        this.currentSideUp = sideUp;
    }

    //printing the dice output with number of sides and the side up
    public void printDice() {
        System.out.println("The current side of " + this.numberOfSides + " is " + this.currentSideUp + ".");
    }

}