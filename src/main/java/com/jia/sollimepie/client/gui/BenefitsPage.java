package com.jia.sollimepie.client.gui;

import com.jia.sollimepie.client.gui.elements.UILabel;
import com.jia.sollimepie.tracking.benefits.BenefitInfo;
import com.jia.sollimepie.utils.RomanNumber;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.ResourceLocationException;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.core.registries.BuiltInRegistries;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.jia.sollimepie.lib.Localization.localized;

public class BenefitsPage extends Page {
    private static final int BENEFITS_PER_PAGE = 3;
    private final Color activeColor;

    private BenefitsPage(Rectangle frame, String header, List<BenefitInfo> benefitInfo, Color activeColor) {
        super(frame, header);
        this.activeColor = activeColor;

        for (BenefitInfo info : benefitInfo) {
            addBenefitInfo(info);
        }
    }

    public static List<BenefitsPage> pages(Rectangle frame, String header, List<BenefitInfo> benefitInfo, Color activeColor) {
        List<BenefitsPage> pages = new ArrayList<>();
        for (int startIndex = 0; startIndex < benefitInfo.size(); startIndex += BENEFITS_PER_PAGE) {
            int endIndex = Math.min(startIndex + BENEFITS_PER_PAGE, benefitInfo.size());
            pages.add(new BenefitsPage(frame, header, benefitInfo.subList(startIndex, endIndex), activeColor));
        }
        return pages;
    }

    private void addBenefitInfo(BenefitInfo info) {
        String thresh = "" + info.threshold;
        String name = info.name;
        double value = info.value;

        if ("effect".equals(info.type)) {
            name = getEffectName(name);
            int amplifier = (int) value;
            name = name + " " + RomanNumber.toRoman(amplifier + 1);
        }
        else if ("attribute".equals(info.type)) {
            name = getAttributeName(name);
            String op = "+";
            if (value < 0) {
                op = "-";
            }
            String modifierValue = op + Math.abs(value);
            name = name + " " + modifierValue;
        }

        UILabel thresholdLabel = new UILabel(localized("gui", "food_book.benefits.threshold_label")
                + ": " + thresh);
        thresholdLabel.color = activeColor;

        if (activeColor.equals(FoodBookScreen.activeGreen)) {
            thresholdLabel.tooltip = localized("gui", "food_book.benefits.active_tooltip");
        }
        else if (activeColor.equals((FoodBookScreen.inactiveRed))) {
            thresholdLabel.tooltip = localized("gui", "food_book.benefits.inactive_tooltip");
        }

        UILabel nameLabel = new UILabel(name);
        nameLabel.color = FoodBookScreen.lessBlack;

        mainStack.addChild(thresholdLabel);
        mainStack.addChild(nameLabel);
        mainStack.addChild(makeSeparatorLine());
        updateMainStack();
    }

    private String getAttributeName(String name){
        Attribute attribute;
        try {
            attribute = BuiltInRegistries.ATTRIBUTE.get(ResourceLocation.parse("generic.speed".equals(name) ? "generic.movement_speed" : name));
        }
        catch (ResourceLocationException e) {
            return localized("gui", "food_book.benefits.invalid", name);
        }

        if (attribute == null) {
            return localized("gui", "food_book.benefits.invalid", name);
        }

        return I18n.get(attribute.getDescriptionId());
    }

    private String getEffectName(String name) {
        MobEffect effect;
        try {
            effect = BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.parse(name));
        }
        catch (ResourceLocationException e) {
            return localized("gui", "food_book.benefits.invalid", name);
        }

        if (effect == null) {
            return localized("gui", "food_book.benefits.invalid", name);
        }

        return I18n.get(effect.getDisplayName().getString());
    }
}
