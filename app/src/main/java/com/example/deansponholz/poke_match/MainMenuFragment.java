package com.example.deansponholz.poke_match;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by deansponholz on 10/1/16.
 */

public class MainMenuFragment extends Fragment{

    //instance Data
    Button instructionsButton = null;
    Button newGameButton = null;
    Button aboutButton = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu, container, false);

        //initialize Buttons
        this.instructionsButton = (Button) root.findViewById(R.id.instructionButton);
        this.newGameButton = (Button) root.findViewById(R.id.newGame);
        this.aboutButton = (Button) root.findViewById(R.id.aboutButton);

        instructionsButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click Cards to find matches.\nFind all card matches in fewest clicks to win!", Toast.LENGTH_LONG).show();
            }
        });

        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GameplayActivity.class);
                getActivity().startActivity(intent);
            }
        });
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "This Application does not support orientation change during gameplay.", Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }
}
