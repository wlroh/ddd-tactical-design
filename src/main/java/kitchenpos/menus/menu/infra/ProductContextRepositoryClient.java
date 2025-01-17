package kitchenpos.menus.menu.infra;

import kitchenpos.menus.menu.domain.ProductContextClient;
import kitchenpos.menus.menu.domain.vo.ProductSpecification;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductContextRepositoryClient implements ProductContextClient {

    private final ProductRepository productRepository;

    public ProductContextRepositoryClient(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductSpecification findProductById(final UUID productId) {
        final Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        return new ProductSpecification(product.id(), product.price().toLong());
    }
}
