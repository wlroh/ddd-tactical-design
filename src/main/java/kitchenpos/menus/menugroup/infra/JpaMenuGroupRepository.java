package kitchenpos.menus.menugroup.infra;

import kitchenpos.menus.menugroup.domain.MenuGroup;
import kitchenpos.menus.menugroup.domain.MenuGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaMenuGroupRepository extends MenuGroupRepository, JpaRepository<MenuGroup, UUID> {
}
