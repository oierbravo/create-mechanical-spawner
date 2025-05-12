package com.oierbravo.create_mechanical_spawner.content.components.collector;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;

public class LootCollectorItem extends BlockItem
{
    public LootCollectorItem(Block block, Properties properties)
    {
        super(block, properties);
    }

    @Override
    public boolean isEnchantable(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment)
    {
        return enchantment == Enchantments.MOB_LOOTING;
    }

    @Override
    public int getEnchantmentValue(ItemStack stack)
    {
        return 15;
    }

}
