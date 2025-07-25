package com.example.schedulemanager.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
	private final String SECRET = "myverystrongsecretkeythatshouldbeatleast256bitslong!";
	private final long EXPIRATION_TIME = 1000 * 60 * 60;
	
	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	public String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch(JwtException | IllegalArgumentException  e) {
			return false;
		}
	}
}
