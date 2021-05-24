package gui;

import app.ClausesSet;
import javafx.scene.layout.Pane;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Frame extends JFrame {
    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JPanel panel1;

    public Frame(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 500);
        setResizable(false);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception ignored) {
        }


        Panel panel = new Panel();
        add(panel);







    }
}