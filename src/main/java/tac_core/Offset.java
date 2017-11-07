/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tac_core;

/**
 *
 * @author gizem
 */
public class Offset {
    
    private int off1;
    private int off2;

    public Offset(int off1, int off2){
        this.off1 = off1;
        this.off2 = off2;
    }
    /**
     * @return the off1
     */
    public int getOff1() {
        return off1;
    }

    /**
     * @param off1 the off1 to set
     */
    public void setOff1(int off1) {
        this.off1 = off1;
    }

    /**
     * @return the off2
     */
    public int getOff2() {
        return off2;
    }

    /**
     * @param off2 the off2 to set
     */
    public void setOff2(int off2) {
        this.off2 = off2;
    }
    
    
}
