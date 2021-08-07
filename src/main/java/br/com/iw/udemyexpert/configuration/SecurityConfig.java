package br.com.iw.udemyexpert.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.iw.udemyexpert.jwtService.JWTAuthFilter;
import br.com.iw.udemyexpert.jwtService.JWTService;
import br.com.iw.udemyexpert.serviceImp.UserServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	//Processo de Autenticação em Memoria ---------------------------------------------------------
	//Aqui faz a autenticação
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
		
		//super.configure(auth);
		auth.inMemoryAuthentication()                        //Autenticação em Memoria
			.passwordEncoder(passwordEncoder())              //Password recebido Criptrografado
			.withUser("Fulano")                              //Usuário
			.password( passwordEncoder().encode("123") )     //Senha
			.roles("USER");                                  //Roles de Acesso
		
	}
	
	//Aqui faz a autorização
	//Pegue o usuário autenticado acima e verifique se ele tem autorização a este serviço
	//Roles e Autorities.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		super.configure(http);
	}*/
	//-----------------------------------------------------------------------------------------------
	
	
	
	
	
	
	
	//Processo de Autenticação em Memoria ---------------------------------------------------------
	//Utilizando Roles ----------------------------------------------------------------------------
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
		
		//super.configure(auth);
		auth.inMemoryAuthentication()                        //Autenticação em Memoria
			.passwordEncoder(passwordEncoder())              //Password recebido Criptrografado
			.withUser("Fulano")                              //Usuário
			.password( passwordEncoder().encode("123") )     //Senha
			.roles("USER","ADMIN");                          //Roles de Acesso
		
	}
	
	//Aqui faz a autorização
	//Pegue o usuário autenticado acima e verifique se ele tem autorização a este serviço
	//Roles e Autorities.
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		http.csrf().disable()
		    .authorizeRequests()
		       //.antMatchers("/api/ping/*")                  //Especifica o Endereço Acessivel
		             //.authenticated()                       //Somente Autenticado
		             //.hasRole("USER_ADMIN")                 //Possui a ROLE
		             //.hasAnyAuthority("MANTER_USUARIO")     //Possui a Authority
		             //.hasAnyRole("ADMIN","USER")            //Possuindo Ambas as Roles
		       .antMatchers("/api/ping/*").hasRole("ADMIN")               //Um Asterisco somente os metodos sem parametros.
		       .antMatchers("/api/produtos/**").hasRole("ADMIN") 
		       .antMatchers("/api/pedidos/**").hasAnyRole("ADMIN","USER")  //Dois Asteriscos porque acessa metodos com Parametros.
		       .antMatchers("/api/clientes/**").hasAnyRole("ADMIN","USER") //Dois Asteriscos porque acessa metodos com Parametros.
		    .and()
		    //.formLogin();    //Cria o Formulario de Login
		    .httpBasic()       //Somente Acessa Via Basic Autentication
            ;
	}
	*/
	//-----------------------------------------------------------------------------------------------
	
	
	
	//Este Processo Criptografa a Senha.
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
		
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UserServiceImpl userService;
		
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JWTAuthFilter( jwtService , userService );
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
		
		auth.userDetailsService(userService)
		    .passwordEncoder(passwordEncoder());
		
	}
	
			
	//Modelo de Autenticação TOKEN
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
			    .authorizeRequests()
			       //.antMatchers("/api/ping/*")                  //Especifica o Endereço Acessivel
			             //.authenticated()                       //Somente Autenticado
			             //.hasRole("USER_ADMIN")                 //Possui a ROLE
			             //.hasAnyAuthority("MANTER_USUARIO")     //Possui a Authority
			             //.hasAnyRole("ADMIN","USER")            //Possuindo Ambas as Roles
			       .antMatchers("/api/ping/*").hasRole("ADMIN")               //Um Asterisco somente os metodos sem parametros.
			       .antMatchers("/api/produtos/**").hasRole("ADMIN") 
			       .antMatchers("/api/pedidos/**").hasAnyRole("ADMIN","USER")     //Dois Asteriscos porque acessa metodos com Parametros.
			       .antMatchers("/api/clientes/**").hasAnyRole("ADMIN","USER")    //Dois Asteriscos porque acessa metodos com Parametros.
			       .antMatchers( HttpMethod.POST , "/api/usuarios/*").permitAll() //Os Metodos POST Podem ser Acessados com Permit All
			       .anyRequest().authenticated()
			       .and()
			    //.formLogin();    //Cria o Formulario de Login
			    //.httpBasic()       //Somente Acessa Via Basic Autentication
			       .sessionManagement()
			       .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			       .and()
			       .addFilterBefore( jwtFilter() , UsernamePasswordAuthenticationFilter.class )
	            ;
	}*/
	
	
	
	
	//Desabilitar todo o Processo de Atenticação
	@Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers("/").permitAll();
    }

	

	
	
	
	
	
	
	
	
    //Utilizando Roles ----------------------------------------------------------------------------
	//Modelo de Autenticação BASIC
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth ) throws Exception {
		
		auth.userDetailsService(userService)
		    .passwordEncoder(passwordEncoder());
		
	}
		
	//Aqui faz a autorização
	@Override
	protected void configure(HttpSecurity http) throws Exception {

			http.csrf().disable()
			    .authorizeRequests()
			       //.antMatchers("/api/ping/*")                  //Especifica o Endereço Acessivel
			             //.authenticated()                       //Somente Autenticado
			             //.hasRole("USER_ADMIN")                 //Possui a ROLE
			             //.hasAnyAuthority("MANTER_USUARIO")     //Possui a Authority
			             //.hasAnyRole("ADMIN","USER")            //Possuindo Ambas as Roles
			       .antMatchers("/api/ping/*").hasRole("ADMIN")               //Um Asterisco somente os metodos sem parametros.
			       .antMatchers("/api/produtos/**").hasRole("ADMIN") 
			       .antMatchers("/api/pedidos/**").hasAnyRole("ADMIN","USER")     //Dois Asteriscos porque acessa metodos com Parametros.
			       .antMatchers("/api/clientes/**").hasAnyRole("ADMIN","USER")    //Dois Asteriscos porque acessa metodos com Parametros.
			       .antMatchers( HttpMethod.POST , "/api/usuarios/*").permitAll() //Os Metodos POST Podem ser Acessados com Permit All
			       .anyRequest().authenticated()
			       .and()
			    //.formLogin();    //Cria o Formulario de Login
			    .httpBasic()       //Somente Acessa Via Basic Autentication
	            ;
	}*/
	//-----------------------------------------------------------------------------------------------
	

}
