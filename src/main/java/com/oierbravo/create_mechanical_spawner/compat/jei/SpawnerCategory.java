package com.oierbravo.create_mechanical_spawner.compat.jei;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.create_mechanical_spawner.ModLang;
import com.oierbravo.create_mechanical_spawner.compat.jei.animations.AnimatedSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlocks;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipes;
import com.oierbravo.mechanicals.compat.jei.CreateRecipeCategoryBuilder;
import com.oierbravo.mechanicals.compat.jei.RecipeRequirementRenderer;
import com.oierbravo.mechanicals.foundation.gui.MechanicalGUITextures;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.neoforge.NeoForgeTypes;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SpawnerCategory extends CreateRecipeCategory<SpawnerRecipe> {
    private final AnimatedSpawner spawner = new AnimatedSpawner();

    @SuppressWarnings("unchecked")
    public final static CreateRecipeCategory<SpawnerRecipe> INFO = CreateRecipeCategoryBuilder
            .builder(SpawnerRecipe.class)
            .addRecipes(ModRecipes::getAllHolders)
            .catalyst(ModBlocks.MECHANICAL_SPAWNER)
            .icon(new ItemIcon(() -> new ItemStack(ModBlocks.MECHANICAL_SPAWNER.asItem())))
            .emptyBackground(177, 100)
            .build(ModConstants.asResource("spawner"), SpawnerCategory::new);



    public SpawnerCategory(Info<SpawnerRecipe> info) {
        super(info);
    }


    public void setRecipe(IRecipeLayoutBuilder builder, SpawnerRecipe recipe, IFocusGroup focuses) {
        SizedFluidIngredient fluidIngredient = recipe.getFluidIngredient();

        List<ItemStack> invisibleIngredientsBuckets = Arrays.stream(fluidIngredient.getFluids()).map(fluidStack -> new ItemStack(fluidStack.getFluid().getBucket())).toList();
        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addItemStacks(invisibleIngredientsBuckets);
        ResourceKey<EntityType<?>> mobKey = recipe.getMob();

        boolean useCustomLoot = !recipe.getCustomLoot().isEmpty() && MConfigs.server().spawner.customLootPerSpawnRecipeEnabled.get();

        if(mobKey != null && !useCustomLoot) {
            Level level = Minecraft.getInstance().level;
            EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(mobKey);
            ItemStack egg = entity.create(level).getPickResult();
            builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(egg);
        }
        if(useCustomLoot){
            List<ProcessingOutput> customLoot = recipe.getCustomLoot();
            boolean single = customLoot.size() == 1;
            int i = 0;
            for (ProcessingOutput output : customLoot) {
                int xOffset = i % 9 == 0 ? 0 : 19;
                int yOffset = 0 ;

                builder
                        .addSlot(RecipeIngredientRole.OUTPUT, 2 + xOffset * i, 75 + yOffset)
                        .setBackground(getRenderedSlot(output), -1, -1)
                        .addItemStack(output.getStack())
                        .addRichTooltipCallback(addStochasticTooltip(output));

                i++;
            }
        }

        builder
            .addSlot(RecipeIngredientRole.INPUT, 2, 2)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(NeoForgeTypes.FLUID_STACK, Arrays.asList(fluidIngredient.getFluids()))
            .addRichTooltipCallback(SpawnerCategory::addFluidAmountTooltip);
    }
    private static void addFluidAmountTooltip(IRecipeSlotView recipeSlotView, ITooltipBuilder tooltip){
        Optional<FluidStack> displayed = recipeSlotView.getDisplayedIngredient(NeoForgeTypes.FLUID_STACK);
        if (displayed.isEmpty())
            return;

        FluidStack fluidStack = displayed.get();
        tooltip.add(Component.literal(fluidStack.getAmount() + "mB"));
    }
    public void draw(SpawnerRecipe recipe, @NotNull IRecipeSlotsView iRecipeSlotsView, @NotNull GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;

        MechanicalGUITextures.JEI_DOWN_RIGHT_ARROW.render(guiGraphics, 6, 25);
        spawner.draw(guiGraphics, 30, 30);
        Level level = Minecraft.getInstance().level;


        ResourceKey<EntityType<?>> mobKey = recipe.getMob();

        boolean useCustomLoot = !recipe.getCustomLoot().isEmpty() && MConfigs.server().spawner.customLootPerSpawnRecipeEnabled.get();


        if(mobKey == null) {
            String text = ModLang.translate("generic.biome_dependant").string();// "Biome dependent";
            guiGraphics.drawString(font, text, 20, 57,  8, false);
        } else {
            EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(mobKey);

            assert level != null;
            LivingEntity mobEntity = (LivingEntity) entity.create(level);
            assert mobEntity != null;
            String id = mobEntity.getEncodeId();

            assert id != null;
            RenderHelper.renderEntity(guiGraphics, 70, 50, 20.0F,
                    38 - mouseX,
                    80 - mouseY,
                    mobEntity);

            Component displayName = mobEntity.getDisplayName();
            guiGraphics.drawString(font, displayName, 20, 57, 8, false);

            if(useCustomLoot){
                String customLoottext = ModLang.translate("generic.with_custom_loot").string();
                guiGraphics.drawString(font, customLoottext, 20, 65, 8, false);
            }


        }
        RecipeRequirementRenderer.drawRequirements(recipe,guiGraphics, 85,10);

    }
}
