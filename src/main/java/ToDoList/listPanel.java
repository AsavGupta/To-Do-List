package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class listPanel{
    static File file;
    static JPanel listPanel;
    Font listFont = new Font("Arial", Font.BOLD, 20);
    listPanel(File file) {
        ToDoList.listPanel.file = file;
        createPanel();
    }

    public JPanel getListPanel() {
        return listPanel;
    }

    public void createPanel(){
        listPanel = new JPanel();
        listPanel.setEnabled(true);
        listPanel.setBackground(Color.LIGHT_GRAY);
        listPanel.setLayout(new BorderLayout());

        JPanel leftDesignPanel = new JPanel();
        leftDesignPanel.setBackground(Color.black);
        listPanel.add(leftDesignPanel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.GRAY);

        String fileName = file.getName();
        if (fileName.endsWith(".txt")) {
            fileName = fileName.substring(0, fileName.length()-4);
        }
        JLabel listLabel = new JLabel(fileName);
        listLabel.setFont(listFont);
        listLabel.setForeground(Color.black);

        //--Delete Button--
        JButton deleteButton = listButtons();
        deleteButton.setIcon(MainWindow.imageResizer(System.getProperty("user.dir") + "\\Images\\DeleteIcon.png",20,20));
        deleteButton.addActionListener(e -> {
            if (file.delete()) {
                MainWindow.fileLabel.setText("Deleted the File: " + file.getName());
                listPanel.setVisible(false);
                MainWindow.listPanelArrayList.remove(listPanel);
                MainWindow.displayPanel.remove(listPanel);
            } else {
                MainWindow.fileLabel.setText("Failed to delete the file");
            }
        });

        //--Edit Button--
        JButton editButton = listButtons();
        editButton.setIcon(MainWindow.imageResizer(System.getProperty("user.dir") + "\\Images\\EditCalendar.png",20,20));
        editButton.addActionListener(e -> {
            EditingWindow editingWindow = new EditingWindow(file);
            MainWindow.fileLabel.setText("Editing List: " + listLabel.getText());
        });

        //--Right/Left Arrows--
        JButton setRightButton = listButtons();
        setRightButton.setText("->");

        JButton setLeftButton = listButtons();
        setLeftButton.setText("<-");

        listPanel.add(textPanel, BorderLayout.NORTH);

        //--File Display--
        itemTextDisplay();

        textPanel.add(setLeftButton);
        textPanel.add(listLabel);
        textPanel.add(deleteButton);
        textPanel.add(editButton);
        textPanel.add(setRightButton);
    }

    public static void itemTextDisplay(){
        int maxDoListLinesDisplayed = 10;
        JPanel doTextPanel = new JPanel();
        doTextPanel.setLayout(new GridLayout(0,1));
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            int lineCount = 0;

            while (!Objects.equals(line, "") && !Objects.equals(line, null) && lineCount< maxDoListLinesDisplayed) {
                doTextPanel.add(new JLabel(line.substring(0,line.length()-2)));
                line = reader.readLine();
                lineCount++;
            }
            reader.close();
            listPanel.add(doTextPanel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JButton listButtons(){
        JButton myButton = new JButton();
        myButton.setOpaque(false);
        myButton.setContentAreaFilled(false);
        myButton.setBorder(null);
        myButton.setFocusPainted(true);
        myButton.setFocusable(true);
        myButton.setForeground(Color.BLACK);
        return myButton;
    }
}
