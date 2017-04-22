package com.R3DKn16h7.kerncraft.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.R3DKn16h7.kerncraft.KernCraft;
import com.R3DKn16h7.kerncraft.capabilities.ElementCapabilities;
import com.R3DKn16h7.kerncraft.capabilities.ElementContainerProvider;
import com.R3DKn16h7.kerncraft.capabilities.IElementContainer;
import com.R3DKn16h7.kerncraft.utils.PotionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Portable beacon that doesn't need to be placed in the world.
 * TODO: baubles + COFH compoatibility
 * @author R3DKn16h7
 */
@Optional.Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class PortableBeacon extends Item implements IBauble {
    public static String unlocalizedName = "portable_beacon";

    public PortableBeacon() {
        super();

        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(unlocalizedName);
        this.setCreativeTab(KernCraft.KERNCRAFT_CREATIVE_TAB);
        this.setMaxStackSize(1);
        GameRegistry.register(this);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn,
                         Entity entityIn, int itemSlot, boolean isSelected) {

        if (entityIn != null && entityIn instanceof EntityPlayer)
            if (((EntityPlayer) entityIn).getActivePotionEffect(PotionHelper.getPotion(PotionHelper.Effect.HASTE)) == null)
                ((EntityPlayer) entityIn).addPotionEffect(PotionHelper.getPotionEffect(PotionHelper.Effect.HASTE, 300));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (stack.hasCapability(ElementCapabilities.CAPABILITY_ELEMENT_CONTAINER, null)) {
            IElementContainer cap = stack.getCapability(ElementCapabilities.CAPABILITY_ELEMENT_CONTAINER, null);
            return 1. - cap.getTotalAmount() / cap.getCapacity();

        }
        return 0;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player,
                                      World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (player.getHeldItem(hand).hasCapability(ElementCapabilities.CAPABILITY_ELEMENT_CONTAINER, null)) {
            IElementContainer cap = player.getHeldItem(hand).getCapability(
                    ElementCapabilities.CAPABILITY_ELEMENT_CONTAINER, null
            );
            cap.addAmountOf(1, 100, false);
        }


        // TODO DEPRECATED
        if (!world.isRemote) {

            player.addPotionEffect(PotionHelper.getPotionEffect(
                    PotionHelper.Effect.HASTE, 300)
            );
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.FAIL;
    }


    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack,
                                                @Nullable NBTTagCompound nbt) {
        return new ElementContainerProvider(5, 5000);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        // TODO DEPRECATED
        if (!player.world.isRemote) {
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                if (entityLivingBase instanceof IMob) {
                    IMob mod = (IMob) entityLivingBase;
                    entityLivingBase.addPotionEffect(PotionHelper.getPotionEffect(PotionHelper.Effect.WITHER, 300));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn,
                               List<String> tooltip, boolean advanced) {
        ElementCapabilities.addTooltip(stack, tooltip);

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    @Optional.Method(modid="baubles")
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.BELT;
    }

    @Override
    @Optional.Method(modid="baubles")
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player != null && player instanceof EntityPlayer)
            if (player.getActivePotionEffect(PotionHelper.getPotion(PotionHelper.Effect.SPECTRAL)) == null)
                player.addPotionEffect(PotionHelper.getPotionEffect(PotionHelper.Effect.SPECTRAL, 300));
    }

    @Override
    @Optional.Method(modid="baubles")
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid="baubles")
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    @Optional.Method(modid="baubles")
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    @Optional.Method(modid="baubles")
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    @Optional.Method(modid="baubles")
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return false;
    }
}
