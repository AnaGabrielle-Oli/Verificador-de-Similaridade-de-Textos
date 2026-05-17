public class HashTable {
    public enum MetodoHash {
        POLINOMIAL,
        DIVISAO
    }

    ItemVocabulario[] tabela;
    int tamanho;
    int numElementos;
    private MetodoHash metodo;
    int totalColisoes;

    public class ItemVocabulario {
        String palavra;
        int frequencia;
        ItemVocabulario proximo;

        public ItemVocabulario(String palavra, int frequencia, ItemVocabulario proximo) {
            this.palavra = palavra;
            this.frequencia = frequencia;
            this.proximo = proximo;
        }
    }

    public HashTable(int tamanho, MetodoHash metodo) {
        this.tamanho = tamanho;
        this.metodo = metodo;
        this.tabela = new ItemVocabulario[tamanho];
        this.numElementos = 0;
        this.totalColisoes = 0;
    }

    public void inserir(String palavra) {
        if (metodo == MetodoHash.DIVISAO) {
            insercaoDivisao(palavra);
        } else {
            insercaoPolinomial(palavra);
        }
    }

    private void insercaoPolinomial(String palavra) {
        int h = 0;
        for (int i = 0; i < palavra.length(); i++)
            h = (31 * h + palavra.charAt(i)) % tamanho;
        int indice = (h & 0x7fffffff) % tamanho;

        ItemVocabulario atual = tabela[indice];
        
        if (atual != null) this.totalColisoes++; 

        while (atual != null) {
            if (atual.palavra.equals(palavra)) {
                atual.frequencia++;
                return;
            }
            atual = atual.proximo;
        }

        ItemVocabulario novo = new ItemVocabulario(palavra, 1, tabela[indice]);
        tabela[indice] = novo; 
        this.numElementos++;
    }

    private void insercaoDivisao(String palavra) {
        int soma = 0;
        for(int i = 0; i < palavra.length(); i++){
            soma += palavra.charAt(i);
        }

        int indice = (soma & 0x7fffffff) % tamanho; 
        ItemVocabulario atual = tabela[indice];
        
        if (atual != null) this.totalColisoes++;

        while (atual != null){
            if (atual.palavra.equals(palavra)){
                atual.frequencia++; 
                return;
            }
            atual = atual.proximo;
        }
        ItemVocabulario novo = new ItemVocabulario(palavra, 1, tabela[indice]);
        tabela[indice] = novo;
        this.numElementos++;
    }    

    public int buscarFrequencia(String palavra) {
        int h = 0;
        for (int i = 0; i < palavra.length(); i++)
            h = (31 * h + palavra.charAt(i)) % tamanho;
        int indice = (h & 0x7fffffff) % tamanho;

        ItemVocabulario atual = tabela[indice];
        while (atual != null) {
            if (atual.palavra.equals(palavra)) {
                return atual.frequencia;
            }
            atual = atual.proximo;
        }
        return 0; 
    }
}