package agh.cs.project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickListener implements ActionListener {
    OperationsPanel operator;
    public ButtonClickListener (OperationsPanel operator) {
        this.operator = operator;
    }
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("PauseAll")) {

            operator.resumeButton.setEnabled(true);
            operator.pauseButton.setEnabled(false);
            operator.parent.paused = true;
        }
        if (command.equals("ResumeAll")) {

            operator.resumeButton.setEnabled(false);
            operator.pauseButton.setEnabled(true);
            operator.parent.paused = false;
        }
    }
}
