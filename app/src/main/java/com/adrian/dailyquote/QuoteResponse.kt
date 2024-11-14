package com.adrian.dailyquote;


import com.google.gson.annotations.SerializedName

 data class QuoteResponse (

        @SerializedName("quote") var quote: String,
        @SerializedName("author") var author: String
)