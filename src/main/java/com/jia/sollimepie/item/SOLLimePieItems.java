package com.jia.sollimepie.item;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.item.foodcontainer.FoodContainerItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SOLLimePieItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SOLLimePie.MOD_ID);
    public static final DeferredItem<FoodBookItem> FOOD_BOOK = ITEMS.register("food_book", FoodBookItem::new);
    public static final DeferredItem<FoodContainerItem> LUNCHBOX = ITEMS.register("lunchbox", () -> new FoodContainerItem(9, "lunchbox"));
    public static final DeferredItem<FoodContainerItem> LUNCHBAG = ITEMS.register("lunchbag", () -> new FoodContainerItem(5, "lunchbag"));
    public static final DeferredItem<FoodContainerItem> GOLDEN_LUNCHBOX = ITEMS.register("golden_lunchbox", () -> new FoodContainerItem(14, "golden_lunchbox"));
}
