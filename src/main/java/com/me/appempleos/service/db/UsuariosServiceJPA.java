package com.me.appempleos.service.db;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.me.appempleos.model.Usuario;
import com.me.appempleos.repository.UsuariosRepository;
import com.me.appempleos.service.IUsuariosService;
import com.me.appempleos.util.Methods;

@Service
public class UsuariosServiceJPA implements IUsuariosService {
	
	@Autowired
	private UsuariosRepository repo;
	
	
	@Autowired
	private JavaMailSender javaMailSender;
	

	@Override
	public void guardar(Usuario usuario) {
		
		repo.save(usuario);

	}

	@Override
	public void eliminar(Integer idUsuario) {
		
		repo.deleteById(idUsuario);
	}

	@Override
	public List<Usuario> buscarTodos() {

		return repo.findAll();
	}

	@Override
	public Usuario buscarPorUsername(String username) {

		return repo.findByUsername(username);
	}

	@Override
	public void bloquearDesbloquearUsuario(int estado, int idUsuario) {

		repo.bloquearDesbloquearUsuario(estado, idUsuario);
	}


	@Override
	public Usuario buscarPorId(int id) {

		Optional<Usuario> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		return null;
	}

	@Override
	public boolean buscarPorUsernameYEmail(String username, String email) {

		return repo.existsByUsernameOrEmail(username, email);
	}

	@Override
	public List<Usuario> buscarDistintoId(Integer idUsuario) {

		return repo.buscarDistintoId(idUsuario);
	}
	

	@Override
	public void enviarMail(String username, String email, List<String> categorias) {

		String mensaje = Methods.mailSubject(categorias);

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(email);
		simpleMailMessage.setSubject("Confirmación");
		
	    simpleMailMessage.setText("Hola " + username + "\r\n\n Este es un mail de confirmación de que te has suscripto a las siguientes categorias: "
		+ mensaje +"\r\n\n Saludos, AppEmpleos");
		
		javaMailSender.send(simpleMailMessage);
		
	}







}
