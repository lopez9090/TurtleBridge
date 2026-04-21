package p02.game;

import p02.pres.SevenSegmentDigit;

public class ScoreManager {

    private int score = 0;
    private SevenSegmentDigit ones;
    private SevenSegmentDigit tens;
    private SevenSegmentDigit hundreds;

    public void setDigits(SevenSegmentDigit ones, SevenSegmentDigit tens, SevenSegmentDigit hundreds) {
        this.ones = ones;
        this.tens = tens;
        this.hundreds = hundreds;
        updateDigits();
    }

    public void addPoints(int points) {
        score += points;
        if (score > 999) score = 999;
        updateDigits();
    }

    public void reset() {
        score = 0;
        updateDigits();
        if (ones != null) ones.start();
        if (tens != null) tens.start();
        if (hundreds != null) hundreds.start();
    }

    public int getScore() {
        return score;
    }

    public void updateDigits() {
        int s = score;
        if (ones != null) ones.setValue(s % 10);
        if (tens != null) tens.setValue((s / 10) % 10);
        if (hundreds != null) hundreds.setValue((s / 100) % 10);
    }
}