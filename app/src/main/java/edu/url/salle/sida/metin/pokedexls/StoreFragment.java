package edu.url.salle.sida.metin.pokedexls;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class StoreFragment extends Fragment {

    public StoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment, container, false);

        Button buyPokeballButton = view.findViewById(R.id.buy_pokeball_button);
        Button buySuperballButton = view.findViewById(R.id.buy_superball_button);
        Button buyUltraballButton = view.findViewById(R.id.buy_ultraball_button);
        Button buyMasterballButton = view.findViewById(R.id.buy_masterball_button);

        buyPokeballButton.setOnClickListener(v -> purchaseItem("Pokeball", 200));
        buySuperballButton.setOnClickListener(v -> purchaseItem("Superball", 500));
        buyUltraballButton.setOnClickListener(v -> purchaseItem("Ultraball", 1500));
        buyMasterballButton.setOnClickListener(v -> purchaseItem("Masterball", 100000));

        return view;
    }

    private void purchaseItem(String itemName, int itemPrice) {
        User user = User.getInstance(getActivity());
        if (user.getMoney() >= itemPrice) {
            user.buyItem(itemName, itemPrice);
            user.setMoney(user.getMoney() - itemPrice);
            Toast.makeText(getActivity(), itemName + " purchased for " + itemPrice, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Not enough money to purchase " + itemName, Toast.LENGTH_SHORT).show();
        }
    }
}
