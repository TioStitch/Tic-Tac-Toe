package org.tiostitch.old.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

public class Jogo_da_Velha extends JFrame {

    private final JButton[] gameButton = new JButton[9];
    private final boolean[] usedSlots = new boolean[9];
    private final int[][] winCombos = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    private boolean playerRound = false;

    private Jogo_da_Velha() {
        setTitle("Jogo da Velha");
        setSize(600, 400);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);

        final int defaultSize = 50;
        final Font font = new Font("BOLD", Font.BOLD, 15);
        for (int repeat = 0; repeat < 3;) {
            final int coluna = (3 * repeat);

            for (int cont = 0; cont < 3; cont++) {
                gameButton[coluna + cont] = new JButton(" ");
                gameButton[coluna + cont].setFont(font);
                gameButton[coluna + cont].setBounds(cont * 50, repeat * 50, defaultSize, defaultSize);
                add(gameButton[coluna + cont]);
            }
            repeat++;
        }

        for (int i = 0; i < gameButton.length; i++) {
            gameButton[i].addActionListener(getAction(i));
        }
    }

    private void checkWin() {
        int contador = 0;
        for (int i = 0; i < 9; i++) {
            if (usedSlots[i]) {
                contador++;
            }
        }

        if (contador >= 9) {
            System.out.println("[Jogo da Velha] Empate!");
            return;
        }

        if (checkWinner("X")) {
            System.out.println("[Jogo da Velha] X ganhou!");
        } else if (checkWinner("Y")) {
            System.out.println("[Jogo da Velha] O ganhou!");
        }
    }

    private boolean checkWinner(String letter) {
        for (int[] combo : winCombos) {
            if (gameButton[combo[0]].getText().equals(letter)
                    && gameButton[combo[1]].getText().equals(letter)
                    && gameButton[combo[2]].getText().equals(letter)) {
                return true;
            }
        }
        return false;
    }

    private ActionListener getAction(int i) {
        return actionEvent -> {
            if (gameButton[i] == null || usedSlots[i]) {
                return;
            }

            final JButton button = gameButton[i];
            final String text = button.getText();

            if (text.equals(" ")) {
                button.setText("X");
                usedSlots[i] = true;
                toggle();
                bootPlay();
            }
            checkWin();
        };
    }

    private void toggle() {
        playerRound = false;
    }

    private void bootPlay() {
        while (!playerRound) {
            final Random random = new Random();
            final int i = random.nextInt(8);

            boolean isUsing = usedSlots[i];

            if (!isUsing) {
                gameButton[i].setText("O");
                usedSlots[i] = true;
                playerRound = true;
                return;
            }
        }
    }


    public static void main(String[] args) {
        new Jogo_da_Velha();
    }
}