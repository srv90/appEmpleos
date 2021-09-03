package com.me.appempleos.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.me.appempleos.model.Solicitud;

public interface SolicitudesRepository extends JpaRepository<Solicitud, Integer> {

	@Query(nativeQuery = true, value = "SELECT * FROM solicitudes WHERE idUsuario = :idUsuario")
	public List<Solicitud> listarPorUsuario(@Param("idUsuario") int idUsuario);

	@Query(nativeQuery = true, value = "SELECT * FROM solicitudes WHERE idVacante = :idVacante AND idUsuario = :idUsuario")
	Optional<Solicitud> buscarPorIdVacante(@Param("idVacante") int idVacante, @Param("idUsuario") int idUsuario);
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "DELETE FROM solicitudes WHERE idUsuario = :idUsuario")
	public int eliminarPorIdUsuario(int idUsuario);

}
