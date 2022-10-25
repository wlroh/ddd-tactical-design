package kitchenpos.eatinorders.ordertable.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderTableRepository {
    OrderTable save(final OrderTable orderTable);

    Optional<OrderTable> findById(final UUID id);

    List<OrderTable> findAll();
}
