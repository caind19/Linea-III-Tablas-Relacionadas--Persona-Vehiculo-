# Linea-III-Tablas-Relacionadas--Persona-Vehiculo-

# Descripción

Agenda de Vehículos y Usuarios con Base de Datos es una aplicación desarrollada en Android Studio utilizando Jetpack Compose y Room. Creada por el estudiante Cain David Martinez como parte de la Línea de Profundización III 901N, la aplicación permite gestionar vehículos y usuarios en una relación de uno a uno. La interfaz es intuitiva y permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) tanto para usuarios como para vehículos.

# Funcionalidades Principales

# VehicleApp.kt

    Gestión de Vehículos: Permite agregar, editar, listar y eliminar vehículos con detalles como marca, modelo, caballos de fuerza y asociación con un usuario.

    Funcionalidades:
        Agregar Vehículo: Registro de nuevos vehículos.
        Editar Vehículo: Actualización de vehículos existentes proporcionando su ID.
        Eliminar Vehículo: Eliminación de vehículos por su ID.
        Listar Vehículos: Visualización de todos los vehículos registrados.

# UserApp.kt

    Gestión de Usuarios: Formulario para la manipulación de usuarios, que permite ingresar nombre, apellido y edad, y proporciona las siguientes funcionalidades:
        Agregar Usuario: Permite registrar nuevos usuarios.
        Editar Usuario: Actualiza los datos de un usuario existente proporcionando su ID.
        Eliminar Usuario: Posibilidad de eliminar usuarios por su ID.
        Listar Usuarios: Muestra todos los usuarios registrados.

# Room Database

    Persistencia de Datos: Room gestiona la base de datos local, almacenando tanto los vehículos como los usuarios de forma eficiente y segura. Además, la relación entre vehículos y usuarios es de uno a uno, permitiendo la asociación directa entre ambas entidades.

# Diseño de Interfaz

    Interfaz Responsiva: Diseñada para ser intuitiva y funcionar en modo vertical u horizontal, utilizando scroll para mantener accesibles todos los elementos.
    Formulario de Usuario y Vehículo: Los formularios están bien organizados y optimizados para una fácil interacción.

# IDE y Lenguaje Utilizados

    Android Studio
    Kotlin

- ¡Gestiona tanto usuarios como vehículos de manera sencilla y eficiente con LineaIII-AgendaBD!