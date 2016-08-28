package tutorial.core.service;

import java.util.List;

import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;

public interface BlogService {

	public BlogEntry createBlogEntry(Long blogId,BlogEntry data);
	
	public Blog findBlog(Long blogId);
	
	public BlogEntryList findAllBlogEntries(Long blogId);
	
	public BlogList findAllBlogs();
}
