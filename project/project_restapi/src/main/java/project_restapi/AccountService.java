package project_restapi;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import project.Account;

@Path(AccountService.ACCOUNT_SERVICE_PATH)
public class AccountService {
	
	public static final String ACCOUNT_SERVICE_PATH = "Account";
	
	@Inject
	private Account account;
	
	@POST //kanskje bruke PUT?
	@Consumes(MediaType.APPLICATION_JSON)
	//@Produces() , vet ikke hva jeg skal returnere til appControler enda
	public boolean creatAccount(String mail, String password) { //ikke sikker på hva som kommer som argument
		Account newAccount = new Account(mail, password);
		boolean creationSuccess = false;
		try {
			newAccount.createAccount();
			creationSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		return creationSuccess;
		
	}
	

}
