package ch.inf.usi.mindbricks.ui.nav.shop;

public class ShopItem {
    private final String id;
    private final String name;
    private final int price;
    private final int drawableResId;

    public ShopItem(String id, String name, int price, int drawableResId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.drawableResId = drawableResId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getDrawableResId() { return drawableResId; }
    public int getPrice() { return price; }
}
