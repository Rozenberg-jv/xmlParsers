package by.kolbun.andersen.entity;

import java.util.Set;

public class MyTransaction {
    private int id;
    private Statuses status;
    private String reason;
    private String from;
    private String to;
    private Set<String> comments;
    private int amount;
    private String currency;

    public MyTransaction(int id, Statuses status, String reason, String from, String to, Set<String> comments, int amount, String currency) {
        this.id = id;
        this.status = status;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.comments = comments;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "\t[id: #" + id + ", status: " + status +
                (status == Statuses.FAILED ? "(" + reason + ")" : "") +
                ", from: '" + from + "', to: '" + to + "', comment: \"" + comments + "\"" +
                ", amount: " + amount + " " + currency + "]";
    }
}