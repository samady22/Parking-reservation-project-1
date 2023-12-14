package sk.stuba.fei.uim.vsa.pr1c.ui;

import sk.stuba.fei.uim.vsa.pr1c.entities.*;
import sk.stuba.fei.uim.vsa.pr1c.service.CarParkService;

import java.util.*;

public class Repl {
    private final CarParkService cps = new CarParkService();
    private final Map<String, Runnable> mapMethod = new HashMap<>();

    public static final String HELP = "VSA Project '1' Group 'C' 2022 \n" +
            "This Application works on SQL databases supported by JPA.\n" +
            "For testing the application, follow the following instructions:\n" +
            "   1               - Create CarPark \n" +
            "   1.1             - Get CarParkById \n" +
            "   1.2             - Get CarParks \n" +
            "   1.3             - Delete CarPark \n" +
            "   1.4             - Update CarPark \n" +
            "   2               - Create CarParkFloor \n" +
            "   2.1             - Get CarParkFloor \n" +
            "   2.2             - Delete CarParkFloors \n" +
            "   2.3             - Delete CarParkFloor \n" +
            "   3               - Create ParkingSpot \n" +
            "   3.1             - Get ParkingSpot \n" +
            "   3.2             - Get ParkingSpots \n" +
            "   3.3             - Update ParkingSpot \n" +
            "   3.4             - Delete ParkingSpot \n" +
            "   3.5             - Get Available ParkingSpots \n" +
            "   3.6             - Get Occupied ParkingSpots \n" +
            "   4               - Create User \n" +
            "   4.1             - Get User \n" +
            "   4.2             - Get Users \n" +
            "   4.3             - Update User \n" +
            "   4.4             - Delete User \n" +
            "   5               - Create Car \n" +
            "   5.1             - Get CarById \n" +
            "   5.2             - Get Cars \n" +
            "   5.3             - Update Car \n" +
            "   5.4             - Delete Car \n" +
            "   6               - Create Reservation \n" +
            "   6.1             - End Reservation \n" +
            "   6.2             - Get Reservations \n" +
            "   6.3             - Update Reservation \n" +
            "   7               - Create Holiday \n" +
            "   7.1             - Get Holiday \n" +
            "   7.2             - Delete Holiday \n" +
            "   help, ?         - Show you the instructions \n" +
            "   exit, quit, q   - End the program \n";

    public Repl() {
        mapMethod.put("?", this::Help);
        mapMethod.put("help", this::Help);
        mapMethod.put("1", this::createCarPark);
        mapMethod.put("1.1", this::getCarParkById);
        mapMethod.put("1.2", this::getCarParks);
        mapMethod.put("1.3", this::deleteCarPark);
        mapMethod.put("1.4", this::updateCarPark);
        mapMethod.put("2", this::createCarParkFloor);
        mapMethod.put("2.1", this::getCarParkFloor);
        mapMethod.put("2.2", this::getCarParkFloors);
        mapMethod.put("2.3", this::deleteCarParkFloor);
        mapMethod.put("3", this::createParkingSpot);
        mapMethod.put("3.1", this::getParkingSpot);
        mapMethod.put("3.2", this::getParkingSpots);
        mapMethod.put("3.3", this::updateParkingSpot);
        mapMethod.put("3.4", this::deleteParkingSpot);
        mapMethod.put("3.5", this::getAvailableParkingSpot);
        mapMethod.put("3.6", this::getOccupiedParkingSpot);
        mapMethod.put("4", this::creatUser);
        mapMethod.put("4.1", this::getUser);
        mapMethod.put("4.2", this::getUsers);
        mapMethod.put("4.3", this::updateUser);
        mapMethod.put("4.4", this::deleteUser);
        mapMethod.put("5", this::createCar);
        mapMethod.put("5.1", this::getCarById);
        mapMethod.put("5.2", this::getCars);
        mapMethod.put("5.3", this::updateCar);
        mapMethod.put("5.4", this::deleteCar);
        mapMethod.put("6", this::createReservation);
        mapMethod.put("6.1", this::endReservation);
        mapMethod.put("6.2", this::getReservations);
        mapMethod.put("6.3", this::updateReservation);
        mapMethod.put("7", this::createHoliday);
        mapMethod.put("7.1", this::getHolidays);
        mapMethod.put("7.2", this::deleteHoliday);


    }


    public void start() {
        System.out.println("*** Enter help or ? for getting the instructions ***");
        while (true) {
            String input = KeyboardInput.readString("").trim();
            switch (input) {
                case "q":
                case "exit":
                case "quit":
                    return;
            }
            if (isCommand(input)) {
                executeCommand(input);
            } else {
                System.out.println("Input '" + input + "' was not recognised as a known command!");
                executeCommand("help");
            }
        }
    }

