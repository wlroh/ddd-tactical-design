package kitchenpos.menus.menu.application.dto;

import kitchenpos.menus.menu.domain.MenuProduct;

import java.util.UUID;

public class MenuProductResponse {

    private UUID productId;
    private Long price;
    private Long quantity;

    public MenuProductResponse(final UUID productId, final Long price, final Long quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getPrice() {
        return price;
    }

    public Long getQuantity() {
        return quantity;
    }

    public static MenuProductResponse from(final MenuProduct menuProduct) {
        return new MenuProductResponse(menuProduct.productId(), menuProduct.price().toLong(), menuProduct.quantity().value());
    }
}
