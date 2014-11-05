import java.math.*;
import java.util.*;


/**
The java file supports creation of a product which consists of three attributes:
1. ProductID : a unique id consists of long int
2. ProductName : a one or more long ints 
3. Product Price: price consist dollar and cents
@author: Debraj Maity (dxm116130)
*/
class dxm116130Product {
	

private Long productId;
private ArrayList<Long> productName;			
/*Since name consist of multiple Long ints we are storing it in ArrayList<Long> */

private BigDecimal productPrice;
/* To represent each product price in proper dollar and cents numerically its represented as Bigdeicmal with scale upto 2 decimal*/
private long productPrice;

/*This constructor creates the product object based on three input
  productID, Productprice, productNames
*/
dxm116130Product(Long productId,BigDecimal productPrice, ArrayList<Long> productName){
	this.productId=productId;
	this.productName=productName;
	this.productPrice=productPrice;
}

dxm116130Product(Long productId,Long productPrice, ArrayList<Long> productName){
	this.productId=productId;
	this.productName=productName;
	this.productPrice=(long)(productPrice*100);
}
/*This constructor creates the product object based string which should
  productID, Productprice, productNames separated by space
*/
dxm116130Product(String newProduct) {

	String[] productAttributes = newProduct.split("\\s+");
	this.productId=Long.parseLong(productAttributes[0]);
	this.productPrice=new BigDecimal(productAttributes[1]);
	this.productName=new ArrayList<Long>(productAttributes.length-1);

	int len=2;
	while(len<(productAttributes.length-1)) {

		(this.productName).add(Long.parseLong(productAttributes[len]));
		len++;

	}


}


public Long getProductId(){
	return this.productId;
}

public ArrayList<Long> getProductNames(){
	return this.productName;
}

public BigDecimal getProductPrice(){
	//return (this.productPrice).getPrice();
	return this.productPrice;
}

/**
 This method calculates the price hiking of the product based on the given percentage
 Input: String which represents percentage
 Returns the net increases of the prices. 
*/
public BigDecimal hikePrice(String percentage){

	BigDecimal h=new BigDecimal(percentage);
	h=h.divide(new BigDecimal(100));
	h=h.multiply(this.productPrice);
	h=h.setScale(2, RoundingMode.DOWN);

	this.productPrice=(this.productPrice).add(h);

	//System.out.println("Return value pricehike for id:"+this.productId+"  "+ h);

	return h;

}


}

