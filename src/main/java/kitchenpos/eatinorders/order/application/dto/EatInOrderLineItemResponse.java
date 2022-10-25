package kitchenpos.eatinorders.order.application.dto;

import kitchenpos.eatinorders.order.domain.EatInOrderLineItem;

import java.util.UUID;

public class EatInOrderLineItemResponse {

    private UUID menuId;
    private Long price;
    private Integer quantity;

    public EatInOrderLineItemResponse(UUID menuId, Long price, Integer quantity) {
        this.menuId = menuId;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getMenuId() {
        return menuId;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public static EatInOrderLineItemResponse from(final EatInOrderLineItem eatInOrderLineItem) {
        return new EatInOrderLineItemResponse(eatInOrderLineItem.menuId(), eatInOrderLineItem.price().toLong(), eatInOrderLineItem.quantity());
    }
}
