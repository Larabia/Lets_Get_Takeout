import java.util.ArrayList;
import java.util.List;

public class FoodMenu {

  private List<Food> menu;

  public FoodMenu() {

    this.menu = new ArrayList<>();

    Food empanada = new Food("empanada", "masa rellena de carne", 200);
    Food milanesa = new Food("milanesa" , "carne de lomo empanizada", 1000);
    Food papas = new Food("papas" , "cubos de papa al horno", 500);

    this.menu.add(empanada);
    this.menu.add(milanesa);
    this.menu.add(papas);
  }

  @Override
  public String toString(){
    StringBuilder foodDesc = new StringBuilder();

    for (int i = 0; i < menu.size(); i++) {
      foodDesc.append(i+1+ ". " + menu.get(i).toString()+"\n");

    }

    return foodDesc.toString();
  }

  public Food getFood(int index){

     try {
       return menu.get(index - 1);
     }catch(IndexOutOfBoundsException e){
       return null;
     }

  }

  public Food getLowestCostFood(){
     int lowerPrice = 0;
     int index = 0;

     for(int i = 0; i < menu.size(); i++){
       if(menu.get(i).getPrice()< lowerPrice){
         lowerPrice = menu.get(i).getPrice();
         index = i;
       }
     }
    return menu.get(index);
  }
}
