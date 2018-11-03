/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btree;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fulviofanelli
 */
public class BTree implements Constantes
{
    No raiz;
    
    public BTree()
    {
        raiz = new No();
    }

    public No getRaiz() 
    {
        return raiz;
    } 
    
    public No navegarAteFolha(int info)
    {
        int i;
        No p = raiz;
        while(p.getvLig(0) != null)
        {
            i = 0;
            while(i < p.getTL() && info > p.getvInfo(i))
            {
                i = i+1;
            }
            p = p.getvLig(i);
        }
        return p;
    }
    
    public No localizarPai(No folha, int info)
    {
        No p, pai;
        int i;
        p = raiz;
        pai = p;
        while(p != folha)
        {
            i = 0;
            while(i < p.getTL() && info > p.getvInfo(i))
                i++;
            pai = p;
            p = p.getvLig(i);
        }
        return pai;
    }
    
    public void split(No folha, No pai, int info)
    {
        No cx1, cx2;
        int i, pos;
        
        cx1 = new No();
        cx2 = new No();
        for (i = 0; i < N; i++) 
        {
            cx1.setvInfo(folha.getvInfo(i), i);
            cx1.setvPos(folha.getvPos(i), i);
            cx1.setvLig(folha.getvLig(i), i);
        }
        cx1.setvLig(folha.getvLig(N), N);
        cx1.setTL(N);

        for (i = N+1;  i < 2*N+1; i++) 
        {
            cx2.setvInfo(folha.getvInfo(i), i-(N+1));
            cx2.setvPos(folha.getvPos(i), i-(N+1));
            cx2.setvLig(folha.getvLig(i), i-(N+1));
        }
        cx2.setvLig(folha.getvLig(2*N+1), N+1);
        cx2.setTL(N);
        
        if(pai == folha)
        {
            folha.setvInfo(folha.getvInfo(N), 0);
            folha.setvPos(folha.getvPos(N), 0);
            folha.setvLig(cx1, 0);
            folha.setvLig(cx2, 1);
            folha.setTL(1);
        }
        else
        {
            info = folha.getvInfo(N);
            pos = pai.buscar(info);
            pai.remanejar(pos);
            pai.setTL(pai.getTL()+1);
            pai.setvInfo(folha.getvInfo(N), pos);
            pai.setvPos(folha.getvPos(N), pos);
            pai.setvLig(cx1, pos);
            pai.setvLig(cx2, pos+1);
            if(pai.getTL() > 2*N)
            {
                folha = pai;
                info = folha.getvInfo(N);
                pai = localizarPai(pai, info);
                split(folha, pai, info);
            }
        }
    }
    
    public void inserir(int info, int posArq)
    {
       No folha, pai;
       int i, pos;
       
       if(raiz == null)
           raiz = new No(info, posArq);
       else
       {
           folha = navegarAteFolha(info);
           pos = folha.buscar(info);
           folha.remanejar(pos);
           folha.setTL(folha.getTL()+1);
           folha.setvInfo(info, pos);
           folha.setvPos(posArq, pos);
           if(folha.getTL() > 2*N)
           {
               pai = localizarPai(folha, info);
               split(folha, pai, info);
           }
       }
    }
    
    public void excluir(int info, int posArq)
    {   
        Aux valor = new Aux();
        No auxNo;
        int infoAux;
        buscar(raiz, info, valor);
        if(valor.getI() == -1)
            System.out.println("Nao existe!");
        else
        {            
            buscarNo(raiz, info, valor);
            No aux = valor.getJ();
            int i = aux.buscar(info);
            if(aux.isFolha())
            {                  
                aux.remanejarEx(i);
                aux.setTL(aux.getTL()-1);
                if(aux.getTL() >= 2)
                    System.out.println("Fim!");
                else
                    mover(aux, info);
            }
            else
            {
                buscaSubstituto(aux, info, valor);               
                No subst = valor.getJ();
                No pai = localizarPai(subst, info);
                No viz = new No();
                aux.setvInfo(subst.getvInfo(subst.getTL()-1), i);
                subst.setTL(subst.getTL()-1);
                if(subst.getTL() < 2)
                {                   
                    int posPai = 0;

                    while(posPai < pai.getTL() && subst != pai.getvLig(posPai))
                        posPai++;

                    if(posPai > 0)
                    {
                        if(posPai == pai.getTL())
                        {
                           viz = pai.getvLig(posPai-1);
                        }
                        else
                        {
                            if(pai.getvLig(posPai-1).getTL() > pai.getvLig(posPai+1).getTL())
                                viz = pai.getvLig(posPai-1);
                            else
                                viz = pai.getvLig(posPai+1);
                        }
                    }
                    else
                    {
                        viz = pai.getvLig(posPai+1);
                    }
                    
                    if(viz.getTL() <= 2)
                    {
                        fusao(viz, pai, subst, posPai);
                        raiz = subst;
                        pai.setvInfo(0, posPai);
                        pai.setvLig(null, posPai);
                    }
                    else
                    {
                        redistribuicao(subst, pai, viz, posPai);
                    }
                }               
            }
        }
    }
    
