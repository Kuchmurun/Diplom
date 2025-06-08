import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

@WebServlet("/read")
public class  IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        ArrayList<Wallet> wallets = WalletDAO.readWallet();
        wallets.sort(Comparator.comparing(Wallet::getId));
        System.out.println("Чтение...");
        request.setAttribute("wallets", wallets);

        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
