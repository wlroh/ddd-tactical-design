package kitchenpos.products.application.dto;

import kitchenpos.products.domain.Product;

import java.util.UUID;

public class ProductResponse {

    private UUID id;
    private String name;
    private Long price;

    public ProductResponse(final UUID id, final String name, final Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public static ProductResponse from(final Product product) {
        return new ProductResponse(product.id(), product.displayedName().value(), product.price().toLong());
    }
}
