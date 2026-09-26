package com.jia.sollimepie.tracking;

import com.jia.sollimepie.SOLLimePie;
import net.minecraft.world.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;

public final class FoodInstance {
	public final Item item;

	public FoodInstance(Item item) {
		this.item = item;
	}

	@Nullable
	public static FoodInstance decode(String encoded) {
		ResourceLocation name = ResourceLocation.parse(encoded);

		// TODO it'd be nice to store (and maybe even count) references to missing items, in case the mod is added back in later
		if (!BuiltInRegistries.ITEM.containsKey(name)) {
			SOLLimePie.LOGGER.warn("attempting to load item into food list that is no longer registered: {} (removing from list)", encoded);
			return null;
		}

		Item item = BuiltInRegistries.ITEM.get(name);
		if (!new net.minecraft.world.item.ItemStack(item).has(net.minecraft.core.component.DataComponents.FOOD)) {
			SOLLimePie.LOGGER.warn("attempting to load item into food list that is no longer edible: {} (ignoring in case it becomes edible again later)", encoded);
		}

		return new FoodInstance(item);
	}

	@Nullable
	public String encode() {
		return BuiltInRegistries.ITEM.getResourceKey(item)
			.map(key -> key.location().toString())
			.orElse(null);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FoodInstance other)) {
		    return false;
		}

		return item.equals(other.item);
	}

	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		String enc = encode();
		return enc == null ? "null" : enc;
	}
}
