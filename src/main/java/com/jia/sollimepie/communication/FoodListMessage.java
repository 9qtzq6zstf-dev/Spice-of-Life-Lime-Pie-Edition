package com.jia.sollimepie.communication;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.tracking.FoodList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Objects;

public record FoodListMessage(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<FoodListMessage> TYPE = new Type<>(SOLLimePie.resourceLocation("food_list"));
    public static final StreamCodec<RegistryFriendlyByteBuf, FoodListMessage> STREAM_CODEC = StreamCodec.of(
        (buffer, message) -> buffer.writeNbt(message.tag),
        buffer -> new FoodListMessage(Objects.requireNonNull(buffer.readNbt(), "Missing food list payload"))
    );

    public FoodListMessage(FoodList foodList) { this(foodList.serializeNBT()); }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(FoodListMessage message, IPayloadContext context) {
        context.enqueueWork(() -> FoodList.get(context.player()).deserializeNBT(message.tag));
    }
}
