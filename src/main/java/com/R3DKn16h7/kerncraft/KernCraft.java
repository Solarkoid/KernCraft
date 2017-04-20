package com.R3DKn16h7.kerncraft;

import com.R3DKn16h7.kerncraft.items.KernCraftItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = KernCraft.MODID, name = KernCraft.NAME,
     version = KernCraft.VERSION, dependencies= "after:tesla")
public class KernCraft {
    public static final String MODID = "kerncraft";
    public static final String NAME = "kerncraft";
    public static final String VERSION = "1.0";

    public static final CreativeTabs KERNCRAFT_CREATIVE_TAB = new CreativeTabs("KernCraft") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(KernCraftItems.TYROCINIUM_CHYMICUM);
        }
    };

    @SidedProxy(clientSide = "com.R3DKn16h7.kerncraft.ClientProxy",
            serverSide = "com.R3DKn16h7.kerncraft.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance(KernCraft.MODID)
    public static KernCraft instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
