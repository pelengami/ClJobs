import Models.FullJobInfo;
import Models.SearchParams;
import Models.ShortJobInfo;
import Rest.RestClientUsage;
import Utils.HtmlReportBuilder;
import Utils.UrlGetter;
import sun.security.ssl.Debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class JobSeeker {
    private HtmlReportBuilder htmlReportBuilder = new HtmlReportBuilder();
    private SearchParams searchParams;
    private List<EmailSubscriber> subscribers = new ArrayList<>();

    public JobSeeker(SearchParams searchParams) {
        this.searchParams = searchParams;
    }

    public void addSubscriber(EmailSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void searchAndNotifyIfNecessary() {
        List jobs;

        try {
            jobs = getJobs();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }

        String htmlReport;

        try {
            htmlReport = htmlReportBuilder.build(jobs);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        for (EmailSubscriber subscriber : subscribers) {
            subscriber.notify(htmlReport);
        }
    }

    private List<FullJobInfo> getJobs() throws ExecutionException, InterruptedException {

        RestClientUsage restClientUsage = new RestClientUsage();
        String absoluteUrl = UrlGetter.getJobSearchUrl(searchParams.Vacancy, searchParams.Location, searchParams.Domain);

        Future futureDownloadHtml = restClientUsage.futureDownloadHtmlSource(absoluteUrl);
        String htmlSource = (String) futureDownloadHtml.get();

        ClSiteUsage clSiteUsage = new ClSiteUsage();
        for (String tag : searchParams.MarkingTags)
            clSiteUsage.addMarkingTag(tag);

        Future<List<ShortJobInfo>> futureShortJobs = clSiteUsage.getShortJobsInfoAsync(htmlSource);
        List<FullJobInfo> fullJobInfoList = new ArrayList<>();

        for (ShortJobInfo shInfo : futureShortJobs.get()) {
            String fullJobInfoUrl = UrlGetter.getFullJobInfoUrl(shInfo.Url, searchParams.Location, searchParams.Domain);
            String fullInfoHtmlSource = restClientUsage.futureDownloadHtmlSource(fullJobInfoUrl).get();

            FullJobInfo fullJobInfo = clSiteUsage.getFullInfoAsync(fullInfoHtmlSource).get();
            fullJobInfo.Location = searchParams.Location;
            fullJobInfo.Link = fullJobInfoUrl;

            printJobInfo(fullJobInfo);

            fullJobInfoList.add(fullJobInfo);
        }

        return fullJobInfoList;
    }

    private static void printJobInfo(FullJobInfo jobInfo) {
        Debug.println(">>>", jobInfo.Title);
        Debug.println(">>>", jobInfo.Type);
        Debug.println(">>>", jobInfo.Compensation);
        Debug.println(">>>", jobInfo.Body);
        Debug.println(">>>", jobInfo.Link);
        Debug.println(">>>", jobInfo.Location);
    }
}
