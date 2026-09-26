package com.jia.sollimepie.client;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.lib.Localization;
import com.jia.sollimepie.item.foodcontainer.FoodContainerScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import com.mojang.blaze3d.platform.InputConstants;


@EventBusSubscriber(modid = SOLLimePie.MOD_ID, value = Dist.CLIENT)
public class SOLClientRegistry {
    public static KeyMapping OPEN_FOOD_BOOK;

    @SubscribeEvent
    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        OPEN_FOOD_BOOK = new KeyMapping(Localization.localized("key", "open_food_book"),
                InputConstants.UNKNOWN.getValue(), Localization.localized("key", "category"));
        event.register(OPEN_FOOD_BOOK);
    }
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ContainerScreenRegistry.FOOD_CONTAINER.get(), FoodContainerScreen::new);
    }
}
