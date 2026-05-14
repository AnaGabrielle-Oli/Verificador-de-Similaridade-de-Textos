import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class Documento{
    String nomeArquivo;
    HashTable tabela;

    public Documento(String nome){
        this.nomeArquivo = nome;
        this.tabela = new HashTable();
    }

    public void processarDocumento(){
        
        String textoCompleto = lerArquivo();
        if (textoCompleto.isEmpty()){
            System.out.println("Aviso: O documento '" + this.nomeArquivo + "' está vazio.");
            return;
        }

        String textoNormalizado = normalizar(textoCompleto);

        String[] palavras = tokenizar(textoNormalizado);

        for (String palavra : palavras){

            if (!palavra.isEmpty() && !stopWord(palavra)){
                tabela.inserir(palavra); 
            }
        }
    }

    String lerArquivo(){
        try{
            return Files.readString(Path.of(this.nomeArquivo));
        }catch(IOException e){
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return ""; 
        }
    }

    public String normalizar(String textoCompleto){
        String texto = textoCompleto.toLowerCase();

        texto = texto.replaceAll("[áàâãä]", "a");
        texto = texto.replaceAll("[éèêë]", "e");
        texto = texto.replaceAll("[íìîï]", "i");
        texto = texto.replaceAll("[óòôõö]", "o");
        texto = texto.replaceAll("[úùûü]", "u");
        texto = texto.replaceAll("[ç]", "c");

        texto = texto.replaceAll("[^a-z0-9\\s]", " ");

        return texto;
    }

    public String[] tokenizar(String textoNormal){
        return textoNormal.trim().split("\\s+");
    }

    private static final String[] STOP_WORDS = {
        "a", "ao", "aos", "as", "ate", "com", "como", "da", "das", "de", 
        "dela", "delas", "dele", "deles", "depois", "do", "dos", "e", 
        "ela", "elas", "ele", "eles", "em", "entre", "era", "essa", 
        "essas", "esse", "esses", "esta", "estas", "este", "estes", 
        "eu", "foi", "foram", "fosse", "ha", "isso", "ja", "lhe", 
        "lhes", "mais", "mas", "me", "mesmo", "meu", "meus", "minha", 
        "minhas", "na", "nao", "nas", "nem", "no", "nos", "nossa", 
        "nossas", "nosso", "nossos", "num", "numa", "o", "os", "ou", 
        "para", "pela", "pelas", "pelo", "pelos", "por", "qual", 
        "quando", "que", "quem", "se", "seja", "sem", "sera", "seu", 
        "seus", "so", "sua", "suas", "tambem", "te", "tem", "tenho", 
        "ter", "teu", "teus", "tinha", "tu", "tua", "tuas", "um", 
        "uma", "voce", "voces", "vos"
    };

    private boolean stopWord(String palavra){
        int inicio = 0;
        int fim = STOP_WORDS.length - 1;

        while (inicio <= fim){
            int meio = (inicio + fim) / 2;
            int comparacao = palavra.compareTo(STOP_WORDS[meio]);

            if (comparacao == 0){
                return true; 
            }else if(comparacao < 0){
                fim = meio - 1;
            }else{
                inicio = meio + 1; 
            }
        }
        return false; 
    }


}