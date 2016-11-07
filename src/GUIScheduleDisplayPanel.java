import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by RobbieZhuang on 2016-11-06.
 */
public class GUIScheduleDisplayPanel extends JPanel {
    //    String[] columnNames = new String[8];
    String[] daysOfTheWeek = {"Time", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    Object[][] data = new Object[24][8];
    private JLabel title;
    private JTable schedule;
    private JButton mainMenu;

    GUIScheduleDisplayPanel() {

//        columnNames[0] = "X";
//        for (int i = 1; i < columnNames.length; i++) {
//            columnNames[i] = i - 1 + "";
//        }
        for (int i = 0; i < 24; i++) {
            data[i][0] = i + ":00";
        }

        for (int i = 0; i < 7; i++) {

            for (int j = 0; j < 24; j++) {
                for (Employee x : Schedule.getSchedule()[i][j].getSlot()) {
                    if (data[j][i + 1] == null) {
                        data[j][i + 1] = x.getFullName() + "\n";
                    } else {
                        data[j][i + 1] += x.getFullName() + "\n";
                    }
                }
            }
        }


        int maxRowHeight = 0;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 24; j++) {
                if (Schedule.getTotalRequiredEmployees()[i][j] > maxRowHeight) {
                    maxRowHeight = Schedule.getTotalRequiredEmployees()[i][j];
                }
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

        schedule = new JTable(dm) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                //all cells false
//                return false;
//            }
        };
        schedule.setDefaultRenderer(String.class, new MultiLineCellRenderer());
        schedule.setRowHeight(schedule.getRowHeight() * maxRowHeight);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = schedule.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
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

    void switchPanel(JPanel p) {
        Scheduler.g.switchPanel(this, p);
    }
}

class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

    public MultiLineCellRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
        }
        setFont(table.getFont());
        if (hasFocus) {
            setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
            if (table.isCellEditable(row, column)) {
                setForeground(UIManager.getColor("Table.focusCellForeground"));
                setBackground(UIManager.getColor("Table.focusCellBackground"));
            }
        } else {
            setBorder(new EmptyBorder(1, 2, 1, 2));
        }
        setText((value == null) ? "" : value.toString());
        return this;
    }
}