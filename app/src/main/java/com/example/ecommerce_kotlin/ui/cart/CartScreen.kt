package com.example.ecommerce_kotlin.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_kotlin.viewmodel.CartViewModel
import com.example.ecommerce_kotlin.domain.model.CartItem

@Composable
fun CartScreen(navController: NavController, viewModel: CartViewModel = hiltViewModel()) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navController.navigate("profile") }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Perfil",
                        tint = Color.White
                    )
                }
            }
        }

        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay nada agregado a tu carrito.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(cartItems) { cartItem ->
                    CartItemCard(
                        cartItem = cartItem,
                        onAdd = { viewModel.addItem(cartItem.product) },
                        onRemove = { viewModel.removeItem(cartItem.product) },
                        onDelete = { viewModel.deleteItem(cartItem.product) }
                    )
                    Divider(color = Color.LightGray.copy(alpha = 0.5f))
                }
            }

            // Total y botón de pedido
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Total: $${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.End)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { navController.navigate("orderSummary") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Hacer pedido")
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(2.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = rememberAsyncImagePainter(cartItem.product.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = cartItem.product.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "$${cartItem.product.price}",
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onRemove) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Menos")
                    }
                    Text("${cartItem.quantity}")
                    IconButton(onClick = onAdd) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Más")
                    }
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }
        }
    }
}
