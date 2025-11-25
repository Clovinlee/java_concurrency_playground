package org.chris.Utils;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

public class Helper {
    public static String joinAll(CompletableFuture<String> articleFuture, CompletableFuture<String> commentFuture, CompletableFuture<String> userFuture) {
        CompletableFuture<String> finalArticleFuture = articleFuture != null ? articleFuture : CompletableFuture.completedFuture("null");
        CompletableFuture<String> finalCommentFuture = commentFuture != null ? commentFuture : CompletableFuture.completedFuture("null");
        CompletableFuture<String> finalUserFuture = userFuture != null ? userFuture : CompletableFuture.completedFuture("null");

        return CompletableFuture
                .allOf(
                        finalArticleFuture,
                        finalCommentFuture,
                        finalUserFuture
                )
                .thenApply(v -> {
                    String article = finalArticleFuture.join();
                    String comment = finalCommentFuture.join();
                    String user = finalUserFuture.join();

                    return String.format("%s, %s, %s", article, comment, user);
                })
                .join();
    }

    public static String now(Clock clock) {
        return LocalTime.now(clock)
                .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
}
