//import java.sql.*;
import java.util.Scanner;
//import db.ConnectionFactory;

public class Main {
    public static void main(String[] args){

        Scanner scanner = new Scanner (System.in);
        AppFunctions functions = new AppFunctions(scanner);


        //Select user menu
        while(true) {
            System.out.println("\n=== Gym Management System ===");
            System.out.println("1. Memeber");
            System.out.println("2. Trainer");
            System.out.println("3. Admin");
            System.out.println("0. Exit");
            System.out.print("Choose a role: ");
            String choice = scanner.nextLine().trim(); //store choice

            switch (choice) {
                case "1":
                    runMemberFlow(functions, scanner);
                    break;
                case "2":
                    runTrainerFlow(functions, scanner);
                    break;
                case "3":
                    runAdminFlow(functions, scanner);
                    break;
                case "0":
                    System.out.println("Exit...Goodbye!");
                    scanner.close();
                    return;
                default: 
                    System.out.println("Invalid choice input");
            };

        }
    }

    private static void runMemberFlow(AppFunctions functions, Scanner scanner){

        //menu
        while(true){
            System.out.println("\n === Member Menu ===");
            System.out.println("1. Register (create memeber account)");
            System.out.println("2. Login");
            System.out.println("0. Back");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            //
            if(choice.equals("1")){ //Option 1 selection
                functions.registerMember(); //run create account function
            }else if (choice.equals("2")) { //if login
                Integer memberId = functions.loginMember(); //login in function
                if(memberId != null){ //if login does not fail
                    runLoggedInMemberMenu(functions, scanner, memberId); //show login menu
                }
            }else if (choice.equals("0")){ //if back
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    private static void runLoggedInMemberMenu(AppFunctions functions, Scanner scanner, int memberId) {

        //Logged in user menu
        while(true){
            System.out.println("\n=== Member Actions (Logged in as member_id=" + memberId + ") ===");
            System.out.println("1. Update Profile"); 
            System.out.println("2. Add Health Metric"); 
            System.out.println("3. Register for class"); 
            System.out.println("0. Logout"); 
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    functions.updateMemberProfile(memberId);
                    break; //return to menu
                case "2":
                    functions.addHealthMetric(memberId);
                    break;
                case "3":
                    functions.registerForClass(memberId);
                case "0":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid choice");
            }

        }

    }

    private static void runTrainerFlow(AppFunctions functions, Scanner scanner) {

        //Get trainer login info
        Integer trainerId = functions.loginTrainer(); 
        if(trainerId == null){ //if invalid trainer id
            return;
        }
        //Trainer login
        while(true){
            System.out.println("\n=== Trainer Actions (Logged in as trainer_id=" + trainerId + ") ===");
            System.out.println("1. Set Availability"); 
            System.out.println("2. View Schedule");  
            System.out.println("0. Logout"); 
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    functions.setTrainerAvailability(trainerId);
                    break; //return to menu
                case "2":
                    functions.viewTrainerSchedule(trainerId);
                    break;
                case "0":
                    System.out.println("Logged out.");
                    return;
                default: //invalid
                    System.out.println("Invalid choice");
            } 
        }
    }

    private static void runAdminFlow(AppFunctions functions, Scanner scanner) {

        //Get admin login
        Integer adminId = functions.loginAdmin();
        if(adminId == null){
            return;
        }

        //Admin login
        while(true){
            System.out.println("\n=== Admin Actions (Logged in as admin_id=" + adminId + ") ===");
            System.out.println("1. Add New Class Type"); 
            System.out.println("2. Assign Room to Session");  
            System.out.println("3. Assign Class To Trainer"); 
            System.out.println("0. Logout"); 
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    functions.addNewClassType();
                    break; //return to menu
                case "2":
                    functions.assignRoomToSession();
                    break;
                case "3":
                    functions.assignClassToTrainer();
                    break;
                case "0":
                    System.out.println("Logged out.");
                    return;
                default: //invalid
                    System.out.println("Invalid choice");
            } 
        }





    }

    
}