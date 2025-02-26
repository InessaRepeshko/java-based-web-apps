package ntukhpi.csit.semit.riv.webappsrivlab2.controller.entity.entrant;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.Range;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;

/**
 * EntrantFiltersAndSorts class is responsible for extracting and processing filtering, sorting,
 * and searching parameters from an HTTP request.
 *
 * It supports the following operations:
 * - Filtering based on entrant attributes such as case number, name, surname, patronymic, birthday range, gender, and rating score.
 * - Sorting based on specified fields with ASC or DESC order.
 * - Searching entrant by case number or surname.
 *
 * The filters, sorts, and search criteria are extracted from the request and stored in maps
 * for use in database queries.
 *
 * @author Inessa Repeshko CS-222a
 */
public class EntrantFiltersAndSorts {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private HttpServletRequest request;
    private Map<String, Object> filters = new HashMap<>();
    private Map<String, String> sortOrders = new HashMap<>();
    private Map<String, String> search = new HashMap<>();
    private Enumeration<String> parameterNames;
    private Map<String, String> attributes = new HashMap<>();


    public EntrantFiltersAndSorts(HttpServletRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }

        this.request = request;
        parameterNames = this.request.getParameterNames();
    }

    public Map<String, Object> getFiltersFromRequest() {
        setFiltersFromRequest();
        return this.filters;
    }

    public Map<String, String> getSortsFromRequest() {
        setSortsFromRequest();
        return this.sortOrders;
    }

    public Map<String, String> getSearchFromRequest() {
        setSearchFromRequest();
        return this.search;
    }

    public Map<String, String> getAttributesFromRequest() {
        setAttributesFromRequest();
        return this.attributes;
    }

    public void setFiltersFromRequest() {
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            switch (paramName) {
                case "caseNumber":
                case "surname":
                case "name":
                case "patronymic":
                    addStringFilter(paramName);
                    break;

                case "birthdayStart":
                case "birthdayEnd":
                    addBirthdayFilter();
                    break;

                case "gender":
                    addGenderFilter();
                    break;

                case "ratingScoreMin":
                case "ratingScoreMax":
                    addRatingScoreFilter();
                    break;

                case "sort":
                case "search":
                    break;

                default:
                    throw new IllegalArgumentException("Unknown parameter passed: " + paramName + ".");
            }
        }
    }

    public void setSortsFromRequest() {
        String sortParam = request.getParameter("sort");

        if (sortParam != null && !sortParam.isBlank()) {
            String[] sortParts = sortParam.split("-");

            if (sortParts.length == 2) {
                String fieldName = sortParts[0];
                String sortDirection = sortParts[1].toUpperCase();

                if (sortDirection.equals("ASC") || sortDirection.equals("DESC")) {
                    addSort(fieldName, sortDirection);
                } else {
                    throw new IllegalArgumentException("The sort direction must be 'ASC' or 'DESC'.");
                }
            } else {
                throw new IllegalArgumentException("Invalid sort parameter format '" + sortParam + "'. Expected format: 'fieldName-order'.");
            }
        }
    }

    public void setSearchFromRequest() {
        String searchParam = request.getParameter("search");

        if (searchParam != null && !searchParam.isBlank()) {
            addSearch(searchParam);
        }
    }

    public void setAttributesFromRequest() {
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            switch (paramName) {
                case "caseNumber":
                case "surname":
                case "name":
                case "patronymic":
                case "birthdayStart":
                case "birthdayEnd":
                case "gender":
                case "ratingScoreMin":
                case "ratingScoreMax":
                case "sort":
                case "search":
                    attributes.put(paramName, request.getParameter(paramName));
                    break;

                default:
                    throw new IllegalArgumentException("Unknown parameter passed: " + paramName + ".");
            }
        }
    }

    private void addStringFilter(String fieldName) {
        String filterValue = request.getParameter(fieldName);

        if (filterValue != null && !filterValue.isBlank()) {
            filters.put(fieldName, filterValue);
        } else {
            throw new IllegalArgumentException("Invalid string value passed for filtering by " + fieldName + ".");
        }
    }

    private void addBirthdayFilter() {
        String birthdayMin = request.getParameter("birthdayStart");
        String birthdayMax = request.getParameter("birthdayEnd");

        try {
            if (birthdayMin != null && !birthdayMin.isBlank() && birthdayMax != null && !birthdayMax.isBlank()) {
                LocalDate minDate = LocalDate.parse(birthdayMin, formatter);
                LocalDate maxDate = LocalDate.parse(birthdayMax, formatter);
                filters.put("birthday", Range.between(minDate, maxDate));
            } else if (birthdayMin != null && !birthdayMin.isBlank()) {
                LocalDate minDate = LocalDate.parse(birthdayMin, formatter);
                LocalDate maxDate = LocalDate.now();
                filters.put("birthday", Range.between(minDate, maxDate));
            } else if (birthdayMax != null && !birthdayMax.isBlank()) {
                LocalDate minDate = LocalDate.now().minusYears(110);
                LocalDate maxDate = LocalDate.parse(birthdayMax, formatter);
                filters.put("birthday", Range.between(minDate, maxDate));
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid date value passed for filtering by birthday.");
        }
    }

    private void addGenderFilter() {
        String filterValue = request.getParameter("gender");

        if (filterValue != null
                && !filterValue.isBlank()
                && (filterValue.equalsIgnoreCase("TRUE")
                || filterValue.equalsIgnoreCase("FALSE"))) {
            filters.put("gender", Boolean.parseBoolean(filterValue));
        } else {
            throw new IllegalArgumentException("Invalid string value passed for filtering by gender.");
        }
    }

    private void addRatingScoreFilter() {
        String ratingScoreMin = request.getParameter("ratingScoreMin");
        String ratingScoreMax = request.getParameter("ratingScoreMax");

        try {
            if (ratingScoreMin != null && ratingScoreMax != null && !ratingScoreMin.isBlank() && !ratingScoreMax.isBlank()) {
                double min = Double.parseDouble(ratingScoreMin);
                double max = Double.parseDouble(ratingScoreMax);
                filters.put("ratingScore", Range.between(min, max));
            } else if (ratingScoreMin != null && !ratingScoreMin.isBlank()) {
                double min = Double.parseDouble(ratingScoreMin);
                double max = Double.valueOf(200.000);
                filters.put("ratingScore", Range.between(min, max));
            } else if (ratingScoreMax != null && !ratingScoreMax.isBlank()) {
                double min = Double.valueOf(120.001);
                double max = Double.parseDouble(ratingScoreMax);
                filters.put("ratingScore", Range.between(min, max));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Invalid double value passed for filtering by rating score.");
        }
    }

    private void addSort(String fieldName, String sortOrder) {
        switch (fieldName) {
            case "caseNumber":
            case "surname":
            case "name":
            case "patronymic":
            case "birthday":
            case "gender":
            case "ratingScore":
                sortOrders.put(fieldName, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Unknown field name passed for sorting: " + fieldName + ".");
        }
    }

    private void addSearch(String value) {
        String string = value.trim();
        search.put("caseNumber", string);
        search.put("surname", string);
    }
}
