package com.R3DKn16h7.kerncraft.plugins.JEI;

import com.R3DKn16h7.kerncraft.items.KernCraftItems;
import mcp.MethodsReturnNonnullByDefault;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by filippo on 23/11/16.
 */
public class ExtractorJEIRecipeCategory implements IRecipeCategory {
    public static final String CATEGORY_UID = "kerncraft.extractor";
    protected static final int inputSlot = 1;
    protected static final int catalystSlot = 2;
    protected static final int canisterSlot = 3;
    protected static final int fuelSlot = 4;
    protected static final int outputSlotStart = 5;
    protected static final int outputSlotSize = 4;
    protected final ResourceLocation backgroundLocation;
    protected final IDrawableAnimated flame;
    protected final IDrawableAnimated brewing;
    //    protected final IDrawableAnimated arrow;
    private final IDrawable background;
    private final String localizedName;

    public ExtractorJEIRecipeCategory(IGuiHelper guiHelper) {
        backgroundLocation = new ResourceLocation("minecraft",
                "textures/gui/container/furnace.png");

        IDrawableStatic flameDrawable = guiHelper.createDrawable(backgroundLocation,
                176, 0, 14, 14);
        flame = guiHelper.createAnimatedDrawable(flameDrawable,
                300, IDrawableAnimated.StartDirection.TOP, true);

//        IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation,
//                176, 14, 24, 17);
//        this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable,
//                200, IDrawableAnimated.StartDirection.LEFT, false);

        ResourceLocation location =
                new ResourceLocation(
                        "kerncraft:textures/gui/container/extractor_gui.png");

        IDrawableStatic brewingDrawable = guiHelper.createDrawable(location,
                182, 0, 12, 28);
        brewing = guiHelper.createAnimatedDrawable(brewingDrawable,
                100, IDrawableAnimated.StartDirection.BOTTOM, false);

        background = guiHelper.createDrawable(location,
                7, 16, 163, 57);
        localizedName = "Extractor";
    }

    @Override
    public String getUid() {
        return CATEGORY_UID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        flame.draw(minecraft, 2 + 18, 2 + 18 * 1);
//        arrow.draw(minecraft, 18 + 18 * 2, 18 * 2);
        brewing.draw(minecraft, 18 + 3 + 18 * 2, 18 + 12);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout,
                          IRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        guiItemStacks.init(inputSlot, true, 18, 0);
        guiItemStacks.init(catalystSlot, true, 18 * 3, 0);
        guiItemStacks.init(canisterSlot, true, 18 * 8, 0);
        guiItemStacks.init(fuelSlot, true, 18, 18 * 2);

        recipeWrapper.getIngredients(ingredients);

        int i = -1;
        guiItemStacks.set(inputSlot, ingredients.getInputs(ItemStack.class).get(++i));
        if(((ExtractorJEIRecipeWrapper) recipeWrapper).recipe.catalyst != null)
            guiItemStacks.set(catalystSlot,
                    ingredients.getInputs(ItemStack.class).get(++i));
        guiItemStacks.set(canisterSlot, new ItemStack(KernCraftItems.CANISTER));

        List<List<ItemStack>> outs = ingredients.getOutputs(ItemStack.class);
        int min = Math.min(outs.size(), outputSlotSize);
        for (int j = 0; j < min; ++j) {
            guiItemStacks.init(j + outputSlotStart,
                    false, 0 + 18 * (j + 5), 18 * 2);
            ItemStack item = outs.get(j).get(0);
            if (item != null)
                guiItemStacks.set(j + outputSlotStart, item);
        }
    }

    @Override
    @MethodsReturnNonnullByDefault
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        ArrayList a = new ArrayList<String>();
        //a.add("Extractor recipes");
        return a;
    }
}
