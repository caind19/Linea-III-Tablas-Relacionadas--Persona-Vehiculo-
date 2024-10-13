package com.example.tablas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tablas.DAO.UserDao
import com.example.tablas.DAO.VehicleDao
import com.example.tablas.Database.AppDatabase
import com.example.tablas.Repository.UserRepository
import com.example.tablas.Repository.VehicleRepository
import com.example.tablas.Screen.UserApp
import com.example.tablas.Screen.VehicleApp
import com.example.tablas.ui.theme.TablasTheme

class MainActivity : ComponentActivity() {
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository
    private lateinit var vehicleDao: VehicleDao
    private lateinit var vehicleRepository: VehicleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtener la instancia de la base de datos
        val db = AppDatabase.getDatabase(applicationContext)

        // Inicializar DAOs y repositorios
        userDao = db.userDao()
        userRepository = UserRepository(userDao)

        vehicleDao = db.vehicleDao()
        vehicleRepository = VehicleRepository(vehicleDao)

        // Habilitar Edge-to-Edge para diseño más moderno
        enableEdgeToEdge()

        // Establecer el contenido de la actividad
        setContent {
            TablasTheme {
                // Scaffold para manejar estructura de la pantalla
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { paddingValues ->
                        MainScreen(
                            userRepository = userRepository,
                            vehicleRepository = vehicleRepository,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    userRepository: UserRepository,
    vehicleRepository: VehicleRepository,
    modifier: Modifier = Modifier
) {
    var currentScreen by remember { mutableStateOf("UserApp") }  // Estado para controlar la pantalla actual

    Column(modifier = modifier) {
        // Botones de navegación
        Button(onClick = { currentScreen = "UserApp" }) {
            Text("Gestión de Usuarios")
        }
        Button(onClick = { currentScreen = "VehicleApp" }) {
            Text("Gestión de Vehículos")
        }

        // Mostrar la pantalla correspondiente
        when (currentScreen) {
            "UserApp" -> UserApp(userRepository = userRepository)
            "VehicleApp" -> VehicleApp(vehicleRepository = vehicleRepository)
        }
    }
}