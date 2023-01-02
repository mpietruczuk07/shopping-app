package pl.edu.pb.shoppingapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Products")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private int quantity;
    private String note;
    private Boolean inCart;

    public Product(String title, int quantity, String note, Boolean inCart) {
        this.title = title;
        this.quantity = quantity;
        this.note = note;
        this.inCart = inCart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }
}