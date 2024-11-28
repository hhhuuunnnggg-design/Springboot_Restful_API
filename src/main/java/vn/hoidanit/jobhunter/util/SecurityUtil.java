package vn.hoidanit.jobhunter.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.nimbusds.jose.util.Base64;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.dto.ResLoginDTO;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SecurityUtil {
    @Autowired
    JwtEncoder jwtEncoder;


    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${hoidanit.jwt.base64-secret}")
    private String jwtKey;

    @Value("${hoidanit.jwt.access-token-validity-in-seconds}")
    private long accessTokenExpiration;


    @Value("${hoidanit.jwt.refresh-token-validity-in-seconds}")
    private long refreshTokenExpiration;

    public String createAccessToken(String email, ResLoginDTO.UserLogin dto) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);

        // hardcode permission (for testing)
        List<String> listAuthority = new ArrayList<String>();

        listAuthority.add("ROLE_USER_CREATE");
        listAuthority.add("ROLE_USER_UPDATE");

        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", dto)
                .claim("permission", listAuthority)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();

    }
    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length,
                JWT_ALGORITHM.getName());
    }

    public Jwt checkValiRefreshToken(String token) {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM).build();
        try {
             return jwtDecoder.decode(token);
        } catch (Exception e) {
            System.out.println(">>> RefetToken error: " + e.getMessage());
            throw e;
        }
    }

    public String refreshToken(String email,ResLoginDTO dto) {
        Instant now = Instant.now();
        Instant validity = now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        // @formatter:off
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .claim("user", dto.getUser())
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println("day chinh la1:"+securityContext);
        System.out.println("day chinh la2:"+securityContext.getAuthentication().getPrincipal());
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));


    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication == null) {
            return null;
        } else if (authentication.getPrincipal() instanceof UserDetails springSecurityUser) {
            System.out.println("Username from UserDetails: " + springSecurityUser.getUsername());
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof Jwt jwt) {
            System.out.println("Subject from JWT: " + jwt.getSubject());
            // Lấy giá trị "username" từ claim "user" nếu tồn tại
            if (jwt.getClaims().containsKey("user") && jwt.getClaims().get("user") instanceof Map userClaims) {
                String username = (String) userClaims.get("username");
                System.out.println("Username from JWT claim: " + username);
                return username;
            }
            return jwt.getSubject(); // Fallback: sử dụng subject nếu không có username
        } else if (authentication.getPrincipal() instanceof String s) {
            System.out.println("Principal as String: " + s);
            return s;
        }
        return null;
    }


}
