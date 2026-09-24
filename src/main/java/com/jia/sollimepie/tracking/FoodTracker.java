package com.jia.sollimepie.tracking;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.item.foodcontainer.FoodContainerItem;
import com.jia.sollimepie.tracking.benefits.BenefitsHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.minecraft.core.registries.BuiltInRegistries;


@EventBusSubscriber(modid = SOLLimePie.MOD_ID)
public final class FoodTracker {

	@SubscribeEvent
	public static void onFoodEaten(LivingEntityUseItemEvent.Finish event) {
		if (!BenefitsHandler.checkEvent(event)) {
			return;
		}

		Player player = (Player) event.getEntity();

		Item usedItem = event.getItem().getItem();
		if (!event.getItem().has(net.minecraft.core.component.DataComponents.FOOD) && usedItem != Items.CAKE) return;
		if (usedItem instanceof FoodContainerItem) return;

		updateFoodList(usedItem, player);
	}

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onCakeBlockEaten(PlayerInteractEvent.RightClickBlock event) {
		// Canceled means some other mod already resolved this event,
		// e.g. Farmer's Delight cut off a slice with a knife.
		if (event.isCanceled()) return;

		BlockState state = event.getLevel().getBlockState(event.getPos());
		Block clickedBlock = state.getBlock();
		Player player = (Player)event.getEntity();

		Item eatenItem = Items.CAKE;
		// If Farmer's Delight is installed, replace "cake" with FD's "cake slice"
		if (ModList.get().isLoaded("farmersdelight")) {
			eatenItem = BuiltInRegistries.ITEM.get(ResourceLocation.parse("farmersdelight:cake_slice"));
		}
		ItemStack eatenItemStack = new ItemStack(eatenItem);

		if (clickedBlock == Blocks.CAKE && player.canEat(false) &&
				event.getHand() == InteractionHand.MAIN_HAND && !event.getLevel().isClientSide) {
			// Fire an event instead of directly updating the food list, so that
			// SoL: Carrot Edition registers the eaten food too.
			NeoForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Finish(player, eatenItemStack, 0, ItemStack.EMPTY));
		}
	}

	public static void updateFoodList(Item food, Player player) {
		FoodList foodList = FoodList.get(player);
		foodList.addFood(food);

		double diversity = foodList.foodDiversity();
		BenefitsHandler.updateBenefits(player, diversity);

		CapabilityHandler.syncFoodList(player);
	}

	private FoodTracker() {}
}
