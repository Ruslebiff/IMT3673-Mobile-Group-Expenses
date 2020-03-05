package imt3673.ass.groupexpenses

import java.lang.NumberFormatException
import java.util.*
import kotlin.math.roundToLong

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

    var expensesList = expenses.allExpenses()
    if (expensesList.size < 2) return listOf()

    var personsList = expensesList.map{it.person}
    var transactionsList = mutableListOf<Transaction>()
    var paidMap = mutableMapOf<String, Long>()

    var totalAmount: Long = 0
    val totalPersons = expensesList.size

    expensesList.forEach{
        totalAmount += it.amount.toLong()
        paidMap[it.person] = it.amount
    }

    var totalPerPerson = (totalAmount / totalPersons)


    // What each person owes
    var owesMap = mutableMapOf<String, Long>()
    paidMap.forEach{
        owesMap[it.key] = (totalPerPerson.minus(it.value))
    }

    var payMap = mutableMapOf<String, Long>()           // should pay receivers
    var receiveMap = mutableMapOf<String, Long>()       // should receive payment from payers

    owesMap.forEach{
        if (it.value > 0L){         // person owes money to someone else
           payMap[it.key] = it.value
        } else if (it.value < 0L) { // person should get money back
            receiveMap[it.key] = it.value*(-1)
        }
    }

    println("Pay: " + payMap)
    println("Receive: " + receiveMap)
    println("\n")
    var payIterator = payMap.iterator()
    var receiveIterator = receiveMap.iterator()
    var receiverKey = receiveIterator.next().key
    var payerKey = payIterator.next().key

    while (payMap.isNotEmpty()){
        while(payMap[payerKey]!! > 0L){
            while (receiveMap.isNotEmpty()) {
                if (receiveIterator.hasNext()){
                    receiverKey = receiveIterator.next().key
                }
                println("ReceiverKey: " + receiverKey)

                println("\n\nREC: " + receiveMap)
                println("PAY: " + payMap + "\n\n")
                println("receiverKey = " + receiverKey)
                println("receiveMap[receiverKey] =" + receiveMap[receiverKey])
                println("payMap[payerKey] =" + payMap[payerKey])

                if (receiveMap[receiverKey] == null){
                    println("ReceiverKey NULL!")

                    println("new receiverkey = " + receiverKey)
                }

                if (payMap[payerKey] == receiveMap[receiverKey]){             // same amount
                    println("=== Same Amount ===")

                    transactionsList.add(Transaction(payerKey, receiverKey, receiveMap[receiverKey]!!))
                    payMap[payerKey] = payMap[payerKey]!!.minus(receiveMap[receiverKey]!!)

                    var oldReceiverKey = receiverKey
                    if (receiveIterator.hasNext()){
                        receiverKey = receiveIterator.next().key
                    }
                    //receiveMap.remove(oldReceiverKey)

                    break
                } else if (receiveMap[receiverKey]!! > payMap[payerKey]!!) {      // receiver need more than payer has
                    println("=== Receiver More Than Payer ===")

                    transactionsList.add(Transaction(payerKey, receiverKey, payMap[payerKey]!!))
                    receiveMap[receiverKey] = receiveMap[receiverKey]!!.minus(payMap[payerKey]!!)
                    payMap[payerKey] = payMap[payerKey]!!.minus(payMap[payerKey]!!)


                    break
                } else if (receiveMap[receiverKey]!! < payMap[payerKey]!!){       // receiver need less than payer has
                    println("=== Receiver Less Than Payer ===")

                    transactionsList.add(Transaction(payerKey, receiverKey, receiveMap[receiverKey]!!))
                    payMap[payerKey] = payMap[payerKey]!!.minus(receiveMap[receiverKey]!!)
                    var oldReceiverKey = receiverKey
                    if (receiveIterator.hasNext()){
                        receiverKey = receiveIterator.next().key
                    }

                    println("\nEND")
                    println("NEW receiver key = " + receiverKey)
                    println("PAYMAP " + payMap)
                    println("RECMAP " + receiveMap)
                    break
                } else {
                    println("N O T H I N G    F I T S")
                    break
                }
            }
        }

        var oldPayerKey = payerKey
        if (payIterator.hasNext()){
            payerKey = payIterator.next().key
        }
        payMap.remove(oldPayerKey)

        println("\npayMap now: " + payMap)
        println("receiveMap now: " + receiveMap)
        println("Trans list now: " + transactionsList +"\n")
    }

//
//     dummy implementation for a simple single case
//     Alice -> 20
//     Bob -> 20
//     Charlie -> 30
//     David -> 50

    // Only one reasonable solution:
    // Alice to David -> 10
    // Bob to David -> 10
    println(transactionsList)
    return transactionsList
    // return transactionsList
}


/**
 * Converts a given Long amount into a formatted String, with
 * two decimal places. Note, the decimal place separator can be
 * dot or comma, subject to the current locale used.
 */
fun convertAmountToString(amount: Long): String {
    var stringAmount = amount.toString()
    when {
        stringAmount.length >= 3 -> {
            // reverse it, add a comma at place 2, reverse it back
            stringAmount = stringAmount.reversed()
            stringAmount = StringBuilder(stringAmount).insert(2, ',').toString()
            stringAmount = stringAmount.reversed()
        }
        stringAmount.length == 2 -> {
            stringAmount = if (stringAmount.startsWith('-')){
                // Same as if length == 1, but with a dash in front
                java.lang.StringBuilder(stringAmount).insert(1, "0,0").toString()
            } else {
                java.lang.StringBuilder(stringAmount).insert(0, "0,").toString()
            }
        }
        else -> {
            stringAmount = stringAmount.reversed()
            stringAmount = java.lang.StringBuilder(stringAmount).append("0,0").toString()
            stringAmount = stringAmount.reversed()
        }
    }

    return stringAmount
}

/**
 * Convert from String to Amount. If error, return failed result with
 * appropriate error string.
 */
fun convertStringToAmount(value: String): Result<Long> {

    var convertedValue = value.replace(',', '.')
    return try{
        val f = convertedValue.toFloat()
        Result.success((f * 100.0).roundToLong())
    } catch(e: NumberFormatException) {
        Result.failure(e)
    }

}