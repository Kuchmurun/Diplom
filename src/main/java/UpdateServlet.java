import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/update")
public class UpdateServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/update.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String middle_name = request.getParameter("middle_name");
            String passport_str = request.getParameter("passport_id");
            int passport_id = (passport_str != null && passport_str.matches("\\d+")) ? Integer.parseInt(passport_str) : 0;

            String rub_str = request.getParameter("rub");
            double rub = (rub_str != null && !rub_str.isEmpty()) ? Double.parseDouble(rub_str) : -1;

            String usd_str = request.getParameter("usd");
            double usd = (usd_str != null && !usd_str.isEmpty()) ? Double.parseDouble(usd_str) : -1;

            String eur_str = request.getParameter("eur");
            double eur = (eur_str != null && !eur_str.isEmpty()) ? Double.parseDouble(eur_str) : -1;

            String id_str = request.getParameter("id");
            int id = (id_str != null && !id_str.isEmpty()) ? Integer.parseInt(id_str) : -1;
            ArrayList<Wallet> wallets = WalletDAO.readWallet();
            Wallet wallet = new Wallet(id,last_name,first_name,middle_name,passport_id,rub,usd,eur);
            ArrayList<String> errors = ErrorChecker.updateChecker(wallets,wallet,id);
            if (ErrorChecker.ans){
                request.setAttribute("errors", errors);
                getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            } else {
                WalletDAO.updateWallet(wallet, id);
                response.sendRedirect(request.getContextPath()+"/read");
            }
        } catch (Exception e){
            System.out.println("Ошибка: " + e);
            getServletContext().getRequestDispatcher("/update.jsp").forward(request,response);
        }
    }
}
