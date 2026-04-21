package p02.pres;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import p02.game.DigitEventListener;
import p02.game.StartEvent;
import p02.game.PlusOneEvent;
import p02.game.ResetEvent;

public class SevenSegmentDigit extends JPanel {

    private int value = 0;
    private final Color active = Color.RED;
    private final Color inactive = Color.DARK_GRAY;

    private final List<DigitEventListener> listeners = new ArrayList<>();

    public SevenSegmentDigit() {
        setPreferredSize(new Dimension(60, 100));
        setOpaque(false);
    }
    public void addDigitEventListener(DigitEventListener l) {
        listeners.add(l);
    }

    private void fireStartEvent() {
        StartEvent event = new StartEvent(this);
        for (DigitEventListener l : listeners) l.onStart(event);
    }

    private void firePlusOneEvent() {
        PlusOneEvent event = new PlusOneEvent(this);
        for (DigitEventListener l : listeners) l.onPlusOne(event);
    }

    private void fireResetEvent() {
        ResetEvent event = new ResetEvent(this);
        for (DigitEventListener l : listeners) l.onReset(event);
    }

    public void start() {
        value = 0;
        repaint();
        fireStartEvent();
    }

    public void plusOne() {
        value = (value + 1) % 10;
        repaint();
        firePlusOneEvent();
    }

    public void reset() {
        value = 0;
        repaint();
        fireResetEvent();
    }

    public void setValue(int val) {
        value = Math.max(0, Math.min(9, val));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawDigit(g, value);
    }

    private void drawDigit(Graphics g, int v) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(8));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int s = 10;

        int[][] segments = {
                {s, s, w - s, s},
                {w - s, s, w - s, h / 2 - s},
                {w - s, h / 2 + s, w - s, h - s},
                {s, h - s, w - s, h - s},
                {s, h / 2 + s, s, h - s},
                {s, s, s, h / 2 - s},
                {s, h / 2, w - s, h / 2}
        };

        int[][] digits = {
                {1,1,1,1,1,1,0}, // 0
                {0,1,1,0,0,0,0}, // 1
                {1,1,0,1,1,0,1}, // 2
                {1,1,1,1,0,0,1}, // 3
                {0,1,1,0,0,1,1}, // 4
                {1,0,1,1,0,1,1}, // 5
                {1,0,1,1,1,1,1}, // 6
                {1,1,1,0,0,0,0}, // 7
                {1,1,1,1,1,1,1}, // 8
                {1,1,1,1,0,1,1}  // 9
        };

        for (int i = 0; i < 7; i++) {
            g2.setColor(digits[v][i] == 1 ? active : inactive);
            g2.drawLine(segments[i][0], segments[i][1], segments[i][2], segments[i][3]);
        }
    }
}
