package passtwo;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PassTwo {

                        
    public static void main(String[] args) {
        
                       InstToOPCode itop = new InstToOPCode();
                        String h = "H ";
                        String t ="";
                        String tempT="";
                        String E = "E ";
                        int currAddress = 0;
                        int startingBlockAddress = 0;
                        String OPCodeList = "";
                        int blockSize = 0;
                        int globalAdr = 0;
                        String hexBlockSize = "";
                        String startAdr = "";
                        int progLen = 0; 
                        boolean bodyReady = false, tempTDone = false;
                        
		try {
                    //variable declarations
                    BufferedReader reader;
                    String fileLoc = "/Users/Abdu/Desktop/Java/PassTwo/src/passtwo/code.txt";
			reader = new BufferedReader(new FileReader(fileLoc));
			String line = reader.readLine();
                        
                        
                        
                        
                        //Read the text file
			while (line != null) {
                                String currInst = "";
                                //Split by space
				String[] split = line.split("\\s+");
                                
                                //iterate over splits
                                for(int i = 0; i<split.length;i++){
                                    
                                    //Check if it's the start of the program
                                    if(split[i].equals("START")){
                                        tempT="";
                                        String progName="";
                                        //Format the program name coorectly so that it's equal to 6 chars
                                        if(split[i-1] != null){
                                        if(split[i-1].length()>6){
                                            progName = String.format("%."+ 6 +"s", split[i-1]);
                                        }
                                        else{
                                            progName = split[i-1];
                                            for(int j = 0; j<(6-split[i-1].length());j++){
                                                progName+="X";
                                            }
                                        
                                        }
                                        //format the starting address 
                                        
                                        if(split[i+1] != null){
                                            if(split[i+1].length()>6){
                                            startAdr = String.format("%."+ 6 +"s", split[i+1]);
                                        }
                                         else{
                                            for(int j = 0; j<(6-split[i+1].length());j++){
                                                startAdr+="0";
                                            }
                                            startAdr+=split[i+1];
                                            globalAdr = Integer.parseInt(startAdr);
                                            bodyReady = true;
                                            
                                            
                                        }
                                        }
                                        h += ". "+progName + " . " + startAdr;
                                       
                                        break;
                                    }
                                    }
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    
                                    //Check if it's the end of the program
                                    else if(split[i].equals("END")){
                                        bodyReady = false;
                                        
                                        E +=". " +startAdr;
                                        
                                        break;
                                    }
                                    
                                    
                                    
                                    
                                    //Work on the T record
                                    if(bodyReady){
                                                                               
                                        //starting address handling
                                        if(startingBlockAddress == 0 && bodyReady){
                                         startingBlockAddress = globalAdr;
                                         tempT = "T . " + startingBlockAddress + " . ";
                                        }
                                        //address this iteration
                                        
                                        
                                        
                                        
                                        
                                            
                                                                                        
                                                
                                                
                                                if(!(itop.getOPCode(split[i]).equals("none"))){
                                                    
                                                    
                                                    
                                                    
                                                    //========================== Need Martin's part here ==========================================
                                                    String addressTo=""; //The address from the symbol table from pass 1           
                                                OPCodeList+= " . " + itop.getOPCode(split[i]) + addressTo;
                                                blockSize+= 3;
                                                if(blockSize<=15)
                                                    hexBlockSize="0" + Integer.toHexString(blockSize);
                                                
                                                else
                                                hexBlockSize = Integer.toHexString(blockSize) + "";
                                                
                                                progLen +=3;
                                                globalAdr+=3;
                                                if(blockSize>=30){
                                                    tempT += hexBlockSize + OPCodeList;
                                                    t += tempT + "\n";
                                                    OPCodeList = "";
                                                    startingBlockAddress = globalAdr;
                                                    tempT = "T . " + startingBlockAddress + " . " ;
                                                    blockSize = 0; 
                                                    }
                                                }
                                            
                                        
                                        
                                        
                                    }
                                }                                
				line = reader.readLine();
			}
                        tempT += hexBlockSize + OPCodeList;
                        t+=tempT;
                        h+= " . " + progLen;
                        System.err.println(h);
                        System.err.println(t);
                        System.err.println(E);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
}
