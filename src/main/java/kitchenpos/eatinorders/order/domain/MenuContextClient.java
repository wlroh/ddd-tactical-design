package kitchenpos.eatinorders.order.domain;

import kitchenpos.eatinorders.order.domain.vo.MenuSpecification;

import java.util.UUID;

public interface MenuContextClient {

    MenuSpecification findOrderMenuItemById(final UUID id);
}
