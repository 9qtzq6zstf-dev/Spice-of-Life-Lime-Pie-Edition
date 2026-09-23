package com.tarinoita.solsweetpotato.tracking;

import com.tarinoita.solsweetpotato.SOLSweetPotato;
import net.minecraft.world.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

import javax.annotation.Nullable;
import java.util.Optional;

public final class FoodInstance {
	public final Item item;

	public FoodInstance(Item item) {
		this.item = item;
	}

	@Nullable
	public static FoodInstance decode(String encoded) {
		ResourceLocation name = ResourceLocation.parse(encoded);

		// TODO it'd be nice to store (and maybe even count) references to missing items, in case the mod is added back in later
		Item item = BuiltInRegistries.ITEM.get(name);
		if (item == null) {
			SOLSweetPotato.LOGGER.warn("attempting to load item into food list that is no longer registered: " + encoded + " (removing from list)");
			return null;
		}

		if (!new net.minecraft.world.item.ItemStack(item).has(net.minecraft.core.component.DataComponents.FOOD)) {
			SOLSweetPotato.LOGGER.warn("attempting to load item into food list that is no longer edible: " + encoded + " (ignoring in case it becomes edible again later)");
		}

		return new FoodInstance(item);
	}

	@Nullable
	public String encode() {
		return Optional.ofNullable(BuiltInRegistries.ITEM.getKey(item))
			.map(ResourceLocation::toString)
			.orElse(null);
	}

	@Override
	public int hashCode() {
		return item.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FoodInstance)) return false;
		FoodInstance other = (FoodInstance) obj;

		return item.equals(other.item);
	}

	public Item getItem() {
		return item;
	}

	@Override
	public String toString() {
		String enc = encode();
		if (enc == null)
			return "null";
		else
			return enc;
	}
}