    private String getCommand(String input) {
        String command = "";
        if (!input.contains(" ")) {
            command = input.trim();
        } else {
            command = input.substring(0, input.indexOf(' '));
        }
        return command;
    }

    private boolean isCommand(String input) {
        return mapMethod.containsKey(getCommand(input));
    }

    private void executeCommand(String input) {
        if (!isCommand(input)) return;
        String command = getCommand(input);
        List<String> parameters = new ArrayList<>(Arrays.asList(input.split(" ")));
        parameters.remove(0);
        mapMethod.get(command).run();
    }

    public void Help() {
        System.out.println(HELP);
    }

    public void createCarPark() {
        System.out.println("*** Enter data for CarPark ***");
        String name = KeyboardInput.readString("Name", 3);
        String address = KeyboardInput.readString("Address", 3);
        int price = KeyboardInput.readInt("Price per Hour", 3);
        if (price < 0 || name == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createCarPark(name, address, price) != null) {
            System.out.println("*** You have successfully created CarPark! ***");

        } else {
            System.out.println("*** Fail in creating CarPark ***");
        }

    }

    public void getCarParkById() {
        System.out.println("*** Enter CarPark Id to find out ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        if (carParkId < 0) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (cps.getCarPark(carParkId) != null) {
            System.out.println("*** The CarPark has been successfully found ***");
            System.out.println(cps.getCarPark(carParkId));
        } else {
            System.out.println("*** The CarPark has not found ***");
        }
    }

    public void getCarParks() {
        if (cps.getCarParks() != null) {
            System.out.println("*** Here are the list of CarParks");
            System.out.println(cps.getCarParks());
        } else {
            System.out.println("*** There is not any CarPark ***");
        }
    }

    public void updateCarPark() {
        System.out.println("*** Enter data for CarPark ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String name = KeyboardInput.readString("Name", 3);
        String address = KeyboardInput.readString("Address", 3);
        int price = KeyboardInput.readInt("Price Per Hour", 3);
        CAR_PARK car_park = new CAR_PARK(name, address, price);
        car_park.setId(carParkId);
        if (price < 0 || name == null || carParkId <= 0) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.updateCarPark(car_park) != null) {
            System.out.println("*** You have successfully updated carPark! ***");

        } else {
            System.out.println("*** Fail in updating CarPark ***");
        }

    }


    public void deleteCarPark() {
        System.out.println("*** Enter carPark ID for deleting the carPark ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        if (carParkId < 0) {
            System.out.println("*** You have entered wrong ID number *** ");
        }
        if (cps.deleteCarPark(carParkId) != null) {
            System.out.println("*** The CarPark has been successfully deleted ***");
        } else {
            System.out.println("*** The CarPark has not found ***");
        }
    }


    public void createCarParkFloor() {

        System.out.println("*** Enter data for CarParkFloor ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier", 3);
        if (carParkId < 0 || floorIdentifier == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createCarParkFloor(carParkId, floorIdentifier) != null) {
            System.out.println("*** You have successfully created CarParkFloor! ***");

        } else {
            System.out.println("*** Fail in creating CarParkFloor ***");
        }

    }

    public void getCarParkFloor() {
        System.out.println("*** Enter CarParkId and FloorIdentifier to find out ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier", 3);
        if (carParkId < 0 || floorIdentifier == null) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (cps.getCarParkFloor(carParkId, floorIdentifier) != null) {
            System.out.println("*** The CarParkFloor has been successfully found ***");
            System.out.println(cps.getCarParkFloor(carParkId, floorIdentifier));
        } else {
            System.out.println("*** The CarParkFloor has not found ***");
        }

    }

    public void getCarParkFloors() {
        System.out.println("*** Enter CarPark Id to find out list of CarParkFloors ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        if (!cps.getCarParkFloors(carParkId).isEmpty()) {
            System.out.println("*** Here are the list of CarParkFloors");
            System.out.println(cps.getCarParkFloors(carParkId));
        } else {
            System.out.println("*** There is not any CarParkFloor ***");
        }
    }

    public void deleteCarParkFloor() {
        System.out.println("*** Enter carParkId and floorIdentifier for deleting CarParkFloor ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier", 3);

        if (carParkId < 0 || floorIdentifier == null) {
            System.out.println("*** You have entered wrong ID number or FloorIdentifier *** ");
        }
        if (cps.deleteCarParkFloor(carParkId, floorIdentifier) != null) {
            System.out.println("*** The CarParkFloor has been successfully deleted ***");
        } else {
            System.out.println("*** The CarParkFloor has not found ***");
        }
    }

