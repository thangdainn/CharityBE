package org.dainn.charitybe.controllers;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IFinancialReportService;
import org.springframework.data.domain.Page;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Endpoint.FinancialReport.BASE)
@RequiredArgsConstructor
public class FinancialReportController {
    private final IFinancialReportService financialReportService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute FinancialReportSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));

        if (request.getPage() == null) {
            return ResponseEntity.ok(financialReportService.findAll());
        }

        Page<FinancialReportDTO> page = financialReportService.findAllByConditions(request);

        return ResponseEntity.ok(PageResponse.<FinancialReportDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.FinancialReport.ID)
    public ResponseEntity<?> get(@Min(1) @PathVariable Integer id) {
        return ResponseEntity.ok(financialReportService.findById(id));
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody FinancialReportDTO dto) {
        return ResponseEntity.ok(financialReportService.insert(dto));
    }

    @PutMapping(Endpoint.FinancialReport.ID)
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id, @RequestBody FinancialReportDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(financialReportService.update(dto));
    }

    @DeleteMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        financialReportService.delete(ids);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
