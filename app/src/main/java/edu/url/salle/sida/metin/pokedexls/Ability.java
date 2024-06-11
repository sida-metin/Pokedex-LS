package edu.url.salle.sida.metin.pokedexls;
import java.util.List;
import java.util.Random;

public class Ability {
    private String name;
    private boolean isHidden;

    public boolean isHidden() {
        return this.isHidden;
    }

    public Ability(String name, boolean isHidden) {
        this.name = name;
        this.isHidden = isHidden;
    }
    public static Ability chooseAbility(List<Ability> abilities) {
        Random random = new Random();
        int chance = random.nextInt(100) + 1;

        for (Ability ability : abilities) {
            if (ability.isHidden()) {
                if (chance <= 25) {
                    return ability;
                }
            } else {
                if (chance <= 50) {
                    return ability;
                }
            }
        }

        return abilities.get(random.nextInt(abilities.size()));
    }
}
