# Matrix Calculator

This is a **Java-based console application** for performing matrix calculations. The program allows users to **create matrices, apply arithmetic operations, and manage matrix states.**

## ⭐ Features

- ✅ Create matrices of custom size
- ✅ Perform basic arithmetic operations (**+, -, \*, /**) on matrices
- ✅ Set and print matrix states
- ✅ Error handling for incorrect inputs

## 📥 Installation

To use this program, ensure you have the following:

- **Java Development Kit (JDK) 8 or higher**

Clone the repository:

```sh
 git clone https://github.com/Cominta/Matrix/edit/master
 cd matrix-calculator
```

Compile the program:

```sh
javac -d bin -sourcepath src src/console/Main.java
```

Run the program:

```sh
java -cp bin console.Main.java
```

## 🚀 Usage

When the program starts, it will prompt you to enter commands. The available commands are:

### 📌 Create a New Matrix

```
new
```

- You will be prompted to enter the **matrix size** (rows and columns).
- Choose the **matrix mode**:
  - `R` for **real numbers**
  - `Z<number>` for **integer numbers with a modulus**
- Input **matrix values** row by row.

### 🔢 Perform Operations

- **Multiplication**: `* n k` *(Multiplies row **`n`** by **`k`**)*
- **Division**: `/ n k` *(Divides row **`n`** by **`k`**)*
- **Addition**: `+ n k multiplier` *(Adds row **`n`** multiplied by **`multiplier`** to row **`k`**)*
- **Subtraction**: `- n k multiplier` *(Subtracts row **`n`** multiplied by **`multiplier`** from row **`k`**)*
- **Swap rows**: `s n k` *(Swaps row **`n`** with row **`k`**)*

### 🖨 Set and Print

- `set currM <matrix_id>`: **Sets the current matrix** to the specified ID.
- `print mlist`: **Prints the list of available matrices.**
- `print currM`: **Prints the current matrix.**

### ❌ Exit Program

```
exit
```

## ⚠️ Error Handling

If incorrect input is provided, the program will display an **error message** and allow you to retry.

## 🤝 Contributing

Feel free to submit **issues** and **pull requests** for improvements!

## 📜 License

This project is licensed under the **MIT License**.

