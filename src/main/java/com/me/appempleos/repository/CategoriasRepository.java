package com.me.appempleos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.me.appempleos.model.Categoria;

public interface CategoriasRepository extends JpaRepository<Categoria, Integer> {
	
}
