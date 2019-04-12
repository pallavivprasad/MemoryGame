package com.jcp.memorygame.Model;

import java.util.ArrayList;
import java.util.Collections;

/* This is memorygame model class, helps in required information for the game to get going*/
public class MgModel  {

    private int noToBeFound ; // no that needs to be found in the game
    private ArrayList<Integer> randomNumberList = new ArrayList<>(); // list of random no that go into grid view
    private ArrayList<Integer> randomNumberListToBeRevealed = new ArrayList<>(); // revealing list of random no, that needs to be found
    private int min = 1, max = 9; // random no within min and max

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getNumberToBeFound(){
        return noToBeFound;
    }

    public void setNoTobeFound(int newNoToBeFound){
        this.noToBeFound = newNoToBeFound;
    }

    public ArrayList<Integer> getRandomNumberList() {
        return randomNumberList;
    }

    public ArrayList<Integer> getRandomNumberListToBeRevealed() {
        return randomNumberListToBeRevealed;
    }

    /* This method takes the random no list and shuffles the list to get the revealing list*/
    public ArrayList<Integer> generateRandomNumberToBeRevealed(){
        randomNumberListToBeRevealed = new ArrayList<>(getRandomNumberList());
        Collections.shuffle(randomNumberListToBeRevealed);
        return randomNumberListToBeRevealed;
    }

    /* Removes the selected no from the revealed list, once user selects the correct grid while guessing*/
    public void removeFromRevealedList(int revealedNo){
        if(randomNumberListToBeRevealed.size() > 0){
        randomNumberListToBeRevealed.remove(0);
        }
    }

    /* reset all teh list values*/
    public void reset(){
        randomNumberListToBeRevealed.clear();
        randomNumberList.clear();
    }
}
