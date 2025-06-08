
import java.sql.*;
import java.util.ArrayList;

public class WalletDAO {
    private static final String url = "some_url";
    private static final String user = "some_user";
    private static final String password = "some_password";

    public static void createWallet (Wallet wallet){
        String query = "INSERT INTO wallets(last_name, first_name, middle_name, passport_id, rub, usd, eur, wallet_status) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(url,user,password)){
            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setString(1, wallet.getLastName());
                statement.setString(2, wallet.getFirstName());
                statement.setString(3, wallet.getMiddleName());
                statement.setInt(4, wallet.getPassportId());
                statement.setDouble(5, wallet.getRub());
                statement.setDouble(6, wallet.getUsd());
                statement.setDouble(7, wallet.getEur());
                String wallStatus = "Доступен";
                statement.setString(8, wallStatus);

                statement.executeUpdate();
            } catch (SQLException e){
                System.out.println("Ошибка при вставке: " + e);
            }
            query = "SELECT SETVAL('wallets_id_seq', (SELECT MAX(id) FROM wallets));";
            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.executeQuery();
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
    }
    public static ArrayList<Wallet> readWallet (){


        String query = "SELECT * FROM wallets;";
        ArrayList<Wallet> wallets = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection conn = DriverManager.getConnection(url,user,password)){
            try (PreparedStatement statement = conn.prepareStatement(query)){
                try (ResultSet set = statement.executeQuery()){
                    while(set.next()){
                        int id = set.getInt(1);
                        String last_name = set.getString(2);
                        String first_name= set.getString(3);
                        String middle_name = set.getString(4);
                        int passport_id = set.getInt(5);
                        double rub = set.getDouble(6);
                        double usd = set.getDouble(7);
                        double eur = set.getDouble(8);
                        String wallet_status = set.getString(9);
                        Wallet wall = new Wallet(id,last_name,first_name,middle_name,passport_id,rub,usd,eur,wallet_status);
                        wallets.add(wall);
                    }
                }
            } catch (SQLException e){
                System.out.println("Ошибка чтения: " + e);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
        return wallets;
    }

    public static void updateWallet(Wallet wallet, int id){
        String query = "UPDATE wallets SET last_name=?,first_name=?, middle_name=?, passport_id=?, rub=?, usd=?, eur=? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url,user,password)){
            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setString(1, wallet.getLastName());
                statement.setString(2, wallet.getFirstName());
                statement.setString(3, wallet.getMiddleName());
                statement.setInt(4, wallet.getPassportId());
                statement.setDouble(5, wallet.getRub());
                statement.setDouble(6, wallet.getUsd());
                statement.setDouble(7, wallet.getEur());
                statement.setInt(8, id);

                statement.executeUpdate();
            } catch (SQLException e){
                System.out.println("Ошибка при обновлении: " + e);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
    }

    public static void updateBalance(Wallet wallet, int id, double amount, String currency){
        String query = "UPDATE wallets SET " + currency + " = ? " + "WHERE id = ?" ;
        try (Connection conn = DriverManager.getConnection(url,user,password)){
            System.out.println("Подключение...");
            try (PreparedStatement statement = conn.prepareStatement(query)){
                double amountDB = 0;
                if (currency.equals("rub")){
                    amountDB = wallet.getRub();
                } else if (currency.equals("usd")) {
                    amountDB = wallet.getUsd();
                } else if (currency.equals("eur")) {
                    amountDB = wallet.getEur();
                }

                amount += amountDB;
                statement.setDouble(1,amount);
                statement.setInt(2,id);
                statement.executeUpdate();
            } catch (SQLException e){
                System.out.println("Ошибка при обновлении: " + e);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
    }

    public static void currencyExchange (Wallet wallet, int id, double amount, String operation){

        String plusCurrency = Currency.getPlus(operation);
        String minusCurrency = Currency.getMinus(operation);

        String query= "UPDATE wallets SET " + plusCurrency + " = ?, " + minusCurrency + " = ?" + " WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url,user,password)){
            try (PreparedStatement statement = conn.prepareStatement(query)){
                Currency.calculateExchange(id,operation,amount);
                statement.setDouble(1, Currency.plus_count);
                statement.setDouble(2, Currency.minus_count);
                statement.setInt(3, id);
                statement.executeUpdate();
            } catch (SQLException e){
                System.out.println("Ошибка запроса: " + e);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
    }

    public static void deleteWallet(int id){

        String query = "DELETE FROM wallets WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url,user,password)){
            try (PreparedStatement statement = conn.prepareStatement(query)){
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e){
                System.out.println("Ошибка при удалении: " + e);
            }
        } catch (SQLException e){
            System.out.println("Ошибка при подключении: " + e);
        }
    }
}
