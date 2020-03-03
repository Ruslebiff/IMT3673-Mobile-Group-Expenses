package imt3673.ass.groupexpenses

import kotlin.math.exp

/**
 * Represents all the expenses of the group of people.
 *
 * TODO implement the functionality of this class
 */
class Expenses {

    // NOTE: Expenses MUST have a default, non-argument constructor.
    private val listOfExpenses = mutableListOf<SingleExpense>()

    // Adds new expense to the expenses list.
    // If the Person does not exist in the expenses,
    //   the person is added to the list, and false is returned.
    // If the Person already exist in the expenses,
    // the new expense amount is added to the person's existing amount and true is returned.
    // There should only be
    // one instance of SingleExpense associated with a single person in the expenses.
    // No duplicates.
    fun add(expense: SingleExpense): Boolean {
        val old : SingleExpense? = listOfExpenses.find{it.person == expense.person}

        return if (old == null) {
            listOfExpenses.add(expense)
            false
        } else {
            val new = SingleExpense(expense.person, expense.amount +
                    old.amount, old.description.plus(", ").plus(expense.description))
            listOfExpenses.remove(old)
            listOfExpenses.add(new)
            true
        }
    }

    // Replaces the expense for a given person
    // This method is similar to #addExpense, however, instead of adding
    // the claim amount to the existing person, it replaces it instead.
    fun replace(expense: SingleExpense): Boolean {
        val old : SingleExpense? = listOfExpenses.find{it.person == expense.person}

        return if (old == null) {
            listOfExpenses.add(expense)
            false // person was not found in list
        } else {
            val new = SingleExpense(expense.person, expense.amount, expense.description)
            listOfExpenses.remove(old)
            listOfExpenses.add(new)
            true
        }
    }

    // Removes an expense association for this person.
    // If the person exists in the expenses, it returns true.
    // Otherwise, it returns false.
    fun remove(person: String): Boolean {
        val personInList : SingleExpense? = listOfExpenses.find{it.person == person}

        return if (personInList == null) {
            false // person was not found in list
        } else {
            listOfExpenses.remove(personInList)
            true
        }
    }

    // Returns the amount of expenses for a given person.
    // If the person does not exist, the function returns failed result.
    fun amountFor(person: String): Result<Long> {
        val personInList : SingleExpense? = listOfExpenses.find{it.person == person}

        return if (personInList == null) {
            Result.failure(Exception("Person does not exist"))
        } else {
            Result.success(personInList.amount)
        }
    }

    // Returns the list of all expenses.
    // If no expenses have been added yet, the function returns an empty list.
    fun allExpenses(): List<SingleExpense> {
        return this.listOfExpenses
    }

    // Makes a deep copy of this expense instance
    fun copy(): Expenses {
        val exp = Expenses()
        allExpenses().forEach {
            exp.add(SingleExpense(it.person, it.amount, it.description))
        }
        return exp
    }
}