-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 03-09-2021 a las 01:24:38
-- Versión del servidor: 10.1.40-MariaDB
-- Versión de PHP: 7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `empleosbd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `descripcion`) VALUES
(2, 'Contablidad/Finanzas', 'Actividades para mantener oportuna y correcta la aplicacion del sistema contable y presupuestal asi como mantener en forma eficiente la programacion, pago de los egresos y tambien la respectiva creacion de pasivos.'),
(6, 'Servicio y atencion al cliente', 'Actividades relacionadas con ofrecer servicios y atencion a los clientes de forma efectiva.'),
(7, 'Logistica/transportes', 'Trabajos relacionados con capacidad de observaciÃ³n, buena memoria, habilidad numÃ©rica y verbal, razonamiento concreto y abstracto, imaginaciÃ³n e inventiva, habilidad para el manejo de instrumentos y material de laboratorio, capacidad de adaptaciÃ³n social y trabajo de campo.'),
(8, 'Desarrollo de software', 'Desarrolladores capaces de analizar, diseÃ±ar y mejorar estratÃ©gicamente proyectos de sistemas de software mediante la aplicaciÃ³n de procesos, modelos, herramientas y estÃ¡ndares de calidad en su desarrollo.'),
(9, 'Diseño', 'Crear conceptos visuales para publicidad, reunirse con clientes para conocer el presupuesto del proyecto, asesorar a los clientes para crear estrategias de publicidad visual, liderar equipos de trabajo, diseÃ±ar logotipos.'),
(12, 'Ventas', 'Habilidades para supervisar y dirigir las actividades de una oficina o de un departamento de Ventas. Coordinar y monitorear el trabajo de los empleados a su cargo.'),
(13, 'Publicidad', 'Habilidadeds para planificar, dirigir y coordinar las actividades de publicidad y relaciones pÃºblicas de la empresa u organizaciÃ³n. DiseÃ±ar y planificar campaÃ±as publicitarias. Dirigir y gestionar las actividades del personal de publicidad y relaciones pÃºblicas.'),
(14, 'Gerencia/Administracion', 'Profesionales capaces de programar, organizar, dirigir, controlar y supervisar las actividades de personal, tesorerÃ­a, contabilidad y costos, logÃ­stica y servicios internos y de mantenimiento.'),
(15, 'Educación Social', 'Funciones de docencia de carÃ¡cter profesional que implica la realizaciÃ³n directa de los procesos sistemÃ¡ticos de enseÃ±anza - aprendizaje, lo cual incluye el diagnÃ³stico, la planificaciÃ³n, la ejecuciÃ³n y la evaluaciÃ³n de los mismos procesos y sus resultados.'),
(16, 'Energía y minería', 'Promover el desarrollo de proyectos de infraestructura energÃ©tica y minera a lo largo y ancho del paÃ­s.'),
(17, 'RR.HH', '\r\nAdministraciÃ³n de nÃ³minas, pagos extras de los empleados, supervisiÃ³n del trabajo de los empleados y determinar las necesidades del personal.');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `perfiles`
--

CREATE TABLE `perfiles` (
  `id` int(11) NOT NULL,
  `perfil` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `perfiles`
--

INSERT INTO `perfiles` (`id`, `perfil`) VALUES
(1, 'ADMINISTRADOR'),
(2, 'SUPERVISOR'),
(3, 'USUARIO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes`
--

