package com.example.foodxdonatur.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LocationResponse (
    @SerializedName("plus_code")
    var plusCode: PlusCode? = null,
    @SerializedName("results")
    var results: List<Result>? = null,
    @SerializedName("status")
    var status: String? = null // OK
) : Serializable {
    data class PlusCode(
        @SerializedName("compound_code")
        var compoundCode: String? = null, // MPV4+W7 South Tangerang, South Tangerang City, Banten, Indonesia
        @SerializedName("global_code")
        var globalCode: String? = null // 6P58MPV4+W7
    ) : Serializable

    data class Result(
        @SerializedName("address_components")
        var addressComponents: List<AddressComponent>? = null,
        @SerializedName("formatted_address")
        var formattedAddress: String? = null, // Jalan Jati Jl. Bukit Nusa Indah Ciputat No.kav 1107, Serua, Ciputat, South Tangerang City, Banten 15414, Indonesia
        @SerializedName("geometry")
        var geometry: Geometry? = null,
        @SerializedName("place_id")
        var placeId: String? = null, // ChIJxyoe-p_laS4RloYnrezl2i4
        @SerializedName("plus_code")
        var plusCode: PlusCode? = null,
        @SerializedName("types")
        var types: List<String?>? = null
    ) : Serializable {
        data class AddressComponent(
            @SerializedName("long_name")
            var longName: String? = null, // Serua
            @SerializedName("short_name")
            var shortName: String? = null, // Serua
            @SerializedName("types")
            var types: List<String?>? = null
        ) : Serializable

        data class Geometry(
            @SerializedName("bounds")
            var bounds: Bounds? = null,
            @SerializedName("location")
            var location: Location? = null,
            @SerializedName("location_type")
            var locationType: String? = null, // GEOMETRIC_CENTER
            @SerializedName("viewport")
            var viewport: Viewport? = null
        ) : Serializable {
            data class Bounds(
                @SerializedName("northeast")
                var northeast: Northeast? = null,
                @SerializedName("southwest")
                var southwest: Southwest? = null
            ) : Serializable {
                data class Northeast(
                    @SerializedName("lat")
                    var lat: Double? = null, // -6.3049653
                    @SerializedName("lng")
                    var lng: Double? = null // 106.7057618
                ) : Serializable

                data class Southwest(
                    @SerializedName("lat")
                    var lat: Double? = null, // -6.305186099999999
                    @SerializedName("lng")
                    var lng: Double? = null // 106.7057345
                ) : Serializable
            }

            data class Location(
                @SerializedName("lat")
                var lat: Double? = null, // -6.305205
                @SerializedName("lng")
                var lng: Double? = null // 106.7057322
            ) : Serializable

            data class Viewport(
                @SerializedName("northeast")
                var northeast: Northeast? = null,
                @SerializedName("southwest")
                var southwest: Southwest? = null
            ) : Serializable {
                data class Northeast(
                    @SerializedName("lat")
                    var lat: Double? = null, // -6.303856019708498
                    @SerializedName("lng")
                    var lng: Double? = null // 106.7070811802915
                ) : Serializable

                data class Southwest(
                    @SerializedName("lat")
                    var lat: Double? = null, // -6.306553980291502
                    @SerializedName("lng")
                    var lng: Double? = null // 106.7043832197085
                ) : Serializable
            }
        }

        data class PlusCode(
            @SerializedName("compound_code")
            var compoundCode: String? = null, // MPV4+W7 Serua, South Tangerang City, Banten, Indonesia
            @SerializedName("global_code")
            var globalCode: String? = null // 6P58MPV4+W7
        ) : Serializable
    }
}