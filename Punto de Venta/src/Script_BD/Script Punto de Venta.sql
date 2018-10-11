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
importe float(4) null
);

create table Factura_Pedido(
folio_factura int not null primary key auto_increment,
id_proveedor int not null,
id_usuario int not null,
montoFactura float(15) not null,
fecha date not null
);


/*******************Llaves foraneas **********/




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




use punto_de_venta;

select * from Cliente;
select * from Usuario;
select * from Categoria;
select * from Producto;
select * from Proveedor;
select * from Ventas;
select * from Detalle_Venta;