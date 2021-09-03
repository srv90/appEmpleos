package com.me.appempleos.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class Methods {

	public static String guardarArchivo(MultipartFile multiPart, String ruta) {
		String nombreOriginal = multiPart.getOriginalFilename();
		nombreOriginal = nombreOriginal.replace(" ", "-");
		String nombreFinal = randomAlphanumeric(8).concat(nombreOriginal);
		try {
			File imageFile = new File(ruta + nombreFinal);
			multiPart.transferTo(imageFile);
			return nombreFinal;
		} catch (IOException e) {
			System.out.println("Error " + e.getMessage());
			return null;
		}

	}
	
	public static boolean validarExtension(String extension) {
		 return !extension.equals("application/pdf");
	}

	public static String randomAlphanumeric(int count) {

		String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWYZ0123456789";
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * CHARACTERS.length());
			builder.append(CHARACTERS.charAt(character));

		}
		return builder.toString();
	}
	
	public static String mailSubject(List<String> categorias) {
		StringBuffer cadena = new StringBuffer();
		for (String categoria : categorias) {
			cadena.append(categoria);
			cadena.append(", ");
			
		}
		cadena.replace(cadena.lastIndexOf(","), cadena.lastIndexOf(",") + 1, ".");
		
		return cadena.toString();
	}
	

}
