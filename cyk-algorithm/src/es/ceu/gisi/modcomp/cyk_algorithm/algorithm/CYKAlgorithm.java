package es.ceu.gisi.modcomp.cyk_algorithm.algorithm;
import java.util.*;
import java.lang.*;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.exceptions.CYKAlgorithmException;
import es.ceu.gisi.modcomp.cyk_algorithm.algorithm.interfaces.CYKAlgorithmInterface;

/**
 * Esta clase contiene la implementación de la interfaz CYKAlgorithmInterface
 * que establece los métodos necesarios para el correcto funcionamiento del
 * proyecto de programación de la asignatura Modelos de Computación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class CYKAlgorithm implements CYKAlgorithmInterface {

    public HashSet<Character> terminales = new HashSet<>();
    public HashSet<Character> noTerminales = new HashSet<>();
    public HashMap<Character, HashSet<String>> producciones = new HashMap<>();
    //public HashMap<Character, String> producciones = new HashMap<>();
    public Character axioma = ' ';
    public HashSet<String> pr1 = new HashSet<>();

    @Override
    /**
     * Método que añade los elementos no terminales de la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento no es una letra mayúscula.
     */
    public void addNonTerminal(char nonterminal) throws CYKAlgorithmException {
        if(!Character.isUpperCase(nonterminal))
            throw new UnsupportedOperationException("Not supported yet.");
        else{
            noTerminales.add(nonterminal);
            producciones.put(nonterminal,new HashSet<>());
        }
    }

    @Override
    /**
     * Método que añade los elementos terminales de la gramática.
     *
     * @param terminal Por ejemplo, 'a'
     * @throws CYKAlgorithmException Si el elemento no es una letra minúscula.
     */
    public void addTerminal(char terminal) throws CYKAlgorithmException {
        if(Character.isUpperCase(terminal))
            throw new UnsupportedOperationException("Not supported yet.");
        else
            terminales.add(terminal);
    }

    @Override
    /**
     * Método que indica, de los elementos no terminales, cuál es el axioma de
     * la gramática.
     *
     * @param nonterminal Por ejemplo, 'S'
     * @throws CYKAlgorithmException Si el elemento insertado no forma parte del
     * conjunto de elementos no terminales.
     */
    public void setStartSymbol(char nonterminal) throws CYKAlgorithmException {
        if(!noTerminales.contains(nonterminal))
            throw new UnsupportedOperationException("Not supported yet.");
        axioma = nonterminal;
    }

    @Override
    
    
    
    /**
     * Método utilizado para construir la gramática. Admite producciones en FNC,
     * es decir de tipo A::=BC o A::=a
     *
     * @param nonterminal A
     * @param production "BC" o "a"
     * @throws CYKAlgorithmException Si la producción no se ajusta a FNC o está
     * compuesta por elementos (terminales o no terminales) no definidos
     * previamente.
     */
    public void addProduction(char nonterminal, String production) throws CYKAlgorithmException {
        //HashSet<String> pr1 = new HashSet<>();
        //HashSet<String> pr2 = new HashSet<>();
        HashSet<String> s = producciones.get(nonterminal);
        if(production.length()== 2){
            //String dividido[]= production.split("", 2);
            char noTer1 = production.charAt(0);
            char noTer2 = production.charAt(1);
            if (noTerminales.contains(noTer1) && noTerminales.contains(noTer2)){
                if(noTerminales.contains(nonterminal)){
                    //pr1.add(production);
                    //pr1.add(noTer2);
                    s.add(production);
                }
            }
            //producciones.put(nonterminal, new HashSet<>()); creo que no va aqui
        }else if(production.length()==1){
            char ter = production.charAt(0);
            
            if(terminales.contains(ter)){
                
                //pr1.add(production);
                s.add(production);
            }
        }else
            throw new UnsupportedOperationException("Not supported yet.");
        pr1.clear();
        
        
       //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Método que indica si una palabra pertenece al lenguaje generado por la
     * gramática que se ha introducido.
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return TRUE si la palabra pertenece, FALSE en caso contrario
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public boolean isDerived(String word) throws CYKAlgorithmException {
        /*int n = word.length();
        String[][] matriz = new String[n][n];
        
        for(int i=0;i<n;i++){
            for(int j=0;j<=n;j++){
                matriz[n][j]="";
                matriz[j][n-i]="";
            }
        }
        
        
        return false;
    }

    @Override
    /**
     * Método que, para una palabra, devuelve un String que contiene todas las
     * celdas calculadas por el algoritmo (la visualización debe ser similar al
     * ejemplo proporcionado en el PDF que contiene el paso a paso del
     * algoritmo).
     *
     * @param word La palabra a verificar, tiene que estar formada sólo por
     * elementos no terminales.
     * @return Un String donde se vea la tabla calculada de manera completa,
     * todas las celdas que ha calculado el algoritmo.
     * @throws CYKAlgorithmException Si la palabra proporcionada no está formada
     * sólo por terminales, si está formada por terminales que no pertenecen al
     * conjunto de terminales definido para la gramática introducida, si la
     * gramática es vacía o si el autómata carece de axioma.
     */
    public String algorithmStateToString(String word) throws CYKAlgorithmException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    /**
     * Elimina todos los elementos que se han introducido hasta el momento en la
     * gramática (elementos terminales, no terminales, axioma y producciones),
     * dejando el algoritmo listo para volver a insertar una gramática nueva.
     */
    public void removeGrammar() {
        producciones.clear();
        noTerminales.clear();
        terminales.clear();
        axioma = ' ';
    }

    @Override
    /**
     * Devuelve un String que representa todas las producciones que han sido
     * agregadas a un elemento no terminal.
     *
     * @param nonterminal
     * @return Devuelve un String donde se indica, el elemento no terminal, el
     * símbolo de producción "::=" y las producciones agregadas separadas, única
     * y exclusivamente por una barra '|' (no incluya ningún espacio). Por
     * ejemplo, si se piden las producciones del elemento 'S', el String de
     * salida podría ser: "S::=AB|BC".
     */
    public String getProductions(char nonterminal) {
        StringBuilder sb = new StringBuilder();
        for (Character c: producciones.keySet()){
            sb.append(c.toString()).append("::=");
            HashSet<String> hs = producciones.get(c);
            for(String ch:hs){
                sb.append(ch.toString()).append("|");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    /**
     * Devuelve un String con la gramática completa.
     *
     * @return Devuelve el agregado de hacer getProductions sobre todos los
     * elementos no terminales.
     */
    public String getGrammar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
