package kitchenpos.eatinorders.order.domain;

import kitchenpos.common.annotation.DomainService;
import kitchenpos.eatinorders.ordertable.domain.CleanUpPolicy;

import java.util.UUID;

@DomainService
public class LastEatInOrderPolicy implements CleanUpPolicy {

    private final EatInOrderRepository eatInOrderRepository;

    public LastEatInOrderPolicy(final EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    @Override
    public boolean isMatchCondition(final UUID orderTableId) {
        return !eatInOrderRepository.existsByOrderTableIdAndStatusNot(orderTableId, EatInOrderStatus.COMPLETED);
    }
}
