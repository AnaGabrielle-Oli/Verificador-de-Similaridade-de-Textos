import java.util.ArrayList;
import java.util.List;

class No {
    double dado;
    No esq, dir;
    int bal;
    List<Resultado> listaResultados; 

    public No(double valor, Resultado res) {
        this.dado = valor;
        this.esq = null;
        this.dir = null;
        this.bal = 0;
        this.listaResultados = new ArrayList<>();
        this.listaResultados.add(res);
    }
}

public class AVLTree {

    No raiz;
    public int totalRotacoes = 0;

    public AVLTree() {
        this.raiz = null;
    }

    public void inserir(double valor, Resultado res) {
        boolean[] h = {false};
        this.raiz = insere(this.raiz, valor, h, res);
    }

    No insere(No p, double valor, boolean[] h, Resultado res) {
        if (p == null) {
            p = new No(valor, res);
            h[0] = true;
        }
        else if (valor < p.dado) { 
            p.esq = insere(p.esq, valor, h, res);
            if (h[0]) {
                switch (p.bal) {
                    case 1: 
                        p.bal = 0; 
                        h[0] = false; 
                        break; 
                    case 0: 
                        p.bal = -1; 
                        break; 
                    case -1:
                        p = caso1(p); 
                        h[0] = false; 
                        break; 
                }
            }
        }
        else if (valor > p.dado) {
            p.dir = insere(p.dir, valor, h, res);
            if (h[0]) {
                switch (p.bal) {
                    case -1: 
                        p.bal = 0; 
                        h[0] = false; 
                        break; 
                    case 0: 
                        p.bal = 1; 
                        break; 
                    case 1: 
                        p = caso2(p); 
                        h[0] = false; 
                        break; 
                }
            }
        }
        else {
            p.listaResultados.add(res);
            h[0] = false;
        }
        return p;
    }

    No caso1(No p) {
        totalRotacoes++;
        No u, v;
        u = p.esq;
        
        if (u.bal == -1){
            p.esq = u.dir;
            u.dir = p;
            p.bal = 0; 
            p = u;
        }
        else { 
            totalRotacoes++;
            v = u.dir;
            u.dir = v.esq;
            p.esq = v.dir;
            v.esq = u; v.dir = p;
            if (v.bal == -1) 
                p.bal = 1;
            else 
                p.bal = 0;
            if (v.bal == 1)
                u.bal = -1;
            else
                u.bal = 0;
            p = v;
        }
        p.bal = 0;
        return p;
    }

    No caso2(No p){
        totalRotacoes++;
        No z, y;
        z = p.dir;

        if (z.bal == 1){
            p.dir = z.esq;
            z.esq = p;
            p.bal = 0;
            p = z;
        }
        else {
            totalRotacoes++;
            y = z.esq;
            z.esq = y.dir;
            p.dir = y.esq;
            y.esq = p; y.dir = z;
            if (y.bal == 1) 
                p.bal = -1;
            else 
                p.bal = 0;
            if (y.bal == -1) 
                z.bal = 1;
            else
                z.bal = 0;
            p = y;
        }
        p.bal = 0;
        return p;
    }
}