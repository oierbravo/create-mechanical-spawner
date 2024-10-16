package com.oierbravo.create_mechanical_spawner.registrate;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE, CreateMechanicalSpawner.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CreateMechanicalSpawner.MODID);

    public static final RegistryObject<RecipeSerializer<SpawnerRecipe>> SPAWNER_SERIALIZER =
            SERIALIZERS.register("spawner", () -> SpawnerRecipe.Serializer.INSTANCE);



    public static final RegistryObject<RecipeType<SpawnerRecipe>> SPAWNER_TYPE = RECIPE_TYPES.register("sieve", () -> RecipeType.simple(CreateMechanicalSpawner.asResource(SpawnerRecipe.Type.ID)));
    public static void register(IEventBus eventBus) {

        SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
    public static Optional<SpawnerRecipe> findSpawner(SimpleContainer inv, Level level){
        return  level.getRecipeManager().getRecipeFor(SpawnerRecipe.Type.INSTANCE,inv, level);
    }
    public static Optional<SpawnerRecipe> findSpawner(FluidStack fluidStack,Level level){
        if(level.isClientSide())
            return Optional.empty();

        return level.getRecipeManager()
                .getAllRecipesFor(SpawnerRecipe.Type.INSTANCE) // Gets all recipes
                .stream() // Looks through all recipes for types
                .filter(recipe -> recipe.matches(fluidStack)) // Checks if the recipe inputs are valid
                .findFirst(); // Finds the first recipe whose inputs match
    }
}