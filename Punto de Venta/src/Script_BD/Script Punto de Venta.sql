/*
Instituto Tecnológico de León
Materia: Gestion de Proyectos de Software
Fecha: 2 de Octubre del 2018
Carrera: Ingeniería en Sistemas Computacionales
Semestre 7
Integrantes
*/


/*create database punto_de_venta;*/
DROP SCHEMA IF EXISTS punto_de_venta;
create database punto_de_venta;

use punto_de_venta;

create table sucursal(
id_sucursal int not null primary key auto_increment,
nombre varchar(100) not null,
sucursal varchar(50) not null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100) not null,
colonia varchar(100) null,
municipio varchar(200) null,
cp varchar(5) null,
estado varchar(200) null,
status varchar(20)
) ENGINE=InnoDB;

create table Proveedor(
id_proveedor int not null primary key auto_increment,
nombre_proveedor varchar(40) not null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null,
status varchar(20)
) ENGINE=InnoDB;

create table Cliente(
id_cliente int not null primary key auto_increment,
nombre varchar(100) not null,
rfc varchar(15) null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null,
status varchar(20) null
) ENGINE=InnoDB;


create table Usuario(
id_usuario int not null primary key auto_increment,
nombre varchar(100) not null,
login varchar (100) not null,
contraseña varchar(100)not null,
rol varchar(100)not null,
status varchar(20)
) ENGINE=InnoDB;


create table Categoria(
id_categoria int not null primary key auto_increment,
nombre varchar(200) not null,
descripcion varchar(300) null,
status varchar(20)
)ENGINE=InnoDB;

create table Marca(
id_marca int not null primary key auto_increment,
marca  varchar(200),
status varchar(20) 
)ENGINE=InnoDB;

create table Producto(
id_producto int not null primary key auto_increment,
codigo_producto varchar(13) not null UNIQUE,
descripcion varchar(400) not null,
marca varchar(200) null,
costo float(15) null,
precio float(15) not null,
presentacion varchar(200) not null,
stock int not null,
stock_minimo int not null,
id_categoria int not null,
id_proveedor int not null,
status varchar(20)
)ENGINE=InnoDB;


create table Ventas(
id_venta int not null primary key auto_increment,
fecha_venta date not null,
subtotal_venta float (15) not null,
iva_venta float(5) not null,
total_venta float(15) not Null,
forma_pago varchar(20),
id_usuario int not null,
id_cliente int not null,
status varchar(20)
)ENGINE=InnoDB;

create table Detalle_Venta(
id_det_ventas int not null primary key auto_increment,
id_venta int not null,
id_producto int not null,
cantidad int not null,
importe float(15) null,
status varchar(20)
)ENGINE=InnoDB;

create table Factura_Pedido(
folio_factura varchar(17) not null primary key,
id_proveedor int not null,
id_usuario int not null,
montoFactura float(15) not null,
fecha date not null,
status varchar(20)
)ENGINE=InnoDB;


/*************************************************************************Llaves foraneas **********/
/*****Llaves foranes en Productos*/
ALTER TABLE Producto  ADD
CONSTRAINT FK_producto_categoria
FOREIGN KEY (id_categoria)
REFERENCES Categoria (id_categoria)
/*ON UPDATE CASCADE ON DELETE restrict*/;

ALTER TABLE Producto  ADD
CONSTRAINT FK_producto_proveedor
FOREIGN KEY (id_proveedor)
REFERENCES Proveedor (id_proveedor)
/*ON UPDATE CASCADE ON DELETE restrict*/;



/***********Llaves foraneas en Ventas*/
ALTER TABLE Ventas  ADD
CONSTRAINT FK_venta_usuario
FOREIGN KEY (id_usuario)
REFERENCES Usuario(id_usuario);

ALTER TABLE  Ventas  ADD
CONSTRAINT FK_venta_cliente
FOREIGN KEY (id_cliente)
REFERENCES Cliente (id_cliente);


/***********Llaves foraneas en Detalle_Venta*/
ALTER TABLE  Detalle_Venta  ADD
CONSTRAINT FK_detalleVenta_ventas
FOREIGN KEY (id_venta)
REFERENCES Ventas (id_venta);


ALTER TABLE  Detalle_Venta  ADD
CONSTRAINT FK_detalleVenta_productos
FOREIGN KEY (id_producto)
REFERENCES Producto (id_producto);


/***********Llaves foraneas en Factura_Pedido*/
ALTER TABLE  Factura_Pedido  ADD
CONSTRAINT FK_FacturaPedido_proveedor
FOREIGN KEY (id_proveedor)
REFERENCES Proveedor (id_proveedor);


ALTER TABLE  Factura_Pedido  ADD
CONSTRAINT FK_FacturaPedido_usuario
FOREIGN KEY (id_usuario)
REFERENCES Usuario (id_usuario);

INSERT INTO USUARIO VALUES (null,'Programadores del sistema','admin','123', 'Programador','activo');
INSERT INTO USUARIO VALUES (null,'miguel Angel','mike','mike123', 'Programador','activo');
INSERT INTO USUARIO VALUES (null,'hernan','hernan','h123', 'Programador','activo');
INSERT INTO USUARIO VALUES (null,'Encargado de almacen','useralmacen','alm123', 'Encargado de Almacen','activo');
INSERT INTO USUARIO VALUES (null,'Vendedor general','vendedor','v123', 'Empleado','activo');



use punto_de_venta;

select * from Cliente;
select * from Usuario;
select * from Categoria;
select * from Producto;
select * from Proveedor;
select * from Ventas;
select * from Detalle_Venta;
select * from categoria;
select * from marca;


/*****************************************************************************************************
					PROCEDIMIENTOS ALMACENADOS
****************************************************************************************************/
/**********************************************************Procedimientos de USUARIO*/

DROP PROCEDURE IF EXISTS getUsuarios;
CREATE PROCEDURE getUsuarios ()
	SELECT * FROM Usuario WHERE status='activo';
/*	
call getUsuarios();	
SELECT * FROM USUARIO;
*/


DROP PROCEDURE IF EXISTS addUsuario;
CREATE PROCEDURE addUsuario(
nombre VARCHAR(100), 
login VARCHAR(100), 
pass VARCHAR(100), 
rol VARCHAR(100))
INSERT INTO Usuario VALUES(null, nombre, login, pass, rol,'activo');
/*	
call addUsuario('Usuario prueba','vendedor','asd', 'Empleado');	
*/


DROP PROCEDURE IF EXISTS updateUsuario;
CREATE PROCEDURE updateUsuario(
id int, 
nombre VARCHAR(100), 
login VARCHAR(100), 
pass VARCHAR(100), 
rol VARCHAR(100))
UPDATE Usuario SET Usuario.nombre = nombre, Usuario.login = login, Usuario.contraseña = pass, 
Usuario.rol = rol WHERE Usuario.id_usuario = id;
/*	
call updateUsuario(6,'Usuario prueba','vendedor','contraseña', 'Empleado');	
*/


/*
DROP PROCEDURE IF EXISTS deleteUsuario;
CREATE PROCEDURE deleteUsuario (ID int)
DELETE FROM Usuario WHERE id_usuario = ID;         
*/
DROP PROCEDURE IF EXISTS deleteUsuario;
CREATE PROCEDURE deleteUsuario(
id int)
UPDATE Usuario SET Usuario.status = 'inactivo' WHERE Usuario.id_usuario = id;
/*	
call deleteUsuario(6);	
*/



DROP PROCEDURE IF EXISTS getBusquedaUsuario1;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaUsuario1(  
nombre VARCHAR(100), 
login VARCHAR(100), 
rol VARCHAR(100))
	SELECT * from Usuario AS a
	where  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.login like (CONCAT('%',login,'%'))
	AND  a.rol like (CONCAT('%',rol,'%'))
    AND  status='activo'
;


DROP PROCEDURE IF EXISTS getBusquedaUsuario2;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaUsuario2( 
id int, 
nombre VARCHAR(100), 
login VARCHAR(100), 
rol VARCHAR(100))
	SELECT * from Usuario AS a
	where 
	a.id_usuario like (CONCAT('%',id,'%'))
	AND  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.login like (CONCAT('%',login,'%'))
	AND  a.rol like (CONCAT('%',rol,'%'))
    AND status='activo'
;

/*pruebas*/
/*
call getBusquedaUsuario1('','','');
call getBusquedaUsuario2(2,'','','');
*/

