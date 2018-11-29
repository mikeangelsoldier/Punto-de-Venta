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
folio_factura int not null primary key auto_increment,
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
INSERT INTO USUARIO VALUES (null,'Vendedores generales','vendedor','v123', 'Empleado','activo');



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

call addCliente('paul','2728282','4771223363','hdjdjd','sjsjs','jjdddjd','sjsjs','27272','gto');
 
 
 
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
INSERT INTO Categoria VALUES (null,'Fontaneria','','activo');
INSERT INTO Categoria VALUES (null,'Electricidad','','activo');
INSERT INTO Categoria VALUES (null,'Otros','Categoria estándar','activo');
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
call addCategoria('Escaleras','Escalera de distintos materiales');	

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
INSERT INTO marca VALUES (null,'Trupper','activo');
INSERT INTO marca VALUES (null,'Urea','activo');
INSERT INTO marca VALUES (null,'Dica','activo');
INSERT INTO marca VALUES (null,'Genérico','activo');

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
call addMarca('Stanley');	
call addMarca('DeWALT');
call addMarca('Escalumex');


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
INSERT INTO Proveedor (id_proveedor,nombre_proveedor,telefono,status) 
		VALUES (null,'Proveedor de prueba','4774265833','activo'); 



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

call addProveedor('Proveedor 2 de prueba','4728563241','','','','','','');
call addProveedor('Proveedor 2 de prueba','4728563241','','','','','','');


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

call updateProveedor(1,'Proveedor de prueba','4774265833','','','','','','');


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

INSERT INTO Producto (id_producto,codigo_producto,descripcion,marca,costo,precio,presentacion,stock,stock_minimo,id_categoria,id_proveedor,status) 
				VALUES (null,'7501000664221','Llave Nariz 1/2','Dica',33,56,'pieza',25,8,1,1,'activo'),
                (null,'000000015425','Mezcladora para Lavavo 4048','Dica',268,342,'pieza',4,1,1,1,'activo'),
				(null,'000000000003','Llave Nariz 1/2','Trupper',112,183,'pieza',10,3,1,1,'activo'); 
/*
UPDATE Producto SET producto.codigo_producto = '7501000664221'
WHERE producto.codigo_producto = '1111111111111';
*/





DROP PROCEDURE IF EXISTS getProductos;
CREATE PROCEDURE getProductos ()
	SELECT * FROM Producto WHERE status='activo';
    
/*	

call getProductos();
SELECT * FROM PRODUCTO;
	
*/


DROP PROCEDURE IF EXISTS getProductosFaltantes;
CREATE PROCEDURE getProductosFaltantes ()
	SELECT * FROM Producto WHERE (producto.stock<=producto.stock_minimo) AND status='activo';
    
/*	

call getProductosFaltantes();
SELECT * FROM PRODUCTO;
	
*/


DROP PROCEDURE IF EXISTS getIdProductoCodigo;
CREATE PROCEDURE getIdProductoCodigo (
codigo_producto varchar(13)
)
SELECT id_producto FROM Producto as p WHERE p.codigo_producto=codigo_producto ;
    
/*	

call getIdProductoCodigo('000000000003');
	
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
call addProducto('000000000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4','Escalumex',607,800,'pieza',3,1,4,1);
call addProducto('000000000005','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4','Escalumex',607,800,'pieza',3,1,4,1);



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
call updateProducto(4,'000004000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',4,4,4,1);

	
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

call getProductosFaltantes();

call reducirStockProducto('7501000664221',15);
call reducirStockProducto('000004000004',7);

	
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
id_proveedor int,
id_usuario int,
montoFactura float(15),
fecha date)
INSERT INTO Factura_Pedido (id_proveedor,id_usuario,montoFactura,fecha,status) 
				VALUES(id_proveedor,id_usuario,montoFactura,fecha,'activo');

DROP PROCEDURE IF EXISTS updatePedido;
CREATE PROCEDURE updatePedido(
folio_factura int,
id_proveedor int,
id_usuario int,
montoFactura float(15),
fecha date)
UPDATE Factura_Pedido as f
	SET 
		f.id_proveedor=id_proveedor,
		f.id_usuario=id_usuario,
		f.montoFactura=montoFactura,
		f.fecha=fecha
WHERE f.folio_factura = folio_factura;

DROP PROCEDURE IF EXISTS deletePedido;
CREATE PROCEDURE deletePedido(
folio_factura int)
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