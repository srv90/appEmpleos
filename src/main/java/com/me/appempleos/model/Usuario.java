package com.me.appempleos.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Usuarios")
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "{usuario.username}")
	@Size(min = 3,  max= 15,message = "{usuario.username}")
	private String username;
	
	@NotBlank(message = "{usuario.nombre}")
	@Size(min = 3,  max= 15,message = "{usuario.nombre}")
	private String nombre;
	
	@Email(message = "{usuario.email}")
	@NotBlank(message = "{usuario.email}")
	@Pattern(regexp=".+@.+\\..+")
	private String email;

	@NotBlank(message = "{usuario.password}")
	private String password;
	private Integer estatus;
	
	private Date fechaRegistro;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
	@JoinTable(name = "UsuarioPerfil", joinColumns = @JoinColumn(name = "idUsuario"), inverseJoinColumns = @JoinColumn(name = "idPerfil"))
	private List<Perfil> perfiles;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Solicitud> solicitudes;

	@AssertTrue(message = "{usuario.terminosYCondiciones}")
	private boolean terminosYCondiciones = true;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public List<Perfil> getPerfiles() {
		return perfiles;
	}

	public void setPerfiles(List<Perfil> perfiles) {
		this.perfiles = perfiles;
	}

	public boolean isTerminosYCondiciones() {
		return terminosYCondiciones;
	}

	public void setTerminosYCondiciones(boolean terminosYCondiciones) {
		this.terminosYCondiciones = terminosYCondiciones;
	}

	public List<Solicitud> getSolicitudes() {
		return solicitudes;
	}

	public void setSolicitudes(List<Solicitud> solicitudes) {
		this.solicitudes = solicitudes;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", nombre=" + nombre + ", email=" + email
				+ ", password=" + password + ", estatus=" + estatus + ", fechaRegistro=" + fechaRegistro + ", perfiles="
				+ perfiles + ", solicitudes=" + solicitudes + ", terminosYCondiciones=" + terminosYCondiciones
				+ "]";
	}

	public void agregarPerfil(Perfil tempPerfil) {
		if (perfiles == null) {
			perfiles = new LinkedList<Perfil>();
		}
		perfiles.add(tempPerfil);
	}

}
