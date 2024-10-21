module org.example.gr2_quizgame {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires org.json;
    requires java.desktop;
    requires java.sql;
    requires javafx.media;


    opens org.example.gr2_quizgame to javafx.fxml;
    exports org.example.gr2_quizgame;
}