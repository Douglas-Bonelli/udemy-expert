package br.com.iw.udemyexpert.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="usuario")
public class Usuario {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO) //Para Outros Bancos de Dados
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Mysql
	private Integer id;
	
	@Column
	@NotEmpty(message = "Campo Login é Obrigatório.")
	private String login;
	
	@Column
	@NotEmpty(message = "Campo senha é Obrigatório.")
	private String senha;
	
	@Column
	private boolean admin;
	

}
