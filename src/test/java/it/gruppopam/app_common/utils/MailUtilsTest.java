package it.gruppopam.app_common.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class MailUtilsTest {

    @Test
    public void generateReportTextTest() {
        MailUtils utils = new MailUtils();

        List<String> lines = new ArrayList<>();
        lines.add("Hello");
        lines.add("World");
        String report = utils.generateReportText(lines);

        assertTrue(report.contains("Hello"));
        assertTrue(report.contains("World"));
        assertTrue(report.contains("\n"));
    }
}
