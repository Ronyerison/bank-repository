package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.BancoDao;
import br.com.rony.model.Banco;

/**
 * Controlador para gerenciar as requisições para banco
 * 
 * @author Rony
 *
 */
@Controller
@Path("/banco")
public class BancoController extends BaseController {

	@Inject
	private BancoDao bancoDao;

	public BancoController() {
		super();
	}

	/**
	 * Método para cadastrar um banco
	 * 
	 * @param banco
	 */
	@Consumes(value = "application/json")
	@Post("")
	public void cadastro(Banco banco) {
		try {
			if (banco != null && !banco.getNome().isEmpty() && !banco.getCodigo().isEmpty()) {
				this.bancoDao.insert(banco);
				addSucessMessage("Banco salvo com sucesso!");
			}else {
				addWarningMessage("Campos devem ser preenchidos!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Problema Interno!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}
}
