import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Currency {
    public static double plus_count = 0;
    public static double minus_count = 0;
    public static boolean shortage = false;
    private static final Map<String, Double> currency = new HashMap<>();
    static {
        currency.put("rub_to_usd", 0.012);
        currency.put("rub_to_eur", 0.0111);
        currency.put("usd_to_rub", 80.31);
        currency.put("usd_to_eur", 0.89);
        currency.put("eur_to_rub",90.1);
        currency.put("eur_to_usd", 1.12);
    }
    public static double getCurrency(String operation){
        return currency.getOrDefault(operation, 0.0);
    }
    public static String getPlus(String operation){
        String str = "";
        if (operation.endsWith("usd")){
            str = "usd";
        } else if (operation.endsWith("eur")) {
            str = "eur";
        } else if (operation.endsWith("rub")) {
            str = "rub";
        }
        return str;
    }
    public static String getMinus(String operation){
        String str = "";
        if (operation.startsWith("rub")){
            str = "rub";
        } else if (operation.startsWith("usd")) {
            str = "usd";
        } else if (operation.startsWith("eur")) {
            str = "eur";
        }
        return str;
    }
    public static void calculateExchange(int id, String operation, double amount){
        ArrayList<Wallet> wallets = WalletDAO.readWallet();
        double currency = getCurrency(operation);
        String plus = getPlus(operation);
        String minus = getMinus(operation);

        if (plus.equals("rub")){
            plus_count = wallets.get(id - 1).getRub();
        } else if (plus.equals("usd")) {
            plus_count = wallets.get(id - 1).getUsd();
        } else if (plus.equals("eur")) {
            plus_count = wallets.get(id - 1).getEur();
        }

        if (minus.equals("rub")){
            minus_count = wallets.get(id - 1).getRub();
        } else if (minus.equals("usd")) {
            minus_count = wallets.get(id - 1).getUsd();
        }
        else if (minus.equals("eur")) {
            minus_count = wallets.get(id - 1).getEur();
        }

        if (minus_count < amount){
            shortage = true;
        } else {
            plus_count += amount * currency;
            System.out.println(plus_count);
            minus_count -= amount;
            if (operation.equals("rub_to_usd")){
                wallets.get(id - 1).setUsd(plus_count);
                wallets.get(id - 1).setRub(minus_count);
            }
            if (operation.equals("rub_to_eur")){
                wallets.get(id - 1).setEur(plus_count);
                wallets.get(id - 1).setRub(minus_count);
            }
            if (operation.equals("usd_to_rub")){
                wallets.get(id - 1).setRub(plus_count);
                wallets.get(id - 1).setUsd(minus_count);
            }
            if (operation.equals("usd_to_eur")){
                wallets.get(id - 1).setEur(plus_count);
                wallets.get(id - 1).setUsd(minus_count);
            }
            if (operation.equals("eur_to_rub")){
                wallets.get(id - 1).setRub(plus_count);
                wallets.get(id - 1).setEur(minus_count);
            }
            if (operation.equals("eur_to_usd")){
                wallets.get(id - 1).setUsd(plus_count);
                wallets.get(id - 1).setEur(minus_count);
            }
        }
    }
    public static boolean isShortage(int id, String operation, double amount){
        ArrayList<Wallet> wallets = WalletDAO.readWallet();
        String plus = getPlus(operation);
        String minus = getMinus(operation);
        boolean shortage = false;
        if (plus.equals("rub")){
            plus_count = wallets.get(id - 1).getRub();
        } else if (plus.equals("usd")) {
            plus_count = wallets.get(id - 1).getUsd();
        } else if (plus.equals("eur")) {
            plus_count = wallets.get(id - 1).getEur();
        }

        if (minus.equals("rub")){
            minus_count = wallets.get(id - 1).getRub();
        } else if (minus.equals("usd")) {
            minus_count = wallets.get(id - 1).getUsd();
        }
        else if (minus.equals("eur")) {
            minus_count = wallets.get(id - 1).getEur();
        }

        if (minus_count < amount){
            shortage = true;
        }
        return shortage;
    }
}