import java.io.Serializable;


public class Wallet implements Serializable {
    private int id;
    private String firstName, lastName, middleName;
    private int passportId;
    private double rub, usd, eur;
    private String walletStatus;

    public Wallet(){}

    public Wallet(int id, String lastName, String firstName, String middleName, int passportId){
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.passportId = passportId;
        rub = 0;
        usd = 0;
        eur = 0;
    }
    public Wallet(int id, String lastName, String firstName,String middleName, int passportId, double rub, double usd,double eur,String wallet_status){
        this(id, lastName, firstName, middleName, passportId);
        this.rub = rub;
        this.usd = usd;
        this.eur = eur;
        this.walletStatus = wallet_status;
    }
    public Wallet(int id, String lastName, String firstName,String middleName, int passportId, double rub, double usd,double eur){
        this(id, lastName, firstName, middleName, passportId);
        this.rub = rub;
        this.usd = usd;
        this.eur = eur;
    }
    public Wallet(String lastName, String firstName, String middleName, int passportId, double rub, double usd,double eur){
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.passportId = passportId;
        this.rub = rub;
        this.usd = usd;
        this.eur = eur;
    }


    public int getId() {
        return id;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public int getPassportId() {
        return passportId;
    }
    public double getRub() {
        return rub;
    }
    public double getUsd() {
        return usd;
    }
    public double getEur() {
        return eur;
    }
    public String getWalletStatus() {
        return walletStatus;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setPassportId(int passportId) {
        this.passportId = passportId;
    }
    public void setRub(double rub) {
        this.rub = rub;
    }
    public void setUsd(double usd) {
        this.usd = usd;
    }
    public void setEur(double eur) {
        this.eur = eur;
    }
    public void setWalletStatus(String walletStatus) {
        this.walletStatus = walletStatus;
    }
}