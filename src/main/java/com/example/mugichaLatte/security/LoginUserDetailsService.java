package com.example.mugichaLatte.security;

import com.example.mugichaLatte.repository.UserRepository;
import com.example.mugichaLatte.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        User user = userRepository.findByAccount(account)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが見つかりません"));

        return new LoginUserDetails(user);
    }
}
