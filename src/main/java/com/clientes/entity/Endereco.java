package com.clientes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "enderecos")
@Data @NoArgsConstructor @AllArgsConstructor
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String cep;
	
	@NotNull
	private String logradouro;
	
	@NotNull
	private String bairro;
	
	@NotNull
	private String cidade;
	
	@NotNull
	private String uf;
	
	private String complemento;
	
	@ManyToOne
	private Cliente cliente;
	
}
