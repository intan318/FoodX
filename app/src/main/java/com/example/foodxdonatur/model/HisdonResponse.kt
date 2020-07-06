package com.example.foodxdonatur.model


import com.google.gson.annotations.SerializedName

data class HisdonResponse(
    @SerializedName("donasi")
    var donasi: List<Donasi?>?
) {
    data class Donasi(
        @SerializedName("accDonasi")
        var accDonasi: Boolean?,
        @SerializedName("alamat_penjemputan")
        var alamatPenjemputan: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("donatur_id")
        var donaturId: Int?,
        @SerializedName("foto")
        var foto: String?,
        @SerializedName("id")
        var id: Int?,
        @SerializedName("komunitas")
        var komunitas: Komunitas?,
        @SerializedName("komunitas_id")
        var komunitasId: Int?,
        @SerializedName("latitude")
        var latitude: String?,
        @SerializedName("longitude")
        var longitude: String?,
        @SerializedName("notes")
        var notes: String?,
        @SerializedName("penerima_donasi")
        var penerimaDonasi: PenerimaDonasi?,
        @SerializedName("penerima_id")
        var penerimaId: Int?,
        @SerializedName("relawan")
        var relawan: Relawan?,
        @SerializedName("relawan_id")
        var relawanId: Int?,
        @SerializedName("status")
        var status: String?,
        @SerializedName("tgl_penjemputan")
        var tglPenjemputan: String?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("waktu_penjemputan")
        var waktuPenjemputan: String?
    ) {
        data class Komunitas(
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("foto_komunitas")
            var fotoKomunitas: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("legalitas")
            var legalitas: String?,
            @SerializedName("status")
            var status: Boolean?,
            @SerializedName("tgl_berdiri")
            var tglBerdiri: String?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("user")
            var user: User?,
            @SerializedName("user_id")
            var userId: Int?
        ) {
            data class User(
                @SerializedName("alamat")
                var alamat: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("email")
                var email: String?,
                @SerializedName("email_verified_at")
                var emailVerifiedAt: Any?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("no_telp")
                var noTelp: String?,
                @SerializedName("updated_at")
                var updatedAt: String?
            )
        }

        data class PenerimaDonasi(
            @SerializedName("alamat_penerima")
            var alamatPenerima: String?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("foto")
            var foto: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("latitude")
            var latitude: String?,
            @SerializedName("longitude")
            var longitude: String?,
            @SerializedName("nama_penerima")
            var namaPenerima: String?,
            @SerializedName("updated_at")
            var updatedAt: String?
        )

        data class Relawan(
            @SerializedName("agama")
            var agama: String?,
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("foto_relawan")
            var fotoRelawan: String?,
            @SerializedName("gol_darah")
            var golDarah: String?,
            @SerializedName("id")
            var id: Int?,
            @SerializedName("jenis_kelamin")
            var jenisKelamin: String?,
            @SerializedName("jenis_sim")
            var jenisSim: String?,
            @SerializedName("kabupaten_kota")
            var kabupatenKota: String?,
            @SerializedName("komunitas_id")
            var komunitasId: Int?,
            @SerializedName("latitude")
            var latitude: Any?,
            @SerializedName("longitude")
            var longitude: Any?,
            @SerializedName("motivasi")
            var motivasi: String?,
            @SerializedName("nama_panggilan")
            var namaPanggilan: String?,
            @SerializedName("organisasi_ongoing")
            var organisasiOngoing: String?,
            @SerializedName("pekerjaan")
            var pekerjaan: String?,
            @SerializedName("pend_terakhir")
            var pendTerakhir: String?,
            @SerializedName("provinsi")
            var provinsi: String?,
            @SerializedName("tempat_lahir")
            var tempatLahir: String?,
            @SerializedName("tgl_lahir")
            var tglLahir: String?,
            @SerializedName("updated_at")
            var updatedAt: String?,
            @SerializedName("user")
            var user: User?,
            @SerializedName("user_id")
            var userId: Int?
        ) {
            data class User(
                @SerializedName("alamat")
                var alamat: String?,
                @SerializedName("created_at")
                var createdAt: String?,
                @SerializedName("email")
                var email: String?,
                @SerializedName("email_verified_at")
                var emailVerifiedAt: Any?,
                @SerializedName("id")
                var id: Int?,
                @SerializedName("name")
                var name: String?,
                @SerializedName("no_telp")
                var noTelp: String?,
                @SerializedName("updated_at")
                var updatedAt: String?
            )
        }
    }
}