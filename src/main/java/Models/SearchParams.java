package Models;

import java.util.List;

public class SearchParams {
    public final String Vacancy;
    public final String Location;
    public final String Domain;
    public final List<String> MarkingTags;

    public SearchParams(String vacancy, String location, String domain, List<String> markingTags) {
        Vacancy = vacancy;
        Location = location;
        Domain = domain;
        MarkingTags = markingTags;
    }
}
