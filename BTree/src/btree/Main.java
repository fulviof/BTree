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
public class Main 
{
    
    public static void main(String[] args) 
    {
        BTree arvre = new BTree();
        
        arvre.inserir(20, 0);
        
        arvre.inserir(30, 0);
        
        arvre.inserir(10, 0);       
        
        arvre.inserir(5, 0);
        
        arvre.inserir(15, 0);
        
        arvre.inserir(19, 0);
        
        arvre.inserir(21, 0);
        
        arvre.inserir(1, 0);
        
        arvre.inserir(2, 0);
        
        arvre.inserir(3, 0);
        
        arvre.inserir(4, 0);
        
        arvre.inserir(7, 0);
        
        
//for(int i=1;i <= 17;i++)
  //  arvre.inserir(i, 0);
        
        arvre.inOrdem(arvre.getRaiz());
        
        arvre.excluir(30, 0);
        
        arvre.excluir(1, 0);
        
        arvre.excluir(5, 0);
        
        arvre.excluir(19, 0);
        
        arvre.inserir(19, 0);

       
//arvre.excluir(1, 0);
//arvre.excluir(2, 0);
//arvre.excluir(3, 0);
//arvre.excluir(4, 0);
//arvre.excluir(9, 0);
        System.out.println("-----------------------");
        
        arvre.inOrdem(arvre.getRaiz());
    }
}
