package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.BancoDao;
import br.com.rony.model.Banco;

@Controller
@Path("/banco")
public class BancoController extends BaseController{

	@Inject
	private BancoDao bancoDao;
	
	public BancoController() {
		super();
	}
	
	@Consumes(value = "application/json")
	@Post("")
	public void cadastro(Banco banco) {
		try{
			this.bancoDao.insert(banco);
			addSucessMessage("Banco salvo com sucesso!");
		}catch (Exception e) {
			e.printStackTrace();
			addErrorMessage("Problema ao Salvar Banco!");
		}
		result.use(Results.json()).withoutRoot().from(messages).serialize();
	}
}
