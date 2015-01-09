package br.com.tiago.drone.impl;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.tiago.drone.dao.UserDAO;
import br.com.tiago.drone.model.Credentials;
import br.com.tiago.drone.model.User;

@Named
@SessionScoped
public class LoginController implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String SUCCESS_MESSAGE = "Welcome";
	private static final String SUCCESS_MESSAGE2 = "User registered sucefully";
	private static final String FAILURE_MESSAGE = "Incorrect username and password combination";
	private static final String FAILURE_MESSAGE2 = "Error saving new User";

	private User currentUser;
	private boolean renderedLoggedIn = false;

	@Inject
	private Credentials credentials;

	@Inject
	private UserDAO userDAO;

	public String login() {
		System.out.println("Entrou no login");
		try {
			 System.out.println("Cadastrando usuário para gambi...");
			 
				currentUser = new User();
				currentUser.setId(1L);
				currentUser.setUserName(credentials.getUsername());
				currentUser.setPassWord(credentials.getPassword());
				currentUser.setNome("tiago");
				currentUser.setCpf("");
				currentUser.setEndereco("");

				userDAO.save(currentUser);
			 
				System.out.println("Buscando usuário com username: " +
						 credentials.getUsername());
				currentUser = userDAO.findByUserName(credentials.getUsername());

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (currentUser != null) {
			//currentUser = new User(1L, "tiago", "tiidle@gmail.com");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(SUCCESS_MESSAGE));
			return "home.xhtml";
		}

		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, FAILURE_MESSAGE,
						FAILURE_MESSAGE));
		return null;
	}

	public String cadastrarUsuario() {
		try {
			currentUser = new User();
			currentUser.setId(2L);
			currentUser.setUserName(credentials.getUsername());
			currentUser.setPassWord(credentials.getPassword());
			currentUser.setNome(credentials.getNome());
			currentUser.setCpf(credentials.getCpf());
			currentUser.setEndereco(credentials.getEndereco() != null
					&& credentials.getEndereco() != "" ? credentials
					.getEndereco() : "");

			userDAO.save(currentUser);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(SUCCESS_MESSAGE2));

			return "home.xhtml";

		} catch (Exception e) {
			e.printStackTrace();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							FAILURE_MESSAGE2, FAILURE_MESSAGE2));
			return null;
		}
	}

	public boolean isRenderedLoggedIn() {
		if (currentUser != null) {
			return renderedLoggedIn;
		} else {
			return false;
		}
	}

	public void renderLoggedIn() {
		this.renderedLoggedIn = true;
	}

	@Produces
	@Named
	public User getCurrentUser() {
		return currentUser;
	}
}
