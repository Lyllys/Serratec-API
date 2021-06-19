package org.serratec.security.service;

import java.util.Date;

import org.serratec.entities.Client;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	private Long expiration = 86400000L;
	private String secret = "Meu segredo";

	public String generateToken(Authentication authentication) {

		Client usuario = (Client) authentication.getPrincipal();

		Date now = new Date();
		Date exp = new Date(now.getTime() + expiration);

		return Jwts.builder().setIssuer("IRS").setSubject(usuario.getId().toString()).setIssuedAt(new Date())
				.setExpiration(exp).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getTokenId(String token) {
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Long.valueOf(body.getSubject());
	}

}
