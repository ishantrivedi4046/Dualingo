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


public class number_fragment extends Fragment {

    public number_fragment() {
        // Required empty public constructor
    }

    private MediaPlayer mp;
    private AudioManager am;

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            mp.release();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View rootView=inflater.inflate(R.layout.global_wordlist,container,false);
        final List<word> words = new ArrayList<word>();
        words.add(new word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new word("Two", "Otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new word("Three", "Tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new word("Four", "Oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new word("Five", "Massoka", R.drawable.number_five, R.raw.number_five));
        words.add(new word("Six", "Temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new word("Seven", "Kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new word("Eight", "Kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new word("Nine", "Wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new word("Ten", "Na'aacha", R.drawable.number_ten, R.raw.number_ten));
        ListView lv = (ListView) rootView.findViewById(R.id.list);
        word_adapter wd = new word_adapter(getActivity(),words,R.color.category_numbers);
        lv.setAdapter(wd);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(audioOnChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mp = MediaPlayer.create(getActivity(), words.get(position).getMediaResource());
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                    {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

      return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
