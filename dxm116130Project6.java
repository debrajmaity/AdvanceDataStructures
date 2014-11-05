import java.util.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.math.*;
/**
This is main program for project 6 at CS 6301.016 .The java file supports creation of a multi-dimensional search capability
for the product item object which is defined in dxm116130Product.java file.
@author: Debraj Maity (dxm116130)
*/
public class dxm116130Project6 {

	/* The treemap is used to store the product based on product ID*/
	private static TreeMap<Long, dxm116130Product> productListOnID = new TreeMap<Long, dxm116130Product>();

	/* The hashmap is used to store the limited product data based on product names. Each name is mapped to TreeMap which stores
	all the unique product and their corresponding price.
	*/
	private static HashMap<Long, TreeMap<Long,BigDecimal>> productListOnNames=new HashMap<Long,TreeMap<Long,BigDecimal>>();
	
	public static void main(String args[]) {

		System.out.println("--------Project 6 for (CS 6301.016) Advanced Data Structures and Implementation--------");
		System.out.println("--------@Author: Debraj Maity (dxm116130)--------");
		System.out.println("Please enter input data (directly copy & paste if input is large)!");
		System.out.println("<Press Enter twice> once the inputs have been given:");

		boolean printDetailoutput=false;

		if(args.length==1){
			printDetailoutput=true;
		}


		Scanner in = new Scanner(System.in);
    
	    String inputStr = null;
	    int countInsert=0;
	    int countFind=0;
	    int countDelete=0;
	    int countComments=0;
	    int countPriceHike=0;
	    int countFindMinPrice=0;
	    int countFindMaxPrice=0;
	    int countFindPriceRange=0;

	    BigDecimal output=new BigDecimal(0);
	    List<String> list = new ArrayList<String>();
	    while ((inputStr = in.nextLine()).length() > 0) {
	     list.add(inputStr);
	    }
	     
	     if(printDetailoutput)System.out.println("\nDetails output");

	     System.out.println("Processing....Please wait!");
	  
	     long l1 = System.currentTimeMillis();
	     for (int j = 0; j < list.size(); j++){

	     		String input=list.get(j);

		     	if(input.startsWith("Insert ")){

		      	//String newProduct=input.replace("Insert ","");
		      	String[] productAttributes = input.split("\\s+");
				Long productId=Long.parseLong(productAttributes[1]);
				BigDecimal productPrice=new BigDecimal(productAttributes[2]);
				ArrayList<Long> productName=new ArrayList<Long>(productAttributes.length-3);

				int len=3;
				while(len<(productAttributes.length-1)) {

					productName.add(Long.parseLong(productAttributes[len]));
					len++;

				}

				int val=Insert(productId,productPrice,productName);

				 if(printDetailoutput)System.out.println((j+1)+":"+" Insert: "+val);

				output=output.add(new BigDecimal(val));
		      	//countInsert++;

		      }else if(input.startsWith("PriceHike ")){
		      	//String hikedetails=input.replace("PriceHike ","");
		      	String[] hikedetailAttributes = input.split("\\s+");

		      	BigDecimal val=PriceHike(hikedetailAttributes[1],hikedetailAttributes[2],hikedetailAttributes[3]);

		      	 if(printDetailoutput)System.out.println((j+1)+":"+" PriceHike: "+val);

		      	output=output.add(val);
		      	//countPriceHike++;

		      }else if(input.startsWith("Find ")){

		      	String[] id=input.split("\\s+");
		      	BigDecimal val=Find(id[1]);

		      	if(printDetailoutput)System.out.println((j+1)+":"+" Find: "+val);

		      	output=output.add(val);
		      	//countFind++;

		      }else if(input.startsWith("Delete ")){

		      	String[] id=input.split("\\s+");
		      	Long val=Delete(id[1]);
		      	output=output.add(new BigDecimal(val));

				if(printDetailoutput)System.out.println((j+1)+":"+" Delete: "+val);

		      	//countDelete++;

		      }else if(input.startsWith("FindMinPrice ")){
		      	String[] name=input.split("\\s+");
		      	BigDecimal val=FindMinPrice(name[1]);

		      	if(printDetailoutput)System.out.println((j+1)+":"+" FindMinPrice: "+val);
		      	output=output.add(val);
		      	//countFindMinPrice++;
		      }else if(input.startsWith("FindMaxPrice ")){
		      	String[] name=input.split("\\s+");
		      	BigDecimal val=FindMaxPrice(name[1]);

		      	if(printDetailoutput)System.out.println((j+1)+":"+" FindMaxPrice: "+val);
		      	output=output.add(val);
		      	//countFindMaxPrice++;

		      }else if(input.startsWith("FindPriceRange ")){
		      	//String rangedetails=input.replace("FindPriceRange ","");
		      	String[] rangedetailAttributes = input.split("\\s+");

		      	Long val=FindPriceRange(rangedetailAttributes[1],rangedetailAttributes[2],rangedetailAttributes[3]);	

		      	if(printDetailoutput)System.out.println((j+1)+":"+" FindPriceRange: "+val);

		      	output=output.add(new BigDecimal(val));
		      	//countFindPriceRange++;
		      }else if(input.startsWith("# ")){
		      	//countComments++;
		      }

	     }
	      
	     long l2 = System.currentTimeMillis();
    	 long l3 = l2 - l1;
    	 
	   // }


	    System.out.println("Output: "+ output);
	    System.out.println("Execution time:" + l3+" ms");

	   /* 
	    System.out.println("TreeMap Based on productID: "+productListOnID);
	    System.out.println("TreeMap Based on productName: "+productListOnNames);
	   	System.out.println("Insert "+countInsert);
	    System.out.println("Find "+countFind);
	    System.out.println("Delete "+countDelete);
	    System.out.println("PriceHike "+countPriceHike);
	    System.out.println("FindPriceRange "+countFindPriceRange);
	    System.out.println("FindMaxPrice "+countFindMaxPrice);
	    System.out.println("FindMinPrice "+countFindMinPrice);
	    System.out.println("Comments "+countComments);*/

	}

