package com.backend.user.service;

import com.backend.user.dto.PageDto;
import com.backend.user.dto.UserCreationRequestDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.dto.UserUpdateRequestDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapUserToDto(user))
                .collect(Collectors.toList());
    }

    public PageDto<UserResponseDto> getAllPaginatedUsers(int pageIndex, int itemsPerPage) {
        PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
        Page<User> page = userRepository.findAll(pageRequest);

        Page<UserResponseDto> dtoPage = page.map(user -> mapUserToDto(user));

        return new PageDto<>(
                dtoPage.getContent(),
                dtoPage.getTotalPages(),
                (int) dtoPage.getTotalElements(),
                dtoPage.getNumber(),
                dtoPage.getSize()
        );
    }

    public UserResponseDto createUser(UserCreationRequestDto userCreationRequestDto) {
        if (userRepository.existsByUsername(userCreationRequestDto.username())) {
            throw new RuntimeException("username already taken");
        }

        if (userRepository.existsByEmail(userCreationRequestDto.email())) {
            throw new RuntimeException("email already taken");
        }

        if (!isPasswordValid(userCreationRequestDto.password())) {
            throw new RuntimeException("Password must be at least 8 characters and include at least one uppercase letter, one lowercase letter, one digit, and one special character");
        }

        User user = User.builder()
                .firstName(userCreationRequestDto.firstName())
                .lastName(userCreationRequestDto.lastName())
                .username(userCreationRequestDto.username())
                .email(userCreationRequestDto.email())
                .phoneNumber(userCreationRequestDto.phoneNumber())
                .topInterests(userCreationRequestDto.topInterests())
                .password(passwordEncoder.encode(userCreationRequestDto.password()))
                .build();

        User savedUser = userRepository.save(user);
        return mapUserToDto(savedUser);
    }


    public UserResponseDto getByUUID(UUID uuid) {
        User user = userRepository.findByUserUUID(uuid).orElseThrow(() -> new EntityNotFoundException("user not found for uuid: " + uuid));

        return mapUserToDto(user);
    }

    public UserResponseDto getByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("user not found with username: " + username));

        return mapUserToDto(user);
    }

    // FIXME: in future, set so that only authenticated user may performance update for his own profile
    public UserResponseDto partialUpdateIndividualUser(
            UUID uuid, UserUpdateRequestDto userUpdateRequestDto) {

        User user = userRepository.findByUserUUID(uuid).orElseThrow(() -> new EntityNotFoundException("user not found"));

        if (!passwordEncoder.matches(userUpdateRequestDto.password(), user.getPassword())) {
            throw new RuntimeException("Provided password does not match current user password");
        }

        if (!Objects.equals(userUpdateRequestDto.username(), user.getUsername())) {
            System.out.println(userUpdateRequestDto.username());
            System.out.println(user.getUsername());
            throw new RuntimeException("username provided does not match username of user in question");
        }

        try {
            if (userUpdateRequestDto.firstName() != null) {
                user.setFirstName(userUpdateRequestDto.firstName());
            }

            if (userUpdateRequestDto.lastName() != null) {
                user.setLastName(userUpdateRequestDto.lastName());
            }

            if (userUpdateRequestDto.username() != null) {
                user.setUsername(userUpdateRequestDto.username());
            }

            if (userUpdateRequestDto.email() != null) {
                user.setEmail(userUpdateRequestDto.email());

            }

            if (userUpdateRequestDto.phoneNumber() != null) {
                user.setPhoneNumber(userUpdateRequestDto.phoneNumber());
            }

            if (userUpdateRequestDto.topInterests() != null) {
                user.setTopInterests(userUpdateRequestDto.topInterests());
            }

            User updatedUser = userRepository.save(user);
            return mapUserToDto(updatedUser);

        } catch (RuntimeException e) {
            throw new RuntimeException("failed to update user with uuid: " + uuid, e);
        }

    }

    private UserResponseDto mapUserToDto(User user) {
        return new UserResponseDto(
                user.getUserUUID(),
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

    private boolean isPasswordValid(String password) {
        // password must be 8 characters and include at least one uppercase letter,
        // one lowercase letter, one digit, and one special character
        return password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }
}
