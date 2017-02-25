import Models.FullJobInfo;
import Models.ShortJobInfo;
import Utils.HtmlParser;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static Utils.HtmlConst.*;

public class ClSiteUsage {
    private final HtmlParser htmlParser = new HtmlParser();
    private final List<String> markingTags = new ArrayList();

    public void addMarkingTag(String tag) {
        if (!this.markingTags.contains(tag)) {
            this.markingTags.add(tag);
        }
    }

    public Future<List<ShortJobInfo>> getShortJobsInfoAsync(String htmlSource) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future future = executor.submit(() -> {
            this.loadHtmlSource(htmlSource);
            return this.getShortJobsInfoSync();
        });
        return future;
    }

    public Future<FullJobInfo> getFullInfoAsync(String htmlSource) {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future future = executor.submit(() -> {
            this.loadHtmlSource(htmlSource);
            return this.getFullJobInfoSync();
        });
        return future;
    }

    private void loadHtmlSource(String htmlSource) {
        this.htmlParser.loadHtmlSource(htmlSource);
    }

    private List<ShortJobInfo> getShortJobsInfoSync() {
        List<Element> allJobPostContainersElement = htmlParser.getElements(JOB_CONTAINER);
        ArrayList jobsInfo = new ArrayList();

        for (Element element : allJobPostContainersElement) {
            Element elementTitleAndLink = htmlParser.getElement(element, TITLE_AND_LINK);
            Element elementDate = htmlParser.getElement(element, DATE_TIME);

            ShortJobInfo jobInfo = new ShortJobInfo();
            jobInfo.Title = elementTitleAndLink.text();
            jobInfo.Url = elementTitleAndLink.attr("href");
            jobInfo.DateTime = elementDate.text();

            jobsInfo.add(jobInfo);
        }

        return jobsInfo;
    }

    private FullJobInfo getFullJobInfoSync() {
        FullJobInfo fullJobInfo = new FullJobInfo();

        Element elementTitle = htmlParser.getElement(TITLE_TEXT_ONLY);
        Element elementBody = htmlParser.getElement(JOB_POSTINTG_BODY);
        Element compensationAndType = htmlParser.getElement(JOB_COMPENSATION_AND_TYPE);

        String title = elementTitle.text();
        fullJobInfo.Title = title;

        String body = elementBody.text();
        fullJobInfo.Body = body.replace("QR Code Link to This Post", "");

        Elements bElements = compensationAndType.getElementsByTag("b");
        String compensation = bElements.get(0).text();
        String type = bElements.get(1).text();
        fullJobInfo.Compensation = compensation;
        fullJobInfo.Type = type;

        String compensationAsLower = compensation.toLowerCase();
        String typeAsLower = type.toLowerCase();
        String bodyAsLower = body.toLowerCase();
        String titleAsLower = title.toLowerCase();

        for (String tag : markingTags) {
            String tagAsLower = tag.toLowerCase();
            if (titleAsLower.contains(tagAsLower) || bodyAsLower.contains(tagAsLower) || compensationAsLower.contains(tagAsLower) || typeAsLower.contains(tagAsLower))
                fullJobInfo.addTag(tag);
        }

        return fullJobInfo;
    }
}
