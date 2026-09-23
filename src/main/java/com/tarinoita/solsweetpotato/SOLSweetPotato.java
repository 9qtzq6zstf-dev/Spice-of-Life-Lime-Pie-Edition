package com.tarinoita.solsweetpotato;

import com.tarinoita.solsweetpotato.client.ContainerScreenRegistry;
import com.tarinoita.solsweetpotato.communication.ConfigMessage;
import com.tarinoita.solsweetpotato.communication.FoodListMessage;
import com.tarinoita.solsweetpotato.item.SOLSweetPotatoItems;
import com.tarinoita.solsweetpotato.item.foodcontainer.FoodContainerItem;
import com.tarinoita.solsweetpotato.tracking.CapabilityHandler;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SOLSweetPotato.MOD_ID)
@EventBusSubscriber(modid = SOLSweetPotato.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class SOLSweetPotato {
    public static final String MOD_ID = "solapplepie";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static boolean HasFarmersDelight() { return ModList.get().isLoaded("farmersdelight"); }
    public static boolean HasPamsHarvestcraft() { return ModList.get().isLoaded("pamhc2foodcore"); }

    public static ResourceLocation resourceLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public SOLSweetPotato(IEventBus modBus, ModContainer container) {
        SOLSweetPotatoConfig.setUp(container);
        SOLSweetPotatoItems.ITEMS.register(modBus);
        ContainerScreenRegistry.MENU_TYPES.register(modBus);
        CapabilityHandler.ATTACHMENTS.register(modBus);
    }

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("1");
        registrar.playToClient(FoodListMessage.TYPE, FoodListMessage.STREAM_CODEC, FoodListMessage::handle);
        registrar.playToClient(ConfigMessage.TYPE, ConfigMessage.STREAM_CODEC, ConfigMessage::handle);
    }

    @SubscribeEvent
    public static void registerGameTests(RegisterGameTestsEvent event) {
        event.register(MigrationGameTests.class);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> FoodContainerItem.getInventory(stack),
            SOLSweetPotatoItems.LUNCHBOX.get(), SOLSweetPotatoItems.LUNCHBAG.get(), SOLSweetPotatoItems.GOLDEN_LUNCHBOX.get());
    }

}
