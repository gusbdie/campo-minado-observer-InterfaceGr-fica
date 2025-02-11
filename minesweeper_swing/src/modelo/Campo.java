package modelo;

import java.util.ArrayList;
import java.util.List;


public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto;
    private boolean minado;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();

    Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    public void registrarObservador(CampoObservador observador){
        observadores.add(observador);
    }

    private void notificarObservadores(CampoEvento evento){
        observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
    }// com esse this eu me refiro ao objeto atual que to trabalhando
    //no caso a classe

    boolean adicionarVizinho(Campo vizinho){
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int valorLinha = Math.abs(this.linha - vizinho.linha);
        int valorColuna = Math.abs(this.coluna - vizinho.coluna);
        int valorGeral = valorColuna + valorLinha;

        if(valorGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        }else if(valorGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        }else{
            return false;
        }
    }

    public void alternarMarcado(){
        if(!aberto){
            marcado = !marcado;

            if(marcado){
                notificarObservadores(CampoEvento.MARCAR);
            } else{
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }

    public boolean abrir(){
        
        if(!aberto && !marcado){
            if(minado){
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }

            setAberto(true);

            if(vizinhancaSegura()){
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        }else{
            return false;
        }
    }

    public boolean vizinhancaSegura(){
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    public boolean isMarcado(){
        return marcado;
    }

    void setAberto(boolean aberto){
        this.aberto = aberto;

        if(aberto){
            notificarObservadores(CampoEvento.ABRIR);
        }
    }
    void minar(){
        minado = true;
    }

    public boolean isMinado(){
        return minado;
    }

    public boolean isAberto(){
        return aberto;
    }

    public boolean isFechado(){
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public int minasNaVizinhanca(){
        return (int)vizinhos.stream().filter(v -> v.minado).count();
        //preciso converter para int pois o resultado de count é long
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;

        notificarObservadores(CampoEvento.REINICIAR);
    }
}
