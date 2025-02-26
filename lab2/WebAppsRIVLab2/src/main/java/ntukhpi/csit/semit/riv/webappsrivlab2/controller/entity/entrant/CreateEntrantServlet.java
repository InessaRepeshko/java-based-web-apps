package ntukhpi.csit.semit.riv.webappsrivlab2.controller.entity.entrant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.DAO.EntrantDAO;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;

import java.io.IOException;

/**
 * CreateEntrantServlet class handles requests for creating a new entrant record.
 * It processes both GET and POST requests for the "/entrants/create" URL.
 *
 * The servlet supports the following operations:
 * - For GET requests, it displays the form for creating a new entrant by forwarding to the EntrantForm.jsp view.
 * - For POST requests, it captures the form data, creates a new entrant, and inserts it into the database.
 * After successful insertion, the user is redirected to the entrant's update page.
 *
 * This servlet interacts with the {@link EntrantDAO} class to perform database operations.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "CreateEntrantServlet", value = "/entrants/create")
public class CreateEntrantServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/entrant/EntrantForm.jsp";
    private static final EntrantDAO operations = new EntrantDAO();
    private static final String REQUEST_TYPE = "CREATE";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("requestType", REQUEST_TYPE);
        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(VIEW_PATH);
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Entrant entrantToInsert = new Entrant(
                request.getParameter("caseNumber"),
                request.getParameter("surname"),
                request.getParameter("name"),
                request.getParameter("patronymic"),
                request.getParameter("birthday"),
                request.getParameter("gender"),
                request.getParameter("ratingScore")
        );

        Entrant insertedEntrant = operations.insert(entrantToInsert);

        response.setStatus(HttpServletResponse.SC_CREATED);
        response.sendRedirect(request.getContextPath() + "/entrants/" + insertedEntrant.getId().toString() + "/update");
    }
}
