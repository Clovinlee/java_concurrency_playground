package org.chris.Services;

import org.chris.Utils.Helper;

import java.time.Clock;

public class UserService {

    private final Clock clock;

    public UserService(Clock clock) {
        this.clock = clock;
    }

    public String getUser(String name) {
        return this.getUser(name, 2000);
    }

    public String getUser(String name, int delay) {
        try {
            System.out.println(Helper.now(clock) + " | LOG: User Service Started");
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(Helper.now(clock) + String.format(" | LOG: User Service Completed (%s ms)", delay));

        return "User Fetched: " + name;
    }
}
