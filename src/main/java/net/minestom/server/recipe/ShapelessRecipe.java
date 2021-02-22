package net.minestom.server.recipe;

import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public abstract class ShapelessRecipe extends Recipe {
    private String group;
    private final List<Ingredient> ingredients;
    private ItemStack result;

    protected ShapelessRecipe(
            @NotNull String recipeId,
            @NotNull String group,
            @Nullable List<Ingredient> ingredients,
            @NotNull ItemStack result
    ) {
        super(RecipeType.SHAPELESS, recipeId);
        this.group = group;
        this.ingredients = Objects.requireNonNullElseGet(ingredients, LinkedList::new);
        this.result = result;
    }

    @NotNull
    public String getGroup() {
        return group;
    }

    public void setGroup(@NotNull String group) {
        this.group = group;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    @NotNull
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @NotNull
    public ItemStack getResult() {
        return result;
    }

    public void setResult(@NotNull ItemStack result) {
        this.result = result;
    }
}
