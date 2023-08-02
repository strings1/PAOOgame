package main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    private Point LastClicked;
    public boolean click_stanga;
    @Override
    public void mouseClicked(MouseEvent e) {
        LastClicked=e.getPoint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton()==1)
        {
            LastClicked=e.getPoint();
            click_stanga=true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        click_stanga=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public Point getLastClicked()
    {
        return LastClicked;
    }

}
