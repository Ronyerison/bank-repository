/**
 * 
 */
package br.com.rony.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.rony.controller.BancoController;
import br.com.rony.dao.AgenciaDao;
import br.com.rony.dao.BancoDao;
import br.com.rony.model.Banco;

/**
 * @author Rony
 *
 */
@RunWith(Arquillian.class)
public class BancoTest {
	
	@Inject
	private BancoDao bancoDao;

	@Deployment
	public static WebArchive create() {
		WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(BancoDao.class.getPackage())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsResource("META-INF/persistence.xml")
				.addClass(AgenciaDao.class)
				.addPackage(Banco.class.getPackage())
				.addPackage(BancoController.class.getPackage())
				.setWebXML("web.xml");

		return webArchive;
	}

	@Test
	public void testBancoDao() {
		bancoDao.getEntityClass();
	}
}
