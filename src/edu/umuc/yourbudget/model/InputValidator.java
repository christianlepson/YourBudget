package edu.umuc.yourbudget.model;

/**
 * Created by Christian on 9/22/17.
 */
public class InputValidator {

    public static boolean isFirstNameValid(String name)
    {
        return name.matches("[a-zA-Z]+$")
                && name.length() >= 2;
    }

    public static boolean isUsernameValid(String username)
    {
        return username.matches("^[a-zA-Z0-9]*$")
                && username.length() >= 7
                && username.length() <= 30;
    }

    public static boolean isPasswordValid(String password) {
        return password.length() >= 10 && password.length() <= 120;
    }

    public static boolean isTextValid(String text) {
        return text.length() > 0 && text.length() <= 120;
    }

    public static boolean isCurrencyValid(String balanceString) {
        try {
            balanceString = balanceString.replaceAll(",", "");
            double amount = Double.parseDouble(balanceString);
            if (amount > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}