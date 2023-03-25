package com.example.security.service;

import com.example.security.model.dto.UserRegisterDto;
import com.example.security.model.entity.UserEntity;
import com.example.security.model.entity.UserRoleEntity;
import com.example.security.model.enums.UserRoleEnum;
import com.example.security.repository.UserRepository;
import com.example.security.repository.UserRoleRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder,
                       UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    public void init() {
        if (userRepository.count() == 0 && userRoleRepository.count() == 0) {

            UserRoleEntity adminRole = new UserRoleEntity().setUserRole(UserRoleEnum.ADMIN);
            UserRoleEntity moderatorRole = new UserRoleEntity().setUserRole(UserRoleEnum.MODERATOR);

            adminRole = userRoleRepository.save(adminRole);
            moderatorRole = userRoleRepository.save(moderatorRole);

            initAdmin(List.of(adminRole, moderatorRole));
            initModerator(List.of(moderatorRole));
            initUser(List.of());
        }
    }

    private void initAdmin(List<UserRoleEntity> roles) {
        UserEntity admin = new UserEntity()
                .setUserRoles(roles)
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setEmail("admin@example.com")
                .setPassword(passwordEncoder.encode("topsecret"));

        userRepository.save(admin);
    }

    private void initModerator(List<UserRoleEntity> roles) {
        UserEntity moderator = new UserEntity()
                .setUserRoles(roles)
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setEmail("moderator@example.com")
                .setPassword(passwordEncoder.encode("topsecret"));

        userRepository.save(moderator);
    }

    private void initUser(List<UserRoleEntity> roles) {
        UserEntity user = new UserEntity()
                .setUserRoles(roles)
                .setFirstName("User")
                .setLastName("Userov")
                .setEmail("user@example.com")
                .setPassword(passwordEncoder.encode("topsecret"));

        userRepository.save(user);
    }

    public void registerAndLogin(UserRegisterDto userRegisterDto) {
        UserEntity newUser =
                new UserEntity()
                        .setFirstName(userRegisterDto.getFirstName())
                        .setLastName(userRegisterDto.getLastName())
                        .setEmail(userRegisterDto.getEmail())
                        .setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepository.save(newUser);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(userRegisterDto.getEmail());

        //logging the new User
        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(auth);
    }
}
