package faunamar;

//Mamifero.java
public class Mamifero extends AnimalMarinho {
 private boolean temDentes; // Ex: Baleia (não tem, tem barbatana), Golfinho (tem)

 public Mamifero(String especie, String local, String data, String condicao, boolean temDentes) {
     super(especie, local, data, condicao);
     this.temDentes = temDentes;
 }

 @Override
 public String exibirDados() {
     String detalhe = temDentes ? "Com dentes" : "Com barbatanas (filtrador)";
     return super.exibirDados() + " | Tipo: " + detalhe;
 }
}