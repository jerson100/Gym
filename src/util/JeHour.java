package util;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;

/**
 *
 * @author Jerson
 */
public class JeHour extends JLabel{

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");
    private static final String AUTHOR = "Creado por <br> Jerson Ramírez Ortiz";

    public JeHour() {
        setPreferredSize(new Dimension(250, 50));
        setSize(200, 30);
        setForeground(new java.awt.Color(102, 255, 255));
        setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        setFont(new java.awt.Font("Tahoma", 0, 14));
        
        Timer timer = new Timer();
        
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    setText("<html>"+
                        "<body style='text-align:center'>"+
                            FORMAT.format(new Date())+"<br>"+
                            AUTHOR+
                        "</body></html>");
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {}
            }
        };
        
        timer.schedule(task, 0,1000);
        
    }

}
