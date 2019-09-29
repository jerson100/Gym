package util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Jerson Ramírez Ortiz
 */
public final class JeTextPlaceHolder extends JTextField implements FocusListener {

    private String textoPlaceholder;
    private String texto;
    private Color colorPlaceHolder;
    private Color colorNormal;
    private boolean holderActive = false;

    public JeTextPlaceHolder() {
        this("");
    }

    public JeTextPlaceHolder(String textoPlaceHolder) {
        this(textoPlaceHolder, new Color(206,206,206), Color.BLACK);
    }

    public JeTextPlaceHolder(String textoPlaceHolder, Color colorPlaceHolde, Color colorNorma) {
        this.textoPlaceholder = textoPlaceHolder;
        this.texto = "";
        this.colorPlaceHolder = colorPlaceHolde;
        this.colorNormal = colorNorma;
        holderActive = true;
        this.setText(textoPlaceHolder);
        this.setPreferredSize(new Dimension(200, 25));
        this.setSize(200, 25);
        this.setOpaque(true);
        this.addFocusListener(this);

    }

    @Override
    public void focusGained(FocusEvent e) {
        setText(texto);
        setForeground(colorNormal);
    }

    @Override
    public void focusLost(FocusEvent e) {
        texto = getText();
        if (texto.isEmpty()) {
            holderActive = true;
            setText(textoPlaceholder);
            holderActive = true;
            setForeground(colorPlaceHolder);
        } else {
            setText(texto);
            setForeground(colorNormal);
        }
    }

    @Override
    public void setForeground(Color c) {
        if (!holderActive) {
            colorNormal = c;
        }
        holderActive = false;
        super.setForeground(c);
    }

    /**
     * Establece el texto del placeholder
     * @param textoPlaceHolder <b>String</b> La cadena de texto del placeholder
     **/
    public void setTextoPlaceHolder(String textoPlaceHolder) {
        this.textoPlaceholder = textoPlaceHolder;
        holderActive = true;
        setText(textoPlaceHolder);
    }

    public String getTextoPlaceHolder() {
        return this.textoPlaceholder;
    }

    public Color getColorPlaceHolder() {
        return colorPlaceHolder;
    }

    /**
     * Establece el color del placeholder
     * @param colorPlaceHolder  <b>Color</b> Color del placeHolder
     **/
    public void setColorPlaceHolder(Color colorPlaceHolder) {
        this.colorPlaceHolder = colorPlaceHolder;
        holderActive = true;
        setForeground(colorPlaceHolder);
    }

    @Override
    public void setText(String txt) {
        if (!holderActive) {
            this.texto = txt;
        }
        holderActive = false;
        super.setText(txt);
    }

    @Override
    public String getText() {
        String cnPrev = super.getText();
        if (textoPlaceholder.equals(cnPrev)) {
            return "";
        } else {
            return cnPrev;
        }
    }

}
