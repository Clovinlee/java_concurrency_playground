package org.chris.Services;

import org.chris.Utils.Helper;

import java.time.Clock;

public class ArticleService {

    private final Clock clock;

    public ArticleService(Clock clock) {
        this.clock = clock;
    }

    public String getArticle() {
        return this.getArticle(1000);
    }

    public String getArticle(int delay) {
        try {
            System.out.println(Helper.now(clock) + " | LOG: Article Service Started");
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Helper.now(clock) + String.format(" | LOG: Article Service Completed (%s ms)", delay));

        return "Article Fetched";
    }
}
