package com.example.tablas.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.font.FontWeight
import com.example.tablas.Model.User
import com.example.tablas.Repository.UserRepository
import androidx.compose.foundation.layout.statusBarsPadding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun UserApp(userRepository: UserRepository) {
    var nombre by rememberSaveable { mutableStateOf("") }
    var apellido by rememberSaveable { mutableStateOf("") }
    var edad by rememberSaveable { mutableStateOf("") }
    var userId by rememberSaveable { mutableStateOf("") }
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var isDeleting by rememberSaveable { mutableStateOf(false) }
    var users by rememberSaveable { mutableStateOf(listOf<User>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDarkTheme = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .border(
                width = 2.dp,
                color = Color(0xFF00BCD4),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xAAFFFFFF),
                        Color(0xCCFFFFFF)
                    )
                ), RoundedCornerShape(16.dp)
            )
            .padding(24.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de la aplicación
            item {
                Text(
                    text = "AGENDA TABLAS (USUARIO)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = if (isDarkTheme) White else Color.Black,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .alpha(0.9f)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            // Formulario de usuario
            item {
                Text(
                    text = "Agregar Usuario",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                TextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xAAE3F2FD),
                                    Color(0xCCBBDEFB)
                                )
                            ), shape = RoundedCornerShape(8.dp)
                        )
                )

                TextField(
                    value = apellido,
                    onValueChange = { apellido = it },
                    label = { Text("Apellido") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xAAE3F2FD),
                                    Color(0xCCBBDEFB)
                                )
                            ), shape = RoundedCornerShape(8.dp)
                        )
                )

                TextField(
                    value = edad,
                    onValueChange = { edad = it },
                    label = { Text("Edad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xAAE3F2FD),
                                    Color(0xCCBBDEFB)
                                )
                            ), shape = RoundedCornerShape(8.dp)
                        )
                )
            }

            // Botón para agregar usuario
            item {
                Button(
                    onClick = {
                        if (nombre.isNotEmpty() && apellido.isNotEmpty() && edad.isNotEmpty()) {
                            scope.launch {
                                val user = User(nombre = nombre, apellido = apellido, edad = edad.toInt())
                                withContext(Dispatchers.IO) {
                                    userRepository.insert(user)
                                }
                                Toast.makeText(context, "Usuario agregado", Toast.LENGTH_SHORT).show()
                                nombre = ""
                                apellido = ""
                                edad = ""
                            }
                        } else {
                            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF90CAF9),
                        contentColor = White
                    )
                ) {
                    Text("Añadir Usuario")
                }
            }

            // Opciones de edición y eliminación
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isEditing = !isEditing },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isEditing) "Cancelar Edición" else "Editar Usuario")
                    }

                    Button(
                        onClick = { isDeleting = !isDeleting },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isDeleting) "Cancelar Eliminación" else "Eliminar Usuario")
                    }
                }
            }

            // Sección para eliminar usuarios
            if (isDeleting) {
                item {
                    TextField(
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("ID Usuario a Eliminar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xAAE3F2FD),
                                        Color(0xCCBBDEFB)
                                    )
                                ), shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Button(
                        onClick = {
                            val id = userId.toIntOrNull()
                            if (id != null) {
                                scope.launch {
                                    val deletedCount = withContext(Dispatchers.IO) {
                                        userRepository.deleteById(id)
                                    }
                                    if (deletedCount > 0) {
                                        Toast.makeText(context, "Usuario eliminado", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                                    }
                                    userId = ""
                                    isDeleting = false
                                }
                            } else {
                                Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Confirmar Eliminación")
                    }
                }
            }

            // Sección para editar usuarios
            if (isEditing) {
                item {
                    TextField(
                        value = userId,
                        onValueChange = { userId = it },
                        label = { Text("ID Usuario a Editar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xAAE3F2FD),
                                        Color(0xCCBBDEFB)
                                    )
                                ), shape = RoundedCornerShape(8.dp)
                            )
                    )

                    Button(
                        onClick = {
                            val id = userId.toIntOrNull()
                            if (id != null) {
                                scope.launch {
                                    val updatedCount = withContext(Dispatchers.IO) {
                                        userRepository.updateUser(
                                            userId = id,
                                            nombre = if (nombre.isNotEmpty()) nombre else null,
                                            apellido = if (apellido.isNotEmpty()) apellido else null,
                                            edad = if (edad.isNotEmpty()) edad.toIntOrNull() else null
                                        )
                                    }
                                    if (updatedCount > 0) {
                                        Toast.makeText(context, "Usuario actualizado", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Usuario no encontrado o sin cambios", Toast.LENGTH_SHORT).show()
                                    }
                                    isEditing = false
                                    nombre = ""
                                    apellido = ""
                                    edad = ""
                                    userId = ""
                                }
                            } else {
                                Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }

            // Botón para listar usuarios
            item {
                Button(
                    onClick = {
                        scope.launch {
                            users = withContext(Dispatchers.IO) {
                                userRepository.getAllUsers()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Listar Usuarios")
                }
            }

            // Mostrar usuarios
            items(users) { user ->
                Text("${user.id}: ${user.nombre} ${user.apellido}, Edad: ${user.edad}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
