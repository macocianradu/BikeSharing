package com.crud.bikeshare.bikeshare.Validators;

import com.crud.bikeshare.bikeshare.Model.User;

public class UserValidaor {
    public static String validate(User user){
        if(hasNumbers(user.getName())){
            return "Name cannot contain numbers";
        }
//        if(emailValidator(user.getEmail())){
//            return "email not valid";
//        }

        if(!user.getPassword().equals(user.getRepeatPassword())){
            System.out.println(user.getPassword() + " :: " + user.getRepeatPassword());
            return "Passwords do not match";
        }
        return "success";
    }

    private static boolean hasNumbers(String string){
        for(char c : string.toCharArray()){
            if("0 1 2 3 4 5 6 7 8 9".indexOf(c) != -1){
                return true;
            }
        }
        return false;
    }

    private static boolean emailValidator(String email){
        char[] aux = email.toCharArray();
        for(int i = 1; i <= aux.length; i++){
            if(aux[i] == '@'){
                for(int j = i + 1; j < aux.length; j++){
                    if (aux[j] == '.'){
                        String termination = new String(aux, j, aux.length-j);
                        switch (termination){
                            case "com": return true;
                            case "co.uk": return true;
                            case "ro": return true;
                            case "de": return true;
                            case "ru": return true;
                            case "net": return true;
                            case "org": return true;
                            case "us": return true;
                            default: return false;
                        }
                    }
                }
            }
        }
        return false;
    }
}
