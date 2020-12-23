package com.ottofriedrich.dsam.services;

import com.ottofriedrich.dsam.config.ProfileUser;
import com.ottofriedrich.dsam.models.User;
import com.ottofriedrich.dsam.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component("dSamUserService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        User user = userRepository.findByUsername(username.trim());
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username.");
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return new ProfileUser(user.getId(), user.getUsername().trim(), bCryptPasswordEncoder.encode(user.getPassword().trim()), grantedAuthorities);
    }
}
