package com.marents.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marents.app.databinding.ActivityMainBinding
import com.marents.app.ui.categorias.CategoriasFragment
import com.marents.app.ui.home.HomeFragment
import com.marents.app.ui.login.LoginFragment
import com.marents.app.ui.register.RegisterFragment
import com.marents.app.ui.welcome.WelcomeFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Navigator.Provider {

    private lateinit var binding: ActivityMainBinding
    private val navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mostrar WelcomeFragment al inicio
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, WelcomeFragment())
                .commit()
        }

        // Observar comandos de navegación
        observeNavigation()
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                navigator.commands.collect { command: NavigationCommand? ->
                    command?.let { navCommand ->
                        when (navCommand) {
                            is NavigationCommand.To -> {
                                when (navCommand.route) {
                                    AppRoutes.HOME -> {
                                        supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, HomeFragment())
                                            .addToBackStack(null)
                                            .commit()
                                    }
                                    AppRoutes.LOGIN -> {
                                        supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, LoginFragment())
                                            .addToBackStack(null) // Permite volver atrás
                                            .commit()
                                    }
                                    AppRoutes.REGISTER -> {
                                        supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, RegisterFragment())
                                            .addToBackStack(null) // Permite volver atrás
                                            .commit()
                                    }
                                    AppRoutes.CATEGORIES -> {
                                        supportFragmentManager.beginTransaction()
                                            .replace(R.id.fragment_container, CategoriasFragment())
                                            .addToBackStack(null)
                                            .commit()
                                    }
                                }
                            }
                            is NavigationCommand.Back -> {
                                if (supportFragmentManager.backStackEntryCount > 0) {
                                    supportFragmentManager.popBackStack()
                                } else {
                                    finish() // Cierra la app si no hay nada en el back stack
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun getNavigator(): Navigator = navigator
}
