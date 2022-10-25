package kitchenpos.eatinorders.ordertable.domain;

import java.util.UUID;

@FunctionalInterface
public interface CleanUpPolicy {

    boolean isMatchCondition(final UUID orderTableId);
}
