package com.example.recipesapp

import android.util.Log
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiCalls (){
    val BASE_URL = "https://api.spoonacular.com/recipes/"
    //RetrofitBuilder for making API calls from the ApiInterface class
    private val retrofitBuilder : ApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(ApiInterface::class.java)

    //Function to get a list of recipes that include a set of ingredients
    fun getRecipesByIngredients(queries : HashMap<String, String>)
    {
        val call = retrofitBuilder.getRecipes(queries)
        call.enqueue(object : Callback<List<RecipesItem>?> {
            override fun onResponse(
                call: Call<List<RecipesItem>?>,
                response: Response<List<RecipesItem>?>
            ) {
                Log.d("MainActivity", response.body().toString())
            }

            override fun onFailure(call: Call<List<RecipesItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    //Function to get the summary of a specific recipe
    fun getSummaryByID(id: Int)
    {
        val call = retrofitBuilder.getSummary(id)

        call.enqueue(object : Callback<Summary?> {
            override fun onResponse(call: Call<Summary?>, response: Response<Summary?>) {
                Log.d("MainActivity", response.body().toString())
            }

            override fun onFailure(call: Call<Summary?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    //Function to get instructions for a specific recipe
    fun getInstructionsByID(id: Int)
    {
        val call = retrofitBuilder.getSteps(id)

        call.enqueue(object : Callback<List<AnalizedStepsItem>?> {
            override fun onResponse(
                call: Call<List<AnalizedStepsItem>?>,
                response: Response<List<AnalizedStepsItem>?>
            ) {
                Log.d("MainActivity", response.body().toString())
            }

            override fun onFailure(call: Call<List<AnalizedStepsItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure" + t.message)
            }
        })
    }

    //Function to remove the tags and unnecessary information included in the summary by default
    fun cleanSummary(summary : String) : String
    {
        var output = summary.replace("<b>", "")
        output = output.replace("</b>", "")
        output = output.replace("\\.[^\\.]+<a href=[\\S\\s]+".toRegex(), "")
        output += "."
        return output
    }
}