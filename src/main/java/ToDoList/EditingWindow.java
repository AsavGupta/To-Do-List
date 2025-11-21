package ToDoList;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class EditingWindow extends Window{
    private static JPanel mainPanel;
    private static JFrame mainFrame;
    public EditingWindow(File file){
        try {
            UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
        } catch (Exception e){
            e.printStackTrace();
        }
        Initialize(file);
    }
    private void Initialize(File file){
        mainFrame = new JFrame("To Do List");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(800,500);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setIconImage(imageResizer(System.getProperty("user.dir") + "\\Images\\Calendar.png",36,36).getImage());
        mainFrame.setTitle(file.getName().replace(".txt",""));

        //---Main Panel---
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(Color.DARK_GRAY);
        mainFrame.add(mainPanel);

        //--Theme Combo Box--
        JComboBox<LookAndFeel> themeList = new JComboBox<>(lafArray);
        themeList.setSelectedIndex(0);
        themeList.setFocusable(false);

        //--Menu Bar--
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        //--Settings Button--
        JButton settingsButton = new JButton();
        settingsButton.setIcon(imageResizer(System.getProperty("user.dir") + "\\Images\\Settings.png",20,20));
        settingsButton.setOpaque(false);
        settingsButton.setFocusable(false);
        settingsButton.setMargin(new Insets(0,0,0,0));
        settingsButton.setFont(menuFont);
        settingsButton.addActionListener(e -> {

        });

        //--Add Item Button--
        JButton itemButton = new JButton("+");
        itemButton.setOpaque(false);
        itemButton.setFocusable(false);
        itemButton.setMargin(new Insets(0,0,0,0));
        itemButton.setForeground(Color.LIGHT_GRAY);
        itemButton.setToolTipText("New Item (Alt+Enter)");
        itemButton.setMnemonic(KeyEvent.VK_ENTER);
        itemButton.setFont(menuFont);

        //--Add Item Text Field--
        JTextField itemTextField = new JTextField(10);
        itemTextField.setMaximumSize(new Dimension(200,1000));
        itemTextField.setFont(menuFont);
        itemTextField.setToolTipText("Hit Enter to Create Item");
        itemTextField.setVisible(false);

        //--Button Action--
        itemButton.addActionListener(e -> {
            if(!itemTextField.isVisible()){
                itemTextField.setVisible(true);
                itemButton.setForeground(Color.DARK_GRAY);
            }
            else{
                itemTextField.setVisible(false);
                itemButton.setForeground(Color.LIGHT_GRAY);
            }
            itemTextField.setText("");
            mainPanel.revalidate();
        });

        //--Text Field Action--
        itemTextField.addActionListener(e -> {
            String fieldText = itemTextField.getText();
            if(!fieldText.equals("")) {
                createTextItem(file, fieldText);
            }
            if(!itemTextField.isVisible()){
                itemTextField.setVisible(true);
                itemButton.setForeground(Color.BLACK);
            }
            else{
                itemTextField.setVisible(false);
                itemButton.setForeground(Color.LIGHT_GRAY);
            }
            itemTextField.setText("");
            mainPanel.revalidate();
        });

        menuBar.add(settingsButton);
        menuBar.add(itemButton);
        menuBar.add(itemTextField);
        mainFrame.setJMenuBar(menuBar);

        loadTextItems(file);
        mainFrame.revalidate();
    }

    private void createTextItem(File file, String itemText){
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get(file.toURI()),
                StandardOpenOption.APPEND)){
            writer.write(itemText + ":0");
            writer.flush();
            loadTextItems(file);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static void loadTextItems(File file){
        try {
            mainPanel.removeAll();
            for (Scanner scanner = new Scanner(file); scanner.hasNext(); ) {
                String data = scanner.next();
                boolean completed = data.endsWith(":1");
                String text = data.substring(0,data.length()-2);
                listItem listItem = new listItem(text, completed, file);
                textItemDisplay(listItem);
            }
            mainFrame.revalidate();
            mainPanel.revalidate();
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    private static void textItemDisplay(listItem item){
        mainPanel.add(item.getPanel());
        mainFrame.revalidate();
    }
}

