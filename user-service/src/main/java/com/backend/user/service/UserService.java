package com.backend.user.service;

import com.backend.user.config.JwtService;
import com.backend.user.dto.PageDto;
import com.backend.user.dto.UserResponseDto;
import com.backend.user.dto.UserUpdateRequestDto;
import com.backend.user.model.User;
import com.backend.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
  private final JwtService jwtService;

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
        dtoPage.getSize());
  }


  public UserResponseDto getByUUID(String userUUID) {
    User user = userRepository.findByUserUUID(userUUID)
        .orElseThrow(() -> new EntityNotFoundException("user not found for uuid: " + userUUID));

    return mapUserToDto(user);
  }

  public UserResponseDto getByUsername(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException("user not found with username: " + username));

    return mapUserToDto(user);
  }

  public UserResponseDto partialUpdateIndividualUser(
      String userUUID, UserUpdateRequestDto userUpdateRequestDto, String authorizationHeader) {

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      throw new RuntimeException("authorization header is missing or invalid");
    }

    String token = authorizationHeader.substring(7);
    String usernameFromToken = jwtService.extractUsername(token);

    User authenticatedUser = userRepository.findByUsername(usernameFromToken)
        .orElseThrow(() -> new EntityNotFoundException("authenticated user not found"));

    if (!authenticatedUser.getUserUUID().equals(userUUID)) {
      throw new RuntimeException("you are not authorized to modify this profile");
    }

    User user = userRepository.findByUserUUID(userUUID)
        .orElseThrow(() -> new EntityNotFoundException("user not found"));

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
      throw new RuntimeException("failed to update user with uuid: " + userUUID, e);
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
        user.getMembershipLength());
  }

}
