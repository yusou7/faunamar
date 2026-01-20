package faunamar;

// Tartaruga.java
public class Tartaruga extends AnimalMarinho {
    private String tipoCasco; // Ex: Duro, Couro

    public Tartaruga(String especie, String local, String data, String condicao, String tipoCasco) {
        super(especie, local, data, condicao); // Chama o construtor da mãe
        this.tipoCasco = tipoCasco;
    }

    @Override
    public String exibirDados() {
        // Aproveita o texto da mãe e adiciona o detalhe do casco
        return super.exibirDados() + " | Casco: " + this.tipoCasco;
    }
}