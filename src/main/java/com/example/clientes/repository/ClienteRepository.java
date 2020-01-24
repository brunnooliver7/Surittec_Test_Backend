package com.example.clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.clientes.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

}
