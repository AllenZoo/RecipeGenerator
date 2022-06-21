package ui;

import model.EventLog;
import model.Event;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

// Represents the app frame used as the base of the ui.
public class AppFrame extends JFrame {
    private static int WIDTH = 800;
    private static int HEIGHT = 800;

    // EFFECTS: creates a new app frame with dimension WIDTH x HEIGHT
    public AppFrame() {
        this.setTitle("Meal Suggester App"); // sets title of frame
        this.setResizable(false); //prevent frame from being resized
        this.setSize(WIDTH, HEIGHT); // sets the x-dimension, and y-dimension of frame
        this.setVisible(true);
        this.getContentPane().setBackground(new Color(123, 50, 250)); // change colour of background

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //exit out of application

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {

                System.out.println("Event log: ");
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }

                //THEN you can exit the program
                System.exit(0);
            }
        });
    }

}
