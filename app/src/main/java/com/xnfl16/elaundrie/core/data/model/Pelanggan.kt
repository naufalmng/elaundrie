package com.xnfl16.elaundrie.core.data.model

import java.io.Serializable

data class Pelanggan(
    val id: String?,
    val nama: String?,
    val alamat: String?,
    val tanggalDanWaktu: String?,
    val jumlah: String?,
    val diskon: String?,
    val total: String?,
    val status: String?,
    val layanan: String?
): Serializable
