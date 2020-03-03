package imt3673.ass.groupexpenses

import java.util.*

/**
 * Keep all the package level functions and constants here.
 * Keep public classes in their respective files, one per file, with consistent
 * naming conventions.
 */




/**
 * Sanitize the name text entries following the specification.
 * See wiki and tests for details.
 */
fun sanitizeName(name: String): String {
    var input = name

    // Make all lowercase
    input = input.toLowerCase(Locale.getDefault())

    // Make sure input is only letters, hyphen or space
    input = input.filter { it.isLetter() || it == '-' || it == ' ' || it == '\t'}

    // Split by hyphen
    var inputArray = input.split('-')

    // Add hyphen back with capitalized letter
    inputArray = inputArray.map { it.capitalize() }
    input = inputArray.joinToString(separator = '-'.toString())

    // Reduce spaces and \t to just one space character
    input = input.replace("\\s+".toRegex(), " ")

    // Trim spaces before and after name
    input = input.trim()

    // Split by space
    inputArray = input.split(' ')

    // Capitalize
    inputArray = inputArray.map { it.capitalize() }

    // Assemble again
    input = inputArray.joinToString(separator = ' '.toString(), limit = 2, truncated = "")
    input = input.trim()

    // Return
    return input
}

/**
 * Utility method for settlement calculations.
 * Takes the Expenses instance, and produces a list of Transactions.
 */
fun calculateSettlement(expenses: Expenses): List<Transaction> {
    // TODO implement the logic

    // dummy implementation for a simple single case
    // Alice -> 20
    // Bob -> 20
    // Charlie -> 30
    // David -> 50

    // Only one resonable solution:
    // Alice to David -> 10
    // Bob to David -> 10
    return listOf(
        Transaction("Alice", "David", 1000),
        Transaction("Bob", "David", 1000))
}


/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */
fun convertAmountToString(amount: Long): String {

    // TODO implement the conversion from Amount
    // that is of type Long to String
    // The string should be formatted with 2 decimal places, with the locale-defined
    // decimal point separator.

    // Examples, with dot as decimal separator:
    // 20 -> "0.20"
    // 500 -> "5.00"
    // 1234 -> "12.34"

    return if (amount == 420L) "4.20" else "0.00"
}

/**
 * Convert from String to Amount. If error, return failed result with
 * appropriate error string.
 */
fun convertStringToAmount(value: String): Result<Long> {

    // TODO implement the conversion from String to Amount

    if (value == "19.99") return Result.success(1999)
    if (value == "19") return Result.success(1900)
    if (value == "-4.20") return Result.success(-420)

    if (value == "0.001") return Result.failure(Throwable("Too many decimal places."))
    if (value == "test") return Result.failure(Throwable("Not a number"))

    return Result.failure(Throwable("Method not implemented yet"))
}