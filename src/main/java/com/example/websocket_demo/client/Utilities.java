package com.example.websocket_demo.client;

import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Utilities {
    public static final Color transparentColor = new Color(0,0,0,0);
    public static final Color primaryColor = Color.decode("#2F2D2D");
    public static final Color secondaryColor = Color.decode("#484444");
    public static final Color textColor = Color.white;

    public static EmptyBorder addPadding(int top, int left, int bottom, int right){
        return new EmptyBorder(top, left, bottom,right);
    }
}
