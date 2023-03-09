import java.util.HashMap;
import java.util.Map;

public class ShoppingBag<T extends PricedItem<Integer>> {
  private Map<T, Integer> shoppingBag;

  public ShoppingBag() {
    this.shoppingBag = new HashMap<>();
  }

  public void addItem(T item){

    if(shoppingBag.containsKey(item)){
      shoppingBag.put(item, shoppingBag.get(item)+1);
    }else{
      shoppingBag.put(item, 1);
    }

  }

  public Integer getTotalPrice(){
    Integer totalPrice = 0;
    for(T item:shoppingBag.keySet()){
      totalPrice = totalPrice + item.getPrice() * shoppingBag.get(item);
    }

    return totalPrice;
  }
}
