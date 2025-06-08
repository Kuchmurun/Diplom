import java.util.ArrayList;

public class ErrorChecker {
public static boolean ans = false;

    public static ArrayList<String> createChecker(Wallet wallet){
        ans = false;
        ArrayList<String> errorStack = new ArrayList<>();
        Integer pass_id = wallet.getPassportId();
        String pass_id_str = pass_id.toString();
        if (wallet.getLastName().isEmpty() || wallet.getLastName().length() < 2){
            errorStack.add("Фамилия не должна быть пустой и короче 2 символов");
            ans = true;
        }
        if (wallet.getFirstName().isEmpty() || wallet.getFirstName().length() < 2){
            errorStack.add("Имя не должно быть пустым и короче 2 символов");
            ans = true;
        }
        if (wallet.getMiddleName().isEmpty() || wallet.getMiddleName().length() < 2){
            errorStack.add("Отчество не должно быть пустым и короче 2 символов");
            ans = true;
        }
        if (pass_id_str.length() != 8){
            errorStack.add("Номер паспорта не должен быть короче/длинее 8 символов или пустой");
            ans = true;
        }
        if (wallet.getRub() < 0){
            errorStack.add("Сумма рублей не может быть меньше 0 или пустым.");
            ans = true;
        }
        if (wallet.getUsd() < 0){
            errorStack.add("Сумма рублей не может быть меньше 0 или пустым.");
            ans = true;
        }
        if (wallet.getEur() < 0){
            errorStack.add("Сумма рублей не может быть меньше 0 или пустым.");
            ans = true;
        }
        return errorStack;
    }

    public static ArrayList<String> currencyChecker(ArrayList<Wallet> wallets, int id, String operation, double amount){
        ans = false;
        ArrayList<String> errorStack = new ArrayList<>();
        if (id <= 0){
            errorStack.add("Айди не может быть пустым или равен/меньше нуля.");
            ans = true;
        } else if (id > wallets.size()){
            errorStack.add("Такого айди не существует.");
            ans = true;
        } else {
            Wallet wallet = wallets.get(id - 1);
            if (Currency.isShortage(id,operation,amount)){
                errorStack.add("Недостаточно средств.");
                ans = true;
            }
        }
        return errorStack;
    }

    public static ArrayList<String> deleteChecker (ArrayList<Wallet> wallets, int id){
        ans = false;
        ArrayList<String> errorStack = new ArrayList<>();
        if (id <= 0){
            errorStack.add("Айди не может быть пустым или равен/меньше нуля.");
            ans = true;
        } else if (id > wallets.size()) {
            errorStack.add("Такого айди не существует.");
            ans = true;
        }
        return errorStack;
    }

    public static ArrayList<String> updateChecker(ArrayList<Wallet> wallets, Wallet wallet, int id){
        ans = false;
        ArrayList<String> errorStack = createChecker(wallet);
        if (id <= 0){
            errorStack.add("Айди не может быть пустым или равен/меньше нуля.");
            ans = true;
        } else if (id > wallets.size()) {
            errorStack.add("Такого айди не существует.");
            ans = true;
        }
        return errorStack;
    }

    public static ArrayList<String> fillChecker(ArrayList<Wallet>wallets,double amount,int id,String currency){
        ans = false;
        ArrayList<String> errorStack = new ArrayList<>();
        if (amount <= 0){
            errorStack.add("Сумма не должна быть отрицательной или равна нулю.");
            ans = true;
        }
        if (id <= 0){
            errorStack.add("Айди не может быть пустым или равен/меньше нуля.");
            ans = true;
        } else if (id > wallets.size()) {
            errorStack.add("Такого айди не существует.");
            ans = true;
        }
        if (currency == null ||currency.trim().isEmpty()){
            errorStack.add("Валюта не может быть пустой.");
            ans = true;
        }
        return errorStack;
    }
}
