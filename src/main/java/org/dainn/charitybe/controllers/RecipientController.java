package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.RecipientDTO;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IRecipientService;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.dainn.charitybe.dtos.request.RecipientSearch;

import java.util.List;

@RestController
@RequestMapping(Endpoint.Recipient.BASE)
@RequiredArgsConstructor
public class RecipientController {
    private final IRecipientService recipientService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute RecipientSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));
        if (request.getPage() == null) {
            return ResponseEntity.ok(recipientService.findAll());
        }
        Page<RecipientDTO> page = recipientService.findAllByConditions(request);

        return ResponseEntity.ok(PageResponse.<RecipientDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Recipient.ID)
    public ResponseEntity<?> get(@Min(1) @PathVariable Integer id) {
        return ResponseEntity.ok(recipientService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RecipientDTO dto) {
        return ResponseEntity.ok(recipientService.insert(dto));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        recipientService.delete(ids);
        return ResponseEntity.ok().build();
    }
}