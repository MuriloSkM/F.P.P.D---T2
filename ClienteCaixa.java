import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.UUID;

public class ClienteCaixa {
    public static void main(String[] args) {
        try {
            BancoService banco = (BancoService) Naming.lookup("//localhost/BancoService");
            
            String transacaoId1 = UUID.randomUUID().toString();
            try {
                banco.depositar("987654321", 200, transacaoId1);
            } catch (RemoteException e) {
                System.out.println("Erro ao depositar: " + e.getMessage());
            }

            String transacaoId2 = UUID.randomUUID().toString();
            try {
                banco.sacar("987654321", 100, transacaoId2);
            } catch (RemoteException e) {
                System.out.println("Erro ao sacar: " + e.getMessage());
            }

            System.out.println("Saldo: R$" + banco.consultarSaldo("987654321"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
