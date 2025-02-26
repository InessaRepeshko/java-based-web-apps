package ntukhpi.csit.semit.riv.webappsrivlab2.controller.entity.entrant;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.DAO.EntrantDAO;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.Entrant;
import ntukhpi.csit.semit.riv.webappsrivlab2.model.entity.entrant.EntrantList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * EntrantTableServlet class handles requests for displaying a table of entrant.
 * It allows for filtering, sorting, and searching entrant based on parameters passed in the request.
 *
 * The servlet supports the following features:
 * - Initial loading of entrant into the database during servlet initialization.
 * - Displaying all entrant or a filtered and sorted list based on request parameters.
 * - Forwarding the request to the EntrantTable.jsp view to display the entrant in a table format.
 *
 * This servlet interacts with {@link EntrantDAO} to retrieve entrant from the database and
 * {@link EntrantFiltersAndSorts} to process filters, sorts, and search criteria from the request.
 *
 * @author Inessa Repeshko CS-222a
 */
@WebServlet(name = "EntrantTableServlet", value = "/entrants")
public class EntrantTableServlet extends HttpServlet {
    private static final String VIEW_PATH = "/view/entrant/EntrantTable.jsp";
    private static final EntrantDAO operations = new EntrantDAO();
    private EntrantFiltersAndSorts filtersAndSortsGetter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        List<Entrant> initialEntrantList = new EntrantList().getEntrants();
        operations.insertAll(initialEntrantList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Entrant> entrantList;

        filtersAndSortsGetter = new EntrantFiltersAndSorts(request);
        Map<String, Object> filters = null;
        Map<String, String> sortOrders = null;
        Map<String, String> search = null;

        String[] currentSort = null;

        if (request.getParameterNames().hasMoreElements()) {
            try {
                filters = filtersAndSortsGetter.getFiltersFromRequest();
                sortOrders = filtersAndSortsGetter.getSortsFromRequest();
                search = filtersAndSortsGetter.getSearchFromRequest();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }

            entrantList = operations.getFilteredAndSortedList(filters, sortOrders, search);
        } else {
            entrantList = operations.getAllList();
        }

        request.setAttribute("entrantList", entrantList);

        Entrant entrant = new Entrant();
        List<String[]> fieldNames = entrant.getFieldNamesAsFormattedString();
        request.setAttribute("fieldNames", fieldNames);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(VIEW_PATH);
        requestDispatcher.forward(request, response);
    }
}
