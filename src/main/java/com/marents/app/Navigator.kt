package com.marents.app

import android.os.Bundle
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


sealed class NavigationCommand {
    data class To(val route: String, val args: Bundle? = null) : NavigationCommand()
    data class ToAction(val actionId: Int, val args: Bundle? = null) : NavigationCommand()
    object Back : NavigationCommand()
}

object AppRoutes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val CATEGORIES = "categories"
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val DETALLE_PRODUCTO = "detalle_producto/{productoId}"
}

class Navigator {
    private val _commands = MutableStateFlow<NavigationCommand?>(null)
    val commands: StateFlow<NavigationCommand?> = _commands.asStateFlow()

    fun navigate(command: NavigationCommand) {
        _commands.value = command
    }

    fun navigateToWelcome() = navigate(NavigationCommand.To(AppRoutes.WELCOME))
    fun navigateToLogin() = navigate(NavigationCommand.To(AppRoutes.LOGIN))
    fun navigateToRegister() = navigate(NavigationCommand.To(AppRoutes.REGISTER))
    fun navigateToCategories() = navigate(NavigationCommand.To(AppRoutes.CATEGORIES))
    fun navigateToHome() = navigate(NavigationCommand.To(AppRoutes.HOME))
    fun navigateToProductos() = navigate(NavigationCommand.To(AppRoutes.PRODUCTOS))
    fun navigateToDetalleProducto(productoId: Int) {
        val route = "detalle_producto/$productoId"
        navigate(NavigationCommand.To(route))
    }

    fun back() = navigate(NavigationCommand.Back)

    interface Provider {
        fun getNavigator(): Navigator
    }
}
