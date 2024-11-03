import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServidorBanco {
    public static void main(String[] args) {
        try {
            BancoService banco = new BancoServiceImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/BancoService", banco);
            System.out.println("Servidor do banco está pronto");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
