package kitchenpos.eatinorders.ordertable.application.dto;

import kitchenpos.eatinorders.ordertable.domain.OrderTable;

import java.util.UUID;

public class OrderTableResponse {

    private UUID id;
    private String name;
    private Integer guestOfNumbers;

    public OrderTableResponse(UUID id, String name, Integer guestOfNumbers) {
        this.id = id;
        this.name = name;
        this.guestOfNumbers = guestOfNumbers;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGuestOfNumbers() {
        return guestOfNumbers;
    }

    public static OrderTableResponse from(final OrderTable orderTable) {
        return new OrderTableResponse(orderTable.id(), orderTable.name().value(), orderTable.guestOfNumbers().value());
    }
}
