package com.jcp.memorygame.View.Interfaces;

import java.util.ArrayList;

/* Helps in view interaction and data*/
public interface IMemoryGameView {

    void setRandomNosToMemoryGameView(ArrayList<Integer> randomNoList);
    void resetMemoryGame();
    void showMessage(String message);
    void showRandomNoToBeRevealed(String no);
}
