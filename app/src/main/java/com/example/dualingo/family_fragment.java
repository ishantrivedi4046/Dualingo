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

public class family_fragment extends Fragment {

    public family_fragment() {
        // Required empty public constructor
    }
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.global_wordlist,container,false);
        final List<word> l = new ArrayList<>();
        l.add(new word("Father", "әpә", R.drawable.family_father, R.raw.family_father));
        l.add(new word("Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        l.add(new word("Son", "angsi", R.drawable.family_son, R.raw.family_son));
        l.add(new word("Daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        l.add(new word("Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        l.add(new word("Younger Brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        l.add(new word("Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        l.add(new word("Younger Sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        l.add(new word("GrandMother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        l.add(new word("GrandFather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));
        ListView lv = (ListView) rootview.findViewById(R.id.list);
        am = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        word_adapter wd = new word_adapter(getActivity(), l, R.color.category_family);
        lv.setAdapter(wd);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(audioOnChange, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
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
        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
