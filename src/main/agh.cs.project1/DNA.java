package agh.cs.project1;

import java.util.Random;

public class DNA {
    final int numberOfGenes; //Size of genome Array
    int[] genome; //Array of DNA code
    int[] genomeQuantity; //Array of DNA genomes quantity

    public DNA () {
        Random g = new Random();
        this.numberOfGenes = 32;
        this.genome = new int [numberOfGenes];
        this.genomeQuantity = new int [8];

        for(int i=0; i<8; i++) {
            genomeQuantity[i] = 0;
        }
        for(int i=0; i<32; i++) {
            int tmp = g.nextInt(8);
            genome[i] = tmp;
            genomeQuantity[genome[i]]++;
        }
        genomeRepairing();
    }
    public DNA (DNA a1, DNA a2) {
        Random g = new Random();
        this.numberOfGenes = 32;
        int break1 = g.nextInt(numberOfGenes );
        int break2 = g.nextInt(numberOfGenes );
        while(break1 == break2) break2 = g.nextInt(numberOfGenes);
        if(break1 > break2){
            int tmp = break1; break1 = break2; break2 = tmp;
        }
        this.genome = new int[numberOfGenes];
        this.genomeQuantity = new int [8];

        for(int i=0 ; i<break1 ; i++) {
            this.genome[i] = a1.genome[i];
            this.genomeQuantity[this.genome[i]]++;
        }
        for(int i=break1 ; i<break2 ; i++) {
            this.genome[i] = a2.genome[i];
            this.genomeQuantity[this.genome[i]]++;
        }
        for(int i=break2 ; i<numberOfGenes ; i++) {
            this.genome[i] = a1.genome[i];
            this.genomeQuantity[this.genome[i]]++;
        }
        genomeRepairing();
    }

    private void genomeRepairing (){
        int sought;
        int i = 0;
        while(this.genomeQuantity[0]*this.genomeQuantity[1]*this.genomeQuantity[2]*this.genomeQuantity[3]*this.genomeQuantity[4]*this.genomeQuantity[5]*this.genomeQuantity[6]*this.genomeQuantity[7] == 0){
            Random g = new Random();
            sought = -1;
            while(sought < 0 && i < 8)
            {
                if(this.genomeQuantity[i] == 0)
                    sought = i;
                i++;
            }
            while(this.genomeQuantity[sought] == 0){
                int j = g.nextInt(numberOfGenes);
                if(this.genomeQuantity[this.genome[j]] > 1){
                    this.genomeQuantity[this.genome[j]]--;
                    this.genomeQuantity[sought]++;
                    this.genome[j] = sought;
                }
            }
        }
    }
    public String toString (){
        String result = "(";
        for(int i=0; i<numberOfGenes; i++) {
            result = result + i +",";
        }
        return result;
    }
}
