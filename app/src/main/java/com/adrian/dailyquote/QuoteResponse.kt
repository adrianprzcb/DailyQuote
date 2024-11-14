package com.adrian.dailyquote;


import com.google.gson.annotations.SerializedName

 data class QuoteResponse (

        @SerializedName("q") var q: String,
        @SerializedName("a") var a: String
)