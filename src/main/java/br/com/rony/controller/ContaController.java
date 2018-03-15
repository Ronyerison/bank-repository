/**
 * 
 */
package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
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
 * @author Rony
 *
 */
@Controller
@Path("/conta")
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

	@Get("/{numero}/extrato")
	public void extratoDaConta(String numero) {
		try {
			Conta conta = contaDao.getCountByNumber(numero);
			if (conta != null) {
				if (conta.getTransacoes().size() > 0) {
					result.use(Results.json()).withoutRoot().from(conta.getTransacoes()).serialize();
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

	@Get("/{numero}/deposito/{valor}")
	public void deposito(String numero, Double valor) {
		try {
			Conta conta = contaDao.getCountByNumber(numero);
			if (conta != null) {
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

	@Get("/{numero}/saque/{valor}")
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
					}else {
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
	
	@Get("/{origem}/{destino}/transferencia/{valor}")
	public void transferencia(String origem, String destino, Double valor) {
		try {
			Conta contaOrigem = contaDao.getCountByNumber(origem);
			Conta contaDestino = contaDao.getCountByNumber(destino);
			if (contaOrigem != null && contaDestino != null) {
				if (valor > 0) {
					if (contaOrigem.getSaldo() >= valor) {
						Transacao transacao = new Transacao(TipoTransacao.TRANSFERENCIA, valor, contaOrigem, contaDestino);
						transacaoDao.insert(transacao);
						contaOrigem.setSaldo(contaOrigem.getSaldo() - valor);
						contaDestino.setSaldo(contaDestino.getSaldo() + valor);
						contaDao.update(contaOrigem);
						contaDao.update(contaDestino);
						addSucessMessage("Saque realizado com sucesso!");
					}else {
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
