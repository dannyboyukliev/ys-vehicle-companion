package com.yamasoft.vehiclecompanion.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yamasoft.vehiclecompanion.ui.theme.VehicleCompanionTheme

@Composable
fun VehicleFormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = {
                Text(
                    text = label
                )
            },
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun VehicleFormFieldPreview() {
    VehicleCompanionTheme {
        VehicleFormField(
            value = "2",
            onValueChange = {},
            label = "Model"
        )
    }
}