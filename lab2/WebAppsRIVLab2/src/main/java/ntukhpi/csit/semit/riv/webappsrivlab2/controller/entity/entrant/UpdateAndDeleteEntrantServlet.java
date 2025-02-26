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
 * UpdateAndDeleteEntrantServlet class handles requests to update or delete an entrant record.
 * It processes both GET and POST requests for URLs matching the pattern "/entrants/*".
 *
 * The servlet supports the following operations:
 * - Update an entrant when the URL matches "/{id}/update".
 * - Delete an entrant when the URL matches "/{id}/delete".
 *
 * For GET requests, it retrieves the entrant information based on the provided ID and forwards
 * the data to the EntrantForm.jsp view for further actions.
 * For POST requests, it either updates or deletes the entrant based on the URL pattern.
 *
 * If the entrant is not found or an error occurs, the servlet redirects to a 404 page.
 *
 * This servlet interacts with the {@link EntrantDAO} class to perform database operations.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "UpdateAndDeleteEntrantServlet", value = "/entrants/*")
public class UpdateAndDeleteEntrantServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/entrant/EntrantForm.jsp";
    private static final EntrantDAO operations = new EntrantDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String req_type = "GET";
        String pathInfo = request.getPathInfo();
        ServletContext servletContext = getServletContext();

        try {
            if (pathInfo != null
                    && (pathInfo.matches("/\\d+/update")
                        || pathInfo.matches("/\\d+/delete")) ) {
                if (pathInfo.matches("/\\d+/update")) {
                    req_type = "UPDATE";
                } else if (pathInfo.matches("/\\d+/delete")) {
                    req_type = "DELETE";
                }

                String idFromURL = pathInfo.split("/")[1];
                Long idToFind = Long.parseLong(idFromURL);

                Entrant foundEntrant = operations.findById(idToFind);

                if (foundEntrant != null) {
                    request.setAttribute("requestType", req_type);
                    request.setAttribute("entrant", foundEntrant);
                    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(VIEW_PATH);
                    requestDispatcher.forward(request, response);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.sendRedirect(request.getContextPath() + "/page-not-found-error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        Entrant entrantToUpdate = new Entrant(
                request.getParameter("id"),
                request.getParameter("caseNumber"),
                request.getParameter("surname"),
                request.getParameter("name"),
                request.getParameter("patronymic"),
                request.getParameter("birthday"),
                request.getParameter("gender"),
                request.getParameter("ratingScore")
        );

        if (pathInfo != null) {
            if (pathInfo.matches("/\\d+/update")) {
                Entrant updatedEntrant = operations.update(entrantToUpdate);

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.sendRedirect(request.getContextPath() + "/entrants/" + updatedEntrant.getId().toString() + "/update");
            }

            else if (pathInfo.matches("/\\d+/delete")) {
                boolean isDeleted = operations.delete(entrantToUpdate);

                response.setStatus(HttpServletResponse.SC_CREATED);
                response.sendRedirect(request.getContextPath() + "/entrants");
            }
        }
    }
}
