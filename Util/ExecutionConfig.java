package Util;

//Clase que almacena los parámetros de configuración elegidos por el usuario, para posteeriormente usarse en la creación del .json
public class ExecutionConfig {
    private final String archivo;
    private final int N;
    private final int G;
    private final int C;
    private final double mutProb;
    private final double crossProb;
    private final String seleccion;
    private final String cruce;
    private final String mutacion;
    private final String supervivientes;
    private final Double porcentajeNN;
    private final Integer tamanioTorneo;
    private final Integer numReemplazoSteadyState;
    private final Integer elite;

    public ExecutionConfig(String archivo, int N, int G, int C, double mutProb, double crossProb,
                           String seleccion, String cruce, String mutacion, String supervivientes,
                           Double porcentajeNN, Integer tamanioTorneo,
                           Integer numReemplazoSteadyState, Integer elite) {
        this.archivo = archivo;
        this.N = N;
        this.G = G;
        this.C = C;
        this.mutProb = mutProb;
        this.crossProb = crossProb;
        this.seleccion = seleccion;
        this.cruce = cruce;
        this.mutacion = mutacion;
        this.supervivientes = supervivientes;
        this.porcentajeNN = porcentajeNN;
        this.tamanioTorneo = tamanioTorneo;
        this.numReemplazoSteadyState = numReemplazoSteadyState;
        this.elite = elite;
    }

    public String getFile() { return archivo; }
    public int getN() { return N; }
    public int getG() { return G; }
    public int getC() { return C; }
    public double getMutProb() { return mutProb; }
    public double getCrossProb() { return crossProb; }
    public String getSelectionOperator() { return seleccion; }
    public String getCrossOperator() { return cruce; }
    public String getMutationOperator() { return mutacion; }
    public String getSurvivorsOperator() { return supervivientes; }
    public Double getNNpercentage() { return porcentajeNN; }
    public Integer getTournamentSize() { return tamanioTorneo; }
    public Integer getSteadyStateNumber() { return numReemplazoSteadyState; }
    public Integer getEliteNumber() { return elite; }
}

