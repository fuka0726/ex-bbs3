package domein;

public class Article {

	
	
	/** ID */
	private Integer id;
	/** 名前 */
	private String name;
	/** コンテンツ */
	private String contents;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return "Article [id=" + id + ", name=" + name + ", contents=" + contents + "]";
	}
	
	
	
}
