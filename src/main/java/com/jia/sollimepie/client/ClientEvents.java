package com.jia.sollimepie.client;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.client.gui.FoodBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import static com.jia.sollimepie.client.SOLClientRegistry.OPEN_FOOD_BOOK;

@EventBusSubscriber(value = Dist.CLIENT, modid = SOLLimePie.MOD_ID)
public class ClientEvents {
    @SubscribeEvent
    public static void handleKeypress(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {
            return;
        }

        if (OPEN_FOOD_BOOK != null && OPEN_FOOD_BOOK.consumeClick()) {
            FoodBookScreen.open(player);
        }
    }
}
