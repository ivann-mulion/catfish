package org.cat.fish.userservice.security.userprinciple;

import org.cat.fish.userservice.exception.wrapper.EmailNotFoundException;
import org.cat.fish.userservice.model.entity.User;
import org.cat.fish.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email does not exist, please try again: " + email));

        return UserPrinciple.build(user);
    }

    @Transactional
    public UserDetails loadUserByPhoneNumber(String phoneNumber) {
        User user = userRepository.findByEmail(phoneNumber)
                .orElseThrow(() -> new EmailNotFoundException("Email does not exist, please try again: " + phoneNumber));
        return UserPrinciple.build(user);
    }
}
