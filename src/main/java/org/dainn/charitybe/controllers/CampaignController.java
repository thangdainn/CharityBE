package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.CampaignDTO;
import org.dainn.charitybe.dtos.request.CampaignSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.ICampaignService;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Project.BASE)
@RequiredArgsConstructor
public class CampaignController {
    private final ICampaignService projectService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute CampaignSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));
        if (request.getPage() == null) {
            return ResponseEntity.ok(projectService.findAll());
        }
        Page<CampaignDTO> page = projectService.findAllByFilters(request);

        return ResponseEntity.ok(PageResponse.<CampaignDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Project.CODE)
    public ResponseEntity<?> get( @PathVariable String code) {
        return ResponseEntity.ok(projectService.findByCode(code));
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CampaignDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.insert(dto));
    }

    @PutMapping(Endpoint.Project.ID)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id,
                                    @Valid @RequestBody CampaignDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(projectService.update(dto));
    }
}
