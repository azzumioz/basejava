package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.storage.MapResumeStorage;
import com.urise.webapp.storage.Storage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    static final Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Григорий Кислин");

        Contacts contacts = new Contacts();
        Sections sections = new Sections();
        Sections personal = new TextSection();
        Sections objective = new TextSection();
        Sections achievement = new ListSection();
        Sections qualifications = new ListSection();
        Sections experience = new DateSection();
        Sections education = new DateSection();

        List<String> listAchievement = new ArrayList<>();
        List<String> listQualifications = new ArrayList<>();

        listAchievement.add("С 2013 года: разработка проектов \\\"Разработка Web приложения\\\",\\\"Java Enterprise\\\", \\\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\\\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");

        ((TextSection) personal).set("Аналитический склад ума, сильная логика, креативность, инициативность. ");
        ((TextSection) objective).set("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        ((ListSection) achievement).set(listAchievement);
        ((ListSection) qualifications).set(listQualifications);
        ((DateSection) experience).set("Java Online Projects", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.", LocalDate.of(2013, 12, 31), LocalDate.now());
        ((DateSection) experience).set("JWrike", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", LocalDate.of(2014, 12, 31), LocalDate.of(2016, 10, 22));
        ((DateSection) experience).set("RIT Center", "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", LocalDate.of(2012, 04, 30), LocalDate.of(2014, 10, 22));
        ((DateSection) education).set("Coursera", null, "\"Functional Programming Principles in Scala\" by Martin Odersky", LocalDate.of(2012, 04, 30), LocalDate.of(2014, 10, 22));
        ((DateSection) education).set("Luxoft", null, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", LocalDate.of(2011, 03, 30), LocalDate.of(2011, 04, 22));
        ((DateSection) education).set("Siemens AG", null, "3 месяца обучения мобильным IN сетям (Берлин)", LocalDate.of(2011, 03, 30), LocalDate.of(2011, 04, 22));

        sections.set(SectionType.PERSONAL, personal);
        sections.set(SectionType.OBJECTIVE, objective);
        sections.set(SectionType.ACHIEVEMENT, achievement);
        sections.set(SectionType.QUALIFICATIONS, qualifications);
        sections.set(SectionType.EXPERIENCE, experience);
        sections.set(SectionType.EDUCATION, education);
        contacts.set(ContactTypes.TELEPHONE, "+7(921) 855-0482");
        contacts.set(ContactTypes.SKYPE, "grigory.kislin");
        contacts.set(ContactTypes.EMAIL, "gkislin@yandex.ru");
        contacts.set(ContactTypes.LINKEDIN, "LinkedIn");
        contacts.set(ContactTypes.GITHUB, "GitHub");
        contacts.set(ContactTypes.STACKOVERFLOW, "Stackoverflow");

        r1.setContacts(contacts);
        r1.setSections(sections);
        ARRAY_STORAGE.save(r1);

        Sections sections2 = ARRAY_STORAGE.get("uuid1").getSections();
        Contacts contacts2 = ARRAY_STORAGE.get("uuid1").getContacts();

        System.out.println(ARRAY_STORAGE.get("uuid1").getFullName());
        System.out.print("\n");
        System.out.println(contacts2);
        System.out.println(sections2);
    }
}
