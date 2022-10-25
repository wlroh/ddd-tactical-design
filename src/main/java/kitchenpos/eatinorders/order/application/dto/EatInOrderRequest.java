package kitchenpos.eatinorders.order.application.dto;

import kitchenpos.eatinorders.order.domain.vo.OrderLineItemSpecification;

import java.util.List;
import java.util.UUID;

public class EatInOrderRequest {

    private UUID orderTableId;
    private List<OrderLineItemSpecification> orderLineItems;

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public List<OrderLineItemSpecification> getOrderLineItems() {
        return orderLineItems;
    }
}
