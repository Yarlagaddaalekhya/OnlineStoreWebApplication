
package johnny.gamestore.mysql.beans;


public class CartItem {
    private ProductItem item;
    private int quantity;

    public CartItem(ProductItem item) {
        setItem(item);
        setQuantity(1);
    }

    public ProductItem getItem() {
        return item;
    }

    protected void setItem(ProductItem item) {
        this.item = item;
    }

    public String getItemId() {
        return getItem().getId();
    }
    
    public String getItemName() {
        return getItem().getName();
    }

    public int getItemType() {
        return getItem().getType();
    }

    public double getUnitPrice() {
        return getItem().getDiscountedPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementItemQuantity() {
        setQuantity(getQuantity() + 1);
    }

    public void cancelOrder() {
        setQuantity(0);
    }

    public double getTotalCost() {
        return(getQuantity() * getUnitPrice());
    }
}
