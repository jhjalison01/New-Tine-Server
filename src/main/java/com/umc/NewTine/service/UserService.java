package com.umc.NewTine.service;

import com.umc.NewTine.domain.User;
import com.umc.NewTine.dto.SignupRequestDto;
import com.umc.NewTine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
//    private final JavaMailSender javaMailSender;
//
//    @Value("${spring.mail.username}")
//    private String sender;

//    private static final String FROM_ADDRESS = "bje5774@gmail.com";
    @Transactional
    public ResponseEntity<Object> joinUser(SignupRequestDto signupRequestDto){

        if (signupRequestDto.getEmail().isEmpty()||signupRequestDto.getPassword().isEmpty()) //||signupRequestDto.getPhone().isEmpty()
            throw new IllegalArgumentException("빈칸은 허용하지 않습니다");

        User user = signupRequestDto.toEntity(signupRequestDto);

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user.getEmail()+"님이 성공적으로 가입되었습니다.");
    }


    public void deleteUserById(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));
        userRepository.deleteById(id);
    }


//    public UserResponseDto findUserById(Long id) {
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("User를 찾을 수 없습니다."));
//
//        return new UserResponseDto(user);
//    }


    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
                            return new IllegalArgumentException("User를 찾을 수 없습니다.");
        });

        return user;
    }


//    public List<UserResponseDto> findUserList() {
//        return userRepository.findAll()
//                .stream()
//                .map(user -> new UserResponseDto(user))
//                .collect(Collectors.toList());
//    }


    public Boolean checkDuplicateUsers(SignupRequestDto signupRequestDto){
        return userRepository.existsByEmail(signupRequestDto.getEmail());
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 사용자입니다."));
//
//    }

//    @Transactional
//    public UserUpdateResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto){
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
//        user.update(updateRequestDto);
//
//        return new UserUpdateResponseDto(userRepository.save(user), "정보가 수정되었습니다.");
//    }


}