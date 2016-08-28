package tutorial.rest.mvc;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.service.AccountService;
import tutorial.core.service.BlogEntryService;
import tutorial.core.service.BlogService;
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.AccountNotFoundException;
import tutorial.core.service.exception.BlogExistsException;
import tutorial.core.service.exception.BlogNotFoundException;
import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;

public class BlogControllerTest {


	@InjectMocks
	private BlogController controller;

	private MockMvc mockMvc;

	@Mock
	BlogService service;

	@Before
	public void setup(){

		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldCreateNewBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		Blog blog=new Blog();		
		Account account=new Account();

		account.setAccountId(100l);
		account.setPassword("testPassword");
		account.setUsername("Shirsh");

		blog.setOwner(account);
		blog.setId(22l);
		blog.setTitle("Blog Title -1");

		entry.setTitle("Blog Entry 1");
		entry.setBlog(blog);
		entry.setId(10l);

		Mockito.when(service.createBlogEntry(Mockito.anyLong(),Mockito.any(BlogEntry.class))).thenReturn(entry);
		mockMvc.perform(post("/rest/blogs/10/blog-entries").contentType(MediaType.APPLICATION_JSON)
				.content("{\"title\":\"Blog Entry 1\"}"))
		.andExpect(jsonPath("$.title", Matchers.is(entry.getTitle())))
		.andExpect(jsonPath("$.links[*].href", Matchers.hasItem(Matchers.endsWith("/rest/blogs/22"))))
		.andExpect(jsonPath("$.links[*].rel", Matchers.hasItems(is("self"),is("blog"))))
		.andExpect(status().isCreated())
		.andDo(print());
		Mockito.verify(service).createBlogEntry(Mockito.anyLong(),Mockito.any(BlogEntry.class));
	}

	@Test
	public void findAllBlogs() throws Exception {
		List<Blog> list = new ArrayList<Blog>();
		Account account=new Account();
		BlogEntry entry=new BlogEntry();
		BlogEntry entry1=new BlogEntry();
		
		account.setAccountId(100l);
		account.setPassword("testPassword");
		account.setUsername("Shirsh");
		
		
		Account account1=new Account();

		account1.setAccountId(200l);
		account1.setPassword("password");
		account1.setUsername("Vivek");

		Blog blogA = new Blog();
		blogA.setId(1L);
		blogA.setTitle("Title A");
		blogA.setOwner(account);
		list.add(blogA);

		Blog blogB = new Blog();
		blogB.setId(2L);
		blogB.setTitle("Title B");
		list.add(blogB);

		entry.setTitle("Blog Entry 1");
		entry.setBlog(blogA);
		entry.setId(10l);
		
		entry1.setTitle("Blog Entry 2");
		entry1.setBlog(blogB);
		entry1.setId(20l);
		BlogList allBlogs = new BlogList(list);

		when(service.findAllBlogs()).thenReturn(allBlogs);

		mockMvc.perform(get("/rest/blogs"))
		.andExpect(jsonPath("$.blogs[*].title",
			hasItems(endsWith("Title A"), endsWith("Title B"))))
		.andExpect(status().isOk()).andDo(print());
	}

	 @Test
	    public void getBlog() throws Exception {
	        Blog blog = new Blog();
	        blog.setTitle("Test Title");
	        blog.setId(1L);

	        Account account = new Account();
	        account.setAccountId(1L);
	        blog.setOwner(account);

	        when(service.findBlog(1L)).thenReturn(blog);

	        mockMvc.perform(get("/rest/blogs/1")).andDo(print());
	        mockMvc.perform(get("/rest/blogs/1"))
	                .andExpect(jsonPath("$.links[*].href",
	                        hasItem(endsWith("/blogs/1"))))
	                .andExpect(jsonPath("$.links[*].href",
	                        hasItem(endsWith("/blogs/1/blog-entries"))))
	                .andExpect(jsonPath("$.links[*].href",
	                        hasItem(endsWith("/accounts/1"))))
	                .andExpect(jsonPath("$.links[*].rel",
	                        hasItems(is("self"), is("owner"), is("entries"))))
	                .andExpect(jsonPath("$.title", is("Test Title")))
	                .andExpect(status().isOk()).andDo(print());
	    }

	    @Test
	    public void createBlogEntryExistingBlog() throws Exception {
	        Blog blog = new Blog();
	        blog.setId(1L);

	        BlogEntry entry = new BlogEntry();
	        entry.setTitle("Test Title");
	        entry.setId(1L);

	        when(service.createBlogEntry(eq(1L), any(BlogEntry.class))).thenReturn(entry);

	        mockMvc.perform(post("/rest/blogs/1/blog-entries")
	                .content("{\"title\":\"Generic Title\"}")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(jsonPath("$.title", is(entry.getTitle())))
	                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("rest/blog-entries/1"))))
	                .andExpect(jsonPath("$.links[*].rel",
	                        hasItems(is("self"))))
	                .andExpect(header().string("Location", endsWith("rest/blog-entries/1")))
	                .andExpect(status().isCreated()).andDo(print());
	    }


	    @Test
	    public void createBlogEntryNonExistingBlog() throws Exception {
	        when(service.createBlogEntry(eq(1L), any(BlogEntry.class))).thenThrow(new BlogNotFoundException());

	        mockMvc.perform(post("/rest/blogs/1/blog-entries")
	                .content("{\"title\":\"Generic Title\"}")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isNotFound());
	    }

	    @Test
	    public void listBlogEntriesForExistingBlog() throws Exception {

	        BlogEntry entryA = new BlogEntry();
	        Blog blog=new Blog();
	        
	        blog.setId(1l);
	        entryA.setId(1L);
	        entryA.setTitle("Test Title");

	        BlogEntry entryB = new BlogEntry();
	        entryB.setId(2L);
	        entryB.setTitle("Test Title");

	        List<BlogEntry> blogListings = new ArrayList();
	        blogListings.add(entryA);
	        blogListings.add(entryB);

	        BlogEntryList list = new BlogEntryList(blog.getId(),blogListings);

	        when(service.findAllBlogEntries(1L)).thenReturn(list);

	        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
	                .andDo(print())
	                .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/blogs/1/blog-entries"))))
	                .andExpect(jsonPath("$.entries[*].title", hasItem(is("Test Title"))))
	                .andExpect(status().isOk());
	        verify(service).findAllBlogEntries(1L);
	    }

	    @Test
	    public void listBlogEntriesForNonExistingBlog() throws Exception {
	        when(service.findAllBlogEntries(1L)).thenThrow(new BlogNotFoundException());

	        mockMvc.perform(get("/rest/blogs/1/blog-entries"))
	                .andDo(print())
	                .andExpect(status().isNotFound());
	    }

}
