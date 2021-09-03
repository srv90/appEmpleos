package com.me.appempleos.service;

import java.util.List;

import com.me.appempleos.model.Usuario;

public interface IUsuariosService {


	void guardar(Usuario usuario);
	void eliminar(Integer idUsuario);
	Usuario buscarPorId(int id);
	List<Usuario> buscarTodos();
	Usuario buscarPorUsername(String username);
	void bloquearDesbloquearUsuario(int estado, int idUsuario);
	List<Usuario> buscarDistintoId(Integer idUsuario);
	boolean buscarPorUsernameYEmail(String username, String email);
	void enviarMail(String username, String email, List<String> categorias);


}


