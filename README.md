# CubicSolver

Programa em Java que resolve a **equação geral do terceiro grau**:

```
a·x³ + b·x² + c·x + d = 0
```

utilizando o **método de Cardano-Tartaglia**, com tratamento completo dos três cenários possíveis de raízes (reais distintas, reais repetidas, e uma real + duas complexas conjugadas).

---

## 📌 Sumário

- [Visão geral](#-visão-geral)
- [Fundamentação matemática](#-fundamentação-matemática)
- [Como funciona o algoritmo](#-como-funciona-o-algoritmo)
- [Estrutura do código](#-estrutura-do-código)
- [Como executar](#-como-executar)
- [Exemplos de uso](#-exemplos-de-uso)
- [Como usar como classe utilitária](#-como-usar-como-classe-utilitária)
- [Limitações](#-limitações)
- [Possíveis melhorias](#-possíveis-melhorias)
- [Referências](#-referências)

---

## 📖 Visão geral

O método de Cardano-Tartaglia é uma fórmula fechada (analítica) para encontrar as raízes de qualquer equação cúbica, publicada por Girolamo Cardano em 1545 (com contribuições originais de Niccolò Tartaglia e Scipione del Ferro). Diferente do caso quadrático (Bhaskara), a solução da cúbica exige uma transformação intermediária — a **equação deprimida** — e a análise de um **discriminante** para decidir qual forma final das raízes deve ser usada.

Este projeto implementa esse processo passo a passo em Java, sem depender de bibliotecas externas de números complexos: as partes real e imaginária são calculadas manualmente quando necessário.

---

## 🧮 Fundamentação matemática

### 1. Normalização

Dada a equação geral:

```
a·x³ + b·x² + c·x + d = 0      (a ≠ 0)
```

Dividimos tudo por `a`, obtendo a forma:

```
x³ + B·x² + C·x + D = 0
```

onde `B = b/a`, `C = c/a`, `D = d/a`.

### 2. Redução à forma deprimida

Aplicando a substituição `x = t - B/3`, o termo quadrático desaparece, resultando em:

```
t³ + p·t + q = 0
```

com:

```
p = C - B²/3
q = 2B³/27 - BC/3 + D
```

### 3. Discriminante

```
Δ = (q/2)² + (p/3)³
```

O sinal de `Δ` determina a natureza das raízes:

| Δ         | Natureza das raízes                                      |
|-----------|-----------------------------------------------------------|
| `Δ > 0`   | Uma raiz real e duas raízes complexas conjugadas           |
| `Δ = 0`   | Três raízes reais, sendo pelo menos duas iguais            |
| `Δ < 0`   | Três raízes reais e distintas (caso irredutível/casus irreducibilis) |

### 4. Cálculo das raízes

**Caso Δ = 0:**

```
u = ∛(-q/2)
t1 = 2u
t2 = t3 = -u
```

**Caso Δ > 0 (fórmula clássica de Cardano):**

```
u = ∛(-q/2 + √Δ)
v = ∛(-q/2 - √Δ)

t1 = u + v                          (raiz real)
t2 = -(u+v)/2 + i·(√3/2)(u-v)       (complexa)
t3 = -(u+v)/2 - i·(√3/2)(u-v)       (complexa conjugada)
```

**Caso Δ < 0 (forma trigonométrica de Viète):**

Como `u` e `v` seriam números complexos nesse caso, usa-se uma abordagem trigonométrica que evita raízes cúbicas complexas:

```
m = 2·√(-p/3)
θ = (1/3)·arccos( (3q) / (p·m) )

t1 = m·cos(θ)
t2 = m·cos(θ - 2π/3)
t3 = m·cos(θ - 4π/3)
```

### 5. Retorno à variável original

Em todos os casos, a raiz final é obtida desfazendo a substituição do passo 2:

```
x = t - B/3
```

---

## ⚙️ Como funciona o algoritmo

O fluxo de execução do método `resolverCubica` segue exatamente as etapas matemáticas acima:

1. Recebe os coeficientes `a`, `b`, `c`, `d`.
2. Normaliza a equação (`B`, `C`, `D`).
3. Calcula `p`, `q` e o deslocamento `B/3`.
4. Calcula o discriminante `Δ`.
5. Verifica o sinal de `Δ` (com tolerância `1e-12` para tratar erros de ponto flutuante ao comparar com zero) e aplica a fórmula correspondente.
6. Imprime as raízes no console, já ajustadas para a variável `x` original.

---

## 🗂️ Estrutura do código

```
CubicSolver.java
│
├── main(String[] args)
│     Lê os coeficientes a, b, c, d via Scanner e chama resolverCubica.
│
└── resolverCubica(double a, double b, double c, double d)
      Executa todo o processo de Cardano-Tartaglia e imprime as raízes.
```

A classe foi escrita de forma que a lógica matemática (`resolverCubica`) esteja separada da entrada de dados (`main`), facilitando reaproveitamento em outros contextos (testes, outras interfaces, etc.).

---

## ▶️ Como executar

### Pré-requisitos

- JDK instalado (Java 8 ou superior)

### Compilar

```bash
javac CubicSolver.java
```

### Executar

```bash
java CubicSolver
```

O programa solicitará os coeficientes `a`, `b`, `c` e `d` via terminal.

---

## 💡 Exemplos de uso

### Exemplo 1 — Três raízes reais distintas (Δ < 0)

Equação: `x³ - 6x² + 11x - 6 = 0` (raízes esperadas: 1, 2, 3)

```
Digite a: 1
Digite b: -6
Digite c: 11
Digite d: -6
```

Saída esperada (aproximada):
```
Caso: Δ < 0 (três raízes reais distintas)
x1 = 3.000000
x2 = 1.000000
x3 = 2.000000
```

### Exemplo 2 — Uma raiz real e duas complexas (Δ > 0)

Equação: `x³ + x + 1 = 0`

```
Digite a: 1
Digite b: 0
Digite c: 1
Digite d: 1
```

Saída esperada (aproximada):
```
Caso: Δ > 0 (uma raiz real e duas complexas conjugadas)
x1 = -0.682328
x2 = 0.341164 + 1.161541i
x3 = 0.341164 - 1.161541i
```

### Exemplo 3 — Raízes reais repetidas (Δ = 0)

Equação: `x³ - 3x + 2 = 0` (raízes esperadas: 1, 1, -2)

```
Digite a: 1
Digite b: 0
Digite c: -3
Digite d: 2
```

Saída esperada (aproximada):
```
Caso: Δ = 0 (raízes reais, com repetição)
x1 = -2.000000
x2 = x3 = 1.000000
```

---

## 🔧 Como usar como classe utilitária

Se você não quiser usar o `Scanner` interativo, chame o método diretamente a partir de outra classe:

```java
public class Main {
    public static void main(String[] args) {
        CubicSolver.resolverCubica(1, -6, 11, -6);
    }
}
```

Isso imprime as raízes no console sem exigir entrada do usuário. Para reaproveitar em outro projeto, você pode adaptar o método para **retornar** as raízes (em vez de apenas imprimir), por exemplo usando um array de `double` para os casos reais e um objeto próprio para números complexos.

---

## ⚠️ Limitações

- O programa assume que `a ≠ 0` (caso contrário, não seria uma equação do 3º grau) e trata esse caso com uma mensagem de erro.
- As raízes complexas são impressas como texto (`parte real + parte imaginária i`), mas não existe uma classe `Complexo` reutilizável — os valores são calculados apenas para exibição.
- Por trabalhar com `double`, o algoritmo está sujeito a erros de arredondamento de ponto flutuante, especialmente em casos limítrofes onde `Δ` está muito próximo de zero (por isso o uso da tolerância `1e-12`).
- A leitura de entrada não trata valores inválidos (ex.: texto no lugar de número); um `InputMismatchException` seria lançado no `Scanner` nesse caso.

---

## 🚀 Possíveis melhorias

- Criar uma classe `Complexo` (com partes real/imaginária e operações básicas) para representar as raízes de forma mais robusta.
- Fazer `resolverCubica` **retornar** as raízes (ex.: `List<Complexo>`) em vez de apenas imprimir, permitindo testes automatizados.
- Adicionar tratamento de exceções na leitura de entrada do `Scanner`.
- Criar uma suíte de testes unitários (JUnit) validando os três casos do discriminante com equações de raízes conhecidas.
- Adicionar suporte a entrada via linha de comando (argumentos) além do modo interativo.

---

## 📚 Referências

- Cardano, G. *Ars Magna* (1545).
- Weisstein, Eric W. "Cubic Formula." *MathWorld — A Wolfram Web Resource*.
- Material didático sobre a forma trigonométrica de Viète para o *casus irreducibilis* da equação cúbica.

---

## 📄 Licença

Uso livre para fins de estudo e aprendizado de algoritmos e métodos numéricos.
