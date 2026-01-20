package faunamar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SistemaFaunaMar {

    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        List<AnimalMarinho> listaAnimais = new ArrayList<>();

        System.out.println("=== SISTEMA FAUNAMAR (VERSÃO FINAL) ===");
        System.out.println("Monitoramento e Estatística de Fauna Marinha - ODS 14");

        boolean rodando = true;
        while (rodando) {
            System.out.println("\n---------------- MENU PRINCIPAL ----------------");
            System.out.println("1. Cadastrar Tartaruga (Manual)");
            System.out.println("2. Cadastrar Mamífero (Manual)");
            System.out.println("3. GERAR RELATÓRIO DETALHADO (BI e Estatística)");
            System.out.println("4. IMPORTAR DADOS DO CSV (SIMBA/IBAMA)");
            System.out.println("0. Sair");
            System.out.print(">> Escolha uma opção: ");

            int opcao = 0;
            try {
                opcao = Integer.parseInt(leitor.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite apenas números.");
                continue;
            }

            if (opcao == 1) {
                // Cadastro Manual Tartaruga
                System.out.print("Espécie: "); String esp = leitor.nextLine();
                System.out.print("Local: "); String local = leitor.nextLine();
                System.out.print("Data: "); String data = leitor.nextLine();
                System.out.print("Condição: "); String cond = leitor.nextLine();
                System.out.print("Casco: "); String casco = leitor.nextLine();
                listaAnimais.add(new Tartaruga(esp, local, data, cond, casco));
                System.out.println(">> Tartaruga cadastrada.");

            } else if (opcao == 2) {
                // Cadastro Manual Mamífero
                System.out.print("Espécie: "); String esp = leitor.nextLine();
                System.out.print("Local: "); String local = leitor.nextLine();
                System.out.print("Data: "); String data = leitor.nextLine();
                System.out.print("Condição: "); String cond = leitor.nextLine();
                System.out.print("Tem dentes? (sim/nao): ");
                boolean dentes = leitor.nextLine().toLowerCase().startsWith("s");
                listaAnimais.add(new Mamifero(esp, local, data, cond, dentes));
                System.out.println(">> Mamífero cadastrado.");

            } else if (opcao == 3) {
                // --- RELATÓRIO DETALHADO (A MÁGICA ACONTECE AQUI) ---
                if (listaAnimais.isEmpty()) {
                    System.out.println(">> Base de dados vazia. Importe o CSV (Opção 4) primeiro.");
                } else {
                    System.out.println("\n################################################");
                    System.out.println("#      RELATÓRIO TÉCNICO DE MONITORAMENTO      #");
                    System.out.println("################################################");
                    
                    // Mapas para contagem inteligente
                    Map<String, Integer> contagemEspecies = new HashMap<>();
                    Map<String, Integer> contagemCidades = new HashMap<>();
                    int vivos = 0, mortos = 0;

                    for (AnimalMarinho a : listaAnimais) {
                        // 1. Contagem de Vivos/Mortos
                        if (a.getCondicao().toLowerCase().contains("vivo")) vivos++;
                        else mortos++;

                        // 2. Contagem por Espécie
                        // O comando 'getOrDefault' pega o valor atual e soma 1, ou começa do 0
                        String nomeEspecie = a.getEspecie().trim();
                        if(nomeEspecie.isEmpty()) nomeEspecie = "Não Identificada";
                        contagemEspecies.put(nomeEspecie, contagemEspecies.getOrDefault(nomeEspecie, 0) + 1);

                        // 3. Contagem por Cidade
                        String nomeCidade = a.getLocalEncalhe().trim(); // Supondo que 'local' guarde a cidade
                        contagemCidades.put(nomeCidade, contagemCidades.getOrDefault(nomeCidade, 0) + 1);
                    }

                    // --- IMPRESSÃO DOS DADOS ---
                    System.out.println("\n[1] RESUMO GERAL");
                    System.out.printf("Total de Ocorrências: %d%n", listaAnimais.size());
                    System.out.printf("Taxa de Mortalidade Aparente: %.1f%%%n", (double)mortos/listaAnimais.size()*100);
                    System.out.println("Vivos: " + vivos + " | Mortos/Carcaças: " + mortos);

                    System.out.println("\n[2] OCORRÊNCIAS POR ESPÉCIE (Biodiversidade)");
                    // Loop para imprimir cada espécie encontrada
                    for (String especie : contagemEspecies.keySet()) {
                        System.out.println(" - " + especie + ": " + contagemEspecies.get(especie));
                    }

                    System.out.println("\n[3] DISTRIBUIÇÃO GEOGRÁFICA (Cidades)");
                    for (String cidade : contagemCidades.keySet()) {
                        System.out.println(" - " + cidade + ": " + contagemCidades.get(cidade));
                    }
                    System.out.println("\n################################################");
                }

            } else if (opcao == 4) {
                // Importação
                System.out.println("\n>> Lendo arquivo 'dados_fauna.csv'...");
                LeitorCSV leitorArq = new LeitorCSV();
                List<AnimalMarinho> novos = leitorArq.lerArquivo("dados_fauna.csv");
                
                if (novos.isEmpty()) {
                    System.out.println(">> Erro: Nenhum dado importado. Verifique o arquivo.");
                } else {
                    listaAnimais.addAll(novos);
                    System.out.println(">> SUCESSO: " + novos.size() + " registros importados e processados.");
                }

            } else if (opcao == 0) {
                rodando = false;
            }
        }
        leitor.close();
    }
}