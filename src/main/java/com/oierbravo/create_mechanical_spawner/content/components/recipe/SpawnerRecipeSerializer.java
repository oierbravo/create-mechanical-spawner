package com.oierbravo.create_mechanical_spawner.content.components.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.create_mechanical_spawner.ModConstants;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecBuilders;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.fluids.crafting.SizedFluidIngredient;

import java.util.List;

public class SpawnerRecipeSerializer implements RecipeSerializer<SpawnerRecipe>  {
    public static final SpawnerRecipeSerializer INSTANCE = new SpawnerRecipeSerializer();

    public final StreamCodec<RegistryFriendlyByteBuf, SpawnerRecipe> STREAM_CODEC = StreamCodec.of(this::toNetwork, this::fromNetwork);

    private SpawnerRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
        SizedFluidIngredient input = SizedFluidIngredient.STREAM_CODEC.decode(buffer);
        ResourceLocation output = ResourceLocation.STREAM_CODEC.decode(buffer);
        NonNullList<ProcessingOutput> customLoot = CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).decode(buffer);
        int processingTime = ByteBufCodecs.VAR_INT.decode(buffer);
        List<IRecipeRequirement> recipeRequirements = IRecipeRequirement.LIST_STREAM_CODEC.decode(buffer);

        return new SpawnerRecipeBuilder()
                .require(input)
                .output(output)
                .withCustomLoot(customLoot)
                .processingTime(processingTime)
                .withRequirements(recipeRequirements)
                .build();
    }

    private void toNetwork(RegistryFriendlyByteBuf buffer, SpawnerRecipe spawnerRecipe) {
        SizedFluidIngredient.STREAM_CODEC.encode(buffer, spawnerRecipe.getFluidIngredient());
        ResourceLocation.STREAM_CODEC.encode(buffer, spawnerRecipe.getMobResourceLocation());
        CatnipStreamCodecBuilders.nonNullList(ProcessingOutput.STREAM_CODEC).encode(buffer, spawnerRecipe.getCustomLoot());
        ByteBufCodecs.VAR_INT.encode(buffer, spawnerRecipe.getProcessingTime());
        IRecipeRequirement.LIST_STREAM_CODEC.encode(buffer, spawnerRecipe.getRecipeRequirements());
    }

    public static final MapCodec<SpawnerRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(
                            SizedFluidIngredient.FLAT_CODEC.fieldOf("input").forGetter(SpawnerRecipe::getInput),
                            ResourceLocation.CODEC.optionalFieldOf("output", ModConstants.asResource("random")).forGetter(SpawnerRecipe::getMobResourceLocation),
                            NonNullList.codecOf(ProcessingOutput.CODEC_NEW).optionalFieldOf("customLoot", NonNullList.create()).forGetter(SpawnerRecipe::getCustomLoot),
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("processingTime", 0).forGetter(SpawnerRecipe::getProcessingTime),
                            IRecipeRequirement.LIST_CODEC.optionalFieldOf("requirements", List.of()).forGetter(SpawnerRecipe::getRecipeRequirements),
                            ICondition.LIST_CODEC.optionalFieldOf(ConditionalOps.DEFAULT_CONDITIONS_KEY, List.of()).forGetter(SpawnerRecipe::getConditions)
                    ).apply(instance, (input, output, customLoot, processingTime, requirements, iConditions) -> {

                        return new SpawnerRecipeBuilder()
                                .require(input)
                                .output(output)
                                .processingTime(processingTime)
                                .withCustomLoot(customLoot)
                                .withRequirements(requirements)
                                .withConditions(iConditions)
                                .build();
                    })
    );
    @Override
    public MapCodec<SpawnerRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SpawnerRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
