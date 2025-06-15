package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class BagResponse {
    private BagData data;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class BagData {
        private String id;
        private String currencyCode;
        private List<Item> items;
        private int itemCount;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class Item {
        private String itemId;
        private String productId;
        private String productName;
        private String size;
        private String color;
        private String sku;
        private int quantity;
        private double price;
        private double originalPrice;
    }
}