    public void inOrdem(No raiz)
    {
        int i;
        if(raiz != null)
        {
            for (i = 0; i < raiz.getTL(); i++) 
            {
                inOrdem(raiz.getvLig(i));
                System.out.println(raiz.getvInfo(i)+" - ");
            }
            inOrdem(raiz.getvLig(i));
        }
    }
    
    public void buscar(No raiz, int info, Aux aux)
    {
        int i;
        if(raiz != null)
        {
            for (i = 0;  i < raiz.getTL(); i++) 
            {
                buscar(raiz.getvLig(i), info, aux);
                if(raiz.getvInfo(i) == info)
                    aux.setI(raiz.getvInfo(i));
            }
            buscar(raiz.getvLig(i), info, aux);
        }
    }
    
    public void buscarNo(No raiz, int info, Aux aux)
    {
       int i;
        if(raiz != null)
        {
            for (i = 0;  i < raiz.getTL(); i++) 
            {
                buscarNo(raiz.getvLig(i), info, aux);
                if(raiz.getvInfo(i) == info)
                    aux.setJ(raiz);
            }
            buscarNo(raiz.getvLig(i), info, aux);
        } 
    }
    
    public void mover(No aux, int info)
    {
        int infoPai;
        int infoAux = 0;
        No pai = localizarPai(aux, info);
        int posPai = 0;
        while(posPai < pai.getTL() && aux != pai.getvLig(posPai))
            posPai++;

        if(aux == pai.getvLig(posPai))
        {
            No vizinhoE = new No();
            No vizinhoD = new No();
            No redist = null;
            if(posPai > 0)
            {
                if(posPai == pai.getTL())
                {
                   vizinhoE = pai.getvLig(posPai-1);
                }
                else
                {
                    if(pai.getvLig(posPai-1).getTL() > pai.getvLig(posPai+1).getTL())
                        vizinhoE = pai.getvLig(posPai-1);
                    else
                        vizinhoD = pai.getvLig(posPai+1);
                }
            }
            else
            {
                vizinhoD = pai.getvLig(posPai+1);
            }

            if(vizinhoE.getTL() > 2)
            {   
                redist = vizinhoE;
                infoAux = redist.getvInfo(redist.getTL()-1);
            }
                
            else
            {
                if(vizinhoD.getTL() > 2)
                {
                    redist = vizinhoD;
                    infoAux = redist.getvInfo(0);
                }
            }
            if(redist == null)//fusao
            {
                if(posPai == 0)
                    fusao(pai.getvLig(posPai+1), pai, pai.getvLig(posPai), posPai);
                else
                    fusao(pai.getvLig(posPai), pai, pai.getvLig(posPai-1), posPai);
            }               
            else //redist
                redistribuicao(aux, pai, redist, posPai);
        }
    }
    
    public void buscaSubstituto(No no, int info, Aux aux)
    {
        int pos = no.buscar(info);
               
        No uax = no.getvLig(pos);
        
        while(uax.getvLig(uax.getTL()) != null)
            uax = uax.getvLig(uax.getTL());
        
        if(uax.getvLig(uax.getTL()) == null)
            aux.setJ(uax);
    }
    
    
    public void redistribuicao(No aux, No pai, No redist, int posPai)
    {
        int infoPai = pai.getvInfo(posPai);
        
        aux.setvInfo(infoPai, aux.getTL());
        aux.setTL(aux.getTL()+1);
        
        pai.setvInfo(redist.getvInfo(0), posPai);
        
        redist.remanejarEx(0);
        redist.setTL(redist.getTL()-1);
        
        if(redist.getTL() < 2)
        {
           fusao(redist, pai, aux, posPai);
        }
    }
    
    public void fusao(No aux, No pai, No fusao, int posPai)
    {
        int infoPai = pai.getvInfo(0);
        
        fusao.setvInfo(infoPai, fusao.getTL());
        fusao.setTL(fusao.getTL()+1);
        
        for(int i = 0; i< aux.getTL(); i++)
        {
            fusao.setvInfo(aux.getvInfo(i), fusao.getTL());
            fusao.setTL(fusao.getTL()+1);
        }       
        pai.remanejarEx(posPai);
        pai.setTL(pai.getTL()-1);
    }
}
