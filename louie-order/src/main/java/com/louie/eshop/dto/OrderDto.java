package com.louie.eshop.dto;

public class OrderDto {
	private String goodsName;
	private Double price;
	
	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderDto(String goodsName, Double price) {
		super();
		this.goodsName = goodsName;
		this.price = price;
	}

	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
