package com.example.teamedup.util

fun moneySuffix(money: Int) : String{
    lateinit var result : String

    if(money > 999999999){
        result = "Rp${money/1000000000}Bil"
    }else if(money > 999999){
        result = "Rp${money/1000000}Mil"
    }else if(money >999){
        result = "Rp${(money/1000)}K"
    }else result = "Rp$money"

    return result
}