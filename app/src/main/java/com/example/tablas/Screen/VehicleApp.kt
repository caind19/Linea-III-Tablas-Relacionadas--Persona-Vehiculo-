package com.example.tablas.Screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tablas.Model.Vehicle
import com.example.tablas.Repository.VehicleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun VehicleApp(vehicleRepository: VehicleRepository) {
    // Estados para los campos de vehículo
    var marca by rememberSaveable { mutableStateOf("") }
    var modelo by rememberSaveable { mutableStateOf("") }
    var caballos by rememberSaveable { mutableStateOf("") }
    var vehicleId by rememberSaveable { mutableStateOf("") } // Campo para ID
    var isEditing by rememberSaveable { mutableStateOf(false) }
    var isDeleting by rememberSaveable { mutableStateOf(false) }
    var vehicles by rememberSaveable { mutableStateOf(listOf<Vehicle>()) }

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
                color = Color(0xFF00BCD4), // Color turquesa tipo KDE Plasma
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
            // Encabezado de la app
            item {
                Text(
                    text = "AGENDA TABLAS (VEHICULOS)",
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

            // Formulario para agregar vehículos
            item {
                Text(
                    text = "Agregar Vehículo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                TextField(
                    value = marca,
                    onValueChange = { marca = it },
                    label = { Text("Marca") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                    value = modelo,
                    onValueChange = { modelo = it },
                    label = { Text("Modelo") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                    value = caballos,
                    onValueChange = { caballos = it },
                    label = { Text("Caballos de Fuerza") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
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
                        if (marca.isNotEmpty() && modelo.isNotEmpty() && caballos.isNotEmpty()) {
                            scope.launch {
                                val vehicle = Vehicle(
                                    marca = marca,
                                    modelo = modelo,
                                    caballos = caballos.toInt()
                                )
                                if (isEditing) {
                                    val id = vehicleId.toIntOrNull()
                                    if (id != null) {
                                        val updatedCount = withContext(Dispatchers.IO) {
                                            vehicleRepository.updateVehicle(id, marca, modelo, caballos.toInt())
                                        }
                                        if (updatedCount > 0) {
                                            Toast.makeText(context, "Vehículo actualizado", Toast.LENGTH_SHORT).show()
                                        } else {
                                            Toast.makeText(context, "Vehículo no encontrado o sin cambios", Toast.LENGTH_SHORT).show()
                                        }
                                    } else {
                                        Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    withContext(Dispatchers.IO) {
                                        vehicleRepository.insert(vehicle)
                                    }
                                    Toast.makeText(context, "Vehículo agregado", Toast.LENGTH_SHORT).show()
                                }
                                // Limpiar campos
                                marca = ""
                                modelo = ""
                                caballos = ""
                                vehicleId = ""
                                isEditing = false
                            }
                        } else {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
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
                    Text(if (isEditing) "Actualizar Vehículo" else "Agregar Vehículo")
                }
            }

            // Botones para editar o eliminar vehículo
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { isEditing = !isEditing },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isEditing) "Cancelar Edición" else "Editar Vehículo")
                    }

                    Button(
                        onClick = { isDeleting = !isDeleting },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (isDeleting) "Cancelar Eliminación" else "Eliminar Vehículo")
                    }
                }
            }

            // Campo y botón para eliminar vehículo por ID
            if (isDeleting) {
                item {
                    Text(
                        text = "Eliminar Vehículo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    TextField(
                        value = vehicleId,
                        onValueChange = { vehicleId = it },
                        label = { Text("ID Vehículo a Eliminar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
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
                            val id = vehicleId.toIntOrNull()
                            if (id != null) {
                                scope.launch {
                                    val deletedCount = withContext(Dispatchers.IO) {
                                        vehicleRepository.deleteById(id)
                                    }
                                    if (deletedCount > 0) {
                                        Toast.makeText(context, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                                        vehicles = vehicles.filter { it.id != id }
                                    } else {
                                        Toast.makeText(context, "Vehículo no encontrado", Toast.LENGTH_SHORT).show()
                                    }
                                    vehicleId = ""
                                    isDeleting = false
                                }
                            } else {
                                Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(4.dp, RoundedCornerShape(8.dp)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = White
                        )
                    ) {
                        Text("Confirmar Eliminación")
                    }
                }
            }

            // Campo y botón para editar vehículo por ID
            if (isEditing) {
                item {
                    Text(
                        text = "Editar Vehículo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                    TextField(
                        value = vehicleId,
                        onValueChange = { vehicleId = it },
                        label = { Text("ID Vehículo a Editar") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
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
                            val id = vehicleId.toIntOrNull()
                            if (id != null) {
                                scope.launch {
                                    val updatedCount = withContext(Dispatchers.IO) {
                                        vehicleRepository.updateVehicle(
                                            vehicleId = id,
                                            marca = if (marca.isNotEmpty()) marca else null,
                                            modelo = if (modelo.isNotEmpty()) modelo else null,
                                            caballos = if (caballos.isNotEmpty()) caballos.toIntOrNull() else null
                                        )
                                    }
                                    if (updatedCount > 0) {
                                        Toast.makeText(context, "Vehículo actualizado", Toast.LENGTH_SHORT).show()
                                        // Actualizar la lista local
                                        vehicles = withContext(Dispatchers.IO) {
                                            vehicleRepository.getAllVehicles()
                                        }
                                    } else {
                                        Toast.makeText(context, "Vehículo no encontrado o sin cambios", Toast.LENGTH_SHORT).show()
                                    }
                                    // Limpiar campos
                                    marca = ""
                                    modelo = ""
                                    caballos = ""
                                    vehicleId = ""
                                    isEditing = false
                                }
                            } else {
                                Toast.makeText(context, "ID no válido", Toast.LENGTH_SHORT).show()
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
                        Text("Guardar Cambios")
                    }
                }
            }

            // Botón para listar vehículos
            item {
                Button(
                    onClick = {
                        scope.launch {
                            vehicles = withContext(Dispatchers.IO) {
                                vehicleRepository.getAllVehicles()
                            }
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
                    Text("Listar Vehículos")
                }
            }

            // Listado de vehículos
            items(vehicles) { vehicle ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFE3F2FD),
                                    Color(0xFFBBDEFB)
                                )
                            ), RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF00BCD4),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    Text(
                        text = "${vehicle.id}: ${vehicle.marca} ${vehicle.modelo}, ${vehicle.caballos} HP",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            onClick = {
                                // Acción para eliminar vehículo
                                scope.launch {
                                    withContext(Dispatchers.IO) {
                                        vehicleRepository.deleteById(vehicle.id)
                                    }
                                    Toast.makeText(context, "Vehículo eliminado", Toast.LENGTH_SHORT).show()
                                    vehicles = vehicles.filter { it.id != vehicle.id }
                                }
                            },
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .shadow(2.dp, RoundedCornerShape(4.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = White
                            )
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}