package com.me.appempleos.service.db;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.me.appempleos.model.Perfil;
import com.me.appempleos.repository.PerfilesRepository;
import com.me.appempleos.service.IPerfilesService;

@Service
public class PerfilesServiceJPA implements IPerfilesService {
	
	
	@Autowired
	private PerfilesRepository repo;

	@Override
	public List<Perfil> buscarTodos() {

		return repo.findAll();
	}

}
