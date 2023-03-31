package uber.uber.utils;

import lombok.Data;
import uber.uber.exceptions.RegistrationException;


@Data
public class ValidateDetails {

    public static boolean isValidEmail(String email){
       return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidPassword(String password){
      return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

    public static boolean isValidPhoneNumber(String phoneNumber){
        for (int i = 0; i < phoneNumber.length(); i++) {
            if(!Character.isDigit(phoneNumber.charAt(i))) throw new RegistrationException("PHONE NUMBER MUST BE DIGIT");
        }
        return (phoneNumber.length() == 11);
    }

//    public static boolean isValidPhoneNumber(String phoneNumber) {
//        return phoneNumber.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
//    }

//    public static boolean isValidPhoneNumber(String phoneNumber){
//        return phoneNumber.matches("^(\\+234|234|0)(701|702|703|704|705|706|707|708|709|802|803|804|805|806|807|808|809|810|811|812|813|814|815|816|817|818|819|909|908|901|902|903|904|905|906|907)([0-9]{7})$");
//    }




}
