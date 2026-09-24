package com.jia.sollimepie.utils;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.tracking.FoodInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ResourceLocationException;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplexityParser {
    public static Map<FoodInstance, Double> parse(List<String> unparsed) {
        Map<FoodInstance, Double> complexityMap = new HashMap<>();

        for (String complexityString : unparsed) {
            String[] s = complexityString.split(",", 0);
            if (s.length != 2) {
                SOLLimePie.LOGGER.warn("Invalid complexity specification: " + complexityString);
                continue;
            }

            String foodString = s[0];
            double complexity = 1.0;
            try {
                complexity = Double.parseDouble(s[1]);
            }
            catch (NumberFormatException e) {
                SOLLimePie.LOGGER.warn("Second argument in complexity specification needs to be a number: " + complexityString);
                continue;
            }

            Item item;
            try {
                item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(foodString));
            }
            catch (ResourceLocationException e) {
                SOLLimePie.LOGGER.warn("Invalid item name: " + foodString);
                continue;
            }
            if (item == null) {
                SOLLimePie.LOGGER.warn("Invalid item name: " + foodString);
                continue;
            }

            if (!new net.minecraft.world.item.ItemStack(item).has(net.minecraft.core.component.DataComponents.FOOD)) {
                SOLLimePie.LOGGER.warn("Item is not food: " + foodString);
                continue;
            }

            FoodInstance food = new FoodInstance(item);

            if (food.encode() == null) {
                SOLLimePie.LOGGER.warn("Item does not exist: " + foodString);
                continue;
            }

            complexityMap.put(food, complexity);
        }
        return complexityMap;
    }
}
