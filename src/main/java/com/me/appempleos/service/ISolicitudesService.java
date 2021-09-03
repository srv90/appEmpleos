package com.me.appempleos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.me.appempleos.model.Solicitud;

public interface ISolicitudesService {
	void guardar(Solicitud solicitud);
	void eliminar(Integer idSolicitud);
	List<Solicitud> buscarTodas();
	Solicitud buscarPorId(Integer idSolicitud);
	Page<Solicitud> buscarTodas(Pageable page);
	List<Solicitud> buscarPorUsuario(int idUsuario);
	Solicitud buscarPorIdVacante(int idVacante, int idUsuario);
	int eliminarPorUsuario(int idUsuario);

}
