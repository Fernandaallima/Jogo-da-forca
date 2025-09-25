import javax.swing.*;
import java.awt.*;

public class PainelForca extends JPanel {
    private int erros = 0; // Quantidade de erros do jogador

    public void setErros(int erros) {
        this.erros = erros;
        repaint(); // Força o painel a se redesenhar
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3)); // Linhas mais grossas

        // Desenhar a base da forca
        g2d.drawLine(50, 250, 150, 250); // Base horizontal
        g2d.drawLine(100, 250, 100, 50); // Mastro
        g2d.drawLine(100, 50, 200, 50);  // Braço
        g2d.drawLine(200, 50, 200, 75);  // Corda

        // Desenhar as partes do boneco com base no número de erros
        if (erros >= 1) { // Cabeça
            g2d.drawOval(175, 75, 50, 50);
        }
        if (erros >= 2) { // Corpo
            g2d.drawLine(200, 125, 200, 180);
        }
        if (erros >= 3) { // Braço esquerdo
            g2d.drawLine(200, 135, 170, 160);
        }
        if (erros >= 4) { // Braço direito
            g2d.drawLine(200, 135, 230, 160);
        }
        if (erros >= 5) { // Perna esquerda
            g2d.drawLine(200, 180, 170, 220);
        }
        if (erros >= 6) { // Perna direita
            g2d.drawLine(200, 180, 230, 220);
        }
    }
}