package murach.survey;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import murach.business.User;
import murach.business.Survey;
import murach.data.SurveyDB;

public class SurveyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) 
                          throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String url = "/index.html";

        // Lấy action từ form (mặc định là "join")
        String action = request.getParameter("action");
        if (action == null) {
            action = "join";
        }

        if (action.equals("join")) {
            url = "/index.html";
        } 
        else if (action.equals("add")) {
            // Nhận thông tin cá nhân (User)
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String birthDate = request.getParameter("birthDate");

            // Nhận thông tin khảo sát & tuỳ chọn liên hệ (Survey)
            String heardFrom = request.getParameter("heardFrom");
            String wantsUpdates = request.getParameter("wantsUpdates");
            String emailAnnouncements = request.getParameter("emailAnnouncements");
            String contactVia = request.getParameter("contactVia");

            // Xử lý giá trị checkbox
            if (wantsUpdates == null) {
                wantsUpdates = "No";
            } else {
                wantsUpdates = "Yes";
            }

            if (emailAnnouncements == null) {
                emailAnnouncements = "No";
            } else {
                emailAnnouncements = "Yes";
            }

            if (heardFrom == null) {
                heardFrom = "NA";
            }

            // Tạo các đối tượng Entity
            User user = new User(firstName, lastName, email, birthDate);
            Survey survey = new Survey(user, heardFrom, wantsUpdates, emailAnnouncements, contactVia);

            // Lưu dữ liệu vào CSDL PostgreSQL thông qua JPA
            try {
                SurveyDB.insert(survey);
            } catch (Exception e) {
                System.err.println("Cảnh báo: Không thể kết nối hoặc ghi vào DB: " + e.getMessage());
            }

            // Lưu đối tượng vào request scope để hiển thị sang JSP
            request.setAttribute("user", user);
            request.setAttribute("survey", survey);

            url = "/thanks.jsp";
        }

        // Chuyển tiếp request & response sang JSP
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) 
                         throws ServletException, IOException {
        doPost(request, response);
    }
}
