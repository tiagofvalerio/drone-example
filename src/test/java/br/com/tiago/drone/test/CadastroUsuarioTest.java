package br.com.tiago.drone.test;

import static org.jboss.arquillian.graphene.Graphene.guardHttp;
import static org.jboss.arquillian.graphene.Graphene.waitAjax;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.logging.Logger;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;

import br.com.tiago.drone.dao.UserDAO;
import br.com.tiago.drone.dao.impl.UserDAOImpl;
import br.com.tiago.drone.impl.LoginController;
import br.com.tiago.drone.model.Credentials;
import br.com.tiago.drone.model.User;

@RunWith(Arquillian.class)
public class CadastroUsuarioTest {
	
	private static final Logger logger = Logger.getLogger("CadastroUsuarioTest");

	private static final String WEBAPP_SRC = "src/main/webapp";

	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		return ShrinkWrap
				.create(WebArchive.class, "exemplo.war")
				.addClasses(Credentials.class, User.class,
						LoginController.class, UserDAO.class, UserDAOImpl.class)
				.merge(ShrinkWrap.create(GenericArchive.class)
						.as(ExplodedImporter.class).importDirectory(WEBAPP_SRC)
						.as(GenericArchive.class), "/",
						Filters.include(".*\\.xhtml$"))
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource(
						new StringAsset("<faces-config version=\"2.0\"/>"),
						"faces-config.xml")
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml");
	}
	
	@Drone
	private FirefoxDriver browser;

	// Injeta a URL da aplicação
	@ArquillianResource
	private URL deploymentUrl;

	// Injeta o elemento que referencia o inputtext userName
	@FindBy(id = "cadastroForm:userName")
	private WebElement userName;

	@FindBy(id = "cadastroForm:password")
	private WebElement password;
	
	@FindBy(id = "cadastroForm:cpf")
	private WebElement cpf;
	
	@FindBy(id = "cadastroForm:nome")
	private WebElement nome;
	
	@FindBy(id = "cadastroForm:endereco")
	private WebElement endereco;
	
	@FindBy(id = "cadastroForm:cadastrar")
	private WebElement cadastroButton;

	// Injeta o primeiro elemento que possui a tag "li"
	@FindBy(tagName = "li")
	private WebElement facesMessage;

	@FindBy(css = "input[type=submit]")
	private WebElement whoAmI;

	// Injeta um elemento usando jQuery selector
	@FindByJQuery("p:visible")
	private WebElement signedAs;
	
	@Test
	public void cadastroUsuarioTeste() {

		try {
			// Abre a página de teste
			browser.get(deploymentUrl.toExternalForm() + "cadastrarUsuario.jsf");

			logger.info("Title" + browser.getTitle());
			logger.info("Page Source" + browser.getPageSource());
			logger.info("Window Handle" + browser.getWindowHandle());

			userName.sendKeys("ediel@gmail.com");
			password.sendKeys("123456");
			cpf.sendKeys("99999999999");
			nome.sendKeys("ediel");
			endereco.sendKeys("Rua teste, 270. Ribeirao Preto SP.");

			// Sincroniza a requisição da página
			guardHttp(cadastroButton).click();

			assertEquals("User registered sucefully", facesMessage.getText().trim());

			whoAmI.click();

			// Sincroniza a requisição AJAX
			waitAjax().until().element(signedAs).is().present();

			assertTrue(signedAs.getText().contains("ediel@gmail.com"));
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
