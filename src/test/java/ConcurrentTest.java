import org.chris.Services.ArticleService;
import org.chris.Services.CommentService;
import org.chris.Services.CounterWithLockService;
import org.chris.Services.UserService;
import org.chris.Utils.Helper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConcurrentTest {
    static final int THREAD_POOL = 3;

    private ArticleService articleService;
    private CommentService commentService;
    private UserService userService;
    private CounterWithLockService counterWithLockService;
    private Clock clock;
    private ExecutorService executor;

    // AtomicInteger, ConcurrentHashmap, CopyOnWriteArrayList

    @Test
    void shouldRunConcurrentWithLock() throws InterruptedException {
        System.out.println("shouldRunConcurrentWithLock Test");

        for (int i = 0; i < 3; i++) {
            executor.submit(() -> counterWithLockService.increment());
        }

        Thread.sleep(2500);
        System.out.printf("Final Counter: %s%n", counterWithLockService.getCount());
    }

    @Test
    void shouldRunDependentConcurrency() {
        System.out.println("DependentConcurrencyTest Test");

        CompletableFuture<String> commentFuture =
                CompletableFuture.supplyAsync(this.articleService::getArticle, executor)
                        .thenApply(article -> this.commentService.getComment());

        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(() -> this.userService.getUser("Chris"), executor);

        Helper.joinAll(null, commentFuture, userFuture);
    }

    @Test
    void shouldRunAtTheSameTime() {
        System.out.println("Concurrency Test");

        CompletableFuture<String> articleFuture =
                CompletableFuture.supplyAsync(this.articleService::getArticle, executor);

        CompletableFuture<String> commentFuture =
                CompletableFuture.supplyAsync(this.commentService::getComment, executor);

        CompletableFuture<String> userFuture =
                CompletableFuture.supplyAsync(() -> this.userService.getUser("Chris"), executor);

//         System.out.println("THIS CODE PRINTS");

        String result = Helper.joinAll(articleFuture, commentFuture, userFuture);

        // System.out.println("THIS CODE PRINTS ONLY AFTER ALL 3 FUTURE ARE DONE");

        assertNotNull(result);
    }

    @AfterEach
    void finishTest() {
        System.out.println(Helper.now(this.clock) +" | Test Done\n");

        executor.shutdown();
    }

    @BeforeEach
    void setup() {
        Instant desiredStart = Instant.parse("2025-10-10T00:00:00Z");
        Instant now = Instant.now();

        Clock testClock = Clock.offset(Clock.systemUTC(),
                Duration.between(now, desiredStart));

        this.executor = Executors.newFixedThreadPool(THREAD_POOL);

        this.clock = testClock;
        this.articleService = new ArticleService(testClock);
        this.commentService = new CommentService(testClock);
        this.userService = new UserService(testClock);
        this.counterWithLockService = new CounterWithLockService(testClock);
    }
}
