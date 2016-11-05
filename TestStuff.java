/**
 * Created by RobbieZhuang on 2016-11-04.
 */
public class TestStuff {
    public static void main(String [] args){
        Schedule s = new Schedule();
        int [][] work = new int[7][24];
        for (int i = 0; i < 7; i++){
            for (int j = 0; j < 24; j++){
                int hours = (int)(4*Math.random());
                work[i][j] = hours;
            }
        }
        TimeSlot t = new TimeSlot(work);
        for (int i = 0; i < 10; i++){
            Employee e = new Manager();
            int end1 = (int)(7*Math.random());
            int end2 = (int)(24*Math.random());
            for (int j = 0; j < end2; j++){
                for (int k = 0; k < end2; k++){
                    e.setHoursAvailable(j,k,true);
                }
            }
        }

    }
}
