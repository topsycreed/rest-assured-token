package tests;

import controllers.BagController;
import dto.BagResponse;
import extensions.GuestTokenExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(GuestTokenExtension.class)
class BagApiTests {
    private final BagController bag = new BagController();

    private static final String TEST_SKU_ID = "0042501858";

    @Test
    void addItemTest() {
        int qty = 1;

        bag.addItem(TEST_SKU_ID, qty)
                .then()
                .statusCode(202);

        BagResponse response = bag.getBag();

        assertThat(response.getData().getItemCount())
                .isEqualTo(1);

        assertThat(response.getData().getItems())
                .as("Bag should contain item with sku %s and quantity %d", TEST_SKU_ID, qty)
                .anySatisfy(item -> {
                    assertThat(item.getSku()).isEqualTo(TEST_SKU_ID);
                    assertThat(item.getQuantity()).isEqualTo(qty);
                });
    }
}
