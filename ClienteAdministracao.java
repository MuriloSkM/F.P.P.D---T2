import java.rmi.Naming;

public class ClienteAdministracao {
    public static void main(String[] args) {
        try {
            BancoService banco = (BancoService) Naming.lookup("//localhost/BancoService");
            banco.criarConta("Luise", "123456789");
            banco.depositar("123456789", 1000, "transacao1");
            System.out.println("Saldo: R$" + banco.consultarSaldo("123456789"));
            banco.sacar("123456789", 200, "transacao2");
            System.out.println("Saldo após saque: R$" + banco.consultarSaldo("123456789"));
            banco.fecharConta("123456789");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