    public void createParkingSpot() {
        System.out.println("*** Enter data for ParkingSpot ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier", 3);
        String parkingSpotIdentifier = KeyboardInput.readString("ParkingSpotIdentifier", 3);
        if (carParkId < 0 || floorIdentifier == null || parkingSpotIdentifier == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createParkingSpot(carParkId, floorIdentifier, parkingSpotIdentifier) != null) {
            System.out.println("*** You have successfully created ParkingSpot! ***");

        } else {
            System.out.println("*** Fail in creating ParkingSpot ***");
        }

    }

    public void getParkingSpot() {
        System.out.println("*** Enter ParkingSpot Id to find out ***");
        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        if (parkingSpotId < 0) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (cps.getParkingSpot(parkingSpotId) != null) {
            System.out.println("*** The ParkingSpot has been successfully found ***");
            System.out.println(cps.getParkingSpot(parkingSpotId));
        } else {
            System.out.println("*** The ParkingSpot has not found ***");
        }

    }

    public void getParkingSpots(){
        System.out.println("*** Enter CarParkId and FloorIdentifier to find out ParkingSpot ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier",3);

        if (carParkId < 0 || floorIdentifier==null) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (!cps.getParkingSpots(carParkId,floorIdentifier).isEmpty()) {
            System.out.println("*** The ParkingSpot has been successfully found ***");
            System.out.println(cps.getParkingSpots(carParkId,floorIdentifier));
        } else {
            System.out.println("*** The ParkingSpots have not found ***");
        }

    }

    public void updateParkingSpot() {
        System.out.println("*** Enter data for ParkingSpot ***");
        Long carParkId = (long) KeyboardInput.readInt("CarParkId", 3);
        String floorIdentifier = KeyboardInput.readString("FloorIdentifier", 3);
        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        String parkingSpotIdentifier = KeyboardInput.readString("ParkingSpotIdentifier", 3);

        CAR_PARK_FLOOR car_park_floor = (CAR_PARK_FLOOR) cps.getCarParkFloor(carParkId, floorIdentifier);

        PARKING_SPOT parking_spot = new PARKING_SPOT(parkingSpotId, parkingSpotIdentifier);
        parking_spot.setCar_park_floor(car_park_floor);
        if (carParkId < 0 || floorIdentifier == null || car_park_floor == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.updateParkingSpot(parking_spot) != null) {
            System.out.println("*** You have successfully updated ParkingSpot! ***");
        } else {
            System.out.println("*** Fail in updating ParkingSpot ***");
        }

    }

    public void deleteParkingSpot() {
        System.out.println("*** Enter parkingSpotId for deleting ParkingSpot ***");
        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        if (parkingSpotId < 0) {
            System.out.println("*** You have entered wrong ID number *** ");
        }
        if (cps.deleteParkingSpot(parkingSpotId) != null) {
            System.out.println("*** The ParkingSpot has been successfully deleted ***");
//            System.out.println(cps.deleteCarPark(carParkId));
        } else {
            System.out.println("*** The ParkingSpot has not found ***");
        }
    }

    public void getAvailableParkingSpot() {
        System.out.println("*** Enter CarPark name to find out list of Available ParkingSpot list ***");
        String name = KeyboardInput.readString("CarParkName", 3);
        if (name == null) {
            System.out.println("*** You have entered wrong carParkName *** ");
        }
        if (!cps.getAvailableParkingSpots(name).isEmpty()) {
            System.out.println("*** Available ParkingSpots have been successfully found ***");
            System.out.println(cps.getAvailableParkingSpots(name));
        } else {
            System.out.println("*** Available ParkingSpots  have not found ***");
        }

    }

    public void getOccupiedParkingSpot() {
        System.out.println("*** Enter CarPark name to find out list of Occupied ParkingSpot list ***");
        String name = KeyboardInput.readString("CarParkName", 3);
        if (name == null) {
            System.out.println("*** You have entered wrong carParkName *** ");
        }
        if (!cps.getOccupiedParkingSpots(name).isEmpty()) {
            System.out.println("*** Occupied ParkingSpots have been successfully found ***");
            System.out.println(cps.getOccupiedParkingSpots(name));
        } else {
            System.out.println("*** Occupied ParkingSpots  have not found ***");
        }

    }

