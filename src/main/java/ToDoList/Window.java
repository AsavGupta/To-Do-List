package ToDoList;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Window {
    Font menuFont = new Font("Arial", Font.BOLD, 18);
    final LookAndFeel[] lafArray = {new FlatGitHubDarkIJTheme(), new FlatGitHubIJTheme()};
    String listDirectory = System.getProperty("user.home") + File.separator + ".ToDoList" + File.separator + "Lists";

    public static ImageIcon imageResizer(String imagePath, int width, int height){
        java.net.URL imgURL = Window.class.getResource(imagePath);
        if (imgURL == null) {
            throw new RuntimeException("Resource not found on classpath: " + imagePath);
        }
        ImageIcon icon = new ImageIcon(imgURL);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }
}
