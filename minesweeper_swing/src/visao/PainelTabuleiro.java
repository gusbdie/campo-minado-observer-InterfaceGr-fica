package visao;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import modelo.Tabuleiro;

//esse JPanel acumula uma série de botões que vou utilizar no jogo
public class PainelTabuleiro extends JPanel{

    public PainelTabuleiro(Tabuleiro tabuleiro){
        
        setLayout(new GridLayout(
            tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCada(c -> add(new BotaoCampo(c)));

        tabuleiro.registrarObservador(e -> {

            //serve para que a interface primeiro marque um X na bomba que
            //cliquei e somente dps mostre o resultado
            SwingUtilities.invokeLater(() -> {
                if(e.isGanhou()) {
                    JOptionPane.showMessageDialog(this, "Ganhou");
                }else {
                    JOptionPane.showMessageDialog(this, "Perdeu");
                }

                tabuleiro.reiniciar();//preciso notificar a interface ainda
            });
        });
    }
}
