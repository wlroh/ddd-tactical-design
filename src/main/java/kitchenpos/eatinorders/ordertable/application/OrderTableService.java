package kitchenpos.eatinorders.ordertable.application;

import kitchenpos.common.domain.vo.Name;
import kitchenpos.eatinorders.ordertable.application.dto.OrderTableResponse;
import kitchenpos.eatinorders.ordertable.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.domain.OrderTableCleanUp;
import kitchenpos.eatinorders.ordertable.domain.OrderTableRepository;
import kitchenpos.eatinorders.ordertable.domain.vo.GuestOfNumbers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderTableService {

    private final OrderTableRepository orderTableRepository;
    private final OrderTableCleanUp orderTableCleanUp;

    public OrderTableService(final OrderTableRepository orderTableRepository, final OrderTableCleanUp orderTableCleanUp) {
        this.orderTableRepository = orderTableRepository;
        this.orderTableCleanUp = orderTableCleanUp;
    }

    @Transactional
    public OrderTableResponse create(final Name name) {
        final OrderTable orderTable = OrderTable.createEmptyTable(name);
        orderTableRepository.save(orderTable);
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public OrderTableResponse sit(final UUID orderTableId) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
            .orElseThrow(NoSuchElementException::new);
        orderTable.use();
        orderTableRepository.save(orderTable);
        return OrderTableResponse.from(orderTable);
    }

    @Transactional
    public void clear(final UUID orderTableId) {
        orderTableCleanUp.clear(orderTableId);
    }

    @Transactional
    public OrderTableResponse changeNumberOfGuests(final UUID orderTableId, final GuestOfNumbers guestOfNumbers) {
        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);
        orderTable.changeGuestOfNumbers(guestOfNumbers);
        orderTableRepository.save(orderTable);
        return OrderTableResponse.from(orderTable);
    }

    public List<OrderTableResponse> findAll() {
        final List<OrderTable> orderTables = orderTableRepository.findAll();
        return orderTables.stream()
                .map(OrderTableResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
