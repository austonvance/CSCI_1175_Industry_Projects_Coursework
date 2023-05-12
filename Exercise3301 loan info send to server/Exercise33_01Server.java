// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;

public class Exercise33_01Server extends Application {
  
  public class LoanServer {
    
    public static void main(String[] args) {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server started at " + new java.util.Date() + "\n");
        
        // Listen for a connection request
        Socket socket = serverSocket.accept();
        
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        
        // Receive loan information from the client
        double annualInterestRate = inputFromClient.readDouble();
        int numberOfYears = inputFromClient.readInt();
        double loanAmount = inputFromClient.readDouble();
        
        // Create a loan object
        Loan loan = new Loan(annualInterestRate, numberOfYears, loanAmount);
        
        // Send monthly payment and total payment back to the client
        outputToClient.writeDouble(loan.getMonthlyPayment());
        outputToClient.writeDouble(loan.getTotalPayment());
        
        // Close the streams and socket
        inputFromClient.close();
        outputToClient.close();
        socket.close();
      } catch (IOException ex) {
        System.err.println(ex);
      }
    }
  }
  
  class Loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
      this.annualInterestRate = annualInterestRate;
      this.numberOfYears = numberOfYears;
      this.loanAmount = loanAmount;
    }
    
    public double getMonthlyPayment() {
      double monthlyInterestRate = annualInterestRate / 1200;
      double monthlyPayment = loanAmount * monthlyInterestRate / (1 - 1 / Math.pow(1 + monthlyInterestRate, numberOfYears * 12));
      return monthlyPayment;
    }
    
    public double getTotalPayment() {
      double totalPayment = getMonthlyPayment() * numberOfYears * 12;
      return totalPayment;
    }
  }
  
  // Text area for displaying contents
  private TextArea ta = new TextArea();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }
    
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