CREATE TABLE `solicitudes` (
  `id` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `archivo` varchar(250) NOT NULL,
  `comentarios` text,
  `idVacante` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarioperfil`
--

CREATE TABLE `usuarioperfil` (
  `idUsuario` int(11) NOT NULL,
  `idPerfil` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarioperfil`
--

INSERT INTO `usuarioperfil` (`idUsuario`, `idPerfil`) VALUES
(16, 1),
(37, 2),
(40, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `estatus` int(11) NOT NULL DEFAULT '1',
  `fechaRegistro` date DEFAULT NULL,
  `terminosYCondiciones` enum('True','False') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `email`, `username`, `password`, `estatus`, `fechaRegistro`, `terminosYCondiciones`) VALUES
(16, 'juan', 'juan@gmail.com', 'juan', '$2a$10$LpX/Xl7P5xfqCoBUkmNdbeoT1tUEM2Ekk0MgDLfwFulvWMa6.jPcS', 1, '2020-02-18', 'True'),
(37, 'Ana', 'ana@yahoo.com', 'ana', '$2a$10$V6RGrk7dHUa3Ix/HIUkv7efcDGoTfcAznTV1p0tpVht2UjHuSfoc2', 1, '2021-01-24', 'True'),
(40, 'tomas', 'tomas@gmail.com', 'tomas', '$2a$10$x849FDeIx511Gs4xOOI9ouHgbVGSzRm4sTjEcDCpEbAZKwsi5cI.i', 1, '2021-09-02', 'True');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vacantes`
--

CREATE TABLE `vacantes` (
  `id` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha` date NOT NULL,
  `salario` double NOT NULL,
  `estatus` enum('Activa','Inactiva') NOT NULL,
  `destacado` int(11) NOT NULL,
  `imagen` varchar(250) NOT NULL,
  `detalles` text,
  `idCategoria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `vacantes`
--

INSERT INTO `vacantes` (`id`, `nombre`, `descripcion`, `fecha`, `salario`, `estatus`, `destacado`, `imagen`, `detalles`, `idCategoria`) VALUES
(12, 'Analista contable', 'Empresa internacional solicita Contador Público para realizar las siguientes actividades: conciliaciones y movimientos bancarios, emisión de estados financieros, cálculo de impuestos y presentación de declaraciones, estrategias fiscales, entre otros.', '2021-08-31', 50000, 'Activa', 1, 'QPINUBIGlogo15.png', '<p><strong>EMPRESA L&Iacute;DER EN LA ADMINISTRACI&Oacute;N DE CAPITAL HUMANO EST&Aacute; EN B&Uacute;SQUEDA DE:</strong><br /><br /><strong>EJECUTIVO DE CONTABLE</strong><br /><br /><strong><span style=\"color: #ff0000;\">REQUISITOS:</span></strong><br /><br />- Contabilidad (Titulado o Pasante)<br />- 1 a&ntilde;o de experiencia comprobable en el &aacute;rea contable.<br /><br /><span style=\"color: #ff0000;\"><strong>FUNCIONES:</strong></span><br /><br />- Manejar los registros contables (p&oacute;lizas de ingreso y egresos diarios).<br />- C&aacute;lculo de impuestos provisionales.<br />- C&aacute;lculo de declaraciones anuales para personas morales y f&iacute;sicas.<br />- Elaboraci&oacute;n de estados financieros.<br />- Atenci&oacute;n a visitas domiciliarias.<br /><br /><strong>ZONA DE TRABAJO: Barracas, Caba.</strong></p>\r\n<p><span style=\"color: #ff0000;\"><strong>OFRECEMOS:</strong></span></p>\r\n<p>- Prepaga Osde 510<br />- Bonos mensuales por objetivo<br /><br /><span style=\"color: #ff0000;\"><strong>REQUERIMIENTOS:</strong></span></p>\r\n<p>- Educaci&oacute;n m&iacute;nima: Educaci&oacute;n superior - Licenciatura<br />- A&ntilde;os de experiencia: 1<br />- Disponibilidad de viajar: Si<br />- Disponibilidad de cambio de residencia: No</p>\r\n<p><strong>INTERESADOS FAVOR DE ENVIAR SU CV POR ESTE MEDIO.</strong><br /><br /></p>', 2),
(15, 'Analista Programador Java', 'Nos encontramos en la búsqueda de Programadores JAVA para formar parte de un importante proyecto liderado por entidad bancaria de primera línea', '2021-01-25', 50000, 'Activa', 1, 'VUTW0VHYlogo11.png', '<p style=\"text-align: justify;\"><strong><span style=\"color: rgba(0, 0, 0, 0.87); font-family: Roboto, arial, sans-serif; white-space: pre-line;\">Principales responsabilidades &bull; Desarrollo de Funcionalidades Nuevas y Mejoras. &bull; Correcciones de Bugs sobre aplicativo productivo &bull; Despliegues de Pasajes entre ambientes Competencias T&eacute;cnicas Esperadas: &bull; S&oacute;lidos conocimientos en POO. &bull; Java8 en adelante. (J2EE). &bull; JSP, HTML, JQUERY, Javascript, CSS. &bull; Conocimientos en los m&oacute;dulos de Spring en especial Spring MVC, Spring Boot excluyente. &bull; SQL, Hibernate, JPA. &bull; Creaci&oacute;n e implementaci&oacute;n de Web Services tanto</span></strong><span class=\"WbZuDe\" style=\"display: inline; color: rgba(0, 0, 0, 0.87); font-family: Roboto, arial, sans-serif; white-space: pre-line;\"><strong> SOAP como REST.</strong> &bull; Conocimientos comandos b&aacute;sicos de linux, Tomcat 7 (no excluyentes)</span></p>', 8),
(16, 'Programador Javascript', 'Nos encontramos en la búsqueda de un Programador Javascript con conocimientos de mas de 3 años en: • HTML5 • .net', '2021-01-06', 50000, 'Activa', 1, 'G6P67A3NUMVY3X25teaching-tech-logo.png', '<p><span style=\"color: rgba(0, 0, 0, 0.87); font-family: Roboto, arial, sans-serif; white-space: pre-line;\">Desarrollar los requerimientos asignados: &bull; Programar las tareas asignadas cumpliendo los plazos. &bull; Cumplir con las normas de desarrollo implementadas en el sector. &bull; Generar un ambiente de testing. &bull; Implementar el requerimiento y asistir en la implementaci&oacute;n a los analistas funcionales y/o usuarios. Relevar los requerimientos &bull; Coordinar reuniones con los analistas funcionales y/o usuarios. &bull; Leer la documentaci&oacute;n de los</span><span class=\"WbZuDe\" style=\"display: inline; color: rgba(0, 0, 0, 0.87); font-family: Roboto, arial, sans-serif; white-space: pre-line;\"> requerimientos. &bull; Proponer cambios y mejoras. &bull; Acordar con el Sub Gerente los tiempos de los proyectos y gestionar los plazos de entrega. Resolver los incidentes &bull; Identificar los problemas y solucionarlos de acuerdo a su prioridad. &bull; Solucionar incidentes que sean identificados como urgentes &bull; Asistir en la resoluci&oacute;n de incidentes con los usuarios o colaboradores. &bull; Proponer mejoras que se identifiquen en la resoluci&oacute;n de los incidentes. SKILLS &bull; Programaci&oacute;n Java &bull; Programaci&oacute;n JavaScript &bull; Programaci&oacute;n HTML, CSS, etc. &bull; Servicios REST IDIOMA: &bull; Ingles: intermedio Lugar: CABA</span></p>', 8),
(17, 'Analista de RRHH', 'Estamos buscando Jefe de RRHH , para importante empresa de medios.', '2021-08-30', 90000, 'Activa', 1, 'KAR8R3LGlogo9.png', '<p><span style=\"color: rgba(0, 0, 0, 0.87); font-family: arial, sans-serif; white-space: pre-line;\">&nbsp;Las principales responsabilidades son: &bull; Clima Organizacional &bull; Detectar necesidades de desarrollo &bull; Seleccion &bull; Capacitaciones &bull; Legajos &bull; Soporte a todas la areas &bull; Manejo de areas sofft de rrhh integral &bull; Recepci&oacute;n de noedades &bull; Conocimiento de presuepuestos de ingreso y de salida &bull; Vacaciones &bull; OS Los requisitos son: &middot;Experiencia m&iacute;nima 3 a&ntilde;os en puesto</span><span class=\"WbZuDe\" style=\"display: inline; color: rgba(0, 0, 0, 0.87); font-family: arial, sans-serif; white-space: pre-line;\"> (Excluyente) &middot;Buena comunicaci&oacute;n, trabajo en equipo, flexibilidad, organizaci&oacute;n y planificaci&oacute;n e iniciativa. &middot;Contar con Notebook propia. &middot;Conocimiento de Tango (Deseable) Zona de Trabajo: COLEGIALES- Modalidad Mixta: 3 dias en oficina 2 dias home office Jornada laboral: Lunes a viernes de 09 a 18 hs Tipo de contrato: Indeterminad</span></p>', 7);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `perfiles`
--
ALTER TABLE `perfiles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `Vacante_Usuario_UNIQUE` (`idVacante`,`idUsuario`),
  ADD KEY `fk_Solicitudes_Vacantes1_idx` (`idVacante`),
  ADD KEY `fk_Solicitudes_Usuarios1_idx` (`idUsuario`);

--
-- Indices de la tabla `usuarioperfil`
--
ALTER TABLE `usuarioperfil`
  ADD UNIQUE KEY `Usuario_Perfil_UNIQUE` (`idUsuario`,`idPerfil`),
  ADD KEY `fk_Usuarios1_idx` (`idUsuario`),
  ADD KEY `fk_Perfiles1_idx` (`idPerfil`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username_UNIQUE` (`username`),
  ADD UNIQUE KEY `email_UNIQUE` (`email`);

--
-- Indices de la tabla `vacantes`
--
ALTER TABLE `vacantes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_vacantes_categorias1_idx` (`idCategoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `perfiles`
--
ALTER TABLE `perfiles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- AUTO_INCREMENT de la tabla `vacantes`
--
ALTER TABLE `vacantes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `solicitudes`
--
ALTER TABLE `solicitudes`
  ADD CONSTRAINT `fk_Solicitudes_Usuarios1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `fk_Solicitudes_Vacantes1` FOREIGN KEY (`idVacante`) REFERENCES `vacantes` (`id`);

--
-- Filtros para la tabla `usuarioperfil`
--
ALTER TABLE `usuarioperfil`
  ADD CONSTRAINT `fk_Perfiles1` FOREIGN KEY (`idPerfil`) REFERENCES `perfiles` (`id`),
  ADD CONSTRAINT `fk_Usuarios1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `vacantes`
--
ALTER TABLE `vacantes`
  ADD CONSTRAINT `fk_vacantes_categorias1` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
