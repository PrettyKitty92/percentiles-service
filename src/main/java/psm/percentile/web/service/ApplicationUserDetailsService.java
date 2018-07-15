package psm.percentile.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import psm.percentile.common.model.user.ApplicationUserDetails;
import psm.percentile.common.model.user.ApplicationUser;
import psm.percentile.web.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser applicationUser = usersRepository.findByUsername(username);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(
                    "No applicationUser found with username: "+ username);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        ApplicationUserDetails cut = new ApplicationUserDetails(applicationUser.getFirstName(), applicationUser.getLastName(), applicationUser.getUsername(), applicationUser.getEmail(),
                        applicationUser.getPassword(), getAuthorities(applicationUser.getAuthorities()), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonLocked);

        return cut;
    }

    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return authorities;
    }
}
