package com.example.ecommerce_kotlin.ui.catalog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_kotlin.domain.model.Product
import com.example.ecommerce_kotlin.viewmodel.CartViewModel
import com.example.ecommerce_kotlin.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    navController: NavController,
    viewModel: ProductViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var query by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(vertical = 16.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Buscar...", color = Color.White) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .background(Color.White, shape = MaterialTheme.shapes.medium),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.DarkGray,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent,
                            cursorColor = MaterialTheme.colorScheme.primary
                        ),
                        trailingIcon = {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar")
                        }
                    )
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { /* navController.navigate("perfil") */ }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Perfil",
                            tint = Color.White
                        )
                    }
                }
            }

            // Contenido
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.error}")
                }
            } else {
                val filteredProducts = if (query.length >= 3) {
                    state.products.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                } else state.products

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            cartViewModel = cartViewModel,
                            navController = navController,
                            onProductAdded = {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Producto agregado al carrito")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ProductCard(
    product: Product,
    cartViewModel: CartViewModel,
    navController: NavController,
    onProductAdded: () -> Unit // 👈 este es el nuevo parámetro
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .clickable {
                navController.navigate("producto/${product.id}")
            },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = rememberAsyncImagePainter(product.imageUrl),
                contentDescription = product.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .padding(8.dp)
            ) {
                Text(
                    text = product.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "ID: ${product.id}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        cartViewModel.addItem(product)
                        onProductAdded() // 👈 ejecutamos la lambda al hacer clic
                    },
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                    modifier = Modifier
                        .height(36.dp)
                        .align(Alignment.End)
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Agregar al carrito",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

