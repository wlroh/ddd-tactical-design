package kitchenpos.menus.menu.domain;

import kitchenpos.menus.menu.domain.vo.ProductSpecification;

import java.util.UUID;

public interface ProductContextClient {

    ProductSpecification findProductById(final UUID productId);
}
