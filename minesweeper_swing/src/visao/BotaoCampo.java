package visao;

import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

//isso é basicamente uma representação visual da classe Campo
public class BotaoCampo extends JButton implements CampoObservador, MouseListener{

    private Campo campo;
    //padronizando as cores que os botões podem ficar
    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 100);

    public BotaoCampo(Campo campo){
        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservador(this);
    }

    public void eventoOcorreu(Campo campo, CampoEvento evento){
        //seleção múltipla
        switch(evento){
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
        }

        //garantindo que não haverá nenhum problema de renderização ao reiniciar
        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }
 
    private void aplicarEstiloAbrir(){
        
        if(campo.isMinado()){
            setBackground(BG_EXPLODIR);
            return;
        }
        
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        switch(campo.minasNaVizinhanca()){
            case 1:
                setForeground(TEXTO_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
        }
        String valor = 
        !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }
    private void aplicarEstiloMarcar(){
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
    }
    private void aplicarEstiloExplodir(){
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }
    private void aplicarEstiloPadrao(){
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    //Interface dos eventos do Mouse

    public void mousePressed(MouseEvent e){
        if(e.getButton() == 1){
            campo.abrir();
        } else{
            campo.alternarMarcado();
        }
    }

    public void mouseClicked(MouseEvent e){System.out.println();}
    public void mouseEntered(MouseEvent e){System.out.println();}
    public void mouseExited(MouseEvent e){System.out.println();}
    public void mouseReleased(MouseEvent e){System.out.println();}
    
}
