package de.seminar.refactoring.before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PricingBeforeCharacterizationTest {

    private final PricingBefore pricing = new PricingBefore();

    @Test
    void simpleOrderWithoutDiscount() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 2, "price", 1000.0)),
                "customer", "C-1"
        );
        assertEquals(2499.0, pricing.calc(order));
    }

    @Test
    void save10Discount() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 2, "price", 1000.0)),
                "code", "SAVE10",
                "customer", "C-1"
        );
        assertEquals(2299.0, pricing.calc(order));
    }

    @Test
    void vip20DiscountRequiresVipPrefix() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", 1000.0)),
                "code", "VIP20",
                "customer", "VIP-7"
        );
        assertEquals(1299.0, pricing.calc(order));
    }

    @Test
    void negativePriceItemIsIgnored() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", -500.0)),
                "customer", "C-1"
        );
        assertEquals(499.0, pricing.calc(order));
    }

    @ParameterizedTest
    @CsvSource({"0", "-1"})
    void calc_zeroOrNegativeQuantity_itemIsIgnored(int quantity) {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", quantity, "price", 1000.0))
        );

        double result = pricing.calc(order);

        assertEquals(499.0, result);
    }

    @Test
    void calc_multipleItems_sumsAllItems() {
        Map<String, Object> order = Map.of(
                "items", List.of(
                        Map.of("qty", 2, "price", 1000.0),
                        Map.of("qty", 1, "price", 500.0)
                )
        );

        double result = pricing.calc(order);

        assertEquals(2999.0, result);
    }

    @Test
    void calc_emptyItems_addsSurcharge() {
        Map<String, Object> order = Map.of("items", List.of());

        double result = pricing.calc(order);

        assertEquals(499.0, result);
    }

    @Test
    void calc_unknownDiscountCode_doesNotChangeTotal() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", 1000.0)),
                "code", "UNKNOWN"
        );

        double result = pricing.calc(order);

        assertEquals(1499.0, result);
    }

    @Test
    void calc_vipDiscountWithNonVipCustomer_doesNotApplyDiscount() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", 1000.0)),
                "code", "VIP20",
                "customer", "C-1"
        );

        double result = pricing.calc(order);

        assertEquals(1499.0, result);
    }

    @ParameterizedTest
    @CsvSource({"4999.0, 5498.0", "5000.0, 5000.0"})
    void calc_totalAtSurchargeThreshold_appliesSurchargeOnlyBelowThreshold(
            double subtotal, double expectedTotal) {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", subtotal))
        );

        double result = pricing.calc(order);

        assertEquals(expectedTotal, result);
    }

    @Test
    void calc_discountMovesSubtotalBelowThreshold_addsSurchargeAfterDiscount() {
        Map<String, Object> order = Map.of(
                "items", List.of(Map.of("qty", 1, "price", 5000.0)),
                "code", "SAVE10"
        );

        double result = pricing.calc(order);

        assertEquals(4999.0, result);
    }
}
