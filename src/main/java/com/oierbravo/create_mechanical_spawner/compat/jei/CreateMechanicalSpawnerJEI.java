package com.oierbravo.create_mechanical_spawner.compat.jei;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipes;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
@SuppressWarnings("unused")
@ParametersAreNonnullByDefault
public class CreateMechanicalSpawnerJEI implements IModPlugin {

    private static final ResourceLocation ID = ModConstants.asResource("jei_plugin");

    public IIngredientManager ingredientManager;
    private final List<CreateRecipeCategory<?>> modCategories = new ArrayList<>();


    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        CreateRecipeCategory.Factory<SpawnerRecipe> factory = SpawnerCategory::new;
        CreateRecipeCategory<SpawnerRecipe> category = factory.create(SpawnerCategory.INFO);

        registration.addRecipeCategories(category);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<SpawnerRecipe> recipes = ModRecipes.getAllHolders().stream().map(RecipeHolder::value).toList();
        registration.addRecipes(SpawnerCategory.TYPE, recipes);

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        SpawnerCategory.INFO.catalysts().forEach(supplier -> registration.addRecipeCatalyst(supplier.get(),SpawnerCategory.TYPE));
    }
}
