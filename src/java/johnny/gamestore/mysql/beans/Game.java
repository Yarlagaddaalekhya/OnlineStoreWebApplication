
package johnny.gamestore.mysql.beans;


public class Game extends BaseBean {
    public Game(String key, String maker, String name, double price, String image, String retailer, String condition, double discount){
        super.setKey(key);
        super.setMaker(maker);
        super.setName(name);
        super.setPrice(price);
        super.setImage(image);
        super.setCondition(condition);
        super.setDiscount(discount);
        super.setRetailer(retailer);
    }
}