DROP PROCEDURE IF EXISTS getBusquedaUsuario3;
CREATE PROCEDURE getBusquedaUsuario3( 
id VARCHAR(100),  
nombre VARCHAR(100), 
login VARCHAR(100), 
rol VARCHAR(100))
	SELECT * from Usuario AS a
	where CONVERT(a.id_usuario,CHAR) like (CONCAT('%',id,'%'))
	AND  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.login like (CONCAT('%',login,'%'))
	AND  a.rol like (CONCAT('%',rol,'%'))
	AND status='activo'
;

/*
call getBusquedaUsuario3('','','','');
call getBusquedaUsuario3('2','','','');

*/

/***********************************************************PROC Validacion LOGIN	---------------*/

DROP PROCEDURE IF EXISTS getLogin;
CREATE PROCEDURE getLogin()
SELECT * FROM usuario WHERE status='activo';

 DROP PROCEDURE IF EXISTS buscarLogin;
 CREATE PROCEDURE buscarLogin(login VARCHAR(100), passw VARCHAR(100))
 select * from Usuario as u
 where u.login = login
 AND u.contraseña =passw
 AND status='activo';

/*
 call buscarLogin('Admin','123'); 
 call buscarLogin('Admin','122'); 
  call buscarLogin('mike','mike123'); 
 */
 
 
 /***********************************************************Procedimientos de CLIENTE   -----------*/
 DROP PROCEDURE IF EXISTS getClientes;
CREATE PROCEDURE getClientes ()
	SELECT * FROM Cliente WHERE Cliente.status = 'activo';
 
call getClientes;
 

