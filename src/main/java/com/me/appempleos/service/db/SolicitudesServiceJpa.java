package com.me.appempleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.me.appempleos.model.Solicitud;
import com.me.appempleos.repository.SolicitudesRepository;
import com.me.appempleos.service.ISolicitudesService;

@Service
public class SolicitudesServiceJpa implements ISolicitudesService {

	@Autowired
	private SolicitudesRepository solicitudesRepo;
	
	@Override
	public void guardar(Solicitud solicitud) {
		solicitudesRepo.save(solicitud);
	}

	@Override
	public void eliminar(Integer idSolicitud) {
		solicitudesRepo.deleteById(idSolicitud);
	}

	@Override
	public List<Solicitud> buscarTodas() {
		return solicitudesRepo.findAll();
	}

	@Override
	public Solicitud buscarPorId(Integer idSolicitud) {
		Optional<Solicitud> optional = solicitudesRepo.findById(idSolicitud);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public Page<Solicitud> buscarTodas(Pageable page) {
		return solicitudesRepo.findAll(page);
	}

	@Override
	public Solicitud buscarPorIdVacante(int idVacante, int idUsuario) {
		
		Optional<Solicitud> optional = solicitudesRepo.buscarPorIdVacante(idVacante, idUsuario);
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public int eliminarPorUsuario(int idUsuario) {

		solicitudesRepo.eliminarPorIdUsuario(idUsuario);
		
		return 1;
	}

	@Override
	public List<Solicitud> buscarPorUsuario(int idUsuario) {

		return solicitudesRepo.listarPorUsuario(idUsuario);
	}
	}



