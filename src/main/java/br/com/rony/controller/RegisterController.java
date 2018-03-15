package br.com.rony.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.view.Results;
import br.com.rony.dao.UserDao;
import br.com.rony.model.User;

@Controller
@Path("/register")
public class RegisterController extends BaseController {

	@Inject
	private UserDao userDao;

	@Consumes(value = "application/json")
	@Post("")
	public void save(User user) {

		System.out.println(user.getName());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		// Caso usuário nao seja válido irá retornar para a página de formulário de novo
		// usuário
//		validator.onErrorForwardTo(RegisterController.class).registerPage();

		// Agora iremos verificar se já existe usuário com este username

		User userExistent = this.userDao.findByUsername(user.getUsername());

		if (userExistent != null) {
			// Caso usuário seja válido e porém já existir, irá redirecionar para o login
			addWarningMessage("Não foi possível cadastrar usuário, usuário já existente");
		} else {

			// Caso usuário seja válido e não existir, irá salvar o usuário e irá
			// redirecionar para o login
			this.userDao.insert(user);
			addSucessMessage("Usuario cadastrado com sucesso!");

		}
		this.result.use(Results.json()).withoutRoot().from(messages).serialize();
	}
}
