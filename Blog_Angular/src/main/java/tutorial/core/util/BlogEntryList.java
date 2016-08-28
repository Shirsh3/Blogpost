package tutorial.core.util;

import java.util.List;

import tutorial.core.model.BlogEntry;

public class BlogEntryList {
	
	private List<BlogEntry> blogEntryList;
	
	public long blogId;

    public BlogEntryList(Long blogId, List<BlogEntry> entries) {
        this.blogId = blogId;
        this.blogEntryList = entries;
    }

	public List<BlogEntry> getBlogEntryList() {
		return blogEntryList;
	}

	public void setBlogEntryList(List<BlogEntry> blogEntryList) {
		this.blogEntryList = blogEntryList;
	}

	public long getBlogId() {
		return blogId;
	}

	public void setBlogId(long blogId) {
		this.blogId = blogId;
	}
	
	

}
