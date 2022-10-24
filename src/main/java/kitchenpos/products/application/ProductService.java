package kitchenpos.products.application;

import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private final ProductRepository productRepository;
    private final Profanity profanity;

    public ProductService(
        final ProductRepository productRepository,
        final Profanity profanity
    ) {
        this.productRepository = productRepository;
        this.profanity = profanity;
    }

    @Transactional
    public ProductResponse create(final ProductRequest.Create request) {
        final DisplayedName displayedName = DisplayedName.valueOf(request.getName(), profanity);
        final Product product = Product.create(displayedName, request.getPrice());
        productRepository.save(product);
        return ProductResponse.from(product);
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest.ChangePrice request) {
        final Product product = productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
        product.changePrice(request.getPrice());
        productRepository.save(product);
        return ProductResponse.from(product);
    }

    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
