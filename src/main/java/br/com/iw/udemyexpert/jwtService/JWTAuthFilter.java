package br.com.iw.udemyexpert.jwtService;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.iw.udemyexpert.serviceImp.UserServiceImpl;

public class JWTAuthFilter extends OncePerRequestFilter{
	
	private JWTService jwtService;
	private UserServiceImpl userService;
	
	

	public JWTAuthFilter(JWTService jwtService, UserServiceImpl userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}



	@Override
	protected void doFilterInternal(  HttpServletRequest request
			                        , HttpServletResponse response
			                        , FilterChain filterChain)  throws ServletException, IOException {
		
		//Recupero as Informações do Authorization
		String authorization = request.getHeader("Authorization");
		
		//Verifico se foi enviada a Authorization e se o BEARER esta preenchido
		if(authorization != null && authorization.startsWith("Bearer")) {
		
		   //Recupero o valor do BEARER
		   String token = authorization.split(" ")[1];
		   boolean isValid = jwtService.tokenValido(token);
		   
		   //Verifico se esta valido.
		   if(isValid) {
			   
			   String loginUsuario = jwtService.obterLoginUsuario(token);
			   UserDetails user = userService.loadUserByUsername(loginUsuario);
			   
			   //Vamos colocar o usuário no contexto do Spring
			   //Criamos um usuário de contexto
			   UsernamePasswordAuthenticationToken userContext = 
					   new UsernamePasswordAuthenticationToken( user , null , user.getAuthorities() );
			   
			   
			   //Diz para o usuário de contexto do Spring que é uma atenticação WEB
			   userContext.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
			   
			   
			   //Neste Ponto inclui o usuário de contexto do Spring.
			   SecurityContextHolder.getContext().setAuthentication(userContext);
			   
		   } 
		}
		
		
		filterChain.doFilter(request, response);
		
	}
	

}
