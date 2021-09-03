package com.me.appempleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.me.appempleos.model.Vacante;
import com.me.appempleos.repository.VacantesRepository;
import com.me.appempleos.service.IVacantesService;

@Service
public class VacantesServiceJPA implements IVacantesService {

	@Autowired
	private VacantesRepository repo;
	
	@Override
	public List<Vacante> buscarTodas() {

		return repo.findAll();
	}

	@Override
	public Vacante buscarPorId(int id) {
		
		Optional<Vacante> optional = repo.findById(id);
		if(optional.isPresent()) {
			
			return optional.get();
		}
		
		return null;
	}

	@Override
	public void guardar(Vacante vacante) {

		repo.save(vacante);
	}

	@Override
	public void eliminar(int id) {

		repo.deleteById(id);
	}

	@Override
	public List<Vacante> buscarByExample(Example<Vacante> example) {

		return repo.findAll(example);
	}

	@Override
	public Page<Vacante> buscarTodas(Pageable page) {

		return repo.findAll(page);
	}

	@Override
	public List<Vacante> buscarDestacadas() {

		return repo.findByDestacadoAndEstatusOrderByIdDesc(1, "Activa");
	}




}
