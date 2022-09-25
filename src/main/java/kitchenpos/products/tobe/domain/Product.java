package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import kitchenpos.products.tobe.domain.exception.InvalidProductPriceException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "product")
@Entity
public class Product {

    private static final int MINIMUM_PRICE = 0;

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Product() {
    }

    private Product(final UUID id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static Product create(final String name, final Long price, final PurgomalumClient purgomalumClient) {
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new InvalidProductNameException();
        }
        if (Objects.isNull(price) || price < MINIMUM_PRICE) {
            throw new InvalidProductPriceException();
        }
        return new Product(UUID.randomUUID(), name, BigDecimal.valueOf(price));
    }

    public void changePrice(final Long price) {
        if (Objects.isNull(price) || price < MINIMUM_PRICE) {
            throw new InvalidProductPriceException();
        }
        this.price = BigDecimal.valueOf(price);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public BigDecimal price() {
        return price;
    }
}
