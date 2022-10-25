package kitchenpos.eatinorders.order.infra;

import kitchenpos.eatinorders.order.domain.EatInOrder;
import kitchenpos.eatinorders.order.domain.EatInOrderRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaEatInOrderRepository extends EatInOrderRepository, JpaRepository<EatInOrder, UUID> {
}