    public void creatUser() {
        System.out.println("*** Enter data for User ***");
        String firstName = KeyboardInput.readString("FirstName", 3);
        String lastName = KeyboardInput.readString("LastName", 3);
        String email = KeyboardInput.readString("Email", 3);
        if (firstName == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createUser(firstName, lastName, email) != null) {
            System.out.println("*** You have successfully created User! ***");

        } else {
            System.out.println("*** Fail in creating User ***");
        }
    }

    public void getUser() {
        System.out.println("*** Enter User Id to find out ***");
        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        if (userId < 0) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (cps.getUser(userId) != null) {
            System.out.println("*** The User has been successfully found ***");
            System.out.println(cps.getUser(userId));
        } else {
            System.out.println("*** The User has not found ***");
        }
    }

    public void getUsers(){
        if (!cps.getUsers().isEmpty()) {
            System.out.println("*** Here are the list of Users");
            System.out.println(cps.getUsers());
        } else {
            System.out.println("*** There is not any User ***");
        }

    }

    public void updateUser() {
        System.out.println("*** Enter data for User ***");
        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        String firstName = KeyboardInput.readString("FirstName", 3);
        String lastName = KeyboardInput.readString("LastName", 3);
        String email = KeyboardInput.readString("Email", 3);

        User user = new User(userId, firstName, lastName, email);
        if (firstName == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.updateUser(user) != null) {
            System.out.println("*** You have successfully updated User! ***");

        } else {
            System.out.println("*** Fail in updating User ***");
        }
    }

    public void deleteUser() {
        System.out.println("*** Enter User Id for deleting User ***");
        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        if (userId < 0) {
            System.out.println("*** You have entered wrong ID number *** ");
        }
        if (cps.deleteUser(userId) != null) {
            System.out.println("*** The User has been successfully deleted ***");
        } else {
            System.out.println("*** The User has not found ***");
        }
    }

    public void createCar() {
        System.out.println("*** Enter data for Car ***");

        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        String brand = KeyboardInput.readString("Brand", 3);
        String colour = KeyboardInput.readString("Colour", 3);
        String model = KeyboardInput.readString("Model", 3);
        String vehicleRegistrationPlate = KeyboardInput.readString("VehicleRegistrationPlate", 3);

        if (userId < 0 || vehicleRegistrationPlate == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createCar(userId, brand, model, colour, vehicleRegistrationPlate) != null) {
            System.out.println("*** You have successfully created Car! ***");

        } else {
            System.out.println("*** Fail in creating Car ***");
        }

    }

    public void getCarById() {
        System.out.println("*** Enter Car Id to find out ***");
        Long carId = (long) KeyboardInput.readInt("CarId", 3);
        if (carId <= 0) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (cps.getCar(carId) != null) {
            System.out.println("*** The Car has been successfully found ***");
            System.out.println(cps.getCar(carId));
        } else {
            System.out.println("*** The Car has not found ***");
        }
    }

    public void getCars(){
        System.out.println("*** Enter User Id to find out Cars ***");
        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        if (userId <= 0) {
            System.out.println("*** You have entered wrong number *** ");
        }
        if (!cps.getCars(userId).isEmpty()) {
            System.out.println("*** Cars have been successfully found ***");
            System.out.println(cps.getCars(userId));
        } else {
            System.out.println("*** Cars have not found ***");
        }

    }

    public void updateCar() {
        System.out.println("*** Enter data for Car ***");
        Long userId = (long) KeyboardInput.readInt("UserId", 3);
        Long carId = (long) KeyboardInput.readInt("CarId", 3);
        String brand = KeyboardInput.readString("Brand", 3);
        String colour = KeyboardInput.readString("Colour", 3);
        String model = KeyboardInput.readString("Model", 3);
        String vehicleRegistrationPlate = KeyboardInput.readString("VehicleRegistrationPlate", 3);

        User user = (User) cps.getUser(userId);
        CAR car = new CAR(carId, brand, model, colour, vehicleRegistrationPlate);
        car.setUser(user);
        if (vehicleRegistrationPlate == null || user == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.updateCar(car) != null) {
            System.out.println("*** You have successfully updated Car! ***");

        } else {
            System.out.println("*** Fail in updating Car ***");
        }
    }

    public void deleteCar() {
        System.out.println("*** Enter Car Id for deleting ParkingSpot ***");
        Long carId = (long) KeyboardInput.readInt("CarId", 3);
        if (carId < 0) {
            System.out.println("*** You have entered wrong ID number *** ");
        }
        if (cps.deleteCar(carId) != null) {
            System.out.println("*** The Car has been successfully deleted ***");
        } else {
            System.out.println("*** The Car has not found ***");
        }

    }

