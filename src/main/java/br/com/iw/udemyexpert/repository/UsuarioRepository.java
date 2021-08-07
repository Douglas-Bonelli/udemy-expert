package br.com.iw.udemyexpert.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.iw.udemyexpert.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	
	Optional<Usuario> findByLogin(String login);

}
