package com.jia.sollimepie.tracking.benefits;

import com.jia.sollimepie.tracking.CapabilityHandler;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EffectBenefitsCapability implements INBTSerializable<CompoundTag>, Iterable<EffectBenefit> {
    private final Set<EffectBenefit> effectBenefits = new HashSet<>();

    private static final String NBT_KEY_EFFECT_BENEFITS = "effect_benefits";

    public static EffectBenefitsCapability get(Player player) {
        return player.getData(CapabilityHandler.EFFECT_BENEFITS);
    }

    public void addEffectBenefit(EffectBenefit b) {
        effectBenefits.add(b);
    }

    public void addEffectBenefitUnique(EffectBenefit b) {
        effectBenefits.removeIf(other -> other.getName().equals(b.getName()));
        addEffectBenefit(b);
    }

    public void removeEffectBenefit(EffectBenefit b) {
        effectBenefits.remove(b);
    }

    public void clear() {
        effectBenefits.clear();
    }

    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        ListTag list = new ListTag();
        effectBenefits.stream().map(EffectBenefit::serializeNBT).forEach(list::add);
        tag.put(NBT_KEY_EFFECT_BENEFITS, list);

        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
       ListTag list = tag.getList(NBT_KEY_EFFECT_BENEFITS, Tag.TAG_COMPOUND);

        effectBenefits.clear();
        list.stream()
                .map(nbt-> (CompoundTag) nbt)
                .map(EffectBenefit::fromNBT)
                .forEach(effectBenefits::add);
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider) { return serializeNBT(); }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) { deserializeNBT(tag); }

    @Nonnull
    @Override
    public Iterator<EffectBenefit> iterator() {
        return effectBenefits.iterator();
    }

    public static class EffectsBenefitsNotFoundException extends RuntimeException {
        public EffectsBenefitsNotFoundException() {
            super("Player must have effect benefits capability attached, but none was found.");
        }
    }
}
