package com.backend.user.service;

import com.backend.user.dto.PageDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.dto.UserUpdateDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(
                user -> new UserResponseDto(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getTopInterests(),
                        user.getDateJoined(),
                        user.getMembershipLength())
        ).collect(Collectors.toList());
    }

    public PageDto<UserResponseDto> getAllPaginatedUsers(int pageIndex, int itemsPerPage) {
        PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
        Page<User> page = userRepository.findAll(pageRequest);

        Page<UserResponseDto> dtoPage = page.map(user -> new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined(),
                user.getMembershipLength())
        );

        return new PageDto<>(
                dtoPage.getContent(),
                dtoPage.getTotalPages(),
                (int) dtoPage.getTotalElements(),
                dtoPage.getNumber(),
                dtoPage.getSize()
        );
    }


    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found for id: " + id));

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined(),
                user.getMembershipLength()
        );
    }

    public UserResponseDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found with username: " + username));

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined(),
                user.getMembershipLength()
        );
    }

    public UserResponseDto getIndividualUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found for id: " + id));

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined(),
                user.getMembershipLength()
        );
    }

    public UserResponseDto partialUpdateIndividualUser(Long id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        try {

            if (userUpdateDto.getFirstName() != null) {
                user.setFirstName(userUpdateDto.getFirstName());
            }

            if (userUpdateDto.getLastName() != null) {
                user.setLastName(userUpdateDto.getLastName());
            }

            if (userUpdateDto.getUsername() != null) {
                user.setUsername(userUpdateDto.getUsername());
            }

            if (userUpdateDto.getEmail() != null) {
                user.setEmail(userUpdateDto.getEmail());

            }

            if (userUpdateDto.getPhoneNumber() != null) {
                user.setPhoneNumber(userUpdateDto.getPhoneNumber());
            }

            if (userUpdateDto.getTopInterests() != null) {
                user.setTopInterests(userUpdateDto.getTopInterests());
            }
            User updatedUser = userRepository.save(user);

            return new UserResponseDto(
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getPhoneNumber(),
                    updatedUser.getTopInterests(),
                    updatedUser.getDateJoined(),
                    user.getMembershipLength()
            );

        } catch (RuntimeException e) {
            throw new RuntimeException("user found, but not updated: " + e.getMessage());
        }

    }
}
