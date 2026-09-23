package com.tarinoita.solsweetpotato.communication;

import com.tarinoita.solsweetpotato.ConfigHandler;
import com.tarinoita.solsweetpotato.SOLSweetPotato;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ConfigMessage(CompoundTag tag) implements CustomPacketPayload {
    public static final Type<ConfigMessage> TYPE = new Type<>(SOLSweetPotato.resourceLocation("config"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfigMessage> STREAM_CODEC = StreamCodec.of(
        (buffer, message) -> buffer.writeNbt(message.tag),
        buffer -> new ConfigMessage(buffer.readNbt())
    );

    public ConfigMessage() { this(ConfigHandler.serializeConfig()); }

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(ConfigMessage message, IPayloadContext context) {
        context.enqueueWork(() -> ConfigHandler.deserializeConfig(message.tag));
    }
}
