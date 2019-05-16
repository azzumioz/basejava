package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private static final Storage storage = Config.get().getStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume r = storage.get(uuid);
        r.setFullName(fullName);
        ArrayList<Integer> index = new ArrayList<>();
        request.setAttribute("index", index);
        index.add(0);

        for (ContactTypes type : ContactTypes.values()) {
            String value = request.getParameter(type.name());
            doSaveContacts(r, type, value);
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case OBJECTIVE:
                    doSaveTextSections(r, type, request.getParameter("sectionObjective"));
                    break;
                case PERSONAL:
                    doSaveTextSections(r, type, request.getParameter("sectionPersonal"));
                    break;
                case ACHIEVEMENT:
                    doSaveListSections(r, type, request.getParameterValues("sectionAchievement"));
                    break;
                case QUALIFICATIONS:
                    doSaveListSections(r, type, request.getParameterValues("sectionQualifications"));
                    break;
                case EXPERIENCE:
//                    doSaveOrganizationSections(r, type, request.getParameterValues("sectionExperience"));
                    break;
                case EDUCATION:
//                    doSaveOrganizationSections(r, type, request.getParameterValues("sectionEducation"));
            }
        }
        storage.update(r);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
        }
        Resume r;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                if (uuid.equals("")) {
                    storage.save(new Resume("!Новое резюме"));
                    response.sendRedirect("resume");
                    return;
                } else {
                    r = storage.get(uuid);
                }
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", r);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp").forward(request, response);
    }

    private void doSaveContacts(Resume r, ContactTypes type, String value) {
        if (value != null && value.trim().length() != 0) {
            r.addContacts(type, value);
        } else {
            r.getContacts().remove(type);
        }
    }

    private void doSaveTextSections(Resume r, SectionType type, String value) {
        if (value != null && value.trim().length() != 0) {
            r.addSections(type, new TextSection(value));
        } else {
            r.getSections().remove(type);
        }
    }

    private void doSaveListSections(Resume r, SectionType type, String[] sectionName) {
        if (sectionName != null && sectionName.length != 0) {
            List<String> list = Arrays.stream(sectionName).filter((p) -> !p.equals("")).collect(Collectors.toList());
            r.getSections().remove(type);
            r.addSections(type, new ListSection(list));
        } else {
            r.getSections().remove(type);
        }
    }

    private void doSaveOrganizationSections(Resume r, SectionType type, String[] sectionQualifications) {

    }

}
