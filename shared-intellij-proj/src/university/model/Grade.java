package university.model;

public class Grade {
    private int value;
    private String comment;

    public Grade(int value, String comment) {
        if(value < 0 || value > 5) throw new IllegalArgumentException("grade value must be in range [0;5]");

        this.value = value;
        this.comment = comment;
    }

    public int getValue() { return value; }
    public String getComment() { return comment; }
}
