package com.me.appempleos.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.me.appempleos.model.Vacante;

public interface IVacantesService {

	List<Vacante> buscarTodas();
	Vacante buscarPorId(int id);
	void guardar(Vacante vacante);
	void eliminar(int id);
	List<Vacante> buscarDestacadas();
	List<Vacante> buscarByExample(Example<Vacante> example);
	Page<Vacante> buscarTodas(Pageable page);
	
	
}
