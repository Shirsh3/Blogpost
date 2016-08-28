package tutorial.core.service.repo;

import java.util.List;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;

public interface BlogEntryRepo {

	public BlogEntry findBlogEntry(Long blogId);
	
	public BlogEntry deleteBlogEntry(Long blogId);
	
	public BlogEntry updateBlogEntry(Long blogId,BlogEntry entry);
	
	public BlogEntry createBlogEntry(BlogEntry data);

    public List<BlogEntry> findByBlogId(Long blogId);

}
