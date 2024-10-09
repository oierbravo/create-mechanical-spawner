package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.foundation.blockEntity.behaviour.DynamicCycleBehavior;
import com.oierbravo.create_mechanical_spawner.foundation.blockEntity.behaviour.IHavePercent;
import com.oierbravo.create_mechanical_spawner.foundation.utility.LivingEntityHelper;
import com.oierbravo.create_mechanical_spawner.foundation.utility.ModLang;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipeTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.CenteredSideValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
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


public class SpawnerBlockEntity extends KineticBlockEntity  implements DynamicCycleBehavior.DynamicCycleBehaviorSpecifics, IHavePercent {
    public UUID owner;
    protected DeployerFakePlayer player;

    DynamicCycleBehavior dynamicCycleBehaviour;
    ScrollValueBehaviour scrollValueBehaviour;
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
        scrollValueBehaviour = new ScrollValueBehaviour(ModLang.translate("spawner.scrollValue.label").component(), this, new CenteredSideValueBoxTransform())
                .between(1, max);
        scrollValueBehaviour.value = 1;

        behaviours.add(scrollValueBehaviour);

        dynamicCycleBehaviour = new DynamicCycleBehavior(this);
        behaviours.add(dynamicCycleBehaviour);
    }
    public ScrollValueBehaviour getScrollValueBehavior() {
        return scrollValueBehaviour;
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
    public Optional<SpawnerRecipe> getRecipe(){
        SpawnerRecipe.SpawnerRecipeWrapper recipeWrapper = new SpawnerRecipe.SpawnerRecipeWrapper(fluidTank.getFluid());
        assert level != null;
        if (lastRecipe == null || !lastRecipe.matches(recipeWrapper, level)) {
            Optional<SpawnerRecipe> sp = ModRecipeTypes.findSpawner( fluidTank.getFluid(), level);
            return ModRecipeTypes.findSpawner( fluidTank.getFluid(), level);
        }
        return Optional.ofNullable(lastRecipe);
    }



    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        SpawnerPointDisplay.display(this);
        boolean added = super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        if(this.getProgressPercent() > 0) {
            ModLang.translate("spawner.tooltip.progress", this.getProgressPercent()).style(ChatFormatting.YELLOW).forGoggles(tooltip);
            added = true;
        }

        if(isSpawnPosBlockLootCollector()) {
            ModLang.translate("spawner.tooltip.with_loot_collector").style(ChatFormatting.GREEN).forGoggles(tooltip);
            added = true;
        }
        return added;

    }


    @Override
    public boolean containedFluidTooltip(List<Component> tooltip, boolean isPlayerSneaking, LazyOptional<IFluidHandler> handler) {
        return super.containedFluidTooltip(tooltip, isPlayerSneaking, handler);
    }

    private BlockPos getSpawnPos(){
        return getBlockPos().relative(Direction.Axis.Y, scrollValueBehaviour.getValue());
    }

    private boolean isSpawnableBlockPos(){
        if(level != null && level.getBlockState(getSpawnPos()).getBlock() == Blocks.AIR)
            return true;
        return isSpawnPosBlockLootCollector();
    }

    private boolean isSpawnPosBlockLootCollector() {
        assert level != null;
        BlockEntity spawnPosBlockEntity = level.getBlockEntity(getSpawnPos());

        if(spawnPosBlockEntity == null)
            return false;

        if(SpawnerConfig.ALLOW_ANY_CONTAINER_FOR_LOOT_COLLECTOR.get())
            return spawnPosBlockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).isPresent();
        if(level.getBlockState(getSpawnPos()).getBlock() instanceof ItemVaultBlock
            && SpawnerConfig.ALLOW_CREATE_ITEM_VAULT_FOR_LOOT_COLLECTOR.get())
            return true;
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
    public FluidTank getFluidTank(){
        return fluidTank;
    }
    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("TankContent", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        fluidTank.readFromNBT(compound.getCompound("TankContent"));
    }

    public int getScrollValueBehaviour() {
        return scrollValueBehaviour.getValue();
    }

    public List<BlockPos> getSpawnBlockPosition(Direction forcedMovement, boolean visualize) {
        List<BlockPos> positions = new ArrayList<>();

        int position = visualize ? scrollValueBehaviour.getValue() : getScrollValueBehaviour();

        BlockPos current = worldPosition.relative(Direction.Axis.Y, position);


        positions.add(current);


        return positions;
    }

    public List<SpawnerBlockEntity> collectSpawnGroup() {
        return new ArrayList<>();
    }

    private void fillCollector(Level pLevel, SpawnerRecipe pSpawnerRecipe, BlockPos pSpawnPos) {
        assert level != null;
        if(pLevel.isClientSide)
            return;
        BlockEntity lootCollector = level.getBlockEntity(pSpawnPos);
        @NotNull LazyOptional<IItemHandler> lootCollectorInventoryHandler = lootCollector.getCapability(ForgeCapabilities.ITEM_HANDLER);
        if(!lootCollectorInventoryHandler.isPresent())
            return;
        IItemHandler lootCollectorInventory = lootCollectorInventoryHandler.resolve().orElseThrow();

        if( SpawnerConfig.CUSTOM_LOOT_PER_SPAWN_RECIPE_ENABLED.get() && !pSpawnerRecipe.customLoot.isEmpty()){
            fillCollectorWithCustomLoot(lootCollectorInventory,pSpawnerRecipe);
        } else {
            fillCollectorWithMobLoot(lootCollectorInventory,pSpawnerRecipe,pSpawnPos);
        }

        lootCollector.setChanged();


    }
    protected void fillCollectorWithCustomLoot(IItemHandler pLootCollectorInventory, SpawnerRecipe pSpawnerRecipe ){
            List<ItemStack> customLootStack = pSpawnerRecipe.rollCustomLoot();
            for (ItemStack itemStack : customLootStack) {
                ItemHandlerHelper.insertItem(pLootCollectorInventory, itemStack,false);
            }
    }
    protected void fillCollectorWithMobLoot(IItemHandler pLootCollectorInventory, SpawnerRecipe pSpawnerRecipe, BlockPos pSpawnPos){
        Entity entitySpawn = LivingEntityHelper.createEntity((ServerLevel) this.level, pSpawnerRecipe.getMob(), pSpawnPos);
        if (!(entitySpawn instanceof Mob mob))
            return;

        List<ItemStack> list = LivingEntityHelper.getLootFromMob((ServerLevel) this.level,mob,pSpawnPos,getPlayer());
        for (ItemStack itemStack : list) {
            ItemHandlerHelper.insertItem(pLootCollectorInventory, itemStack,false);
        }
    }

    public int getProgressPercent() {
       return  this.dynamicCycleBehaviour.getProgressPercent();
    }

    public DeployerFakePlayer getPlayer() {
        return player;
    }

    @Override
    public void onCycleCompleted() {

    }

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    }

    @Override
    public int getProcessingTime() {
        if(getRecipe().isEmpty())
            return 0;
        return getRecipe().get().getProcessingTime();
    }
    private boolean checkRequirements(SpawnerRecipe recipe) {
        if(!isSpawnPosBlockLootCollector())
            return false;
        if(recipe != null && recipe.getFluidAmount() > fluidTank.getFluidAmount())
            return false;
        return true;
    }
    @Override
    public boolean tryProcess(boolean simulate) {
        if(getRecipe().isEmpty())
            return false;

        /*if(!checkLootCollector())
            return false;*/
        if (fluidTank.getFluidAmount() < getRecipe().get().getFluidAmount())
            return false;
        if(!isSpawnableBlockPos())
            return false;
        if(simulate)
            return true;

        if(this.level != null && this.level.isClientSide())
            return true;

        fluidTank.drain(getRecipe().get().getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);

        if(isSpawnPosBlockLootCollector()){
            fillCollector(level,getRecipe().get(), getSpawnPos() );
        } else {
            LivingEntityHelper.spawnLivingEntity(level,getRecipe().get().getMob(), getSpawnPos() );
        }

        sendData();
        setChanged();
        return true;
    }
    private boolean checkLootCollector(){
        if(SpawnerConfig.LOOT_COLLECTOR_REQUIRED.get() ||
                SpawnerConfig.ALLOW_CREATE_ITEM_VAULT_FOR_LOOT_COLLECTOR.get() ||
                SpawnerConfig.ALLOW_ANY_CONTAINER_FOR_LOOT_COLLECTOR.get()
        )
            return isSpawnPosBlockLootCollector();
        return false;
    }

    @Override
    public void playSound() {

    }
}
