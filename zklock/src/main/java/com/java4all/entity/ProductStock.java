package com.java4all.entity;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/28 15:04
 */

public class ProductStock {

  private Integer id;

  private String name;

  private Integer stock;

  @Override
  public String toString() {
    return "ProductStock{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", stock=" + stock +
        '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }
}
