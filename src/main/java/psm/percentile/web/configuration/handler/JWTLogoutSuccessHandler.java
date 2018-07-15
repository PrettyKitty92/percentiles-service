package psm.percentile.web.configuration.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static psm.percentile.web.configuration.auth.SecurityConstants.HEADER_STRING;
import static psm.percentile.web.configuration.auth.SecurityConstants.SECRET;
import static psm.percentile.web.configuration.auth.SecurityConstants.TOKEN_PREFIX;

/**
 * Created by Patrycja on 05.09.2017.
 *
 */
@Component
public class JWTLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

            authentication.setAuthenticated(false);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
