package com.example.bookmart.project.Request;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class UploadImageRequest {
    @NotNull
    private List<MultipartFile> images;

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }
}

