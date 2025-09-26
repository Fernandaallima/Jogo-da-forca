import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class JogoDaForcaGUI extends JFrame implements ActionListener {

    // Componentes da interface
    private JLabel labelPalavra;
    private JLabel labelMensagem;
    private JLabel labelMensagemVitoria;
    private JLabel labelDica;
    private JTextField campoEntrada;
    private JButton botaoTentar;
    private PainelForca painelForca; 

    // Lógica do jogo
    private HashMap<String, String> palavrasComDicas;
    private ArrayList<String> listaDePalavras;
    private Random random;
    private String palavraChave;
    private String palavraAdivinhada;
    private String letrasUsadas;
    private int tentativasRestantes;
    private final int MAX_TENTATIVAS = 6; 

    // Construtor da classe
    public JogoDaForcaGUI() {
        super("Jogo da Forca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400); 
        setLayout(new BorderLayout()); 

        // Lista de palavras e dicas
        palavrasComDicas = new HashMap<>();
        palavrasComDicas.put("MOUSE", "Usado para clicar e mover na tela.");
        palavrasComDicas.put("CODIGO", "As instruções que um programador escreve.");
        palavrasComDicas.put("LINK", "Um endereço para uma página na internet.");
        palavrasComDicas.put("BYTES", "Unidade básica de informação no computador.");
        palavrasComDicas.put("TELA", "Onde você vê as imagens do computador.");
        palavrasComDicas.put("ROBOT", "Máquina programável que faz tarefas.");
        palavrasComDicas.put("EMAIL", "Mensagem digital enviada pela internet.");
        palavrasComDicas.put("NUVEM", "Espaço de armazenamento virtual.");
        
        listaDePalavras = new ArrayList<>(palavrasComDicas.keySet());
        random = new Random();

        // Inicializa os componentes da interface
        labelPalavra = new JLabel("");
        labelMensagem = new JLabel("Digite sua tentativa:");
        labelDica = new JLabel("");
        campoEntrada = new JTextField(10);
        botaoTentar = new JButton("Tentar");
        
        painelForca = new PainelForca(); 
        
        labelMensagemVitoria = new JLabel("");
        labelMensagemVitoria.setFont(new Font("Arial", Font.BOLD, 28));

        labelPalavra.setHorizontalAlignment(JLabel.CENTER);
        labelMensagem.setHorizontalAlignment(JLabel.CENTER);
        labelMensagemVitoria.setHorizontalAlignment(JLabel.CENTER);
        labelDica.setHorizontalAlignment(JLabel.CENTER);

        JPanel painelTextos = new JPanel(new GridLayout(4, 1));
        painelTextos.add(labelDica);
        painelTextos.add(labelPalavra);
        painelTextos.add(labelMensagem);
        painelTextos.add(labelMensagemVitoria);

        JPanel painelEntrada = new JPanel(new FlowLayout());
        painelEntrada.add(campoEntrada);
        painelEntrada.add(botaoTentar);

        add(painelTextos, BorderLayout.NORTH);
        add(painelForca, BorderLayout.CENTER); 
        add(painelEntrada, BorderLayout.SOUTH);

        botaoTentar.addActionListener(this);

        setLocationRelativeTo(null);
        setVisible(true);

        iniciarNovoJogo();
    }

    private void iniciarNovoJogo() {
        palavraChave = listaDePalavras.get(random.nextInt(listaDePalavras.size()));
        String dica = palavrasComDicas.get(palavraChave);
        
        palavraAdivinhada = "";
        letrasUsadas = "";
        tentativasRestantes = MAX_TENTATIVAS;

        for (int i = 0; i < palavraChave.length(); i++) {
            palavraAdivinhada += "_";
        }
        
        labelPalavra.setText(palavraAdivinhada);
        labelDica.setText("Dica: " + dica);
        labelMensagem.setText(String.format("Tentativas restantes: %d", tentativasRestantes));
        
        labelMensagemVitoria.setText("");
        
        painelForca.setErros(0);
        botaoTentar.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String entrada = campoEntrada.getText().toUpperCase();
        campoEntrada.setText("");

        if (entrada.isEmpty()) {
            labelMensagem.setText("Por favor, digite uma letra ou a palavra inteira.");
            return;
        }

        if (entrada.length() > 1) {
            if (entrada.equals(palavraChave)) {
                int tentativasUsadas = MAX_TENTATIVAS - tentativasRestantes;
                labelPalavra.setText(palavraChave);
                
                labelMensagemVitoria.setText("Parabéns! Você venceu!");
                labelMensagem.setText("A palavra era: " + palavraChave + ". Você precisou de " + tentativasUsadas + " tentativas.");
                
                botaoTentar.setEnabled(false);
            } else {
                tentativasRestantes--;
                int erros = MAX_TENTATIVAS - tentativasRestantes;
                painelForca.setErros(erros); 
                labelMensagem.setText("Palavra incorreta. Você perdeu uma tentativa. Tentativas restantes: " + tentativasRestantes);
            }
        } else {
            char letraTentada = entrada.charAt(0);
            if (letrasUsadas.indexOf(letraTentada) >= 0) {
                labelMensagem.setText("Você já tentou a letra '" + letraTentada + "'.");
                return;
            }
            letrasUsadas += letraTentada;

            if (palavraChave.indexOf(letraTentada) >= 0) {
                String novaPalavraAdivinhada = "";
                for (int i = 0; i < palavraChave.length(); i++) {
                    novaPalavraAdivinhada += letrasUsadas.indexOf(palavraChave.charAt(i)) >= 0 ? palavraChave.charAt(i) : "_";
                }
                palavraAdivinhada = novaPalavraAdivinhada;
                labelPalavra.setText(palavraAdivinhada);

                if (!palavraAdivinhada.contains("_")) {
                    int tentativasUsadas = MAX_TENTATIVAS - tentativasRestantes;
                    
                    labelMensagemVitoria.setText("Parabéns! Você venceu!");
                    labelMensagem.setText("A palavra era: " + palavraChave + ". Você precisou de " + tentativasUsadas + " tentativas.");
                    
                    botaoTentar.setEnabled(false);
                } else {
                    labelMensagem.setText("Muito bom! Tentativas restantes: " + tentativasRestantes);
                }
            } else {
                
                tentativasRestantes--;
                int erros = MAX_TENTATIVAS - tentativasRestantes;
                
                painelForca.setErros(erros); 
                
                labelMensagem.setText("Infelizmente, você errou. Tentativas restantes: " + tentativasRestantes);
            }
        }

        if (tentativasRestantes <= 0) {
            labelMensagemVitoria.setText("GAME OVER!");
            labelMensagem.setText("NÃO FOI DESSA VEZ. A palavra era: " + palavraChave);
            botaoTentar.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JogoDaForcaGUI());
    }

}