	/**
	This method inserts new product based on id, price and productname.
	@input: Long,BigDecimal,ArrayList<Long>
	@return 1 if the item is new, and 0 otherwise.

	*/
	static int Insert(Long productId,BigDecimal productprice, ArrayList<Long> productname){

		int returnVal=1;
		dxm116130Product newProduct=null;
		if(productname.size()==0){
			dxm116130Product currProduct=productListOnID.get(productId);
			newProduct=new dxm116130Product(productId,productprice,currProduct.getProductNames());
		}else{
			newProduct=new dxm116130Product(productId,productprice,productname);
		}
		
		if(productListOnID.containsKey(productId)){
				Delete(productId.toString());
				returnVal=0;
		}
		
		productListOnID.put(productId,newProduct);
		InsertNames(newProduct.getProductNames(),newProduct);
		
		return returnVal;
		
	}

	/**
	This method deletes a product based on id.
	@input: String
	@return Long that is sum of names of the item deleted, and 0 if id does not exist.

	*/
	static Long Delete(String productId){
		
		Long returnVal=new Long(0);

		Long key=Long.parseLong(productId);

		if(productListOnID.containsKey(key)){
		
			dxm116130Product product=productListOnID.get(key);
			ArrayList<Long> productnames=product.getProductNames();

			returnVal=removeNames(productnames,key);
			productListOnID.remove(key);
		}


		return returnVal;

	}

	/**
	This method finds a product based on id.
	@input: String
	@return BigDecimal Price of the product and 0 if id does not exist.

	*/
	static BigDecimal Find(String productId){

		BigDecimal price=null;

		Long key=Long.parseLong(productId);

		if(!productListOnID.containsKey(key)){
			price=new BigDecimal("0");
		}else{

			dxm116130Product product=productListOnID.get(key);
			price=product.getProductPrice();

		}
		
		return price;


	}


	
/**
 This method increase the price of every product, whose id is in the range [l,h]
 Input are three strings represent lowerRangeID, higherRangeID and percentage r
 Returns the sum of the net increases of the prices. 
*/

	
	static BigDecimal PriceHike(String l,String h, String r) {

		Long lowerRangeId=Long.parseLong(l);
		Long higherRangeId=Long.parseLong(h);
		BigDecimal hike=new BigDecimal(0);

		SortedMap<Long, dxm116130Product> range = new TreeMap<Long, dxm116130Product>();
		range=productListOnID.subMap(lowerRangeId,true,higherRangeId,true);

		Set<Long> keys = range.keySet();

		//System.out.println("Found PriceHike keyset: "+keys);
		for(Long key: keys){
			
			//System.out.println("Found PriceHike key: "+key);
			dxm116130Product currProduct=productListOnID.get(key);
			hike=hike.add(currProduct.hikePrice(r));
			Insert(currProduct.getProductId(),currProduct.getProductPrice(),currProduct.getProductNames());
		}


		return hike;

	}

