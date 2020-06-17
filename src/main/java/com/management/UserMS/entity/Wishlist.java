package com.management.UserMS.entity;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name="Wishlist")
public class Wishlist{

	@EmbeddedId
	private CartId cartId;

	public CartId getCartId() {
		return cartId;
	}

	public void setCartId(CartId cartId) {
		this.cartId = cartId;
	}
	
}