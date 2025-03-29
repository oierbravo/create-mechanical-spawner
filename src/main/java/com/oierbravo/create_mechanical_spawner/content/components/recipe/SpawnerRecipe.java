package com.oierbravo.create_mechanical_spawner.content.components.recipe;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipe;
import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeParams;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SpawnerRecipe extends AbstractMechanicalRecipe<RecipeInput, SpawnerRecipe.SpawnerRecipeParams> {
    private final FluidIngredient fluidIngredient;

    private NonNullList<ProcessingOutput> customLoot;

    private SpawnerRecipeOutput mob;
    private final int processingTime;
    public SpawnerRecipe(SpawnerRecipeParams params) {
        super(params);
        this.mob = params.mob;
        this.fluidIngredient = params.fluidIngredient;
        this.processingTime = params.processingTime;
        this.customLoot = params.customLoot;

    }

    public boolean matches(FluidStack fluidStack) {
        return this.fluidIngredient.test(fluidStack);
    }

    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return SpawnerRecipeSerializer.INSTANCE;
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public int getFluidAmount() {
        return fluidIngredient.getRequiredAmount();
    }

    public FluidIngredient getFluidIngredient() { return fluidIngredient; }

    public ResourceKey<EntityType<?>> getMob() {
        return mob.getMob();
    }

    public NonNullList<ProcessingOutput> getCustomLoot() {
        return customLoot;
    }

    public List<ItemStack> rollCustomLoot() {
        return rollCustomLoot(this.getCustomLoot());
    }

    public List<ItemStack> rollCustomLoot(List<ProcessingOutput> rollableResults) {
        List<ItemStack> results = new ArrayList<>();
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            ItemStack stack = output.rollOutput();
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    public SpawnerRecipeOutput getOutput() {
        /*if(mob == null)
            return SpawnerRecipeOutput.EMPTY;*/
        return this.mob;
    }

    @Override
    public ArrayList<IRecipeRequirement> getRecipeRequirements() {
        return recipeRequirements;
    }

    public FluidIngredient getInput() {
        return fluidIngredient;
    }

    public ResourceLocation getMobResourceLocation() {
        assert mob.getMob() != null;
        if(mob.getMob() != null)
            return mob.getMob().location();
        return ModConstants.asResource("random");
    }

    public ResourceLocation getId() {
        return id;
    }

    public static class Type implements RecipeType<SpawnerRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "spawner";
    }

    public static class SpawnerRecipeParams extends AbstractMechanicalRecipeParams {

        protected FluidIngredient fluidIngredient;
        protected SpawnerRecipeOutput mob;
        protected int processingTime;

        protected NonNullList<ProcessingOutput> customLoot;

        protected SpawnerRecipeParams(ResourceLocation id) {
            super(id);
            mob = new SpawnerRecipeOutput();
            fluidIngredient = FluidIngredient.EMPTY;
            processingTime = 200;
            customLoot = NonNullList.create();
        }

    }
}
