package tutorial.rest.mvc;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
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
import tutorial.core.service.exception.AccountExistsException;
import tutorial.core.service.exception.AccountNotFoundException;
import tutorial.core.service.exception.BlogExistsException;

public class BlogEntryControllerTest {


	@InjectMocks
	private BlogEntryController controller;

	private MockMvc mockMvc;

	@Mock
	BlogEntryService service;

	@Before
	public void setup(){

		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(controller).build();
	}
	@Test
	public void shouldGetExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		Blog blog=new Blog();
		blog.setId(2l);
		entry.setId(1l);
		entry.setTitle("First Blog Entry");
		
		entry.setBlog(blog);

		when(service.findBlogEntry(any(Long.class))).thenReturn(entry);

		mockMvc.perform(get("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON)).
		andExpect(jsonPath("$.title", org.hamcrest.Matchers.is(entry.getTitle())))
		.andExpect(jsonPath("$.links[*].href", org.hamcrest.Matchers.hasItem(Matchers.endsWith("/rest/blog-entries/1"))))
		.andExpect(jsonPath("$.links[*].rel", org.hamcrest.Matchers.hasItems(is("self"),is("blog"))))
		.andExpect(status().isOk()).andDo(print());

		verify(service).findBlogEntry(any(Long.class));
	}
	
	public void shouldGetNonExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		entry.setId(1l);
		entry.setTitle("First Blog Entry");

		when(service.findBlogEntry(any(Long.class))).thenReturn(null);

		mockMvc.perform(get("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andDo(print());

		verify(service).findBlogEntry(any(Long.class));
	}
	
	@Test
	public void shouldDeleteExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		entry.setId(1l);
		entry.setTitle("First Blog Entry");

		when(service.deleteBlogEntry(any(Long.class))).thenReturn(entry);

		mockMvc.perform(delete("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk()).andDo(print());

		verify(service).deleteBlogEntry(any(Long.class));
	}
	
	public void shouldDeleteNonExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		entry.setId(1l);
		entry.setTitle("First Blog Entry");

		when(service.findBlogEntry(any(Long.class))).thenReturn(null);

		mockMvc.perform(delete("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andDo(print());

		verify(service).deleteBlogEntry(any(Long.class));
	}

	@Test
	public void shouldUpdateExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		Blog blog=new Blog();
		blog.setId(100l);
		blog.setTitle("Updated blog Entry");
		entry.setId(1l);
		entry.setTitle("First Blog Entry");
		entry.setBlog(blog);

		when(service.updateBlogEntry(any(Long.class),any(BlogEntry.class))).thenReturn(entry);

		mockMvc.perform(put("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON)).
		andExpect(jsonPath("$.title", org.hamcrest.Matchers.is(entry.getTitle())))
		.andExpect(jsonPath("$.links[*].href", org.hamcrest.Matchers.hasItem(Matchers.endsWith("/rest/blog-entries/1"))))
		.andExpect(jsonPath("$.links[*].rel", org.hamcrest.Matchers.hasItems(is("self"),is("blog"))))
		.andExpect(status().isOk()).andDo(print());

		verify(service).updateBlogEntry(any(Long.class),any(BlogEntry.class));
	}
	
	@Test
	public void shouldUpdateNonExistingBlogEntry() throws Exception{

		BlogEntry entry=new BlogEntry();
		entry.setId(1l);
		entry.setTitle("First Blog Entry");

		//when(service.updateBlogEntry(any(Long.class))).thenReturn(null);

		mockMvc.perform(put("/rest/blog-entries/1").content("{\"title\":\"First Blog Entry\"}")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound()).andDo(print());

		//verify(service).updateBlogEntry(any(Long.class));
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
}
