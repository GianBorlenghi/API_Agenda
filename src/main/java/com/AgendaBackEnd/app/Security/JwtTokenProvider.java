package com.AgendaBackEnd.app.Security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.AgendaBackEnd.app.Exception.RequestException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JwtTokenProvider {
	@Value("${security.jwt.secret}")
	private String key;
	
	@Value("${security.jwt.issuer}")
	private String issuer;
	
	@Value("${security.jwt.ttlMillis}")
	private long ttlMillis;
	
	public String generateToken(Authentication authentication) {
		String username = authentication.getName();
		Date date = new Date();
		Date expiration = new Date(date.getTime() + ttlMillis);
		
		String token = Jwts.builder().setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, key).compact();
		
		return token;
	}
	
	public String obtainUsernameJwt(String token) {
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		}catch(SignatureException ex) {
			throw new RequestException("JWT Signature invalid ", HttpStatus.BAD_REQUEST,"P-500");
		}catch(MalformedJwtException ex) {
			throw new RequestException("JWT Token invalid", HttpStatus.BAD_REQUEST,"P-500");
		}catch(ExpiredJwtException ex) {
			throw new RequestException("JWT Token timed out", HttpStatus.BAD_REQUEST,"P-500");
		}catch(UnsupportedJwtException ex) {
			throw new RequestException("JWT Token incompatible", HttpStatus.BAD_REQUEST,"P-500");
		}catch(IllegalArgumentException ex) {
			throw new RequestException("Empty claims string", HttpStatus.BAD_REQUEST,"P-500");
		}
	
	}
		
}
