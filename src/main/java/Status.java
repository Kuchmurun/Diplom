import java.util.HashSet;

public class Status {

    private static final HashSet<String> status = new HashSet<>();
    static {
        status.add("Доступен");
        status.add("Неактивен");
        status.add("Заблокирован");
    }
    public static HashSet<String> getStatus(){
        return status;
    }
}
