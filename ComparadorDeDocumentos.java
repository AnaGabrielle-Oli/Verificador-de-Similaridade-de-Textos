public class ComparadorDeDocumentos {

    public static Resultado comparar(Documento doc1, Documento doc2) {
        double produtoEscalar = 0.0;
        double magnitudeDoc1 = 0.0;
        double magnitudeDoc2 = 0.0;

        for (int i = 0; i < doc1.tabela.tamanho; i++) {
            HashTable.ItemVocabulario atual = doc1.tabela.tabela[i];
            
            while (atual != null) {
                int freq1 = atual.frequencia;
                magnitudeDoc1 += Math.pow(freq1, 2);

                int freq2 = doc2.tabela.buscarFrequencia(atual.palavra);
                if (freq2 > 0) {
                    produtoEscalar += (freq1 * freq2);
                }

                atual = atual.proximo;
            }
        }

        for (int i = 0; i < doc2.tabela.tamanho; i++) {
            HashTable.ItemVocabulario atual = doc2.tabela.tabela[i];
            
            while (atual != null) {
                magnitudeDoc2 += Math.pow(atual.frequencia, 2);
                atual = atual.proximo;
            }
        }

        magnitudeDoc1 = Math.sqrt(magnitudeDoc1);
        magnitudeDoc2 = Math.sqrt(magnitudeDoc2);

        double similaridade = 0.0;
        
        if (magnitudeDoc1 != 0.0 && magnitudeDoc2 != 0.0) {
            similaridade = produtoEscalar / (magnitudeDoc1 * magnitudeDoc2);
        }

        similaridade = Math.round(similaridade * 10000.0) / 10000.0;

        return new Resultado(doc1.nomeArquivo, doc2.nomeArquivo, similaridade);
    }
}