package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.request.UserRequest;
import org.dainn.charitybe.dtos.request.UserSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.User.BASE)
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAll(@ModelAttribute UserSearch request) {
        if (request.getPage() == null) {
            return ResponseEntity.ok(userService.findAll(request.getStatus()));
        }

        Page<UserDTO> page = userService.findWithSpec(request);

        return ResponseEntity.ok(PageResponse.<UserDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.User.ID)
    public ResponseEntity<?> get(@Min(1) @PathVariable Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody UserRequest dto) {
        return ResponseEntity.ok(userService.insert(dto));
    }

    @PutMapping(Endpoint.User.ID)
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody UserRequest dto) {
        dto.setId(id);
        return ResponseEntity.ok(userService.update(dto));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        userService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
