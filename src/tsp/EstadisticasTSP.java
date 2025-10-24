package tsp;

import ga.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase EstadisticasTSP
 * Recopila estadisticas de multiples ejecuciones del GA para TSP
 * y genera archivos CSV para analisis comparativo
 * 
 * @author Ian Saucedo
 * @version 2025.10
 */
public class EstadisticasTSP {

  private static class ResultadoEjecucion {
    int ejecucion;
    String algoritmo;
    double costeMinimo;
    double costeMedio;
    double costemMaximo;
    double desviacionEstandar;
    long tiempoEjecucion;
    int iteraciones;

    public ResultadoEjecucion(int ejecucion, String algoritmo, double costeMinimo,
        double costeMedio, double costemMaximo, double desviacionEstandar,
        long tiempoEjecucion, int iteraciones) {
      this.ejecucion = ejecucion;
      this.algoritmo = algoritmo;
      this.costeMinimo = costeMinimo;
      this.costeMedio = costeMedio;
      this.costemMaximo = costemMaximo;
      this.desviacionEstandar = desviacionEstandar;
      this.tiempoEjecucion = tiempoEjecucion;
      this.iteraciones = iteraciones;
    }

    public String toCSV() {
      return String.format("%d,%s,%.2f,%.2f,%.2f,%.2f,%d,%d",
          ejecucion, algoritmo, costeMinimo, costeMedio, costemMaximo,
          desviacionEstandar, tiempoEjecucion, iteraciones);
    }
  }

  public static void main(String[] args) {
    // CONFIGURACION
    String archivoProblema = "gr17.tsp.txt";
    int numEjecuciones = 10; // Numero de ejecuciones por algoritmo
    int maxIter = 1000;
    double pc = 0.9;
    double pm = 0.4;
    int tamPob = 10000;

    System.out.println("=== RECOPILACION DE ESTADISTICAS TSP ===");
    System.out.println("Problema: " + archivoProblema);
    System.out.println("Ejecuciones por algoritmo: " + numEjecuciones);
    System.out.println("Parametros: maxIter=" + maxIter + ", pc=" + pc + ", pm=" + pm + ", tamPob=" + tamPob);
    System.out.println();

    ProblemaTSP prob = new ProblemaTSP(archivoProblema);

    List<ResultadoEjecucion> resultados = new ArrayList<>();

    // EJECUTAR GA SIN 2-OPT
    System.out.println("--- Ejecutando GA sin 2-opt ---");
    for (int i = 1; i <= numEjecuciones; i++) {
      System.out.println("Ejecucion " + i + "/" + numEjecuciones);
      ResultadoEjecucion resultado = ejecutarGASin2Opt(i, prob, maxIter, pc, pm, tamPob);
      resultados.add(resultado);
      System.out.println("  Coste: " + resultado.costeMinimo + ", Tiempo: " + resultado.tiempoEjecucion + "ms");
    }

    System.out.println();

    // EJECUTAR GA CON 2-OPT
    System.out.println("--- Ejecutando GA con 2-opt ---");
    for (int i = 1; i <= numEjecuciones; i++) {
      System.out.println("Ejecucion " + i + "/" + numEjecuciones);
      ResultadoEjecucion resultado = ejecutarGACon2Opt(i, prob, maxIter, pc, pm, tamPob);
      resultados.add(resultado);
      System.out.println("  Coste: " + resultado.costeMinimo + ", Tiempo: " + resultado.tiempoEjecucion + "ms");
    }

    // GUARDAR RESULTADOS EN CSV
    String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String nombreArchivo = "estadisticas_tsp_" + timestamp + ".csv";

    try {
      guardarCSV(nombreArchivo, resultados);
      System.out.println();
      System.out.println("Resultados guardados en: " + nombreArchivo);

      // MOSTRAR RESUMEN
      mostrarResumen(resultados, numEjecuciones);

    } catch (IOException e) {
      System.err.println("Error al guardar archivo CSV: " + e.getMessage());
    }
  }

  private static ResultadoEjecucion ejecutarGASin2Opt(int numEjecucion, ProblemaTSP prob,
      int maxIter, double pc, double pm, int tamPob) {

    long inicioTiempo = System.currentTimeMillis();

    // OPERADORES
    OpGeneracion<Integer> opGen = new OpGenRandNoRep<>(prob.getAlfabeto(), prob.getNumCiudades());
    OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<>();
    OpMutacion<Integer> opMut = new OpMutacionSwap<>();
    OpSeleccion<Integer> opSel = new OpSelRandom<>();
    OpDecodificacion<Integer> opDecod = new OpDecodTSP(prob);
    OpReemplazo<Integer> opReemp = new OpReempGenElitista<>();

    // ALGORITMO GENETICO
    AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<>(
        prob.getNumCiudades(), prob.getAlfabeto(), pc, pm, tamPob);

    Individuo<Integer> cromoSol = ga.lanzaGA(opGen, maxIter, opCruce, opMut, opSel, opDecod, opReemp);
    Solucion solucion = opDecod.apply(cromoSol);

    long finTiempo = System.currentTimeMillis();
    long tiempoTotal = finTiempo - inicioTiempo;

    double coste = ((SolucionTSP) solucion).getCoste();

    return new ResultadoEjecucion(numEjecucion, "GA_sin_2opt", coste, coste, coste, 0.0, tiempoTotal, maxIter);
  }

