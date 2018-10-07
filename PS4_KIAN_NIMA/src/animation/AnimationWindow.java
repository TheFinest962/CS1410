// Written by Joe Zachary for CS 1410, September 2015

package animation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Provides a top-level animation window
 */
@SuppressWarnings("serial")
public class AnimationWindow extends JFrame implements ActionListener
{
    /**
     * Animation refresh rate in milliseconds
     */
    public final static int REFRESH_RATE = 5;

    private DrawingArea area; // JPanel that contains animation
    private JButton start; // Start button
    private JButton reset; // Reset button
    private Timer timer; // Animation timer

    /**
     * Launches an AnimationWindow
     */
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> {
            AnimationWindow g = new AnimationWindow();
            g.setSize(1000, 1000);
            g.setVisible(true);
        });
    }

    /**
     * Constructs a custom CS 1410 graphics window
     */
    public AnimationWindow ()
    {
        setTitle("CS 1410 Animation Window");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        timer = new Timer(REFRESH_RATE, this);
        JLabel time = new JLabel();
        area = new DrawingArea(time);
        start = new JButton("Play");
        start.addActionListener(this);
        reset = new JButton("Reset");
        reset.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(area, "Center");
        JPanel buttons = new JPanel();
        buttons.add(start);
        buttons.add(reset, "South");
        panel.add(buttons, "South");
        time.setText("xxx");
        JPanel timer = new JPanel();
        timer.add(time);
        panel.add(timer, "North");
        Font timerFont = time.getFont();
        Font font = new Font(timerFont.getName(), timerFont.getStyle(), timerFont.getSize() * 3);
        time.setFont(font);
        start.setFont(font);
        reset.setFont(font);
        setContentPane(panel);
    }

    /**
     * Handles button presses and timer events.
     */
    public void actionPerformed (ActionEvent e)
    {

        // Timer to generate a new frame
        if (e.getSource() == timer)
        {
            synchronized (area)
            {
                area.tick(REFRESH_RATE);
            }
            area.repaint();
        }

        // Start button has been pressed
        else if (e.getSource() == start)
        {
            if (start.getText().equals("Play"))
            {
                start.setText("Pause");
                timer.start();
            }
            else
            {
                start.setText("Play");
                timer.stop();
            }
        }

        // Stop button has been pressed
        else if (e.getSource() == reset)
        {
            timer.stop();
            synchronized (area)
            {
                area.clearTimer();
            }
            start.setText("Play");
            area.repaint();
        }
    }
}
