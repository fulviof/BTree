/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;

/**
 *
 * @author fulviofanelli
 */
public class No implements Constantes
{
    private int vInfo[];
    private int vPos[];
    private No vLig[];
    int TL;
    
    public No()
    {
       vInfo = new int[2*N+1];
       vPos = new int[2*N+1];
       vLig = new No[2*N+2];
       int TL = 0;
    }
    
    public No(int info, int posArq)
    {
       this();
       vInfo[TL] = info;
       vPos[TL++] = posArq;    
    }

    public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int info, int pos) {
        this.vInfo[pos] = info;
    }

    public int getvPos(int pos) {
        return vPos[pos];
    }

    public void setvPos(int info, int pos) {
        this.vPos[pos] = info;
    }

    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(No info, int pos) {
        this.vLig[pos] = info;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }
    
    public void remanejar(int pos)
    {
        vLig[TL+1] = vLig[TL];
        for (int j = TL; j>pos; j--) 
        {
            vInfo[j] = vInfo[j-1];
            vPos[j] = vPos[j-1];
            vLig[j] = vLig[j-1];
        }
    }
    
    public void remanejarEx(int pos)
    {
        vLig[TL] = vLig[TL-1];
        for (int j = pos; j<TL-1; j++) 
        {
            vInfo[j] = vInfo[j+1];
            vPos[j] = vPos[j+1];
            vLig[j] = vLig[j+1];
        }
    }
    
    public int buscar(int info)
    {
        int i = 0;
        
        while(i < TL && info > vInfo[i])
            i = i+1;
        
        return i;
    }
    
    public boolean isFolha()
    {
        return getvLig(0) == null;
    }

}
