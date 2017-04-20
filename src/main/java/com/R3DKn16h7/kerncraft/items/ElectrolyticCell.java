package com.R3DKn16h7.kerncraft.items;

import com.R3DKn16h7.kerncraft.KernCraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElectrolyticCell extends Item {
    public static String base_name = "electrolytic_cell";

    public ElectrolyticCell() {
        super();

        this.setUnlocalizedName(base_name);
        this.setRegistryName(base_name);
        this.setCreativeTab(KernCraft.KERNCRAFT_CREATIVE_TAB);
        GameRegistry.register(this);
    }

}