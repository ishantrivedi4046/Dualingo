package com.example.dualingo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class phrases_fragment extends Fragment {

    public phrases_fragment() {
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
        // Inflate the layout for this fragment
        View rootview= inflater.inflate(R.layout.global_wordlist, container, false);
        final List<word> l = new ArrayList<>();
        l.add(new word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        l.add(new word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        l.add(new word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        l.add(new word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        l.add(new word("I’m feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        l.add(new word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        l.add(new word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        l.add(new word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        l.add(new word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        l.add(new word("Come here.", "әnni'nem", R.raw.phrase_come_here));
        ListView lv = (ListView) rootview.findViewById(R.id.list);
        word_adapter wd = new word_adapter(getActivity(), l, R.color.category_phrases);
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

        return rootview;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
