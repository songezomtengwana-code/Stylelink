package com.ekasi.studios.stylelink.base.components.StorePreview

import com.ekasi.studios.stylelink.utils.services.stores.models.Store

sealed class StorePreviewState {
    data object Loading : StorePreviewState()
    data class Error(val message: String) : StorePreviewState()
    data class Success(val store: Store) : StorePreviewState()
}
