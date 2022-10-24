package kitchenpos.menus.menugroup.application.dto;

import kitchenpos.menus.menugroup.domain.MenuGroup;

import java.util.UUID;

public class MenuGroupResponse {

    private UUID id;
    private String name;

    public MenuGroupResponse(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static MenuGroupResponse from(final MenuGroup menuGroup) {
        return new MenuGroupResponse(menuGroup.id(), menuGroup.name().value());
    }
}
