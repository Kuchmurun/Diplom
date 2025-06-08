import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/delete")

public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/delete.jsp").forward(request,response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            ArrayList<Wallet> wallets = WalletDAO.readWallet();
            ArrayList<String> errors = ErrorChecker.deleteChecker(wallets,id);
            if (ErrorChecker.ans){
                request.setAttribute("errors", errors);
                getServletContext().getRequestDispatcher("/error.jsp").forward(request,response);
            } else {
                WalletDAO.deleteWallet(id);
                response.sendRedirect(request.getContextPath()+"/read");
            }
        } catch (Exception e){
            System.out.println("Ошибка: " + e);
            getServletContext().getRequestDispatcher("/delete.jsp").forward(request,response);
        }
    }
}
