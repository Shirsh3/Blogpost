package tutorial.core.service.repo;

import java.util.List;

import tutorial.core.model.Account;
import tutorial.core.model.Blog;
import tutorial.core.model.BlogEntry;
import tutorial.core.util.BlogEntryList;
import tutorial.core.util.BlogList;

public interface BlogRepo {

	public Blog createBlog(Blog data);
    public List<Blog> findAllBlogs();
    public Blog findBlog(Long id);
    public Blog findBlogByTitle(String title);
    public List<Blog> findBlogsByAccount(Long accountId);
}
