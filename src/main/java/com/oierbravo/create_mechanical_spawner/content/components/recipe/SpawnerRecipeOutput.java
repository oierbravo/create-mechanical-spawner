package com.oierbravo.create_mechanical_spawner.content.components.recipe;

import com.oierbravo.create_mechanical_spawner.ModConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

import javax.annotation.Nullable;

public class SpawnerRecipeOutput {
    @Nullable
    private ResourceKey<EntityType<?>> mob;
    public static String RANDOM_KEY = "random";
    public static ResourceLocation RANDOM_RESOURCE_LOCATION = ModConstants.asResource(RANDOM_KEY);
    SpawnerRecipeOutput(ResourceKey<EntityType<?>> mobEntity){
        this.mob = mobEntity;
    }

    public static final SpawnerRecipeOutput EMPTY =  new SpawnerRecipeOutput();

    public SpawnerRecipeOutput() {
        this.mob = null;
    }

    /*public static SpawnerRecipeOutput of(ResourceKey<EntityType<?>> mobEntity){
        return new SpawnerRecipeOutput(mobEntity);
    }*/
    public static SpawnerRecipeOutput of(EntityType<?> mobEntity){
        //return of(mobEntity.builtInRegistryHolder().value());
        return new SpawnerRecipeOutput(mobEntity.builtInRegistryHolder().key());
    }
    public static SpawnerRecipeOutput of(String id){
        return of(ResourceLocation.parse(id));
    }
    public static SpawnerRecipeOutput of(ResourceLocation resourceLocation){
        if(resourceLocation.compareTo(RANDOM_RESOURCE_LOCATION) == 0)
            return SpawnerRecipeOutput.EMPTY;
        return of(BuiltInRegistries.ENTITY_TYPE.get(resourceLocation));
    }

    public ResourceKey<EntityType<?>> getMob(){
        return this.mob;
    }
}
