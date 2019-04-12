package com.jcp.memorygame.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.jcp.memorygame.R;
import com.jcp.memorygame.View.Interfaces.IOnBackPress;

public class MainActivity  extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new InfoFragment(), R.id.fragment_container, true);
    }

    /* loading the required fragment*/
    private void loadFragment(Fragment fragment, int container, boolean isAddToBackStack) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().add(container, fragment);
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    /* on click of start game*/
    public void StartGame(View view) {

        loadFragment(new GameFragment(), R.id.fragment_container, true);
    }

    /*  on backpress from activity*/
    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof IOnBackPress) || !((IOnBackPress) fragment).onBackPress()) {

            super.onBackPressed();
        }
    }
}
