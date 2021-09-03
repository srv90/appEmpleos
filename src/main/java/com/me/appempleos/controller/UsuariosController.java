package com.me.appempleos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.appempleos.model.Usuario;
import com.me.appempleos.service.ICategoriasService;
import com.me.appempleos.service.IPerfilesService;
import com.me.appempleos.service.IUsuariosService;

@Controller
public class UsuariosController {

	@Autowired
	private IUsuariosService serviceUsuarios;

	@Autowired
	private ICategoriasService serviceCategorias;

	@Autowired
	private IPerfilesService servicePerfiles;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "usuarios/index", method = RequestMethod.GET)
	public String mostrarIndex(Usuario usuario, Model model) {

		model.addAttribute("lista", serviceUsuarios.buscarTodos());
		return "usuarios/listUsuarios";
	}

	@RequestMapping(value = "usuarios/create", method = RequestMethod.GET)
	public String crearUsuario(Usuario usuario) {

		return "usuarios/formRegistro";
	}

	@RequestMapping(value = "register", params = { "crear" }, method = RequestMethod.POST)
	public String crearUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes attr) {

		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

		if (serviceUsuarios.buscarPorUsernameYEmail(usuario.getUsername(), usuario.getEmail())) {
			result.addError(new ObjectError("usuarioEmail", "El usuario y/o el email ya se encuentran registrados!"));
		}

		if (result.hasErrors()) {

			return "usuarios/formRegistro";

		}

		serviceUsuarios.guardar(usuario);

		return "redirect:/usuarios/index";
	}

	@RequestMapping(value = "usuarios/edit/{id}", method = RequestMethod.GET)
	public String editarUsuario(Usuario usuario, @PathVariable("id") int id, Model model) {

		model.addAttribute("usuario", serviceUsuarios.buscarPorId(id));

		return "usuarios/formUsuario";
	}

	@RequestMapping(value = "usuarios/update", method = RequestMethod.POST)
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model,
			RedirectAttributes attributes, Authentication auth) {

		Usuario usuarioDb = serviceUsuarios.buscarPorId(usuario.getId());
		
		if (checkUsernameAndEmail(usuario)) {
			result.addError(new ObjectError("usuarioEmail", "El usuario y/o el email ya se encuentran tomados por otro usuario!"));
		}

		if (result.hasErrors()) {
			return "usuarios/formUsuario";

		} else {

			if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USUARIO"))) {

				checkPassword(usuario, attributes, usuarioDb);
				return "redirect:/";

			} else {

			   checkPassword(usuario, attributes, usuarioDb);
			   return "redirect:/usuarios/index";

			}
		}
	}


	@RequestMapping(value = "usuarios/delete/{id}", method = RequestMethod.GET)
	public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attributes, HttpServletRequest request,
			Authentication auth) {

		Usuario usuarioActual = serviceUsuarios.buscarPorUsername(auth.getName());

		if (usuarioActual.getId() == idUsuario) {
			serviceUsuarios.eliminar(idUsuario);
			SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
			logoutHandler.logout(request, null, null);
			return "redirect:/";

		}
		serviceUsuarios.eliminar(idUsuario);
		attributes.addFlashAttribute("msg", "Registro eliminado exitosamente.");
		return "redirect:/usuarios/index";
	}

	@RequestMapping(value = "usuarios/block/{id}", method = RequestMethod.GET)
	public String bloquear(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {

		Usuario usuario = serviceUsuarios.buscarPorId(idUsuario);
		serviceUsuarios.bloquearDesbloquearUsuario(0, idUsuario);
		attributes.addFlashAttribute("msg", "El usuario " + usuario.getUsername() + " ha sido bloqueado.");
		return "redirect:/usuarios/index";
	}

	@RequestMapping(value = "usuarios/unblock/{id}", method = RequestMethod.GET)
	public String desbloquear(@PathVariable("id") int idUsuario, RedirectAttributes attributes) {

		Usuario usuario = serviceUsuarios.buscarPorId(idUsuario);
		serviceUsuarios.bloquearDesbloquearUsuario(1, idUsuario);
		attributes.addFlashAttribute("msg", "El usuario  " + usuario.getUsername() + " ha sido desbloqueado.");
		return "redirect:/usuarios/index";
	}

	@ModelAttribute
	public void setGenerics(Model model) {

		model.addAttribute("listaPerfiles", servicePerfiles.buscarTodos());
		model.addAttribute("categorias", serviceCategorias.buscarTodas());

	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), false));

	}

	private void checkPassword(Usuario usuario, RedirectAttributes attributes, Usuario usuarioDb) {

		if (passwordEncoder.matches(usuarioDb.getPassword(), passwordEncoder.encode(usuario.getPassword()))) {

			guardarUsuario(usuario, attributes, usuarioDb);
		} else {

			guardarUsuario(usuario, attributes, usuarioDb);
		}
	}

	private void guardarUsuario(Usuario usuario, RedirectAttributes attributes, Usuario usuarioDb) {
		usuario.setPassword(usuarioDb.getPassword());
		serviceUsuarios.guardar(usuario);
		attributes.addFlashAttribute("msg", "Los datos han sido modificados.");
	}
	
	private boolean checkUsernameAndEmail(Usuario usuario) {
		boolean existe = false;

		for (Usuario usuarioTmp : serviceUsuarios.buscarDistintoId(usuario.getId())) {
			if (usuarioTmp.getUsername().equalsIgnoreCase(usuario.getUsername())
					|| usuarioTmp.getEmail().equalsIgnoreCase(usuario.getEmail())) {
				existe = true;
				break;
			}

		}
		return existe;
	}

}