import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

    System.out.println("Ingrese su nombre: ");
    String customerName = input.next();

    System.out.println("Ingrese la cantidad de dinero con la que cuenta: ");
    int money = validatedIntInput(input);


    System.out.println(money);
    Customer customer = new Customer(customerName, money);


    FoodMenu myMenu = new FoodMenu();

    TakeOutSimulator takeOutSimulator = new TakeOutSimulator(customer, myMenu, input);

    takeOutSimulator.startTakeOutSimulator();

    input.close();






  }

  private static <T> T getOutputOnIntInput(Scanner input , IntUserInputRetriever<T> intUserInputRetriever) {
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


  private static int validatedIntInput(Scanner input){

    IntUserInputRetriever<Integer> intUserInputRetreiver = (userInput) -> {
      if(userInput > 0){
        return userInput;
      }else {
        throw new IllegalArgumentException();
      }
    };

    return getOutputOnIntInput(input,intUserInputRetreiver);
  }
}
