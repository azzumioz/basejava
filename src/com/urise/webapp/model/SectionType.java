package com.urise.webapp.model;

public enum SectionType {
    OBJECTIVE("Позиция"),
    PERSONAL("Личные качества"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(AbstractSection value) {
        String resultHtml = "";
        switch (this) {
            case OBJECTIVE:
                return "<tr><td colspan=\"2\"><h2>" + getTitle() + "</h2></td></tr><tr><td colspan=\"2\"><h3>" + value + "</h3></td></tr>";
            case PERSONAL:
                return "<tr><td colspan=\"2\"><h2>" + getTitle() + "</h2></td></tr><tr><td colspan=\"2\">" + value + "</td></tr>";
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                for (String item : ((ListSection) value).getItems()) {
                    resultHtml += "<li>" + item + "</li>";
                }
                return "<tr><td colspan=\"2\"><h2>" + getTitle() + "</h2></td></tr>" + "<tr><td colspan=\"2\"><ul>" + resultHtml + "</ul></td></tr>";

            case EXPERIENCE:
            case EDUCATION:
                for (Organization organization : (((OrganizationSection) value).getOrganizations())) {
                    resultHtml += "<tr><td colspan=\"2\"><h3><a href = \"" + organization.getHomePage().getUrl() + "\">" + organization.getHomePage().getName() + "</a></h3></td></tr>";
                    for (Organization.Position position : organization.getPositions()) {
                        resultHtml += "<tr><td width=\"15%\" style=\"vertical-align: top\">";
                        resultHtml += position.getStartDate().getMonthValue() + "/" + position.getStartDate().getYear() + " - " + position.getEndDate().getMonthValue() + "/" + position.getEndDate().getYear();
                        resultHtml += "</td>";
                        resultHtml += "<td><b>" + position.getTitle() + "</b><br>";
                        resultHtml += writeMayBeNull(position.getDescription()) + "</td></tr>";
                    }
                }
                return "<tr><td colspan=\"2\"><h2>" + getTitle() + "</h2></td></tr>" + resultHtml;
        }
        return null;
    }

    public String toHtml(AbstractSection value) {
        return (value == null) ? "" : toHtml0(value);
    }

    private String writeMayBeNull(String value) {
        return (value == null) ? "" : value;
    }

}
