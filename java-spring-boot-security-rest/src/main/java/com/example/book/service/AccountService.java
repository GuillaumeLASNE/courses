package com.example.book.service;

import com.example.book.model.AppRole;
import com.example.book.model.AppUser;
import com.example.book.repository.AppRoleRepository;
import com.example.book.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    public AppUser saveUser(AppUser user) {
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);

        return appUserRepository.save(user);
    }

    public AppRole saveRole(AppRole role) {

        return appRoleRepository.save(role);
    }

    public void addRoleToUser(String userName, String roleName) {

        AppUser user = appUserRepository.findByUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        AppRole role = appRoleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("role not found"));

        user.getRoles().add(role);
        appUserRepository.save(user);
    }

}
