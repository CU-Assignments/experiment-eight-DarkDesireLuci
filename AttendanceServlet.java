import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String studentName = request.getParameter("student_name");
        String course = request.getParameter("course");
        String attendanceDate = request.getParameter("attendance_date");
        String status = request.getParameter("status");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO attendance (student_name, course, attendance_date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, studentName);
            stmt.setString(2, course);
            stmt.setString(3, attendanceDate);
            stmt.setString(4, status);

            int rows = stmt.executeUpdate();
            out.println("<html><body>");
            if (rows > 0) {
                out.println("<h3>Attendance recorded successfully.</h3>");
            } else {
                out.println("<h3>Failed to record attendance.</h3>");
            }
            out.println("</body></html>");

            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            out.println("</body></html>");
        }
    }
}
