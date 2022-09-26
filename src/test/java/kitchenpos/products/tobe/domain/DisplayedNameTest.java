package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.InvalidProductNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DisplayedNameTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @DisplayName("이름을 생성한다.")
    @Test
    void valueOf() {
        final DisplayedName displayedName = DisplayedName.valueOf("상품", purgomalumClient);

        assertThat(displayedName.value()).isEqualTo("상품");
    }

    @ParameterizedTest(name = "상품명에는 비어있거나, 비속어가 포함되면 안된다. name={0}")
    @NullSource
    @ValueSource(strings = {"비속어", "욕설"})
    void invalid_name(final String name) {
        assertThatThrownBy(() -> DisplayedName.valueOf(name, purgomalumClient))
                .isInstanceOf(InvalidProductNameException.class);
    }
}
