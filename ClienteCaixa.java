import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.UUID;

public class ClienteCaixa {
    public static void main(String[] args) {
        try {
            BancoService banco = (BancoService) Naming.lookup("//localhost/BancoService");
            String cpf = "987654321";
            String nome = "João";
            banco.criarConta(nome, cpf);
            System.out.println("Conta criada para: " + nome + " com CPF: " + cpf);
            String transacaoId1 = UUID.randomUUID().toString();
            try {
                banco.depositar(cpf, 200, transacaoId1);
            } catch (RemoteException e) {
                System.out.println("Erro ao depositar: " + e.getMessage());
            }
            String transacaoId2 = UUID.randomUUID().toString();
            try {
                banco.sacar(cpf, 100, transacaoId2);
            } catch (RemoteException e) {
                System.out.println("Erro ao sacar: " + e.getMessage());
            }
            double saldo = banco.consultarSaldo(cpf);
            System.out.println("Saldo: R$" + saldo);
            banco.fecharConta(cpf);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
