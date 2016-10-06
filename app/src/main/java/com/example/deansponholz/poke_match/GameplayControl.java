package com.example.deansponholz.poke_match;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by deansponholz on 10/3/16.
 */

public class GameplayControl extends Fragment {

    //instance data
    Button menuButton = null;
    Button restartButton = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_game_menu, container, false);

        this.menuButton = (Button) root.findViewById(R.id.mainMenu);
        this.restartButton = (Button) root.findViewById(R.id.restart);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GameplayActivity) getActivity()).restartGame();
                ((GameplayActivity) getActivity()).hideRestart();
            }
        });


        return root;
    }
}
