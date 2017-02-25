package Utils;

public class UrlGetter {
    private static final String SEARCH_URL = "http://%s.craigslist.%s/search/jjj?query=%s";
    private static final String JOB_INFO_URL = "http://%s.craigslist.%s%s";

    public UrlGetter() {
    }

    public static String getJobSearchUrl(String vacancy, String location, String domain) {
        String url = String.format(SEARCH_URL, location, domain, vacancy);
        return url;
    }

    public static String getFullJobInfoUrl(String smallUrl, String location, String domain) {
        String url = String.format(JOB_INFO_URL, location, domain, smallUrl);
        return url;
    }
}
