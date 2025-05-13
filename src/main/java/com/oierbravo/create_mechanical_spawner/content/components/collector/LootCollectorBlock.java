package com.oierbravo.create_mechanical_spawner.content.components.collector;

import com.oierbravo.create_mechanical_spawner.registrate.ModBlockEntities;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class LootCollectorBlock extends Block implements IBE<LootCollectorBlockEntity>, IWrenchable {

    public LootCollectorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Class<LootCollectorBlockEntity> getBlockEntityClass() {
        return LootCollectorBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends LootCollectorBlockEntity> getBlockEntityType() {
        return ModBlockEntities.LOOT_COLLECTOR.get();
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        //Based on create backtank code
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        if (worldIn.isClientSide)
        {
            return;
        }
        if (stack == null)
        {
            return;
        }

        withBlockEntityDo(worldIn, pos, be ->
        {
            be.setLootingLevel(stack.getEnchantmentLevel(Enchantments.MOB_LOOTING));
            CompoundTag vanillaTag = stack.getOrCreateTag();

            be.setTags(vanillaTag);
        });
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter blockGetter, BlockPos pos, BlockState state)
    {
        //Based on create backtank code
        Item item = asItem();

        Optional<LootCollectorBlockEntity> blockEntityOptional = getBlockEntityOptional(blockGetter, pos);

        CompoundTag vanillaTag = blockEntityOptional.map(LootCollectorBlockEntity::getVanillaTag)
                .map(CompoundTag::copy)
                .orElse(new CompoundTag());

        ItemStack stack = new ItemStack(item, 1);
        stack.setTag(vanillaTag);
        return stack;
    }
}
