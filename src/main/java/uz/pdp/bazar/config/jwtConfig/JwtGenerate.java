package uz.pdp.bazar.config.jwtConfig;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import uz.pdp.bazar.entity.User;
import uz.pdp.bazar.repository.UserRepository;

import java.util.Date;

import static uz.pdp.bazar.enums.Constants.AUTHORITIES;


@Service
@RequiredArgsConstructor
public class JwtGenerate {

    private final UserRepository userRepository;

    private static final String JWT_ACCESS_KEY = "404E635266556A586E327235753878F413F4428472B4B6250645367566B5970";
    private static final long accessTokenLiveTime = 60 * 60 * 1000 * 24;
    Logger logger = LoggerFactory.getLogger(JwtGenerate.class);

    public static synchronized String generateAccessToken(User user) {
        return Jwts
                .builder()
                .setSubject(user.getPhoneNumber())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + accessTokenLiveTime))
                .signWith(SignatureAlgorithm.HS256, JWT_ACCESS_KEY)
                .claim(AUTHORITIES, user.getAuthorities())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(JWT_ACCESS_KEY)
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.debug("expired token " + e);
        } catch (MalformedJwtException e) {
            logger.debug("malFormed jwt exception " + e);
        } catch (UnsupportedJwtException e) {
            logger.debug("unsupported" + e);
        } catch (IllegalArgumentException e) {
            logger.debug("Illegal" + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        return Jwts
                .parser()
                .setSigningKey(JWT_ACCESS_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
