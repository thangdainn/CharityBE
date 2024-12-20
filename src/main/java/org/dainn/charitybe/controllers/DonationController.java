package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.DonationDTO;
import org.dainn.charitybe.dtos.request.DonationSearch;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IDonationService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Endpoint.Donation.BASE)
@RequiredArgsConstructor
public class DonationController {
    private final IDonationService donationService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute DonationSearch request) {
        Page<DonationDTO> page = donationService.findAllByFilters(request);

        return ResponseEntity.ok(PageResponse.<DonationDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Donation.EXPORT)
    public ResponseEntity<byte[]> export(@PathVariable Integer campaignId) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=donations.xlsx")
                .body(donationService.exportDonationsByCampaignId(campaignId));
    }

    @GetMapping(Endpoint.Donation.CAMPAIGN)
    public ResponseEntity<?> getByCampaign(@PathVariable Integer campaignId) {
        return ResponseEntity.ok(donationService.findByCampaignId(campaignId));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody DonationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donationService.insert(dto));
    }
}
