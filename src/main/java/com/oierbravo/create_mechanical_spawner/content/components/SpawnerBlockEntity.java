package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.foundation.utility.ModLang;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipeTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;


public class SpawnerBlockEntity extends KineticBlockEntity {
    public UUID owner;
    protected DeployerFakePlayer player;

    ScrollValueBehaviour range;
    protected FluidTank fluidTank = createFluidTank();;

    protected LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> fluidTank);;
    public int timer;
    protected int totalTime;

    private SpawnerRecipe lastRecipe;

    public SpawnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        fluidTank = createFluidTank();
        fluidCapability = LazyOptional.of(() -> fluidTank);
        totalTime = 100;
        timer = 100;
    }
    @Override
    public void initialize() {
        super.initialize();
        initHandler();
    }
    private void initHandler() {
        if (level instanceof ServerLevel sLevel) {
            player = new DeployerFakePlayer(sLevel, owner);
            Vec3 initialPos = VecHelper.getCenterOf(worldPosition.relative(getBlockState().getValue(HORIZONTAL_FACING)));
            player.setPos(initialPos.x, initialPos.y, initialPos.z);
        }
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        int max = SpawnerConfig.SPAWNER_MAX_RANGE.get();
;
        range = new ScrollValueBehaviour(ModLang.translate("spawner.scrollValue.label").component(), this, new CenteredSideValueBoxTransform())
                .between(1, max);
        range.value = 1;

        behaviours.add(range);
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
        return SpawnerConfig.SPAWNER_LIQUID_CAPACITY.get();
    }

    @Override
    public void tick() {
        super.tick();

        if (getSpeed() == 0)
            return;
        if(!checkRequirements(lastRecipe))
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

            Optional<SpawnerRecipe> recipe = ModRecipeTypes.findSpawner( fluidTank.getFluid(), level);
            if (recipe.isEmpty()) {
                timer = 100;
                totalTime = 100;
            } else {
                lastRecipe = recipe.get();
                timer = lastRecipe.getProcessingTime();
                totalTime =  lastRecipe.getProcessingTime();
            }
            sendData();
            return;
        }

        timer = lastRecipe.getProcessingTime();
        totalTime =  lastRecipe.getProcessingTime();
        sendData();
    }

    private boolean checkRequirements(SpawnerRecipe recipe) {
        if(SpawnerConfig.LOOT_COLLECTOR_REQUIRED.get() && !isSpawnPosBlockLootCollector())
            return false;
        if(recipe != null && recipe.getFluidAmount() > fluidTank.getFluidAmount())
            return false;
        return true;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(this.timer < this.totalTime) {
            ModLang.translate("spawner.tooltip.progress", this.getProgressPercent()).style(ChatFormatting.YELLOW).forGoggles(tooltip);
            added = true;
        }
        BlockPos spawnPos = getBlockPos().relative(Direction.Axis.Y, range.getValue());

        if(isSpawnPosBlockLootCollector()) {
            ModLang.translate("spawner.tooltip.with_loot_collector").style(ChatFormatting.GREEN).forGoggles(tooltip);
            added = true;
        }
        return added;

    }

    private void process() {

        SpawnerRecipe.SpawnerRecipeWrapper recipeWrapper =  new SpawnerRecipe.SpawnerRecipeWrapper(fluidTank.getFluid());

        if (lastRecipe == null || !lastRecipe.matches(recipeWrapper, level)) {
            Optional<SpawnerRecipe> recipe = ModRecipeTypes.findSpawner(fluidTank.getFluid(), level);
            if (!recipe.isPresent())
                return;
            lastRecipe = recipe.get();
        }
        if (!lastRecipe.matches(recipeWrapper, level))
                return;
        if(lastRecipe.getFluidAmount() > fluidTank.getFluidAmount())
            return;

        fluidTank.drain(lastRecipe.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);


        if(isSpawnPosBlockLootCollector()){
            fillCollector(level,lastRecipe.getMob(), getSpawnPos() );
        } else {
            spawnLivingEntity(level,lastRecipe.getMob(), getSpawnPos() );
        }

        sendData();
        setChanged();
    }

    private BlockPos getSpawnPos(){
        return getBlockPos().relative(Direction.Axis.Y, range.getValue());
    }

    private boolean isSpawnPosBlockLootCollector() {
        assert level != null;

        if(level.getBlockEntity(getSpawnPos()) == null)
            return false;
        if(SpawnerConfig.ALLOW_ANY_CONTAINER_FOR_LOOT_COLLECTOR.get())
            return level.getBlockEntity(getSpawnPos()).getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
        if(level.getBlockState(getSpawnPos()).getBlock() instanceof LootCollectorBlock)
            return true;
        return false;
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
        if (isFluidHandlerCap(cap)) {
            if (fluidCapability == null) {
                initHandler();
            }
            return fluidCapability.cast();
        }
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
    private Entity getEntity(EntityType<?> pEntityType, BlockPos pPos){
        Entity entity;
        if(pEntityType == null){
            Optional<MobSpawnSettings.SpawnerData> spawn = level.getBiome(pPos).value().getMobSettings().getMobs(MobCategory.MONSTER).getRandom(level.getRandom());
            try {
                entity = spawn.get().type.create(level);
                return entity;
            } catch (Exception exception) {
                CreateMechanicalSpawner.LOGGER.warn("Failed to create random mob", (Throwable)exception);
                return null;
            }
        }
        try {
            entity = pEntityType.create(level);
            return entity;
        } catch (Exception exception) {
            CreateMechanicalSpawner.LOGGER.warn("Failed to create mob", (Throwable)exception);
            return null;
        }
    }

    private void fillCollector(Level pLevel, EntityType<?> pEntityType, BlockPos pSpawnPos) {
        assert level != null;
        if(pLevel.isClientSide)
            return;
        BlockEntity lootCollector = level.getBlockEntity(pSpawnPos);
        @NotNull LazyOptional<IItemHandler> lootCollectorInventoryHandler = lootCollector.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if(!lootCollectorInventoryHandler.isPresent())
            return;
        IItemHandler lootCollectorInventory = lootCollectorInventoryHandler.resolve().orElseThrow();
        Entity entitySpawn = getEntity(pEntityType, pSpawnPos);
        if (!(entitySpawn instanceof Mob mob))
            return;

        ResourceLocation resourceLocation = mob.getLootTable();

        FakePlayer fakePlayer = new DeployerFakePlayer((ServerLevel) level, getPlayer().getUUID());
        DamageSource damageSource = level.damageSources().playerAttack(fakePlayer);

        LootParams.Builder builder = new LootParams.Builder((ServerLevel) level);
        builder.withParameter(LootContextParams.ORIGIN, pSpawnPos.getCenter());
        //builder.withLuck(fakePlayer.getLuck())
        builder.withLuck(3)
               .withParameter(LootContextParams.THIS_ENTITY, entitySpawn).withParameter(LootContextParams.ORIGIN, entitySpawn.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.KILLER_ENTITY, fakePlayer)
                .withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, damageSource.getDirectEntity());
        builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, fakePlayer);

        LootParams params = builder.create(LootContextParamSet.builder().build());

        LootTable table = level.getServer().getLootData().getLootTable(resourceLocation);

        List<ItemStack> list = table.getRandomItems(params);
        for (ItemStack itemStack : list) {
            ItemHandlerHelper.insertItem(lootCollectorInventory, itemStack,false);
        }
        lootCollector.setChanged();

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
        

        if (mob.checkSpawnObstruction(level)) {
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
            //if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)pos.getX() + 0.51, pos.getY()+ 0.51, (double)pos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return;
            //if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
            if (mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
            }
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

            //if (net.minecraftforge.common.ForgeHooks.canEntitySpawn(mob, level, (double)currentSpawnPos.getX() + 0.51, currentSpawnPos.getY()+ 0.51, (double)currentSpawnPos.getZ() + 0.51, null, MobSpawnType.TRIGGERED) == -1) return false;
            if (mob.checkSpawnRules(level, MobSpawnType.TRIGGERED) && mob.checkSpawnObstruction(level)) {
                level.addFreshEntity(mob);
                return true;
            }
        }
        return false;
    }

    public int getProgressPercent() {
        return 100 - this.timer * 100 / this.totalTime;
    }

    public DeployerFakePlayer getPlayer() {
        return player;
    }
}
