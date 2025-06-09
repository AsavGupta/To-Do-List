package ToDoList;

import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;

import javax.swing.*;
import java.awt.*;

public class Window {
    Font menuFont = new Font("Arial", Font.BOLD, 18);
    final LookAndFeel[] lafArray = {new FlatGitHubDarkIJTheme(), new FlatGitHubIJTheme()};
    String listDirectory = System.getProperty("user.dir") + "\\src\\main\\java\\ToDoList\\Lists\\";
    public static ImageIcon imageResizer(String imageUrl, int width, int height){
        ImageIcon icon = new ImageIcon(imageUrl);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
        return icon;
    }
}
