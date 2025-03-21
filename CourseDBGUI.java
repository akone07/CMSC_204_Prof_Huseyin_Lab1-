package courseDatabase;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class CourseDBGUI extends Application {
	public static void main(String[] args) { launch(args); }
	@Override
	public void start(Stage stage) throws Exception {
		FXMainPane root = new FXMainPane();
		stage.setScene(new Scene(root, 600, 700));
		stage.setTitle("Course Database");
		stage.show(); 
		}
	}