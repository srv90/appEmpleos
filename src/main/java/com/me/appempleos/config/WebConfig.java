package com.me.appempleos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Value(value = "${appempleos.ruta.images}")
	private String ruta;
	
	@Value("${appempleos.ruta.cv}")
	private String rutaCv;
	

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		 registry.addResourceHandler("/logos/**").addResourceLocations("file:" +ruta);
	     registry.addResourceHandler("/cv/**").addResourceLocations("file:"+ rutaCv); 

		
	}
	
	
}
