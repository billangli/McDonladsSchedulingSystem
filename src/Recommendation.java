/**
 * Created by Robbie on 2016-11-07.
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

    public String getRecommendation() {
        return "We recommend finding " + numberOfEmployeesRequired + " employee(s) who can work on " + daysOfTheWeek[day] + " at " + time + ":00.";
    }
}
