package agh.cs.project1;


import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class OperationsPanel extends JPanel {
    SimulationRunner parent;
    JButton pauseButton;
    JButton resumeButton;
    ButtonClickListener btnListener;

    public OperationsPanel (SimulationRunner parent) {
        this.parent = parent;
        setBackground(Color.LIGHT_GRAY);

        this.btnListener = new ButtonClickListener(this);

        this.pauseButton = new JButton("Pause");
        pauseButton.setEnabled(true);
        pauseButton.setActionCommand("PauseAll");
        pauseButton.addActionListener(btnListener);

        this.resumeButton = new JButton("Resume");
        resumeButton.setEnabled(false);
        resumeButton.setActionCommand("ResumeAll");
        resumeButton.addActionListener(btnListener);

        add(pauseButton);
        add(resumeButton);
    }
}
