package org.serratec.security;

import org.serratec.repositories.ClientRepository;
import org.serratec.security.filter.TokenAuthenticationFilter;
import org.serratec.security.service.AuthenticationService;
import org.serratec.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private ClientRepository repository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
    //Configurations for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuration for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        	.antMatchers(HttpMethod.POST, "/auth").permitAll()
        	
        	.antMatchers(HttpMethod.POST, "/clients/cadastrar").permitAll()
        	.antMatchers(HttpMethod.GET, "/clients").permitAll()
        	
        	.antMatchers(HttpMethod.GET, "/categoria").permitAll()
        	.antMatchers(HttpMethod.POST, "/categorias/cadastrar").permitAll()
        	.antMatchers(HttpMethod.PUT, "/categoria/alterar/{nome}").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/categoria/deletar/{id}").permitAll()
        	
        	.antMatchers(HttpMethod.GET, "/produtos").permitAll()
        	.antMatchers(HttpMethod.GET, "/produtos/{nome}").permitAll()
        	.antMatchers(HttpMethod.GET, "/produtos/filtro/{nome}").permitAll()
        	.antMatchers(HttpMethod.GET, "/produtos/arquivados").permitAll()
        	.antMatchers(HttpMethod.GET, "/produtos/imagem/{id}").permitAll() //?????????
        	.antMatchers(HttpMethod.POST, "/produtos/cadastrar").permitAll()
        	.antMatchers(HttpMethod.PUT, "/produtos/alterar/{codigo}").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/produtos/deletar/{id}").permitAll()

        	.antMatchers(HttpMethod.GET, "/pedidos").permitAll()
        	.antMatchers(HttpMethod.DELETE, "/pedidos/deletar/{numeroPedido}").permitAll() //criar
        	
        	.anyRequest().authenticated()
        	.and().csrf().disable()
        	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        	.and().addFilterBefore(new TokenAuthenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);
    }

    //Configuration for static resources
    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
