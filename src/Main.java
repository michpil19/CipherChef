import Classes.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXMLFiles/StartMenu.fxml"));
        primaryStage.setTitle("CipherChef");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
    /*
        Monoalphabetic mono = new Monoalphabetic();
        mono.setInput("Litwo Ojczyzno moja Ty jesteś jak zdrowie Ile cię stracił Dziś piękność twą w szlacheckim stanie trudno zaradzić wolał gości Żydom do nowej mody odsyłać konie porzucone same szczypiąc trawę ciągnęły powoli pod lasem zwaliska");
        mono.decrypt();
        mono.displayTextLettersFrequency();
    */

/*
        Playfair playfair = new Playfair("wtorek", "marcin");
        playfair.displayMatrix();
        launch(args);*/


        Vigener vigener = new Vigener("tajny tekst","klucz");
        vigener.displayMatrix();
        System.out.println("getOutput() " + vigener.getOutput());
        //launch(args);

        MorseCode morseCode = new MorseCode("sos");
        System.out.println("getOutput() " + morseCode.getOutput());

    }
}
