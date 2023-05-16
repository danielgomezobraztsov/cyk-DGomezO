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
            throw new CYKAlgorithmException();
        else{
            if(noTerminales.contains(nonterminal))
                throw new CYKAlgorithmException();
            else{
                noTerminales.add(nonterminal);
                producciones.put(nonterminal,new HashSet<>());
            }
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
        if(Character.isLowerCase(terminal)){
            if(terminales.contains(terminal)){
                throw new CYKAlgorithmException();
            }
            else
                terminales.add(terminal);
        }
        else
            throw new CYKAlgorithmException();
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
            throw new CYKAlgorithmException();
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
                    if((nonterminal != 'S')&& (noTer1=='S')||(noTer2=='S')){
                        throw new CYKAlgorithmException();
                    }
                    else{    
                    s.add(production);
                    producciones.put(nonterminal, s);
                    }
                }
                else
                    throw new CYKAlgorithmException();
            }
            else
                throw new CYKAlgorithmException();
            //producciones.put(nonterminal, new HashSet<>()); creo que no va aqui
        }else if(production.length()==1){
            char ter = production.charAt(0);
            
            if(terminales.contains(ter)){
                if(noTerminales.contains(nonterminal)){
                    //pr1.add(production);
                    s.add(production);
                    producciones.put(nonterminal, s);
                }
                else
                    throw new CYKAlgorithmException();
            }
            else
                throw new CYKAlgorithmException();
        }else
            throw new CYKAlgorithmException();
        //pr1.clear();
        
        
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
        int n = word.length();
        Set<String>[][] matriz = new Set[n][n];
        String[] entrada = word.split("");
        
        for(int p=0;p<n;p++){
            if(terminales.contains(word.charAt(p))){
                if(word==word.toLowerCase()){
                    for (int i=0;i<n;i++){
                        Set<String> simbolos = new HashSet<>();
                        for (Map.Entry<Character, HashSet<String>> entry : producciones.entrySet()) {
                            String noT = entry.getKey().toString();
                            Set<String> t = entry.getValue();
                            if (t.contains(entrada[i]))
                                simbolos.add(noT);
                        }
                        matriz[i][0] = simbolos;
                    }
                    for (int j = 1; j < n; j++) {
                        for (int i = 0; i < n - j; i++) {
                            Set<String> simbolos = new HashSet<>();
                            for (int k = 0; k < j; k++) {
                                Set<String> arriba = matriz[i][k];
                                Set<String> diagonal = matriz[i + k + 1][j - k - 1];
                                for (String arr : arriba) {
                                    for (String dia : diagonal) {
                                        String combinacion = arr + dia;
                                        if (producciones.containsKey(combinacion)) {
                                            simbolos.addAll(producciones.get(combinacion));
                                        }
                                    }
                                }
                            }
                            matriz[i][j] = simbolos;
                        }
                    }
                }
                else
                    throw new CYKAlgorithmException();    
            }
            else
                throw new CYKAlgorithmException();
        }
        Set<String> s = matriz[0][n-1];
        if(s.contains(axioma))
            return true;
        else
            return false;
        
//        for (int i=0;i<n;i++){
//            Set<String> simbolos = new HashSet<>();
//            for (Map.Entry<Character, HashSet<String>> entry : producciones.entrySet()) {
//                String noT = entry.getKey().toString();
//                Set<String> t = entry.getValue();
//                if (t.contains(entrada[i]))
//                    simbolos.add(noT);
//            }
//            matriz[i][0] = simbolos;
//        }
//        for (int j = 1; j < n; j++) {
//            for (int i = 0; i < n - j; i++) {
//                Set<String> simbolos = new HashSet<>();
//                for (int k = 0; k < j; k++) {
//                    Set<String> arriba = matriz[i][k];
//                    Set<String> diagonal = matriz[i + k + 1][j - k - 1];
//                    for (String arr : arriba) {
//                        for (String dia : diagonal) {
//                            String combinacion = arr + dia;
//                            if (producciones.containsKey(combinacion)) {
//                                simbolos.addAll(producciones.get(combinacion));
//                            }
//                        }
//                    }
//                }
//                matriz[i][j] = simbolos;
//            }
//        }
//        Set<String> s = matriz[0][n-1];
//        if(s.contains(axioma))
//            return true;
//        else
//            return false;
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
//        StringBuilder sb = new StringBuilder();
//        for (Character c: producciones.keySet()){
//            sb.append(c.toString()).append("::=");
//            HashSet<String> hs = producciones.get(c);
//            for(String ch:hs){
//                sb.append(ch.toString()).append("|");
//            }
//            sb.append("\n");
//        }
//        return sb.toString();
        
        StringBuilder sb = new StringBuilder();
        /*for (Character c: producciones.keySet()){
            HashSet<String> hs = producciones.get(nonterminal);
            sb.append(nonterminal).append("::=");
            for(String ch:hs){
                sb.append(ch).append("|");
            }
        }
        return sb.toString();*/
        
        HashSet<String> pr = producciones.get(nonterminal);
        sb.append(nonterminal).append("::=");
        for(String ch : pr){
            sb.append(ch);
            if(sb.indexOf("|") == -1)
                sb.append("|");
        }
        if(pr.isEmpty()){
            pr.add("");
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
