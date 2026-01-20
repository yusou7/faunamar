package faunamar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {

    public List<AnimalMarinho> lerArquivo(String caminhoArquivo) {
        List<AnimalMarinho> listaRecuperada = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {

            String linha;
            int contadorLinha = 0;

            while ((linha = br.readLine()) != null) {
                contadorLinha++;

                // 1. PULA CABEÇALHO (10 LINHAS)
                if (contadorLinha <= 10) continue;

                // 2. LIMPEZA E SEPARAÇÃO
                linha = linha.replace("\"", ""); 
                String[] dados = linha.split(";");

                try {
                    if (dados.length > 32) { 
                        // Mapeamento
                        String cidade = dados[5];
                        String data = dados[19];
                        String especie = dados[32];
                        String observacao = (dados.length > 22) ? dados[22] : "";

                        // --- INTELIGÊNCIA 1: VIVO OU MORTO? ---
                        String condicao = "Morto/Indefinido"; 
                        String obsMin = observacao.toLowerCase();
                        if (obsMin.contains("vivo") || obsMin.contains("viva") || obsMin.contains("vivos")) {
                            condicao = "Vivo";
                        } else if (obsMin.contains("mort") || obsMin.contains("carcaça")) {
                            condicao = "Morto";
                        }

                        // --- INTELIGÊNCIA 2: QUANTOS ANIMAIS? (NOVO!) ---
                        // Chama o método auxiliar lá de baixo para ler "Duas", "Três", etc.
                        int quantidade = detectarQuantidade(observacao);

                        // LOOP DE CADASTRO MULTIPLO
                        // Se a observação diz "Quatro tartarugas", cadastramos 4 vezes.
                        for (int i = 0; i < quantidade; i++) {
                            
                            // Criação dos Objetos (Tartaruga vs Mamífero)
                            if (especie.toLowerCase().contains("chelonia") || 
                                especie.toLowerCase().contains("caretta") || 
                                especie.toLowerCase().contains("lepidochelys") ||
                                especie.toLowerCase().contains("eretmochelys")) {
                                
                                listaRecuperada.add(new Tartaruga(especie, cidade, data, condicao, "Indefinido"));
                            
                            } else {
                                listaRecuperada.add(new Mamifero(especie, cidade, data, condicao, true));
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Aviso: Falha leve na linha " + contadorLinha);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro crítico ao abrir arquivo: " + e.getMessage());
        }

        return listaRecuperada;
    }

    // --- MÉTODO NOVO: DETETIVE DE NÚMEROS ---
    private int detectarQuantidade(String texto) {
        String t = texto.toLowerCase();
        
        // Procura por numerais escritos por extenso (Muito comum nos relatórios)
        if (t.contains("duas ") || t.contains("dois ")) return 2;
        if (t.contains("três ") || t.contains("tres ") || t.contains("t r ê s")) return 3;
        if (t.contains("quatro ")) return 4;
        if (t.contains("cinco ")) return 5;
        if (t.contains("seis ")) return 6;
        if (t.contains("sete ")) return 7;
        if (t.contains("oito ")) return 8;
        if (t.contains("nove ")) return 9;
        if (t.contains("dez ")) return 10;
        if (t.contains("onze ")) return 11;
        if (t.contains("doze ")) return 12;

        // Procura por padrões numéricos específicos (ex: "Totalizando 4 animais")
        // Adicionamos espaços ao redor para não confundir "COD.4" com "4"
        if (t.contains(" 2 ")) return 2;
        if (t.contains(" 3 ")) return 3;
        if (t.contains(" 4 ")) return 4;
        if (t.contains(" 5 ")) return 5;
        if (t.contains(" 6 ")) return 6;
        
        // Se não achou nada explícito, assume que é 1 animal só
        return 1; 
    }
}