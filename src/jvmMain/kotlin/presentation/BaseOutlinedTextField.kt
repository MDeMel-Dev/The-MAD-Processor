package presentation

import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun BaseOutlinedTextField(label: String, passInput: (inputValue: Float) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = {
            text = it
            passInput.invoke(it.toFloat())
        },
        label = { Text(label) },
        placeholder = { Text("input....") }
    )
}