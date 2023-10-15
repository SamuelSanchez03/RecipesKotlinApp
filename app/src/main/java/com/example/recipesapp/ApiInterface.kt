package com.example.recipesapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiInterface {
    //API key = 0419d9cd6d75420aae0691315b632fb7
    //API call for getting recipes according to a list of ingredients
    //queries map requires a string of ingredients separated by comas
    //and a number as string, representing the maximum number of recipes the call will return
    //Visit https://spoonacular.com/food-api/docs#Search-Recipes-by-Ingredients for full documentation
    @GET("findByIngredients?apiKey=0419d9cd6d75420aae0691315b632fb7")
    fun getRecipes (@QueryMap queries: HashMap<String, String>): Call<List<RecipesItem>>

    //API call for getting the summary of a specific recipe
    //ID can be gotten by the previous API call
    //Visit https://spoonacular.com/food-api/docs#Summarize-Recipe for full documentation
    @GET("{id}/summary?apiKey=0419d9cd6d75420aae0691315b632fb7")
    fun getSummary(@Path("id") id:Int): Call<Summary>

    //API call for getting the instructions from a specific recipe
    //The instructions include description, equipment and ingredient
    //ID can be gotten by the first API call
    //Visit https://spoonacular.com/food-api/docs#Get-Analyzed-Recipe-Instructions for full documentation
    @GET("{id}/analyzedInstructions?apiKey=0419d9cd6d75420aae0691315b632fb7")
    fun getSteps(@Path("id") id:Int): Call<List<AnalizedStepsItem>>
}