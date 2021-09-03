package com.me.appempleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.me.appempleos.model.Categoria;
import com.me.appempleos.repository.CategoriasRepository;
import com.me.appempleos.service.ICategoriasService;

@Service

public class CategoriasServiceJPA implements ICategoriasService {

	@Autowired
	private CategoriasRepository repo;
	
	@Override
	public void guardar(Categoria categoria) {

		repo.save(categoria);
	}

	@Override
	public List<Categoria> buscarTodas() {

		return repo.findAll();
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {

		Optional<Categoria> optional = repo.findById(idCategoria);
		if(optional.isPresent()) {
			
			return optional.get();
		}
		return null;
	}

	@Override
	public void eliminar(Integer idCategoria) {
		
		repo.deleteById(idCategoria);
		
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable page) {

		return repo.findAll(page);
	}


}
