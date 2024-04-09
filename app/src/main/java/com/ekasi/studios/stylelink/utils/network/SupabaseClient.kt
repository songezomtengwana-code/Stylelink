package com.ekasi.studios.stylelink.utils.network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest

class SupabaseClient {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://lxbuxlzmbfczffjsscsj.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imx4YnV4bHptYmZjemZmanNzY3NqIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTIzMTQ1MDAsImV4cCI6MjAyNzg5MDUwMH0.J4Ygl5Ty_5zRN12Co1H1LQTvGWv8H-LpTQra7keh1uc"
    ) {
        install(Postgrest)
    }
}