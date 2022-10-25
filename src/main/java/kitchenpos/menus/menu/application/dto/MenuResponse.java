package kitchenpos.menus.menu.application.dto;

import kitchenpos.menus.menu.domain.Menu;
import kitchenpos.menus.menu.domain.MenuProducts;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuResponse {

    private UUID id;
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductResponse> menuProducts;

    public MenuResponse(final UUID id,
                        final String name,
                        final Long price,
                        final UUID menuGroupId,
                        final boolean displayed,
                        final List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return menuProducts;
    }

    public static MenuResponse from(final Menu menu) {
        final List<MenuProductResponse> menuProductResponses = findMenuProductResponse(menu.menuProducts());
        return new MenuResponse(menu.id(), menu.displayedName().value(), menu.price().toLong(), menu.menuGroupId(), menu.isDisplayed(), menuProductResponses);
    }

    private static List<MenuProductResponse> findMenuProductResponse(final MenuProducts menuProducts) {
        return menuProducts.values()
                .stream()
                .map(MenuProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
