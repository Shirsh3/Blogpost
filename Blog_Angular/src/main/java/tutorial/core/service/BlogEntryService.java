package tutorial.core.service;

import tutorial.core.model.BlogEntry;

public interface BlogEntryService {

	public BlogEntry findBlogEntry(Long blogId);
	
	public BlogEntry deleteBlogEntry(Long blogId);
	
	public BlogEntry updateBlogEntry(Long blogId,BlogEntry entry);
}
