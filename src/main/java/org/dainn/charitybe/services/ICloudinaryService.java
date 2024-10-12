package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.response.CloudinaryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ICloudinaryService {
    CloudinaryResponse uploadFile(MultipartFile file);
}
