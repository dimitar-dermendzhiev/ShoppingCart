package com.example.android.myapplication.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Product> products;

    public ShoppingCart (){
        products = new ArrayList<>();
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public int calculateAppleQuantity(){
        int totalApples = 0;
        for (Product product : products){
            if (product instanceof Apple){
                totalApples++;
            }
        }
        return totalApples;
    }

    public int calculateOrangeQuantity(){
        int totalOranges = 0;
        for (Product product : products){
            if (product instanceof Orange){
                totalOranges++;
            }
        }
        return totalOranges;
    }
}
