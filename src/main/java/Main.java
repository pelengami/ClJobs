import Models.SearchParams;
import Utils.EmailCredentials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static List<JobSeeker> seekers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String command;
        do {
            command = bf.readLine();

            switch (command) {
                case "-add-seeker":
                    String params = bf.readLine();
                    SearchParams searchParams = getSearchParams(params);

                    EmailCredentials mailCredentials = getEmailCredentials(bf.readLine());

                    JobSeeker jobSeeker = new JobSeeker(searchParams);
                    EmailSubscriber subscriber = new EmailSubscriber(mailCredentials);
                    jobSeeker.addSubscriber(subscriber);
                    seekers.add(jobSeeker);
                    break;

                case "-search":
                    for (JobSeeker seeker : seekers)
                        seeker.searchAndNotifyIfNecessary();
                    break;
            }

        } while (!command.equals("-exit"));

        System.exit(0);
    }

    private static EmailCredentials getEmailCredentials(String credentials) {
        String[] spCredentials = credentials.split(" ");
        String login = spCredentials[0];
        String pass = spCredentials[1];
        String host = spCredentials[2];
        String recipient = spCredentials[3];
        return new EmailCredentials(login, pass, host, recipient);
    }

    private static SearchParams getSearchParams(String params) {
        String[] spParams = params.split(" ");
        String vacancy = spParams[0];
        String location = spParams[1];
        String domain = spParams[2];
        List<String> tags = new ArrayList<>();
        for (int i = 3; i < spParams.length; i++)
            tags.add(spParams[i]);
        return new SearchParams(vacancy, location, domain, tags);
    }

    private static void example() {
        ArrayList markingTags = new ArrayList();
        markingTags.add("Software");
        markingTags.add("Developer");
        markingTags.add("Academy");

        SearchParams searchParams = new SearchParams("developer", "seoul", "co.kr", markingTags);
        JobSeeker jobSeeker = new JobSeeker(searchParams);

        EmailCredentials mailCredentials = new EmailCredentials("login", "password", "smtp.email.com", "recipient");
        EmailSubscriber subscriber = new EmailSubscriber(mailCredentials);

        jobSeeker.addSubscriber(subscriber);
        jobSeeker.searchAndNotifyIfNecessary();
    }
}
