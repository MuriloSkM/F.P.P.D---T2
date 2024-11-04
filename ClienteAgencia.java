import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

public class ClienteAgencia {
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
        try{
            BancoService banco = (BancoService) Naming.lookup("//localhost/BancoService");

            banco.criarConta("José", "987654321");

            String transacaoId1 = UUID.randomUUID().toString();
            banco.depositar("987654321", 1500, transacaoId1);

            String transacaoId2 = UUID.randomUUID().toString();
            banco.sacar("987654321", 300, transacaoId2);

            System.out.println("Saldo: R$" + banco.consultarSaldo("987654321"));

            banco.fecharConta("987654321");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
