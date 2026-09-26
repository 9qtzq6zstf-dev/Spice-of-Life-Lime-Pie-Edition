package com.jia.sollimepie.item;

import com.jia.sollimepie.SOLLimePie;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class SOLLimePieCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SOLLimePie.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.sollimepie"))
                    .icon(() -> SOLLimePieItems.FOOD_BOOK.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(SOLLimePieItems.FOOD_BOOK.get());
                        output.accept(SOLLimePieItems.LUNCHBAG.get());
                        output.accept(SOLLimePieItems.LUNCHBOX.get());
                        output.accept(SOLLimePieItems.GOLDEN_LUNCHBOX.get());
                    })
                    .build());

    private SOLLimePieCreativeTabs() {}
}
