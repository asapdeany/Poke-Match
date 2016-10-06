package com.example.deansponholz.poke_match;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by deansponholz on 10/1/16.
 */

public class GameplayFragment extends Fragment{

    //Instance Data
    int clickCount;


    private float myVolume = 1f;
    SoundPool soundPool = null;
    int correctAudioSound = 0;
    int incorrectAudioSound = 0;
    int cardFlipSound = 0;
    int clapSound = 0;

    private final static int totalTiles = 16;
    private final static int imagesNeeded = 8;


    private ArrayList<Integer> cardsBefore;
    private static ArrayList<Integer> imageBank;
    private static ArrayList<Integer> chosenImages;
    private int[] chosenCards = new int[totalTiles];


    private ImageButton firstTile;
    private int firstTilePosition;
    private  int firstTileArrayPosiion;
    private int cardMatchCount;
    private Handler progressHandler = null;

    @RequiresApi(api = Build.VERSION_CODES.M)



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        //intialize Game
        initGame();
        //intialize sounds
        this.soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        this.correctAudioSound = this.soundPool.load(getContext(), R.raw.pikanoise, 1);
        this.incorrectAudioSound = this.soundPool.load(getContext(), R.raw.tryagain, 1);
        this.cardFlipSound = this.soundPool.load(getContext(), R.raw.cardflip, 1);
        this.clapSound = this.soundPool.load(getContext(), R.raw.clap, 1);
        //initialize handler
        progressHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_game, container, false);
        //set to true otherwise false
        boolean gameRestarted = (savedInstanceState == null)?true:false;
        initView(root, gameRestarted);
        return root;
    }

    public void initGame(){
        loadImageBank();
        pickImages();
    }

    private void loadImageBank(){
        imageBank = new ArrayList<>();
        imageBank.add(R.drawable.bulbasaur);
        imageBank.add(R.drawable.chansey);
        imageBank.add(R.drawable.charmander);
        imageBank.add(R.drawable.dragonite);
        imageBank.add(R.drawable.drowzee);
        imageBank.add(R.drawable.exeggutor);
        imageBank.add(R.drawable.golbat);
        imageBank.add(R.drawable.grimer);
        imageBank.add(R.drawable.horsea);
        imageBank.add(R.drawable.jigglypuff);
        imageBank.add(R.drawable.machop);
        imageBank.add(R.drawable.oddish);
        imageBank.add(R.drawable.pikachu);
        imageBank.add(R.drawable.poliwag);
        imageBank.add(R.drawable.psyduck);
        imageBank.add(R.drawable.raticate);
        imageBank.add(R.drawable.slowbro);
        imageBank.add(R.drawable.squirtle);

    }

    private void pickImages() {

        chosenImages = new ArrayList<>();
        Random imageR = new Random();
        for (int i = 0; i < imagesNeeded; i++) {
            int pickedImagePosition = imageR.nextInt(18 - i);
            Integer picked = imageBank.remove(pickedImagePosition);
            chosenImages.add(picked);
        }

        ArrayList<Integer> ImgPosForCards = new ArrayList<>();
        for (int j=0;j<imagesNeeded;j++){
            ImgPosForCards.add(new Integer(j));
            ImgPosForCards.add(new Integer(j));
        }

        for (int position=0;position<totalTiles;position++){
            int pickedPos = imageR.nextInt(ImgPosForCards.size());
            int ImgPos = ImgPosForCards.get(pickedPos);
            ImgPosForCards.remove(pickedPos);
            chosenCards[position]=ImgPos;
        }
    }

    private void initView(View rootView, boolean gameRestarted){

        if (gameRestarted == true){
            clickCount = 0;
            cardMatchCount = 0;
            cardsBefore = new ArrayList<>();
        }

        Resources res = getResources();


        for (int i = 0; i < totalTiles; i++) {
            final int iterator = i;
            int id = res.getIdentifier(
                    "t" + i, "id", getActivity().getPackageName());

            final ImageButton ib = (ImageButton) rootView.findViewById(id);

            if (gameRestarted == true){
                ib.setImageDrawable(null);
            }else {
                if (cardsBefore.indexOf(new Integer(i)) > -1) {
                    ib.setImageResource(chosenImages.get(chosenCards[i]));
                }
            }
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((ImageView)view).getDrawable()!=null){
                        return;
                    }else{
                        clickCount++;
                        ((GameplayActivity)getActivity()).updateClickScore(clickCount);
                        int imageLocation = chosenCards[iterator];
                        final int imageId = (chosenImages.get(imageLocation)).intValue();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Drawable backgroundImg = getResources().getDrawable(imageId);
                                ib.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        ib.setImageDrawable(backgroundImg);
                                    }
                                });
                            }
                        }).start();

                        //odd
                        if (clickCount % 2 != 0){

                            firstTile = ib;
                            firstTilePosition = imageLocation;
                            firstTileArrayPosiion = iterator;
                        }

                        else{

                            if (firstTilePosition == imageLocation){
                                cardsBefore.add(firstTileArrayPosiion);
                                cardsBefore.add(iterator);
                                cardMatchCount +=2;
                                soundPool.play(correctAudioSound, myVolume, myVolume, 1, 0, 1f);
                                if (cardMatchCount == totalTiles){
                                    soundPool.play(clapSound, myVolume, myVolume, 1, 0, 1f);
                                    ((GameplayActivity)getActivity()).playerWin();
                                }
                            }

                            else{
                                ((GameplayActivity)getActivity()).progressBegin();
                                soundPool.play(incorrectAudioSound, myVolume, myVolume, 1, 0, 1f);
                                progressHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        firstTile.setImageDrawable(null);
                                        ib.setImageDrawable(null);
                                        ((GameplayActivity)getActivity()).progressEnd();
                                    }
                                }, 1000);

                            }
                        }

                    }

                }
            });
        }
    }
    public void restartGame() {
        initGame();
        initView(getView(), true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.progressHandler.removeCallbacksAndMessages(null);
    }

}
