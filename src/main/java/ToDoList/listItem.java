package ToDoList;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import static ToDoList.Window.imageResizer;

public class listItem {

    JPanel listItem;
    String text;
    File file;
    boolean completed;
    Font itemFont = new Font("Arial", Font.BOLD, 20);

    listItem(String itemText, boolean completed, File file){
        text = itemText;
        this.file = file;
        this.completed = completed;
        createPanel();
    }

    public JPanel getPanel(){
        return listItem;
    }

    public void createPanel(){
        listItem = new JPanel();
        listItem.setEnabled(true);
        listItem.setVisible(true);
        listItem.setBackground(Color.DARK_GRAY);
        listItem.setBorder(BorderFactory.createEtchedBorder(1));

        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.LIGHT_GRAY);
        textPanel.setBorder(BorderFactory.createEtchedBorder(0, Color.ORANGE, Color.WHITE));

        JLabel itemLabel = new JLabel(text);
        itemLabel.setFont(itemFont);
        itemLabel.setForeground(Color.BLACK);
        textPanel.add(itemLabel);

        //--Completion Mark--
        JButton completionButton = itemButtons();
        completionButton.setIcon(checkMarkSwitch());
        completionButton.setBackground(new Color(100,200,0,20));
        completionButton.setToolTipText("Click to toggle completion");
        completionButton.addActionListener(e -> {
            completed = !completed;
            completionButton.setIcon(checkMarkSwitch());
            try {
                saveCheckedStatus();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });


        //--Delete Button--
        JButton deleteButton = new JButton();
        deleteButton.setOpaque(false);
        deleteButton.setFocusPainted(false);
        deleteButton.setIcon(MainWindow.imageResizer(System.getProperty("user.dir") + "\\Images\\DeleteIcon.png",24,24));
        deleteButton.addActionListener(e -> {
            try {
                deleteButtonSave();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        listItem.add(textPanel);
        listItem.add(completionButton);
        listItem.add(deleteButton);
    }

    private ImageIcon checkMarkSwitch(){
        if (!completed){
            return imageResizer(System.getProperty("user.dir") + "\\Images\\Uncheckedmark.png",24,24);
        }
        return imageResizer(System.getProperty("user.dir") + "\\Images\\Checkmark.png",24,24);
    }

    private void saveCheckedStatus() throws IOException {
        ArrayList<String> lines = fileReader();
        for (int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if(line.contains(text + ":")) {
                if (lines.contains(text + ":1")) lines.set(i, text + ":0");
                else lines.set(i, text + ":1");
            }
        }
        Files.write(Paths.get(file.toPath().toUri()), lines);
    }

    private void deleteButtonSave() throws IOException {
        ArrayList<String> lines = fileReader();
        for (int i = 0; i < lines.size(); i++){
            String line = lines.get(i);
            if(line.contains(text + ":")) {
                lines.remove(i);
                break;
            }
        }
        Files.write(Paths.get(file.toPath().toUri()), lines);
        EditingWindow.loadTextItems(file);
    }

    private ArrayList<String> fileReader(){
        try {
            Path path = file.toPath();
            return (ArrayList<String>) Files.readAllLines(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JButton itemButtons(){
        JButton myButton = new JButton();
        myButton.setOpaque(false);
        myButton.setFocusPainted(false);
        return myButton;
    }
}
