public class Perceptron
{
   double[] genes;
   double c = 0.0001; //learning constant
   //double[] genes = new double[3];
   //char[] genes = new char[target.length()];
   float fitness;
   int correct;
   int incorrect;
   int n1;
   public Perceptron(int n)
   {
      n1 = n;
      genes = new double[n];
      for(int i = 0; i < genes.length; i++)
      {
         genes[i] = (Math.random()*2)-1;
      }
      //genes[1] = 0.5;
   }

   public int feedforward1(double[] inputs)
   {
      double sum = 0;
      for(int i = 0; i < genes.length; i++)
      {
         sum += inputs[i]*genes[i];
      }
      
      return activate(sum);
   }
   /*public double what(double[] inputs)
   {
      double sum = 0;
      for(int i = 0; i < genes.length; i++)
      {
         sum += inputs[i]*genes[i];
      }
      return sum;
   }*/
   public int activate(double sum1)
   {
      if(sum1>0)
         return 1;
      else
         return -1;
   }
   public void train(double[] inputs, int desired)
   {
      int guess = feedforward1(inputs);
      
      double error = desired - guess;
      if(error!=0)
         //System.out.println("lul");
      for(int i = 0; i < genes.length; i++)
         genes[i] += c * error * inputs[i];
   }
   public void setCorrect(int a)
   {
      correct = a;
   }
   public void setIncorrect(int a)
   {
      incorrect = a;
   }
   public double fitness()
   {
      if(correct+incorrect==0)
         return 0;
      double score = correct/(correct+incorrect);
      score = score*score;
      return score;
      /*double score = 0;
      for(int i = 0; i < genes.length; i++)
      {
         if(genes[i] ==  target.charAt(i))
            score++;
      }
      fitness = (float)(score/(double)(target.length()));
      fitness = fitness*fitness;*/
   }
   public Perceptron crossover(Perceptron partner)
   {
      Perceptron child = new Perceptron(n1);
      
      int midpoint = (int)(Math.random()*genes.length);
      
      for(int i = 0; i < genes.length; i++)
      {
         if(i > midpoint)
            child.genes[i] = genes[i];
         else
            child.genes[i] = partner.genes[i];   
      }
      return child;
   }
   public void mutate(double mutationRate)
   {
      
      for (int i = 0; i < genes.length; i++)
      {
         if(Math.random() < mutationRate)
         {
            double random = (Math.random()*100-50);
            genes[i] = random;
         }
      }
   }
   public String print()
   {
      String s = "";
      for(int i = 0; i < genes.length; i++)
      {
         s = s+genes[i];
      }
      return s;
   }
      
}