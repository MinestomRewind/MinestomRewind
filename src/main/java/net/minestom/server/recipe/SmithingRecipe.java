package net.minestom.server.recipe;

import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class SmithingRecipe extends Recipe {
    private Ingredient baseIngredient;
    private Ingredient additionIngredient;
    private ItemStack result;

    protected SmithingRecipe(
            @NotNull String recipeId,
            @NotNull Ingredient baseIngredient,
            @NotNull Ingredient additionIngredient,
            @NotNull ItemStack result
    ) {
        super(RecipeType.SMITHING, recipeId);
        this.baseIngredient = baseIngredient;
        this.additionIngredient = additionIngredient;
        this.result = result;
    }

    @NotNull
    public Ingredient getBaseIngredient() {
        return baseIngredient;
    }

    public void setBaseIngredient(@NotNull Ingredient baseIngredient) {
        this.baseIngredient = baseIngredient;
    }

    @NotNull
    public Ingredient getAdditionIngredient() {
        return additionIngredient;
    }

    public void setAdditionIngredient(@NotNull Ingredient additionIngredient) {
        this.additionIngredient = additionIngredient;
    }

    @NotNull
    public ItemStack getResult() {
        return result;
    }

    public void setResult(@NotNull ItemStack result) {
        this.result = result;
    }
}
