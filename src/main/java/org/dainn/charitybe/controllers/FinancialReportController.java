package org.dainn.charitybe.controllers;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.FinancialReportDTO;
import org.dainn.charitybe.dtos.request.FinancialReportSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IFinancialReportService;
import org.springframework.data.domain.Page;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.FinancialReport.BASE)
@RequiredArgsConstructor
public class FinancialReportController {
    private final IFinancialReportService financialReportService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute FinancialReportSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));

        if (request.getPage() == null) {
            return ResponseEntity.ok(financialReportService.findAll(request.getStatus()));
        }

        Page<FinancialReportDTO> page = financialReportService.findAllByConditions(request);

        return ResponseEntity.ok(PageResponse.<FinancialReportDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }
}
