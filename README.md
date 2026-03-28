# kinalapp - 2022166
Descripción:  
kinalapp es una API REST desarrollada con Spring Boot que permite la gestión de diferentes entidades como clientes, productos, usuarios, ventas y detalle de ventas. El sistema implementa operaciones CRUD completas y cuenta con autenticación mediante Spring Security para el acceso seguro a los endpoints.

## Tecnologías Utilizadas
* **Java 21**
* **Spring Boot 4.0.2**
* **Maven** (Gestor de dependencias)
* **MySQL** (Sistema gestor de base de datos)
* **Spring Security** (Autenticación y autorización)

## Requisitos Previos
Antes de ejecutar la aplicación, debe tener instalado en su dispositivo:
* JDK 17 o superior
* Maven instalado
* Una instancia activa en MySQL
* Contar con Postman instalado (opcional, recomendado para pruebas de endpoints)

## Instalación y Ejecución
1. Clonar el repositorio de GitHub (https://github.com/ddeleon-2022166/kinalapp.git)
2. Ejecutar el IDE de su preferencia, tanto para Spring Boot como para la base de datos
3. Dentro del IDE (Spring Boot), abrir el proyecto previamente clonado
4. Como primera disposición, debe dirigirse a la configuración de la aplicación (src/main/resources/application.properties)

5. Ubicado en el archivo de configuración, encontrará lo siguiente:

* spring.datasource.url=jdbc:mysql://localhost:3306/dbClientes_in5am?createDatabaseIfNotExist=true  
  (Esto creará la base de datos automáticamente. Para su correcto funcionamiento, asegúrese de que MySQL esté en ejecución)

* spring.datasource.username=IN5AM  
  (Corresponde al usuario de su instancia de MySQL. Debe ajustarlo según su configuración)

* spring.datasource.password=Kinal@2026AM  
  (Debe colocar la contraseña correspondiente al usuario configurado en MySQL)

* server.port=8081  
  (Define el puerto en el que se ejecutará la aplicación)

6. Una vez realizado lo anterior, deberá ejecutar la aplicación. El proyecto cuenta con Spring Security, por lo que, para poder acceder a cada endpoint, deberá crear un usuario siguiendo el siguiente flujo:

* En la clase main verá:  
  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();  
  System.out.println(encoder.encode("1234"));  
  (El valor dentro de "1234" representa la contraseña a encriptar)

* Al ejecutar la aplicación, se generará un hash en la consola del IDE (Ej: $2a$10$hhtfgLfK9fK87DeVc7MKN.bW8Vu54...)

* La base de datos y sus respectivas tablas se crean automáticamente al iniciar la aplicación. Luego, en su gestor de base de datos, ejecutar:  
  use dbclientes_in5am;

* Posteriormente, insertar un usuario mediante:  
  INSERT INTO users (username, password, role)  
  VALUES ('admin', 'AQUI_EL_HASH_GENERADO', 'ADMIN');

7. Ahora, en base al puerto (véase en el paso 5), en su navegador de preferencia deberá ingresar en el buscador:  
   http://localhost:8081

8. Esto lo llevará a un formulario de inicio de sesión, donde deberá ingresar el nombre y la contraseña del usuario previamente creado

9. Una vez haya iniciado sesión, podrá acceder a los demás endpoints sin restricciones (/clientes, /productos, /usuarios, /ventas, /detalle-ventas)

10. Al ingresar a cualquiera de los mencionados, notará que no existe ningún dato. Para poder gestionarlos (crear, consultar, actualizar y eliminar), deberá utilizar el CRUD de cada entidad por medio de Postman.
11. Se adjuntan las respectivas pruebas de cada metodo HTTPS, creado por medio de Postman
* Clientes:
  ![Crear Cliente](docs/images/clientes/img_1.png)
  ![Listar Clientes](docs/images/clientes/img_2.png)
  ![Buscar Cliente](docs/images/clientes/img_3.png)
  ![Actualizar Cliente](docs/images/clientes/img_4.png)
  ![Eliminar Cliente](docs/images/clientes/img_5.png)
  ![Clientes Activos](docs/images/clientes/img_6.png)

* Productos:
  ![Crear Producto](docs/images/productos/img_7.png)
  ![Listar Productos](docs/images/productos/img_8.png)
  ![Buscar Producto](docs/images/productos/img_9.png)
  ![Actualizar Producto](docs/images/productos/img_10.png)
  ![Eliminar Producto](docs/images/productos/img_11.png)
  ![Productos Activos](docs/images/productos/img_12.png)

* Usuarios:
  ![Crear Usuario](docs/images/usuarios/img_13.png)
  ![Listar Usuarios](docs/images/usuarios/img_15.png)
  ![Buscar Usuario](docs/images/usuarios/img_16.png)
  ![Actualizar Usuario](docs/images/usuarios/img_17.png)
  ![Eliminar Usuario](docs/images/usuarios/img_18.png)
  ![Usuarios Activos](docs/images/usuarios/img_19.png)

* Ventas:
  ![Crear Venta](docs/images/ventas/img_20.png)
  ![Listar Ventas](docs/images/ventas/img_21.png)
  ![Buscar Venta](docs/images/ventas/img_22.png)
  ![Actualizar Venta](docs/images/ventas/img_23.png)
  ![Eliminar Venta](docs/images/ventas/img_24.png)
  ![Ventas Activas](docs/images/ventas/img_25.png)

* DetalleVentas:
  ![Crear Detalle Venta](docs/images/detalle-ventas/img_26.png)
  ![Listar Detalles Venta](docs/images/detalle-ventas/img_27.png)
  ![Buscar Detalle Venta](docs/images/detalle-ventas/img_28.png)
  ![Actualizar Detalle Venta](docs/images/detalle-ventas/img_29.png)
  ![Eliminar Detalle Venta](docs/images/detalle-ventas/img_30.png)