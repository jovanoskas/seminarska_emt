package com.example.seminarska_emt.model;

import com.sun.istack.NotNull;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    private Float price;

    @ManyToMany
    private List<Artist> artist;

    @JsonIgnore
    @ManyToMany(mappedBy = "songs")
    private List<ShoppingCart> shoppingCart;

    public Song() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }


    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist){
        this.artist =  artist;
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCart;
    }

    public void setShoppingCart(List<ShoppingCart> shoppingCart){
        this.shoppingCart =  shoppingCart;
    }
}
