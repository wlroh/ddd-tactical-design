package kitchenpos.menus.menu.application;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.application.dto.MenuResponse;
import kitchenpos.menus.menu.domain.Menu;
import kitchenpos.menus.menu.domain.MenuFactory;
import kitchenpos.menus.menu.domain.MenuRepository;
import kitchenpos.menus.menu.domain.vo.MenuSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuFactory menuFactory;

    public MenuService(final MenuRepository menuRepository, final MenuFactory menuFactory) {
        this.menuRepository = menuRepository;
        this.menuFactory = menuFactory;
    }

    @Transactional
    public MenuResponse create(final MenuSpecification menuSpecification) {
        final Menu menu = menuFactory.create(menuSpecification);
        menuRepository.save(menu);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse changePrice(final UUID menuId, final Price price) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
        menu.changePrice(price);
        menuRepository.save(menu);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse display(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
        menu.show();
        menuRepository.save(menu);
        return MenuResponse.from(menu);
    }

    @Transactional
    public MenuResponse hide(final UUID menuId) {
        final Menu menu = menuRepository.findById(menuId).orElseThrow(IllegalArgumentException::new);
        menu.hide();
        menuRepository.save(menu);
        return MenuResponse.from(menu);
    }

    public List<MenuResponse> findAll() {
        final List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(MenuResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
