package kitchenpos.menus.menugroup.application;

import kitchenpos.menus.menugroup.application.dto.MenuGroupRequest;
import kitchenpos.menus.menugroup.application.dto.MenuGroupResponse;
import kitchenpos.menus.menugroup.domain.MenuGroup;
import kitchenpos.menus.menugroup.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupResponse create(final MenuGroupRequest.Create request) {
        final MenuGroup menuGroup = MenuGroup.create(request.getName());
        menuGroupRepository.save(menuGroup);
        return MenuGroupResponse.from(menuGroup);
    }

    public List<MenuGroupResponse> findAll() {
        final List<MenuGroup> menuGroups = menuGroupRepository.findAll();
        return menuGroups.stream()
                .map(MenuGroupResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
}
