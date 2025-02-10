package visao;

import java.awt.GridLayout;

import javax.swing.JPanel;

import modelo.Tabuleiro;

//esse JPanel acumula uma série de botões que vou utilizar no jogo
public class PainelTabuleiro extends JPanel{

    public PainelTabuleiro(Tabuleiro tabuleiro){
        
        setLayout(new GridLayout(
            tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCada(c -> add(new BotaoCampo(c)));

        tabuleiro.registrarObservador(e -> {
            //TODO mostrar resultado pro usuário!
        });
    }
}
