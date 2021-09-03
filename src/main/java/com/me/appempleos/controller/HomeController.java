package com.me.appempleos.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.appempleos.model.Perfil;
import com.me.appempleos.model.Usuario;
import com.me.appempleos.model.Vacante;
import com.me.appempleos.service.ICategoriasService;
import com.me.appempleos.service.IUsuariosService;
import com.me.appempleos.service.IVacantesService;

@Controller
public class HomeController {

	@Autowired
	private IVacantesService serviceVacantes;

	@Autowired
	private IUsuariosService serviceUsuarios;

	@Autowired
	private ICategoriasService serviceCategorias;

	@Autowired
	private PasswordEncoder passwordEncoder;



	@GetMapping(value = "/")
	public String index(Model model) {
		
		model.addAttribute("lista", serviceVacantes.buscarDestacadas());
		return "home";
	}

	@GetMapping(value = "/index")
	public String mostrarIndex(Authentication auth, HttpSession session) {
		   Usuario usuario;
	
		if (session.getAttribute("usuario") == null) {
		    usuario = serviceUsuarios.buscarPorUsername(auth.getName());
			usuario.setPassword(null);
			session.setAttribute("usuario", usuario);

		}else {
			usuario = (Usuario) session.getAttribute("usuario");			
		}		
		return "redirect:/";
	}

	@GetMapping(value = "/register")
	public String registrarse(Usuario usuario) {

		return "usuarios/formRegistro";

	} 

	@RequestMapping(value = "register", params = { "registrar" }, method = RequestMethod.POST)
	public String guardarUsuario(@Valid Usuario usuario,BindingResult result,  Model model, RedirectAttributes attr) {

		
		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setEstatus(1);
		usuario.setFechaRegistro(new Date());
		Perfil perfil = new Perfil();
		perfil.setId(3);
		usuario.agregarPerfil(perfil);
		
		

		if (serviceUsuarios.buscarPorUsernameYEmail(usuario.getUsername(), usuario.getEmail())) {

			result.addError(new ObjectError("usuarioEmail", "El usuario y/o el email ya se encuentran tomados por otro usuario!"));
		}
		
		if(result.hasErrors()) {
			return "usuarios/formRegistro";

		}
		attr.addFlashAttribute("msg", "Ya puedes iniciar sesion!");
		serviceUsuarios.guardar(usuario);

		return "redirect:/";

	}

	@GetMapping(value = "/search")
	public String buscar(@ModelAttribute("vacanteSearch") Vacante vacante, Model model) {

		ExampleMatcher matcher = ExampleMatcher.matchingAny().withIgnoreCase().withMatcher("nombre",
				GenericPropertyMatchers.contains());
		Example<Vacante> example = Example.of(vacante, matcher);
		List<Vacante> lista = serviceVacantes.buscarByExample(example);

		if(lista.isEmpty()) {
			model.addAttribute("msg", "No hay vacantes que coincidan con tu busqueda.");
			return "home";

		}
		
		model.addAttribute("lista", lista);

		return "home";
	}
	
	@PostMapping("/ofertas")
	public String recibirOfertas(@RequestParam("listadoCategorias")List<String> categorias, HttpSession session, RedirectAttributes attr) {
		
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		serviceUsuarios.enviarMail(usuario.getNombre(), usuario.getEmail(), categorias);
		attr.addFlashAttribute("msg", "Ya te has suscripto. Se ha enviado un mail de confirmación a tu email.");
		return "redirect:/";
	}

	@GetMapping("/login")
	public String mostrarLogin( Usuario usuario) {
		
		return "formLogin";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, null, null);
		return "redirect:/";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));

	}

	@ModelAttribute
	public void setGenerics(Model model) {

		Vacante vacanteSearch = new Vacante();
		vacanteSearch.resetImage();
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		model.addAttribute("vacanteSearch", vacanteSearch);

	}

}
