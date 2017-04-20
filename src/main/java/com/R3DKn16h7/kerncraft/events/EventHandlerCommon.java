package com.R3DKn16h7.kerncraft.events;

import com.R3DKn16h7.kerncraft.KernCraft;
import com.R3DKn16h7.kerncraft.achievements.AchievementHandler;
import com.R3DKn16h7.kerncraft.capabilities.ITyrociniumProgressCapability;
import com.R3DKn16h7.kerncraft.capabilities.TyrociniumProgressDefaultCapability;
import com.R3DKn16h7.kerncraft.items.ExtraShield;
import com.R3DKn16h7.kerncraft.items.ModItems;
import com.R3DKn16h7.kerncraft.network.KernCraftNetwork;
import com.R3DKn16h7.kerncraft.network.MessageSyncTyrociniumProgress;
import com.R3DKn16h7.kerncraft.utils.PotionHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerPickupXpEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
//import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;


public class EventHandlerCommon {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onPlayerPickupXP(PlayerPickupXpEvent e) {

    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void LivingHurtEvent(LivingHurtEvent e) {

        EntityLivingBase ent = e.getEntityLiving();

        // Handle shield effects
        if ((ent != null) &&
                ent instanceof EntityPlayer &&
                ent.isActiveItemStackBlocking() &&
                (
                        (
                                ent.getHeldItemOffhand() != ItemStack.EMPTY &&
                                        ent.getHeldItemOffhand().getItem() instanceof ExtraShield
                        ) || (
                                ent.getHeldItem(EnumHand.MAIN_HAND) != ItemStack.EMPTY &&
                                        ent.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ExtraShield)
                )
                ) {

            // Add spectral effect to damage source
            if (e.getSource().getSourceOfDamage() instanceof EntityArrow) {
                EntityArrow a = (EntityArrow) e.getSource().getSourceOfDamage();
                EntityLiving lv = (EntityLiving) a.shootingEntity;
                lv.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionHelper.Effect.SPECTRAL),
                        500, 300));
            } else if (e.getSource().getSourceOfDamage() instanceof EntityLiving) {
                EntityLiving lv = (EntityLiving) e.getSource().getSourceOfDamage();
                lv.addPotionEffect(new PotionEffect(Potion.getPotionById(PotionHelper.Effect.SPECTRAL),
                        500, 300));
            }
        } else {
            return;
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void entityConstructing(AttachCapabilitiesEvent<Entity> event) {
        // Attach capability to players
        if(event.getObject() instanceof EntityPlayer){
            event.addCapability(
                    new ResourceLocation(KernCraft.MODID + ":tyrociniumProgressCapability"),
                    new TyrociniumProgressDefaultCapability()
            );
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void respawning(PlayerEvent.Clone event) {
        // Reattach capabilities on death, not if returning from end
        if(event.isWasDeath()) {
            EntityPlayerMP playerOriginal = ((EntityPlayerMP) event.getOriginal());
            EntityPlayerMP playerNew = ((EntityPlayerMP) event.getEntityPlayer());

            ITyrociniumProgressCapability capabilityOriginal = playerOriginal.getCapability(TyrociniumProgressDefaultCapability.INSTANCE, null);
            ITyrociniumProgressCapability capabilityNew = playerNew.getCapability(TyrociniumProgressDefaultCapability.INSTANCE, null);

            capabilityNew.clone(capabilityOriginal);
//            KernCraftNetwork.networkWrapper.sendTo(new MessageSyncTyrociniumProgress(capabilityNew), playerNew);
//            capabilityNew.setDirty();
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    // LOL
    public void crafting(net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem() == ModItems.CANISTER) {
            event.player.addStat(AchievementHandler.APPRENTICE, 1);
        } else if (event.crafting.getItem() == ModItems.POTATO_BATTERY) {
            event.player.addStat(AchievementHandler.MASTER_OF_POTATOES, 1);
        } else if (event.crafting.getItem() == ModItems.ELECTROLYTIC_CELL) {
            event.player.addStat(AchievementHandler.CHEMIST, 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void PlayerTickEvent(TickEvent.PlayerTickEvent event) {
        EventUpdate.tick();
    }

}