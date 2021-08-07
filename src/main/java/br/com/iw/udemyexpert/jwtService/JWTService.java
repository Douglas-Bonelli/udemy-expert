package br.com.iw.udemyexpert.jwtService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.iw.udemyexpert.UdemyExpertApplication;
import br.com.iw.udemyexpert.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTService {
	
	@Value("${security.jwt.expiracao}")
	private String expiracao;	
	
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	public String gerarToken(Usuario usuario) {
		long expString = Long.valueOf(expiracao);
		LocalDateTime dataExpiracao = LocalDateTime.now().plusMinutes(expString);
		Instant instant = dataExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		
		
		//Informações Adicionais do Token
		//Informações do usuário
		//HashMap <String,Object> claims = new HashMap<>();
		//claims.put("emaildousuario", usuario.getLogin());
		//claims.put("roles", "admin");
		
		
		
		return Jwts
				   .builder()                                           //Builder to Token
				   .setSubject(usuario.getLogin())                      //Subject - Identificador do Token
                   .setExpiration(date)                                 //Data e Hora de Expiração doToken
                   //.setClaims(claims)                                   //Informações Extras
                   .signWith(SignatureAlgorithm.HS512, chaveAssinatura) //Assinatura do Token
                   .compact();
                   
	}
	
	
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		
		return Jwts
				 .parser()
				 .setSigningKey(chaveAssinatura)
				 .parseClaimsJws(token)
				 .getBody();
		
	}
	
	
	
	//Verifica se o token não esta expirado
	public boolean tokenValido( String token ){
		try {
			
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			
			LocalDateTime data = dataExpiracao
										.toInstant()
										.atZone(ZoneId.systemDefault())
										.toLocalDateTime();
			
			return ! LocalDateTime.now().isAfter(data);
			
		}catch(Exception e) 
		    {return false;}
	}
	
	
	
	//Retorna o Subject do Token - Definimos o Subject como Login.
	public String obterLoginUsuario( String token ) throws ExpiredJwtException {
		return obterClaims(token).getSubject();
	}
	
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext contexto = SpringApplication.run( UdemyExpertApplication.class , args);
		JWTService jwt = contexto.getBean(JWTService.class);
		
		Usuario usuario = new Usuario();
		usuario.setLogin("Douglas");
		
		String token = jwt.gerarToken(usuario);
		System.out.println(token);
		
		//Para Verificação do Token Acesse o site -- https://jwt.io/
		
		boolean isTokenValid = jwt.tokenValido(token);
		System.out.println("O Token esta Valido ? " + isTokenValid );
		System.out.println("User: " + jwt.obterLoginUsuario(token));
		
		
	}
	

}
