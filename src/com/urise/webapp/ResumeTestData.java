package com.urise.webapp;

import com.urise.webapp.model.ContactTypes;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

public class ResumeTestData {

    public static Resume getResume(String uuid, String fullName) {
        Resume r1 = new Resume(uuid, fullName);

        r1.addContacts(ContactTypes.PHONE, "+7(921) 855-0482");
        r1.addContacts(ContactTypes.SKYPE, "grigory.kislin");
        r1.addContacts(ContactTypes.EMAIL, "gkislin@yandex.ru");
        r1.addContacts(ContactTypes.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r1.addContacts(ContactTypes.GITHUB, "https://github.com/gkislin");
        r1.addContacts(ContactTypes.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        r1.addContacts(ContactTypes.HOME_PAGE, "http://gkislin.ru/");
//
//        r1.addSections(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
//        r1.addSections(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
//
//        r1.addSections(SectionType.ACHIEVEMENT, new ListSection(
//                "С 2013 года: разработка проектов 'Разработка Web приложения,Java Enterprise, Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA). Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
//                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
//                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера."));
//
//        r1.addSections(SectionType.QUALIFICATIONS, new ListSection(
//                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
//                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
//                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,"));
//
//        r1.addSections(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("name1", "http://javaops.ru/",
//                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2014, Month.OCTOBER), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
//                new Organization("name2", "https://www.wrike.com/",
//                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))));
//
//        r1.addSections(SectionType.EXPERIENCE, new OrganizationSection(
//                new Organization("Java Online Projects", "http://javaops.ru/",
//                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2014, Month.OCTOBER), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.")),
//                new Organization("Wrike", "https://www.wrike.com/",
//                        new Organization.Position(DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."))));
//
//        r1.addSections(SectionType.EDUCATION, new OrganizationSection(
//                new Organization("Coursera", "https://www.coursera.org/course/progfun",
//                        new Organization.Position(DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "\"Functional Programming Principles in Scala\" by Martin Odersky", null)),
//                new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/",
//                        new Organization.Position(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null),
//                        new Organization.Position(DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null))));

        return r1;
    }

    public static void main(String[] args) {
        Resume r1 = getResume("UUID1", "Григорий Кислин");
        System.out.println(r1.getFullName());
        for (ContactTypes type : ContactTypes.values()) {
            if (r1.getContacts() != null) {
                System.out.println(type.getTitle() + ": " + r1.getContacts(type));
            }
        }
        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + "\n " + r1.getSections());
        }
    }
}
