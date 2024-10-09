package com.oierbravo.create_mechanical_spawner.foundation.utility;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.common.util.FakePlayer;

import java.util.List;
import java.util.Optional;

public class LivingEntityHelper {
    public static void spawnLivingEntity(Level level, EntityType<?> entity, BlockPos pos) {
        if(level.isClientSide){
            return;
        }

        if(entity == null ) {
            spawnRandomLivingEntity(level, pos);
            return;
        }
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
    /*public static boolean spawnLivingEntity(SpawnerBlockEntity SpawnerBlockEntity){
        assert SpawnerBlockEntity.level != null && !SpawnerBlockEntity.level.isClientSide;

        int offset = SpawnerBlockEntity.getScrollValueBehaviour() ;

        BlockPos currentSpawnPos = SpawnerBlockEntity.getBlockPos().relative(Direction.Axis.Y, offset);

        Level level = SpawnerBlockEntity.getLevel();
        Optional<MobSpawnSettings.SpawnerData> spawn = SpawnerBlockEntity.level.getBiome(SpawnerBlockEntity.getBlockPos()).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(level.getRandom());
        if(spawn.isPresent()){
            SpawnGroupData spawngroupdata = null;

            Entity entity;
            try {
                entity = spawn.get().type.create(level);
            } catch (Exception exception) {
                CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
                return false;
            }
            assert entity != null;
            entity.moveTo( (double)currentSpawnPos.getX() + 0.51, currentSpawnPos.getY(), (double)currentSpawnPos.getZ() + 0.51, level.getRandom().nextFloat() * 360.0F, 0.0F);
            if (!(entity instanceof Mob mob)) {
                return false;
            }

            //if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)currentSpawnPos.getX() + 0.51, currentSpawnPos.getY()+ 0.51, (double)currentSpawnPos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return false;
            if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
                return true;
            }
        }
        return false;
    }*/
    public static Entity createEntity(ServerLevel pLevel, EntityType<?> pEntityType, BlockPos pPos){
        Entity entity;
        if(pEntityType == null){
            Optional<MobSpawnSettings.SpawnerData> spawn = pLevel.getBiome(pPos).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(pLevel.getRandom());
            try {
                entity = spawn.get().type.create(pLevel);
                return entity;
            } catch (Exception exception) {
                CreateMechanicalSpawner.LOGGER.warn("Failed to create random mob", (Throwable)exception);
                return null;
            }
        }
        try {
            entity = pEntityType.create(pLevel);
            return entity;
        } catch (Exception exception) {
            CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
            return null;
        }
    }
    public static List<ItemStack> getLootFromMob(ServerLevel pLevel,Entity entity,  BlockPos pSpawnPos, DeployerFakePlayer pFakePlayer){
        if (!(entity instanceof Mob mob))
            return List.of();

        ResourceLocation resourceLocation = mob.getLootTable();

        FakePlayer fakePlayer = new DeployerFakePlayer(pLevel, pFakePlayer.getUUID());
        DamageSource damageSource = pLevel.damageSources().playerAttack(fakePlayer);

        LootParams.Builder builder = new LootParams.Builder(pLevel);
        builder.withParameter(LootContextParams.ORIGIN, pSpawnPos.getCenter());
        //builder.withLuck(fakePlayer.getLuck())
        builder.withLuck(3)
                .withParameter(LootContextParams.THIS_ENTITY, entity).withParameter(LootContextParams.ORIGIN, entity.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, fakePlayer)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
        builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer);

        LootParams params = builder.create(LootContextParamSet.builder().build());

        LootTable table = pLevel.getServer().getLootData().getLootTable(resourceLocation);

        return table.getRandomItems(params);
    }

}
