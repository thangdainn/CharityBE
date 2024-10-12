package org.dainn.charitybe.services.impls;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.response.CloudinaryResponse;
import org.dainn.charitybe.exceptions.FileUploadException;
import org.dainn.charitybe.services.ICloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinaryService {
    private final Cloudinary cloudinary;

    @Override
    public CloudinaryResponse uploadFile(MultipartFile file) {
        try {
            String url = cloudinary.uploader().upload(file.getBytes(), null).get("url").toString();
            return new CloudinaryResponse(url);
        } catch (Exception e) {
            throw new FileUploadException(e.getMessage());
        }
    }
}
