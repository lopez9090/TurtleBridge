package p02.game;

public interface DigitEventListener {
    void onStart(StartEvent e);
    void onPlusOne(PlusOneEvent e);
    void onReset(ResetEvent e);
}
