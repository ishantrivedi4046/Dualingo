package com.example.dualingo;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class color_fragment extends Fragment {
    private MediaPlayer mp;
    private AudioManager am;
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
            am.abandonAudioFocus(audioOnChange);
        }
    }
    private AudioManager.OnAudioFocusChangeListener audioOnChange = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                mp.pause();
                mp.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mp.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();

        }
    };
    public color_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootview=inflater.inflate(R.layout.global_wordlist,container,false);
        final List<word> l = new ArrayList<>();
        l.add(new word("Red", "Weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        l.add(new word("Green", "Chokokki", R.drawable.color_green, R.raw.color_green));
        l.add(new word("Brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        l.add(new word("Gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        l.add(new word("Black", "Kululli", R.drawable.color_black, R.raw.color_black));
        l.add(new word("White", "Kelelli", R.drawable.color_white, R.raw.color_white));
        l.add(new word("Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        l.add(new word("Mustard Yellow", "Chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        ListView lv = (ListView)rootview.findViewById(R.id.list);
        word_adapter wd = new word_adapter(getActivity(), l, R.color.category_colors);
        lv.setAdapter(wd);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(audioOnChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getActivity(), l.get(position).getMediaResource());
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
       return  rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