  private static ResultadoEjecucion ejecutarGACon2Opt(int numEjecucion, ProblemaTSP prob,
      int maxIter, double pc, double pm, int tamPob) {

    long inicioTiempo = System.currentTimeMillis();

    // OPERADORES CON 2-OPT
    BusquedaLocal2Opt busqueda2Opt = new BusquedaLocal2Opt(prob);
    OpGeneracion<Integer> opGen = new OpGenRandNoRepCon2Opt<>(
        prob.getAlfabeto(), prob.getNumCiudades(), busqueda2Opt, 1.0);
    OpCruce<Integer> opCruce = new OpCruce1PuntoNoRep<>();
    OpMutacion<Integer> opMut = new OpMutacionSwap<>();
    OpSeleccion<Integer> opSel = new OpSelRandom<>();
    OpDecodificacion<Integer> opDecod = new OpDecodTSP(prob);
    OpReemplazo<Integer> opReemp = new OpReempGenElitista<>();

    // ALGORITMO GENETICO
    AlgoritmoGenetico<Integer> ga = new AlgoritmoGenetico<>(
        prob.getNumCiudades(), prob.getAlfabeto(), pc, pm, tamPob);

    Individuo<Integer> cromoSol = ga.lanzaGA(opGen, maxIter, opCruce, opMut, opSel, opDecod, opReemp);
    Solucion solucion = opDecod.apply(cromoSol);

    long finTiempo = System.currentTimeMillis();
    long tiempoTotal = finTiempo - inicioTiempo;

    double coste = ((SolucionTSP) solucion).getCoste();

    return new ResultadoEjecucion(numEjecucion, "GA_con_2opt", coste, coste, coste, 0.0, tiempoTotal, maxIter);
  }

  private static void guardarCSV(String nombreArchivo, List<ResultadoEjecucion> resultados) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
      // Encabezado
      writer.println(
          "Ejecucion,Algoritmo,Coste_Minimo,Coste_Medio,Coste_Maximo,Desviacion_Estandar,Tiempo_ms,Iteraciones");

      // Datos
      for (ResultadoEjecucion resultado : resultados) {
        writer.println(resultado.toCSV());
      }
    }
  }

  private static void mostrarResumen(List<ResultadoEjecucion> resultados, int numEjecuciones) {
    double sumaCosteSin2Opt = 0;
    double sumaTiempoSin2Opt = 0;
    double minCosteSin2Opt = Double.MAX_VALUE;
    double maxCosteSin2Opt = 0;

    double sumaCosteCon2Opt = 0;
    double sumaTiempoCon2Opt = 0;
    double minCosteCon2Opt = Double.MAX_VALUE;
    double maxCosteCon2Opt = 0;

    for (ResultadoEjecucion r : resultados) {
      if (r.algoritmo.equals("GA_sin_2opt")) {
        sumaCosteSin2Opt += r.costeMinimo;
        sumaTiempoSin2Opt += r.tiempoEjecucion;
        minCosteSin2Opt = Math.min(minCosteSin2Opt, r.costeMinimo);
        maxCosteSin2Opt = Math.max(maxCosteSin2Opt, r.costeMinimo);
      } else {
        sumaCosteCon2Opt += r.costeMinimo;
        sumaTiempoCon2Opt += r.tiempoEjecucion;
        minCosteCon2Opt = Math.min(minCosteCon2Opt, r.costeMinimo);
        maxCosteCon2Opt = Math.max(maxCosteCon2Opt, r.costeMinimo);
      }
    }

    double promedioCosteSin2Opt = sumaCosteSin2Opt / numEjecuciones;
    double promedioTiempoSin2Opt = sumaTiempoSin2Opt / numEjecuciones;
    double promedioCosteCon2Opt = sumaCosteCon2Opt / numEjecuciones;
    double promedioTiempoCon2Opt = sumaTiempoCon2Opt / numEjecuciones;

    System.out.println();
    System.out.println("=== RESUMEN ESTADISTICO ===");
    System.out.println();
    System.out.println("GA SIN 2-OPT:");
    System.out.printf("  Coste promedio: %.2f\n", promedioCosteSin2Opt);
    System.out.printf("  Coste minimo: %.2f\n", minCosteSin2Opt);
    System.out.printf("  Coste maximo: %.2f\n", maxCosteSin2Opt);
    System.out.printf("  Tiempo promedio: %.2f ms\n", promedioTiempoSin2Opt);
    System.out.println();
    System.out.println("GA CON 2-OPT:");
    System.out.printf("  Coste promedio: %.2f\n", promedioCosteCon2Opt);
    System.out.printf("  Coste minimo: %.2f\n", minCosteCon2Opt);
    System.out.printf("  Coste maximo: %.2f\n", maxCosteCon2Opt);
    System.out.printf("  Tiempo promedio: %.2f ms\n", promedioTiempoCon2Opt);
    System.out.println();
    System.out.println("MEJORA CON 2-OPT:");
    double mejoraPorcentaje = ((promedioCosteSin2Opt - promedioCosteCon2Opt) / promedioCosteSin2Opt) * 100;
    System.out.printf("  Reduccion de coste: %.2f%%\n", mejoraPorcentaje);
    double incrementoTiempo = ((promedioTiempoCon2Opt - promedioTiempoSin2Opt) / promedioTiempoSin2Opt) * 100;
    System.out.printf("  Incremento de tiempo: %.2f%%\n", incrementoTiempo);
  }
}
