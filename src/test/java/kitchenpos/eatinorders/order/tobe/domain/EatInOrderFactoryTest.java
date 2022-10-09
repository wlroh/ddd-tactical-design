package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.eatinorders.order.tobe.domain.vo.OrderLineItem;
import kitchenpos.eatinorders.order.tobe.infra.MenuContextRepositoryClient;
import kitchenpos.menus.menu.dto.MenuDto;
import kitchenpos.menus.menu.dto.MenuProductDto;
import kitchenpos.menus.menu.tobe.domain.InMemoryMenuRepository;
import kitchenpos.menus.menu.tobe.domain.Menu;
import kitchenpos.menus.menu.tobe.domain.MenuFactory;
import kitchenpos.menus.menu.tobe.domain.MenuRepository;
import kitchenpos.menus.menugroup.tobe.domain.InMemoryMenuGroupRepository;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroup;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroupRepository;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderFactoryTest {

    private MenuFactory menuFactory;
    private ProductRepository productRepository;
    private MenuGroupRepository menuGroupRepository;
    private MenuRepository menuRepository;
    private Profanity profanity;
    private MenuContextClient menuContextClient;

    private Menu menu;
    private MenuGroup menuGroup;
    private Product product;

    @BeforeEach
    void setUp() {
        menuFactory = new MenuFactory();
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        profanity = new FakeProfanity();
        menuContextClient = new MenuContextRepositoryClient(menuRepository);

        menuGroup = MenuGroup.create("추천메뉴");
        menuGroupRepository.save(menuGroup);

        final DisplayedName productName = DisplayedName.valueOf("치킨", profanity);
        product = Product.create(productName, 15_000L);
        productRepository.save(product);

        final MenuProductDto menuProductDto = new MenuProductDto(product.id(), 1L);
        final MenuDto menuDto = new MenuDto("치킨메뉴", 15_000L, menuGroup.id(), true, List.of(menuProductDto));
        menu = menuFactory.create(menuDto, menuGroupRepository, productRepository, profanity);
        menuRepository.save(menu);
    }

    @DisplayName("매장주문을 생성한다.")
    @Nested
    class CreateTest {

        @Test
        void success() {
            final OrderLineItem orderLineItem = new OrderLineItem(menu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, (orderTableId) -> false);

            final EatInOrder eatInOrder = eatInOrderFactory.create(UUID.randomUUID(), List.of(orderLineItem));

            assertThat(eatInOrder).isNotNull();
        }

        @DisplayName("사용중인 주문테이블만 매장 주문 생성이 가능합니다.")
        @Test
        void error_1() {
            final OrderLineItem orderLineItem = new OrderLineItem(menu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, (orderTableId) -> true);

            assertThatThrownBy(() -> eatInOrderFactory.create(UUID.randomUUID(), List.of(orderLineItem)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("사용중인 주문테이블만 매장 주문이 가능합니다.");
        }

        @DisplayName("전시 중인 메뉴만 주문이 가능합니다.")
        @Test
        void error_2() {
            final Menu findMenu = menuRepository.findById(menu.id()).get();
            findMenu.hide();
            final OrderLineItem orderLineItem = new OrderLineItem(findMenu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, (orderTableId) -> false);

            assertThatThrownBy(() -> eatInOrderFactory.create(UUID.randomUUID(), List.of(orderLineItem)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("전시 중인 메뉴만 주문이 가능합니다.");
        }

        @DisplayName("주문상품의 가격은 메뉴 가격과 동일해야 한다.")
        @Test
        void error_3() {
            final OrderLineItem orderLineItem = new OrderLineItem(menu.id(), 25_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, (orderTableId) -> false);

            assertThatThrownBy(() -> eatInOrderFactory.create(UUID.randomUUID(), List.of(orderLineItem)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주문상품의 가격은 메뉴 가격과 동일해야 합니다.");
        }
    }
}
