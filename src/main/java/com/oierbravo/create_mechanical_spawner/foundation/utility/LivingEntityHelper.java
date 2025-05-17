package com.oierbravo.create_mechanical_spawner.foundation.utility;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.neoforged.neoforge.common.util.FakePlayer;

import java.util.List;
import java.util.Optional;

public class LivingEntityHelper {
    public static void spawnLivingEntity(Level level, ResourceKey<EntityType<?>> entityKey, BlockPos pos) {
        if(level.isClientSide){
            return;
        }
        if(entityKey == null){
            spawnRandomLivingEntity(level, pos);
            return;
        }

        EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(entityKey);
        Entity entitySpawn;
        try {
            entitySpawn = entity.create(level);
        } catch (Exception exception) {
            CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
            return;
        }
        assert entitySpawn != null;
        entitySpawn.moveTo( (double)pos.getX() + 0.51, pos.getY(), (double)pos.getZ() + 0.51, level.getRandom().nextFloat() * 360.0F, 0.0F);
        if (!(entitySpawn instanceof Mob mob)) {
            return;
        }


        if (mob.checkSpawnObstruction(level)) {
            level.addFreshEntity(mob);
        }

    }

    public static void spawnRandomLivingEntity(Level level, BlockPos pos){
        Optional<MobSpawnSettings.SpawnerData> spawn = level.getBiome(pos).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(level.getRandom());
        if(spawn.isPresent()){
            SpawnGroupData spawngroupdata = null;

            Entity entity;
            try {
                entity = spawn.get().type.create(level);
            } catch (Exception exception) {
                CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
                return;
            }
            assert entity != null;
            entity.moveTo( (double)pos.getX() + 0.51, pos.getY(), (double)pos.getZ() + 0.51, level.getRandom().nextFloat() * 360.0F, 0.0F);
            if (!(entity instanceof Mob mob)) {
                return;
            }
            //if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)pos.getX() + 0.51, pos.getY()+ 0.51, (double)pos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return;
            //if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
            if (mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
            }
        }
    }

    public static Entity createEntity(ServerLevel pLevel, ResourceKey<EntityType<?>> pEntityKey, BlockPos pPos){

        if(pEntityKey == null){
            return createRandomEntity(pLevel, pPos);
        }
        EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(pEntityKey);

        try {
            return entityType.create(pLevel);
        } catch (Exception exception) {
            CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
            return null;
        }
    }
    private static Entity createRandomEntity(ServerLevel pLevel, BlockPos pPos){
        Optional<MobSpawnSettings.SpawnerData> spawn = pLevel.getBiome(pPos).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(pLevel.getRandom());
        try {
            return spawn.get().type.create(pLevel);
        } catch (Exception exception) {
            CreateMechanicalSpawner.LOGGER.warn("Failed to create random mob", (Throwable)exception);
            return null;
        }
    }
    public static List<ItemStack> getLootFromMob(ServerLevel pLevel,Entity entity,  BlockPos pSpawnPos, DeployerFakePlayer pFakePlayer, int lootingLevel){
        if (!(entity instanceof Mob mob))
            return List.of();

        ResourceLocation resourceLocation = mob.getLootTable().location();
        ResourceKey<LootTable> lootTableKey = ResourceKey.create(Registries.LOOT_TABLE, resourceLocation);

        FakePlayer fakePlayer = new DeployerFakePlayer(pLevel, pFakePlayer.getUUID());
        ItemStack fakeSword = new ItemStack(Items.DIAMOND_SWORD);
        if(lootingLevel > 0)
            fakeSword.enchant(pLevel.holderOrThrow(Enchantments.LOOTING), lootingLevel);
        fakePlayer.getInventory().add(fakePlayer.getInventory().selected, fakeSword);

        DamageSource damageSource = pLevel.damageSources().playerAttack(fakePlayer);

        LootParams.Builder builder = new LootParams.Builder(pLevel);
        builder.withParameter(LootContextParams.ORIGIN, pSpawnPos.getCenter());
        builder.withLuck(3)
                .withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withParameter(LootContextParams.TOOL, fakeSword)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, fakePlayer)
                .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());
        builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer);

        LootParams params = builder.create(LootContextParamSet.builder().build());

        LootTable table = pLevel.getServer().reloadableRegistries().getLootTable(lootTableKey);
        return table.getRandomItems(params);
    }

}
