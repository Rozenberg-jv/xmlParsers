package by.kolbun.andersen.entity;

public enum Statuses {
    OK, FAILED;


    @Override
    public String toString() {
        switch (this) {
            case OK:
                return "ok";
            case FAILED:
                return "failed";
            default:
                throw new IllegalArgumentException();
        }
    }
}
