package tutorial.rest.mvc;


import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import tutorial.core.model.BlogEntry;
import tutorial.core.service.BlogEntryService;
import tutorial.rest.resources.BlogEntryResource;
import tutorial.rest.resources.asm.BlogEntryResourceAsm;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RequestMapping("/rest/blog-entries")
@Controller
public class BlogEntryController{

	
	BlogEntryService service;

	@Autowired
	public BlogEntryController(BlogEntryService service) {
		this.service = service;
	}
	
	@RequestMapping(value="/{blogId}", method=RequestMethod.GET)
	public ResponseEntity<BlogEntryResource> getExistingBlogEntry(@PathVariable Long blogId) {
		
		BlogEntry blogEntry=service.findBlogEntry(blogId);
		
		if(blogEntry==null)
			return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
		BlogEntryResource resource=new BlogEntryResourceAsm().toResource(blogEntry);
		
		return new ResponseEntity<BlogEntryResource>(resource,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{blogId}", method=RequestMethod.DELETE)
	public ResponseEntity<BlogEntryResource> deleteBlogEntry(@PathVariable Long blogId) {
		
		BlogEntry blogEntry=service.deleteBlogEntry(blogId);
		
		if(blogEntry==null)
			return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
		BlogEntryResource resource=new BlogEntryResourceAsm().toResource(blogEntry);
		
		return new ResponseEntity<BlogEntryResource>(resource,HttpStatus.OK);
	}
	
	@RequestMapping(value="/{blogId}", method=RequestMethod.PUT)
	public ResponseEntity<BlogEntryResource> updateBlogEntry(@PathVariable Long blogId,@RequestBody BlogEntry entry) {
		
		BlogEntry blogEntry=service.updateBlogEntry(blogId,entry);
		
		if(blogEntry==null)
			return new ResponseEntity<BlogEntryResource>(HttpStatus.NOT_FOUND);
		BlogEntryResource resource=new BlogEntryResourceAsm().toResource(blogEntry);
		
		return new ResponseEntity<BlogEntryResource>(resource,HttpStatus.OK);
	}
}
