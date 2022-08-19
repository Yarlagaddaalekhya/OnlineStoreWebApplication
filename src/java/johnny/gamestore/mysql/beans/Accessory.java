
package johnny.gamestore.mysql.beans;


public class Accessory extends BaseBean {
    public Accessory(String key, String maker, String name, double price, String image, String retailer, String condition, double discount){
        super.setKey(key);
        super.setMaker(maker); // parent console
        super.setName(name);
        super.setPrice(price);
        super.setImage(image);
        super.setCondition(condition);
        super.setDiscount(discount);
        super.setRetailer(retailer);
    }
}