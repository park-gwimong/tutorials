package sample.iam.api.service;

import javax.transaction.Transactional;
import sample.iam.api.domain.UserDetailsImpl;
import sample.iam.api.domain.entity.UserEntity;
import sample.iam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findFirstUserByUserIdOrderByIdAsc(username).orElseThrow(() -> new RuntimeException("Not Found User"));
        return new UserDetailsImpl(
                userEntity.getUserId(),
                userEntity.getEncryptedPassword(),
                userEntity.getEmail(),
                userEntity.getName(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

}