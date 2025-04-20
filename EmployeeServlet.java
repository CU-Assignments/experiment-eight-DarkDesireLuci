import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String searchId = request.getParameter("id");

        out.println("<html><body>");
        out.println("<form method='get'>");
        out.println("Search Employee by ID: <input type='text' name='id'/>");
        out.println("<input type='submit' value='Search'/></form><hr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs;

            if (searchId != null && !searchId.trim().isEmpty()) {
                rs = stmt.executeQuery("SELECT * FROM employees WHERE id = " + searchId);
                if (rs.next()) {
                    out.println("<h3>Employee Details</h3>");
                    out.println("ID: " + rs.getInt("id") + "<br>");
                    out.println("Name: " + rs.getString("name") + "<br>");
                    out.println("Position: " + rs.getString("position") + "<br>");
                    out.println("Department: " + rs.getString("department") + "<br>");
                } else {
                    out.println("<p>No employee found with ID: " + searchId + "</p>");
                }
            }

            rs = stmt.executeQuery("SELECT * FROM employees");
            out.println("<h3>All Employees</h3>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Position</th><th>Department</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("position") + "</td>");
                out.println("<td>" + rs.getString("department") + "</td></tr>");
            }
            out.println("</table>");

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }
}
