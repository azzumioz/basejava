package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
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

        r1.setSections(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r1.setSections(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> listAchievement = new ArrayList<>();
        listAchievement.add("С 2013 года: разработка проектов \\\"Разработка Web приложения\\\",\\\"Java Enterprise\\\", \\\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\\\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        r1.setSections(SectionType.ACHIEVEMENT, new ListSection(listAchievement));

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        r1.setSections(SectionType.QUALIFICATIONS, new ListSection(listQualifications));

        List<Organization> listExperience = new ArrayList<>();
        listExperience.add(new Organization("Java Online Projects", "http://javaops.ru/", DateUtil.of(2013, Month.OCTOBER), LocalDate.now(), "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        listExperience.add(new Organization("Wrike", "https://www.wrike.com/", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        listExperience.add(new Organization("RIT Center", null, DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        r1.setSections(SectionType.EXPERIENCE, new OrganizationSection(listExperience));

        List<Organization> listEducation = new ArrayList<>();
        listEducation.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        listEducation.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        listEducation.add(new Organization("Siemens AG", "http://www.siemens.ru/", DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "3 месяца обучения мобильным IN сетям (Берлин)", null));
        r1.setSections(SectionType.EDUCATION, new OrganizationSection(listEducation));

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
