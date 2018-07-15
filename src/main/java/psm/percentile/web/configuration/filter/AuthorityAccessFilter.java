package psm.percentile.web.configuration.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static psm.percentile.web.configuration.auth.SecurityConstants.*;

/**
 * Created by Patrycja on 05.09.2017.
 */
public class AuthorityAccessFilter extends BasicAuthenticationFilter {

    private static final String USER_AUTHORITY = "USER";
    private static final String ADMIN_AUTHORITY = "ADMIN";
    RequestMatcher USER_PATH_MATCHER = new AntPathRequestMatcher("/user/**");
    RequestMatcher ADMIN_PATH_MATCHER = new AntPathRequestMatcher("/admin/**");

    public AuthorityAccessFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }


    @Override
    public void doFilterInternal(javax.servlet.http.HttpServletRequest request,
                                 javax.servlet.http.HttpServletResponse response,
                                 javax.servlet.FilterChain chain)
            throws IOException, ServletException {

        List<GrantedAuthority> authorities = getAuthorities(request.getHeader(HEADER_STRING));

        if (USER_PATH_MATCHER.matches(request) &&
                !authorities.contains(new SimpleGrantedAuthority(USER_AUTHORITY))
                || ADMIN_PATH_MATCHER.matches(request) &&
                !authorities.contains(new SimpleGrantedAuthority(ADMIN_AUTHORITY))) {
            response.reset();
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied - Inappropriate permissions");
            return;
        }
        chain.doFilter(request, response);
    }

    private static List<GrantedAuthority> getAuthorities(String token) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();

            List<String> roles = Arrays.asList(claims.get(AUTHORITIES_KEY).toString().split(","));


            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }
        return authorities;
    }
}
