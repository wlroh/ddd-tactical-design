package kitchenpos.menus.menu.domain;

import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.menu.domain.exception.InvalidMenuPriceException;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private DisplayedName displayedName;

    @Embedded
    private Price price;

    @Column(name = "menu_group_id", nullable = false)
    private UUID menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    private Menu(UUID id, DisplayedName displayedName, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    protected static Menu create(final DisplayedName displayedName, final Price price, final UUID menuGroupId, final boolean displayed, final MenuProducts menuProducts) {
        validatePrice(price, menuProducts.totalAmount());
        final Menu menu = new Menu(UUID.randomUUID(), displayedName, price, menuGroupId, displayed, menuProducts);
        menu.menuProducts.makeRelation(menu);
        return menu;
    }

    private static void validatePrice(final Price price, final Price totalAmount) {
        if (price.isLessThan(totalAmount) || price.equals(totalAmount)) {
            return;
        }
        throw new InvalidMenuPriceException(price.toLong(), totalAmount.toLong());
    }

    public void changePrice(Price price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("변경할 금액이 필요합니다.");
        }
        validatePrice(price, menuProducts.totalAmount());
        this.price = price;
    }

    public void changeMenuProductPrice(final UUID productId, final Price price) {
        menuProducts.changePrice(productId, price);
        if (this.price.isBiggerThan(menuProducts.totalAmount())) {
            hide();
        }
    }

    public void show() {
        if (price.isBiggerThan(menuProducts.totalAmount())) {
            throw new IllegalStateException();
        }
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public UUID id() {
        return id;
    }

    public DisplayedName displayedName() {
        return displayedName;
    }

    public Price price() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID menuGroupId() {
        return menuGroupId;
    }

    public MenuProducts menuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
