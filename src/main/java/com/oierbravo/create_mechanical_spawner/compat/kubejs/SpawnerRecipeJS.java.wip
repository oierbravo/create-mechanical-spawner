package com.oierbravo.create_mechanical_spawner.compat.kubejs;

import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipeOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.latvian.mods.kubejs.fluid.FluidStackJS;
import dev.latvian.mods.kubejs.recipe.*;
import dev.latvian.mods.rhino.NativeJavaObject;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

public class SpawnerRecipeJS extends RecipeJS {
    public FluidIngredient fluidIngredient = FluidIngredient.EMPTY;
    SpawnerRecipeOutput output = SpawnerRecipeOutput.EMPTY;
    int processingTime;
    @Override
    public void create(RecipeArguments recipeArguments) {
        output = SpawnerRecipeOutput.fromJson(recipeArguments.get(0).toString());
        fluidIngredient = parseFluid((NativeJavaObject) recipeArguments.get(1));
        processingTime = 1000;
    }

    private FluidIngredient parseFluid(@Nullable NativeJavaObject recipeInputArgument){
        if(recipeInputArgument == null)
            return FluidIngredient.EMPTY;
        FluidStackJS fluidJS = FluidStackJS.of(recipeInputArgument.unwrap());
        return FluidIngredient.fromFluid(fluidJS.getFluid(), (int) fluidJS.getAmount());
    }
    @Override
    public void deserialize() {
        fluidIngredient = FluidIngredient.deserialize(GsonHelper.getAsJsonObject(json, "fluid"));
        output = SpawnerRecipeOutput.fromJson(GsonHelper.getAsString(json, "mob"));
        if(GsonHelper.isValidNode(json,"processingTime")){
            processingTime =  GsonHelper.getAsInt(json, "processingTime");
        }
    }

    @Override
    public void serialize() {
        if (serializeInputs) {
            json.add("fluid", fluidIngredient.serialize());
        }
        if (serializeOutputs) {
            json.addProperty("mob", output.serialize());
        }

    }
    public SpawnerRecipeJS processingTime(int processingTime) {
        json.addProperty("processingTime", processingTime);
        save();
        return this;
    }
    @Override
    public boolean hasInput(IngredientMatch ingredientMatch) {
        return false;

    }

    @Override
    public boolean replaceInput(IngredientMatch ingredientMatch, Ingredient ingredient, ItemInputTransformer itemInputTransformer) {
        return false;
    }

    @Override
    public boolean hasOutput(IngredientMatch ingredientMatch) {
        return false;
    }

    @Override
    public boolean replaceOutput(IngredientMatch ingredientMatch, ItemStack itemStack, ItemOutputTransformer itemOutputTransformer) {

        return false;
    }
}
