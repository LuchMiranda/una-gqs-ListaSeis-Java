import java.util.Scanner;

/**
 * Resolve a equação geral do terceiro grau:
 *      a*x^3 + b*x^2 + c*x + d = 0
 *
 * Método: Cardano-Tartaglia
 *
 * Passos:
 *  1. Normaliza a equação dividindo por 'a'.
 *  2. Reduz para a forma deprimida: t^3 + p*t + q = 0
 *     (usando a substituição x = t - b/(3a))
 *  3. Calcula o discriminante Δ = (q/2)^2 + (p/3)^3
 *  4. Dependendo do sinal de Δ:
 *      - Δ > 0  -> uma raiz real e duas complexas conjugadas
 *      - Δ = 0  -> raízes reais, com pelo menos duas iguais
 *      - Δ < 0  -> três raízes reais distintas (caso irredutível,
 *                  resolvido via forma trigonométrica de Viète)
 */
public class CubicSolver {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Resolução de equação do 3º grau: a*x^3 + b*x^2 + c*x + d = 0");
        System.out.print("Digite a: ");
        double a = sc.nextDouble();
        System.out.print("Digite b: ");
        double b = sc.nextDouble();
        System.out.print("Digite c: ");
        double c = sc.nextDouble();
        System.out.print("Digite d: ");
        double d = sc.nextDouble();

        sc.close();

        if (a == 0) {
            System.out.println("Coeficiente 'a' não pode ser zero (não seria uma equação do 3º grau).");
            return;
        }

        resolverCubica(a, b, c, d);
    }

    public static void resolverCubica(double a, double b, double c, double d) {
        // 1. Normalização: x^3 + B*x^2 + C*x + D = 0
        double B = b / a;
        double C = c / a;
        double D = d / a;

        // 2. Redução à forma deprimida: t^3 + p*t + q = 0, com x = t - B/3
        double deslocamento = B / 3.0;
        double p = C - (B * B) / 3.0;
        double q = (2.0 * B * B * B) / 27.0 - (B * C) / 3.0 + D;

        // 3. Discriminante
        double delta = (q * q) / 4.0 + (p * p * p) / 27.0;

        System.out.println("\nEquação deprimida: t^3 + (" + p + ")t + (" + q + ") = 0");
        System.out.println("Discriminante (Δ) = " + delta);

        double[] raizesReais;

        if (Math.abs(delta) < 1e-12) {
            // Δ = 0 -> raízes reais, pelo menos duas iguais
            double u = Math.cbrt(-q / 2.0);
            double t1 = 2 * u;
            double t2 = -u;
            System.out.println("\nCaso: Δ = 0 (raízes reais, com repetição)");
            System.out.printf("x1 = %.6f%n", t1 - deslocamento);
            System.out.printf("x2 = x3 = %.6f%n", t2 - deslocamento);

        } else if (delta > 0) {
            // Δ > 0 -> uma raiz real e duas complexas conjugadas
            double sqrtDelta = Math.sqrt(delta);
            double u = Math.cbrt(-q / 2.0 + sqrtDelta);
            double v = Math.cbrt(-q / 2.0 - sqrtDelta);
            double t1 = u + v;

            double x1 = t1 - deslocamento;

            // Parte real e imaginária das raízes complexas
            double parteReal = -(u + v) / 2.0 - deslocamento;
            double parteImag = (Math.sqrt(3) / 2.0) * (u - v);

            System.out.println("\nCaso: Δ > 0 (uma raiz real e duas complexas conjugadas)");
            System.out.printf("x1 = %.6f%n", x1);
            System.out.printf("x2 = %.6f + %.6fi%n", parteReal, parteImag);
            System.out.printf("x3 = %.6f - %.6fi%n", parteReal, parteImag);

        } else {
            // Δ < 0 -> três raízes reais distintas (forma trigonométrica de Viète)
            double m = 2.0 * Math.sqrt(-p / 3.0);
            double theta = Math.acos((3.0 * q) / (p * m)) / 3.0;

            double x1 = m * Math.cos(theta) - deslocamento;
            double x2 = m * Math.cos(theta - 2.0 * Math.PI / 3.0) - deslocamento;
            double x3 = m * Math.cos(theta - 4.0 * Math.PI / 3.0) - deslocamento;

            System.out.println("\nCaso: Δ < 0 (três raízes reais distintas)");
            System.out.printf("x1 = %.6f%n", x1);
            System.out.printf("x2 = %.6f%n", x2);
            System.out.printf("x3 = %.6f%n", x3);
        }
    }
}