DROP PROCEDURE IF EXISTS addCliente;
CREATE PROCEDURE addCliente(
nombre varchar(100),
rfc varchar(15),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
INSERT INTO Cliente VALUES(null, nombre, rfc, 
telefono, correo, direccion, colonia, municipio, cp, estado, 'activo');


call addCliente ('Venta Publico General','XXXXXXXXXX', '4772129324','ferreteriaaraujo@hotmail.com', 'Blvd. Antonio Madrazo #6703','Los Murales', 'León', '37219','Guanajuto');
call addCliente ('Emmanuel Rodriguez Liñan', 'ROLE21215LIT','4772151254', 'intelecto@hotmail.com' , 'Mision Catolica #8723', 'Caja Popular' , 'León', '37207', 'Guanajuato');
call addCliente ('FHINO Construcciones SA DE CV','FIN8817363L12', '47754578', 'fernandofhino@hotmail.com', 'Panorama #1983', 'Buenos Aires','León' ,'37644', 'Guanajuato');
call addCliente ('Inmobiliaria Linaza','LIN9818733LZ1','4737377322','robertogarcia@hotmail.com', 'San Juan de Arriba #8722', '10 de Mayo', 'León', '37732','Guanajuato');
call addCliente ('Tomas Humberto Bustos Solis','BUST19751LM', '4737636421', 'tomashumberto123@hotmail.com', 'Industrial #8712', 'La Hermita',  'Guanajuato', '36111','Guanajuato');
 
 DROP PROCEDURE IF EXISTS updateCliente;
CREATE PROCEDURE updateCliente(
id_cliente int,
nombre varchar(100),
rfc varchar(15),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
UPDATE Cliente SET Cliente.nombre = nombre, Cliente.rfc = rfc,
Cliente.telefono = telefono, Cliente.correo= correo, Cliente.direccion = direccion, Cliente.colonia = colonia, 
Cliente.municipio = municipio, Cliente.cp = cp, Cliente.estado = estado WHERE Cliente.id_cliente = id_cliente;

DROP PROCEDURE IF EXISTS deleteCliente;
CREATE PROCEDURE deleteCliente(
id int)
UPDATE Cliente SET Cliente.status = 'inactivo' WHERE Cliente.id_cliente = id;

DROP PROCEDURE IF EXISTS getBusquedaCliente1;
CREATE PROCEDURE getBusquedaCliente1(  
nombre varchar(100),
rfc varchar(15),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Cliente AS c
	where  c.nombre like (CONCAT('%',nombre,'%'))
	AND  c.rfc like (CONCAT('%',rfc,'%'))
    AND c.telefono like (CONCAT('%',telefono,'%'))
    AND c.correo like (CONCAT('%',correo,'%'))
    AND c.direccion like (CONCAT('%',direccion,'%'))
    AND c.colonia like (CONCAT('%',colonia,'%'))
    AND c.municipio like (CONCAT('%',municipio,'%'))
    AND c.cp like (CONCAT('%',cp,'%'))
    AND c.estado like (CONCAT('%',estado,'%'))
    AND status = 'activo'
;

DROP PROCEDURE IF EXISTS getBusquedaCliente2;
CREATE PROCEDURE getBusquedaCliente2(  
id_cliente int,
nombre varchar(100),
rfc varchar(15),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Cliente AS c
	where c.id_cliente like (CONCAT('%',id_cliente,'%')) 
    AND c.nombre like (CONCAT('%',nombre,'%'))
	AND  c.rfc like (CONCAT('%',rfc,'%'))
    AND c.telefono like (CONCAT('%',telefono,'%'))
    AND c.correo like (CONCAT('%',correo,'%'))
    AND c.direccion like (CONCAT('%',direccion,'%'))
    AND c.colonia like (CONCAT('%',colonia,'%'))
    AND c.municipio like (CONCAT('%',municipio,'%'))
    AND c.cp like (CONCAT('%',cp,'%'))
    AND c.estado like (CONCAT('%',estado,'%'))
    AND status = 'activo';
;

 
 
 DROP PROCEDURE IF EXISTS getBusquedaCliente3;
CREATE PROCEDURE getBusquedaCliente3( 
id_cliente varchar(100),
nombre varchar(100),
rfc varchar(15),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Cliente AS c
	where CONVERT(c.id_cliente,CHAR) like (CONCAT('%',id_cliente,'%'))
	AND c.nombre like (CONCAT('%',nombre,'%'))
	AND  c.rfc like (CONCAT('%',rfc,'%'))
    AND c.telefono like (CONCAT('%',telefono,'%'))
    AND c.correo like (CONCAT('%',correo,'%'))
    AND c.direccion like (CONCAT('%',direccion,'%'))
    AND c.colonia like (CONCAT('%',colonia,'%'))
    AND c.municipio like (CONCAT('%',municipio,'%'))
    AND c.cp like (CONCAT('%',cp,'%'))
    AND c.estado like (CONCAT('%',estado,'%'))
    AND status = 'activo';

 /*
 call getBusquedaCliente3('2','','','','','','','','',''); 
 call getBusquedaCliente3('','','','','','','','','',''); 
  call getBusquedaCliente3('','','','','','','','','',''); 
 */
 
 DROP PROCEDURE IF EXISTS getBusquedaCliente4;
CREATE PROCEDURE getBusquedaCliente4( 
id_cliente varchar(100),
nombre varchar(100),
rfc varchar(15))
	SELECT * from Cliente AS c
	where CONVERT(c.id_cliente,CHAR) like (CONCAT('%',id_cliente,'%'))
	AND c.nombre like (CONCAT('%',nombre,'%'))
	AND  c.rfc like (CONCAT('%',rfc,'%'))
    AND status = 'activo';
 
  /*
 call getBusquedaCliente4('1','',''); 
 call getBusquedaCliente4('2','','');  
  call getBusquedaCliente4('','',''); 
 */
 
 
 /***********************************************************Procedimientos de CATEGORIA*/
SELECT * FROM Categoria;
/*
id_categoria int not null primary key auto_increment,
nombre varchar(200) not null,
descripcion varchar(300) null
*/

DROP PROCEDURE IF EXISTS getCategorias;
CREATE PROCEDURE getCategorias ()
	SELECT * FROM Categoria WHERE status='activo';

/*	
call getCategorias();
SELECT * FROM CATEGORIA;	
*/

DROP PROCEDURE IF EXISTS addCategoria;
CREATE PROCEDURE addCategoria(
nombre VARCHAR(200), 
descripcion VARCHAR(300))
INSERT INTO Categoria VALUES(null, nombre, descripcion,'activo');

/*	

call addCategoria('otros 2','Otra categoria estándar');	
call addCategoria('Escaleras','Escalera de distintos materiales');	

*/

call addCategoria('Tuberia'               , 'Tubos de pvc,cpvc,cobre,hidraulica');
call addCategoria('Conexiones para tuberia', 'Tee , codos, coples etc.');
call addCategoria('Jardineria'               , 'Tijeras para podar, palas jardineria' );
call addCategoria('Accesorios electricos'    , 'Accesorios electricos y conexiones de luz');
call addCategoria('Tornilleria'              , 'Tornillos, pijas, taquetes etc');
call addCategoria('Fontaneria General'       , 'Mezcladoras, tinacos, Accesorios para baño ');
call addCategoria('Iluminacion'              , 'Focos, lamparas, Portalamparas, etc');
call addCategoria('Ferreteria en general'  , 'Herramientas de ferreteria en general'   ) ;
call addCategoria('Electricidad'             , 'Material para electricidad en general');
call addCategoria('Albañileria'             , 'Material para albañileria en general');

DROP PROCEDURE IF EXISTS updateCategoria;
CREATE PROCEDURE updateCategoria(
id int,
nombre VARCHAR(200), 
descripcion VARCHAR(300))
UPDATE Categoria SET Categoria.nombre = nombre, Categoria.descripcion = descripcion
WHERE Categoria.id_categoria= id;

/*	

call updateCategoria(1,'Fontaneria','Herramientas de mano, equipos, etc');	

*/


/*
DROP PROCEDURE IF EXISTS deleteCategoria;
CREATE PROCEDURE deleteCategoria (ID int)
DELETE FROM Categoria WHERE id_categoria = ID;
*/       
DROP PROCEDURE IF EXISTS deleteCategoria;
CREATE PROCEDURE deleteCategoria(
id int)
UPDATE Categoria SET Categoria.status = 'inactivo'
WHERE Categoria.id_categoria= id;
/*	
call deleteCategoria(1);	
*/



DROP PROCEDURE IF EXISTS getBusquedaCategoria1;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaCategoria1(  
nombre VARCHAR(200), 
descripcion VARCHAR(300))
	SELECT * from Categoria AS a
	where a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.descripcion like (CONCAT('%',descripcion,'%'))
    AND  status='activo';
;

/*

call getBusquedaCategoria1('','a');
call getBusquedaCategoria1('o','a');
 
*/
 
DROP PROCEDURE IF EXISTS getBusquedaCategoria2;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaCategoria2( 
id int, 
nombre VARCHAR(200), 
descripcion VARCHAR(300))
	SELECT * from Categoria AS a
	where a.id_categoria like (CONCAT('%',id,'%'))
	AND  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.descripcion like (CONCAT('%',descripcion,'%'))
    AND a.status='activo';
;

/*

call getBusquedaCategoria2(1,'','');
call getBusquedaCategoria2(2,'','');
 
 */
 
 
 DROP PROCEDURE IF EXISTS getBusquedaCategoria3;
CREATE PROCEDURE getBusquedaCategoria3( 
id VARCHAR(200), 
nombre VARCHAR(200), 
descripcion VARCHAR(300))
	SELECT * from Categoria AS a
	where CONVERT(a.id_categoria,char) like (CONCAT('%',id,'%'))
	AND  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.descripcion like (CONCAT('%',descripcion,'%'))
    AND  status='activo';
;

/*

call getBusquedaCategoria3('','','');
call getBusquedaCategoria3(2,'','');
 
 */
 
 DROP PROCEDURE IF EXISTS getBusquedaCategoria4;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaCategoria4(
id_cat int,  
nombre VARCHAR(200), 
descripcion VARCHAR(300))
	SELECT * from Categoria AS a
	where a.id_categoria like(CONCAT('%',id_cat,'%'))
    AND a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.descripcion like (CONCAT('%',descripcion,'%'))
    AND  status='activo';
;


 
  /***********************************************************Procedimientos de MARCA*/
/*
id_marca int not null primary key auto_increment,
marca  varchar(200) 
*/

SELECT * FROM marca;
DROP PROCEDURE IF EXISTS getMarcas;
CREATE PROCEDURE getMarcas ()
	SELECT * FROM Marca WHERE status='activo';
/*	

call getMarcas();
SELECT * FROM MARCA;
*/

DROP PROCEDURE IF EXISTS addMarca;
CREATE PROCEDURE addMarca(
marca VARCHAR(200))
INSERT INTO Marca VALUES(null, marca,'activo');

/*	

call addMarca('Stanley');	
call addMarca('DeWALT');
call addMarca('Escalumex');
*/
call addMarca ('Truper');
call addMarca ('Dica');
call addMarca ('Urrea');
call addMarca ('Lion Tools');
call addMarca ('Pretul');
call addMarca ('Iusa');
call addMarca ('Cato');
call addMarca ('Fiero');
call addMarca ('Squarde');
call addMarca ('Bticino');
call addMarca ('Fleximatic');
call addMarca ('Escalumex');
call addMarca ('Flowguard Gold');
call addMarca ('Hecort');
call addMarca ('Nacobre');
call addMarca ('Volteck');
call addMarca ('Pintucom');
call addMarca ('Foset');
call addMarca ('PepeSilicon');
call addMarca ('Perfect');

DROP PROCEDURE IF EXISTS updateMarca;
CREATE PROCEDURE updateMarca(
id int, 
marca VARCHAR(200))
UPDATE Marca as m 
SET m.marca = marca
WHERE m.id_marca = id;

/*	

call updateMarca(7,'dewalt');
call updateMarca(7,'DeWALT');
	
*/


/*
DROP PROCEDURE IF EXISTS deleteMarca;
CREATE PROCEDURE deleteMarca (ID int)
DELETE FROM Marca WHERE id_marca = ID;         
*/
DROP PROCEDURE IF EXISTS deleteMarca;
CREATE PROCEDURE deleteMarca(
id int)
UPDATE Marca as m 
SET m.status = 'inactivo'
WHERE m.id_marca = id;
/*	
call deleteMarca(7);	
*/



DROP PROCEDURE IF EXISTS getBusquedaMarca1;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaMarca1(  
marca VARCHAR(200))
	SELECT * from Marca AS m
	where  m.marca like (CONCAT('%',marca,'%'))
    AND status='activo';
;

/*	

call getBusquedaMarca1('');	

*/


DROP PROCEDURE IF EXISTS getBusquedaMarca2;/*RECOMIENDO USAr la opcion 3*/
CREATE PROCEDURE getBusquedaMarca2( 
id int, 
marca VARCHAR(200))
	SELECT * from Marca AS m
	where 
	m.id_marca like (CONCAT('%',id,'%'))
	AND  m.marca like (CONCAT('%',marca,'%'))
    AND status='activo';
;

/*	

call getBusquedaMarca2(1,'');	

*/



DROP PROCEDURE IF EXISTS getBusquedaMarca3;
CREATE PROCEDURE getBusquedaMarca3( 
id VARCHAR(200), 
marca VARCHAR(200))
	SELECT * from Marca AS m
	where 
	CONVERT(m.id_marca,char) like (CONCAT('%',id,'%'))
	AND  m.marca like (CONCAT('%',marca,'%'))
    AND status='activo';
;

/*	

call getBusquedaMarca3('','');	
call getBusquedaMarca3('2','');	
call getBusquedaMarca3(1,'');	
*/


  /***********************************************************Procedimientos de PROVEEDOR*/
SELECT * FROM Proveedor;
/*
id_proveedor int not null primary key auto_increment,
nombre_proveedor varchar(40) not null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null

*/


DROP PROCEDURE IF EXISTS getProveedores;
CREATE PROCEDURE getProveedores()
	SELECT * FROM Proveedor WHERE status='activo';
    
/*	

call getProveedores();
SELECT * FROM PROVEEDOR;
*/


/*
id_proveedor int not null primary key auto_increment,
nombre_proveedor varchar(40) not null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null

*/
DROP PROCEDURE IF EXISTS addProveedor;
CREATE PROCEDURE addProveedor(
nombre_proveedor varchar(40),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
INSERT INTO proveedor (nombre_proveedor,telefono,correo,direccion,colonia,municipio,cp,estado,status) 
				VALUES(nombre_proveedor,telefono,correo,direccion,colonia,municipio,cp,estado,'activo');


/*	
call getProveedores();
call addProveedor('Proveedor 2 de prueba','4728563241','','','','','','');
call addProveedor('Proveedor 2 de prueba','4728563241','','','','','','');
*/

call addProveedor('RS Distribuciones SA DE CV', '4771518298', 'fidel982@rsdist.com', 'Blvd. Aldama #8575','Insurgentes','Salamanca','36214','Guanajuato');
call addProveedor('Selecto TEC SA DE CV', '4779277323', 'selectrotec@hotmail.com', ' Tepic #342','San Miguel','Leon','37084','Guanajuato');
call addProveedor('May Distribuciones SA DE CV','', 'contactomay@hotmail.com', 'Oro', 'Valle de señora','Leon','37203','Guanajuato');
call addProveedor('Marcasi SA DE CV' ,'', 'marcasi@outlookmail.com','Blvd. Hidalgo', 'Valle de señora','Leon','37203', 'Guanajuato');
call addProveedor('Vilches Ferreteros','', 'rockstarvilches@contacto','Blvd. Jose Maria Morelos # 10022','Industrial','Leon','37288','Guanajuato');

DROP PROCEDURE IF EXISTS updateProveedor;
CREATE PROCEDURE updateProveedor(
id_proveedor int,
nombre_proveedor varchar(40),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
UPDATE Proveedor as p 
	SET 
		p.nombre_proveedor=nombre_proveedor,
		p.telefono=telefono,
		p.correo=correo,
		p.direccion=direccion,
		p.colonia=colonia,
		p.municipio=municipio,
		p.cp=cp,
		p.estado=estado
WHERE p.id_proveedor = id_proveedor;


/*	
call getProveedores();
call updateProveedor(3,'Proveedor de prueba 3','4728563248','proveedor3@hotmail.com','','','León','','Guanajuato');
call updateProveedor(3,'Proveedor de prueba 3','4587654123','prov3@hotmail.com','','','León','','Guanajuato');
	
*/



/*
DROP PROCEDURE IF EXISTS deleteProveedor;
CREATE PROCEDURE deleteProveedor (id int)
DELETE FROM proveedor WHERE id_proveedor = id;       
*/
  DROP PROCEDURE IF EXISTS deleteProveedor;
CREATE PROCEDURE deleteProveedor(
id_proveedor int)
UPDATE Proveedor as p 
	SET 
		p.status='inactivo'
WHERE p.id_proveedor = id_proveedor;
  
/*	
call deleteProveedor(4);
*/


SELECT * FRom proveedor;


DROP PROCEDURE IF EXISTS getBusquedaProveedor1;
CREATE PROCEDURE getBusquedaProveedor1(  
nombre_proveedor varchar(100),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from proveedor AS p
	where  p.nombre_proveedor like (CONCAT('%',nombre_proveedor,'%'))
    AND p.telefono like (CONCAT('%',telefono,'%'))
    AND p.correo like (CONCAT('%',correo,'%'))
    AND p.direccion like (CONCAT('%',direccion,'%'))
    AND p.colonia like (CONCAT('%',colonia,'%'))
    AND p.municipio like (CONCAT('%',municipio,'%'))
    AND p.cp like (CONCAT('%',cp,'%'))
    AND p.estado like (CONCAT('%',estado,'%'))
    AND status = 'activo'
;

DROP PROCEDURE IF EXISTS getBusquedaProveedor2;
CREATE PROCEDURE getBusquedaProveedor2( 
id_proveedor varchar(100),
nombre_proveedor varchar(40),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Proveedor AS p
	where CONVERT(p.id_proveedor,CHAR)  like (CONCAT('%',id_proveedor,'%'))
    AND p.nombre_proveedor like (CONCAT('%',nombre_proveedor,'%'))
	AND p.telefono like (CONCAT('%',telefono,'%'))
	AND p.correo like (CONCAT('%',correo,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND p.direccion  like (CONCAT('%',direccion,'%'))
	AND p.colonia like (CONCAT('%',colonia,'%'))
    AND p.municipio like (CONCAT('%',municipio,'%'))
	AND p.cp like (CONCAT('%',cp,'%'))
    AND p.estado like (CONCAT('%',estado,'%'))
    AND status='activo';
;




/*	

call getBusquedaProveedor('','','','','','','','','');
call getBusquedaProveedor('1','','','','','','','','');
call getBusquedaProveedor('000','','','','','','','','');

*/







  
  
 
  /***********************************************************Procedimientos de PRODUCTO*/
  /*Otra forma de nombrar a los procedimientos puede ser SP_I_sdasd    SP_S_sdasd    SP_U_sdasd    SP_D_sdasd*/
SELECT * FROM Producto;
/*
codigo_producto varchar(13) not null primary key,
descripcion varchar(400) not null,
marca varchar(200) null,  //como no es llave foranea le puedo pasar tal cual la marca como String
costo float(15) null,
precio float(15) not null,
presentacion varchar(200) not null,
stock int not null,
stock_minimo int not null,
id_categoria int not null,
id_proveedor int not null

*/

DROP PROCEDURE IF EXISTS getProductos;
CREATE PROCEDURE getProductos ()
	SELECT * FROM Producto WHERE status='activo';
    
/*	

call getProductos();
SELECT * FROM PRODUCTO;
	
*/


DROP PROCEDURE IF EXISTS getProductosFaltantes;
CREATE PROCEDURE getProductosFaltantes (
marca varchar(200),
id_categoria varchar(200),
id_proveedor varchar(200)
)
	SELECT * FROM Producto WHERE (producto.stock<=producto.stock_minimo) 
	AND producto.marca like (CONCAT('%',marca,'%'))
    AND CONVERT(producto.id_categoria,CHAR) like (CONCAT('%',id_categoria,'%'))
	AND CONVERT(producto.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
    AND status='activo';
    
    
/*	

call getProductosFaltantes('7','4','1');
call getProductosFaltantes('','','');
SELECT * FROM PRODUCTO;
	
*/

DROP PROCEDURE IF EXISTS getProductosPorCodigo;
CREATE PROCEDURE getProductosPorCodigo (
codigo varchar(50)
)
	SELECT * FROM Producto 
    WHERE 
	producto.codigo_producto = codigo
    AND status='activo';
    
    
/*	

call getProductosPorCodigo('7');
call getProductosPorCodigo('');
call getProductosPorCodigo('7501000664221');
SELECT * FROM PRODUCTO;
	
*/




DROP PROCEDURE IF EXISTS getIdProductoCodigo;
CREATE PROCEDURE getIdProductoCodigo (
codigo_producto varchar(13)
)
SELECT id_producto FROM Producto as p WHERE p.codigo_producto=codigo_producto ;
    
/*	

call getIdProductoCodigo('000000000003');
call getIdProductoCodigo('7501000664221');
    
*/

DROP PROCEDURE IF EXISTS addProducto;
CREATE PROCEDURE addProducto(
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200) ,
costo float(15) ,
precio float(15),
presentacion varchar(200),
stock int,
stock_minimo int,
id_categoria int,
id_proveedor int)
INSERT INTO Producto (codigo_producto,descripcion,marca,costo,precio,presentacion,stock,stock_minimo,id_categoria,id_proveedor,status) 
				VALUES(codigo_producto,descripcion,marca,costo,precio,presentacion,stock,stock_minimo,id_categoria,id_proveedor,'activo');


/*	
call getMarcas();
call getCategorias();
call addProducto('000000000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',3,1,4,1);
	
*/

call addProducto('8844722171443','Tubo cpvc 1/2" 6mts', 'Flowguard Gold', 53, 68, 'pieza',50,10,1,4);
call addProducto('4220725327682','Codo cpvc 1/2"' , 'Flowguard Gold', 2.20, 4, 'pieza',200,25,2,4);
call addProducto('2087037288201','Tee  cpvc 1/2"' , 'Flowguard Gold', 2.8, 5, 'pieza',180,25,2,4);
call addProducto('1783173372220','Cople cpvc 1/2"', 'Flowguard Gold', 2.20, 4, 'pieza',120,25,2,4);
call addProducto('2128350340627','Pinza de corte 8"', 'Pretul', 49.50, 68,'pieza', 5,2,8,1);
call addProducto('6205614301155','Pinza de electricista 10"', 'Truper',163, 195,'pieza', 5,2,8,1);
call addProducto('8132124528162','Tinaco 1100 lts', 'Iusa' , 1589, 1800,'pieza',2 ,1,6,5);
call addProducto('8523045783116','Manguera Truper 1/2"', 'Truper',5.20,8,'metro', 100,10,3,1);
call addProducto('5310775522386','Brocha 2"','Perfect',12.5,16,'pieza', 15,4,8,3);
call addProducto('5630023013280','Pastilla Termomagnetica 20Amp', 'Squarde', 113.50,145,'pieza',8,2,9,2);
call addProducto('2234572884481','Tubo cobre 1/2" 6mts M', 'Nacobre', 348.6, 468, 'pieza',10,3,1,4);
call addProducto('2014347848762','Mezcladora 4048', 'Dica' ,234.5, 325,'pieza',5,2,6,4) ;


DROP PROCEDURE IF EXISTS updateProducto;
CREATE PROCEDURE updateProducto(
id int,
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200) ,
costo float(15) ,
precio float(15),
presentacion varchar(200),
stock int,
stock_minimo int,
id_categoria int,
id_proveedor int)
UPDATE Producto as p 
	SET 
		p.codigo_producto=codigo_producto,
		p.descripcion=descripcion,
		p.marca=marca,
		p.costo=costo,
		p.precio=precio,
		p.presentacion=presentacion,
		p.stock=stock,
		p.stock_minimo=stock_minimo,
		p.id_categoria=id_categoria,
		p.id_proveedor=id_proveedor
WHERE p.id_producto = id;


/*	
call getProductos();
call updateProducto(4,'000000000004','Escale de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',3,1,4,1);
call updateProducto(4,'000004000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',8,10,4,1);

	
*/



DROP PROCEDURE IF EXISTS reducirStockProducto;
CREATE PROCEDURE reducirStockProducto(
codigo_producto varchar(13),
cantidad int)
UPDATE Producto as p 
	SET p.stock=(p.stock-cantidad)
WHERE p.codigo_producto = codigo_producto;


/*	
call getProductos();

call getProductosFaltantes('','','');

call reducirStockProducto('7501000664221',15);
call reducirStockProducto('000004000004',7);

	
*/


DROP PROCEDURE IF EXISTS aumentarStockProducto;
CREATE PROCEDURE aumentarStockProducto(
codigo_producto varchar(13),
cantidad int)
UPDATE Producto as p 
	SET p.stock=(p.stock+cantidad)
WHERE p.codigo_producto = codigo_producto;


/*	
call getProductos();

call getProductosFaltantes('','','');

call aumentarStockProducto('7501000664221',15);
call aumentarStockProducto('000004000004',3);
call aumentarStockProducto('000000000005',12);

	
*/

/*
ESTO SERVIA CUANDO EL CODIGO ERA LLAVE PRIMARIA, PERO AHORA EL ID_PRODUCTO ES PK Y EL CODigo ES SoLo un ATRIBUTO
DROP PROCEDURE IF EXISTS updateProducto;
CREATE PROCEDURE updateProducto(
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200) ,
costo float(15) ,
precio float(15),
presentacion varchar(200),
stock int,
stock_minimo int,
id_categoria int,
id_proveedor int)
UPDATE Producto as p 
	SET 
		p.codigo_producto=codigo_producto,
		p.descripcion=descripcion,
		p.marca=marca,
		p.costo=costo,
		p.precio=precio,
		p.presentacion=presentacion,
		p.stock=stock,
		p.stock_minimo=stock_minimo,
		p.id_categoria=id_categoria,
		p.id_proveedor=id_proveedor
WHERE p.codigo_producto = codigo_producto;



call getProductos();
call updateProducto('000000000004','Escale de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',3,1,4,1);
call updateProducto('000004000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',3,1,4,1);

	
*/

/*
DROP PROCEDURE IF EXISTS deleteProducto;
CREATE PROCEDURE deleteProducto (id int)
DELETE FROM Producto WHERE id_producto = id;       
*/

DROP PROCEDURE IF EXISTS deleteProducto;
CREATE PROCEDURE deleteProducto(
id int)
UPDATE Producto as p 
	SET 
		p.status='inactivo'
WHERE p.id_producto = id;

/*	

call deleteProducto(5);
	
*/

/*ESTO SERVIA CUANDO EL CODIGO ERA LLAVE PRIMARIA, PERO AHORA EL ID_PRODUCTO ES PK Y EL CODigo ES SoLo un ATRIBUTO
DROP PROCEDURE IF EXISTS deleteProducto;    Antes de eliminar un producto debo de verificar que no se afecten las ventas porque tienen llaves foraneas del producto a eliminar
CREATE PROCEDURE deleteProducto (codigo varchar(13))
DELETE FROM Producto WHERE codigo_producto = codigo;       
  
  
	

call deleteProducto('000000000005');
	
*/

DROP PROCEDURE IF EXISTS getBusquedaProducto1;/*Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 2*/
CREATE PROCEDURE getBusquedaProducto1( 
codigo_producto varchar(13), 
descripcion varchar(400),
marca varchar(200) ,
costo varchar(200) ,
precio varchar(200),
presentacion varchar(200),
stock varchar(200),
stock_minimo varchar(200),
id_categoria varchar(200),
id_proveedor varchar(200))
	SELECT * from Producto AS p
	where  p.codigo_producto like (CONCAT('%',codigo_producto,'%'))
    AND p.descripcion like (CONCAT('%',descripcion,'%'))
	AND p.marca like (CONCAT('%',marca,'%'))
	AND CONVERT(p.costo,CHAR) like (CONCAT('%',costo,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND CONVERT( p.precio,CHAR) like (CONCAT('%',precio,'%'))
	AND p.presentacion like (CONCAT('%',presentacion,'%'))
    AND CONVERT(p.stock,CHAR) like (CONCAT('%',stock,'%'))
	AND CONVERT(p.stock_minimo,CHAR)  like (CONCAT('%',stock_minimo,'%'))
    AND CONVERT(p.id_categoria,CHAR) like (CONCAT('%',id_categoria,'%'))
	AND CONVERT(p.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
    AND status='activo';
;

/*	

call getBusquedaProducto1('','','','','','','','','','');
call getBusquedaProducto1('15','','','','','','','','','');
call getBusquedaProducto1('000','','','','','','','','','');

*/



DROP PROCEDURE IF EXISTS getBusquedaProducto2;/*Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 1*/
CREATE PROCEDURE getBusquedaProducto2( 
id_producto varchar(200),
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200) ,
costo varchar(200),
precio varchar(200) ,
presentacion varchar(200),
stock varchar(200),
stock_minimo varchar(200),
id_categoria varchar(200),
id_proveedor varchar(200))
	SELECT * from Producto AS p
	where CONVERT(p.id_producto,CHAR) like (CONCAT('%',id_producto,'%'))
    AND p.codigo_producto like (CONCAT('%',codigo_producto,'%'))
	AND p.descripcion like (CONCAT('%',descripcion,'%'))
	AND p.marca like (CONCAT('%',marca,'%'))
	AND CONVERT(p.costo,CHAR) like (CONCAT('%',costo,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND CONVERT( p.precio,CHAR) like (CONCAT('%',precio,'%'))
	AND p.presentacion like (CONCAT('%',presentacion,'%'))
    AND CONVERT(p.stock,CHAR) like (CONCAT('%',stock,'%'))
	AND CONVERT(p.stock_minimo,CHAR)  like (CONCAT('%',stock_minimo,'%'))
    AND CONVERT(p.id_categoria,CHAR) like (CONCAT('%',id_categoria,'%'))
	AND CONVERT(p.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
    AND status='activo';
;

/*	
call getBusquedaProducto2('','','','','','','','','','','');
call getBusquedaProducto2('1','','','','','','','','','','');
call getBusquedaProducto2('','00000','','','','','','','','','');


	
*/





/*
ESTO SERVIA CUANDO EL CODIGO ERA LLAVE PRIMARIA, PERO AHORA EL ID_PRODUCTO ES PK Y EL CODigo ES SoLo un ATRIBUTO


DROP PROCEDURE IF EXISTS getBusquedaProducto1;Recibe puros string    ESTA BIEN ESTA OPCION Y LA 2
CREATE PROCEDURE getBusquedaProducto1(  
descripcion varchar(400),
marca varchar(200) ,
costo varchar(200) ,
precio varchar(200),
presentacion varchar(200),
stock varchar(200),
stock_minimo varchar(200),
id_categoria varchar(200),
id_proveedor varchar(200))
	SELECT * from Producto AS p
	where  p.descripcion like (CONCAT('%',descripcion,'%'))
	AND p.marca like (CONCAT('%',marca,'%'))
	AND CONVERT(p.costo,CHAR) like (CONCAT('%',costo,'%'))Uso convert para comparar el valor char recibido con un int o double
    AND CONVERT( p.precio,CHAR) like (CONCAT('%',precio,'%'))
	AND p.presentacion like (CONCAT('%',presentacion,'%'))
    AND CONVERT(p.stock,CHAR) like (CONCAT('%',stock,'%'))
	AND CONVERT(p.stock_minimo,CHAR)  like (CONCAT('%',stock_minimo,'%'))
    AND CONVERT(p.id_categoria,CHAR) like (CONCAT('%',id_categoria,'%'))
	AND CONVERT(p.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
;

	

call getBusquedaProducto1('','3','3','','','','','','');
	




DROP PROCEDURE IF EXISTS getBusquedaProducto2;   Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 1
CREATE PROCEDURE getBusquedaProducto2( 
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200) ,
costo varchar(200),
precio varchar(200) ,
presentacion varchar(200),
stock varchar(200),
stock_minimo varchar(200),
id_categoria varchar(200),
id_proveedor varchar(200))
	SELECT * from Producto AS p
	where p.codigo_producto like (CONCAT('%',codigo_producto,'%'))
	AND p.descripcion like (CONCAT('%',descripcion,'%'))
	AND p.marca like (CONCAT('%',marca,'%'))
	AND CONVERT(p.costo,CHAR) like (CONCAT('%',costo,'%'))   Uso convert para comparar el valor char recibido con un int o double
    AND CONVERT( p.precio,CHAR) like (CONCAT('%',precio,'%'))
	AND p.presentacion like (CONCAT('%',presentacion,'%'))
    AND CONVERT(p.stock,CHAR) like (CONCAT('%',stock,'%'))
	AND CONVERT(p.stock_minimo,CHAR)  like (CONCAT('%',stock_minimo,'%'))
    AND CONVERT(p.id_categoria,CHAR) like (CONCAT('%',id_categoria,'%'))
	AND CONVERT(p.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
;


call getBusquedaProducto2('','','','','','','','','','');
call getBusquedaProducto2('00000','','','','','','','','','');
	
*/


DROP PROCEDURE IF EXISTS getBusquedaProducto4;/*Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 1*/
CREATE PROCEDURE getBusquedaProducto4( 
codigo_producto varchar(13),
descripcion varchar(400),
marca varchar(200))
	SELECT * from Producto AS p
	where p.codigo_producto like (CONCAT('%',codigo_producto,'%'))
	AND p.descripcion like (CONCAT('%',descripcion,'%'))
	AND p.marca like (CONCAT('%',marca,'%'))
	AND status='activo';
;

/*	
call getBusquedaProducto4('','','');
call getBusquedaProducto4('1','','');
call getBusquedaProducto4('','lla','');


	
*/

/***********************************************************Procedimientos de VENTAS Y DETALLE VENTAS*/

/*-------------------------------------------------------------------------DE VENTAs*/
DROP PROCEDURE IF EXISTS getVentas;
CREATE PROCEDURE getVentas ()
	SELECT * FROM ventas WHERE status='activo';
    
/*	

call getVentas();
SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS getIdMayorDeUltimaVenta;
CREATE PROCEDURE getIdMayorDeUltimaVenta ()
	SELECT MAX(id_venta) idUltimaVenta FROM ventas WHERE status='activo';
    
/*	

call getIdMayorDeUltimaVenta();
SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS getAñosVenta;
CREATE PROCEDURE getAñosVenta ()
	SELECT  year(v.fecha_venta) años_ventas FROM ventas v WHERE status='activo'
    group by años_ventas;
    
/*	

call getAñosVenta();
SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS getMesesVenta;
CREATE PROCEDURE getMesesVenta ()
	SELECT  month(v.fecha_venta) meses_ventas FROM ventas v WHERE status='activo'
    group by meses_ventas;
    
/*	

call getMesesVenta();
SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS getDiasVenta;
CREATE PROCEDURE getDiasVenta ()
	SELECT  day(v.fecha_venta) dias_ventas FROM ventas v WHERE status='activo'
    group by dias_ventas;
    
/*	

call getDiasVenta();
SELECT * FROM ventas;
	
*/



DROP PROCEDURE IF EXISTS addVenta;
CREATE PROCEDURE addVenta(
fecha_venta date,
subtotal_venta float (15)  ,
iva_venta float(5)  ,
total_venta float(15)  ,
forma_pago varchar(20),
id_usuario int,
id_cliente int)
INSERT INTO ventas (fecha_venta,subtotal_venta,iva_venta,total_venta,forma_pago,id_usuario,id_cliente,status) 
				VALUES(fecha_venta,subtotal_venta,iva_venta,total_venta,forma_pago,id_usuario,id_cliente,'activo');

/*	
call getVentas();
call addVenta('2017-05-24',25.0,2.5,35.16,'EFECTIVO',1,1);
call addVenta('2018-11-30',31.0,1.5,37.03,'EFECTIVO',2,1);
call addVenta('2018-12-01',15.0,3.5,21.50,'EFECTIVO',2,1);
*/


DROP PROCEDURE IF EXISTS getBusquedaVenta;
CREATE PROCEDURE getBusquedaVenta(  
fecha_ventaInicio varchar(100) ,
fecha_ventaFin varchar(100) ,
id_usuario varchar(100),
id_cliente varchar(100)
)
	SELECT * from ventas AS v
	where  (v.fecha_venta between fecha_ventaInicio AND fecha_ventaFin)
    AND CONVERT(v.id_usuario,CHAR) like (CONCAT('%',id_usuario,'%'))
    AND CONVERT(v.id_cliente,CHAR) like (CONCAT('%',id_cliente,'%'))
    AND v.status = 'activo'
;

/*	

call getBusquedaVenta('2016-05-10','2018-12-12','2','1');
call getBusquedaVenta('2016-05-10','2018-12-12','','');
call getBusquedaVenta('2018-10-31','2018-12-22','','');
call getBusquedaVenta('2018-11-30','2018-12-22','','');
call getBusquedaVenta('2016-05-10','2018-12-12','2','');
call getBusquedaVenta('2016-05-10','2018-12-12','1','');

SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS getBusquedaVentaMesAño;
CREATE PROCEDURE getBusquedaVentaMesAño(
año varchar(100),
mes varchar(100),  
dia varchar(100),
id_usuario varchar(100)
)
	SELECT v.id_venta,v.fecha_venta,v.subtotal_venta,v.iva_venta,v.total_venta,v.forma_pago,v.id_usuario,v.id_cliente,c.nombre 
    from ventas AS v JOIN cliente c on v.id_cliente=c.id_cliente
	where  CONVERT(v.id_usuario,CHAR) like (CONCAT('%',id_usuario,'%'))
    AND CONVERT((year(v.fecha_venta)),CHAR)  like (CONCAT('%',año,'%'))  
    and CONVERT((month(v.fecha_venta)),CHAR)  like (CONCAT('%',mes,'%')) 
    and CONVERT((day(v.fecha_venta)),CHAR)  like (CONCAT('%',dia,'%'))
    AND v.status = 'activo'
;

/*	

call getBusquedaVentaMesAño('2018','11','','');
call getBusquedaVentaMesAño('2018','11','30','');
call getBusquedaVentaMesAño('2018','12','30','');
call getBusquedaVentaMesAño('2018','11','30','2');
call getBusquedaVentaMesAño('2018','12','','');
call getBusquedaVentaMesAño('2018','','','');
call getBusquedaVentaMesAño('','','','');

SELECT * FROM ventas;
	
*/

DROP PROCEDURE IF EXISTS deleteVentaYSusDetalles;
CREATE PROCEDURE deleteVentaYSusDetalles(
id_venta int)
UPDATE ventas as v JOIN detalle_venta dv ON v.id_venta=dv.id_venta 
	SET v.status='inactivo', dv.status='inactivo'
WHERE v.id_venta = id_venta;
/*	
call getVentasYSusDetalles();
SELECT * FROM ventas v JOIN detalle_venta dv ON v.id_venta=dv.id_venta;

call deleteVentaYSusDetalles(1);

*/



/*-------------------------------------------------------------------DE DETALLE_VENTAS*/
DROP PROCEDURE IF EXISTS getDetallesVenta;
CREATE PROCEDURE getDetallesVenta ()
	SELECT * FROM detalle_venta WHERE status='activo';
    
/*	

call getDetallesVenta();
SELECT * FROM detalle_venta;
	
*/



DROP PROCEDURE IF EXISTS addDetalleVenta;
CREATE PROCEDURE addDetalleVenta(
id_venta int,
id_producto int,
cantidad int,
importe float(15))
INSERT INTO detalle_venta (id_venta,id_producto,cantidad,importe,status) 
				VALUES(id_venta,id_producto,cantidad,importe,'activo');

/*	
call getProductos();
call getVentas();
call getDetallesVenta();

SELECT * FROM ventas v JOIN detalle_venta dv ON v.id_venta=dv.id_venta;

*/

DROP PROCEDURE IF EXISTS getDetallesDeUnaVenta;
CREATE PROCEDURE getDetallesDeUnaVenta (
id_venta int
)
	SELECT * FROM detalle_venta dv 
    WHERE dv.id_venta=id_venta AND dv.status='activo';

/*	

call getDetallesDeUnaVenta(1);
call getDetallesDeUnaVenta(2);
call getDetallesDeUnaVenta(3);
*/


DROP PROCEDURE IF EXISTS getDetallesDeProductoDeUnaVenta;
CREATE PROCEDURE getDetallesDeProductoDeUnaVenta (
id_venta int
)
	SELECT dv.id_det_ventas,p.codigo_producto,p.descripcion, p.precio, dv.cantidad,dv.importe 
    FROM detalle_venta dv JOIN producto p 
    ON dv.id_producto=p.id_producto
    WHERE dv.id_venta=id_venta AND dv.status='activo';

/*	
call getVentas();
call getDetallesVenta();

call getDetallesDeProductoDeUnaVenta(1);
call getDetallesDeProductoDeUnaVenta(2);
call getDetallesDeProductoDeUnaVenta(3);
call getDetallesDeProductoDeUnaVenta(4);
call getDetallesDeProductoDeUnaVenta(5);
call getDetallesDeProductoDeUnaVenta(6);
call getDetallesDeProductoDeUnaVenta(7);
call getDetallesDeProductoDeUnaVenta(8);

*/


/*-------------------------------------------------------------------DE VENTA Y SUS DETALLES_VENTA*/
DROP PROCEDURE IF EXISTS getVentasYSusDetalles;
CREATE PROCEDURE getVentasYSusDetalles (
)
	SELECT v.id_venta,v.fecha_venta,v.subtotal_venta,v.iva_venta,v.total_venta,v.forma_pago,v.id_usuario,v.id_cliente,
    dv.id_det_ventas,dv.id_producto,dv.cantidad,  dv.importe
    FROM ventas v JOIN detalle_venta dv ON v.id_venta=dv.id_venta 
    WHERE v.status='activo' AND dv.status='activo';
    
/*	

call getVentasYSusDetalles();
SELECT * FROM detalle_venta;
	
*/

DROP PROCEDURE IF EXISTS getBusquedaVentaYSusDetallesDeVenta;
CREATE PROCEDURE getBusquedaVentaYSusDetallesDeVenta(  
fecha_ventaInicio date ,
fecha_ventaFin date ,
id_usuario varchar(100),
id_cliente varchar(100)
)
    SELECT v.id_venta,v.fecha_venta,v.subtotal_venta,v.iva_venta,v.total_venta,v.forma_pago,v.id_usuario,v.id_cliente,
    dv.id_det_ventas,dv.id_producto,dv.cantidad,  dv.importe
    FROM ventas v JOIN detalle_venta dv ON v.id_venta=dv.id_venta 
    WHERE (v.fecha_venta between fecha_ventaInicio AND fecha_ventaFin)
    AND CONVERT(v.id_usuario,CHAR) like (CONCAT('%',id_usuario,'%'))
    AND CONVERT(v.id_cliente,CHAR) like (CONCAT('%',id_cliente,'%'))
    AND v.status='activo' AND dv.status='activo';
;

/*	

call getBusquedaVentaYSusDetallesDeVenta('2016-05-10','2018-12-12','2','1');
call getBusquedaVentaYSusDetallesDeVenta('2016-05-10','2018-12-12','','');
call getBusquedaVentaYSusDetallesDeVenta('2018-10-31','2018-12-22','','');
call getBusquedaVentaYSusDetallesDeVenta('2018-11-30','2018-12-22','','');
call getBusquedaVentaYSusDetallesDeVenta('2016-05-10','2018-12-12','2','');
call getBusquedaVentaYSusDetallesDeVenta('2016-05-10','2018-12-12','1','');

SELECT * FROM ventas v JOIN detalle_venta dv ON v.id_venta=dv.id_venta;
*/



call addVenta('2017/06/01',18112,2897.92,21009.92,'EFECTIVO',4,3);
call addVenta('2018/03/21',2945,471.2,3416.2,'EFECTIVO',5,3);
call addVenta('2018/09/26',56,8.96,64.96,'EFECTIVO',5,4);
call addVenta('2017/08/01',10800,1728,12528,'EFECTIVO',4,5);
call addVenta('2018/02/11',68,10.88,78.88,'EFECTIVO',4,5);
call addVenta('2018/12/08',696,111.36,807.36,'EFECTIVO',4,1);
call addVenta('2018/03/12',640,102.4,742.4,'EFECTIVO',5,2);
call addVenta('2018/03/18',3421,547.36,3968.36,'EFECTIVO',4,2);
call addVenta('2018/01/07',3390,542.4,3932.4,'EFECTIVO',5,1);
call addVenta('2018/11/16',145,23.2,168.2,'EFECTIVO',4,1);
call addVenta('2017/07/13',18000,2880,20880,'EFECTIVO',5,4);
call addVenta('2017/05/25',975,156,1131,'EFECTIVO',4,1);
call addVenta('2017/01/29',80,12.8,92.8,'EFECTIVO',4,5);
call addVenta('2018/01/17',408,65.28,473.28,'EFECTIVO',4,3);
call addVenta('2017/08/28',14492,2318.72,16810.72,'EFECTIVO',5,5);
call addVenta('2017/03/26',20,3.2,23.2,'EFECTIVO',5,1);
call addVenta('2018/05/11',96,15.36,111.36,'EFECTIVO',5,3);
call addVenta('2018/01/15',2600,416,3016,'EFECTIVO',4,5);
call addVenta('2017/03/06',48,7.68,55.68,'EFECTIVO',4,3);
call addVenta('2017/09/13',1715,274.4,1989.4,'EFECTIVO',5,5);
call addVenta('2017/12/20',530,84.8,614.8,'EFECTIVO',4,2);
call addVenta('2018/12/19',114,18.24,132.24,'EFECTIVO',5,4);
call addVenta('2018/01/05',4,0.64,4.64,'EFECTIVO',4,1);
call addVenta('2017/05/13',8,1.28,9.28,'EFECTIVO',5,2);
call addVenta('2018/02/19',1170,187.2,1357.2,'EFECTIVO',4,3);
call addVenta('2018/11/12',40,6.4,46.4,'EFECTIVO',5,2);






call addDetalleVenta(1,9,7,112);
call addDetalleVenta(1,7,10,18000);
call addDetalleVenta(2,6,1,195);
call addDetalleVenta(2,10,6,870);
call addDetalleVenta(2,7,1,1800);
call addDetalleVenta(2,8,10,80);
call addDetalleVenta(3,8,7,56);
call addDetalleVenta(4,7,6,10800);
call addDetalleVenta(5,1,1,68);
call addDetalleVenta(6,9,1,16);
call addDetalleVenta(6,5,10,680);
call addDetalleVenta(7,11,1,468);
call addDetalleVenta(7,4,9,36);
call addDetalleVenta(7,1,2,136);
call addDetalleVenta(8,9,6,96);
call addDetalleVenta(8,12,8,2600);
call addDetalleVenta(8,10,5,725);
call addDetalleVenta(9,12,2,650);
call addDetalleVenta(9,5,2,136);
call addDetalleVenta(9,12,8,2600);
call addDetalleVenta(9,4,1,4);
call addDetalleVenta(10,10,1,145);
call addDetalleVenta(11,7,10,18000);
call addDetalleVenta(12,6,5,975);
call addDetalleVenta(13,8,10,80);
call addDetalleVenta(14,1,6,408);
call addDetalleVenta(15,7,7,12600);
call addDetalleVenta(15,2,5,20);
call addDetalleVenta(15,11,4,1872);
call addDetalleVenta(16,8,2,16);
call addDetalleVenta(16,2,5,20);
call addDetalleVenta(17,9,6,96);
call addDetalleVenta(18,12,8,2600);
call addDetalleVenta(19,8,6,48);
call addDetalleVenta(20,12,2,650);
call addDetalleVenta(20,1,5,340);
call addDetalleVenta(20,10,5,725);
call addDetalleVenta(21,10,2,290);
call addDetalleVenta(21,9,5,80);
call addDetalleVenta(21,9,10,160);
call addDetalleVenta(22,3,9,45);
call addDetalleVenta(22,2,6,24);
call addDetalleVenta(22,3,9,45);
call addDetalleVenta(23,4,1,4);
call addDetalleVenta(24,2,2,8);
call addDetalleVenta(25,6,6,1170);
call addDetalleVenta(26,3,8,40);


/***********************************************************Procedimientos de Sucursal*/
SELECT * FROM Sucursal;
DROP PROCEDURE IF EXISTS getSucursales;
CREATE PROCEDURE getSucursales()
	SELECT * FROM Sucursal WHERE status='activo';
    


DROP PROCEDURE IF EXISTS addSucursal;
CREATE PROCEDURE addSucursal(
nombre varchar(100),
sucursal varchar(50),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
INSERT INTO Sucursal (nombre,sucursal,telefono,correo,direccion,colonia,municipio,cp,estado,status) 
				VALUES(nombre,sucursal,telefono,correo,direccion,colonia,municipio,cp,estado,'activo');
                
call addSucursal('FERRETERIA ARAUJO','Sucursal 1','4772129324','ferreteriaaraujo@hotmail.com','BLVD. ANTONIO MADRAZO N° 6703','LOS MURALES','León','37219','Guanajuato');
                
DROP PROCEDURE IF EXISTS updateSucursal;
CREATE PROCEDURE updateSucursal(
id_sucursal int,
nombre varchar(100),
sucursal varchar(50),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
UPDATE Sucursal as s
	SET 
		s.nombre=nombre,
        s.sucursal=sucursal,
		s.telefono=telefono,
		s.correo=correo,
		s.direccion=direccion,
		s.colonia=colonia,
		s.municipio=municipio,
		s.cp=cp,
		s.estado=estado
WHERE s.id_sucursal = id_sucursal;


DROP PROCEDURE IF EXISTS deleteSucursal;
CREATE PROCEDURE deleteSucursal(
id_sucursal int)
UPDATE Sucursal as s 
	SET 
		s.status='inactivo'
WHERE s.id_sucursal = id_sucursal;
  
DROP PROCEDURE IF EXISTS getBusquedaSucursal1;
CREATE PROCEDURE getBusquedaSucursal1(  
nombre varchar(100),
sucursal varchar(50),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Sucursal AS s
	where  s.nombre like (CONCAT('%',nombre,'%'))
    AND s.sucursal like (CONCAT('%',sucursal,'%'))
    AND s.telefono like (CONCAT('%',telefono,'%'))
    AND s.correo like (CONCAT('%',correo,'%'))
    AND s.direccion like (CONCAT('%',direccion,'%'))
    AND s.colonia like (CONCAT('%',colonia,'%'))
    AND s.municipio like (CONCAT('%',municipio,'%'))
    AND s.cp like (CONCAT('%',cp,'%'))
    AND s.estado like (CONCAT('%',estado,'%'))
    AND status = 'activo'
;
DROP PROCEDURE IF EXISTS getBusquedaSucursal2;
CREATE PROCEDURE getBusquedaSucursal2( 
id_sucursal varchar(100),
nombre varchar(100),
sucursal varchar(50),
telefono varchar(10),
correo varchar(100),
direccion varchar(100),
colonia varchar(100),
municipio varchar(200),
cp varchar(5),
estado varchar(200))
	SELECT * from Sucursal AS s
	where CONVERT(s.id_sucursal,CHAR)  like (CONCAT('%',id_sucursal,'%'))
    AND s.nombre like (CONCAT('%',nombre,'%'))
	AND s.telefono like (CONCAT('%',telefono,'%'))
	AND s.correo like (CONCAT('%',correo,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND s.direccion  like (CONCAT('%',direccion,'%'))
	AND s.colonia like (CONCAT('%',colonia,'%'))
    AND s.municipio like (CONCAT('%',municipio,'%'))
	AND s.cp like (CONCAT('%',cp,'%'))
    AND s.estado like (CONCAT('%',estado,'%'))
    AND status='activo';





-- ------------------Procedimientos Factura Pedido ---------------------------------
DROP PROCEDURE IF EXISTS getPedidos;
CREATE PROCEDURE getPedidos ()
	SELECT * FROM Factura_Pedido WHERE status='activo';
    
DROP PROCEDURE IF EXISTS addPedido;
CREATE PROCEDURE addPedido(
folio varchar(17),
id_proveedor int,
id_usuario int,
montoFactura float(15),
fecha date)
INSERT INTO Factura_Pedido (folio_factura,id_proveedor,id_usuario,montoFactura,fecha,status) 
				VALUES(folio,id_proveedor,id_usuario,montoFactura,fecha,'activo');

DROP PROCEDURE IF EXISTS updatePedido;
CREATE PROCEDURE updatePedido(
folio_factura varchar(200),
id_proveedor int,
id_usuario int,
montoFactura float(15),
fecha date)
UPDATE Factura_Pedido as f
	SET 
		f.folio_factura =folio_factura,
		f.id_proveedor=id_proveedor,
		f.id_usuario=id_usuario,
		f.montoFactura=montoFactura,
		f.fecha=fecha
WHERE f.folio_factura = folio_factura;

DROP PROCEDURE IF EXISTS deletePedido;
CREATE PROCEDURE deletePedido(
folio_factura varchar(200))
UPDATE Factura_Pedido as f 
	SET 
		f.status='inactivo'
WHERE f.folio_factura = folio_factura;

DROP PROCEDURE IF EXISTS getBusquedaPedido1;/*Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 2*/
CREATE PROCEDURE getBusquedaPedido1( 
id_proveedor varchar(200),
id_usuario varchar(200),
montoFactura varchar(200),
fecha varchar(200))
	SELECT * from Factura_Pedido AS f
	where  CONVERT(f.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
    AND CONVERT(f.id_usuario,CHAR) like (CONCAT('%',id_usuario,'%'))
	AND CONVERT(f.montoFactura,CHAR) like (CONCAT('%',montoFactura,'%'))
	AND CONVERT(f.fecha,CHAR) like (CONCAT('%',fecha,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND status='activo';
;


DROP PROCEDURE IF EXISTS getBusquedaPedido2;/*Recibe puros string*//*ESTA BIEN ESTA OPCION Y LA 2*/
CREATE PROCEDURE getBusquedaPedido2( 
folio_factura varchar(200),
id_proveedor varchar(200),
id_usuario varchar(200),
montoFactura varchar(200),
fecha varchar(200))
	SELECT * from Factura_Pedido AS f
    where CONVERT(f.folio_factura,CHAR) like (CONCAT('%',folio_factura,'%'))
	AND  CONVERT(f.id_proveedor,CHAR) like (CONCAT('%',id_proveedor,'%'))
    AND CONVERT(f.id_usuario,CHAR) like (CONCAT('%',id_usuario,'%'))
	AND CONVERT(f.montoFactura,CHAR) like (CONCAT('%',montoFactura,'%'))
	AND CONVERT(f.fecha,CHAR) like (CONCAT('%',fecha,'%'))/*Uso convert para comparar el valor char recibido con un int o double*/
    AND status='activo';
;


DROP PROCEDURE IF EXISTS geReportePedidos;
CREATE PROCEDURE geReportePedidos(  
fecha_Inicio varchar(100) ,
fecha_Fin varchar(100) 
)
	SELECT * from factura_pedido AS f
	where  CONVERT(f.fecha,CHAR) between fecha_Inicio AND fecha_Fin
    AND f.status = 'activo'
;

/*	

call geReportePedidos('2018-01-12','2018-31-12');

call geReportePedidos('2018/11/14','2018/11/15');
SELECT * FROM ventas;
	
*/

call addPedido('0145214785414',1,4,1000.0,'2018/11/12');

