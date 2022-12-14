
package johnny.gamestore.mysql.beans;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Console extends BaseBean {
    private List<Accessory> accessories = new ArrayList<Accessory>();
    
    public Console(String key, String maker, String name, double price, String image, String retailer, String condition, int discount, List<Accessory> accessories){
        super.setKey(key);
        super.setMaker(maker);
        super.setName(name);
        super.setPrice(price);
        super.setImage(image);
        super.setCondition(condition);
        super.setDiscount(discount);
        super.setRetailer(retailer);
        this.setAccessories(accessories);
    }
    
    public List<Accessory> getAccessories() {
        return accessories;
    }
    public void setAccessories(List<Accessory> accessories) {
        this.accessories = accessories;
    }
}