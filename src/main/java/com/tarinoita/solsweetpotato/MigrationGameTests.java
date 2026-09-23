package com.tarinoita.solsweetpotato;

import com.tarinoita.solsweetpotato.item.SOLSweetPotatoItems;
import com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainerInventory;
import com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainerItem;
import com.tarinoita.solsweetpotato.tracking.FoodList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
public final class MigrationGameTests {
    @GameTest(templateNamespace = SOLSweetPotato.MOD_ID, template = "tests/empty")
    public static void lunchbagContentsPersist(GameTestHelper helper) {
        ItemStack bag = new ItemStack(SOLSweetPotatoItems.LUNCHBAG.get());
        FoodContainerInventory inventory = FoodContainerItem.getInventory(bag);
        helper.assertTrue(inventory != null, "Lunchbag inventory missing");
        inventory.setStackInSlot(2, new ItemStack(Items.BREAD, 3));
        helper.assertTrue(bag.has(DataComponents.CONTAINER), "Lunchbag contents were not written");
        FoodContainerInventory reopened = FoodContainerItem.getInventory(bag);
        helper.assertTrue(reopened != null && reopened.getSlots() == 5, "Lunchbag slot count changed");
        helper.assertTrue(reopened.getStackInSlot(2).is(Items.BREAD) && reopened.getStackInSlot(2).getCount() == 3,
            "Lunchbag food was lost after reopening");
        helper.succeed();
    }

    @GameTest(templateNamespace = SOLSweetPotato.MOD_ID, template = "tests/empty")
    public static void playerFoodAttachmentPersists(GameTestHelper helper) {
        FoodList food = FoodList.get(helper.makeMockPlayer(GameType.SURVIVAL));
        food.addFood(Items.BREAD);
        FoodList restored = new FoodList();
        restored.deserializeNBT(food.serializeNBT());
        helper.assertTrue(restored.hasEaten(Items.BREAD) && restored.getFoodsEaten() == 1,
            "Food attachment did not round trip");
        helper.succeed();
    }
}
