/*
Instituto Tecnológico de León
Materia: Gestion de Proyectos de Software
Fecha: 2 de Octubre del 2018
Carrera: Ingeniería en Sistemas Computacionales
Semestre 7
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
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
status varchar(200) null
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
codigo_producto varchar(13) not null ,
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



DROP PROCEDURE IF EXISTS getBusquedaProveedor;
CREATE PROCEDURE getBusquedaProveedor( 
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
call updateProducto(4,'000004000004','Escalera de Tijera C/Plataforma Escalumex 3 Exc Sta-4',7,607,800,'pieza',3,1,4,1);

	
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



