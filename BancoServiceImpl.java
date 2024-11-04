import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class BancoServiceImpl extends UnicastRemoteObject implements BancoService {
    private final Map<String, Double> contas = new HashMap<>();
    private final Map<String, ReentrantLock> locks = new HashMap<>();
    private final Map<String, String> transacoes = new HashMap<>();
    private final Random random = new Random();

    protected BancoServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public synchronized void criarConta(String nome, String cpf) throws RemoteException {
        if (!contas.containsKey(cpf)) {
            contas.put(cpf, 0.0);
            locks.put(cpf, new ReentrantLock());
            System.out.println("Conta criada para: " + nome);
        } else {
            System.out.println("Conta já existe para: " + nome);
        }
    }

    @Override
    public synchronized void fecharConta(String cpf) throws RemoteException {
        if (contas.containsKey(cpf)) {
            contas.remove(cpf);
            locks.remove(cpf);
            System.out.println("Conta fechada para: " + cpf);
        } else {
            System.out.println("Conta não encontrada para: " + cpf);
        }
    }

    @Override
    public void depositar(String cpf, double valor, String transacaoId) throws RemoteException {
        if (transacoes.containsKey(transacaoId)) {
            System.out.println("Depósito já realizado para a transação ID: " + transacaoId);
            return;
        }

        ReentrantLock lock = locks.get(cpf);
        if (lock == null) {
            System.out.println("Conta não encontrada para: " + cpf);
            return;
        }

        lock.lock();
        try {
            // Simulação de falha aleatória
            if (random.nextInt(10) < 2) { // 20% de chance de falha
                System.out.println("Falha ao realizar depósito na conta de " + cpf);
                throw new RemoteException("Erro de simulação: falha no depósito");
            }

            if (contas.containsKey(cpf)) {
                contas.put(cpf, contas.get(cpf) + valor);
                transacoes.put(transacaoId, "DEPOSITADO");
                System.out.println("Depositado R$" + valor + " na conta de " + cpf);
            } else {
                System.out.println("Conta não encontrada para: " + cpf);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void sacar(String cpf, double valor, String transacaoId) throws RemoteException {
        if (transacoes.containsKey(transacaoId)) {
            System.out.println("Saque já realizado para a transação ID: " + transacaoId);
            return;
        }

        ReentrantLock lock = locks.get(cpf);
        if (lock == null) {
            System.out.println("Conta não encontrada para: " + cpf);
            return;
        }

        lock.lock();
        try {
            // Simulação de falha aleatória
            if (random.nextInt(10) < 2) { // 20% de chance de falha
                System.out.println("Falha ao realizar saque na conta de " + cpf);
                throw new RemoteException("Erro de simulação: falha no saque");
            }

            if (contas.containsKey(cpf)) {
                if (contas.get(cpf) >= valor) {
                    contas.put(cpf, contas.get(cpf) - valor);
                    transacoes.put(transacaoId, "SACADO");
                    System.out.println("Sacado R$" + valor + " da conta de " + cpf);
                } else {
                    System.out.println("Saldo insuficiente para " + cpf);
                }
            } else {
                System.out.println("Conta não encontrada para: " + cpf);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public double consultarSaldo(String cpf) throws RemoteException {
        if (contas.containsKey(cpf)) {
            return contas.get(cpf);
        } else {
            System.out.println("Conta não encontrada para: " + cpf);
            return 0.0;
        }
    }
}
