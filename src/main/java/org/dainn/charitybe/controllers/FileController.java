package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.services.ICloudinaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Endpoint.File.BASE)
@RequiredArgsConstructor
public class FileController {
    private final ICloudinaryService cloudinaryService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cloudinaryService.uploadFile(file));
    }

}
