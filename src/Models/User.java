package Models;

public class User {
    private final long userId;  // ~19 digit unique user id
    private final String gender; // male or female
    private final String age; // <25, 25-34, 35-44, 45-54, >54
    private final String income; // high, medium or low

    // constructor for a new user
    public User(long userId, String gender, String age, String income) {
        this.userId = userId;
        this.gender = gender;
        this.age = age;
        this.income = income;
    }

    public long getUserId() {
        return userId;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getIncome() {
        return income;
    }
}
