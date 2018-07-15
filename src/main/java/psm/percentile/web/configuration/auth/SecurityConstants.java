package psm.percentile.web.configuration.auth;

/**
 * Created by Patrycja on 04.09.2017.
 */
public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 100_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "authorities";
    public static final String SIGN_UP_URL = "/user/sign-up";
}
