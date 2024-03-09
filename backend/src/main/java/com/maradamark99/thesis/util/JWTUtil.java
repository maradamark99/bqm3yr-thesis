package com.maradamark99.thesis.util;

import java.util.Date;
import java.util.Set;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTUtil {
	
	private JWTUtil() {
		
	}
	
	// TODO: move these to a config file
	private static final long EXPIRATION_TIME = 9_000_000;
	
	private static final String SECRET = "secret";

	private static final Algorithm ALGORITHM = Algorithm.HMAC512(SECRET);
	
	public static final String TOKEN_PREFIX = "Bearer ";
	
    public static DecodedJWT decodeToken(String encodedToken) {
        return JWT.require(ALGORITHM)
                .build()
                .verify(encodedToken.replace(TOKEN_PREFIX, ""));
    }

    public static String generateToken(String email, Set<String> roles) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("role", String.join(", ", roles))
                .sign(ALGORITHM);
    }
    
}
