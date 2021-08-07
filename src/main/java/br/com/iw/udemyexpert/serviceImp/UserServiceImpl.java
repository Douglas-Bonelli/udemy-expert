package br.com.iw.udemyexpert.serviceImp;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.iw.udemyexpert.entity.Usuario;
import br.com.iw.udemyexpert.exception.SenhaInvalidaException;
import br.com.iw.udemyexpert.repository.UsuarioRepository;


@Service
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository userRepository;
	
	
	public UserDetails autenticar( Usuario usuario ) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhaOK = encoder.matches(usuario.getSenha(), user.getPassword()); //Primeiro Parametro deve ser sempre a senha Digitada.
		
		if(senhaOK) return user;
		throw new SenhaInvalidaException();
		
	}
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Usuario usuario = userRepository.findByLogin(username)
		                                .orElseThrow( ()-> new UsernameNotFoundException("Usuário não encontrado na base de dados.")  );
		
		String[] roles = usuario.isAdmin() ? new String[] {"ADMIN","USER"} 
		                                   : new String[]{"USER"};
		
		return User
				.builder()
				.password(usuario.getSenha()) //Este Processo Já Verifica Usuário
				.username(usuario.getLogin()) //Senha do Processo
				.roles(roles)
				.build();
		
	}
	
	//Metodo para Salvar o Usuário
	@Transactional
	public Usuario salvarUsuario(Usuario usuario) {
		return userRepository.save(usuario);
	}
		
	//Simples Não Utiliza a Base de Dados
	/*
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		if(!username.equals("cicrano")){
			throw new UsernameNotFoundException("Usuário não Encontrado na Base de Dados.");
		}
		
		return User
				.builder()
				.password(encoder.encode("123"))
				.username("cicrano")
				.roles("USER","ADMIN")
				.build();
	  
	}*/
	

}
