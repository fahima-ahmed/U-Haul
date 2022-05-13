package com.fahima.uhaul.test.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class User(
    val id: Int? = null,
    @SerializedName("username")
    val userName: String? = null,
    val address: Address? = null
) : Serializable

data class Address(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val geo: Geo? = null
) : Serializable

data class Geo(
    val lat: String? = null,
    val lng: String? = null
) : Serializable