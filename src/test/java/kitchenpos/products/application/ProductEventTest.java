package kitchenpos.products.application;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.event.ProductPriceChangedEvent;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RecordApplicationEvents
@Transactional
@SpringBootTest
public class ProductEventTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationEvents events;

    private Profanity profanity;

    @BeforeEach
    void setUp() {
        profanity = new FakeProfanity();
    }

    @Test
    void productPriceChangedEvent() {
        // given
        final DisplayedName displayedName = DisplayedName.valueOf("상품", profanity);
        final Product product = Product.create(displayedName, 10_000L);

        // when
        product.changePrice(50_000L);
        productRepository.save(product);

        // then
        assertThat(events.stream(ProductPriceChangedEvent.class).count()).isEqualTo(1);
    }
}
