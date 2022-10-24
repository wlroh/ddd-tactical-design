package kitchenpos.products.application.dto;

public class ProductRequest {

    public static class Create {
        private String name;
        private Long price;

        private Create() {
        }

        public String getName() {
            return name;
        }

        public Long getPrice() {
            return price;
        }
    }

    public static class ChangePrice {
        private Long price;

        private ChangePrice() {
        }

        public Long getPrice() {
            return price;
        }
    }
}
