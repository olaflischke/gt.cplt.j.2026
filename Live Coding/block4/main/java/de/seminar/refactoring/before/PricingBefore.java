package de.seminar.refactoring.before;

import java.util.List;
import java.util.Map;

/**
 * Vorher: absichtlich mit Code-Smells versehene Rabattberechnung.
 * Nicht als Vorbild fuer produktiven Code verwenden.
 */
public final class PricingBefore {

    private static final String SAVE10_CODE = "SAVE10";
    private static final String VIP20_CODE = "VIP20";
    private static final String VIP_CUSTOMER_PREFIX = "VIP-";
    private static final int SAVE10_DISCOUNT_PERCENT = 10;
    private static final int VIP20_DISCOUNT_PERCENT = 20;
    private static final int PERCENTAGE_BASE = 100;
    private static final double SURCHARGE_THRESHOLD = 5000;
    private static final double SURCHARGE = 499;

    public double calc(Map<String, Object> order) {
        List<Map<String, Object>> items = (List<Map<String, Object>>) order.get("items");
        double total = calculateItemsTotal(items);
        total = applyDiscount(total, order);
        total = applySurcharge(total);
        return total;
    }

    private double applySurcharge(double total) {
        if (total < SURCHARGE_THRESHOLD) {
            total = total + SURCHARGE;
        }
        return total;
    }

    private double applyDiscount(double total, Map<String, Object> order) {
        String code = (String) order.get("code");
        if (code != null) {
            if (code.equals(SAVE10_CODE)) {
                total = total - (total * SAVE10_DISCOUNT_PERCENT / PERCENTAGE_BASE);
            } else if (code.equals(VIP20_CODE)) {
                String customer = (String) order.get("customer");
                if (customer != null && customer.startsWith(VIP_CUSTOMER_PREFIX)) {
                    total = total - (total * VIP20_DISCOUNT_PERCENT / PERCENTAGE_BASE);
                }
            }
        }
        return total;
    }

    private double calculateItemsTotal(List<Map<String, Object>> items) {
        double total = 0;
        for (Map<String, Object> item : items) {
            int qty = (Integer) item.get("qty");
            double price = (Double) item.get("price");
            if (qty > 0 && price >= 0) {
                total = total + qty * price;
            }
        }
        return total;
    }
}
