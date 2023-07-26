package com.oierbravo.create_mechanical_spawner.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import com.oierbravo.create_mechanical_spawner.compat.jei.animations.AnimatedSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.foundation.utility.ModLang;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class SpawnerCategory extends CreateRecipeCategory<SpawnerRecipe> {
    private final AnimatedSpawner spawner = new AnimatedSpawner();
    private final RandomMobCycleTimer randomMobCycleTimer;
    private List<LivingEntity> displayedMobs = List.of();
    private List<Optional<EntityType<?>>> allMobs = List.of();

    public SpawnerCategory(Info<SpawnerRecipe> info) {
        super(info);
        this.randomMobCycleTimer = new RandomMobCycleTimer(0);

    }


    public void setRecipe(IRecipeLayoutBuilder builder, SpawnerRecipe recipe, IFocusGroup focuses) {
        FluidIngredient fluidIngredient = recipe.getFluidIngredient();
        builder
            .addSlot(RecipeIngredientRole.INPUT, 15, 9)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(ForgeTypes.FLUID_STACK, withImprovedVisibility(fluidIngredient.getMatchingFluidStacks()))
            .addTooltipCallback(addFluidTooltip(fluidIngredient.getRequiredAmount()));

    }

    public LivingEntity getDisplayedMob() {
        return randomMobCycleTimer.getCycledLivingEntity(displayedMobs);
    }
    public void draw(SpawnerRecipe recipe, @NotNull IRecipeSlotsView iRecipeSlotsView, @NotNull PoseStack matrixStack, double mouseX, double mouseY) {
        randomMobCycleTimer.onDraw();
        Font font = Minecraft.getInstance().font;

        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 43, 4);
        spawner.draw(matrixStack, 48, 27);
        Level level = Minecraft.getInstance().level;
        EntityType<?> mob = recipe.getMob();

        if(mob != null){
            assert level != null;
            LivingEntity mobEntity = (LivingEntity) mob.create(level);

            RenderHelper.renderEntity(matrixStack, 100, 35, 20.0F,
                    38 - mouseX,
                    80 - mouseY,
                    randomMobCycleTimer.getCycledLivingEntity(List.of(mobEntity)));
            String text = mobEntity.getLootTable().toString();

            font.draw(matrixStack, text, 20, 60, 8);

            return;
        }

        String text = ModLang.translate("generic.biome_dependant").string();// "Biome dependent";
        font.draw(matrixStack, text, 80, 60, 8);
    }
}
