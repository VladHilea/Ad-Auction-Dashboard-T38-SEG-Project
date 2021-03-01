package Models;

import java.time.LocalDateTime;

public class Impression {
    LocalDateTime date; // date and time
    long id; // ~19 digit unique user id
    String gender; // male or female
    String age; // <25, 25-34, 35-44, 45-54, >54
    String income; // high, medium or low
    String context; // blog, new, shopping, social media
    double impressionCost; // 6 d.p. value (>0)

    public Impression(LocalDateTime date, long id, String gender, String age, String income, String context, double impressionCost) {
        this.date = date;
        this.id = id;
        this.gender = gender;
        this.age = age;
        this.income = income;
        this.context = context;
        this.impressionCost = impressionCost;
    }
}
