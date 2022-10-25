package kitchenpos.eatinorders.order.infra;

import kitchenpos.eatinorders.order.domain.MenuContextClient;
import kitchenpos.eatinorders.order.domain.vo.MenuSpecification;
import kitchenpos.menus.menu.domain.Menu;
import kitchenpos.menus.menu.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MenuContextRepositoryClient implements MenuContextClient {

    private final MenuRepository menuRepository;

    public MenuContextRepositoryClient(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public MenuSpecification findOrderMenuItemById(final UUID id) {
        final Menu menu = menuRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return new MenuSpecification(menu.id(), menu.price(), menu.isDisplayed());
    }
}
