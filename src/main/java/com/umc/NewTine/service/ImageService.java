//package com.umc.NewTine.service;
//
//import com.umc.NewTine.domain.Image;
//import com.umc.NewTine.domain.User;
//import com.umc.NewTine.dto.request.ImageRequestDto;
//import com.umc.NewTine.dto.response.ImageResponseDto;
//import com.umc.NewTine.repository.ImageRepository;
//import com.umc.NewTine.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ImageService {
//
//    private final ImageRepository imageRepository;
//    private final UserRepository userRepository;
//
//    @Value("${file.path}")
//    private String uploadFolder;
//
//    public void upload(ImageRequestDto imageRequestDto, String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
//        MultipartFile file = imageRequestDto.getFile();
//
//        UUID uuid = UUID.randomUUID();
//        String imageFileName = uuid + "_" + file.getOriginalFilename();
//
//        File destinationFile = new File(uploadFolder + imageFileName);
//        System.out.println("uploadFolder+imageFileName = " + uploadFolder+imageFileName);
//
//        try {
//            file.transferTo(destinationFile);
//
//            Image image = imageRepository.findByUser(user);
//            if (image != null) {
//                // 이미지가 이미 존재하면 url 업데이트
//                image.updateUrl("/image/" + imageFileName);
//            } else {
//                // 이미지가 없으면 객체 생성 후 저장
//                image = Image.builder()
//                        .user(user)
//                        .url("/image/" + imageFileName)
//                        .build();
//            }
//            imageRepository.save(image);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public ImageResponseDto findImage(String email) {
//        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("이메일이 존재하지 않습니다."));
//        Image image = imageRepository.findByUser(user);
//
//        String defaultImageUrl = "/profileImages/anonymous.png";
//
//        if (image == null) {
//            return ImageResponseDto.builder()
//                    .url(defaultImageUrl)
//                    .build();
//        } else {
//            return ImageResponseDto.builder()
//                    .url(image.getUrl())
//                    .build();
//        }
//    }
//}
