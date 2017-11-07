/*
Project     :  Biomedd Sum Shared Task
Programmer  :  Gizem Sogancioglu,  Arzucan Ozgur
Advisor     :  Arzucan Ozgur
Date        :  02.09.2014
*/
package tac_core;
/**
 *
 * @author gizem
 */
public enum FacetEnum {
    Hypothesis(1),Method(2),Discussion(3),Result(4), Implication(5), Introduction(6),Other(7);
    private int value;
    
    private FacetEnum(int value){
        this.value = value;
    }
    
    public int getVal(){
        //System.out.println(this.value);sssssss
        return this.value;
    }
}
