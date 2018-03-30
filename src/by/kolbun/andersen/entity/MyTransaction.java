package by.kolbun.andersen.entity;

public class MyTransaction {
    private int id;
    private Statuses status;
    private String reason;
    private String from;
    private String to;
    private String comment;
    private int amount;
    private String currency;

    public MyTransaction(int id, Statuses status, String reason, String from, String to, String comment, int amount, String currency) {
        this.id = id;
        this.status = status;
        this.reason = reason;
        this.from = from;
        this.to = to;
        this.comment = comment;
        this.amount = amount;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "\t[id: #" + id + ", status: " + status +
                (status == Statuses.FAILED ? "(" + reason + ")" : "") +
                ", from: '" + from + "', to: '" + to + "', comment: \"" + comment + "\"" +
                ", amount: " + amount + " " + currency + "]";
    }
}