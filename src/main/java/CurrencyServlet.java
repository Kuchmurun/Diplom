import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/currency")

public class CurrencyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/currency_exchange.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Wallet> wallets = WalletDAO.readWallet();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String operation = request.getParameter("operation");
            double amount = Double.parseDouble(request.getParameter("amount"));
            ArrayList <String> errors = ErrorChecker.currencyChecker(wallets,id,operation,amount);
            if (ErrorChecker.ans){
                request.setAttribute("errors", errors);
                getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            } else {
                WalletDAO.currencyExchange(wallets.get(id - 1), id, amount, operation);
                response.sendRedirect(request.getContextPath()+"/read");
            }
        } catch (Exception e){
            System.out.println("Ошибка: " + e);
            getServletContext().getRequestDispatcher("/currency_exchange.jsp").forward(request,response);
        }
    }
}
