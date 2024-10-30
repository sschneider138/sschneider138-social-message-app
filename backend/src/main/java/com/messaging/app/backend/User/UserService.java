package com.messaging.app.backend.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.messaging.app.backend.exceptions.UserNotCreatedException;
import com.messaging.app.backend.exceptions.UserNotFoundException;
import com.messaging.app.backend.exceptions.UserNotUpdatedException;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
    List<UserResponseDto> users = userRepository.findAll().stream()
        .map(
            user -> new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getTopInterests(),
                user.getDateJoined()))
        .collect(Collectors.toList());

    if (users.isEmpty()) {
      throw new UserNotFoundException("users not found");
    }

    return users;
  }

  public UserPageDto getAllPaginatedUsers(int pageIndex, int itemsPerPage) throws UserNotFoundException {
    PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
    Page<UserResponseDto> page = userRepository.findAllBy(pageRequest);

    return new UserPageDto(page.getContent(), page.getTotalPages(), (int) page.getTotalElements(), page.getNumber(),
        page.getSize());
  }

  public Optional<User> getById(Long id) throws UserNotFoundException {
    return userRepository.findById(id);
  }

  public Optional<UserResponseDto> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User createUser(UserCreateDto userCreateDto) throws UserNotCreatedException {
    try {
      User user = User.builder()
          .firstName(userCreateDto.firstName())
          .lastName(userCreateDto.lastName())
          .username(userCreateDto.username())
          .email(userCreateDto.email())
          .phoneNumber(userCreateDto.phoneNumber())
          .topInterests(userCreateDto.topInterests())
          .build();
      return userRepository.save(user);
    } catch (Exception e) {
      throw new UserNotCreatedException("failed to create user " + e.getMessage());
    }
  }

  public User partialUpdateUser(Long id, UserUpdateDto userUpdateDto) throws UserNotUpdatedException {
    User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));
    try {

      if (userUpdateDto.firstName() != null) {
        user.setFirstName(userUpdateDto.firstName());
      }

      if (userUpdateDto.lastName() != null) {
        user.setLastName(userUpdateDto.lastName());
      }

      if (userUpdateDto.username() != null) {
        user.setUsername(userUpdateDto.username());
      }

      if (userUpdateDto.email() != null) {
        user.setEmail(userUpdateDto.email());

      }
      if (userUpdateDto.phoneNumber() != null) {
        user.setPhoneNumber(userUpdateDto.phoneNumber());
      }

      if (userUpdateDto.topInterests() != null) {
        user.setTopInterests(userUpdateDto.topInterests());
      }
      return userRepository.save(user);
    } catch (UserNotUpdatedException e) {
      throw new UserNotUpdatedException("user found, but not updated: " + e.getMessage());
    }

  }
}