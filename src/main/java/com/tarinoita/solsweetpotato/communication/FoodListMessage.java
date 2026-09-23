package com.tarinoita.solsweetpotato.communication;

import com.tarinoita.solsweetpotato.SOLSweetPotato;
import com.tarinoita.solsweetpotato.tracking.FoodList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record FoodListMessage(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<FoodListMessage> TYPE = new Type<>(SOLSweetPotato.resourceLocation("food_list"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FoodListMessage> STREAM_CODEC = StreamCodec.of(
        (buffer, message) -> buffer.writeNbt(message.tag),
        buffer -> new FoodListMessage(buffer.readNbt())
    );

    public FoodListMessage(FoodList foodList) { this(foodList.serializeNBT()); }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(FoodListMessage message, IPayloadContext context) {
        context.enqueueWork(() -> FoodList.get(context.player()).deserializeNBT(message.tag));
    }
}
