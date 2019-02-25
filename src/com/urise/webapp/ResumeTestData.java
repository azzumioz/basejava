package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Григорий Кислин");

        r1.setContacts(ContactTypes.PHONE, "+7(921) 855-0482");
        r1.setContacts(ContactTypes.SKYPE, "grigory.kislin");
        r1.setContacts(ContactTypes.EMAIL, "gkislin@yandex.ru");
        r1.setContacts(ContactTypes.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r1.setContacts(ContactTypes.GITHUB, "https://github.com/gkislin");
        r1.setContacts(ContactTypes.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        r1.setContacts(ContactTypes.HOME_PAGE, "http://gkislin.ru/");

        r1.setSections(SectionType.OBJECTIVE, new TextAbstractSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r1.setSections(SectionType.PERSONAL, new TextAbstractSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> listAchievement = new ArrayList<>();
        listAchievement.add("С 2013 года: разработка проектов \\\"Разработка Web приложения\\\",\\\"Java Enterprise\\\", \\\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\\\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        r1.setSections(SectionType.ACHIEVEMENT, new ListAbstractSection(listAchievement));

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        r1.setSections(SectionType.QUALIFICATIONS, new ListAbstractSection(listQualifications));

        List<Organization> listExperience = new ArrayList<>();
        Organization organization1 = new Organization("Java Online Projects", "http://javaops.ru/");
        organization1.addOrganizationDetail(new OrganizationDetail(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2014, Month.OCTOBER), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        listExperience.add(organization1);
        Organization organization2 = new Organization("Wrike", "https://www.wrike.com/");
        organization2.addOrganizationDetail(new OrganizationDetail(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        listExperience.add(organization2);
        r1.setSections(SectionType.EXPERIENCE, new OrganizationAbstractSection(listExperience));

        List<Organization> listEducation = new ArrayList<>();
        Organization organization3 = new Organization("Coursera", "https://www.coursera.org/course/progfun");
        organization3.addOrganizationDetail(new OrganizationDetail(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        listEducation.add(organization3);
        Organization organization4 = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/");
        organization4.addOrganizationDetail(new OrganizationDetail(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null));
        organization4.addOrganizationDetail(new OrganizationDetail(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null));
        listEducation.add(organization4);
        r1.setSections(SectionType.EDUCATION, new OrganizationAbstractSection(listEducation));

        System.out.println(r1.getFullName());
        for (ContactTypes type : ContactTypes.values()) {
            if (r1.getContacts(type) != null) {
                System.out.println(type.getTitle() + ": " + r1.getContacts(type));
            }
        }
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + "\n " + r1.getSection(type));
        }
    }
}
