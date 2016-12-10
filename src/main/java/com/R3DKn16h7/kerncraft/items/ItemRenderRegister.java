package com.R3DKn16h7.kerncraft.items;

import com.R3DKn16h7.kerncraft.KernCraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;

public final class ItemRenderRegister {

    public static void registerItemRenderer() {
        reg(ModItems.portableBeacon);
        ModItems.canister.registerRender();
        reg(ModItems.EXTRA_SHIELD);

        reg(ModItems.A);
        reg(ModItems.B);
        reg(ModItems.C);
        reg(ModItems.D);

        reg(ModItems.TEST_ITEM);

        reg(ModItems.TYROCINIUM_CHYMICUM);

        reg(ModItems.POTATO_BATTERY);
        reg(ModItems.ELECTROLYTIC_CELL);
    }


    public static void reg(Item item) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
                .register(item, 0, new ModelResourceLocation(
                        KernCraft.MODID + ":" + item.getUnlocalizedName().substring(5),
                        "inventory"));
    }
}