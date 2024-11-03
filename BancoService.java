import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BancoService extends Remote {
    void criarConta(String nome, String cpf) throws RemoteException;
    void fecharConta(String cpf) throws RemoteException;
    void depositar(String cpf, double valor, String transacaoId) throws RemoteException;
    void sacar(String cpf, double valor, String transacaoId) throws RemoteException;
    double consultarSaldo(String cpf) throws RemoteException;
}
