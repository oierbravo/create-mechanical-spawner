package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.speedController.SpeedControllerBlockEntity;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnerBlockEntity extends KineticBlockEntity {
    ScrollValueBehaviour range;
    protected FluidTank fluidTank;

    protected LazyOptional<IFluidHandler> fluidCapability;
    public int timer;
    protected int totalTime;

    private SpawnerRecipe lastRecipe;
    private int processingTime;


    public SpawnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidTank = createFluidTank();
        fluidCapability = LazyOptional.of(() -> fluidTank);
        processingTime = 200;
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        int max = 254;
        range = new KineticScrollValueBehaviour(Lang.translateDirect("generic.range"), this, new CenteredSideValueBoxTransform());
        //range.requiresWrench();
        range.between(1, max);
        range
                .withClientCallback(
                        i -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SpawnerPointDisplay.display(this)));
        range.value = max / 2;


        behaviours.add(range);
    }
    public static int FLUID_CAPACITY = 2000;

    protected SmartFluidTank createFluidTank() {
        return new SmartFluidTank(getCapacityMultiplier(), this::onFluidStackChanged);
    }
    protected void onFluidStackChanged(FluidStack newFluidStack) {
        if (!hasLevel())
            return;

        if (!level.isClientSide) {
            setChanged();
            sendData();
        }
    }
    public static int getCapacityMultiplier() {
        return SpawnerConfig.SPAWNER_LIQUID_CAPACITY.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpeed() == 0)
            return;

        if (timer > 0) {
            timer -= getProcessingSpeed();

            assert level != null;
            if (level.isClientSide) {
                spawnParticles();
                return;
            }
            if (timer <= 0)
                process();
            return;
        }

        if (fluidTank.isEmpty())
            return;

        SpawnerRecipe.SpawnerRecipeWrapper recipeWrapper = new SpawnerRecipe.SpawnerRecipeWrapper(fluidTank.getFluid());
        assert level != null;
        if (lastRecipe == null || !lastRecipe.matches(recipeWrapper, level)) {

            Optional<SpawnerRecipe> recipe = ModRecipes.findSpawner( fluidTank.getFluid(), level);
            //Optional<SpawnerRecipe> recipe = ModRecipes.findSpawner( recipeWrapper, level);
            if (recipe.isEmpty()) {
                timer = 100;
                totalTime = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingTime();
                totalTime =  lastRecipe.getProcessingTime();
                sendData();
            }
            return;
        }

        timer = lastRecipe.getProcessingTime();
        totalTime =  lastRecipe.getProcessingTime();
        sendData();
    }


    private void process() {

        SpawnerRecipe.SpawnerRecipeWrapper recipeWrapper =  new SpawnerRecipe.SpawnerRecipeWrapper(fluidTank.getFluid());

        if (lastRecipe == null || !lastRecipe.matches(recipeWrapper, level)) {
            Optional<SpawnerRecipe> recipe = ModRecipes.findSpawner(fluidTank.getFluid(), level);
            if (!recipe.isPresent())
                return;
            lastRecipe = recipe.get();
        }
        if (!lastRecipe.matches(recipeWrapper, level))
                return;
        if(lastRecipe.getFluidAmount() > fluidTank.getFluidAmount())
            return;

        fluidTank.drain(lastRecipe.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);

        BlockPos spawnPos = getBlockPos().relative(Direction.Axis.Y, range.getValue());

        spawnLivingEntity(level,lastRecipe.getMob(), spawnPos );
        sendData();
        setChanged();
    }
    public int getProcessingSpeed() {
        return Mth.clamp((int) Math.abs(getSpeed() / 16f), 1, 512);
    }
    public void spawnParticles() {
        //ToDo:
        return;
    }
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (isFluidHandlerCap(cap))
            return fluidCapability.cast();
        return super.getCapability(cap, side);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("TankContent", fluidTank.writeToNBT(new CompoundTag()));
        compound.putInt("Timer", timer);
        compound.putInt("TotalTime", totalTime);


    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        fluidTank.readFromNBT(compound.getCompound("TankContent"));
        timer = compound.getInt("Timer");
        totalTime = compound.getInt("TotalTime");


    }

    public int getRange() {
        return range.getValue();
    }

    public List<BlockPos> getSpawnBlockPosition(Direction forcedMovement, boolean visualize) {
        List<BlockPos> positions = new ArrayList<>();

        int position = visualize ? range.getValue() : getRange();

        BlockPos current = worldPosition.relative(Direction.Axis.Y, position);


        positions.add(current);


        return positions;
    }

    public List<SpawnerBlockEntity> collectSpawnGroup() {
        return new ArrayList<>();
    }
    protected static void spawnLivingEntity(Level level, EntityType<?>  entity,BlockPos pos) {
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
        if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)pos.getX() + 0.51, pos.getY()+ 0.51, (double)pos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return;
        if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
            level.addFreshEntity(mob);
        }

    }
    protected static void spawnRandomLivingEntity(Level level, BlockPos pos){
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
            if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)pos.getX() + 0.51, pos.getY()+ 0.51, (double)pos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return;
            //For now: unrestricted spawn
            //if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
            //}
        }
    }
    protected static boolean spawnLivingEntity(SpawnerBlockEntity SpawnerBlockEntity){
        assert SpawnerBlockEntity.level != null && !SpawnerBlockEntity.level.isClientSide;

        int offset = SpawnerBlockEntity.getRange() ;

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
            if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)currentSpawnPos.getX() + 0.51, currentSpawnPos.getY()+ 0.51, (double)currentSpawnPos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return false;
            if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
                return true;
            }
        }
        return false;
    }
}
