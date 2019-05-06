package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class JsonParserTest {
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "Name1");

    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection section = new TextSection("Objective");
        String json = JsonParser.write(section, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(section, section2);
    }
}