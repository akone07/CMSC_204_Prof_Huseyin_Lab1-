package generic_Lab;

/*
 * Class: CMSC204-31645
 * 
 * Instructor: Huseyin_Aygun 
 * 
 * Description: The DataSetGen.java
 *
 *   The DataSetGen class is like a versatile container that can hold and analyze any type of data that has a measurable value. 
 * It's designed to work with objects that implement the Measurable interface, such as bank accounts or baseball players.
 * The class allows you to add these objects, calculates the average of their measures, and finds the one with the highest measure. 
 * By being generic, it can handle various types of measurable data without needing to be modified. 
 * In short, it's a flexible tool for working with different kinds of data that have measurable values.
 *
 * Due: 02-09-2025
 * 
 * Platform/compiler: Windows 
 * 
 * I pledge that I have completed the programming 
 * assignment independently. I have not copied the code 
 * from a student or any source. I have not given my code 
 * to any student.
   Print your Name here: ALASSANE KONE
*/






/**
   Computes the average of a set of data values.
*/
public class DataSetGen<T extends Measurable>
{
   private double sum;
   private T maximum;
   private int count;

   /**
      Constructs an empty data set.
   */
   public DataSetGen()
   {
      sum = 0;
      count = 0;
      maximum = null;
   }

   /**
      Adds a data value to the data set.
      @param x a data value
   */
   public void add(T x)
   {
      sum = sum + x.getMeasure();
      if (count == 0 || maximum.getMeasure() < x.getMeasure())
         maximum = x;
      count++;
   }

   /**
      Gets the average of the added data.
      @return the average or 0 if no data has been added
   */
   public double getAverage()
   {
      if (count == 0) return 0;
      else return sum / count;
   }

   /**
      Gets the largest of the added data.
      @return the maximum or null if no data has been added
   */
   public T getMaximum()
   {
      return maximum;
   }
}