    public void createReservation() {
        System.out.println("*** Enter data for Reservation ***");

        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        Long carId = (long) KeyboardInput.readInt("CarId", 3);

        if (carId <= 0 || parkingSpotId <= 0) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createReservation(parkingSpotId, carId) != null) {
            System.out.println("*** You have successfully created Reservation! ***");

        } else {
            System.out.println("*** Fail in creating Reservation ***");
        }
    }

    public void endReservation() {
        System.out.println("*** Enter Reservation Id for ending Reservation ***");
        Long reservationId = (long) KeyboardInput.readInt("ReservationId", 3);
        if (reservationId <= 0) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.endReservation(reservationId) != null) {
            System.out.println("*** You have successfully ended the Reservation! ***");

        } else {
            System.out.println("*** Fail in ending Reservation ***");
        }

    }

    public void getReservations() {
        System.out.println("*** Enter ParkingSpot Id and Date to find out Reservations ***");
        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        int year = KeyboardInput.readInt("Year", 3);
        int month = KeyboardInput.readInt("Month", 3);
        int day = KeyboardInput.readInt("Day", 3);
        Date date = toDate(year, month, day);
        if (parkingSpotId <= 0 || year <= 0 || month <= 0 || day <= 0) {
            System.out.println("*** You have entered wrong data *** ");
        }
        if (cps.getReservations(parkingSpotId, date)!=null) {
            System.out.println("*** The Reservations have been successfully found ***");
            System.out.println(cps.getReservations(parkingSpotId, date));
        } else {
            System.out.println("*** The Reservations have not found ***");
        }
    }

    public void updateReservation() {
        System.out.println("*** Enter data for Reservation ***");
        Long reservationId = (long) KeyboardInput.readInt("ReservationId", 3);
        Long parkingSpotId = (long) KeyboardInput.readInt("ParkingSpotId", 3);
        Long carId = (long) KeyboardInput.readInt("CarId", 3);
        int year = KeyboardInput.readInt("Start-Year", 3);
        int month = KeyboardInput.readInt("Start-Month", 3);
        int day = KeyboardInput.readInt("Start-Day", 3);
        int hour = KeyboardInput.readInt("Hour", 3);
        int minute = KeyboardInput.readInt("Minute", 3);
        int second = KeyboardInput.readInt("Second", 3);
        Date date = toDate(year, month, day, hour, minute, second);

        PARKING_SPOT parkingSpot = (PARKING_SPOT) cps.getParkingSpot(parkingSpotId);
        CAR car = (CAR) cps.getCar(carId);
        Reservation reservation = new Reservation(reservationId, date);
        reservation.setParking_spot(parkingSpot);
        reservation.setCar(car);
        if (reservationId <= 0 || parkingSpotId <= 0 || carId < 0 || year < 0 || month < 0 || day < 0
                || parkingSpot == null || car == null) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.updateReservation(reservation) != null) {
            System.out.println("*** You have successfully updated Reservation! ***");

        } else {
            System.out.println("*** Fail in updating Reservation ***");
        }

    }

    public void createHoliday() {
        System.out.println("*** Enter data for Holiday ***");

        String holidayName = KeyboardInput.readString("HolidayName", 3);
        int year = KeyboardInput.readInt("Year", 3);
        int month = KeyboardInput.readInt("Month", 3);
        int day = KeyboardInput.readInt("Day", 3);
        Date date = toDate(year, month, day);


        if (holidayName == null || year <= 0 || month <= 0 || day <= 0) {
            System.out.println("*** You have entered wrong data ***");
            return;
        }
        if (cps.createHoliday(holidayName, date) != null) {
            System.out.println("*** You have successfully created Holiday! ***");

        } else {
            System.out.println("*** Fail in creating Holiday ***");
        }

    }

    public void getHolidays() {
        if (cps.getHolidays() != null) {
            System.out.println("*** Here are the list of Holidays");
            System.out.println(cps.getHolidays());
        } else {
            System.out.println("*** There is not any Holidays ***");
        }
    }

    public void deleteHoliday() {
        System.out.println("*** Enter Holiday ID for deleting Holiday ***");
        Long holidayId = (long) KeyboardInput.readInt("HolidayId", 3);
        if (cps.deleteHoliday(holidayId) != null) {
            System.out.println("*** You have successfully deleted a Holiday ***");
        } else {
            System.out.println("*** Fail on deleting a Holiday ***");
        }
    }

    public static Date toDate(int y, int m, int d, int hr, int min, int sec) {
        if (y <= 0 || m <= 0 || d <= 0) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d, hr, min, sec);

        return calendar.getTime();
    }

    public static Date toDate(int y, int m, int d) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m - 1, d, 0, 0, 0);

        return calendar.getTime();
    }

}
