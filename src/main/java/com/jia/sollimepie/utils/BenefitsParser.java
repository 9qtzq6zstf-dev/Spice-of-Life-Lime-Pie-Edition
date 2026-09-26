package com.jia.sollimepie.utils;

import com.jia.sollimepie.SOLLimePie;
import com.jia.sollimepie.tracking.benefits.AttributeBenefit;
import com.jia.sollimepie.tracking.benefits.Benefit;
import com.jia.sollimepie.tracking.benefits.EffectBenefit;

import java.util.ArrayList;
import java.util.List;

public class BenefitsParser {
    public static List<List<Benefit>> parse(List<String> unparsed) {
        List<List<Benefit>> allBenefits = new ArrayList<>();

        int thresh = 0;
        for (String s : unparsed) {
            String[] thresholdBenefitsString = s.split(";", 0);
            List<Benefit> thresholdBenefits = new ArrayList<>();

            for (String benefitString : thresholdBenefitsString) {
                if (benefitString.isEmpty()) {
                    continue;
                }
                boolean is_detriment = false;
                switch(benefitString.charAt(0)){
                    case '-':
                        is_detriment = true;
                    case '+':
                        benefitString = benefitString.substring(1);
                    default:
                        break;
                }
                String[] benefitArgs = benefitString.split(",", 0);
                int len = benefitArgs.length;

                if (len < 2) {
                    SOLLimePie.LOGGER.warn("Invalid benefit specification: {}", benefitString);
                    continue;
                }

                String benefitType;
                if ("attribute".equals(benefitArgs[0])) {
                    if (len < 3) {
                        SOLLimePie.LOGGER.warn("Need to specify a value when defining an attribute benefit: {}", benefitString);
                        continue;
                    }
                    benefitType = "attribute";
                } else if ("effect".equals(benefitArgs[0])) {
                    benefitType = "effect";
                } else {
                    SOLLimePie.LOGGER.warn("Invalid benefit type: {} in string {}", benefitArgs[0], benefitString);
                    continue;
                }

                String benefitName = benefitArgs[1];
                double benefitValue = 0;
                if (len > 2) {
                    try {
                        benefitValue = Double.parseDouble(benefitArgs[2]);
                    } catch (NumberFormatException e) {
                        SOLLimePie.LOGGER.warn("Invalid benefit value: {}", benefitString);
                        continue;
                    }
                }

                if ("attribute".equals(benefitType)) {
                    thresholdBenefits.add(new AttributeBenefit(benefitName, benefitValue, thresh, is_detriment));
                }
                else {
                    thresholdBenefits.add(new EffectBenefit(benefitName, benefitValue, thresh, is_detriment));
                }
            }
            allBenefits.add(thresholdBenefits);
            thresh++;
        }

        return allBenefits;
    }
}
