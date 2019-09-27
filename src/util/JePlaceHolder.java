package util;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Jerson
 */
public class JePlaceHolder extends JTextField implements FocusListener {

    private String textoPlaceholder;
    private String texto;
    private Color colorPlaceHolder;
    private Color colorNormal;

    public JePlaceHolder(String textoPlaceHolder) {
        this(textoPlaceHolder,Color.GREEN,Color.BLACK);
    }
    
    public JePlaceHolder(String textoPlaceHolder,Color colorPlaceHolder,Color colorNormal){
        this.textoPlaceholder = textoPlaceHolder;
        this.texto = "";
        this.colorPlaceHolder = colorPlaceHolder;
        this.colorNormal = colorNormal;
        this.setForeground(colorPlaceHolder);
        this.setText(textoPlaceHolder);
        this.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        setForeground(colorNormal);
        if(texto.isEmpty()){
            setText(null);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().isEmpty()){
            texto = "";
            setText(textoPlaceholder);
            setForeground(colorPlaceHolder);
        }else{
            texto = this.getText();
            setForeground(colorNormal); 
        }
    }

    public void setTextoPlaceHolder(String textoPlaceHolder){
        this.textoPlaceholder = textoPlaceHolder;
    }
    
    public String getTextoPlaceHolder(){
        return this.textoPlaceholder;
    }

    public Color getColorPlaceHolder() {
        return colorPlaceHolder;
    }

    public void setColorPlaceHolder(Color colorPlaceHolder) {
        this.colorPlaceHolder = colorPlaceHolder;
    }

    public Color getColorNormal() {
        return colorNormal;
    }

    public void setColorNormal(Color colorNormal) {
        this.colorNormal = colorNormal;
    }
    
    @Override
    public String getText(){
       if(this.textoPlaceholder.equals(super.getText())){
          return "";
       }else{
          return super.getText();
       }
    }
    
    /*
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setSize(500,300);
        f.setLocationRelativeTo(null);
        JePlaceHolder e = new JePlaceHolder("Ingrese su nombre");
        JTextField e2 = new JTextField("");
        JButton btn = new JButton("Prueba");
        btn.addActionListener((ActionEvent) -> {
            System.out.println(e.getText());
        });
        btn.setBounds(150, 200,100,30);
        e.setSize(120, 30);
        e2.setSize(120, 30);
        e2.setLocation(80, 80);
        f.setVisible(true);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(e);
        f.add(e2);
        f.add(btn);
    }
    */
}
