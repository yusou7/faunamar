package faunamar;

public abstract class AnimalMarinho {
    // Atributos protegidos
    protected String especie;
    protected String localEncalhe;
    protected String dataEncalhe;
    protected String condicao; // Ex: Vivo, Morto

    // Construtor
    public AnimalMarinho(String especie, String local, String data, String condicao) {
        this.especie = especie;
        this.localEncalhe = local;
        this.dataEncalhe = data;
        this.condicao = condicao;
    }

    // Método que exibe os dados (usado no relatório simples)
    public String exibirDados() {
        return "Espécie: " + especie + " | Local: " + localEncalhe + 
               " | Data: " + dataEncalhe + " | Status: " + condicao;
    }

    // --- GETTERS (MÉTODOS DE ACESSO) ---
    
    public String getEspecie() {
        return especie;
    }

    public String getCondicao() {
        return condicao;
    }

    // ESTE AQUI ERA O QUE FALTAVA PARA O ERRO SUMIR:
    public String getLocalEncalhe() {
        return localEncalhe;
    }
}