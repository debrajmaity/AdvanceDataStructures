import java.util.Comparator;


class dxm116130ProductPrice implements Comparable<dxm116130ProductPrice> {
	
	private Long dollars;
	private int cents;
	
	
	dxm116130ProductPrice(String productPrice){
		
		String[] prices=productPrice.split("\\.");

		this.dollars=Long.parseLong(prices[0]);
		this.cents=Integer.parseInt(prices[1]);
		
	}
	
	public Long getDollars(){
		return this.dollars;
	}
	
	public int getCents(){
		return this.cents;
	}

	public void setDollars(Long dollars){
		this.dollars=dollars;
	}

	public void setCents(int cents){
		this.cents=cents;
	}

	public void setPrice(String price){

		String[] prices=price.split("\\.");

		this.dollars=Long.parseLong(prices[0]);
		this.cents=Integer.parseInt(prices[1]);
	}

	@Override
	public int compareTo(dxm116130ProductPrice arg0) {
		
		int val=0;
		
		if((this.dollars)==(arg0.getDollars())) {

			if(this.cents==arg0.getCents()){
				val=0;
			}else if(this.cents>arg0.getCents()){
				val=1;
			}else if(this.cents<arg0.getCents()){
				val=-1;
			}
				
			
		}else{
			if(this.dollars>arg0.getDollars()){
				 val=1;
			}
			else
				val=-1;
			
		}
		
		return val;
		
		
	}
	
	
	
	

}
