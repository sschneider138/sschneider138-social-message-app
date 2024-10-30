package com.messaging.app.backend.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<UserResponseDto> getAllUsers() {
    return userRepository.findAll().stream()
        .map(
            user -> new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getTopInterests(),
                user.getDateJoined()))
        .collect(Collectors.toList());
  }

  public UserPageDto getAllPaginatedUsers(int pageIndex, int itemsPerPage) {
    PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
    Page<UserResponseDto> page = userRepository.findAllBy(pageRequest);

    return new UserPageDto(page.getContent(), page.getTotalPages(), (int) page.getTotalElements(), page.getNumber(),
        page.getSize());
  }

  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  public Optional<UserResponseDto> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public User createUser(UserCreateDto userCreateDto) {
    System.out.println("starting user service");
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
      throw new RuntimeException("failed to create user: " + e.getMessage());
    }
  }
}