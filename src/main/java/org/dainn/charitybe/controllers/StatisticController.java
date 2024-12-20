package org.dainn.charitybe.controllers;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.request.StatisticRequest;
import org.dainn.charitybe.services.IStatisticService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Endpoint.Statistic.BASE)
@RequiredArgsConstructor
public class StatisticController {
    private final IStatisticService statisticService;

    @GetMapping(Endpoint.Statistic.CAMPAIGN)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getByCampaign(@ModelAttribute StatisticRequest request) {
        return ResponseEntity.ok(statisticService.findCampaignStatistic(request));
    }

    @GetMapping(Endpoint.Statistic.EXPORT)
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> export(@ModelAttribute StatisticRequest request) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=statistic.xlsx")
                .body(statisticService.generateStatisticExcel(request));
    }
}
