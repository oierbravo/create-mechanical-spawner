package com.oierbravo.create_mechanical_spawner.content.components;

import com.oierbravo.create_mechanical_spawner.ModLang;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlock;
import com.oierbravo.create_mechanical_spawner.content.components.collector.LootCollectorBlockEntity;
import com.oierbravo.create_mechanical_spawner.content.components.recipe.SpawnerRecipe;
import com.oierbravo.create_mechanical_spawner.foundation.utility.LivingEntityHelper;
import com.oierbravo.create_mechanical_spawner.infrastructure.config.MConfigs;
import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.oierbravo.create_mechanical_spawner.registrate.ModRecipes;
import com.oierbravo.mechanicals.compat.jade.IHavePercent;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.DynamicCycleBehavior;
import com.oierbravo.mechanicals.foundation.blockEntity.behaviour.RecipeRequirementsBehaviour;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerFakePlayer;
import com.simibubi.create.content.logistics.vault.ItemVaultBlock;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.simibubi.create.content.kinetics.base.HorizontalKineticBlock.HORIZONTAL_FACING;


public class SpawnerBlockEntity extends KineticBlockEntity  implements DynamicCycleBehavior.DynamicCycleBehaviorSpecifics , RecipeRequirementsBehaviour.RecipeRequirementsSpecifics<SpawnerRecipe>, IHavePercent {
    public UUID owner;
    protected DeployerFakePlayer player;

    DynamicCycleBehavior dynamicCycleBehaviour;
    ScrollValueBehaviour scrollValueBehaviour;
    public SmartFluidTankBehaviour inputTank;
    public RecipeRequirementsBehaviour<SpawnerRecipe> recipeRequirementsBehaviour;


    public SpawnerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
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
        int max = MConfigs.server().spawner.maxRange.get();

        inputTank = SmartFluidTankBehaviour.single(this, MConfigs.server().spawner.fluidCapacity.get());
        behaviours.add(inputTank);

        scrollValueBehaviour = new ScrollValueBehaviour(ModLang.translate("spawner.scrollValue.label").component(), this, new SpawnPointValuePositioning())
                .between(1, max);
        scrollValueBehaviour.value = 1;

        behaviours.add(scrollValueBehaviour);

        dynamicCycleBehaviour = new DynamicCycleBehavior(this);
        behaviours.add(dynamicCycleBehaviour);

