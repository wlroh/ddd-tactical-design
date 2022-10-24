package kitchenpos.menus.menugroup.ui;

import kitchenpos.menus.menugroup.application.MenuGroupService;
import kitchenpos.menus.menugroup.application.dto.MenuGroupRequest;
import kitchenpos.menus.menugroup.application.dto.MenuGroupResponse;
import kitchenpos.menus.menugroup.domain.MenuGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuGroupService menuGroupService;

    public MenuGroupRestController(final MenuGroupService menuGroupService) {
        this.menuGroupService = menuGroupService;
    }

    @PostMapping
    public ResponseEntity<MenuGroupResponse> create(@RequestBody final MenuGroupRequest.Create request) {
        final MenuGroupResponse response = menuGroupService.create(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
            .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupResponse>> findAll() {
        return ResponseEntity.ok(menuGroupService.findAll());
    }
}
