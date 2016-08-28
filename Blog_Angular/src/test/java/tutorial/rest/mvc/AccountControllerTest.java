package tutorial.rest.mvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.springframework.test.web.servlet.RequestBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.hamcrest.Matchers;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.service.AccountService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.AccountNotFoundException;
import tutorial.core.service.exception.BlogExistsException;
import tutorial.core.service.repo.AccountRepo;
import tutorial.core.service.repo.BlogRepo;
import tutorial.rest.resources.BlogResource;

public class AccountControllerTest {

	@InjectMocks
	private AccountController controller;

	private MockMvc mockMvc;

	@Mock
	AccountService service;
	

	@Before
	public void setup(){

		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void shouldCreateNewAccount() throws Exception{

		Account createdAccount = getDummyAccount();

		when(service.createAccount(any(Account.class))).thenReturn(createdAccount);

		mockMvc.perform(post("/rest/accounts").content("{\"name\":\"test\",\"password\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON)).
		andExpect(jsonPath("$.username", org.hamcrest.Matchers.is(createdAccount.getUsername()))).
		andExpect(redirectedUrl("http://localhost/rest/accounts/"+createdAccount.getAccountId().toString())).
		andExpect(header().string("Location",Matchers.endsWith("/rest/accounts/1")))
		.andExpect(status().isCreated());
		

		verify(service).createAccount(any(Account.class));
	}

	private Account getDummyAccount() {
		Account createdAccount = new Account();
		createdAccount.setAccountId(1L);
		createdAccount.setPassword("test");
		createdAccount.setUsername("test");
		return createdAccount;
	}
	
	@Test
	public void whenAccountNotExists() throws Exception{

		Account createdAccount = getDummyAccount();

		when(service.createAccount(any(Account.class))).thenThrow(new AccountExistsException());

		mockMvc.perform(post("/rest/accounts")
	                .content("{\"name\":\"test\",\"password\":\"test\"}")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isConflict());

		verify(service).createAccount(any(Account.class));
	}
	
	@Test
	public void getAccountExistsTest() throws Exception{

		Account createdAccount = getDummyAccount();

		when(service.findAccount(any(Long.class))).thenReturn(createdAccount);

		mockMvc.perform(get("/rest/accounts/1").content("{\"name\":\"test\",\"password\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON)).
		andExpect(status().isOk());
		

		verify(service).findAccount(any(Long.class));
	}
	
	@Test
	public void whenAccountNotExistTest() throws Exception{

		Account createdAccount = getDummyAccount();

		when(service.findAccount(any(Long.class))).thenReturn(null);

		mockMvc.perform(get("/rest/accounts/1").content("{\"name\":\"test\",\"password\":\"test\"}")
				.contentType(MediaType.APPLICATION_JSON)).
		andExpect(status().isNotFound());
		

		verify(service).findAccount(any(Long.class));
	}
	
	@Test
	public void shouldCreateBlog() throws Exception{
		
		Blog resource=new Blog();
		Account createdAccount = getDummyAccount();

		resource.setTitle("My First Blog");
		resource.setId(1l);
		resource.setOwner(createdAccount);
		Mockito.when(service.createBlog(Mockito.anyLong(), Mockito.any(Blog.class))).thenReturn(resource);
		
		mockMvc.perform(post("/rest/accounts/1/blogs").contentType(MediaType.APPLICATION_JSON).
				content("{\"title\":\"My First Blog\"}")).andExpect(jsonPath("$.title",Matchers.is(resource.getTitle())))
		.andExpect(jsonPath("$.links[2].href", Matchers.endsWith("/rest/accounts/1")))
		.andExpect(jsonPath("$.links[1].href", Matchers.endsWith("/rest/blogs/1/blog-entries")))
		.andExpect(status().isCreated()).andDo(print());
		
		Mockito.verify(service).createBlog(Mockito.anyLong(), Mockito.any(Blog.class));
		
	}
	
	@Test
	public void cerateBlogWhenAccountNameNotExists() throws Exception{
		
		Blog resource=new Blog();
		Account createdAccount = getDummyAccount();

		resource.setTitle("My First Blog");
		resource.setId(1l);
		resource.setOwner(createdAccount);
		Mockito.when(service.createBlog(Mockito.anyLong(), Mockito.any(Blog.class))).thenThrow(new AccountNotFoundException());
		
		mockMvc.perform(post("/rest/accounts/1/blogs").contentType(MediaType.APPLICATION_JSON).
				content("{\"title\":\"My First Blog\"}"))
		.andExpect(status().isBadRequest()).andDo(print());
		
		Mockito.verify(service).createBlog(Mockito.anyLong(), Mockito.any(Blog.class));
		
	}
	
	@Test
	public void cerateBlogWhenBlogAlreadyExist() throws Exception{
		
		Blog resource=new Blog();
		Account createdAccount = getDummyAccount();

		resource.setTitle("My First Blog");
		resource.setId(1l);
		resource.setOwner(createdAccount);
		Mockito.when(service.createBlog(Mockito.anyLong(), Mockito.any(Blog.class))).thenThrow(new BlogExistsException());
		
		mockMvc.perform(post("/rest/accounts/1/blogs").contentType(MediaType.APPLICATION_JSON).
				content("{\"title\":\"My First Blog\"}"))
		.andExpect(status().isConflict()).andDo(print());
		
		Mockito.verify(service).createBlog(Mockito.anyLong(), Mockito.any(Blog.class));
		
	}
}
