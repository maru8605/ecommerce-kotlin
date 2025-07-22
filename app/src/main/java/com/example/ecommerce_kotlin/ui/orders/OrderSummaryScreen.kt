package com.example.ecommerce_kotlin.ui.orders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecommerce_kotlin.domain.model.CartItem
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderSummaryScreen(
    cartItems: List<CartItem>,
    total: Double,
    onConfirm: () -> Unit,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Resumen del pedido") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)

            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Productos:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(cartItems) { item ->
                    Text("- ${item.product.title} (${item.quantity})")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Total: $${"%.2f".format(total)}", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Te va a llegar un email con los detalles para abonar el pedido.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    onConfirm()
                    navController.navigate("catalog") {
                        popUpTo("orderSummary") { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Confirmar compra")
            }
        }
    }
}
