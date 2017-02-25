package Utils;

import Models.FullJobInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class HtmlReportBuilder {
    public HtmlReportBuilder() {
    }

    public String build(List<FullJobInfo> jobsInfo) throws IOException {
        String htmlReport = "";

        String html_template = getResource("/report_template.html");

        for (FullJobInfo fulljobInfo : jobsInfo) {
            String tags_html = "";
            for (String tag : fulljobInfo.Tags) {
                String tag_html_template = getResource("/tag_template.html");
                tags_html += String.format(tag_html_template, tag);
            }
            String href = '"' + fulljobInfo.Link + '"';
            htmlReport += String.format(html_template, href, fulljobInfo.Title, fulljobInfo.Type, fulljobInfo.Compensation, "None", fulljobInfo.Location, fulljobInfo.Body, tags_html);
        }

        return htmlReport;
    }

    public String getResource(String filePath) throws IOException {
        InputStream resourceAsStream = getClass().getResourceAsStream(filePath);
        InputStreamReader streamReader = new InputStreamReader(resourceAsStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        String result = "";
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

        return result;
    }
}
