package net.minestom.server.recipe;

import net.minestom.server.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class CampfireCookingRecipe extends Recipe {
    private String group;
    private Ingredient ingredient;
    private ItemStack result;
    private float experience;
    private int cookingTime;

    protected CampfireCookingRecipe(
            @NotNull String recipeId,
            @NotNull String group,
            @NotNull ItemStack result,
            float experience,
            int cookingTime
    ) {
        super(RecipeType.CAMPFIRE_COOKING, recipeId);
        this.group = group;
        this.result = result;
        this.experience = experience;
        this.cookingTime = cookingTime;
    }

    @NotNull
    public String getGroup() {
        return group;
    }

    public void setGroup(@NotNull String group) {
        this.group = group;
    }

    @NotNull
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(@NotNull Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @NotNull
    public ItemStack getResult() {
        return result;
    }

    public void setResult(@NotNull ItemStack result) {
        this.result = result;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(int cookingTime) {
        this.cookingTime = cookingTime;
    }
}
