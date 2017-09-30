package edu.umuc.yourbudget.model;

/**
 * Created by Christian on 9/22/17.
 */
public class InputValidator {

    public static boolean isFirstNameValid(String name)
    {
        return name.matches("[a-zA-Z]+$");
    }

    public static boolean isUsernameValid(String username)
    {
        return username.matches("[a-zA-Z]+$") && username.length() <= 7;
    }

}