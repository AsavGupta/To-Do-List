package ToDoList;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class MainWindow extends Window{
    final static JDesktopPane desktop = new JDesktopPane();

    private JPanel mainPanel;
    public static JLabel fileLabel;
    public static JPanel displayPanel;
    public static ArrayList<listPanel> listPanelArrayList = new ArrayList<>();
    public MainWindow(){
        try {
            UIManager.setLookAndFeel(new FlatGitHubDarkIJTheme());
        } catch (Exception e){
            e.printStackTrace();
        }
        Initialize();
    }
    private void Initialize(){
        //---Main Frame---
        JFrame mainFrame = new JFrame("To Do List");
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(800,500);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setIconImage(imageResizer(System.getProperty("user.dir") + "\\Images\\Calendar.png",36,36).getImage());
        mainFrame.setBackground(Color.BLACK);
        mainFrame.add(desktop);

        //---Main Panel---
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.setBackground(Color.BLACK);
        mainFrame.add(mainPanel);

        //---Menu Bar---
        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.X_AXIS));

        //--Settings Frame--
        JInternalFrame settingsFrame = new JInternalFrame("My Internal Frame", true, true,true, true);
        settingsFrame.setMaximumSize(new Dimension(300,200));
        settingsFrame.setVisible(false);
        settingsFrame.moveToFront();

        //--Theme Combo Box--
        JComboBox<LookAndFeel> themeList = new JComboBox<>(lafArray);
        themeList.setSelectedIndex(0);
        themeList.setFocusable(false);

        //--Settings Button--
        JButton settingsButton = new JButton();
        settingsButton.setIcon(imageResizer(System.getProperty("user.dir") + "\\Images\\Settings.png",20,20));
        settingsButton.setOpaque(false);
        settingsButton.setFocusable(false);
        settingsButton.setMargin(new Insets(0,0,0,0));
        settingsButton.setFont(menuFont);
        settingsButton.addActionListener(e -> settingsFrame.setVisible(!settingsFrame.isVisible()));
        desktop.add(settingsFrame);

        themeList.addActionListener(e -> {
            int themeIndex = themeList.getSelectedIndex();
            LookAndFeel myLaf = lafArray[themeIndex];
            try {
                UIManager.setLookAndFeel(myLaf);
            } catch (Exception err){
                err.printStackTrace();
            }
            mainPanel.revalidate();
        });

        //--Menu Button--
        JButton fileButton = new JButton("+");
        fileButton.setOpaque(false);
        fileButton.setFocusable(false);
        fileButton.setMargin(new Insets(0,0,0,0));
        fileButton.setForeground(Color.LIGHT_GRAY);
        fileButton.setToolTipText("New To Do List (Alt+Enter)");
        fileButton.setMnemonic(KeyEvent.VK_ENTER);
        fileButton.setFont(menuFont);

        //--Menu Text Field--
        JTextField fileTextField = new JTextField(10);
        fileTextField.setMaximumSize(new Dimension(200,1000));
        fileTextField.setFont(menuFont);
        fileTextField.setToolTipText("Hit Enter to Create To Do List");
        fileTextField.setVisible(false);

        //--Menu Label--
        fileLabel = new JLabel("");
        fileLabel.setFont(menuFont);

        //--Button Action--
        fileButton.addActionListener(e -> {
            if(!fileTextField.isVisible()){
                fileTextField.setVisible(true);
                fileButton.setForeground(Color.DARK_GRAY);
            }
            else{
                fileTextField.setVisible(false);
                fileButton.setForeground(Color.LIGHT_GRAY);
            }
            fileTextField.setText("");
            mainPanel.revalidate();
        });

        //--Text Field Action--
        fileTextField.addActionListener(e -> {
            createToDoList(fileTextField, fileLabel);
            if (!fileTextField.isVisible()){
                fileButton.setForeground(Color.BLACK);
            }
            mainPanel.revalidate();
        });

        //--Menu Bar--
        menuBar.add(settingsButton);
        menuBar.add(fileButton);
        menuBar.add(fileTextField);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(fileLabel);
        mainFrame.setJMenuBar(menuBar);

        //---Display Panel---
        displayPanel = new JPanel();
        displayPanel.setBackground(Color.DARK_GRAY);
        displayPanel.setLayout(new GridLayout(0,2));
        mainPanel.add(displayPanel);

        fileManager();
        mainPanel.add(displayPanel);
    }

    private void createToDoList(JTextField textField, JLabel label){
        label.setVisible(true);
        try {
            File myFile = new File(listDirectory + textField.getText() + ".txt");
            if (myFile.createNewFile()) {
                label.setText("File created: " + myFile.getName());
                textField.setVisible(false);
                listPanel listPanel = new listPanel(myFile);
                listPanelArrayList.add(listPanel);
                listPanelDisplay(listPanel);
            } else {
                label.setText("File already exists");
            }
        } catch (IOException error) {
            label.setText("An error occurred");
            error.printStackTrace();
        }
    }
    private void fileManager(){
        File dir = new File(listDirectory);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File myFile : directoryListing) {
                listPanel listPanel = new listPanel(myFile);
                listPanelArrayList.add(listPanel);
                listPanelDisplay(listPanel);
            }
        } else {
            fileLabel.setText("Files Failed to Load");
        }
    }

    private void listPanelDisplay(listPanel listPanel){
        displayPanel.add(listPanel.getListPanel());
    }

}
