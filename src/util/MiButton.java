package util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

/**
 *
 * @author RojeruSan
 */
public class MiButton extends JButton implements MouseListener{

    private Color colorHover = new Color(153,0,0);
    private Color colorPressed = new Color(0,0,0);
    private Color colorNormal = new Color(255,0,0);
    
    private Color colorTextHover = new Color(255, 255, 255);
    private Color colorTextPressed = new Color(255, 255, 255);
    private Color colorTextNormal = new Color(255, 255, 255);
    
    public MiButton() {
        this.setBorder(null);
        this.setContentAreaFilled(false);
        this.setOpaque(true);
        this.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.setBackground(Color.red);
        this.setForeground(Color.white);
        this.setPreferredSize(new Dimension(200, 40));
        this.setSize(200, 40);
        this.setCursor(new Cursor(12));       
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.setBackground(this.colorPressed);
        this.setForeground(this.colorTextPressed);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.setBackground(this.colorHover);
        this.setForeground(this.colorTextHover);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground(this.colorHover);
        this.setForeground(this.colorTextHover);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground(this.colorNormal);
        this.setForeground(this.colorTextNormal);
    }

    public Color getColorHover() {
        return colorHover;
    }

    public void setColorHover(Color colorHover) {
        this.colorHover = colorHover;
    }

    public Color getColorNormal() {
        return colorNormal;
    }

    public void setColorNormal(Color colorNormal) {
        this.colorNormal = colorNormal;
        this.setBackground(this.colorNormal);
    }

    public Color getColorTextHover() {
        return colorTextHover;
    }

    public void setColorTextHover(Color colorTextHover) {
        this.colorTextHover = colorTextHover;
    }

    public Color getColorTextNormal() {
        return colorTextNormal;
    }

    public void setColorTextNormal(Color colorTextNormal) {
        this.colorTextNormal = colorTextNormal;
        this.setForeground(this.colorTextNormal);
    }

    public Color getColorPressed() {
        return colorPressed;
    }

    public void setColorPressed(Color colorPressed) {
        this.colorPressed = colorPressed;
        this.setBackground(this.colorPressed);
    }

    public Color getColorTextPressed() {
        return colorTextPressed;
    }

    public void setColorTextPressed(Color colorTextPressed) {
        this.colorTextPressed = colorTextPressed;
        this.setForeground(this.colorTextPressed);
    }
    
}
