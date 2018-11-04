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
estado varchar(200) null
);

create table Proveedor(
id_proveedor int not null primary key auto_increment,
nombre_proveedor varchar(40) not null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null
);

create table Cliente(
id_cliente int not null primary key auto_increment,
nombre varchar(100) not null,
apellido_paterno varchar(100) null,
apellido_materno varchar(100) null,
rfc varchar(15) null,
fecha_nacimiento date null,
telefono varchar(10) null,
correo varchar(100) null,
direccion varchar(100)  null,
colonia varchar(100) null,
municipio varchar(200)  null,
cp varchar(5) null,
estado varchar(200) null
);


create table Usuario(
id_usuario int not null primary key auto_increment,
nombre varchar(100) not null,
login varchar (100) not null,
contraseña varchar(100)not null,
rol varchar(100)not null
) ;


create table Categoria(
id_categoria int not null primary key auto_increment,
nombre varchar(200) not null,
descripcion varchar(300) null
);

create table Producto(
codigo_producto varchar(13) not null primary key,
descripcion varchar(400) not null,
marca varchar(200) null,
costo float(15) null,
precio float(15) not null,
presentacion varchar(200) not null,
stock int not null,
stock_minimo int not null,
id_categoria int not null,
id_proveedor int not null
);

create table Ventas(
id_venta int not null primary key auto_increment,
fecha_venta date not null,
subtotal_venta float (15) not null,
iva_venta float(5) not null,
total_venta float(15) not Null,
forma_pago varchar(20),
id_usuario int not null,
id_cliente int not null
);

create table Detalle_Venta(
id_det_ventas int not null primary key auto_increment,
id_venta int not null,
codigo_producto varchar(13) not null,
cantidad int not null,
importe float(15) null
);

create table Factura_Pedido(
folio_factura int not null primary key auto_increment,
id_proveedor int not null,
id_usuario int not null,
montoFactura float(15) not null,
fecha date not null
);


/*************************************************************************Llaves foraneas **********/
/*****Llaves foranes en Productos*/
ALTER TABLE Producto  ADD
CONSTRAINT FK_producto_categoria
FOREIGN KEY (id_categoria)
REFERENCES Categoria (id_categoria);

ALTER TABLE Producto  ADD
CONSTRAINT FK_producto_proveedor
FOREIGN KEY (id_proveedor)
REFERENCES Proveedor (id_proveedor);



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
FOREIGN KEY (codigo_producto)
REFERENCES Producto (codigo_producto);


/***********Llaves foraneas en Factura_Pedido*/
ALTER TABLE  Factura_Pedido  ADD
CONSTRAINT FK_FacturaPedido_proveedor
FOREIGN KEY (id_proveedor)
REFERENCES Proveedor (id_proveedor);


ALTER TABLE  Factura_Pedido  ADD
CONSTRAINT FK_FacturaPedido_usuario
FOREIGN KEY (id_usuario)
REFERENCES Usuario (id_usuario);

INSERT INTO USUARIO VALUES (null,'Programadores del sistema','admin','123', 'Programador');
INSERT INTO USUARIO VALUES (null,'miguel Angel','mike','mike123', 'Programador');
INSERT INTO USUARIO VALUES (null,'hernan','hernan','h123', 'Programador');
INSERT INTO USUARIO VALUES (null,'Encargado de almacen','useralmacen','alm123', 'Encargado de Almacen');
INSERT INTO USUARIO VALUES (null,'Vendedores generales','vendedor','v123', 'Empleado');



use punto_de_venta;

select * from Cliente;
select * from Usuario;
select * from Categoria;
select * from Producto;
select * from Proveedor;
select * from Ventas;
select * from Detalle_Venta;



/*****************************************************************************************************
					PROCEDIMIENTOS ALMACENADOS
****************************************************************************************************/
/*Procedimientos de USUARIO*/

DROP PROCEDURE IF EXISTS getUsuarios;
CREATE PROCEDURE getUsuarios ()
	SELECT * FROM Usuario;


DROP PROCEDURE IF EXISTS addUsuario;
CREATE PROCEDURE addUsuario(
nombre VARCHAR(100), 
login VARCHAR(100), 
pass VARCHAR(100), 
rol VARCHAR(100))
INSERT INTO Usuario VALUES(null, nombre, login, pass, rol);



DROP PROCEDURE IF EXISTS updateUsuario;
CREATE PROCEDURE updateUsuario(
id int, 
nombre VARCHAR(100), 
login VARCHAR(100), 
pass VARCHAR(100), 
rol VARCHAR(100))
UPDATE Usuario SET Usuario.nombre = nombre, Usuario.login = login, Usuario.contraseña = pass, 
Usuario.rol = rol WHERE Usuario.id_usuario = id;


DROP PROCEDURE IF EXISTS deleteUsuario;
CREATE PROCEDURE deleteUsuario (ID int)
DELETE FROM Usuario WHERE id_usuario = ID;         



DROP PROCEDURE IF EXISTS getBusquedaUsuario1;
CREATE PROCEDURE getBusquedaUsuario1(  
nombre VARCHAR(100), 
login VARCHAR(100), 
rol VARCHAR(100))
	SELECT * from Usuario AS a
	where  a.nombre like (CONCAT('%',nombre,'%'))
	AND  a.login like (CONCAT('%',login,'%'))
	AND  a.rol like (CONCAT('%',rol,'%'))
;


DROP PROCEDURE IF EXISTS getBusquedaUsuario2;
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
;


call getBusquedaUsuario1('','','');
call getBusquedaUsuario2(2,'','','');


/*PROC Validacion LOGIN	--------------------------------------------------------------------------------------------------------------------------------------------------*/
/*ADMINISTRADOR*/

DROP PROCEDURE IF EXISTS getLogin;
CREATE PROCEDURE getLogin()
SELECT * FROM usuario;

 DROP PROCEDURE IF EXISTS buscarLogin;
 CREATE PROCEDURE buscarLogin(login VARCHAR(100), passw VARCHAR(100))
 select * from Usuario as u
 where u.login = login
 AND u.contraseña =passw;

 
 call buscarLogin('Admin','123'); 
 
 
 
 