	/**
	This method finds minimum price amoung the items which contains input name.
	@input: String productname
	@return minimun price found;0 otherwise.

	*/

	static BigDecimal FindMinPrice(String productname){
		Long prdname=Long.parseLong(productname);
		BigDecimal returnVal=new BigDecimal(0);
		if(productListOnNames.containsKey(prdname)){
			TreeMap<Long,BigDecimal> existingSet=productListOnNames.get(prdname);

			returnVal=Collections.min(existingSet.values());

		}

		return returnVal;

	}

	/**
	This method finds maximum price amoung the items which contains input name.
	@input: String productname
	@return maximum price found;0 otherwise.

	*/


	static BigDecimal FindMaxPrice(String productname){
		Long prdname=Long.parseLong(productname);
		BigDecimal returnVal=new BigDecimal(0);
		if(productListOnNames.containsKey(prdname)){
			TreeMap<Long,BigDecimal> existingSet=productListOnNames.get(prdname);

			returnVal=Collections.max(existingSet.values());

		}

		return returnVal;

	}

	/**
	This method finds all the items which has input name and whose price ranges between given input values.
	@input: String productname, String low, String hign
	@return total number of items found; 0 otherwise.

	*/


	static Long FindPriceRange(String productname,String low, String high){
		Long prdname=Long.parseLong(productname);
		Long returnVal=new Long(0);
		BigDecimal lowRangePrice=new BigDecimal(low);
		BigDecimal highRangePrice=new BigDecimal(high);

		if(productListOnNames.containsKey(prdname)){
			TreeMap<Long,BigDecimal> existingSet=productListOnNames.get(prdname);

			Set<Long> keys = existingSet.keySet();

			BigDecimal currVal=new BigDecimal(0);

			for(Long key: keys){
				
				currVal=existingSet.get(key);

				if(((currVal.compareTo(lowRangePrice))>=0) && (((currVal.compareTo(highRangePrice))<=0))) {
					returnVal++;
				}
			}
		}

		return returnVal;

	}


	/*
	 This is a helper method which removes names from one of the Hashmap which stores data based on product names.

	*/

	private static Long removeNames(ArrayList<Long> productnames, Long productId){

			Long returnVal=new Long(0);

			for(int i=0;i<productnames.size();i++){

				Long name=(Long)productnames.get(i);

				returnVal+=name;

				if((productListOnNames.isEmpty())||(!productListOnNames.containsKey(name))) {
					//returnVal=0;
				}else{

						TreeMap<Long,BigDecimal> existingSet=productListOnNames.get(name);
						existingSet.remove(productId);
						if(existingSet.isEmpty()){
							productListOnNames.remove(name);
						}else{
							productListOnNames.put(name,existingSet);
						}
				}

			}

		return returnVal;

	}

	/*
	 This is a helper method which Inserts names in productlistBasedonNames Hashmap
	 The values are productID and productPrice which is stored in a HashMap

	*/
	private static void InsertNames(ArrayList<Long> productnames,dxm116130Product newProductItem){

			for(int i=0;i<productnames.size();i++){

				Long name=(Long)productnames.get(i);
				if((productListOnNames.isEmpty())||(!productListOnNames.containsKey(name))) {
					TreeMap<Long,BigDecimal> newSet=new TreeMap<Long,BigDecimal>();
					newSet.put(newProductItem.getProductId(),newProductItem.getProductPrice());
					productListOnNames.put(name,newSet);
				}else{
					if(productListOnNames.containsKey(name)){
						TreeMap<Long,BigDecimal> existingSet=productListOnNames.get(name);
						existingSet.put(newProductItem.getProductId(),newProductItem.getProductPrice());
						productListOnNames.put(name,existingSet);
					}
				}
				

			}

	}

}