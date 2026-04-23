package Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AllExecutionsEvolutionChart {

    // Genera un HTML con los gráficos de evolución (fitness y diversidad) de todas las ejecuciones
    public static void generateHTML(List<ExecutionResult> resultados, String filename, String problemName) throws IOException {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n<html>\n<head>\n");
        html.append("    <meta charset='UTF-8'>\n");
        html.append("    <script src='https://cdn.jsdelivr.net/npm/chart.js'></script>\n");
        html.append("    <title>Evoluciones - ").append(problemName).append("</title>\n");

        // Reutilizar estilos de EvolutionChart y agregar estilos adicionales
        html.append(EvolutionChart.getStyles());
        html.append("    <style>\n");
        html.append("        .execution-section { margin-bottom: 60px; padding: 30px; background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%); border-radius: 12px; }\n");
        html.append("        .execution-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 8px; margin-bottom: 30px; display: flex; justify-content: space-between; align-items: center; }\n");
        html.append("        .execution-title { font-size: 24px; font-weight: bold; }\n");
        html.append("        .execution-stats { display: flex; gap: 30px; }\n");
        html.append("        .stat-item { text-align: center; }\n");
        html.append("        .stat-item .stat-label { font-size: 12px; opacity: 0.9; }\n");
        html.append("        .stat-item .stat-value { font-size: 18px; font-weight: bold; margin-top: 5px; }\n");
        html.append("        .charts-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 30px; }\n");
        html.append("        .chart-container { position: relative; height: 400px; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }\n");
        html.append("        .best-badge { background: #28a745; color: white; padding: 5px 15px; border-radius: 20px; font-size: 14px; font-weight: bold; }\n");
        html.append("        @media (max-width: 1200px) { .charts-grid { grid-template-columns: 1fr; } }\n");
        html.append("    </style>\n");

        html.append("</head>\n<body>\n");
        html.append("<div class='container'>\n");
        html.append("    <h1> Evolución de todas las Ejecuciones - ").append(problemName).append("</h1>\n");

        // Encontrar la mejor ejecución
        int mejorIdx = 0;
        int mejorCosto = resultados.getFirst().getBestCost();
        for (int i = 1; i < resultados.size(); i++) {
            if (resultados.get(i).getBestCost() < mejorCosto) {
                mejorCosto = resultados.get(i).getBestCost();
                mejorIdx = i;
            }
        }

        // Generar sección para cada ejecución
        for (int i = 0; i < resultados.size(); i++) {
            ExecutionResult resultado = resultados.get(i);
            boolean esMejor = (i == mejorIdx);

            html.append("    <div class='execution-section'>\n");
            html.append("        <div class='execution-header'>\n");
            html.append("            <div class='execution-title'>Ejecución #").append(i + 1);
            if (esMejor) {
                html.append(" <span class='best-badge'> MEJOR RESULTADO</span>");
            }
            html.append("</div>\n");
            html.append("            <div class='execution-stats'>\n");
            html.append("                <div class='stat-item'>\n");
            html.append("                    <div class='stat-label'>Mejor Costo</div>\n");
            html.append("                    <div class='stat-value'>").append(resultado.getBestCost()).append("</div>\n");
            html.append("                </div>\n");
            html.append("                <div class='stat-item'>\n");
            html.append("                    <div class='stat-label'>Fitness Final</div>\n");
            html.append("                    <div class='stat-value'>").append(String.format("%.2e", resultado.getFinalBestFitness())).append("</div>\n");
            html.append("                </div>\n");
            html.append("                <div class='stat-item'>\n");
            html.append("                    <div class='stat-label'>Tiempo</div>\n");
            html.append("                    <div class='stat-value'>").append(resultado.getExecutionTime()).append(" ms</div>\n");
            html.append("                </div>\n");
            html.append("            </div>\n");
            html.append("        </div>\n");

            html.append("        <div class='charts-grid'>\n");
            html.append("            <div class='chart-container'>\n");
            html.append("                <canvas id='fitnessChart").append(i).append("'></canvas>\n");
            html.append("            </div>\n");
            html.append("            <div class='chart-container'>\n");
            html.append("                <canvas id='diversityChart").append(i).append("'></canvas>\n");
            html.append("            </div>\n");
            html.append("        </div>\n");
            html.append("    </div>\n");
        }

        // Sección de estadísticas globales
        html.append("    <div class='execution-section' style='background: linear-gradient(135deg, #e0f7fa 0%, #80deea 100%);'>\n");
        html.append("        <div class='execution-header' style='background: linear-gradient(135deg, #0097a7 0%, #00838f 100%);'>\n");
        html.append("            <div class='execution-title'> Estadísticas Globales de la configuración</div>\n");
        html.append("        </div>\n");

        // Calcular estadísticas
        double promedioFitness = ExecutionResult.calculateFitnessMean(resultados);
        double desviacionFitness = ExecutionResult.calculateStdFitness(resultados);
        double promedioCosto = ExecutionResult.calcularCostsMean(resultados);
        double desviacionCosto = ExecutionResult.calculateStdCosts(resultados);
        double promedioTiempo = ExecutionResult.calculateTimesMean(resultados);
        double desviacionTiempo = ExecutionResult.calculateStdTimes(resultados);

        // Tabla de estadísticas
        html.append("        <div style='background: white; padding: 20px; border-radius: 8px; margin-bottom: 30px;'>\n");
        html.append("            <h3 style='text-align: center; margin-bottom: 20px;'>Resumen Estadístico</h3>\n");
        html.append("            <table style='width: 100%; border-collapse: collapse;'>\n");
        html.append("                <thead>\n");
        html.append("                    <tr style='background: #0097a7; color: white;'>\n");
        html.append("                        <th style='padding: 12px; text-align: left;'>Métrica</th>\n");
        html.append("                        <th style='padding: 12px; text-align: center;'>Promedio</th>\n");
        html.append("                        <th style='padding: 12px; text-align: center;'>Desviación Estándar</th>\n");
        html.append("                    </tr>\n");
        html.append("                </thead>\n");
        html.append("                <tbody>\n");
        html.append("                    <tr style='border-bottom: 1px solid #ddd;'>\n");
        html.append("                        <td style='padding: 12px;'><strong>Fitness Final</strong></td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.6e", promedioFitness)).append("</td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.6e", desviacionFitness)).append("</td>\n");
        html.append("                    </tr>\n");
        html.append("                    <tr style='border-bottom: 1px solid #ddd;'>\n");
        html.append("                        <td style='padding: 12px;'><strong>Costo de la Solución</strong></td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.2f", promedioCosto)).append("</td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.2f", desviacionCosto)).append("</td>\n");
        html.append("                    </tr>\n");
        html.append("                    <tr>\n");
        html.append("                        <td style='padding: 12px;'><strong>Tiempo de Ejecución (ms)</strong></td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.2f", promedioTiempo)).append("</td>\n");
        html.append("                        <td style='padding: 12px; text-align: center;'>").append(String.format("%.2f", desviacionTiempo)).append("</td>\n");
        html.append("                    </tr>\n");
        html.append("                </tbody>\n");
        html.append("            </table>\n");
        html.append("        </div>\n");

        // Gráficos de distribución normal
        html.append("        <div class='charts-grid'>\n");
        html.append("            <div class='chart-container'>\n");
        html.append("                <canvas id='distributionCostChart'></canvas>\n");
        html.append("            </div>\n");
        html.append("            <div class='chart-container'>\n");
        html.append("                <canvas id='distributionTimeChart'></canvas>\n");
        html.append("            </div>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");

        html.append("</div>\n");
        html.append("<script>\n");

        // Reutilizar el método de EvolutionChart para cada ejecución
        for (int i = 0; i < resultados.size(); i++) {
            ExecutionResult resultado = resultados.get(i);

            // Crear métricas temporales para esta ejecución
            EvolutionMetrics metricas = new EvolutionMetrics();
            metricas.loadFitnessData(resultado.getFitnessEvolution());
            metricas.loadDiversityData(resultado.getDiversityEvolution());

            // Generar los scripts para las ejecuciones
            html.append("// Ejecución #").append(i + 1).append("\n");
            html.append(EvolutionChart.generateChartScript(metricas, String.valueOf(i)));
            html.append("\n");
        }

        // Generar scripts para los gráficos de las estadísticas
        html.append(generateStatisticsCharts(resultados));

        html.append("</script>\n");
        html.append("</body>\n</html>");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(html.toString());
        }
    }

    private static String generateStatisticsCharts(List<ExecutionResult> resultados) {
        StringBuilder script = new StringBuilder();

        // Calcular estadísticas
        double promedioCosto = ExecutionResult.calcularCostsMean(resultados);
        double desviacionCosto = ExecutionResult.calculateStdCosts(resultados);
        double promedioTiempo = ExecutionResult.calculateTimesMean(resultados);
        double desviacionTiempo = ExecutionResult.calculateStdTimes(resultados);

        // Obtener todos los costos y tiempos
        script.append("\n// Estadísticas de las ejecuciones\n");
        script.append("const costos = [");
        for (int i = 0; i < resultados.size(); i++) {
            script.append(resultados.get(i).getBestCost());
            if (i < resultados.size() - 1) script.append(", ");
        }
        script.append("];\n");

        script.append("const tiempos = [");
        for (int i = 0; i < resultados.size(); i++) {
            script.append(resultados.get(i).getExecutionTime());
            if (i < resultados.size() - 1) script.append(", ");
        }
        script.append("];\n\n");

        // Función para generar curva normal
        script.append("function generarCurvaNormal(media, desviacion, min, max, puntos = 100) {\n");
        script.append("    const datos = [];\n");
        script.append("    const paso = (max - min) / puntos;\n");
        script.append("    for (let x = min; x <= max; x += paso) {\n");
        script.append("        const exponente = -0.5 * Math.pow((x - media) / desviacion, 2);\n");
        script.append("        const y = (1 / (desviacion * Math.sqrt(2 * Math.PI))) * Math.exp(exponente);\n");
        script.append("        datos.push({x: x, y: y});\n");
        script.append("    }\n");
        script.append("    return datos;\n");
        script.append("}\n\n");

        // Gráfico de distribución de costos
        script.append("// Gráfico de distribución de costos\n");
        script.append("const minCosto = Math.min(...costos);\n");
        script.append("const maxCosto = Math.max(...costos);\n");
        script.append("const promedioCosto = ").append(promedioCosto).append(";\n");
        script.append("const desviacionCosto = ").append(desviacionCosto).append(";\n");
        script.append("const curvaNormalCosto = desviacionCosto > 0 ? generarCurvaNormal(promedioCosto, desviacionCosto, minCosto - desviacionCosto, maxCosto + desviacionCosto) : [];\n");
        script.append("const maxYCosto = curvaNormalCosto.length > 0 ? Math.max(...curvaNormalCosto.map(p => p.y)) : 1;\n\n");

        script.append("new Chart(document.getElementById('distributionCostChart'), {\n");
        script.append("    type: 'scatter',\n");
        script.append("    data: {\n");
        script.append("        datasets: (() => {\n");
        script.append("            const datasets = [];\n");
        script.append("            if (desviacionCosto > 0) {\n");
        script.append("                datasets.push({\n");
        script.append("                    label: 'Curva Normal (μ=' + promedioCosto.toFixed(2) + ', σ=' + desviacionCosto.toFixed(2) + ')',\n");
        script.append("                    data: curvaNormalCosto,\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgb(0, 151, 167)',\n");
        script.append("                    backgroundColor: 'rgba(0, 151, 167, 0.1)',\n");
        script.append("                    borderWidth: 3,\n");
        script.append("                    fill: true,\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    tension: 0.4,\n");
        script.append("                    order: 2\n");
        script.append("                });\n");
        script.append("            }\n");
        script.append("            datasets.push({\n");
        script.append("                label: 'Media (μ=' + promedioCosto.toFixed(2) + ')',\n");
        script.append("                data: [{x: promedioCosto, y: 0}, {x: promedioCosto, y: maxYCosto}],\n");
        script.append("                type: 'line',\n");
        script.append("                borderColor: 'rgb(0, 151, 167)',\n");
        script.append("                borderWidth: 2,\n");
        script.append("                borderDash: [5, 5],\n");
        script.append("                pointRadius: 0,\n");
        script.append("                order: 1\n");
        script.append("            });\n");
        script.append("            if (desviacionCosto > 0) {\n");
        script.append("                datasets.push({\n");
        script.append("                    label: '+1σ',\n");
        script.append("                    data: [{x: promedioCosto + desviacionCosto, y: 0}, {x: promedioCosto + desviacionCosto, y: maxYCosto}],\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgba(0, 151, 167, 0.5)',\n");
        script.append("                    borderWidth: 1,\n");
        script.append("                    borderDash: [3, 3],\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    order: 1\n");
        script.append("                });\n");
        script.append("                datasets.push({\n");
        script.append("                    label: '-1σ',\n");
        script.append("                    data: [{x: promedioCosto - desviacionCosto, y: 0}, {x: promedioCosto - desviacionCosto, y: maxYCosto}],\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgba(0, 151, 167, 0.5)',\n");
        script.append("                    borderWidth: 1,\n");
        script.append("                    borderDash: [3, 3],\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    order: 1\n");
        script.append("                });\n");
        script.append("            }\n");
        script.append("            datasets.push({\n");
        script.append("                label: 'Costos Observados',\n");
        script.append("                data: costos.map(c => ({x: c, y: 0})),\n");
        script.append("                backgroundColor: 'rgb(255, 99, 132)',\n");
        script.append("                pointRadius: 6,\n");
        script.append("                pointHoverRadius: 8,\n");
        script.append("                order: 0\n");
        script.append("            });\n");
        script.append("            return datasets;\n");
        script.append("        })()\n");
        script.append("    },\n");
        script.append("    options: {\n");
        script.append("        responsive: true,\n");
        script.append("        maintainAspectRatio: false,\n");
        script.append("        plugins: {\n");
        script.append("            title: {\n");
        script.append("                display: true,\n");
        script.append("                text: 'Distribución de Costos de las Soluciones',\n");
        script.append("                font: { size: 16, weight: 'bold' }\n");
        script.append("            },\n");
        script.append("            legend: { position: 'top' }\n");
        script.append("        },\n");
        script.append("        scales: {\n");
        script.append("            x: {\n");
        script.append("                title: { display: true, text: 'Costo de la Solución' },\n");
        script.append("                type: 'linear'\n");
        script.append("            },\n");
        script.append("            y: {\n");
        script.append("                title: { display: true, text: 'Densidad de Probabilidad' },\n");
        script.append("                beginAtZero: true\n");
        script.append("            }\n");
        script.append("        }\n");
        script.append("    }\n");
        script.append("});\n\n");

        // Gráfico de distribución de tiempos de ejecución
        script.append("// Gráfico de distribución de tiempos\n");
        script.append("const minTiempo = Math.min(...tiempos);\n");
        script.append("const maxTiempo = Math.max(...tiempos);\n");
        script.append("const promedioTiempo = ").append(promedioTiempo).append(";\n");
        script.append("const desviacionTiempo = ").append(desviacionTiempo).append(";\n");
        script.append("const curvaNormalTiempo = desviacionTiempo > 0 ? generarCurvaNormal(promedioTiempo, desviacionTiempo, minTiempo - desviacionTiempo, maxTiempo + desviacionTiempo) : [];\n");
        script.append("const maxYTiempo = curvaNormalTiempo.length > 0 ? Math.max(...curvaNormalTiempo.map(p => p.y)) : 1;\n\n");

        script.append("new Chart(document.getElementById('distributionTimeChart'), {\n");
        script.append("    type: 'scatter',\n");
        script.append("    data: {\n");
        script.append("        datasets: (() => {\n");
        script.append("            const datasets = [];\n");
        script.append("            if (desviacionTiempo > 0) {\n");
        script.append("                datasets.push({\n");
        script.append("                    label: 'Curva Normal (μ=' + promedioTiempo.toFixed(2) + ', σ=' + desviacionTiempo.toFixed(2) + ')',\n");
        script.append("                    data: curvaNormalTiempo,\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgb(0, 151, 167)',\n");
        script.append("                    backgroundColor: 'rgba(0, 151, 167, 0.1)',\n");
        script.append("                    borderWidth: 3,\n");
        script.append("                    fill: true,\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    tension: 0.4,\n");
        script.append("                    order: 2\n");
        script.append("                });\n");
        script.append("            }\n");
        script.append("            datasets.push({\n");
        script.append("                label: 'Media (μ=' + promedioTiempo.toFixed(2) + ' ms)',\n");
        script.append("                data: [{x: promedioTiempo, y: 0}, {x: promedioTiempo, y: maxYTiempo}],\n");
        script.append("                type: 'line',\n");
        script.append("                borderColor: 'rgb(0, 151, 167)',\n");
        script.append("                borderWidth: 2,\n");
        script.append("                borderDash: [5, 5],\n");
        script.append("                pointRadius: 0,\n");
        script.append("                order: 1\n");
        script.append("            });\n");
        script.append("            if (desviacionTiempo > 0) {\n");
        script.append("                datasets.push({\n");
        script.append("                    label: '+1σ',\n");
        script.append("                    data: [{x: promedioTiempo + desviacionTiempo, y: 0}, {x: promedioTiempo + desviacionTiempo, y: maxYTiempo}],\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgba(0, 151, 167, 0.5)',\n");
        script.append("                    borderWidth: 1,\n");
        script.append("                    borderDash: [3, 3],\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    order: 1\n");
        script.append("                });\n");
        script.append("                datasets.push({\n");
        script.append("                    label: '-1σ',\n");
        script.append("                    data: [{x: promedioTiempo - desviacionTiempo, y: 0}, {x: promedioTiempo - desviacionTiempo, y: maxYTiempo}],\n");
        script.append("                    type: 'line',\n");
        script.append("                    borderColor: 'rgba(0, 151, 167, 0.5)',\n");
        script.append("                    borderWidth: 1,\n");
        script.append("                    borderDash: [3, 3],\n");
        script.append("                    pointRadius: 0,\n");
        script.append("                    order: 1\n");
        script.append("                });\n");
        script.append("            }\n");
        script.append("            datasets.push({\n");
        script.append("                label: 'Tiempos Observados',\n");
        script.append("                data: tiempos.map(t => ({x: t, y: 0})),\n");
        script.append("                backgroundColor: 'rgb(255, 159, 64)',\n");
        script.append("                pointRadius: 6,\n");
        script.append("                pointHoverRadius: 8,\n");
        script.append("                order: 0\n");
        script.append("            });\n");
        script.append("            return datasets;\n");
        script.append("        })()\n");
        script.append("    },\n");
        script.append("    options: {\n");
        script.append("        responsive: true,\n");
        script.append("        maintainAspectRatio: false,\n");
        script.append("        plugins: {\n");
        script.append("            title: {\n");
        script.append("                display: true,\n");
        script.append("                text: 'Distribución de Tiempos de Ejecución',\n");
        script.append("                font: { size: 16, weight: 'bold' }\n");
        script.append("            },\n");
        script.append("            legend: { position: 'top' }\n");
        script.append("        },\n");
        script.append("        scales: {\n");
        script.append("            x: {\n");
        script.append("                title: { display: true, text: 'Tiempo de Ejecución (ms)' },\n");
        script.append("                type: 'linear'\n");
        script.append("            },\n");
        script.append("            y: {\n");
        script.append("                title: { display: true, text: 'Densidad de Probabilidad' },\n");
        script.append("                beginAtZero: true\n");
        script.append("            }\n");
        script.append("        }\n");
        script.append("    }\n");
        script.append("});\n");

        return script.toString();
    }
}

