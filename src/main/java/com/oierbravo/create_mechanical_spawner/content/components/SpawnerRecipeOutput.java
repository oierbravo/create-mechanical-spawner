package com.oierbravo.create_mechanical_spawner.content.components;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;

public class SpawnerRecipeOutput {
    @Nullable
    private EntityType<?> mob;

    SpawnerRecipeOutput(@Nullable EntityType<?> mobEntity){
        this.mob = mobEntity;
    }

    public static final SpawnerRecipeOutput EMPTY =  new SpawnerRecipeOutput();

    public SpawnerRecipeOutput() {
        this.mob = null;
    }

    public static SpawnerRecipeOutput fromJson(String mob) {
        if(mob == null)
            return new SpawnerRecipeOutput();
        if(mob.equals("random"))
            return new SpawnerRecipeOutput();
        ResourceLocation mobResourceLocation = new ResourceLocation(mob);
        EntityType<?> mobEntity = ForgeRegistries.ENTITY_TYPES.getValue(mobResourceLocation);
        if(mobEntity == null)
            return new SpawnerRecipeOutput();
        return new SpawnerRecipeOutput(mobEntity);
    }
    public static SpawnerRecipeOutput fromNetwork(FriendlyByteBuf buffer) {
        String mobId = buffer.readUtf();
        if(mobId.equals("random"))
            return new SpawnerRecipeOutput();

        ResourceLocation mobResourceLocation = new ResourceLocation(mobId);
        EntityType<?> mobEntity = ForgeRegistries.ENTITY_TYPES.getValue(mobResourceLocation);
        if(mobEntity == null)
            return new SpawnerRecipeOutput();
        return new SpawnerRecipeOutput(mobEntity);
    }

    public String toJson() {
        assert this.mob != null;
        return this.mob.toString();
    }
    public void toNetwork(FriendlyByteBuf buffer) {
        if(this.mob == null) {
            buffer.writeUtf("random");
            return;
        }
        ResourceLocation mobResourceLocation = ForgeRegistries.ENTITY_TYPES.getKey(mob);
        assert mobResourceLocation != null;
        buffer.writeUtf(mobResourceLocation.toString());
    }

    public EntityType<?> getMob(){

        return this.mob;
    }

    public String serialize() {
        if(mob == null){
            return "";
        }
        return mob.toShortString();
    }
}
