package com.oierbravo.create_mechanical_spawner.content.components;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
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
        ResourceLocation mobResourceLocation = new ResourceLocation(mob);
        EntityType<?> mobEntity = ForgeRegistries.ENTITY_TYPES.getValue(mobResourceLocation);
        if(mobEntity == null)
            return new SpawnerRecipeOutput();
        return new SpawnerRecipeOutput(mobEntity);
    }
    public static SpawnerRecipeOutput fromNetwork(FriendlyByteBuf buffer) {
        ResourceLocation mobResourceLocation = new ResourceLocation(buffer.readUtf());
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
        if(this.mob == null)
            return;
        ResourceLocation mobResourceLocation = ForgeRegistries.ENTITY_TYPES.getKey(mob);
        assert mobResourceLocation != null;
        buffer.writeUtf(mobResourceLocation.toString());
    }

    public EntityType<?> getMob(){

        return this.mob;
    }


}
