package com.jia.sollimepie.api;

import com.jia.sollimepie.tracking.CapabilityHandler;
import com.jia.sollimepie.tracking.FoodList;
import net.minecraft.world.entity.player.Player;

/**
 Provides a stable API for interfacing with Spice of Life: Carrot Edition.
 */
public final class SOLLimePieAPI
{

	private SOLLimePieAPI() {}

	/**
	 Retrieves the {@link com.jia.sollimepie.api.FoodCapability} for the given player.
	 */
	public static FoodCapability getFoodCapability(Player player) {
		return FoodList.get(player);
	}

	/**
	 Synchronizes the food list for the given player to the client, updating their max health in the process.
	 */
	public static void syncFoodList(Player player) {
		CapabilityHandler.syncFoodList(player);
	}
}
