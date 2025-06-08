import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/fill")

public class FillWallet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/fill.jsp").forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Wallet> wallets = WalletDAO.readWallet();
        try {
            String amount_str = request.getParameter("amount");
            double amount = (amount_str != null && !amount_str.isEmpty()) ? Double.parseDouble(amount_str) : -1;
            String id_str = request.getParameter("id");
            int id = (id_str != null && !id_str.isEmpty()) ? Integer.parseInt(id_str) : -1;
            String currency = request.getParameter("currency");
            ArrayList<String> errors = ErrorChecker.fillChecker(wallets,amount,id,currency);
            if (ErrorChecker.ans){
                request.setAttribute("errors", errors);
                getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            } else {
                WalletDAO.updateBalance(wallets.get(id - 1),id,amount,currency);
                response.sendRedirect(request.getContextPath()+"/read");
            }
        } catch (Exception e){
            System.out.println("Ошибка: " + e);
            getServletContext().getRequestDispatcher("/fill.jsp").forward(request,response);
        }
    }
}
