package dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddItemRequest {
    private List<Item> items;

    @Data
    @AllArgsConstructor
    public static class Item {
        private String skuId;
        private int quantity;
    }
}
