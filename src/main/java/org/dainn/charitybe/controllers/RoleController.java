package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.RoleDTO;
import org.dainn.charitybe.dtos.request.RoleSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.impls.RoleService;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.Role.BASE)
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAll(@ModelAttribute RoleSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));
        if (request.getPage() == null) {
            return ResponseEntity.ok(roleService.findAll(request.getStatus()));
        }
        Page<RoleDTO> page = roleService.findAllByName(request);
        return ResponseEntity.ok(PageResponse.<RoleDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Role.NAME)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> get(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody RoleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.insert(dto));
    }

    @PutMapping(Endpoint.Role.ID)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id, @Valid @RequestBody RoleDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(roleService.update(dto));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        roleService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
