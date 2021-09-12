package com.clientes.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data @NoArgsConstructor @AllArgsConstructor
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, max = 100)
	@Pattern(regexp = "^[a-zA-Z0-9\\-\\s]+$")
	private String nome;
	
	@NotNull
	private String cpf;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Endereco> endereco;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Telefone> telefone;
	
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Email> email;
		
}
