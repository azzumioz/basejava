package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ResumeServlet extends HttpServlet {
    private static final Storage STORAGE = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume resume = STORAGE.get(uuid);
            response.getWriter().write("<a href='http://localhost:8080/resumes/resume'>К списку резюме</a>");
            response.getWriter().write("<hr><h1>" + resume.getFullName() + "</h1><p>");
            for (ContactTypes type : ContactTypes.values()) {
                if (resume.getContacts(type) != null) {
                    String item = resume.getContacts(type);
                    switch (type) {
                        case PHONE:
                        case MOBILE:
                        case HOME_PHONE:
                            response.getWriter().write(type.getTitle() + ": " + item + "<BR>");
                            break;
                        case SKYPE:
                        case EMAIL:
                            response.getWriter().write(type.getTitle() + ": " + "<a href=" + item + ">" + item + "</a><BR>");
                            break;
                        case LINKEDIN:
                        case GITHUB:
                        case STACKOVERFLOW:
                        case HOME_PAGE:
                            response.getWriter().write("<a href=" + item + ">" + type.getTitle() + "</a><BR>");
                    }
                }
            }
            response.getWriter().write("<p><hr>");
            response.getWriter().write("<table cellpadding='2'>");

            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                response.getWriter().write("<tr><td colspan='2'><h2>" + section.getKey().getTitle() + "</h2></td></tr>");
                response.getWriter().write("<tr><td colspan='2'>");
                switch (section.getKey()) {
                    case OBJECTIVE:
                        response.getWriter().write("<h3>" + section.getValue().toString() + "</h3>");
                        break;
                    case PERSONAL:
                        response.getWriter().write(section.getValue().toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> list = ((ListSection) section.getValue()).getItems();
                        response.getWriter().write("<ul>");
                        for (String item : list) {
                            response.getWriter().write("<li>" + item + "</li>");
                        }
                        response.getWriter().write("</ul>");
                }
                response.getWriter().write("</td></tr>");
            }
            response.getWriter().write("</table>");

        } else {
            response.getWriter().write("<h1>" + "Список резюме" + "</h1>");
            List<Resume> listResume = STORAGE.getAllSorted();
            response.getWriter().write("<table cellpadding='2'>");
            response.getWriter().write("<tr><td colspan='2'>UUID</td><td colspan='2'>Name</td></tr>");
            for (Resume resume : listResume) {
                String uuid1 = resume.getUuid();
                response.getWriter().write("<tr><td colspan='2'><a href=?uuid=" + uuid1 + ">" + uuid1 + "</a></td>");
                response.getWriter().write("<td>" + resume.getFullName() + "</td></tr>");
            }
            response.getWriter().write("</table>");
        }
    }
}
