package net.minestom.server.recipe;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class RecipeManager {
    private final Set<Recipe> recipes = new CopyOnWriteArraySet<>();

    public void addRecipe(@NotNull Recipe recipe) {
        if (this.recipes.add(recipe)) {
            // TODO add to all players
        }
    }

    public void removeRecipe(@NotNull Recipe recipe) {
        if (this.recipes.remove(recipe)) {
            // TODO remove to all players
        }
    }

    @NotNull
    public Set<Recipe> getRecipes() {
        return recipes;
    }
}
