import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class AppFunctions {

    private Scanner scanner;

    // Constructor to initialize scanner
    public AppFunctions(Scanner scanner) {
        this.scanner = scanner;
    }

    //Log in functions

    public Integer loginMember(){

        //Get id
        System.out.println("\n=== Member Login ===");
        System.out.print("Enter member_id: ");
        String idText = scanner.nextLine().trim();

        //Store id
        int memberId;
        try {
            memberId = Integer.parseInt(idText);
        } catch (NumberFormatException e) {
            System.out.println("Invalid member_id");
            return null;
        }

        //Get email
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        String sql = "SELECT 1 FROM Member WHERE member_id = ? AND email = ?";
        
        //Open connection
        try(Connection connect = DBConnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)) {
                
                //Bind credentials
                statement.setInt(1, memberId);
                statement.setString(2, email);

                //Run query
                ResultSet set = statement.executeQuery(); 
                
                //Go through rows
                if(set.next()) {
                    System.out.println("Login successful");
                    return memberId;
                } else {
                    System.out.println("Login failed: ID/email not found");
                    return null;
                }
            } catch (SQLException exception) {
                //Error message
                System.out.println("Error during login");
                return null;
            }
    }
    
    //login trainer
    public Integer loginTrainer(){

        //Get user id
        System.out.println("\n=== Trainer Login ===");
        System.out.print("Enter trainer_id: ");
        String idText = scanner.nextLine().trim();

        //Store id and turn to int
        int trainerId;
        try{
            trainerId = Integer.parseInt(idText);
        } catch(NumberFormatException e) {
            System.out.println("Invalid trainer_id.");
            return null;
        }

        //Get email
        System.out.println("Enter email: ");
        String email = scanner.nextLine().trim();

        String sql = "SELECT 1 FROM Trainer WHERE trainer_id = ? AND email = ?";


        //open conncetion
        try(Connection connect = DBConnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)) {

                //Bind credentials
                statement.setInt(1, trainerId);
                statement.setString(2, email);

                ResultSet set = statement.executeQuery(); //Run query
                if(set.next()){
                    System.out.println("Login successful.");
                    return trainerId;
                } else {
                    System.out.println("Login failed: ID/email not found");
                    return null;
                }
            } catch (SQLException exception){
                System.out.println("Error during login ");
                return null;
            }
    }

    public Integer loginAdmin() {

        System.out.println("\n=== Admin Login ===");
        System.out.print("Enter admin id: ");
        String idText = scanner.nextLine().trim();

        int adminId;
        try{
            adminId = Integer.parseInt(idText);
        } catch (NumberFormatException e){
            System.out.println("Invalid admin id");
            return null;
        }

        System.out.println("Enter email: ");
        String email = scanner.nextLine().trim();

        String sql = "SELECT 1 FROM AdminStaff WHERE admin_id = ? AND email = ?";

        try(Connection connect = DBConnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)) {


                //Bind credentials
                statement.setInt(1, adminId);
                statement.setString(2, email);

                ResultSet set = statement.executeQuery(); //Run query
                if(set.next()){
                    System.out.println("Login successful.");
                    return adminId;
                } else {
                    System.out.println("Login failed: ID/email not found");
                    return null;
                }
        }catch (SQLException exception) {
                System.out.println("Error during login");
                return null;
        }

    }

    //User registration function
    public void registerMember(){
        System.out.println("\n === Member Registration === \n");

        //Get name from user
        System.out.print("Enter Full Name: ");
        String name  = scanner.nextLine();

        //Set email
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        //Set DOB
        System.out.print("Enter date of birth (YYY-MM-DD or Leave blank): ");
        String dob = scanner.nextLine();

        //Set gender
        System.out.print("Enter gender: ");
        String gender = scanner.nextLine();

        //Set phone number
        System.out.print("Enter phone number (xxx-xxx-xxxx): ");
        String phone = scanner.nextLine();

        //Set target weight(lbs)
        System.out.print("Enter new target weight(lbs) (1 decimals): ");
        Double targetWeight = Double.parseDouble(scanner.nextLine());

        //Set target body fat %
        System.out.print("Enter new body fat % (1 decimals): ");
        Double targetbodyfat = Double.parseDouble(scanner.nextLine());

        //fill in blank sql
        String sql = "INSERT INTO Member (name, email, date_of_birth, gender, phone, target_weight, target_body_fat) " + "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING member_id, email";

        
        try(Connection connect = DBConnection.getConnection();
            PreparedStatement statement = connect.prepareStatement(sql)) {

                //Fill sql string
                statement.setString(1, name);
                statement.setString(2, email);

                //if user left DOB blank, set it to null
                if (dob == null || dob.isEmpty()){
                    statement.setNull(3, java.sql.Types.DATE);
                } else {
                    statement.setDate(3, java.sql.Date.valueOf(dob));
                }

                statement.setString(4, gender);
                statement.setString(5, phone);
                statement.setDouble(6, targetWeight);
                statement.setDouble(7, targetbodyfat);

                //NEW TRIGGER STUFF
                ResultSet set = statement.executeQuery();

                if(set.next()){
                    int newId = set.getInt("member_id");
                    String storedEmail = set.getString("email");

                    System.out.println("Member successfully registered!");
                    System.out.println("New Member ID: " + newId);
                    System.out.println("Stored email (normalized): " + storedEmail);
                }else{
                    System.out.println("Registration failed");
                }

            } catch (SQLException exception) {
                System.out.println("Error adding member");
                //exception.printStackTrace();
            }
    }

    //Profile Management
    public void updateMemberProfile(int memberId){
        System.out.println("\n === Update Member Profile === \n");
        System.out.println("Updating profile for member_id: " + memberId);

        //Update email
        System.out.print("Enter new email: ");
        String newEmail = scanner.nextLine();

        //New phone number
        System.out.print("Enter new phone number (xxx-xxx-xxxx): ");
        String newPhoneNumber = scanner.nextLine();

        //Update target weight(lbs)
        System.out.print("Enter new target weight(lbs) (1 decimals): ");
        Double targetWeight = Double.parseDouble(scanner.nextLine());

        //Update target body fat %
        System.out.print("Enter new body fat % (1 decimals): ");
        Double targetbodyfat = Double.parseDouble(scanner.nextLine());

        //Fill in sql string
        String sql = "UPDATE Member SET email = ?, phone = ?, target_weight = ?, target_body_fat = ? WHERE member_id = ?";


        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Fill sql string (?)  
                statement.setString(1, newEmail.toLowerCase());     
                statement.setString(2, newPhoneNumber);
                statement.setDouble(3, targetWeight);
                statement.setDouble(4, targetbodyfat);
                statement.setInt(5, memberId);

                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Member profile updated!");
                } else {
                    System.out.println("No member found with that ID.");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error updating member profile");
                //exception.printStackTrace();
        }
    }


    //Add Health Metric
    public void addHealthMetric(int memberId) {
        System.out.println("\n === Add Health Metric === \n");
        System.out.println("Adding metric for member_id: " + memberId);


        //Get member id
        System.out.print("Enter Weight(lbs) (2 deciamls): ");
        Double weight = Double.parseDouble(scanner.nextLine());

        //Get weight in kg
        System.out.print("Enter Heart Rate: ");
        int heartRate = Integer.parseInt(scanner.nextLine());

        //Get bodyfat
        System.out.print("Body fat %(2 decimal): ");
        double bodyFat = Double.parseDouble(scanner.nextLine());

        //Get Notes
        System.out.print("Notes (Optional): ");
        String notes = scanner.nextLine();

        //Fill in sql string
        String sql = "INSERT INTO HealthMetric " + "(member_id, weight, heart_rate, body_fat_percentage, notes) " + "VALUES (?, ?, ?, ?, ?)";

        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Fill sql string (?)       
                statement.setInt(1, memberId);
                statement.setDouble(2, weight);
                statement.setInt(3, heartRate);
                statement.setDouble(4, bodyFat);

                //If notes is null
                if(notes == null || notes.isEmpty()){
                    statement.setNull(5, java.sql.Types.VARCHAR);
                } else {
                    statement.setString(5, notes);
                }


                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Health metric added!");
                } else {
                    System.out.println("Unable to add health metric.");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error adding health metric");
                //exception.printStackTrace();
        }

    }

    public void registerForClass(int memberId) {

        System.out.println("\n === Class Registration === \n");
        System.out.println("Registering member_id: " + memberId);


        //Get session 
        System.out.print("Enter session_id: ");
        int sessionId = Integer.parseInt(scanner.nextLine());

        //Get capacity sql
        String capacitySql = 
        "SELECT " +
        " cs.capacity AS class_capacity, " + //class max
        " r.capacity AS room_capacity, " + //room max
        " COUNT(cr.registration_id) AS current_registration " + //how many are registrered
        "FROM ClassSession cs " +
        "LEFT JOIN Room r ON cs.room_id = r.room_id " + //get room info
        "LEFT JOIN ClassRegistration cr ON cr.session_id = cs.session_id " + //get registration info
        "WHERE cs.session_id = ? " + 
        "GROUP BY cs.capacity, r.capacity";


        //Fill in sql String
        String sql = "INSERT INTO ClassRegistration (member_id, session_id) VALUES (?, ?)";

        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement capacityStatement = connect.prepareStatement(capacitySql); 
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Check capcacity restraints
                capacityStatement.setInt(1, sessionId);
                ResultSet set = capacityStatement.executeQuery();

                //error check session id
                if(!set.next()){
                    System.out.println("No session found");
                    return;
                }

                int class_capacity = set.getInt("class_capacity");
                int room_capacity = set.getInt("room_capacity");
                int current_registration = set.getInt("current_registration");

                //check if room is assigned to determine used max
                int max;
                if(room_capacity > 0){
                    max = Math.min(class_capacity, room_capacity);
                }else{
                    max = class_capacity;
                }

                if(current_registration >= max){
                    System.out.println("class/room Capacity reached");
                    return;
                }
    
                //Fill sql string (?)       
                statement.setInt(1, memberId);
                statement.setInt(2, sessionId);

                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Class registration successful!");
                } else {
                    System.out.println("Class registration failed");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error registering for a class");
                //exception.printStackTrace();
        }

    }

    //Trainer
    public void setTrainerAvailability(int trainerId) {

        System.out.println("\n === Set Trainer Availability === \n");
        System.out.println("Setting new trainer Availability: " + trainerId);

        

        //Get Day of the week
        System.out.print("Day of week (1=Mon, 2=Tues, 3=Wed, 4=Thurs, 5=Fri, 6=Sat, 7=Sun): ");
        int dow = Integer.parseInt(scanner.nextLine());

        //Get start time
        System.out.print("Start time (HH:MM): ");
        String startTime = scanner.nextLine();

        //End Time
        System.out.print("End Time (HH:MM): ");
        String endTime = scanner.nextLine();


        //Fill in sql string
        String sql = "INSERT INTO TrainerAvailability (trainer_id, day_of_week, start_time, end_time) VALUES (?, ?, ?, ?)";

        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Fill sql string (?)       
                statement.setInt(1, trainerId);
                statement.setInt(2, dow);
                statement.setTime(3, java.sql.Time.valueOf(startTime + ":00"));
                statement.setTime(4, java.sql.Time.valueOf(endTime + ":00"));


                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Availability set");
                } else {
                    System.out.println("Failed to set availability");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error setting trainer's availability");
                //exception.printStackTrace();
        }

    }

    //TRAINER: View trainer schedule
    public void viewTrainerSchedule(int trainerId){
        System.out.println("\n === View Trainer Schedule === \n");
        System.out.println("View Trainer Schedule For: " + trainerId);

        //Fill sql String
        //String sql = "SELECT session_id, start_time, end_time FROM ClassSession WHERE trainer_id = ? ORDER BY start_time";
        String sql = "SELECT session_id, start_time, end_time FROM TrainerScheduleView WHERE trainer_id = ? ORDER BY start_time";
        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Fill sql string (?)       
                statement.setInt(1, trainerId);
                ResultSet set = statement.executeQuery();


                System.out.println("Session for trainer " + trainerId + ":");
                //Read read through each row to get info
                while (set.next()) {
                    int sessionID = set.getInt("session_id");
                    java.sql.Time start = set.getTime("start_time");
                    java.sql.Time end = set.getTime("end_time");

                    System.out.println("Session " + sessionID + " | " + start + " - " + end);

                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error viewing trainer schedule");
                exception.printStackTrace();
        } 
    }

    //ADMIN: add new class
    public void addNewClassType() {

        System.out.println("\n --Add New Class Type ===\n");

        //Class names
        System.out.print("Class Name: ");
        String name = scanner.nextLine();

        //get Description
        System.out.print("Description: ");
        String description = scanner.nextLine();

        //get capacity
        System.out.print("Default Capacity: ");
        int defaultCapacity = Integer.parseInt(scanner.nextLine());

        //Fill in sql
        String sql = "INSERT INTO ClassType (name, description, default_capacity) VALUES (?, ?, ?)";

        //Open connection
        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Fill sql string (?)       
                statement.setString(1, name);
                statement.setString(2, description);
                statement.setInt(3, defaultCapacity);


                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("New class type added");
                } else {
                    System.out.println("new class type not added");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error adding class type");
                //exception.printStackTrace();
        }
    }

    //ADMIN: Assign Room to ClassSession
    public void assignRoomToSession(){
        System.out.println("\n === Assign Room to Session === \n");

        //Get session ID
        System.out.print("Enter session_id: ");
        int sessionId = Integer.parseInt(scanner.nextLine());

        //Get room_id
        System.out.print("Enter room_id: ");
        int roomId = Integer.parseInt(scanner.nextLine());

        //SQL to get room capcacity
        String roomCapacitySql = "SELECT capacity FROM Room WHERE room_id = ?";

        //SQL to count members of a class
        String registrationCountSql = 
        "SELECT COUNT(*) AS current_registration " +
        "FROM ClassRegistration " +
        "WHERE session_id = ?";                              

        //Fill in sql string
        String sql = "UPDATE ClassSession SET room_id = ? WHERE session_id = ?";

        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement roomStatement = connect.prepareStatement(roomCapacitySql);
            PreparedStatement regcountStatement = connect.prepareStatement(registrationCountSql);
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Get room capacity
                roomStatement.setInt(1, roomId);
                ResultSet roomSet = roomStatement.executeQuery();

                if(!roomSet.next()){
                    System.out.println("Room id not found");
                    return;
                }

                int roomCapacity = roomSet.getInt("capacity");

                //get num of registered members
                regcountStatement.setInt(1, sessionId);
                ResultSet regcountSet = regcountStatement.executeQuery();
                
                int currentRegistration = 0;
                if(regcountSet.next()){
                    currentRegistration = regcountSet.getInt("current_registration");
                }

                //Check if room can fit class size
                if(currentRegistration > roomCapacity){
                    System.out.println("Room Not Assigned: Class registration count exceeds room capacity");
                    return;
                }
                
                
                //Update room

                //Fill sql string (?)       
                statement.setInt(1, roomId);
                statement.setInt(2, sessionId);

                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Room assigned successfully");
                } else {
                    System.out.println("No session found with that ID");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error assigning room");
                //exception.printStackTrace();
        }

    }

    //TEST
    public void assignClassToTrainer(){
        System.out.println("\n === Assign Class Type To Trainer === \n");

        //Get class Id
        System.out.print("Enter class_type_id: ");
        int classTypeId = Integer.parseInt(scanner.nextLine());

        //Get trainer id
        System.out.print("Enter trainer_id: ");
        int trainerId = Integer.parseInt(scanner.nextLine());

        //Get room id
        System.out.print("Enter room_id: ");
        int roomId = Integer.parseInt(scanner.nextLine());

        //Get Day of week
        System.out.print("Enter Day of week: ");
        int DOW = Integer.parseInt(scanner.nextLine());

        //Get start time
        System.out.print("Enter start time (HH:MM): ");
        String startTime = scanner.nextLine();

        //Get end time
        System.out.print("Enter end time (HH:MM): ");
        String endTime = scanner.nextLine();

        //SQL status
        String classStatusSql = "SELECT status, default_capacity FROM ClassType WHERE class_type_id = ?";

        //insert
        String sql = "INSERT INTO ClassSession " + 
        "(class_type_id, trainer_id, room_id, day_of_week, start_time, end_time, capacity) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        //Update classtype status sql
        String updateStatusSql = "UPDATE ClassType SET status = ? WHERE class_type_id = ?";

        //Check trainer availabality
        String availSql = "SELECT availability_id FROM TrainerAvailability " + 
        "WHERE trainer_id = ? AND day_of_week = ? AND status = 'unoccupied' " +
        "AND start_time <= ? AND end_time >= ?";

        //Update classtype status sql
        String updateavailabalitySql = "UPDATE TrainerAvailability SET status = 'occupied' WHERE availability_id = ?";

        try(Connection connect = DBConnection.getConnection();
            //prepare SQL statement
            PreparedStatement classStatus = connect.prepareStatement(classStatusSql); //Check status
            PreparedStatement newclassStatus = connect.prepareStatement(updateStatusSql); //Update staus
            PreparedStatement availabilatyStatement = connect.prepareStatement(availSql); //check availabilty
            PreparedStatement newavailabilatyStatement = connect.prepareStatement(updateavailabalitySql); //update availabilty
            PreparedStatement statement = connect.prepareStatement(sql)){
                //Check if room is unschduled
                classStatus.setInt(1, classTypeId);
                ResultSet set = classStatus.executeQuery();

                if(!set.next()){
                    System.out.println("Class id not found");
                    return;
                }

                String currentStatus = set.getString("status");
                int defaultCapacity = set.getInt("default_capacity");


                if(!"unscheduled".equalsIgnoreCase(currentStatus)){
                    System.out.println("Class is already scheduled");
                    return;
                }
               

                //Check availability
                availabilatyStatement.setInt(1, trainerId);
                availabilatyStatement.setInt(2,DOW);
                availabilatyStatement.setTime(3, java.sql.Time.valueOf(startTime + ":00"));
                availabilatyStatement.setTime(4, java.sql.Time.valueOf(endTime + ":00"));

                ResultSet set2 = availabilatyStatement.executeQuery();

                if(!set2.next()){
                    System.out.println("Trainer is not available at this time.");
                    return;
                }

                int availabilatyId = set2.getInt("availability_id");

                //Update class session and class status and trainer availability
                newclassStatus.setInt(2, classTypeId);
                newclassStatus.setString(1,"scheduled");
                newclassStatus.executeUpdate();

                //update trainer availability status
                newavailabilatyStatement.setInt(1, availabilatyId);
                newavailabilatyStatement.executeUpdate();

                statement.setInt(1, classTypeId);
                statement.setInt(2, trainerId);
                statement.setInt(3, roomId);
                statement.setInt(4, DOW);
                statement.setTime(5, java.sql.Time.valueOf(startTime + ":00"));
                statement.setTime(6, java.sql.Time.valueOf(endTime + ":00"));
                statement.setInt(7, defaultCapacity);



                //INSERT
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Trainer assigned class successfully");
                } else {
                    System.out.println("unable to assign Trainer to class");
                }

            } catch (SQLException exception) { //if error occurs
                System.out.println("Error assigning class");
                exception.printStackTrace();
        }
    }


}