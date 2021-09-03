package com.me.appempleos.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@NotBlank(message = "{categoria.nombre}")
	private String nombre;
	@NotBlank(message = "{categoria.descripcion}")
	private String descripcion;
	
	@OneToMany(mappedBy = "categoria")
	private List<Vacante> vacante;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	public List<Vacante> getVacante() {
		return vacante;
	}
	public void setVacante(List<Vacante> vacante) {
		this.vacante = vacante;
		
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", vacante=" + vacante
				+ "]";
	}
	
}