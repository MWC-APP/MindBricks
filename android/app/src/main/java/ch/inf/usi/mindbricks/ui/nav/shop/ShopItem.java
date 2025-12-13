package ch.inf.usi.mindbricks.ui.nav.shop;

public class ShopItem {
    private final String id;
    private final String name;
    private final int drawableResId;
    private final int price;

    public ShopItem(String id, String name, int drawableResId, int price) {
        this.id = id;
        this.name = name;
        this.drawableResId = drawableResId;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getDrawableResId() { return drawableResId; }
    public int getPrice() { return price; }
}
