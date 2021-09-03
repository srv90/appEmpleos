package com.me.appempleos.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.appempleos.model.Usuario;
import com.me.appempleos.model.Vacante;
import com.me.appempleos.service.ICategoriasService;
import com.me.appempleos.service.ISolicitudesService;
import com.me.appempleos.service.IUsuariosService;
import com.me.appempleos.service.IVacantesService;
import com.me.appempleos.util.Methods;

@Controller
@RequestMapping(value = "/vacantes")
public class VacantesController {

	@Value(value = "${appempleos.ruta.images}")
	private String ruta;

	@Autowired
	private IVacantesService vacanteService;

	@Autowired
	private ICategoriasService categoriaService;

	@Autowired
	private ISolicitudesService solicitudService;

	@Autowired
	private IUsuariosService usuarioService;

	@GetMapping(value = "/index")
	public String mostrarIndex(Model model) {

		model.addAttribute("lista", vacanteService.buscarTodas());

		return "vacantes/listVacantes";

	}

	@PostMapping("/save")
	public String guardar(@Valid Vacante vacante, BindingResult result, RedirectAttributes attributes,
			@RequestParam("archivoImagen") MultipartFile multiPart) {

		if (!multiPart.isEmpty()) {
			if(multiPart.getSize() > 2097152) {
	      		result.addError(new ObjectError("archivoCV", "El archivo no puede superar los 2MB"));		
			}
			String nombreImagen = Methods.guardarArchivo(multiPart, ruta);
			if (nombreImagen != null) {
				vacante.setImagen(nombreImagen);
			}
		}

		if (result.hasErrors()) {

			return "vacantes/formVacante";
		}

		
		vacanteService.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro guardado exitosamente");
		return "redirect:/vacantes/indexPaginate";
	}

	@GetMapping(value = "/create")
	public String crear(Vacante vacante, Model model) {
		return "vacantes/formVacante";
	}

	@GetMapping(value = "/view/{id}")
	public String verDetalle(@PathVariable("id") int id, Model model, Authentication authentication,
			HttpSession session, RedirectAttributes attributes, Principal user) {

		model.addAttribute("vacante", vacanteService.buscarPorId(id));

		if (user != null) {
			if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("USUARIO"))) {

				Usuario usuario = usuarioService.buscarPorUsername(authentication.getName());

				if (solicitudService.buscarPorIdVacante(id, usuario.getId()) != null) {

					model.addAttribute("msg", "Ya te has postulado a esta oferta.");
					return "detalle";

				}
				model.addAttribute("vacante", vacanteService.buscarPorId(id));
				return "detalle";
			}
			 else {

				model.addAttribute("msg", "Solo los usuarios puede postularse.");
				return "detalle";
			}
		}
		model.addAttribute("msg", "Debes iniciar sesion para postularte a esta oferta.");

		return "detalle";
	}

	@GetMapping(value = "/edit/{id}")
	public String editar(Vacante vacante, @PathVariable("id") int id, Model model) {

		model.addAttribute("vacante", vacanteService.buscarPorId(id));

		return "vacantes/formVacante";
	}

	@GetMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable("id") int id, RedirectAttributes attributes) {

		vacanteService.eliminar(id);
		attributes.addFlashAttribute("msg", "Registro eliminado exitosamente");

		return "redirect:/vacantes/indexPaginate";
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPageable(Model model, Pageable page) {

		Page<Vacante> lista = vacanteService.buscarTodas(page);

		model.addAttribute("vacantes", lista);
		return "vacantes/listVacantes";

	}

	@ModelAttribute
	public void agregarCategorias(Model model) {

		model.addAttribute("categorias", categoriaService.buscarTodas());

	}

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {

		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("dd-MM-yyyy"), false));
	}

}
