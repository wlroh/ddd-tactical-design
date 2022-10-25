package kitchenpos.eatinorders.ordertable.ui;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.ordertable.application.OrderTableService;
import kitchenpos.eatinorders.ordertable.application.dto.OrderTableResponse;
import kitchenpos.eatinorders.ordertable.domain.vo.GuestOfNumbers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/order-tables")
@RestController
public class OrderTableRestController {
    private final OrderTableService orderTableService;

    public OrderTableRestController(final OrderTableService orderTableService) {
        this.orderTableService = orderTableService;
    }

    @PostMapping
    public ResponseEntity<OrderTableResponse> create(@RequestBody final Name name) {
        final OrderTableResponse response = orderTableService.create(name);
        return ResponseEntity.created(URI.create("/api/order-tables/" + response.getId()))
            .body(response);
    }

    @PutMapping("/{orderTableId}/sit")
    public ResponseEntity<OrderTableResponse> sit(@PathVariable final UUID orderTableId) {
        return ResponseEntity.ok(orderTableService.sit(orderTableId));
    }

    @PutMapping("/{orderTableId}/clear")
    public ResponseEntity<Void> clear(@PathVariable final UUID orderTableId) {
        orderTableService.clear(orderTableId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{orderTableId}/number-of-guests")
    public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
        @PathVariable final UUID orderTableId,
        @RequestBody final GuestOfNumbers guestOfNumbers
    ) {
        return ResponseEntity.ok(orderTableService.changeNumberOfGuests(orderTableId, guestOfNumbers));
    }

    @GetMapping
    public ResponseEntity<List<OrderTableResponse>> findAll() {
        return ResponseEntity.ok(orderTableService.findAll());
    }
}
