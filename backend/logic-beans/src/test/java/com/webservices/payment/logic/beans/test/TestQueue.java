package com.webservices.payment.logic.beans.test;

import javax.jms.Queue;
import javax.jms.TemporaryQueue;
import java.util.Objects;
/**
 * @author Lasse
 */
public class TestQueue implements Queue, TemporaryQueue {
    public static TestQueue Persistency = new TestQueue("Persistency Test Queue");
    public static TestQueue CreateClient = new TestQueue("Create Client Test Queue");
    public static TestQueue IssueToken = new TestQueue("Issue Token Test Queue");
    public static TestQueue Transaction = new TestQueue("Transaction Test Queue");

    private final String name;

    public TestQueue(String name) {
        this.name = name;
    }

    @Override
    public String getQueueName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void delete() {

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestQueue testQueue = (TestQueue) o;
        return Objects.equals(name, testQueue.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
