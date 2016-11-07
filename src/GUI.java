import javax.swing.*;
import java.awt.*;

/**
 * Created by RobbieZhuang on 2016-11-05.
 */
class GUI extends JFrame {

    GUI() {
        this.setName("McDonlads Scheduling System");
        this.setTitle("McDonlads Scheduling System");
        this.add(new GUIMainPanel());
        this.setSize(new Dimension(1000, 400));
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void switchPanel(JPanel old, JPanel next) {
        this.remove(old);
        this.add(next);
        this.pack();
        this.setVisible(true);
    }

    void quit() {
        this.dispose();
    }
}
