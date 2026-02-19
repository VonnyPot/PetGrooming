// Made by Abiud Emanuel Ramos Ruiz 02/18/2026
package com.example.petgroomv2;

import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Row;
import com.healthmarketscience.jackcess.Table;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin", "/Admin Login", "/AdminLogin"})
public class AdminServlet extends HttpServlet {

    private File dbFile;

    @Override
    public void init() throws ServletException {
        dbFile = resolveDbFileOrThrow();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = valueOrDefault(request.getParameter("action"), "dashboard");

        if ("logout".equalsIgnoreCase(action)) {
            HttpSession s = request.getSession(false);
            if (s != null) s.invalidate();
            response.sendRedirect(request.getContextPath() + "/AdminLogin.jsp");
            return;
        }

        if (!isAdmin(request)) {
            response.sendRedirect(request.getContextPath() + "/AdminLogin.jsp");
            return;
        }

        if ("appointments".equalsIgnoreCase(action)) {
            renderAppointmentsReadOnly(response, request);
        } else {
            renderDashboard(response, request);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idOrEmail = firstNonBlank(
                request.getParameter("adminID"),
                request.getParameter("id"),
                request.getParameter("email")
        );

        String password = firstNonBlank(
                request.getParameter("password"),
                request.getParameter(" password") // supports Vonny's original field name with leading space
        );

        if (isBlank(idOrEmail) || isBlank(password)) {
            response.sendRedirect(request.getContextPath() + "/AdminLogin.jsp?err=1");
            return;
        }

        try {
            AdminRecord admin = authenticateAdmin(idOrEmail.trim(), password);
            if (admin == null) {
                response.sendRedirect(request.getContextPath() + "/AdminLogin.jsp?err=1");
                return;
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("isAdmin", true);
            session.setAttribute("adminName", admin.fullName);
            session.setAttribute("adminRole", admin.role);

            response.sendRedirect(request.getContextPath() + "/admin");
        } catch (Exception ex) {
            response.sendRedirect(request.getContextPath() + "/AdminLogin.jsp?err=db");
        }
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        return s != null && Boolean.TRUE.equals(s.getAttribute("isAdmin"));
    }

    private AdminRecord authenticateAdmin(String idOrEmail, String password) throws IOException {
        try (Database db = DatabaseBuilder.open(dbFile)) {
            Table adminTable = findTableByColumns(db, Set.of("adminid", "password", "isactive"));
            if (adminTable == null) return null;

            Map<String, String> col = columnMap(adminTable);

            String cAdminId = col.get("adminid");
            String cPass = col.get("password");
            String cActive = col.get("isactive");

            String cEmail = col.get("email");       // optional
            String cFirst = col.get("firstname");   // optional
            String cLast = col.get("lastname");     // optional
            String cRole = col.get("role");         // optional

            for (Row r : adminTable) {
                String rowAdminId = toStr(r.get(cAdminId));
                String rowEmail = (cEmail != null) ? toStr(r.get(cEmail)) : "";
                String rowPass = toStr(r.get(cPass));
                String rowActive = toStr(r.get(cActive));

                boolean idMatch = idOrEmail.equalsIgnoreCase(rowAdminId.trim())
                        || (!isBlank(rowEmail) && idOrEmail.equalsIgnoreCase(rowEmail.trim()));

                boolean passMatch = password.equals(rowPass);

                String a = rowActive.trim();
                boolean active = "yes".equalsIgnoreCase(a) || "true".equalsIgnoreCase(a) || "1".equals(a);

                if (idMatch && passMatch && active) {
                    String first = (cFirst != null) ? toStr(r.get(cFirst)) : "";
                    String last = (cLast != null) ? toStr(r.get(cLast)) : "";
                    String fullName = (first + " " + last).trim();
                    if (fullName.isEmpty()) fullName = idOrEmail;

                    String role = (cRole != null) ? toStr(r.get(cRole)) : "Admin";
                    return new AdminRecord(fullName, role);
                }
            }
            return null;
        }
    }

    private void renderDashboard(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession s = request.getSession(false);
        String name = valueOrDefault((String) s.getAttribute("adminName"), "Admin");
        String role = valueOrDefault((String) s.getAttribute("adminRole"), "Admin");

        out.println("<!DOCTYPE html><html><head><title>Admin</title></head><body>");
        out.println("<h1>Admin Dashboard</h1>");
        out.println("<p>Welcome, <b>" + esc(name) + "</b> (" + esc(role) + ")</p>");

        out.println("<ul>");
        out.println("<li><a href='" + request.getContextPath() + "/admin?action=appointments'>View Appointments (Read-Only)</a></li>");
        out.println("<li><a href='" + request.getContextPath() + "/admin?action=logout'>Logout</a></li>");
        out.println("</ul>");

        out.println("</body></html>");
    }

    private void renderAppointmentsReadOnly(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Appointments</title></head><body>");
        out.println("<h1>Appointments (Read-Only)</h1>");
        out.println("<p><a href='" + request.getContextPath() + "/admin'>Back</a> | " +
                "<a href='" + request.getContextPath() + "/admin?action=logout'>Logout</a></p>");

        try (Database db = DatabaseBuilder.open(dbFile)) {
            Table apptTable = findTableByColumns(db, Set.of(
                    "appointmentid", "customerid", "petid", "serviceid",
                    "appointmentdate", "starttime", "endtime", "totalprice", "status"
            ));

            if (apptTable == null) {
                out.println("<p>Appointment table not found.</p></body></html>");
                return;
            }

            Map<String, String> col = columnMap(apptTable);

            out.println("<table border='1' cellpadding='6' cellspacing='0'>");
            out.println("<tr>"
                    + "<th>AppointmentID</th><th>CustomerID</th><th>PetID</th><th>ServiceID</th>"
                    + "<th>Date</th><th>Start</th><th>End</th><th>Total</th><th>Status</th>"
                    + "</tr>");

            for (Row r : apptTable) {
                out.println("<tr>");
                out.println("<td>" + esc(toStr(r.get(col.get("appointmentid")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("customerid")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("petid")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("serviceid")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("appointmentdate")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("starttime")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("endtime")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("totalprice")))) + "</td>");
                out.println("<td>" + esc(toStr(r.get(col.get("status")))) + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        } catch (Exception ex) {
            out.println("<p>Database error reading appointments.</p>");
        }

        out.println("</body></html>");
    }

    private File resolveDbFileOrThrow() throws ServletException {
        String realDbDir = getServletContext().getRealPath("/WEB-INF/db");
        if (realDbDir != null) {
            File dir = new File(realDbDir);
            if (dir.exists() && dir.isDirectory()) {
                File[] accdb = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".accdb"));
                if (accdb != null && accdb.length > 0) return accdb[0];
            }
        }
        throw new ServletException("Access DB not found. Place your .accdb under /WEB-INF/db/");
    }

    private Table findTableByColumns(Database db, Set<String> requiredNormalized) throws IOException {
        for (String tableName : db.getTableNames()) {
            Table t = db.getTable(tableName);
            Set<String> cols = new HashSet<>();
            t.getColumns().forEach(c -> cols.add(normalize(c.getName())));
            if (cols.containsAll(requiredNormalized)) return t;
        }
        return null;
    }

    private Map<String, String> columnMap(Table table) {
        Map<String, String> map = new HashMap<>();
        table.getColumns().forEach(c -> map.put(normalize(c.getName()), c.getName()));
        return map;
    }

    private static String normalize(String s) {
        if (s == null) return "";
        return s.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    private static String toStr(Object o) { return (o == null) ? "" : String.valueOf(o); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
    private static String valueOrDefault(String s, String fallback) { return isBlank(s) ? fallback : s; }

    private static String firstNonBlank(String... values) {
        for (String v : values) if (!isBlank(v)) return v;
        return null;
    }

    private static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    private static final class AdminRecord {
        final String fullName;
        final String role;

        AdminRecord(String fullName, String role) {
            this.fullName = fullName;
            this.role = role;
        }
    }
}