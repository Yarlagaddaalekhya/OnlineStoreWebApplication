
package johnny.gamestore.mysql.beans;

import java.util.Date;



public class Review implements java.io.Serializable {
    private String id;
    private String productid;
    private String username;
    private int rating;    
    private Date reviewdate;
    private String reviewtext;
    public Review(String id, String productid, String username, int rating, Date reviewdate, String reviewtext) {
        this.id = id;
        this.productid = productid;
        this.username = username;
        this.rating = rating;
        this.reviewdate = reviewdate;
        this.reviewtext = reviewtext;
    }
    
    public String getId() {
        return id;
    }
    
    public String getProductId() {
        return productid;
    }
    
    public String getUserName() {
        return username;
    }
    
    public int getRating() {        
        return rating;
    }    
    
    public Date getReviewDate() {
        return reviewdate;
    }    
        
    public String getReviewText() {
        return reviewtext;
    }
}
