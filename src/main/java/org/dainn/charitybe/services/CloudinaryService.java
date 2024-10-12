package org.dainn.charitybe.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.exceptions.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            return cloudinary.uploader().upload(file.getBytes(), null).get("url").toString();
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
    }
}
