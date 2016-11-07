import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Robbie on 2016-11-07.
 */
public class GUIIndividualScheduleDisplayPanel extends GUIScheduleDisplayPanel {
    String[] daysOfTheWeek = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] data = new Object[24][8];
    private JLabel title;
    private JTable schedule;
    private JButton mainMenu;

    GUIIndividualScheduleDisplayPanel(Employee x) {
        for (int i = 0; i < x.getHoursWorking().length; i++) {
            for (int j = 0; j < x.getHoursWorking()[i].length; j++) {

            }
        }
        DefaultTableModel dm = new DefaultTableModel() {
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }

        };
        dm.setDataVector(data, daysOfTheWeek);

        schedule = new JTable(dm);
        schedule.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = schedule.getColumnModel().getColumn(i);
            column.setPreferredWidth(50);
        }

        schedule.setFont(new Font("Serif", Font.BOLD, 10));
        JScrollPane scroll = new JScrollPane(schedule);
        scroll.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width - 200, Toolkit.getDefaultToolkit().getScreenSize().height - 200));
        mainMenu = new JButton("Go back to main menu");
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open up the main panel
                switchPanel(new GUIMainPanel());
            }
        });
        this.add(scroll);
        this.add(mainMenu);
        this.repaint();
    }
}
