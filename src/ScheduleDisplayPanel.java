import javax.swing.*;
import java.awt.*;

/**
 * Created by RobbieZhuang on 2016-11-06.
 */
public class ScheduleDisplayPanel extends JPanel {
    private JLabel title;

    ScheduleDisplayPanel() {
        this.setLayout(new GridLayout(7, 24));
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                JPanel subPanel = new JPanel();
                for (Employee x : Schedule.getTable()[i][j].getSlot()) {
                    subPanel.add(new Label(x.getFullName()));
                    System.out.println("Something is happening");
                }
                this.add(subPanel);
            }
        }
    }
}
