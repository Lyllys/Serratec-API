package org.serratec.resources;

import org.serratec.dtos.login.LoginDTO;
import org.serratec.dtos.login.TokenDTO;
import org.serratec.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResource {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/auth")
	public ResponseEntity<?> auth(@RequestBody @Validated LoginDTO loginDTO){
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDTO.getUser(), loginDTO.getPass());
		
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		
		String token = tokenService.generateToken(authentication);		
		
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setToken(token);
		tokenDTO.setType("Bearer");
		
		return new ResponseEntity<>(tokenDTO, HttpStatus.OK);		
	}

}
