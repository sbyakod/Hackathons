import java.util.*;
import java.io.*;


public class DataStuff {
   public static Queue<DataPoint> q, secondQ;
   public static void main(String[] args) throws Exception {
      Scanner infile = new Scanner(new File("Acquisition_2015Q1.txt"));
      
      Queue<String[]> a = new LinkedList<>();
      
      int i = 0;
      
      while (infile.hasNext()) {
      //for (int y = 0; y < 10; y++) {
         String[] str = infile.nextLine().split("\\|");
         String[] str2 = new String[4];
         str2[0] = str[0];
         str2[1] = str[3];
         str2[2] = str[4];
         str2[3] = str[12];
         
         a.add(str2);
         
         i++;
         if (i%10000==0)
            System.out.println(i);
      }
      
      infile = new Scanner(new File("Performance_2015Q1.txt"));
      
      secondQ = new LinkedList<>();
      
      String temp = "";
      
      
      while (infile.hasNext() && !a.isEmpty()) {
         String[] str = a.poll();
         String id = str[0];
         
         String[] temp2 = infile.nextLine().split("\\|");
         temp = temp2[0];
         
         int f = -1;
         
         boolean good = true;
         
         while (temp.equals(id)) {
            if ((temp2.length >= 16 && !temp2[16].isEmpty()))
               f = 1;
               
            try {
               int x = Integer.parseInt(temp2[10]);
               if (x > 3)
                  f = 1;
            } catch (Exception e) {
               
            }
               
            if (temp2[10].equalsIgnoreCase("X"))
               good = false;
               
            try {
               temp2 = infile.nextLine().split("\\|");
               temp = temp2[0];
            } 
            catch (Exception e) {
               break;
            }
         }
         
         try {
            if (good) {
               DataPoint d = new DataPoint(str, f);
               /*if (d.bad == 1)
                  System.out.println(d);*/
               secondQ.add(d);
            }
         } 
         catch (Exception e) {
            
         }
      }
   
   
      q = new LinkedList<>();
      int a1 = secondQ.size();
      for(int i2 = 0; i2 < a1/2; i2++)
      {
         DataPoint temp123 = secondQ.poll();
         q.add(temp123);
         /*if(secondQ.peek().bad==1)
         {
            q.add(temp123);
            secondQ.add(temp123);
         }
         else
            q.add(temp123);*/
           
      }

      
      NNetwork.go();
      
      //System.out.println("Get me out of here");
   }
   public static class DataPoint {
      public int bad;
      public String id;
      /*
      * 0: interest rate
      * 1: principal balance
      * 2: credit score
      */
      public double[] data;
      public DataPoint(String[] str, int f) {
         bad = f;
         id = str[0];
         data = new double[4];
         data[0] = Double.parseDouble(str[1]);
         data[1] = Double.parseDouble(str[2]);
         data[2] = Double.parseDouble(str[3]);
         data[3] = 1;
      }
      public String toString() {
         return id+": "+data[0]+"%, $"+data[1]+", "+data[2]+" pts. At risk: " + bad;
      }
   }

}
class NNetwork
{
   static Perceptron p;
   //static Trainer[] training = new Trainer[2000];
   int count = 0;
   
   public static void go()
   {
      p = new Perceptron(4);
      //setup();
      //double[] point = {50,-12,1};
      //int result = p.feedforward(point);
      //System.out.println(result);
      
      int sumcorrect = 0;
      int sumwrong = 0;
      
      for (int i3 = 0; i3 < DataStuff.q.size() - 1; i3++) {
         trainPoint(DataStuff.q.poll());
         }
      for(int i4 = 0; i4 < DataStuff.secondQ.size()-1;i4++)
      {
         if (verify(DataStuff.secondQ.poll()))
            sumcorrect++;
         else
            sumwrong++;
      }
      
      /*for(DataStuff.DataPoint d:DataStuff.q)
      {
         
      }
      
      
      
      for(DataStuff.DataPoint d: DataStuff.secondQ)
      {
         
      }*/
      
      System.out.println("Correct: " + sumcorrect +
                        "\nWrong: " + sumwrong +
                        "\n% success: " + (((double)(100*sumcorrect))/(sumwrong+sumcorrect))
                        );
      
   }
   /*public static void setup()
   {
      for(int i = 0; i < training.length; i++)
      {
         double x = Math.random()*400;
         double y = Math.random()*400;
         int answer = 1;
         if(y<f(x))
            answer = -1;
         training[i] = new Trainer(x, y, answer);
      }
   }*/
   public static void setupNew()
   {
      
   }
   /*public static int activate(double sum)
   {
      if(sum>0)
         return 1;
      else
         return 0;
   }*/
   public static double f(double x)
   {
      return 2*x+1;
   }
   public static void trainPoint(DataStuff.DataPoint d)
   {
      p.train(d.data, d.bad);
   }
   public static boolean verify(DataStuff.DataPoint d)
   {
      int i = p.feedforward1(d.data);
      //if(i != d.bad)
         //System.out.println("me2thanks");
      //System.out.println(i);
      return (i == d.bad);
   }
   //public static int query(double[] inputs)
}