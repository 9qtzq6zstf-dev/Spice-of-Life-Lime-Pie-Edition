package com.tarinoita.solsweetpotato.api;

import net.minecraft.world.item.Item;

/**
 Provides a stable, (strongly) simplified view of the food list.
 */
public interface FoodCapability  {
	/**
	 @return whether or not the given food is being tracked.
	 */
	boolean hasEaten(Item item);

	/**
	 * @return the food diversity score of the current food queue.
	 */
	double foodDiversity();
}
