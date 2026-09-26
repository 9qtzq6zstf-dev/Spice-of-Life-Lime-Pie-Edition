package com.jia.sollimepie;

import com.jia.sollimepie.item.SOLLimePieItems;
import com.jia.sollimepie.item.foodcontainer.FoodContainerInventory;
import com.jia.sollimepie.item.foodcontainer.FoodContainerItem;
import com.jia.sollimepie.item.foodcontainer.FoodSlot;
import com.jia.sollimepie.tracking.FoodList;
import com.jia.sollimepie.tracking.FoodInstance;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

import java.util.Objects;

@PrefixGameTestTemplate(false)
public final class MigrationGameTests {
    @GameTest(templateNamespace = SOLLimePie.MOD_ID, template = "tests/empty")
    public static void lunchbagContentsPersist(GameTestHelper helper) {
        ItemStack bag = new ItemStack(SOLLimePieItems.LUNCHBAG.get());
        FoodContainerInventory inventory = Objects.requireNonNull(FoodContainerItem.getInventory(bag), "Lunchbag inventory missing");
        inventory.setStackInSlot(2, new ItemStack(Items.BREAD, 3));
        helper.assertTrue(bag.has(DataComponents.CONTAINER), "Lunchbag contents were not written");
        FoodContainerInventory reopened = Objects.requireNonNull(FoodContainerItem.getInventory(bag), "Lunchbag inventory missing after reopening");
        helper.assertTrue(reopened.getSlots() == 5, "Lunchbag slot count changed");
        helper.assertTrue(reopened.getStackInSlot(2).is(Items.BREAD) && reopened.getStackInSlot(2).getCount() == 3,
            "Lunchbag food was lost after reopening");
        helper.succeed();
    }

    @GameTest(templateNamespace = SOLLimePie.MOD_ID, template = "tests/empty")
    public static void playerFoodAttachmentPersists(GameTestHelper helper) {
        FoodList food = FoodList.get(helper.makeMockPlayer(GameType.SURVIVAL));
        food.addFood(Items.BREAD);
        FoodList restored = new FoodList();
        restored.deserializeNBT(food.serializeNBT());
        helper.assertTrue(restored.hasEaten(Items.BREAD) && restored.getFoodsEaten() == 1,
            "Food attachment did not round trip");
        helper.succeed();
    }
    @GameTest(templateNamespace = SOLLimePie.MOD_ID, template = "tests/empty")
    public static void missingFoodAndRecipes(GameTestHelper helper) {
        helper.assertTrue(FoodInstance.decode("missing:no_such_food") == null,
            "A missing food ID resolved to the default item");
        helper.assertTrue(helper.getLevel().getRecipeManager().byKey(SOLLimePie.resourceLocation("lunchbag")).isPresent(),
            "Lunchbag recipe did not load");
        helper.succeed();
    }
    @GameTest(templateNamespace = SOLLimePie.MOD_ID, template = "tests/empty")
    public static void slotMutationPersists(GameTestHelper helper) {
        ItemStack bag = new ItemStack(SOLLimePieItems.LUNCHBAG.get());
        FoodContainerInventory inventory = Objects.requireNonNull(FoodContainerItem.getInventory(bag), "Lunchbag inventory missing");
        inventory.setStackInSlot(0, new ItemStack(Items.BREAD, 4));
        FoodSlot slot = new FoodSlot(inventory, 0, 0, 0);
        slot.getItem().shrink(2);
        slot.setChanged();
        FoodContainerInventory reopened = Objects.requireNonNull(FoodContainerItem.getInventory(bag), "Lunchbag inventory missing after reopening");
        helper.assertTrue(reopened.getStackInSlot(0).getCount() == 2,
            "Direct menu slot mutation was not persisted");
        helper.succeed();
    }
}
