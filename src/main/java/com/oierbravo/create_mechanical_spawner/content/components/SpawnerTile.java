package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.BlockMovementChecks;
import com.simibubi.create.content.contraptions.components.structureMovement.chassis.ChassisRangeDisplay;
import com.simibubi.create.content.contraptions.components.structureMovement.chassis.ChassisTileEntity;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.BulkScrollValueBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.scrollvalue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SpawnerTile extends KineticTileEntity {
    ScrollValueBehaviour range;
    protected FluidTank fluidTank;

    protected LazyOptional<IFluidHandler> fluidCapability;
    public int timer;
    protected int totalTime;

    //private SiftingRecipe lastRecipe;


    public SpawnerTile(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        fluidTank = createFluidTank();
        fluidCapability = LazyOptional.of(() -> fluidTank);
    }
    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        //int max = AllConfigs.SERVER.kinetics.maxChassisRange.get();
        int max = 4;
        range = new BulkScrollValueBehaviour(Lang.translateDirect("generic.range"), this, new CenteredSideValueBoxTransform(),
                te -> ((SpawnerTile) te).collectSpawnGroup());
        range.requiresWrench();
        range.between(1, max);
        range
                .withClientCallback(
                        i -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> SpawnerPointDisplay.display(this)));
        range.value = max / 2;
        behaviours.add(range);
    }
    public static int FLUID_CAPACITY = 2000;
    public int FLUID_AMOUNT_NEEDED = 1000;
    public ResourceLocation FLUID = new ResourceLocation("minecraft/lava");
    private FluidTank createFluidTankOld() {

        return new FluidTank(FLUID_CAPACITY) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    //ModMessages.sendToClients(new FluidStackSyncS2CPacket(this.fluid, worldPosition));
                }
            }

        };
    }
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
        return FLUID_CAPACITY;
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpeed() == 0)
            return;

        if (timer > 0) {
            timer -= getProcessingSpeed();

            if (level.isClientSide) {
                spawnParticles();
                return;
            }
            if (timer <= 0)
                spawnLivingEntity(this);
            return;
        }

        if (fluidTank.isEmpty())
            return;

        /*RecipeWrapper inventoryIn = new RecipeWrapper(inputAndMeshCombined);
        if (lastRecipe == null || !lastRecipe.matches(inventoryIn, level,this.isWaterlogged())) {
            Optional<SiftingRecipe> recipe = ModRecipeTypes.SIFTING.find(inventoryIn, level, this.isWaterlogged());
            if (!recipe.isPresent()) {
                timer = 100;
                totalTime = 100;
                sendData();
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingDuration();
                totalTime =  lastRecipe.getProcessingDuration();
                sendData();
            }
            return;
        }*/

        //timer = lastRecipe.getProcessingDuration();
        //totalTime =  lastRecipe.getProcessingDuration();
        timer = 200;
        totalTime =  200;
        sendData();
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

        int position = visualize ? range.scrollableValue : getRange();

        BlockPos current = worldPosition.relative(Direction.Axis.Y, position);


        positions.add(current);


        return positions;
    }

    public List<SpawnerTile> collectSpawnGroup() {
        List<SpawnerTile> collected = new ArrayList<>();
        return collected;
    }

    protected static void spawnLivingEntity(SpawnerTile spawnerTile){
        assert spawnerTile.level != null && !spawnerTile.level.isClientSide;

        int offset = spawnerTile.getRange() ;

        BlockPos currentSpawnPos = spawnerTile.getBlockPos().relative(Direction.Axis.Y, offset);

        Level level = spawnerTile.getLevel();
        Optional<MobSpawnSettings.SpawnerData> spawn = spawnerTile.level.getBiome(spawnerTile.getBlockPos()).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(level.getRandom());
        // NaturalSpawner::spawnMobsForChunkGeneration
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
            entity.moveTo(0.5, (double)currentSpawnPos.getY(), 0.5, level.getRandom().nextFloat() * 360.0F, 0.0F);
            if (!(entity instanceof Mob mob)) {
                return;
            }
            if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, 0.5, currentSpawnPos.getY(), 0.5, null, MobSpawnType.SPAWNER) == -1) return;
            if (mob.checkSpawnRules(level, MobSpawnType.SPAWNER) && mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
            }
        }
    }
}
