package TravelBudgetPlanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
 


//---------- INTERFACE ----------
interface CostStrategy {
 double calculateCost(int days, int people);
}

//---------- ABSTRACT CLASS ----------
abstract class TravelPlan implements CostStrategy {

 public double hotelRate;
 public double foodRate;

 TravelPlan(double hotelRate, double foodRate) {
     this.hotelRate = hotelRate;
     this.foodRate = foodRate;
 }

 public double getHotelTotal(int days, int people) {
	    return hotelRate * days * people;
	}

	public double getFoodTotal(int days, int people) {
	    return foodRate * days * people;
	}
}

//---------- INHERITANCE ----------
class BudgetPlan extends TravelPlan {
 BudgetPlan() {
     super(800, 250);
 }

 public double calculateCost(int days, int people) {
     return getHotelTotal(days, people) + getFoodTotal(days, people);
 }
}

class StandardPlan extends TravelPlan {
 StandardPlan() {
     super(2500, 600);
 }

 public double calculateCost(int days, int people) {
     return getHotelTotal(days, people) + getFoodTotal(days, people);
 }
}

class LuxuryPlan extends TravelPlan {
 LuxuryPlan() {
     super(6000, 1200);
 }

 public double calculateCost(int days, int people) {
     return getHotelTotal(days, people) + getFoodTotal(days, people);
 }
}

//---------- TRANSPORT (SWITCH CASE) ----------
class TravelCostCalculator {

 // Distance Logic
 public static int getDistance(String from, String to) {

     if (from == null || to == null) return 0;

     String key = from + "-" + to;

     switch (key) {
         case "Mumbai-Goa": case "Goa-Mumbai": return 600;
         case "Mumbai-Pune": case "Pune-Mumbai": return 150;
         case "Mumbai-Delhi": case "Delhi-Mumbai": return 1400;
         case "Mumbai-Jaipur": case "Jaipur-Mumbai": return 1150;
         case "Mumbai-Manali": case "Manali-Mumbai": return 1900;
         case "Mumbai-Kerala": case "Kerala-Mumbai": return 1200;
         case "Delhi-Manali": case "Manali-Delhi": return 530;
         case "Delhi-Jaipur": case "Jaipur-Delhi": return 280;
         case "Delhi-Goa": case "Goa-Delhi": return 1900;
         case "Delhi-Kerala": case "Kerala-Delhi": return 2800;
         case "Delhi-Pune": case "Pune-Delhi": return 1450;
         case "Pune-Goa": case "Goa-Pune": return 450;
         case "Pune-Jaipur": case "Jaipur-Pune": return 1200;
         case "Goa-Kerala": case "Kerala-Goa": return 650;
         default: return 700; // fallback
     }
 }

 // Transport Cost Logic
 public static double getTransportCost(int distance, String mode, int people) {

     double ratePerKm;

     switch (mode) {
         case "Bus":
             ratePerKm = 4;
             break;
         case "Train":
             ratePerKm = 6;
             break;
         case "Flight":
             ratePerKm = 12;
             break;
         default:
             ratePerKm = 5;
     }

     return distance * ratePerKm * people;
 }
}

//---------- CUSTOM EXCEPTION ----------
class InvalidTripInputException extends Exception {
 InvalidTripInputException(String message) {
     super(message);
 }
}

//---------- FILE MANAGER (GUI SUPPORT) ----------
class FileManager {

 public static void saveTrip(String data) {

     try {
         java.io.FileWriter writer = new java.io.FileWriter("trip_data.txt", true);
         writer.write(data + System.lineSeparator());
         writer.close();

     } catch (Exception e) {
         System.out.println("Error saving trip data");
     }
 }
}