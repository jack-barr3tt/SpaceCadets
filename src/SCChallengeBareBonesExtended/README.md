# Barebones Extended Documentation

## Keywords

### Variables

* #### `clear <key>`
Initialised a variable called `key` with a value of 0 or sets the value to 0 if the variable already exists.

* #### `incr <key>`
Increases the value of a variable called `key` by 1.

* #### `decr <key>`
Decreases the value of a variable called `key` by 1. Throws an error if the value becomes negative.

* #### `set <key> <value>`
Sets the value of variable `key` to the value specified by `value`.

### Math

* #### `add <key> <value1> <value2>`
Adds the values specified by `value1` and `value2` and stores the result in the variable `key`

* #### `subtract <key> <value1> <value2>`
Subtracts the value in `value2` from the value in `value1` and stores the result in the variable `key`

* #### `multiply <key> <value1> <value2>`
Multiplies the values specified in `value1` and `value2` and stores the result in the variable `key`

* #### `divide <key> <value1> <value2>`
Divides the value in `value1` by the value in `value2` and stores the result in the variable `key`

### Boolean conditions

Boolean conditions can be used to control selection and iteration

* #### `<value1> eq <value2>`
Returns true when the values in `value1` and `value2` are equal, and false otherwise

* #### `<value1> not <value2>`
Returns true when the values in `value1` and `value2` are not equal, and false otherwise

* #### `<value1> gt <value2>`
Returns true when the value in `value1` is greater than the value in `value2` and false otherwise

* #### `<value1> lt <value2>`
Returns true when the value in `value1` is less than the value in `value2` and false otherwise

### Selection
* Selection can be achieved using `if <condition> then` where `<condition>` is a boolean condition as specified above.
* If the condition is true, the code between the `else` or `end` (whichever appears first) is executed.
* If the condition is false and the statement has an `else` clause, code between the `else` and `end` statements are executed
* Selection statements can be nested

### Iteration
* Iteration can be achieved using `while <condition> do` where `<condition>` is a boolean condition as specified above
* The code between the `while` and `end` statements will be continually executed until the condition is no longer met
* If the condition is never met the code inside the loop will never run
* Iteration statements can be nested

## Comments

#### Line comments
* The only part of the language that takes into account linebreaks is line comments.
* A line comment is started by `//`
* All text between this delimiter and a newline will be ignored by the interpreter

#### Fragment comments
* Fragment comments are enclosed by `/*` and `*/`
* All text between these delimiters will be ignored by the interpreter

## Language Quirks

### Scope
Variables are not scoped, which means as long as they have been previously initialised by `clear` they can be accessed and modified from anywhere

### Datatypes
The only type of data you can store in variables is positive integers. Attempting to store a negative integer will result in a runtime error

### Addressing
When providing data (shown by `<value>`), the interpreter will first check if the given key exists in memory. If it does not, it will attempt to parse the key as an integer. If this fails, a runtime error will be thrown.

### Line separators
Lines are separated by semicolons. Spaces and line breaks will be completely disregarded by the interpreter, so code style is left as a choice for the user.