        recipeRequirementsBehaviour = new RecipeRequirementsBehaviour<>(this);
        behaviours.add(recipeRequirementsBehaviour);
    }
    public ScrollValueBehaviour getScrollValueBehavior() {
        return scrollValueBehaviour;
    }


    public Optional<SpawnerRecipe> getRecipe(){
        return ModRecipes.findSpawner( inputTank.getPrimaryHandler().getFluid(), level);
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
        if(!inputTank.isEmpty()) {
            containedFluidTooltip(tooltip, isPlayerSneaking, inputTank.getPrimaryHandler());
            added = true;
        }

        boolean addedRequirements = recipeRequirementsBehaviour.addToGoggleTooltip(tooltip, isPlayerSneaking, added);
        if(addedRequirements)
            added = true;

        return added;

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

        if(MConfigs.server().spawner.allowAnyContainerForLootCollector.get()){
            @Nullable IItemHandler cap = level.getCapability(Capabilities.ItemHandler.BLOCK, getSpawnPos(),Direction.DOWN);
            return cap != null;
        }
        if(level.getBlockState(getSpawnPos()).getBlock() instanceof ItemVaultBlock
            && MConfigs.server().spawner.allowCreateItemVaultForLootCollector.get())
            return true;
        if(level.getBlockState(getSpawnPos()).getBlock() instanceof LootCollectorBlock)
            return true;
        return false;
    }


    @Override
    public void showParticles() {
        if(!isSpeedRequirementFulfilled())
            return;
        Vec3 offset = new Vec3(0f, 0f, 0f);

        Vec3 center = offset.add(VecHelper.getCenterOf(worldPosition));

        assert level != null;
        level.addParticle(ParticleTypes.POOF, center.x, center.y , center.z, 0, 0.01, 0);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.MECHANICAL_SPAWNER.get(),
                (be, context) -> be.inputTank.getCapability());
    }
    @Override
    public void invalidate() {
        super.invalidate();
        invalidateCapabilities();
    }
    public FluidTank getInputTank(){
        return inputTank.getPrimaryHandler();
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
        int lootLevel = 0;
        if(lootCollector instanceof LootCollectorBlockEntity lootCollectorBlockEntity){
            lootLevel = lootCollectorBlockEntity.getLootingLevel();
        }
        @NotNull IItemHandler lootCollectorInventoryHandler = level.getCapability(Capabilities.ItemHandler.BLOCK, pSpawnPos, Direction.DOWN);
        if(lootCollectorInventoryHandler == null)
            return;

        if( MConfigs.server().spawner.customLootPerSpawnRecipeEnabled.get() && !pSpawnerRecipe.getCustomLoot().isEmpty()){
            fillCollectorWithCustomLoot(lootCollectorInventoryHandler,pSpawnerRecipe, level.random);
        } else {
            fillCollectorWithMobLoot(lootCollectorInventoryHandler,pSpawnerRecipe,pSpawnPos,lootLevel);
        }

        lootCollector.setChanged();


    }
    protected void fillCollectorWithCustomLoot(IItemHandler pLootCollectorInventory, SpawnerRecipe pSpawnerRecipe, RandomSource random){
            List<ItemStack> customLootStack = pSpawnerRecipe.rollCustomLoot(random);
            for (ItemStack itemStack : customLootStack) {
                ItemHandlerHelper.insertItem(pLootCollectorInventory, itemStack,false);
            }
    }
    protected void fillCollectorWithMobLoot(IItemHandler pLootCollectorInventory, SpawnerRecipe pSpawnerRecipe, BlockPos pSpawnPos, int lootingLevel){
        Entity entitySpawn = LivingEntityHelper.createEntity((ServerLevel) this.level, pSpawnerRecipe.getOutput().getMob(), pSpawnPos);
        if (!(entitySpawn instanceof Mob mob))
            return;

        List<ItemStack> list = LivingEntityHelper.getLootFromMob((ServerLevel) this.level,mob,pSpawnPos,getPlayer(), lootingLevel);
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
    public float getKineticSpeed() {
        return getSpeed();
    }

    @Override
    public int getProcessingTime() {
        if(getRecipe().isEmpty())
            return 1;
        return (int)(getRecipe().get().getProcessingTime() * MConfigs.server().spawner.timeMultiplier.get());
    }
    private boolean checkRequirements(SpawnerRecipe recipe) {
        if(!isSpawnPosBlockLootCollector())
            return false;
        if(recipe != null && recipe.getFluidAmount() > inputTank.getPrimaryHandler().getFluidAmount())
            return false;
        return true;
    }
    @Override
    public boolean tryProcess(boolean simulate) {
        if(!isSpeedRequirementFulfilled())
            return false;

        Optional<SpawnerRecipe> optionalSpawnerRecipe = getRecipe();

        if(optionalSpawnerRecipe.isEmpty()){
            recipeRequirementsBehaviour.cleanRequirements();
            return false;
        }
        SpawnerRecipe spawnerRecipe = optionalSpawnerRecipe.get();

        if(!recipeRequirementsBehaviour.checkRequirements(spawnerRecipe))
            return false;


        if(simulate)
            return true;

        if(this.level != null && this.level.isClientSide())
            return true;

        inputTank.getPrimaryHandler().drain(spawnerRecipe.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);

        if(isSpawnPosBlockLootCollector()){
            fillCollector(level,spawnerRecipe, getSpawnPos() );
        } else {
            LivingEntityHelper.spawnLivingEntity(level,spawnerRecipe.getMob(), getSpawnPos() );
        }

        //sendData();
        //setChanged();
        return true;
    }

    @Override
    public boolean hasEnoughOutputSpace(SpawnerRecipe spawnerRecipe) {
        return isSpawnableBlockPos();
    }

    @Override
    public boolean matchesIngredients(SpawnerRecipe spawnerRecipe) {
        return spawnerRecipe.getFluidIngredient().test(inputTank.getPrimaryHandler().getFluid());
    }

    public static class SpawnPointValuePositioning extends ValueBoxTransform.Sided {
        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction != Direction.UP  && direction != Direction.DOWN;
        }

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8f, 8f,  16f);
        }

    }
}
