package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.SponsorDTO;
import org.dainn.charitybe.dtos.request.SponsorSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.ISponsorService;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.Sponsor.BASE)
@RequiredArgsConstructor
public class SponsorController {
    private final ISponsorService sponsorService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute SponsorSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));
        if (request.getPage() == null) {
            return ResponseEntity.ok(sponsorService.findAll(request.getStatus()));
        }
        Page<SponsorDTO> page = sponsorService.findAllByName(request);

        return ResponseEntity.ok(PageResponse.<SponsorDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Category.ID)
    public ResponseEntity<?> get(@Min(1) @PathVariable Integer id) {
        return ResponseEntity.ok(sponsorService.findById(id));
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody SponsorDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sponsorService.insert(dto));
    }

    @PutMapping(Endpoint.Category.ID)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id,
                                    @Valid @RequestBody SponsorDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(sponsorService.update(dto));
    }

    @DeleteMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        sponsorService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
