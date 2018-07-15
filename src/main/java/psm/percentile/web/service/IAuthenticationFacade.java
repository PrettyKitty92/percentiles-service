package psm.percentile.web.service;

import org.springframework.security.core.Authentication;

/**
 * Created by Patrycja on 03.09.2017.
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}