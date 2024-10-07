package com.example.gnitio.service;

import com.example.gnitio.entity.UserEntity;
import com.example.gnitio.exception.UserAlreadyExistException;
import com.example.gnitio.exception.UserNotFoundException;
import com.example.gnitio.repository.RoleRepo;
import com.example.gnitio.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    private RoleRepo roleRepository;

    public Optional<UserEntity> findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName())).collect(Collectors.toList())
        );
    }

    public void createNewUser(UserEntity user) throws UserAlreadyExistException {
        if(userRepo.findByUsername(user.getUsername()) != null){
            throw  new UserAlreadyExistException("USER WITH THIS USERNAME HAS BEEN SAVED");
        }
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        userRepo.save(user);
    }

    public UserEntity getOne(Long id) throws UserNotFoundException {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User с ID " + id + " не найден"));
    }

    public Long delete(Long id){
        userRepo.deleteById(id);
        return id;
    }


}
