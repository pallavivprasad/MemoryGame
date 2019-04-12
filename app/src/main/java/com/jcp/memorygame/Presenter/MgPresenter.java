package com.jcp.memorygame.Presenter;

import com.jcp.memorygame.Model.MgModel;
import com.jcp.memorygame.View.Interfaces.IMemoryGameView;

import java.util.Random;

/* This class helps in interacting between the view and model*/
public class MgPresenter  {

    private Random randomNo;
    private MgModel mgModel;
    private IMemoryGameView iMemoryGameView;

    public MgPresenter(IMemoryGameView view){
        randomNo = new Random();
        mgModel = new MgModel();
        iMemoryGameView = view;
    }

    /* Generate list of random no between 1 to 9 without repetition*/
    public void generateRandomNoList() {

        while (mgModel.getRandomNumberList().size() < mgModel.getMax()){
            int rn = randomNo.nextInt(mgModel.getMax() - mgModel.getMin() + 1) + mgModel.getMin();
            if(!mgModel.getRandomNumberList().contains(rn)){
                mgModel.getRandomNumberList().add(rn);
            }
        }
    }

    /* get the random no list and invoke method call to view to load the random no on to the game view*/
    public void setUpRandomNoList(){
        generateRandomNoList();

        new Runnable(){

            @Override
            public void run() {
                //TODO add it in separate thread
                mgModel.generateRandomNumberToBeRevealed();
            }
        }.run();

       iMemoryGameView.setRandomNosToMemoryGameView(mgModel.getRandomNumberList());
    }

    /* Displays the next no tobe revealed*/
    public void showNoTobeRevealed(){
        if(mgModel.getRandomNumberListToBeRevealed().size() > 0) {
            mgModel.setNoTobeFound(mgModel.getRandomNumberListToBeRevealed().get(0));
            iMemoryGameView.showRandomNoToBeRevealed(mgModel.getRandomNumberListToBeRevealed().get(0).toString());
        }
        else // show game over and reset the game
        {
            resetMemoryGrid();
        }
    }


    /* checks whether the selected grid and teh number to be found are correct */
    public boolean isSelectionCorrect(String value) {
        if(Integer.valueOf(value) == mgModel.getNumberToBeFound()){
            return true;
        }else return false;
    }

    /* reset the model lists and reset the game view*/
    public void resetMemoryGrid() {

            mgModel.reset();
            iMemoryGameView.resetMemoryGame();
    }

    /* removes the correct selected no; revealed no from the randomnumberlisttoberevealed */
    public void removeRevealedNoFromRevealingList(String revealedNo){

            // remove from list
           mgModel.removeFromRevealedList( Integer.valueOf(revealedNo));

           // check if the size is 0
           if(mgModel.getRandomNumberListToBeRevealed().size() > 0){
               // set the next no to be found
               mgModel.setNoTobeFound(mgModel.getRandomNumberListToBeRevealed().get(0));

               // show case the next no to be found in the game view
               iMemoryGameView.showRandomNoToBeRevealed(String.valueOf(mgModel.getNumberToBeFound()));
           }else{ // show gameover and reset game view
               resetMemoryGrid();
           }
        }
}
