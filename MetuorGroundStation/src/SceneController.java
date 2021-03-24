
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Random;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;


public class SceneController {

    private int[] counter = new int[6];
    private double prog = 0.1;
    private boolean isStoped = false;
    
    
    @FXML
    private ResourceBundle resources;

    @FXML
    private ProgressBar progress;

    @FXML
    private URL location;

    @FXML
    private AnchorPane videoControl;

    @FXML
    private LineChart<String, Number> temperature;

    @FXML
    private LineChart<String, Number> pressure;

    @FXML
    private LineChart<String, Number> hight;

    @FXML
    private LineChart<String, Number> velocity;

    @FXML
    private LineChart<String, Number> line;

    @FXML
    private LineChart<String, Number> voltage;

    @FXML
    void initialize() {  
        
    }

    public void lineChartController(LineChart<String, Number> chart){
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
   
        

        Random rand= new Random();
        int datas[] = new int[5];
        int index;

        for(int i = 0; i < datas.length; i++){
            datas[i] = rand.nextInt(11);
        }

         
        
        if (chart.getTitle().equals("Hight versus Time")){
            index = 1;
        } else if (chart.getTitle().equals("Temperature versus Time")){
            index = 2;
        } else if (chart.getTitle().equals("Pressure versus Time")){
            index = 3;
        } else if (chart.getTitle().equals("Voltage versus Time")){
            index = 4;
        } else if(chart.getTitle().equals("Line Graph Example")){
            index = 5;
        } else index = 6;
        

        Thread thread = new Thread(() -> {
            int delay = 500;
            
            try {
                while(true){

                        Platform.runLater(() -> {
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(counter[(index-1)%6]), datas[0]));
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(counter[(index-1)%6] + 1), datas[1]));
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(counter[(index-1)%6] + 2), datas[2]));
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(counter[(index-1)%6] + 3), datas[3]));
                        series.getData().add(new XYChart.Data<String, Number>(Integer.toString(counter[(index-1)%6] + 4), datas[4]));
                        series.setName("Time");
                        chart.getData().add(series);
                        });

                        Thread.sleep(delay);
                        Platform.runLater(() -> {
                        chart.getData().clear();
                        series.getData().clear();
                        });

                        Platform.runLater(() -> {
                            for(int i = 0; i < datas.length-1; i++){
                                datas[i] = datas[i+1];
                            }
                            datas[datas.length-1] = rand.nextInt(11);
                        });

                        ++counter[(index-1)%6];
                        
                        Platform.runLater(() -> {
                            chart.getData().clear();
                            series.getData().clear();
                        });
            }
                
            } catch (InterruptedException exc) {

                throw new Error("Unexpected interruption");
            }
        });
        thread.start();

    }

    public void stopTransfering(ActionEvent event){
        isStoped = true;
        System.out.println("stoped");
    }
    
    public void startTransfering(ActionEvent event) {
        prog = 0.1;

        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                
                try {
                    while(prog >= 0.1 && prog < 1){
                        Platform.runLater(() -> {
                            
                            prog += 0.1;
                            if(prog > 1)
                                prog = 1;
                            if(isStoped){
                                prog = 0;
                                System.out.println("stop");
                                isStoped = false;
                            }
                        });
                        
                        Thread.sleep(500);
                        Platform.runLater(() -> progress.progressProperty().set(prog));
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();     

    }

    public void openFolder(ActionEvent event){

        try {
            Desktop.getDesktop().open(new File("C:\\"));
        } catch (IOException e) {
            
            e.printStackTrace();
        }
        System.out.println("open");  
    }

    public void test(ActionEvent event) throws InterruptedException{

        chartTest();
        
    }

    

    public void chartTest() throws InterruptedException{
        
     
        lineChartController(velocity);
        lineChartController(hight);
        lineChartController(temperature);
        lineChartController(pressure);
        lineChartController(voltage);
        lineChartController(line);  
                       
        
    }

}
