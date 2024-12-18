package com.example.hot_deal.auth.provider;

import com.example.hot_deal.auth.configuration.MemberInfo;
import com.example.hot_deal.auth.constants.TokenType;
import com.example.hot_deal.common.exception.HotDealException;
import com.example.hot_deal.member.domain.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_EXPIRED;
import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_INVALID;
import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_MALFORMED;
import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_SIGNATURE_FAILED;
import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_TYPE_INVALID;
import static com.example.hot_deal.auth.constants.error.AuthErrorCode.TOKEN_UNSUPPORTED;

@Component
public class JwtProvider {
    private final String MEMBER_ID = "member_id";
    private final String TOKEN_TYPE = "token_type";

    private final SecretKey secretKey;

    public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String createToken(Member member, TokenType tokenType) {
        Instant now = Instant.now();
        Instant expirationTime = now.plusSeconds(tokenType.getExpirationTime());
        return Jwts.builder()
                .claim(TOKEN_TYPE, tokenType.name())
                .claim(MEMBER_ID, member.getId())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expirationTime))
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token, TokenType tokenType) throws HotDealException {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            validateTokenType(token, tokenType);
        } catch (ExpiredJwtException e) {
            throw new HotDealException(TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new HotDealException(TOKEN_UNSUPPORTED);
        } catch (MalformedJwtException e) {
            throw new HotDealException(TOKEN_MALFORMED);
        } catch (SignatureException e) {
            throw new HotDealException(TOKEN_SIGNATURE_FAILED);
        } catch (JwtException e) {
            throw new HotDealException(TOKEN_INVALID);
        } catch (Exception e) {
            throw new HotDealException(TOKEN_MALFORMED);
        }
    }

    private void validateTokenType(String token, TokenType tokenType) {
        String type = getType(token);
        if (type == null || !type.equals(tokenType.name())) {
            throw new HotDealException(TOKEN_TYPE_INVALID);
        }
    }

    private String getType(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
                .get(TOKEN_TYPE, String.class);
    }

    public MemberInfo getMemberInfo(String token) {
        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();

        return new MemberInfo(
                claims.get(MEMBER_ID, Long.class)
        );
    }
}
