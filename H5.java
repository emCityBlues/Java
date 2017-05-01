import java.util.Scanner;

/**
  <ul>    
      <li>Assignment: Week 4 - Homework 5, spherical distance</li>
      <li>Due Date: by Apr 23 at 11:55pm</li>
  </ul>
  <p>
  This program computes the spherical distance in miles between two points 
  on the surface of the Earth with user input for latitudes and longitudes.
  <p><b><small>* Input validation is not performed. :(</small></b>
*/ 
public class H5   {

   /**
      Main method for spherical distance computation program.
      <p>
      The main loop will loop 4x and call get_input and calc_DMS_to_DD(deg, min, sec, direction)
      since 2 sets of latitude and longitudes are used.  Finally, calc_spherical_distance
      is called to compute the spherical distance of 2 positions. 
      
      @param args An array of console line argument 
   */
   public static void main(String[] args) {
      
      int coordData[];  // holds user input coordinates
      int deg;         
      int min;
      int sec;
      int direction;
      
      // [0] = latitude 1, [1] = longitude 2, [2] = latitude 2, [3] = longitude 2
      double decimalDegreesInRadian[] = {0.0, 0.0, 0.0, 0.0};
            
      String[] inputPrompts = new String[4];
      inputPrompts[0] = "latitude 1?";
      inputPrompts[1] = "longitude 1?";
      inputPrompts[2] = "latitude 2?";
      inputPrompts[3] = "longitude 2?";
      
      print_header();
      for ( int i = 0; i < 4; i++ )  {
         System.out.println(inputPrompts[i]);
         coordData = get_input();
         deg = coordData[0];
         min = coordData[1];
         sec = coordData[2];
         direction = coordData[3];
         
         decimalDegreesInRadian[i] = Math.toRadians(calc_DMS_to_DD(deg, min, sec, direction));
      } // end for    
      
      double distance = calc_spherical_distance(decimalDegreesInRadian);
      
      System.out.println("\nSpherical distance is " + String.format("%.1f", distance) 
                                                    + " miles." );
   } // end main
   
   /**
      Prints header information, including brief instructions.      
   */
   public static void print_header() {
   
      System.out.println("======= HW5: Spherical Distance ===============================");
      System.out.println("Enter each DMS coordinates in the format, DDD MM SS Direction.");
      System.out.println("[ DDD:0-180  MM:0-59  SS:0-59  Directions:N,S,W,E ]");
      System.out.println("ex. 118 24 0 W\n");
      System.out.println("** For Sample data, you can use **");
      System.out.println("\tNashville, TN   ( Latitude: 36 7 2 N   Longitude: 86 40 2 W )");
      System.out.println("\tLos Angeles, CA ( Latitude: 33 56 4 N  Longitude: 118 24 0 W )");    
      System.out.println(); 
   } // end printHeader
   
   /**
      Obtains degrees, minutes, seconds, and direction for coordinates.
      North and east directions are represented as an integer +1 while
      south and west as integer -1. 
      
      @return An array: degrees, minutes, seconds, and direction  
   */
   public static int[] get_input()  {
      
      Scanner console = new Scanner(System.in);
      int[] inputs = new int[4];
      
      inputs[0] = console.nextInt();  // DDD
      inputs[1] = console.nextInt();  // MM
      inputs[2] = console.nextInt();  // SS
      
      String direction = console.next();
      direction = direction.toUpperCase();
      
      if ( direction.equals("N") || direction.equals("E") ) {
         inputs[3] = 1;   // positive degrees
      } else if ( direction.equals("S") || direction.equals("W") ) {
         inputs[3] = -1;  // negative degrees
      }
      
      return inputs;
      
   } // end getInput
   
   /**
      Converts DMS(degrees, minutes, seconds, direction) format to 
      decimal degree format.
      
      @param DDD Degrees ranging from 0 to 180
      @param MM Minues ranging from 0 to 59
      @param SS Seconds ranging from 0 to 59
      @param direction For north and east, +1. For south and west, -1.
      @return Coordinates in decimal degrees
   */
   public static double calc_DMS_to_DD(int DDD, int MM, int SS, int direction)   {
      
      final double secsInHour = 3600.0;
      final int secsInMin = 60;
         
      int inSeconds = MM * secsInMin + SS;
      double fractionDegrees = inSeconds / secsInHour;
            
      return ((DDD + fractionDegrees) * direction); // returning in decimal degrees
        
   } // end calc_DMS_to_DD SB
   
   /**
      Calculates spherical distance of 2 locations.
      <p>
      It first uses the spherical law of cosines to compute the central angle 
      in radian between 2 locations.  Spherical distance is obtained by multiplying 
      the central angle with Earth's radius in miles.
      <p>
      central angle = arccos(sin(latitude1) * sin(latitude2) + cos(latitude1) * cos(latitude2)
                        * cos(longitude1 - longitude2))
      <p>
      spherical distance = C * R where R used is 3959.87 miles
      
      @param deciDegInRadian An array of decimal degrees in radian for latitudes and longitudes
      @return Spherical distance in miles
   */
   public static double calc_spherical_distance(double[] deciDegInRadian)  {
   
      double lat1 = deciDegInRadian[0];
      double lon1 = deciDegInRadian[1];
      double lat2 = deciDegInRadian[2];
      double lon2 = deciDegInRadian[3];
      
      final double earthRadius = 3959.87;  // earth radius in miles 
      
      // C is central angle in radian   
      double C = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                         + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
   
      return (earthRadius * C);   // returning spherical distance
   } // end calc_spherical_distance
    
} // end HW 5
