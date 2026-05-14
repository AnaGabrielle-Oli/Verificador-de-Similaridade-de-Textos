class No{
    int dado;
    No esq, dir;
    int bal;

    public No(int valor){
        this.dado = valor;
        this.esq = null;
        this.dir = null;
        this.bal = 0;
    }
}

public class AVLTree{

    No raiz;

    public AVLTree(int valor){
        this.raiz = insere(this.raiz, valor);
    }

    No insere(No p, int valor) {
        if (p == null){
            p = new No(valor);
        }
        else if (valor < p.dado){ 
            p.esq = insere(p.esq, valor);
            switch (p.bal) {
                case 1: p.bal = 0; break;
                case 0: p.bal = -1; break;
                case -1: p = caso1(p); break;
            }
        }
        else if (valor > p.dado){
            p.dir = insere(p.dir, valor);
            switch (p.bal) {
                case -1: p.bal = 0; break;
                case 0: p.bal = 1; break;
                case 1: p = caso2(p); break;
            }
        }
        return p;
    }

    No caso1(No p) {
        No u, v;
        u = p.esq;
        
        if (u.bal == -1){
            p.esq = u.dir;
            u.dir = p;
            p.bal = 0; 
            p = u;
        }
        else { 
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
        No z, y;
        z = p.dir;

        if (z.bal == 1){
            p.dir = z.esq;
            z.esq = p;
            p.bal = 0;
            p = z;
        }
        else {
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

