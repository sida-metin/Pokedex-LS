package edu.url.salle.sida.metin.pokedexls;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CoverActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cover_activity);

        final ImageView pokeballClosed = findViewById(R.id.pokeball_closed);
        final ImageView pokeballOpen = findViewById(R.id.pokeball_open);
        RelativeLayout coverLayout = findViewById(R.id.cover_layout);

        pokeballClosed.setVisibility(View.VISIBLE);
        pokeballOpen.setVisibility(View.GONE);


        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setRepeatCount(1);
        scaleAnimation.setRepeatMode(Animation.REVERSE);


        AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
        fadeOut.setDuration(500);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pokeballClosed.setVisibility(View.GONE);
                pokeballOpen.setVisibility(View.VISIBLE);
                pokeballOpen.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pokeballClosed.startAnimation(fadeOut);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(CoverActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }
}