package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipeSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, ModConstants.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, ModConstants.MODID);

    public static final Supplier<SpawnerRecipeSerializer> SPAWNER_SERIALIZER =
            SERIALIZERS.register("spawner", () -> SpawnerRecipeSerializer.INSTANCE);

    public static final Supplier<RecipeType<SpawnerRecipe>> SPAWNER_TYPE = RECIPE_TYPES.register("sieve", () -> RecipeType.simple(ModConstants.asResource(SpawnerRecipe.Type.ID)));
    public static void register(IEventBus eventBus) {

        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }

    public static Optional<SpawnerRecipe> findSpawner(FluidStack fluidStack, Level level){
        if(level.isClientSide())
            return Optional.empty();

        return level.getRecipeManager()
                .getAllRecipesFor(SpawnerRecipe.Type.INSTANCE) // Gets all recipes
                .stream() // Looks through all recipes for types
                .map(RecipeHolder::value)
                .filter(recipe -> recipe.matches(fluidStack)) // Checks if the recipe inputs are valid
                .findAny(); // Finds the first recipe whose inputs match
    }
    public static List<RecipeHolder<SpawnerRecipe>> getAllHolders() {
        return Objects.requireNonNull(Minecraft.getInstance().getConnection())
                .getRecipeManager()
                .getAllRecipesFor(SpawnerRecipe.Type.INSTANCE);
    }
}