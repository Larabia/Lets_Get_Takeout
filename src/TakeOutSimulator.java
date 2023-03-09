import java.util.Scanner;

public class TakeOutSimulator {

  Customer customer;
  FoodMenu menu;
  Scanner input;

  public TakeOutSimulator(Customer customer, FoodMenu menu, Scanner input){
    this.customer = customer;
    this.menu = menu;
    this.input = input;
  }

  //takes an input, validate that is an int and passes it to an IntUserInputRetriever implementation
  private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever) {
    System.out.println(userInputPrompt);
    while (true) {
      if (input.hasNextInt()) {
        try {
          int userInput = input.nextInt();
          return intUserInputRetriever.produceOutputOnIntUserInput(userInput);
        } catch (NumberFormatException e) {
          System.out.println("Input needs to be an integer. Please try again.");
          input.nextInt();
        } catch (IllegalArgumentException e) {
          System.out.println(e.getMessage() + " Please try again.");
          input.nextInt();
        }
      }
    }
  }

  //creates an IntUserInputRetriever implementation to evaluate if the customer can afford the selection
  //call getOutputOnIntInput and pass this specific implementation
  public boolean shouldSimulate(){
    String userPrompt = "Enter 1 to proceed with simulation, or 0 to stop: ";
    IntUserInputRetriever<Boolean> intUserInputRetreiver = (selection) -> {
      if (selection == 1 && customer.getMoney() >= menu.getLowestCostFood().getPrice()) {
        System.out.println("Your choice was 1 and you've got some cash, nice, let us continue...");
        return true;
      }
      else if (customer.getMoney() < menu.getLowestCostFood().getPrice()) {
        System.out.println(selection);
        System.out.println("Sorry, you do not have enough money to continue shopping (only $" + customer.getMoney() + "), so go and find a better job. End of simulation..." );
        return false;
      }
      else if (selection == 0 ) {
        System.out.println(selection);
        System.out.println("As you wish, simulation ending...");
        return false;
      }
      else {
        throw new IllegalArgumentException();
      }
    };
    return this.getOutputOnIntInput(userPrompt, intUserInputRetreiver);
  }

  public Food getMenuSelection() {
    String userPrompt = "Today's Menu Options! \n" + this.menu.toString();

    IntUserInputRetriever<Food> intUserInputRetreiver = (selection) -> {
      if(menu.getFood(selection) == null){
        throw new IllegalArgumentException();
      }else{
        return menu.getFood(selection);

      }

    };

    return this.getOutputOnIntInput(userPrompt, intUserInputRetreiver);
  }

  public boolean isStillOrderingFood(){
    String userPrompt = "Enter 1 to CONTINUE shopping or 0 to CHECKOUT:";

    IntUserInputRetriever<Boolean> intUserInputRetreiver = (selection) -> {
      if(selection==1){
        return true;
      }else if(selection==0){
        return false;
      }else{
        throw new IllegalArgumentException();
      }

    };

    return this.getOutputOnIntInput(userPrompt, intUserInputRetreiver);
  }

  public void checkoutCustomer(ShoppingBag  shoppingBag ){
    System.out.println("Processing payment...");
    int remainingMoney = customer.getMoney() - shoppingBag.getTotalPrice();
    customer.setMoney(remainingMoney);
    System.out.println("Your remaining money: $" + customer.getMoney());
    System.out.println("Thank you and enjoy your food!");
  }

  public void takeOutPrompt(){
    ShoppingBag<Food> shoppingBag = new ShoppingBag<>();
    int customerMoneyLeft = customer.getMoney();
    boolean answer = false;

    System.out.println("Your remaining money: $" + customerMoneyLeft);
    Food selected = getMenuSelection();

    if (customerMoneyLeft < selected.getPrice()) {
      System.out.println("Oops! Looks like you don't have enough for that. Choose another item or checkout." );
    } else {
      customerMoneyLeft = customerMoneyLeft - selected.getPrice();
      shoppingBag.addItem(selected);
      answer = isStillOrderingFood();
      while (answer) {
        selected = getMenuSelection();
        if (customerMoneyLeft < selected.getPrice()) {
          System.out.println("Oops! Looks like you don't have enough for that. Choose another item or checkout.");
          answer = isStillOrderingFood();
        } else {
          customerMoneyLeft = customerMoneyLeft - selected.getPrice();
          shoppingBag.addItem(selected);
          answer = isStillOrderingFood();
        }

      }
      checkoutCustomer(shoppingBag);
    }
  }


  public void startTakeOutSimulator(){

    boolean answer = false;
    System.out.printf("Hello, welcome to my restaurant!" + "\nWelcome %s!", customer.getName());
    while (answer) {
      answer = shouldSimulate();
    }

    takeOutPrompt();

  }
}
