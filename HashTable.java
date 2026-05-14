public class HashTable{
    ItemVocabulario[] tabela;
    int tamanho;
    int numElementos;

    public class ItemVocabulario{
        String palavra;
        int frequencia;
        ItemVocabulario proximo;

        public ItemVocabulario(String palavra, int frequencia, ItemVocabulario proximo){
            this.palavra = palavra;
            this.frequencia = frequencia;
            this.proximo = proximo;
        }
    }

    public HashTable(int tamanho){
        this.tamanho = tamanho;
        this.tabela = new ItemVocabulario;
        this.numElementos = 0;
    }

    private int insercaoPolinomial(String palavra){
        int h = 0;
        for (int i = 0; i < palavra.length(); i++)
            h = (31 * h + palavra.charAt(i)) % tamanho;
        return (h & 0x7fffffff) % tamanho;
    }

    private void insercaoDivisao(String palavra){
        int soma = 0;

        for(int i = 0; i < palavra.length(); i++){
            soma += palavra.charAt(i);
        }

        int indice = (soma & 0x7fffffff) % tamanho; 
        ItemVocabulario atual = table[indice];
        while (atual != null){
            if (atual.palavra.equals(palavra)){
                atual.frequncia++;
                return;
            }
            atual.proximo;
        }
        ItemVocabulario novo = new ItemVocabulario(palavra, 1, tabela[indice]);
        tabela[indice] = novo;
        this.numElementos++;
    }
    
}