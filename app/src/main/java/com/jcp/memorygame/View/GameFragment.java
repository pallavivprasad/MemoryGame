package com.jcp.memorygame.View;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jcp.memorygame.Presenter.MgPresenter;
import com.jcp.memorygame.R;
import com.jcp.memorygame.View.Interfaces.IMemoryGameView;
import com.jcp.memorygame.View.Interfaces.IOnBackPress;

import java.util.ArrayList;

/* Game board class*/
public class GameFragment extends Fragment implements IMemoryGameView, View.OnClickListener, IOnBackPress {


    private MgPresenter mgPresenter;
    private TextView mNoToBefound; // no that needs to be found
    private Handler mHandler = new Handler();
    private int waitTime = 6;
    private TextView mTv1, mTv2, mTv3, mTv11, mTv12, mTv13, mTv21, mTv22, mTv23;
    private TextView mAppName;
    private ArrayList<TextView> mGameTextViews ; // list of textview used as grid view in the game board
    private LinearLayout mGameLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // initialize the required variables
        initialize(view);

        // game
        startTheGame();
    }

    public Runnable runnable = new Runnable() {
        public void run() {
            if (waitTime == 0) {

                // hide all the view as the game begins
                hideAllView();

                // first no to  find
                mgPresenter.showNoTobeRevealed();
                } else {
                waitTime -= 1;
                mHandler.postDelayed(this, 1 * 1000);
                showMessage(getString(R.string.game_starts_in) + "" + waitTime + "" + getString(R.string.sec));
            }
        }
    };

    /* display the random no in the game grid*/
    public void startTheGame(){
        mHandler.postDelayed(runnable, 1 * 1000);
        mgPresenter = new MgPresenter(this);

        mgPresenter.setUpRandomNoList();
    }

    /* initialize req variables and required data*/
    private void initialize(View view){

        mTv1 = (TextView)view.findViewById(R.id.tv_grid_1);
        mTv1.setOnClickListener(this);

        mTv2 = (TextView)view.findViewById(R.id.tv_grid_2);
        mTv2.setOnClickListener(this);

        mTv3 = (TextView)view.findViewById(R.id.tv_grid_3);
        mTv3.setOnClickListener(this);

        mTv11 = (TextView)view.findViewById(R.id.tv_grid_11);
        mTv11.setOnClickListener(this);

        mTv12 = (TextView)view.findViewById(R.id.tv_grid_12);
        mTv12.setOnClickListener(this);

        mTv13 = (TextView)view.findViewById(R.id.tv_grid_13);
        mTv13.setOnClickListener(this);

        mTv21 = (TextView)view.findViewById(R.id.tv_grid_21);
        mTv21.setOnClickListener(this);

        mTv22 = (TextView)view.findViewById(R.id.tv_grid_22);
        mTv22.setOnClickListener(this);

        mTv23 = (TextView)view.findViewById(R.id.tv_grid_23);
        mTv23.setOnClickListener(this);

        mNoToBefound = (TextView)view.findViewById(R.id.tv_no_to_guess);
        mNoToBefound.setTextAppearance(getActivity(), R.style.tv_default); // change the style

        mGameTextViews = new ArrayList<>();
        mGameTextViews.add(mTv1);
        mGameTextViews.add(mTv2);
        mGameTextViews.add(mTv3);
        mGameTextViews.add(mTv11);
        mGameTextViews.add(mTv12);
        mGameTextViews.add(mTv13);
        mGameTextViews.add(mTv21);
        mGameTextViews.add(mTv22);
        mGameTextViews.add(mTv23);

        mGameLayout = (LinearLayout)view.findViewById(R.id.ll_grid);
        mAppName = (TextView)view.findViewById(R.id.tv_app_name);
        if(mAppName.getVisibility() == View.INVISIBLE){ mAppName.setVisibility(View.VISIBLE);}
    }

    @Override
    public void setRandomNosToMemoryGameView(ArrayList<Integer> randomNumberList) {
        for(int i =0; i <= mGameTextViews.size() - 1; i++){
            mGameTextViews.get(i).setText(String.valueOf(randomNumberList.get(i)));
        }
    }

    /*After 5 sec hide the view*/
    public  void hideAllView(){
        for (TextView tv:mGameTextViews) {
            tv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void resetMemoryGame() {

        mGameLayout.setBackgroundColor(Color.TRANSPARENT);
        for(TextView tv:mGameTextViews){
            tv.setText("");
        }

        mAppName.setVisibility(View.INVISIBLE);
        mNoToBefound.setText(getResources().getString(R.string.game_over));
        mNoToBefound.setTextAppearance(getActivity(), R.style.tv_attention_style);

    }

    /* Toast message*/
    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();}

    @Override
    public void showRandomNoToBeRevealed(String no){
        mNoToBefound.setText(getResources().getString(R.string.find_no) + " " + no);
    }

    /* On selection verify if the selected and no to be be found are same */
    private void checkIfSelectedGridHasRevealedNo(TextView textView){

        if(((ColorDrawable) textView.getBackground()).getColor() == getResources().getColor(R.color.colorPrimaryDark) ){
            if(mgPresenter.isSelectionCorrect(textView.getText().toString())){
                mgPresenter.removeRevealedNoFromRevealingList(textView.getText().toString());
                textView.setBackgroundColor(Color.TRANSPARENT);
            }else{
                showMessage(getResources().getString(R.string.error_in_selection));
            }
        }
    }

    /* on click of game grid view*/
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tv_grid_1:
            case R.id.tv_grid_2:
            case R.id.tv_grid_3:
            case R.id.tv_grid_11:
            case R.id.tv_grid_12:
            case R.id.tv_grid_13:
            case R.id.tv_grid_21:
            case R.id.tv_grid_22:
            case R.id.tv_grid_23:
                checkIfSelectedGridHasRevealedNo((TextView)v);
                break;

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(mHandler != null){
            mHandler.removeCallbacks(runnable);
            }
    }

    /* remove the callbacks*/
    private void removeHandler(){
        if(mHandler != null){
            mHandler.removeCallbacks(runnable);
        }
    }

    /* fragment backpress handled here*/
    @Override
    public boolean onBackPress() {
        if(mHandler != null) {
            removeHandler();
        }
        return false;
    }
}
