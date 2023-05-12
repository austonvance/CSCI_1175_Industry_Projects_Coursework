import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;import java.net.*;
import java.sql.*;
import java.io.*;
import javax.xml.soap.*;


public class Exercise33_09Client extends Application {
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
    taClient.setWrapText(true);
    //taServer.setDisable(true);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

    // To complete later
    new Thread(() -> {
      try{
        //create a server socket
        ServerSocket serverSocket = new ServerSocket(8080);
        //append connection status if successful
        Platform.runlater(() -> {
          textarServer.appendText("Server Started At: " + new Date() + "\n");
        });
        //opens connection for a request
        Socket socket = serverSocket.accept();
        
        //creates data I/O streams
        DataInputStream inputFromServer = new DataInputStream (socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());
        
        while (true) {
          //receives Text from Server User
          String Text = inputFromServer.readUTF();
          outputToClient.writeUTF(Text);
          
          Platform.runLater(() -> {
            textarServer.appendText(Text + "\n");
          });
        }
        
      } catch(Exception err) {
        err.printStackTrace();
      }
    }).start();
    textarClient.setOnKeyPressed( e-> {
      if (e.getCode() == KeyCode.ENTER) {
        String Text = null;
        Text = textarClient.getText().trim();
        if (toServer == null) {
          try {
            //create socket to connect to server on port 8001
            Socket socket = new Socket("localhost", 8000);
            
            fromServer = new DataInputStram(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
            
            InetAddress inetAddress = socket.getInetAddress();
          } catch(Exception er) {
            er.printStackTrace();
          }
        }
        
        try {
          //get radius from text field
          toServer.writeUTF(Text);
          toServer.flush();
          //get server from server
          
        } catch(Exception err) {
          err.printStackTrace();
          
        }
        textarClient.clear();
      }
      
    });
    
    
  }
    
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
          