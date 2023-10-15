package com.example.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variable that allows us to make API calls
        val caller = ApiCalls()

        //Function testing
        var queries = HashMap<String, String>()
        queries["ingredients"] = "apples,sugar,flour"
        queries["number"] = "5"

        caller.getRecipesByIngredients(queries)
        caller.getSummaryByID(4632)
        caller.getInstructionsByID(4632)
    }
}