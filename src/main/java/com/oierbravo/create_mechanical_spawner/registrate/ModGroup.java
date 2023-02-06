package com.oierbravo.create_mechanical_spawner.registrate;


import com.oierbravo.create_mechanical_spawner.CreateMechanicalSpawner;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;


public class ModGroup extends CreativeModeTab {
	public static ModGroup MAIN;;
	
	public ModGroup(String name) {
		super(CreateMechanicalSpawner.MODID+":"+name);
		MAIN = this;
	}

	@Override
	public ItemStack makeIcon() {
		return new ItemStack(ModBlocks.MECHANICAL_SPAWNER.get());
	}

}
