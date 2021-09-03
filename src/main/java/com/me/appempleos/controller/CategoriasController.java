package com.me.appempleos.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.me.appempleos.model.Categoria;
import com.me.appempleos.service.ICategoriasService;

@Controller
@RequestMapping(value = "/categorias")
public class CategoriasController {

	@Autowired
	private ICategoriasService categoriasService;

	@GetMapping(value = "/index")
	public String mostrarIndex(Model model) {

		model.addAttribute("lista", categoriasService.buscarTodas());
		return "categorias/listCategorias";
	}

	@GetMapping(value = "/create")
	public String crear(Categoria categoria) {

		return "categorias/formCategoria";
	}

	@PostMapping(value = "/save")
	public String guardar(@Valid Categoria categoria, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {

			return "categorias/formCategoria";

		}

		attributes.addFlashAttribute("msg", "Registro guardado exitosamente");
		categoriasService.guardar(categoria);

		return "redirect:/categorias/indexPaginate";

	}

	@GetMapping(value = "/edit/{id}")
	public String editar(@PathVariable("id") int id, Model model) {

		model.addAttribute("categoria", categoriasService.buscarPorId(id));
		return "categorias/formCategoria";

	}

	@GetMapping(value = "/delete/{id}")
	public String eliminar(@PathVariable("id") int id, RedirectAttributes attributes) {

		categoriasService.eliminar(id);
		attributes.addFlashAttribute("msg", "Registro eliminado exitosamente");
		return "redirect:/categorias/indexPaginate";
	}

	@GetMapping(value = "/indexPaginate")
	public String mostrarIndexPageable(Model model, @PageableDefault(sort = { "nombre" }, direction = Sort.Direction.ASC) Pageable page) {
		Page<Categoria> lista = categoriasService.buscarTodas(page);
		model.addAttribute("categorias", lista);
		return "categorias/listCategorias";

	}
}