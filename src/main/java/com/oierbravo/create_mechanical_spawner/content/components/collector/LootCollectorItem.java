package com.oierbravo.create_mechanical_spawner.content.components.collector;

import net.minecraft.core.Holder;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;

import java.util.Set;

public class LootCollectorItem extends BlockItem {

    public LootCollectorItem(Block block, Properties properties)  {
        super(block, properties);
    }

    @Override
    public int getEnchantmentValue() {
        return 70;
    }
    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        ItemEnchantments itemEnchantments = EnchantmentHelper.getEnchantmentsForCrafting(book);
        Set<Holder<Enchantment>> enchantments = itemEnchantments.keySet();
        if(enchantments.size() > 1)
            return false;
        for(Holder<Enchantment> enchantment : enchantments ){
            if(enchantment.is(Enchantments.LOOTING))
                return true;
        }
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(Enchantments.LOOTING))
            return true;
        return super.supportsEnchantment(stack, enchantment);
    }
}
