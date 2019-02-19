package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Григорий Кислин");

        EnumMap<ContactTypes, String> enumMapContact = new EnumMap<>(ContactTypes.class);
        enumMapContact.put(ContactTypes.TELEPHONE, "+7(921) 855-0482");
        enumMapContact.put(ContactTypes.SKYPE, "grigory.kislin");
        enumMapContact.put(ContactTypes.EMAIL, "gkislin@yandex.ru");
        enumMapContact.put(ContactTypes.LINKEDIN, "LinkedIn");
        enumMapContact.put(ContactTypes.GITHUB, "GitHub");
        enumMapContact.put(ContactTypes.STACKOVERFLOW, "Stackoverflow");
        Contact contact = new Contact(enumMapContact);

        Section section = new Section();
        section.set(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        section.set(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. "));

        List<String> listAchievement = new ArrayList<>();
        listAchievement.add("С 2013 года: разработка проектов \\\"Разработка Web приложения\\\",\\\"Java Enterprise\\\", \\\"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\\\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        listAchievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        listAchievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        section.set(SectionType.ACHIEVEMENT, new ListSection(listAchievement));

        List<String> listQualifications = new ArrayList<>();
        listQualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        listQualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        listQualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        section.set(SectionType.QUALIFICATIONS, new ListSection(listQualifications));

        Section experience = new ExperienceSection();
        ((ExperienceSection) experience).set("Java Online Projects", "http://javaops.ru/","Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.", LocalDate.of(2013, 12, 31), LocalDate.now());
        ((ExperienceSection) experience).set("JWrike", "https://www.wrike.com/","Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", LocalDate.of(2014, 12, 31), LocalDate.of(2016, 10, 22));
        ((ExperienceSection) experience).set("RIT Center", "","Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", LocalDate.of(2012, 04, 30), LocalDate.of(2014, 10, 22));
        section.set(SectionType.EXPERIENCE, experience);

        Section education = new ExperienceSection();
        ((ExperienceSection) education).set("Coursera", "https://www.coursera.org/course/progfun",null, "\"Functional Programming Principles in Scala\" by Martin Odersky", LocalDate.of(2012, 04, 30), LocalDate.of(2014, 10, 22));
        ((ExperienceSection) education).set("Luxoft","http://www.luxoft-training.ru/training/catalog/course.html?ID=22366" ,null, "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", LocalDate.of(2011, 03, 30), LocalDate.of(2011, 04, 22));
        ((ExperienceSection) education).set("Siemens AG","http://www.siemens.ru/" ,null, "3 месяца обучения мобильным IN сетям (Берлин)", LocalDate.of(2011, 03, 30), LocalDate.of(2011, 04, 22));
        section.set(SectionType.EDUCATION, education);

        System.out.println(r1.getFullName());
        System.out.print("\n");
        System.out.println(contact);
        System.out.println(section);
    }
}
