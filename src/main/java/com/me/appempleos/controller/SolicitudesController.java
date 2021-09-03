package com.me.appempleos.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.appempleos.model.Solicitud;
import com.me.appempleos.model.Usuario;
import com.me.appempleos.service.ICategoriasService;
import com.me.appempleos.service.ISolicitudesService;
import com.me.appempleos.service.IUsuariosService;
import com.me.appempleos.service.IVacantesService;
import com.me.appempleos.util.Methods;

@Controller
@RequestMapping(value = "/solicitudes")
public class SolicitudesController {

	@Value(value = "${appempleos.ruta.cv}")
	private String rutaCv;

	@Autowired
	private IVacantesService vacanteService;

	@Autowired
	private IUsuariosService usuarioService;

	@Autowired
	private ISolicitudesService solicitudService;

	@Autowired
	private ICategoriasService serviceCategorias;

	@GetMapping(value = "/index")
	public String mostrarIndex(Model model) {

		List<Solicitud> solicitudes = solicitudService.buscarTodas();
		if (solicitudes.isEmpty()) {
			model.addAttribute("msg", "No hay postulaciones en curso.");
		}
	    model.addAttribute("solicitudes", solicitudes );
		return "solicitudes/listSolicitudes";

	}

	@GetMapping("/apply/{id}")
	public String aplicar(@PathVariable int id, Model model, Solicitud solicitud) {

		model.addAttribute("vacante", vacanteService.buscarPorId(id));

		return "solicitudes/formSolicitud";
	}

	@PostMapping(value = "/save")
	public String guardar(Solicitud solicitud, BindingResult result, Model model, HttpSession session,
			@RequestParam("archivoCV") MultipartFile multiPart, RedirectAttributes attributes,
			Authentication authentication, @RequestParam("vacante.id") int id) {

		model.addAttribute("vacante", vacanteService.buscarPorId(id));

		if (!multiPart.isEmpty()) {
			if(multiPart.getSize() > 2097152) {
	      		result.addError(new ObjectError("archivoCV", "El archivo no puede superar los 2MB"));		

			}
			if(Methods.validarExtension(multiPart.getContentType())){
				result.addError(new ObjectError("archivoCV", "Solo puedes subir archivos en formato PDF"));
				return "solicitudes/formSolicitud";
			}
			String nombreArchivo = Methods.guardarArchivo(multiPart, rutaCv);
			if (nombreArchivo != null) {
				solicitud.setArchivo(nombreArchivo);
			}
		}else {
			result.addError(new ObjectError("archivoCV", "El archivo no puede estar vacio"));
		}
		
		if (result.hasErrors()) {
			return "solicitudes/formSolicitud";
		}

		Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());

		solicitud.setUsuario(usuario);
		solicitud.setFecha(new Date());
		solicitudService.guardar(solicitud);
		attributes.addFlashAttribute("msg", "Tu cv ha sido cargado exitosamente!");

		return "redirect:/solicitudes/misSolicitudes";
	}

	@GetMapping(value = "/misSolicitudes")
	public String mostrarIndexPorUsuario(Model model, Authentication authentication) {

		Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());
		List<Solicitud> solicitudes = solicitudService.buscarPorUsuario(usuario.getId());
		if (solicitudes.isEmpty()) {
			model.addAttribute("msg", "No tienes postulaciones en curso.");
		}
		model.addAttribute("solicitudes", solicitudes);

		return "solicitudes/listSolicitudes";
	}

	@GetMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable("id") int idSolicitud, RedirectAttributes attributes, Authentication auth) {

		solicitudService.eliminar(idSolicitud);

		if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USUARIO"))) {
			attributes.addFlashAttribute("msg", "La solicitud fue eliminada!");
			return "redirect:/solicitudes/misSolicitudes";

		} else {
			attributes.addFlashAttribute("msg", "La solicitud fue eliminada!");
			return "redirect:/solicitudes/index";
		}
	}

	@ModelAttribute
	public void setGenerics(Model model) {

		model.addAttribute("categorias", serviceCategorias.buscarTodas());

	}
}