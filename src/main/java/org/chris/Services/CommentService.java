package org.chris.Services;

import org.chris.Utils.Helper;

import java.time.Clock;

public class CommentService {

    private final Clock clock;

    public CommentService(Clock clock) {
        this.clock = clock;
    }

    public String getComment()
    {
        return this.getComment(500);
    }

    public String getComment(int delay) {
        try {
            System.out.println(Helper.now(clock) + " | LOG: Comment Service Started");
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Helper.now(clock) + String.format(" | LOG: Comment Service Completed (%s ms)", delay));


        return "Comment Fetched";
    }
}
