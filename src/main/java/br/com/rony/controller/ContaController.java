/**
 * 
 */
package br.com.rony.controller;

import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.serialization.gson.WithRoot;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.annotations.RequiredLogin;
import br.com.rony.dao.AgenciaDao;
import br.com.rony.dao.ClienteDao;
import br.com.rony.dao.ContaDao;
import br.com.rony.dao.TransacaoDao;
import br.com.rony.enums.TipoTransacao;
import br.com.rony.model.Agencia;
import br.com.rony.model.Cliente;
import br.com.rony.model.Conta;
import br.com.rony.model.Transacao;
import br.com.rony.model.vo.ContaVO;

/**
 * Controlador para gerenciar as requisições para contas bancarias
 * 
 * @author Rony
 *
 */
@Controller
@Path("/conta")
@RequiredLogin
public class ContaController extends BaseController {

	@Inject
	private ContaDao contaDao;
	@Inject
	private ClienteDao clienteDao;
	@Inject
	private AgenciaDao agenciaDao;
	@Inject
	private TransacaoDao transacaoDao;

	public ContaController() {
		super();
	}

	/**
	 * Método para cadastrar uma nova conta. O número da conta é gerado pela API
	 * 
	 * @param conta
	 */
	@Consumes(value = "application/json")
	@Post("")
	public void cadastro(ContaVO conta) {
		try {
			// Verificando se o cliente está cadastrado
			Cliente clienteDB = clienteDao.find(conta.getCliente());
			if (clienteDB != null) {
				// Verificando se a agencia está cadastrada
				Agencia agenciaDB = agenciaDao.find(conta.getAgencia());
				if (agenciaDB != null) {

					// É gerada uma nova conta para o cliente
					Conta contaDB = new Conta();
					contaDB.setAgencia(agenciaDB);
					contaDB.setCliente(clienteDB);
					contaDB = this.contaDao.update(contaDB);
					String numero = (1000 + contaDB.getId()) + "-" + contaDB.getAgencia().getId();
					contaDB.setNumero(numero);
					this.contaDao.update(contaDB);

					addSucessMessage("Conta cadstrada com sucesso!");
				} else {
					addWarningMessage("Agencia não cadastrada!");
				}

			} else {
				addWarningMessage("Cliente não cadastrado!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
		}

		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}

	/**
	 * Método para retornar o extrato de uma conta
	 * 
	 * @param numero
	 */
	@Post("/extrato")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void extratoDaConta(String numero) {
		try {

			// verificando se a conta está cadastrada
			Conta conta = contaDao.getCountByNumber(numero);
			if (conta != null) {
				// verifica se existem transações realizadas na conta
				List<Transacao> transacoes = transacaoDao.buscarTransacoesPorConta(conta.getId());
				if (transacoes != null) {
					result.use(Results.json()).withoutRoot().from(transacoes)
							.include("contaOrigem", "contaDestino")
							.serialize();
				} else {
					addSucessMessage("Não existem transações realizadas na conta!");
					result.use(Results.json()).withoutRoot().from(messages).serialize();
				}
			} else {
				addWarningMessage("Conta " + numero + " não encontrada!");
				result.use(Results.json()).withoutRoot().from(messages).serialize();
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
			result.use(Results.json()).withoutRoot().from(messages).serialize();
		}

	}

	/**
	 * Método para realizar deposito em uma conta
	 * 
	 * @param numero
	 *            - numero da conta que será realizado o deposito
	 * @param valor
	 *            - valor que será depositado
	 */
	@Post("/deposito")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void deposito(String numero, Double valor) {
		try {
			// verifica se a conta existe
			Conta conta = contaDao.getCountByNumber(numero);
			if (conta != null) {
				// verificando se o valor é valido
				if (valor > 0) {
					Transacao transacao = new Transacao(TipoTransacao.DEPOSITO, valor, conta, null);
					transacaoDao.insert(transacao);
					conta.setSaldo(conta.getSaldo() + valor);
					contaDao.update(conta);
					addSucessMessage("Depósito realizado com sucesso!");

				} else {
					addWarningMessage("Não existem transações realizadas na conta!");
				}
			} else {
				addWarningMessage("Conta " + numero + " não encontrada!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}

	/**
	 * @param numero
	 * @param valor
	 */
	@Post("/saque")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void saque(String numero, Double valor) {
		try {
			Conta conta = contaDao.getCountByNumber(numero);
			if (conta != null) {
				if (valor > 0) {
					if (conta.getSaldo() >= valor) {
						Transacao transacao = new Transacao(TipoTransacao.SAQUE, valor, conta, null);
						transacaoDao.insert(transacao);
						conta.setSaldo(conta.getSaldo() - valor);
						contaDao.update(conta);
						addSucessMessage("Saque realizado com sucesso!");
					} else {
						addWarningMessage("Saldo insuficiente!");
					}
				} else {
					addWarningMessage("Valor não pode ser negativo!");
				}
			} else {
				addWarningMessage("Conta " + numero + " não encontrada!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}

	/**
	 * Método para transferencia entre contas do mesmo banco
	 * 
	 * @param origem
	 * @param destino
	 * @param valor
	 */
	@Post("/transferencia")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void transferencia(String origem, String destino, Double valor) {
		try {
			Conta contaOrigem = contaDao.getCountByNumber(origem);
			Conta contaDestino = contaDao.getCountByNumber(destino);
			if (contaOrigem != null && contaDestino != null && !contaOrigem.equals(contaDestino)) {
				if (valor > 0) {
					if (contaOrigem.getSaldo() >= valor) {
						Transacao transacao = new Transacao(TipoTransacao.TRANSFERENCIA, valor, contaOrigem,
								contaDestino);
						transacaoDao.insert(transacao);
						contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
						contaDestino.setSaldo(contaDestino.getSaldo() + valor);
						contaDao.update(contaOrigem);
						contaDao.update(contaDestino);
						addSucessMessage("Transferência realizada com sucesso!");
					} else {
						addWarningMessage("Saldo insuficiente!");
					}
				} else {
					addWarningMessage("Valor não pode ser negativo!");
				}
			} else {
				addWarningMessage("Conta não encontrada");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}

	@Post("/transferencia/bancos")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void transferenciaEntreBancos(String bancoOrigem, String origem, String bancoDestino, String destino,
			Double valor) {
		try {

			Conta contaOrigem = contaDao.getCountByNumberAndBank(origem, bancoOrigem);
			Conta contaDestino = contaDao.getCountByNumberAndBank(destino, bancoDestino);
			if (contaOrigem != null && contaDestino != null) {
				if (valor > 0) {
					if (contaOrigem.getSaldo() >= valor) {
						Transacao transacao = new Transacao(TipoTransacao.TRANSFERENCIA, valor, contaOrigem,
								contaDestino);
						transacaoDao.insert(transacao);
						contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
						contaDestino.setSaldo(contaDestino.getSaldo() + valor);
						contaDao.update(contaOrigem);
						contaDao.update(contaDestino);
						addSucessMessage("Saque realizado com sucesso!");
					} else {
						addWarningMessage("Saldo insuficiente!");
					}
				} else {
					addWarningMessage("Valor não pode ser negativo!");
				}
			} else {
				addWarningMessage("Conta não encontrada");
			}

		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Erro Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}

}
