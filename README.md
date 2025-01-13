# Ex2: Simple Spreadsheet Project

Hello! this is my project for Ex2. It's a basic spreadsheet program where you can enter text, numbers, and formulas into a grid of cells. You can also save your work to a file and load it back later.





![Screenshot 2025-01-13 220231](https://github.com/user-attachments/assets/19b7d3bd-2e46-46bb-a61c-14ac451cf45a)

## Files

### 1. **Ex2GUI.java**
This is the graphical interface of the program. It:
- Lets you click on cells to edit their content.
- Displays the spreadsheet grid.
- Allows you to save and load files through buttons.

  
### 2. **Ex2Sheet.java**
This is where most of the logic happens. It:
- Manages the 2D grid of cells.
- Allows you to get or set cell values.
- Evaluates formulas like `=A1+B2*3` or `=(2+3)*4`.
- Handles saving the spreadsheet to a file and loading it back.


### 3. **SCell.java**
This file defines what a cell is. A cell can:
- Hold text, numbers, or formulas.
- Validate and parse formulas.
- Return its value when evaluated.

### 4. **Sheet.java**
This is an interface that defines the main methods a spreadsheet should have. It includes:
- Getting and setting cell values.
- Evaluating formulas.
- Managing the grid size.

### 5. **Ex2Utils.java**
This is a helper class. It:
- Contains constants for the project (like default spreadsheet size).
- Provides utility functions for things like checking if a cell is empty.

### 6. **Cell.java**
This is another class that defines the structure of a single cell. It has methods for:
- Getting the cell's value.
- Checking its type (text, number, or formula).

### 7. **CellEntry.java**
This class manages cell positions in the grid using 2D coordinates.

### 8. **Index2D.java**
This is a class for managing 2D grid indices. It’s used to track cell locations in the spreadsheet.

---

## Features
- **Text Support**: Enter and display text in cells.
- **Number Support**: Enter numbers and use them in formulas.
- **Formula Support**: Write formulas to calculate values based on other cells.
  - Examples:
    - `=1+2`
    - `=A1+B2*3`
    - `=(2+3)*4`
- **Save and Load**: Save the spreadsheet to a file and load it later.
- **GUI**: Use a graphical interface to interact with the spreadsheet.

### Cycle and Error Handling in Spreadsheet

The spreadsheet program ensures robust handling of invalid inputs and self-referential cycles in formulas. Below are the mechanisms implemented for managing these cases:

#### **Cycle Detection**
A cycle occurs when a formula references a cell that directly or indirectly depends on itself. For example:
- If `A1` references `B1`, and `B1` references `A1`, this forms a loop.
- The depth calculation identifies such cycles during formula evaluation. Cells involved in a cycle are marked with the error code `ERR_CYCLE`.

In the GUI, cells detected as part of a cycle are displayed in **red**, making it visually clear to the user that a circular dependency exists.

#### **ERR_FORM Handling**
When invalid formulas are entered, the program marks them with `ERR_FORM`. Examples of invalid formulas include:
- Incomplete expressions, like `=A1+`
- Non-matching parentheses, such as `=(A1+B2`
- References to invalid cells or improper formats like `=123A`

Cells with `ERR_FORM` are displayed in **red** in the GUI. The error handling ensures that invalid formulas do not disrupt the calculation or display of other cells.


---

## How to Use

### Running the Program
1. Open `Ex2GUI.java` in your IDE (IntelliJ, Eclipse, etc.).
2. Run it.

### Using the GUI
1. Click on a cell to select it.
2. Enter text, numbers, or formulas (start formulas with `=`) in the input box.
3. Save your work using the save button or load a file with the load button.

### File Format
The program saves data in a text file with this format:
- **First Line**: A header (ignored during loading).
- **Other Lines**: Each line represents a cell in the format:

---

## Example
1. Open the program.
2. Enter the following:
 - `A1 = 5`
 - `B1 = 3`
 - `C1 = =A1+B1`
3. Save the file and close the program.
4. Reload the file, and `C1` will still show `8`.

---

## Testing
JUnit tests are included to check:
- If formulas are evaluated correctly.
- If saving and loading work as expected.
- If errors are handled properly.

---

## Author
- **Shachar Barel**  
- GitHub: https://github.com/Shachar-Barel/Ex2.git

---

## License
This project is for educational purposes.
