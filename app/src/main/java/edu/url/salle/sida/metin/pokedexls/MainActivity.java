package edu.url.salle.sida.metin.pokedexls;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PokedexFragment pokedexFragment = new PokedexFragment();
            fragmentTransaction.add(R.id.fragment_container, pokedexFragment);
            fragmentTransaction.commit();
        }
    }
    public void onTabBarClicked() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PokedexFragment())
                .commit();
    }
}