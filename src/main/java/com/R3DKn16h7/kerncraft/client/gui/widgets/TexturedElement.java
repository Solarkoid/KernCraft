package com.R3DKn16h7.kerncraft.client.gui.widgets;

import com.R3DKn16h7.kerncraft.client.gui.AdvancedGuiContainer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

/**
 * Created by Filippo on 27/11/2016.
 */
public class TexturedElement extends Widget {

    public ResourceLocation textureLocation;
    public int offsetX, offsetY;
    public Color tint;


    public TexturedElement(AdvancedGuiContainer container, String texture,
                           int xPosition, int yPosition,
                           int xSize, int ySize,
                           int offsetX, int offsetY) {
        super(container, xPosition, yPosition, xSize, ySize);
        this.textureLocation = new ResourceLocation(texture);
        // Offset within texture
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.tint = null;
    }

    public static TexturedElement ENERGY_BAR_BACKGROUND(AdvancedGuiContainer container,
                                                        int xPosition, int yPosition) {
        return new TexturedElement(container, "kerncraft:textures/gui/container/extractor_gui.png",
                xPosition, yPosition, 8, 3 * DEFAULT_SLOT_SIZE_Y,
                16, 16);

    }

    public TexturedElement setTint(Color tint) {
        this.tint = tint;
        return this;
    }

    @Override
    public void draw() {
        if (!(container instanceof GuiScreen)) {
            return;
        }
        GuiScreen C = (GuiScreen) container;

        if (tint != null) {
            GlStateManager.color(tint.getRed() / 255.f,
                    tint.getGreen() / 255.f,
                    tint.getBlue() / 255.f,
                    1.0F);
        }

        C.mc.getTextureManager().bindTexture(textureLocation);


        C.drawTexturedModalRect(xPosition, yPosition,
                offsetX, offsetY,
                xSize, ySize);

        GlStateManager.resetColor();

    }

}
