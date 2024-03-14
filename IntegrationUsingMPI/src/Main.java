
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import mpi.*;
public class Main {
    public static void main(String args[]) throws Exception {

        float  a = 0 , b =1, n = 1000000 ;
        int p =4 ;
        float h = (b-a)/n;
        long startingTime = 0;
        MPI.Init(args);
        int MainRank = MPI.COMM_WORLD.Rank();
        if (MainRank ==0 ){
            startingTime = System.currentTimeMillis();
        }
        int start = (int)(MainRank * n/p) ;
        int end = (int)((MainRank+1)*n/p);
        float deltasSum=0;
        for(int i = start ; i<end; i++) {
            float delta =  a + i *h;
            deltasSum+=(delta*delta);
        }
        deltasSum = deltasSum *h ;
        float [] res= new float[1];
        float [] sum = new float[1];
        res[0] =deltasSum;
        MPI.COMM_WORLD.Reduce(res , 0,sum ,0,1 ,MPI.FLOAT ,MPI.SUM ,0);
        if(MainRank == 0 ) {
            System.out.println("Result " + sum[0]);
            System.out.println("running time = " + (System.currentTimeMillis()- startingTime) + " ms.  n = " + (int)n );
        }

        MPI.Finalize();



    }


}