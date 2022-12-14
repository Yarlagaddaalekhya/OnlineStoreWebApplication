
package johnny.gamestore.mysql.beans;


public class Menu {
    private String name;
    private String title;
    private String url;
    
    public Menu(String name, String url) {
        this.name = name;
        this.title = name;
        this.url = url;
    }
    public Menu(String name, String title, String url) {
        this.name = name;
        this.title = title;
        this.url = url;
    }
     
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
