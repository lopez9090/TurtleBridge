package p02.game;

import p02.pres.BoardPanel;
import p02.pres.SevenSegmentDigit;
import p02.pres.BoardTableModel;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import javax.swing.Timer;

public class Board implements KeyListener {

    private boolean carryingPackage = true;
    private boolean hasPickedUpPackage = true;
    private boolean isJumping = false;
    private boolean gameOver = false;
    private boolean started = false;
    private boolean inDeliveryAnimation = false;
    private int playerX = 0;
    private int playerY = 0;
    private javax.swing.Timer gameLoopTimer;
    private int tickInterval = 3000;
    private final int MIN_INTERVAL = 1000;
    private int tickCount = 0;

    private final int[][] grid = new int[12][5];
    private final List<Point> fishList = new ArrayList<>();
    private final Set<Integer> submergedTurtles = new HashSet<>();
    private final Random random = new Random();
    private final ScoreManager scoreManager = new ScoreManager();

    private SevenSegmentDigit ones, tens, hundreds;
    private BoardPanel panel;
    private BoardTableModel tableModel;

    public void setPanel(BoardPanel panel) {
        this.panel = panel;
    }

    public BoardPanel getPanel() {
        return panel;
    }

    public void setTableModel(BoardTableModel model) {
        this.tableModel = model;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setScore(SevenSegmentDigit ones, SevenSegmentDigit tens, SevenSegmentDigit hundreds) {
        this.ones = ones;
        this.tens = tens;
        this.hundreds = hundreds;
        scoreManager.setDigits(ones, tens, hundreds);
    }
    public SevenSegmentDigit getHundredsDigit() {
        return hundreds;
    }


    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWon() {
        return scoreManager.getScore() >= 999;
    }

    private void updateGridCell(int x, int y, int value) {
        grid[x][y] = value;
        if (tableModel != null) tableModel.updateCell(x, y);
    }

    private void updateScoreDisplay() {
        int score = scoreManager.getScore();
        if (ones != null && tens != null && hundreds != null) {
            ones.setValue(score % 10);
            tens.setValue((score / 10) % 10);
            hundreds.setValue((score / 100) % 10);
        }
    }

    public void tick() {
        if (!started || gameOver) return;

        List<Point> toRemove = new ArrayList<>();
        List<Point> toAdd = new ArrayList<>();
        for (Point f : fishList) {
            if (f.y == 2) {
                toRemove.add(f);
                submergedTurtles.add(f.x);
                System.out.println("Żółw na " + f.x + " zanurza się");
            } else {
                toAdd.add(new Point(f.x, f.y - 1));
                toRemove.add(f);
            }
        }
        fishList.removeAll(toRemove);
        fishList.addAll(toAdd);

        List<Integer> toRestore = new ArrayList<>();
        for (int x : submergedTurtles) {
            if (random.nextDouble() < 0.25) {
                toRestore.add(x);
                System.out.println("Żółw na " + x + " wynurza się");
            }
        }
        submergedTurtles.removeAll(toRestore);

        int score = scoreManager.getScore();
        int fishAllowed = (score % 10 != 0 ? 1 : 0)
                + ((score / 10) % 10 != 0 ? 1 : 0)
                + ((score / 100) % 10 != 0 ? 1 : 0);

        if (fishList.size() < fishAllowed) {
            int[] possibleX = {2, 4, 6, 8, 10};
            int x = possibleX[random.nextInt(possibleX.length)];
            fishList.add(new Point(x, 4));
        }

        if (playerY == 1) {
            boolean onTurtle = Arrays.asList(2, 4, 6, 8, 10).contains(playerX);
            if (!onTurtle || submergedTurtles.contains(playerX)) {
                System.out.println("Gracz spadł na polu: " + playerX);
                gameOver = true;
                if (panel != null) panel.repaint();
                return;
            }
        }

        if (playerX == 0 && playerY == 0 && !carryingPackage) {
            carryingPackage = true;
            hasPickedUpPackage = true;
            scoreManager.addPoints(8);
            if (scoreManager.getScore() >= 999) endGame();
            updateScoreDisplay();
        }

        for (int x = 0; x < 12; x++) {
            for (int y = 0; y < 5; y++) updateGridCell(x, y, 0);
        }

        for (int x : new int[]{2, 4, 6, 8, 10}) {
            if (!submergedTurtles.contains(x)) updateGridCell(x, 1, 2);
        }

        updateGridCell(playerX, playerY, 1);
        for (Point f : fishList) updateGridCell(f.x, f.y, 3);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyChar() == 's') {
            resetGame();
            startGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!started || gameOver || inDeliveryAnimation) return;
        if (e.getKeyChar() == 'a') moveLeft();
        else if (e.getKeyChar() == 'd') moveRight();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private void repaintAndTick() {
        tick();
        if (panel != null) panel.repaint();
    }

    private void moveLeft() {
        if (isJumping || playerX <= 0 || inDeliveryAnimation) return;
        playerX--;
        playerY = 0;
        System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
        repaintAndTick();

        if (playerX == 1) {
            isJumping = true;
            inDeliveryAnimation = true;
            new Timer(500, e -> {
                playerX = 0;
                playerY = 0;
                isJumping = false;
                inDeliveryAnimation = false;
                ((Timer) e.getSource()).stop();
                System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
                repaintAndTick();
            }).start();
        } else if (playerX % 2 == 1 && playerX > 0) {
            isJumping = true;
            new Timer(500, e -> {
                playerX--;
                playerY = 1;
                isJumping = false;
                ((Timer) e.getSource()).stop();
                System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
                repaintAndTick();
            }).start();
        }
    }

    private void moveRight() {
        if (isJumping || playerX == 11 || inDeliveryAnimation) return;
        if (playerX == 10 && playerY == 1 && (!carryingPackage || !hasPickedUpPackage)) return;

        if (playerX == 10 && playerY == 1 && carryingPackage && hasPickedUpPackage) {
            isJumping = true;
            inDeliveryAnimation = true;
            new Timer(500, e -> {
                playerX = 11;
                playerY = 0;
                System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
                ((Timer) e.getSource()).stop();
                repaintAndTick();

                new Timer(500, ev -> {
                    carryingPackage = false;
                    hasPickedUpPackage = false;
                    scoreManager.addPoints(3);
                    if (scoreManager.getScore() >= 999) endGame();
                    updateScoreDisplay();
                    playerX = 10;
                    playerY = 1;
                    isJumping = false;
                    inDeliveryAnimation = false;
                    ((Timer) ev.getSource()).stop();
                    System.out.println("Gracz rusza napole: (" + playerX + ", " + playerY + ")");
                    repaintAndTick();
                }).start();
            }).start();
            return;
        }

        playerX++;
        playerY = 0;
        System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
        repaintAndTick();

        if (playerX % 2 == 1 && playerX < 11) {
            isJumping = true;
            new Timer(500, e -> {
                playerX++;
                playerY = 1;
                isJumping = false;
                ((Timer) e.getSource()).stop();
                System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
                repaintAndTick();
            }).start();
        }
    }

    public void resetGame() {
        if (gameLoopTimer != null) {
            gameLoopTimer.stop();
        }

        tickInterval = 3000;
        tickCount = 0;

        started = false;
        gameOver = false;
        playerX = 0;
        playerY = 0;
        isJumping = false;
        inDeliveryAnimation = false;
        hasPickedUpPackage = true;
        carryingPackage = true;
        fishList.clear();
        submergedTurtles.clear();
        scoreManager.reset();
        updateScoreDisplay();
        System.out.println("Gracz rusza na pole: (" + playerX + ", " + playerY + ")");
        tick();
    }

    public void startGame() {
        if (!started) {
            started = true;
            gameOver = false;

            gameLoopTimer = new Timer(tickInterval, e -> {
                tick();
                if (panel != null) panel.repaint();

                tickCount++;
                if (tickCount % 5 == 0 && tickInterval > MIN_INTERVAL) {
                    tickInterval = Math.max(MIN_INTERVAL, tickInterval - 100);
                    gameLoopTimer.setDelay(tickInterval);
                    System.out.println("Przyspieszenie ticku: " + tickInterval + " ms");
                }
            });
            gameLoopTimer.start();

            tick();
            if (panel != null) panel.repaint();
        }
    }
    public void endGame() {
        if (!gameOver) {
            gameOver = true;
            System.out.println("Koniec gry");
            if (gameLoopTimer != null) {
                gameLoopTimer.stop();
            }
        }
    }}
