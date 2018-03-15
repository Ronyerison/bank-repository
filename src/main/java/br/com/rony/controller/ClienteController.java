/**
 * 
 */
package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.AgenciaDao;
import br.com.rony.dao.ClienteDao;
import br.com.rony.dao.ContaDao;
import br.com.rony.model.Agencia;
import br.com.rony.model.Cliente;
import br.com.rony.model.Conta;
import br.com.rony.model.vo.ClienteVO;

/**
 * @author Rony
 *
 */
@Controller
@Path("/cliente")
public class ClienteController extends BaseController {

	@Inject
	private ClienteDao clienteDao;

	@Inject
	private ContaDao contaDao;

	@Inject
	private AgenciaDao agenciaDao;

	public ClienteController() {
		super();
	}

	@Consumes(value = "application/json")
	@Post("")
	public void cadastro(ClienteVO cliente) {
		try {
			// criando o objeto que será persistido
			if (clienteDao.fincByCpf(cliente.getCpf()) == null) {

				Cliente clienteBD = new Cliente(cliente);
				Agencia agencia = agenciaDao.find(cliente.getAgencia());
				// verificando se a agencia existe
				if (agencia != null) {
					clienteBD.setAgencia(agencia);

					// criando a conta do cliente
					Conta conta = new Conta();
					conta.setAgencia(agencia);
					conta.setCliente(clienteBD);
					Conta contaBD = this.contaDao.update(conta);
					String numero = (1000 + contaBD.getId()) + "-" + contaBD.getAgencia().getId();
					contaBD.setNumero(numero);
					this.contaDao.update(contaBD);
					addSucessMessage("Cliente salvo com sucesso!\n Numero da Conta: " + contaBD.getNumero());

				} else {
					addErrorMessage("Agencia não cadastrada!");
				}
			}else {
				addErrorMessage("Já existe cliente cadastrado com o CPF informado!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Problema ao Salvar Banco!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}
	
	
}
