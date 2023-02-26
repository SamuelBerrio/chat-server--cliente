module com.example.chatfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.logging;


    opens com.example.chatfx to javafx.fxml;
    exports com.example.chatfx;
}