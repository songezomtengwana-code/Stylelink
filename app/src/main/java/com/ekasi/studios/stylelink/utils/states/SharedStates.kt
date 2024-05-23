package com.ekasi.studios.stylelink.utils.states

import com.ekasi.studios.stylelink.data.model.Product
import com.ekasi.studios.stylelink.data.model.Service

class SharedStates {
}

sealed class ServicesState {
    data object Loading : ServicesState()
    data class MultipleServicesSuccess(val services: List<Service>) : ServicesState()
    data class SingleServiceSuccess(val services: Service) : ServicesState()
    data class Error(val message: String) : ServicesState()
}

sealed class ProductState {
    data object Loading : ProductState()
    data class MultipleProductsSuccess(val products: List<Product>) : ProductState()
    data class SingleProductSuccess(val products: Product) : ProductState()
    data class Error(val message: String) : ProductState()
}