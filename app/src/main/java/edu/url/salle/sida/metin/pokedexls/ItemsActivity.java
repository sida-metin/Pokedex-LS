package edu.url.salle.sida.metin.pokedexls;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemsActivity extends AppCompatActivity {

    private ListView selectedBallListView;

    private TextView textView;
    private ArrayAdapter<String> inventoryAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        ListView inventoryListView = findViewById(R.id.inventory_list_view);
        User user = User.getInstance(this);
        Set<String> inventory = user.getInventory();
        inventoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(inventory));
        inventoryListView.setAdapter(inventoryAdapter);



        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        SharedPreferences sharedPreferences = getSharedPreferences("SelectedItems", MODE_PRIVATE);
        String selectedBall = sharedPreferences.getString("selectedBall", "No ball selected");

        selectedBallListView = findViewById(R.id.selected_ball_text_view);

        List<String> selectedBalls = new ArrayList<>();
        selectedBalls.add(selectedBall);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedBalls);
        selectedBallListView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInventory();
    }

    private void updateInventory() {
        User user = User.getInstance(this);
        Set<String> inventory = user.getInventory();
        inventoryAdapter.clear();
        inventoryAdapter.addAll(inventory);
        inventoryAdapter.notifyDataSetChanged();
    }
}