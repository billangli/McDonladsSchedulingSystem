/*
 Recommendation.java
 @version 1.0
 @author Robbie Zhuang
 @date 2016-11-06
 This recommendation object tells the user where to add employees, if required
 */

public class Recommendation {
    private int numberOfEmployeesRequired;
    private int day;
    private String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private int time;

    Recommendation(int n, int d, int t) {
        this.numberOfEmployeesRequired = n;
        this.day = d;
        this.time = t;
    }

    /**
     * getRecommendation
     * This method usues the data and returns a string that displays the situation
     * @return A string for display
     */
    public String getRecommendation() {
        return "We recommend finding " + numberOfEmployeesRequired + " employee(s) who can work on " + daysOfTheWeek[day] + " at " + time + ":00.";
    }
}
