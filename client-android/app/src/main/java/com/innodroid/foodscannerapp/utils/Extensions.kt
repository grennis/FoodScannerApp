import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(key)

fun Fragment.setNavigationResult(result: String, key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
}

fun Fragment.alert(@StringRes text: Int, dismissed: () -> Unit = {}) {
    alert(activity?.getString(text), dismissed)
}

fun Fragment.alert(message: String?, dismissed: () -> Unit = {}) {
    message ?: return
    val activity = activity ?: return

    AlertDialog.Builder(activity)
        .setMessage(message)
        .setPositiveButton(android.R.string.ok) { _, _ -> dismissed() }
        .show()
}
