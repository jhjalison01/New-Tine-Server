package com.umc.NewTine.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequestDto {

    private MultipartFile file;
}
