//Ana Gabrielle da Silva Oliveira     RA: 10721801
//Pedro Tessaro Augusto               RA: 10723715
//Ricardo Dias Pimenta                RA: 10723451

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Uso incorreto. Assinatura esperada:");
            System.out.println("java Main <diretorio_documentos> <limiar> <modo> [argumentos_opcionais]");
            return;
        }

        String diretorio = args[0];
        double limiar = Double.parseDouble(args[1]);
        String modo = args[2].toLowerCase();

        StringBuilder saida = new StringBuilder();
        saida.append("=== VERIFICADOR DE SIMILARIDADE DE TEXTOS ===\n");

        try {
            if (modo.equals("busca")) {
                if (args.length < 5) {
                    System.out.println("Erro: Para o modo 'busca', informe os dois arquivos.");
                    return;
                }
                String nomeArq1 = args[3];
                String nomeArq2 = args[4];

                Documento doc1 = new Documento(diretorio + "/" + nomeArq1);
                Documento doc2 = new Documento(diretorio + "/" + nomeArq2);
                
                doc1.processarDocumento();
                doc2.processarDocumento();

                Resultado res = ComparadorDeDocumentos.comparar(doc1, doc2);

                saida.append("Comparando: ").append(nomeArq1).append(" <-> ").append(nomeArq2).append("\n");
                saida.append("Similaridade calculada: ").append(res.similaridade).append("\n");
                saida.append("Métrica utilizada: Cosseno\n");

            } else {
                File dir = new File(diretorio);
                File[] arquivos = dir.listFiles();
                
                if (arquivos == null || arquivos.length == 0) {
                    System.out.println("Erro: Diretório vazio ou inválido.");
                    return;
                }

                List<Documento> listaDocumentos = new ArrayList<>();
                for (File f : arquivos) {
                    if (f.isFile() && f.getName().endsWith(".txt")) {
                        Documento doc = new Documento(f.getAbsolutePath());
                        doc.processarDocumento();
                        listaDocumentos.add(doc);
                    }
                }

                saida.append("Total de documentos processados: ").append(listaDocumentos.size()).append("\n");

                AVLTree arvore = new AVLTree();
                int totalPares = 0;

                for (int i = 0; i < listaDocumentos.size(); i++) {
                    for (int j = i + 1; j < listaDocumentos.size(); j++) {
                        Resultado res = ComparadorDeDocumentos.comparar(listaDocumentos.get(i), listaDocumentos.get(j));
                        arvore.inserir(res.similaridade, res);
                        totalPares++;
                    }
                }

                saida.append("Total de pares comparados: ").append(totalPares).append("\n");
                saida.append("Função hash utilizada: Polinomial\n");
                saida.append("Métrica de similaridade: Cosseno\n\n");

                List<Resultado> resultadosOrdenados = new ArrayList<>();
                coletarResultados(arvore.raiz, resultadosOrdenados);

                if (modo.equals("lista")) {
                    saida.append("Pares com similaridade >= ").append(limiar).append(":\n");
                    for (Resultado r : resultadosOrdenados) {
                        if (r.similaridade >= limiar) {
                            String arq1 = new File(r.nomeArquivo1).getName();
                            String arq2 = new File(r.nomeArquivo2).getName();
                            saida.append(arq1).append(" <-> ").append(arq2)
                                 .append(" = ").append(r.similaridade).append("\n");
                        }
                    }
                } else if (modo.equals("topk")) {
                    if (args.length < 4) {
                        System.out.println("Erro: Informe o valor de K para o modo topk.");
                        return;
                    }
                    int k = Integer.parseInt(args[3]);
                    saida.append("Top ").append(k).append(" pares mais semelhantes:\n");
                    
                    int limite = Math.min(k, resultadosOrdenados.size());
                    for (int i = 0; i < limite; i++) {
                        Resultado r = resultadosOrdenados.get(i);
                        String arq1 = new File(r.nomeArquivo1).getName();
                        String arq2 = new File(r.nomeArquivo2).getName();
                        saida.append(arq1).append(" <-> ").append(arq2)
                             .append(" = ").append(r.similaridade).append("\n");
                    }
                } else {
                    System.out.println("Erro: Modo desconhecido. Use lista, topk ou busca.");
                    return;
                }
            }

            String saidaFinal = saida.toString();
            System.out.println(saidaFinal);
            Files.writeString(Path.of("resultado.txt"), saidaFinal);

        } catch (IOException e) {
            System.out.println("Erro de IO: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void coletarResultados(No no, List<Resultado> lista) {
        if (no != null) {
            coletarResultados(no.dir, lista);
            for (Resultado r : no.listaResultados) {
                lista.add(r);
            }
            coletarResultados(no.esq, lista);
        }
    }
}
