package edu.umuc.yourbudget.model;

import java.util.ArrayList;

public class SpendingCategories {

    public static ArrayList<String> get() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Housing/Utilities");
        categories.add("Grocery");
        categories.add("Car Payment/Insurance");
        categories.add("Gas");
        categories.add("Eating Out");
        categories.add("Entertainment");
        categories.add("Clothing");
        categories.add("Beauty");
        categories.add("Other");
        return categories;
    }

}
