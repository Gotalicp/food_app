package com.example.food_app.data

class ApiData {
}
@JsonIgnoreProperties(ignoreUnknown = true)
data class RawResult(
    @JsonProperty("results") val results: List<Recipe?> = emptyList(),
    @JsonProperty("offset") val offset: Int? = 0,
    @JsonProperty("number") val number: Int? = 0,
    @JsonProperty("totalResults") val totalResults: Int? = 0
)
    @JsonIgnoreProperties(ignoreUnknown = true)
data class RawRecipe(
    @JsonProperty("id") val id: Int,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("image") val image: String? = null,
    @JsonProperty("imageType") val imageType: String? = null
)