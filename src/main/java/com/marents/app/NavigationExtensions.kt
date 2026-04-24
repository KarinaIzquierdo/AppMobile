package com.marents.app

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

fun Fragment.observeNavigation(
    navigator: Navigator,
    handler: (NavigationCommand) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navigator.commands.collect { command ->
                command?.let { handler(it) }
            }
        }
    }
}
