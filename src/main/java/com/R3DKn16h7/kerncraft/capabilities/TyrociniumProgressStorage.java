package com.R3DKn16h7.kerncraft.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

/**
 * Created by Filippo on 19-Apr-17.
 */
public class TyrociniumProgressStorage
        implements Capability.IStorage<ITyrociniumProgressCapability> {

    @Override
    public NBTBase writeNBT(Capability<ITyrociniumProgressCapability> capability,
                            ITyrociniumProgressCapability instance, EnumFacing side) {

        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<ITyrociniumProgressCapability> capability,
                        ITyrociniumProgressCapability instance, EnumFacing side, NBTBase nbt) {

        instance.deserializeNBT(((NBTTagCompound) nbt));

    }
}
