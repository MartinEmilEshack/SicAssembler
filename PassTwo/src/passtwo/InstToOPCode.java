
package passtwo;


public class InstToOPCode {
    public String[] validInstructions = {"ADD", "AND","COMP","DIV","J","JEQ","JGT","JLT","JSUB","LDA","LDCH","LDL","LDX","MUL","OR","RD",
    "RSUB","STA","STCH","STL","STSW","STX","SUB","TD","TIX","WD"};
    public String[] correspondingOPCode = {"18","40","28","24","3C","30","34","38","48","00","50","08","04","20","44","D8","4C","0C","54","14","E8","10","1C",
        "E0","2C","DC"};
    
    public String getOPCode(String inst){
        String ret = "none";
    for(int i = 0; i<validInstructions.length; i++){
    if(inst.equals(validInstructions[i])){
    ret = correspondingOPCode[i];
    break;
    }
    }
    return ret;
    } 
}
