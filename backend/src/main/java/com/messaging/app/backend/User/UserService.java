package com.messaging.app.backend.User;

import com.messaging.app.backend.Exceptions.UserNotCreatedException;
import com.messaging.app.backend.Exceptions.UserNotFoundException;
import com.messaging.app.backend.Exceptions.UserNotUpdatedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponseDto> getAllUsers() throws UserNotFoundException {
        return userRepository.findAll().stream()
                .map(
                        user -> new UserResponseDto(
                                user.getFirstName(),
                                user.getLastName(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getPhoneNumber(),
                                user.getTopInterests(),
                                user.getDateJoined()))
                .collect(Collectors.toList());
    }

    public UserPageDto getAllPaginatedUsers(int pageIndex, int itemsPerPage) throws UserNotFoundException {
        PageRequest pageRequest = PageRequest.of(pageIndex, itemsPerPage);
        Page<User> page = userRepository.findAllPaginatedUsers(pageRequest);

        Page<UserResponseDto> dtoPage = page.map(user -> new UserResponseDto(
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getTopInterests(),
                        user.getDateJoined()
                )
        );

        return new UserPageDto(
                dtoPage.getContent(),
                dtoPage.getTotalPages(),
                (int) dtoPage.getTotalElements(),
                dtoPage.getNumber(),
                dtoPage.getSize()
        );
    }


    public UserResponseDto getById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found for id: " + id));

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined()
        );
    }

    public UserResponseDto getByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined()
        );
    }

    public UserResponseDto createUser(UserCreateDto userCreateDto) throws UserNotCreatedException {
        User user = User.builder()
                .firstName(userCreateDto.firstName())
                .lastName(userCreateDto.lastName())
                .username(userCreateDto.username())
                .email(userCreateDto.email())
                .phoneNumber(userCreateDto.phoneNumber())
                .topInterests(userCreateDto.topInterests())
                .build();

        User savedUser = userRepository.save(user);
        
        return new UserResponseDto(
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getTopInterests(),
                savedUser.getDateJoined()
        );
    }

    public UserResponseDto getIndividualUser(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found for id: " + id));

        return new UserResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getTopInterests(),
                user.getDateJoined()
        );
    }

    public UserResponseDto partialUpdateIndividualUser(Long id, UserUpdateDto userUpdateDto) throws
            UserNotUpdatedException {
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
            User updatedUser = userRepository.save(user);

            return new UserResponseDto(
                    updatedUser.getFirstName(),
                    updatedUser.getLastName(),
                    updatedUser.getUsername(),
                    updatedUser.getEmail(),
                    updatedUser.getPhoneNumber(),
                    updatedUser.getTopInterests(),
                    updatedUser.getDateJoined());

        } catch (UserNotUpdatedException e) {
            throw new UserNotUpdatedException("user found, but not updated: " + e.getMessage());
        }

    }
}