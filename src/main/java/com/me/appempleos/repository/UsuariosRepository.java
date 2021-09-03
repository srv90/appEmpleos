package com.me.appempleos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.me.appempleos.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {

	Usuario findByUsername(String username);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true, value = "UPDATE usuarios SET estatus= :estatus WHERE id =:id")
	void bloquearDesbloquearUsuario(@Param("estatus") int estatus,  @Param("id") int idUsuario);

    boolean existsByUsernameOrEmail(String username, String email);


	@Query(nativeQuery = true, value = "SELECT * FROM usuarios WHERE  id != :id")
    List<Usuario> buscarDistintoId(@Param("id") int id); 

}
