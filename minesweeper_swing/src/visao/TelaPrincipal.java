package visao;

import javax.swing.JFrame;

import modelo.Tabuleiro;

public class TelaPrincipal extends JFrame{
    
    public TelaPrincipal(){
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);
        PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);

        add(painelTabuleiro);
        //ou add(new PainelTabuleiro(tabuleiro));

        setTitle("Minesweeper");
        setSize(690,438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
