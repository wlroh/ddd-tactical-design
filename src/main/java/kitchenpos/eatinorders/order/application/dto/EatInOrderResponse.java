package kitchenpos.eatinorders.order.application.dto;

import kitchenpos.eatinorders.order.domain.EatInOrder;
import kitchenpos.eatinorders.order.domain.EatInOrderLineItems;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EatInOrderResponse {

    private UUID id;
    private UUID orderTableId;
    private LocalDateTime orderDateTime;
    private String status;
    private List<EatInOrderLineItemResponse> eatInOrderLineItems;

    public EatInOrderResponse(UUID id, UUID orderTableId, LocalDateTime orderDateTime, String status, List<EatInOrderLineItemResponse> eatInOrderLineItems) {
        this.id = id;
        this.orderTableId = orderTableId;
        this.orderDateTime = orderDateTime;
        this.status = status;
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public String getStatus() {
        return status;
    }

    public List<EatInOrderLineItemResponse> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    public static EatInOrderResponse from(final EatInOrder eatInOrder) {
        final List<EatInOrderLineItemResponse> eatInOrderLineItemResponses = getEatInOrderLineItemResponses(eatInOrder.eatInOrderLineItems());
        return new EatInOrderResponse(eatInOrder.id(), eatInOrder.orderTableId(), eatInOrder.orderDateTime(), eatInOrder.status().name(), eatInOrderLineItemResponses);
    }

    private static List<EatInOrderLineItemResponse> getEatInOrderLineItemResponses(final EatInOrderLineItems eatInOrderLineItems) {
        return eatInOrderLineItems.values().stream().map(EatInOrderLineItemResponse::from).collect(Collectors.toUnmodifiableList());
    }
}
