package pro;


import java.util.Random;
import java.text.NumberFormat;
import java.text.DecimalFormat;


public class Coordinate {

    public static double[][] station_coords = null;
    public static double[][] customer_coords = null;


    public static void randomGenerator(double width_x, double height_y, int m, int n){
        Random rnd = new Random();
        NumberFormat formatter = new DecimalFormat("#0.0");


        station_coords = new double[n][2];
        customer_coords= new double[m][2];



        for (int i = 0; i < n; i++) {
            station_coords[i][0] = (1 + rnd.nextDouble()*width_x);//X coord
            station_coords[i][1] =(1 + rnd.nextDouble()*height_y); //Y coord
            System.out.println("Baz Yerleşim "+i+" ("+formatter.format(station_coords[i][0]).replace(",",".") +", "+formatter.format(station_coords[i][1]).replace(",",".")+")");

        }
        System.out.println();

        for (int i = 0; i < m; i++) {
            customer_coords[i][0] = (1 + rnd.nextDouble()*width_x);//X coord
            customer_coords[i][1] = (1 + rnd.nextDouble()*height_y); //Y coord
            System.out.println("Müşteri "+i+" ("+formatter.format(customer_coords[i][0]).replace(",",".") +", "+formatter.format(customer_coords[i][1]).replace(",",".")+")");

        }




    }







}
